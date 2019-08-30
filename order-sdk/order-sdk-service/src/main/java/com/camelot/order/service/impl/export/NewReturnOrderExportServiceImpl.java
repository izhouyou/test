package com.camelot.order.service.impl.export;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.AjaxInfoConstants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.domain.NewReturnOrderDomain;
import com.camelot.order.export.service.*;
import com.camelot.order.export.snapshot.*;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisticsSalesVO;
import com.camelot.order.export.vo.StatisticsSourceVO;
import com.camelot.order.feign.FeignActivityService;
import com.camelot.order.feign.FeignCouponService;
import com.camelot.order.feign.FeignStoreService;
import com.camelot.order.service.NewReturnOrderService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hudya
 */
@Service("newReturnOrderExportService")
public class NewReturnOrderExportServiceImpl extends BaseServiceImpl<NewReturnOrderVO, NewReturnOrderDomain> implements NewReturnOrderExportService {

    private static Logger log = Log.get(NewReturnOrderExportServiceImpl.class);
    @Value("${fms.activity.number}")
    private String fmsActivityNumber;

    /**
     * 退货订单service
     */
    @Autowired
    private NewReturnOrderService newReturnOrderService;
    /**
     * 销售订单service
     */
    @Autowired
    private NewSalesOrderExportService newSalesOrderExportService;
    /**
     * 订单商品service
     */
    @Autowired
    private NewSalesOrderGoodsExportService newSalesOrderGoodsExportService;
    /**
     * 退货订单商品service
     */
    @Autowired
    private NewReturnOrderGoodsExportService newReturnOrderGoodsExportService;
    /**
     * 事务控制
     */
    @Autowired
    private TransactionTemplate transactionTemplate;
    /**
     * 优惠吗服务
     */
    @Autowired
    private FeignCouponService feignCouponService;
    /**
     * 单号service
     */
    @Autowired
    private NewOrderNumberExportService newOrderNumberExportService;
    /**
     * 记录日志
     */
    @Autowired
    private NewSalesOrderLogExportService newSalesOrderLogExportService;
    /**
     * 门店service
     */
    @Autowired
    private FeignStoreService feignStoreService;
    /** 营促销服务 */
    @Autowired
    private FeignActivityService feignActivityService;

    @Override
    public ExecuteResult<PageInfo<NewReturnOrderVO>> queryReturnOrderListPage(NewReturnOrderVO vo, Page page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-querySalesOrderList", JSONObject.toJSONString(vo), JSONObject.toJSONString(page));
        ExecuteResult<PageInfo<NewReturnOrderVO>> result = new ExecuteResult<>();
        try {
            queryAuthorize(vo);
            ExecuteResult<List<NewReturnOrderGoodsVO>> returnOrderGoodsResult = new ExecuteResult<>();
            if (Utility.isNotEmpty(vo.getGoodsSn()) || Utility.isNotEmpty(vo.getGoodsFrameNumber()) || Utility.isNotEmpty(vo.getGoodsBarcode())) {
                NewReturnOrderGoodsVO newReturnOrderGoodsVO = new NewReturnOrderGoodsVO();
                newReturnOrderGoodsVO.setGoodsSn(vo.getGoodsSn());
                newReturnOrderGoodsVO.setGoodsFrameNumber(vo.getGoodsFrameNumber());
                newReturnOrderGoodsVO.setGoodsBarcode(vo.getGoodsBarcode());
                returnOrderGoodsResult = newReturnOrderGoodsExportService.queryReturnOrderGoods(newReturnOrderGoodsVO);
                if (Utility.isNotEmpty(returnOrderGoodsResult.getResult())) {
                    List<NewReturnOrderGoodsVO> newReturnOrderGoodsVOS = returnOrderGoodsResult.getResult();
                    // 退货订单id
                    List<Long> fieldList = newReturnOrderGoodsVOS.stream().map(e -> e.getReturnOrderId()).collect(Collectors.toList());
                    vo.setNewReturnOrderIdList(fieldList);
                }else{
                    result.setCode(Constants.EMPTY_CODE);
                    result.setResultMessage(Constants.QUERY_SUCCESS);
                    return result;
                }
            }
            vo.setDelFlg(Constants.DELFLG_NORMAL);
            ExecuteResult<PageInfo<NewReturnOrderVO>> executeResult = newReturnOrderService.queryListByPage(vo, page);
            if (Utility.isNotEmpty(executeResult.getResult())) {
                List<NewReturnOrderVO> list = executeResult.getResult().getList();
                // 退货订单id集合
                List<Long> fieldList = list.stream().map(e -> e.getReturnOrderId()).collect(Collectors.toList());
                NewReturnOrderGoodsVO newReturnOrderGoodsVO = new NewReturnOrderGoodsVO();
                newReturnOrderGoodsVO.setReturnOrderIdList(fieldList);
                // 订单id集合
                List<Long> saleIdList = list.stream().map(e -> e.getSalesOrderId()).collect(Collectors.toList());
                NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
                newSalesOrderVO.setSalesOrderIdList(saleIdList);
                ExecuteResult<List<NewSalesOrderVO>> saleResult = newSalesOrderExportService.queryList(newSalesOrderVO);
                // 销售订单集合
                List<NewSalesOrderVO> saleList = saleResult.getResult();
                // 退货订单商品集合数据
                ExecuteResult<List<NewReturnOrderGoodsVO>> returnGoodsResult = newReturnOrderGoodsExportService.queryReturnOrderGoods(newReturnOrderGoodsVO);
                if(Utility.isNotEmpty(returnGoodsResult.getResult())){
                    List<NewReturnOrderGoodsVO> newReturnOrderGoodsVOList = returnGoodsResult.getResult();
                    for (NewReturnOrderVO newReturnOrderVO : list) {
                        List<NewReturnOrderGoodsVO> returnGoodsList = new ArrayList<>();
                        // 退货订单商品数据
                        for (NewReturnOrderGoodsVO returnOrderGoodsVO : newReturnOrderGoodsVOList) {
                            if (newReturnOrderVO.getReturnOrderId().equals(returnOrderGoodsVO.getReturnOrderId())) {
                                returnGoodsList.add(returnOrderGoodsVO);
                            }
                        }
                        newReturnOrderVO.setNewReturnOrderGoodsList(returnGoodsList);

                        //退货订单创建人
                        for (NewSalesOrderVO salesOrderVO : saleList) {
                            if(newReturnOrderVO.getSalesOrderId().equals(salesOrderVO.getSalesOrderId())){
                                newReturnOrderVO.setSalePersonName(salesOrderVO.getCreateUserName());
                            }
                        }

                    }
                }
                result.setResult(executeResult.getResult());
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            } else {
                result.setResult(null);
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setResult(null);
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-querySalesOrderList", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-querySalesOrderList", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<NewReturnOrderVO> searchReturnOrderDetail(Long id) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-searchSalesOrderDetail", JSONObject.toJSONString(id));
        ExecuteResult<NewReturnOrderVO> result = new ExecuteResult<>();
        try {
            if (Utility.isEmpty(id)) {
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage(Constants.NET_DELAY);
                return result;
            }
            ExecuteResult<NewReturnOrderVO> executeResult = newReturnOrderService.queryById(id);
            if (Utility.isNotEmpty(executeResult.getResult())) {
                //退货订单信息
                NewReturnOrderVO newReturnOrderVO = executeResult.getResult();
                // 销售订单详情
                Long salesOrderId = newReturnOrderVO.getSalesOrderId();
                ExecuteResult<NewSalesOrderVO> saleResult = newSalesOrderExportService.queryById(salesOrderId);
                NewSalesOrderVO newSalesOrderVO = saleResult.getResult();
                // 订单创建人信息
                newReturnOrderVO.setSalePersonName(newSalesOrderVO.getCreateUserName());
                // 查询该退货订单商品信息
                NewReturnOrderGoodsVO newReturnOrderGoodsVO = new NewReturnOrderGoodsVO();
                newReturnOrderGoodsVO.setReturnOrderId(newReturnOrderVO.getReturnOrderId());
                ExecuteResult<List<NewReturnOrderGoodsVO>> returnOrderGoodsResult = newReturnOrderGoodsExportService.queryReturnOrderGoods(newReturnOrderGoodsVO);
                if (Utility.isNotEmpty(returnOrderGoodsResult.getResult())) {
                    List<NewReturnOrderGoodsVO> newReturnOrderGoodsVOList = returnOrderGoodsResult.getResult();
                    // 金额保留两位小数
                    DecimalFormat format = new DecimalFormat("0.00");
                    for (NewReturnOrderGoodsVO returnOrderGoodsVO : newReturnOrderGoodsVOList) {
                        // 指导零售价
                        BigDecimal retailPrice = returnOrderGoodsVO.getRetailPrice();
                        // 实销单价
                        BigDecimal actualPrice = returnOrderGoodsVO.getActualPrice();
                        // 小计:零售单价*数量
                        if(Utility.isNotEmpty(returnOrderGoodsVO.getReturnAmount())){
                            BigDecimal amount = new BigDecimal(returnOrderGoodsVO.getReturnAmount());
                            // 实付金额:实销单价*数量
                            BigDecimal actualPriceSubtotal = actualPrice.multiply(amount);
                            returnOrderGoodsVO.setActualPriceSubtotal(format.format(actualPriceSubtotal));
                            if(Utility.isNotEmpty(retailPrice)){
                                BigDecimal retailPriceSubtotal = retailPrice.multiply(amount);
                                returnOrderGoodsVO.setRetailPriceSubtotal(format.format(retailPriceSubtotal));
                                // 零售差异
                                BigDecimal retailDifference = retailPriceSubtotal.subtract(actualPriceSubtotal);
                                returnOrderGoodsVO.setRetailDifference(format.format(retailDifference));
                            }
                        }
                    }
                    newReturnOrderVO.setNewReturnOrderGoodsList(newReturnOrderGoodsVOList);
                }

                // 订单小日志
                NewSalesOrderLogVO newSalesOrderLogVO = new NewSalesOrderLogVO();
                newSalesOrderLogVO.setSalesOrderId(salesOrderId);
                ExecuteResult<List<NewSalesOrderLogVO>> saleOrderLogResult = newSalesOrderLogExportService.queryList(newSalesOrderLogVO);
                if(Utility.isNotEmpty(saleOrderLogResult.getResult())){
                    List<NewSalesOrderLogVO> newSalesOrderLogVOList = saleOrderLogResult.getResult().stream().sorted(Comparator.comparing(NewSalesOrderLogVO::getOperationTime).reversed()).collect(Collectors.toList());
                    newReturnOrderVO.setNewSalesOrderLogVOS(newSalesOrderLogVOList);
                }

                result.setResult(newReturnOrderVO);
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            } else {
                result.setResult(null);
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setResult(null);
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-searchSalesOrderDetail", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-searchSalesOrderDetail", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<BigDecimal> queryReturnAmount(NewReturnOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReturnOrderExportServiceImpl-queryReturnAmount", JSONObject.toJSONString(vo));
        ExecuteResult<BigDecimal> result = new ExecuteResult<>();
        try {
            result = newReturnOrderService.queryReturnAmount(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewReturnOrderExportServiceImpl-queryReturnAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReturnOrderExportServiceImpl-queryReturnAmount", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<String> submitReturnOrder (NewReturnOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReturnOrderExportServiceImpl-submitReturnOrder", JSONObject.toJSONString(vo));
        ExecuteResult<String> result = new ExecuteResult<>();
        String validateResult = validateReturnOrderNotEmpty(vo);
        // 参数非空校验
        if (!Constants.SUCCESS_CODE.equals(validateResult)) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(validateResult);
            Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReturnOrderExportServiceImpl-submitReturnOrder", result);
            return result;
        }

        //退货订单重复性校验
        NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
        ExecuteResult<NewSalesOrderVO> repeatResult = newSalesOrderExportService.queryById(vo.getSalesOrderId());
        if (Utility.isNotEmpty(repeatResult.getResult())) {
            newSalesOrderVO = repeatResult.getResult();
            if(Utility.isNotEmpty(newSalesOrderVO.getReturnOrderNumber())&&newSalesOrderVO.getReturnStatusId().equals(OrderConstants.ORDER_DICT_RUTURNYES)){
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage("该订单已退货完成");
                Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReturnOrderExportServiceImpl-submitReturnOrder", result);
                return result;
            }
            String returnOrderNumber = newOrderNumberExportService.getOrderNumber(OrderConstants.ORDER_TYPE_RETURN,newSalesOrderVO.getStoreNumber());

            vo.setReturnOrderNumber(returnOrderNumber);
            vo.setDelFlg(Constants.DELFLG_NORMAL);
            vo.setCreateDate(new Date());
            vo.setReturnOrderAmount(newSalesOrderVO.getSalesOrderAmount());
            // 添加退货订单数据
            NewReturnOrderVO newReturnOrderVO = BeanUtil.copyPropertes(NewReturnOrderVO.class, newSalesOrderVO);
            // 退货订单状态赋值
            newReturnOrderVOCover(newReturnOrderVO,vo);
            newReturnOrderVO.setReturnOrderAmount(newSalesOrderVO.getSalesOrderAmount());
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        // 获取退单编号
                        ExecuteResult<String> genid = newOrderNumberExportService.getGenid();
                        if(Utility.isEmpty(genid.getResult())){
                            result.setCode(Constants.ERROR_CODE);
                            result.setResultMessage(Constants.SAVE_FAILURE);
                            status.setRollbackOnly();
                        }
                        String returnOrderId = genid.getResult();
                        Date nowDate = new Date();
                        newReturnOrderVO.setReturnOrderId(Long.valueOf(returnOrderId));
                        newReturnOrderVO.setReturnOrderNumber(returnOrderNumber);
                        newReturnOrderVO.setCreateDate(nowDate);
                        newReturnOrderVO.setCreateUserId(vo.getCreateUserId());
                        newReturnOrderVO.setCreateUserName(vo.getCreateUserName());
                        // 添加退单表数据
                        ExecuteResult<NewReturnOrderVO> addNewReturnOrderVO  = newReturnOrderService.add(newReturnOrderVO);
                        if(Utility.isEmpty(addNewReturnOrderVO.getResult())){
                            result.setCode(Constants.ERROR_CODE);
                            result.setResultMessage(Constants.SAVE_FAILURE);
                            status.setRollbackOnly();
                        }
                        //
                        NewReturnOrderVO newReturnOrder = addNewReturnOrderVO.getResult();
                        // 订单退货状态赋值
                        NewSalesOrderVO so = BeanUtil.copyPropertes(NewSalesOrderVO.class, vo);
                        newSalesOrderVOCoover(so,vo);
                        // 禁止修改原始订单创建人信息和提交信息
                        so.setCreateDate(null);
                        so.setOperaorPhone(null);
                        so.setCreateUserId(null);
                        so.setCreateUserName(null);
                        so.setModifyDate(nowDate);
                        so.setUpdateUserId(vo.getCreateUserId());
                        so.setUpdateUserName(vo.getCreateUserName());
                        // 修改订单状态
                        so.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
                        so.setSalesOrderStatusName(OrderConstants.ORDER_STATUS_FINISH_NAME);
                        //pay
                        so.setPaymentStatusId(OrderConstants.DEFAULT_RECEIPT_STATUS);
                        so.setPaymentStatusName(OrderConstants.DEFAULT_RECEIPT_STATUS_NAME);
                        //return
                        so.setReturnStatusId(OrderConstants.ORDER_DICT_RUTURNYES);
                        so.setReturnStatusName(OrderConstants.ORDER_DICT_RUTURNYES_NAME);

                        ExecuteResult<NewSalesOrderVO> updateNewSalesOrderVO = newSalesOrderExportService.update(so);
                        if(Utility.isEmpty(updateNewSalesOrderVO.getResult())){
                            result.setCode(Constants.ERROR_CODE);
                            result.setResultMessage("退单失败");
                            status.setRollbackOnly();
                        }
                        // 查询订单商品信息
                        NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
                        newSalesOrderGoodsVO.setSalesOrderId(vo.getSalesOrderId());
                        ExecuteResult<List<NewSalesOrderGoodsVO>> queryOrderGoodsResult = newSalesOrderGoodsExportService.queryOrderGoods(newSalesOrderGoodsVO);
                        if(Utility.isNotEmpty(queryOrderGoodsResult.getResult())){
                            List<NewSalesOrderGoodsVO> newSalesOrderGoodsList = queryOrderGoodsResult.getResult();
                            // 将订单商品数据复制到退单商品数据中
                            List<NewReturnOrderGoodsVO> newReturnOrderGoodsList = newSalesOrderGoodsList.stream().map(sg -> {
                                NewReturnOrderGoodsVO rg = BeanUtil.copyPropertes(NewReturnOrderGoodsVO.class, sg);
                                if (sg.getGoodsId().equals(rg.getGoodsId())) {
                                    rg.setReturnOrderId(newReturnOrder.getReturnOrderId());
                                    rg.setRetailPrice(sg.getActualPrice());
                                    rg.setReturnAmount(sg.getOrderAmount());
                                }
                                return rg;
                            }).collect(Collectors.toList());
                            // 添加退单商品数据
                            for (NewReturnOrderGoodsVO newReturnOrderGoodsVO : newReturnOrderGoodsList) {
                                    newReturnOrderGoodsVO.setReturnOrderId(addNewReturnOrderVO.getResult().getReturnOrderId());
                                    ExecuteResult<NewReturnOrderGoodsVO> addNewReturnOrderGoodsVO = newReturnOrderGoodsExportService.addNewReturnGoods(newReturnOrderGoodsVO);
                                    if(Utility.isEmpty(addNewReturnOrderGoodsVO.getResult())){
                                        result.setCode(Constants.ERROR_CODE);
                                        result.setResultMessage("退单失败");
                                        status.setRollbackOnly();
                                    }
                            }
                        }

                        ExecuteResult<NewSalesOrderVO> repeatResult = newSalesOrderExportService.queryById(vo.getSalesOrderId());
                        NewSalesOrderVO newSalesOrderVO = repeatResult.getResult();

                        // 添加日志数据
                        NewSalesOrderLogVO newSalesOrderLogVO = BeanUtil.copyPropertes(NewSalesOrderLogVO.class, newSalesOrderVO);
                        newSalesOrderLogVO.setOperatorName(vo.getCreateUserName());
                        newSalesOrderLogVO.setOperatorId(vo.getCreateUserId());
                        newSalesOrderLogVO.setOperatorNumber(vo.getCreateUserNumber());
                        ExecuteResult<NewSalesOrderLogVO> addSalesOrderLog = newSalesOrderLogExportService.addSalesOrderLog(newSalesOrderLogVO);
                        if(Utility.isEmpty(addSalesOrderLog.getResult())){
                            result.setCode(Constants.ERROR_CODE);
                            result.setResultMessage("退单失败");
                            status.setRollbackOnly();
                        }

                        // 扣除库存数量
                        if(Constants.VERIFY.equals(vo.getVerify())){
                            newSalesOrderVO.setReturnOrderNumber(so.getReturnOrderNumber());
                            ExecuteResult<String> salesVolumeVOExecuteResult = newSalesOrderExportService.reduceWarhouseGoodsAmount(newSalesOrderVO);
                            if(Constants.ERROR_CODE.equals(salesVolumeVOExecuteResult.getCode())){
                                result.setCode(Constants.ERROR_CODE);
                                result.setResultMessage(salesVolumeVOExecuteResult.getResultMessage());
                                status.setRollbackOnly();
                            }
                        }

//                        if (Utility.isNotEmpty(newSalesOrderVO.getStoreId())) {
//                            AjaxInfo storeInfo = feignStoreService.getStoreById(newSalesOrderVO.getStoreId().longValue());
//                            if (Utility.isNotEmpty(storeInfo.getData())) {
//                                Map<String, Object> map = (Map) storeInfo.getData();
//                                Integer verify = (Integer) map.get("verify");
//                                if(Constants.VERIFY.equals(verify)){
//                                    // 扣除库存数量
//                                    newSalesOrderVO.setReturnOrderNumber(so.getReturnOrderNumber());
//                                    ExecuteResult<String> salesVolumeVOExecuteResult = newSalesOrderExportService.reduceWarhouseGoodsAmount(newSalesOrderVO);
//                                    if(Constants.ERROR_CODE.equals(salesVolumeVOExecuteResult.getCode())){
//                                        result.setCode(Constants.ERROR_CODE);
//                                        result.setResultMessage(salesVolumeVOExecuteResult.getResultMessage());
//                                        status.setRollbackOnly();
//                                    }
//                                }
//                            }
//                        }

                        //启用优惠码
                        if(Utility.isNotEmpty(newSalesOrderVO.getCouponId())) {
                            Map<String, Object> couponMap = new HashMap<>();
                            couponMap.put("tCouponId", newSalesOrderVO.getCouponId());
                            couponMap.put("isWriteOff", Constants.DELFLG_NORMAL);
                            AjaxInfo couponInfo = feignCouponService.destroy(couponMap);
                            if (AjaxInfoConstants.ERROR_CODE.equals(couponInfo.getCode())) {
                                result.setCode(Constants.ERROR_CODE);
                                result.setResultMessage("优惠码启用失败");
                                status.setRollbackOnly();
                            } else {
                                result.setCode(Constants.SUCCESS_CODE);
                                result.setResultMessage(Constants.SAVE_SUCCESS);
                                result.setResult(returnOrderNumber);
                            }
                        }else{
                            if(Utility.isNotEmpty(result.getResultMessage())){
                                if(Constants.ERROR_CODE.equals(result.getCode())){
                                    result.setCode(Constants.ERROR_CODE);
                                    result.setResultMessage("退货失败!");
                                    status.isCompleted();
                                }
                            }else{
                                result.setCode(Constants.SUCCESS_CODE);
                                result.setResultMessage(Constants.SAVE_SUCCESS);
                                result.setResult(returnOrderNumber);
                            }
                        }
                    } catch (Exception e) {
                        result.setCode(Constants.ERROR_CODE);
                        result.setResultMessage(e.toString());
                        Log.error(log, "\n 方法[{}]，异常：[{}]", "NewReturnOrderExportServiceImpl-submitReturnOrder", e.getMessage());
                        status.setRollbackOnly();
                    }
                }
            });
            try {
	            if(fmsActivityNumber.equals(newSalesOrderVO.getActivityNumber()) && Constants.SUCCESS_CODE.equals(result.getCode())) {
	            	Thread thread = new Thread(){
	        			public void run(){
			            	// FMS更新客户卡券状态
							HashMap<String, Object> param = new HashMap<>();
							// 退货订单编号
							param.put("salesOrderNumber", vo.getReturnOrderNumber());
							// 核销码
							param.put("couponCode", newReturnOrderVO.getCouponCode());
							// 核销状态(默认1-已核销)
							param.put("isWriteOff", 0);
							// 核销状态value(默认已核销)
							param.put("writeOffValue", "未核销");
							// 活动编号
							param.put("activityCode", fmsActivityNumber);
							// 数据时间
							param.put("dataDate", String.valueOf(System.currentTimeMillis()/1000));
							feignActivityService.updateCardState(param);
	        			}
	            	};
	            	thread.start();
				}
            } catch (Exception e) {
				Log.error(log, "\n 方法[{}]，调用营促销客户卡券更新状态接口异常：[{}]", "ReceiptRecordExportServiceImpl-addReceiptRecord", e.toString());
			}
        } else {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage("销售订单不存在");
        }
        return result;
    }

    // 订单退货状态赋值
    private void newSalesOrderVOCoover(NewSalesOrderVO so, NewReturnOrderVO vo) {
        so.setReturnStatusId(vo.getReturnOrderStatusId());
        so.setReturnStatusNumber(vo.getReturnOrderStatusNumber());
        so.setReturnStatusName(vo.getReturnOrderStatusName());


    }
    // 退货订单状态赋值
    private void newReturnOrderVOCover(NewReturnOrderVO newReturnOrderVO, NewReturnOrderVO vo) {
        // 退货原因
        newReturnOrderVO.setReturnReasonId(vo.getReturnReasonId());
        newReturnOrderVO.setReturnReasonNumber(vo.getReturnReasonNumber());
        newReturnOrderVO.setReturnReasonName(vo.getReturnReasonName());
        newReturnOrderVO.setReturnOrderStatusId(vo.getReturnOrderStatusId());
        newReturnOrderVO.setReturnOrderNumber(vo.getReturnOrderNumber());
        newReturnOrderVO.setReturnOrderStatusName(vo.getReturnOrderStatusName());

    }

    @Override
    public String getMaxReturnOrderNumber(String nowDate) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReturnOrderExportServiceImpl-getMaxReturnOrderNumber", nowDate);
        String result = "";
        try {
            result = newReturnOrderService.getMaxReturnOrderNumber(nowDate);
        } catch (Exception e) {
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewReturnOrderExportServiceImpl-getMaxReturnOrderNumber", e.getMessage());
            return Constants.ERROR_CODE;
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReturnOrderExportServiceImpl-getMaxReturnOrderNumber", result);
        return result;
    }

    /**
     * @param vo
     * @return
     * @Description 校验退单必须参数是否为空
     * @author xupengfei
     * @Date 2019年4月9日
     */
    public String validateReturnOrderNotEmpty(NewReturnOrderVO vo) {
        if (Utility.isEmpty(vo.getSalesOrderId()) || Utility.isEmpty(vo.getSalesOrderNumber())) {
            return "订单编号为空";
        } else if (Utility.isEmpty(vo.getReturnReasonId()) || Utility.isEmpty(vo.getReturnReasonNumber()) || Utility.isEmpty(vo.getReturnReasonName())) {
            return "退货原因为空";
        } else if (Utility.isEmpty(vo.getCreateUserId()) || Utility.isEmpty(vo.getCreateUserName())) {
            return "退货状态为空";
        }else if (Utility.isEmpty(vo.getReturnOrderStatusId()) || Utility.isEmpty(vo.getReturnOrderStatusName())|| Utility.isEmpty(vo.getReturnOrderStatusNumber())) {
            return "操作人为空";
        }else {
            return Constants.SUCCESS_CODE;
        }
    }

    /**
     * 权限信息
     *
     * @param vo
     */
    private void queryAuthorize(NewReturnOrderVO vo) {
        // 权限信息
        if (Utility.isNotEmpty(vo.getOrgStr())) {
            // 管控区域id集合
            List<Integer> listIds = Arrays.asList(vo.getOrgStr().split(",")).stream().map(e -> Integer.valueOf(e.trim())).collect(Collectors.toList());
            vo.setOrgList(listIds);
            // 区域搜索条件
            if (Utility.isNotEmpty(vo.getOrgArrStr())) {
                List<Integer> listIdsQuery = Arrays.asList(vo.getOrgArrStr().split(",")).stream().map(e -> Integer.valueOf(e.trim())).collect(Collectors.toList());
                List<Integer> intersection = listIds.stream().filter(item -> listIdsQuery.contains(item)).collect(Collectors.toList());
                vo.setOrgList(intersection);
            }
        }
        // 管控门店集合
        if (Utility.isNotEmpty(vo.getStoreStr())) {
            List<Integer> listIds = Arrays.asList(vo.getStoreStr().split(",")).stream().map(e -> Integer.valueOf(e.trim())).collect(Collectors.toList());
            vo.setStoreList(listIds);
        }
    }



    @Override
    public ExecuteResult<List<StatisticsSalesVO>> queryStatsReturnOrderPage(NewReturnOrderVO newSalesOrderParamVO) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryStatsSalesOrderPage", JSONObject.toJSONString(newSalesOrderParamVO));
        ExecuteResult<List<StatisticsSalesVO>> result = new ExecuteResult<>();
        try {
            result = newReturnOrderService.queryStatsReturnOrderPage(newSalesOrderParamVO);

        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryStatsSalesOrderPage", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryStatsSalesOrderPage", JSONObject.toJSONString(result));
        return result;
    }


    @Override
    public ExecuteResult<List<StatisticsSourceVO>> queryStatsReturnByReason(NewReturnOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryListCustomerSource", JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        try {
                result = newReturnOrderService.queryStatsReturnByReason(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryListCustomerSource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryListCustomerSource", JSONObject.toJSONString(result));
        return result;
    }

	@Override
	public ExecuteResult<SalesVolumeVO> queryCountAmount(NewReturnOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryCountAmount", JSONObject.toJSONString(vo));
        ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
        try {
                result = newReturnOrderService.queryCountAmount(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryCountAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryCountAmount", JSONObject.toJSONString(result));
        return result;
	}

}

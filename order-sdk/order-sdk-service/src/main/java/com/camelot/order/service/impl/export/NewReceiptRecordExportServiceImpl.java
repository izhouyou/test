package com.camelot.order.service.impl.export;


import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.domain.NewReceiptRecordDomain;
import com.camelot.order.export.service.NewReceiptRecordExportService;
import com.camelot.order.export.service.NewSalesOrderExportService;
import com.camelot.order.export.service.NewSalesOrderGoodsExportService;
import com.camelot.order.export.service.NewSalesOrderLogExportService;
import com.camelot.order.export.snapshot.NewReceiptRecordVO;
import com.camelot.order.export.snapshot.NewSalesOrderLogVO;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
import com.camelot.order.feign.FeignActivityService;
import com.camelot.order.feign.FeignStoreService;
import com.camelot.order.feign.FeignWarhouseGoodsService;
import com.camelot.order.service.NewReceiptRecordService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author hudya
 */
@Service("newReceiptRecordExportService")
public class NewReceiptRecordExportServiceImpl extends BaseServiceImpl<NewReceiptRecordVO, NewReceiptRecordDomain> implements NewReceiptRecordExportService {

    private static Logger log = Log.get(NewSalesOrderExportServiceImpl.class);
    @Value("${fms.activity.number}")
    private String fmsActivityNumber;

    /**
     * 收款service
     */
    @Autowired
    private NewReceiptRecordService newReceiptRecordService;
    /**
     * 订单service
     */
    @Autowired
    private NewSalesOrderExportService newSalesOrderExportService;
    /**
     * 事务
     */
    @Autowired
    TransactionTemplate transactionTemplate;
    /**
     * 编号series
     */
    @Autowired
    private NewOrderNumberExportService newOrderNumberExportService;
    /**
     * 编号series
     */
    @Autowired
    private NewSalesOrderLogExportService newSalesOrderLogExportService;
    /**
     * 门店series
     */
    @Autowired
    private FeignStoreService feignStoreService;
    /**
     * 库存series
     */
    @Autowired
    private FeignWarhouseGoodsService feignWarhouseGoodsService;
    /**
     * 销售订单商品series
     */
    @Autowired
    private NewSalesOrderGoodsExportService newSalesOrderGoodsExportService;
    /** 营促销服务 */
    @Autowired
    private FeignActivityService feignActivityService;

    @Override
    public ExecuteResult<PageInfo<NewReceiptRecordVO>> queryReceiptRecordList(NewReceiptRecordVO vo, Page<NewReceiptRecordVO> page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReceiptRecordExportServiceImpl-queryReceiptRecordList", JSONObject.toJSONString(vo), JSONObject.toJSONString(page));
        ExecuteResult<PageInfo<NewReceiptRecordVO>> result = new ExecuteResult<>();
        try {
            queryAuthorize(vo);
            ExecuteResult<PageInfo<NewReceiptRecordVO>> executeResult = newReceiptRecordService.queryListByPage(vo, page);
            if (Utility.isNotEmpty(executeResult.getResult())) {
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
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewReceiptRecordExportServiceImpl-queryReceiptRecordList", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReceiptRecordExportServiceImpl-queryReceiptRecordList", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<NewReceiptRecordVO> addReceiptRecord(NewReceiptRecordVO vo){
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReceiptRecordExportServiceImpl-addReceiptRecord", JSONObject.toJSONString(vo));
        ExecuteResult<NewReceiptRecordVO> result = new ExecuteResult<>();
        // status
        vo.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
        vo.setSalesOrderStatusName(OrderConstants.ORDER_STATUS_FINISH_NAME);
        //pay
        vo.setPaymentStatusId(OrderConstants.DEFAULT_RECEIPT_STATUS);
        vo.setPaymentStatusName(OrderConstants.DEFAULT_RECEIPT_STATUS_NAME);

        // 非空校验
        String validateResult = validateNotEmpty(vo);
        if (!Constants.SUCCESS_CODE.equals(validateResult)) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(validateResult);
            Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReceiptRecordExportServiceImpl-addReceiptRecord", result);
            return result;
        }
        ExecuteResult<NewSalesOrderVO> orderResult = newSalesOrderExportService.queryById(vo.getSalesOrderId());
        if (Utility.isEmpty(orderResult.getResult())) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage("订单有误");
            Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReceiptRecordExportServiceImpl-addReceiptRecord", result);
            return result;
        }
        NewReceiptRecordVO newReceiptRecordQuery = new NewReceiptRecordVO();
        newReceiptRecordQuery.setSalesOrderId(vo.getSalesOrderId());
        ExecuteResult<List<NewReceiptRecordVO>> newReceiptRecordResult = newReceiptRecordService.queryList(newReceiptRecordQuery);
        if (Utility.isNotEmpty(newReceiptRecordResult.getResult())) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage("该订单已收款完成");
            result.setResult(newReceiptRecordResult.getResult().get(0));
            Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReceiptRecordExportServiceImpl-addReceiptRecord", result);
            return result;
        }
        // 订单信息
        NewSalesOrderVO newSalesOrderVO = orderResult.getResult();
        ExecuteResult<String> genidResult = newOrderNumberExportService.getGenid();
        if(Utility.isEmpty(genidResult.getResult())){
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.SAVE_FAILURE);
            return result;
        }
        String number = genidResult.getResult();
        vo.setReceiptRecordId(Long.valueOf(number));
        // copy资源属性 - 收款记录
        NewReceiptRecordVO newReceiptRecordVO = BeanUtil.copyPropertes(NewReceiptRecordVO.class, newSalesOrderVO);
        // 订单数据赋值到收款记录数据
        newSaleOrderCoverTOReceiptRecord(newSalesOrderVO, newReceiptRecordVO,vo);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    Date nowDate = new Date();
                    newReceiptRecordVO.setCreateDate(nowDate);
                    newReceiptRecordVO.setModifyDate(nowDate);
                    ExecuteResult<NewReceiptRecordVO> executeResult = newReceiptRecordService.add(newReceiptRecordVO);
                    if (Utility.isEmpty(executeResult.getResult())) {
                        result.setCode(Constants.ERROR_CODE);
                        result.setResultMessage("收款记录添加失败");
                        status.setRollbackOnly();
                    }

                    // 修改订单状态
                    newSalesOrderVO.setSalesOrderId(vo.getSalesOrderId());
                    newSalesOrderVO.setModifyDate(nowDate);//修改时间
                    newSalesOrderVO.setUpdateUserId(vo.getCreateUserId());//修改人
                    newSalesOrderVO.setUpdateUserName(vo.getUpdateUserName());
                    // 设置状态为已完成
                    newSalesOrderVO.setSalesOrderStatusId(vo.getSalesOrderStatusId());
                    newSalesOrderVO.setSalesOrderStatusNumber(vo.getSalesOrderStatusNumber());
                    newSalesOrderVO.setSalesOrderStatusName(vo.getSalesOrderStatusName());
                    // 设置收款状态已收款
                    newSalesOrderVO.setPaymentStatusId(vo.getPaymentStatusId());
                    newSalesOrderVO.setPaymentStatusNumber(vo.getPaymentStatusNumber());
                    newSalesOrderVO.setPaymentStatusName(vo.getPaymentStatusName());
                    // 设置时间
                    newSalesOrderVO.setModifyDate(new Date());
                    ExecuteResult<NewSalesOrderVO> updateNewSalesOrder = newSalesOrderExportService.update(newSalesOrderVO);
                    if (Constants.ERROR_CODE.equals(updateNewSalesOrder.getCode())) {
                        result.setCode(Constants.ERROR_CODE);
                        result.setResultMessage(updateNewSalesOrder.getResultMessage());
                        status.setRollbackOnly();
                    }
                    // 添加日志
                    NewSalesOrderVO newOrder = updateNewSalesOrder.getResult();
                    NewSalesOrderLogVO newSalesOrderLogVO = BeanUtil.copyPropertes(NewSalesOrderLogVO.class, newOrder);
                    newSalesOrderLogVO.setOperatorId(vo.getCreateUserId());
                    newSalesOrderLogVO.setOperatorName(vo.getCreateUserName());
                    newSalesOrderLogVO.setOperatorNumber(vo.getOperatorNumber());
                    ExecuteResult<NewSalesOrderLogVO> addSalesOrderLog = newSalesOrderLogExportService.addSalesOrderLog(newSalesOrderLogVO);
                    if(Utility.isEmpty(addSalesOrderLog.getResult())){
                        result.setCode(Constants.ERROR_CODE);
                        result.setResultMessage("添加日志失败");
                        status.setRollbackOnly();
                    }

//                    if(Constants.VERIFY.equals(vo.getVerify())){
//                        // 扣除库存数量
//                        newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_SUBMISSION);
//                        ExecuteResult<String> salesVolumeVOExecuteResult = newSalesOrderExportService.reduceWarhouseGoodsAmount(newSalesOrderVO);
//                        if(Constants.ERROR_CODE.equals(salesVolumeVOExecuteResult.getCode())){
//                            result.setCode(Constants.ERROR_CODE);
//                            result.setResultMessage(salesVolumeVOExecuteResult.getResultMessage());
//                            status.setRollbackOnly();
//                        }
//                    }

                    if (Utility.isNotEmpty(newSalesOrderVO.getStoreId())) {
                        AjaxInfo storeInfo = feignStoreService.getStoreById(newSalesOrderVO.getStoreId().longValue());
                        if (Utility.isNotEmpty(storeInfo.getData())) {
                            Map<String, Object> map = (Map) storeInfo.getData();
                            Integer verify = (Integer) map.get("verify");
                            if(Constants.VERIFY.equals(verify)){
                                // 扣除库存数量
                                newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_SUBMISSION);
                                ExecuteResult<String> salesVolumeVOExecuteResult = newSalesOrderExportService.reduceWarhouseGoodsAmount(newSalesOrderVO);
                                if(Constants.ERROR_CODE.equals(salesVolumeVOExecuteResult.getCode())){
                                    result.setCode(Constants.ERROR_CODE);
                                    result.setResultMessage(salesVolumeVOExecuteResult.getResultMessage());
                                    status.setRollbackOnly();
                                }
                            }
                        }
                    }

                    result.setCode(Constants.SUCCESS_CODE);
                    result.setResultMessage(Constants.SAVE_SUCCESS);
                    result.setResult(vo);
                } catch (Exception e) {
                    result.setCode(Constants.ERROR_CODE);
                    result.setResultMessage(e.toString());
                    Log.error(log, "\n 方法[{}]，异常：[{}]", "ReceiptRecordExportServiceImpl-addReceiptRecord", e.getMessage());
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
						// 订单编号
						param.put("salesOrderNumber", newSalesOrderVO.getSalesOrderNumber());
						// 核销码
						param.put("couponCode", newSalesOrderVO.getCouponCode());
						// 核销状态(默认1-已核销)
						param.put("isWriteOff", 1);
						// 核销状态value(默认已核销)
						param.put("writeOffValue", "已核销");
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
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReceiptRecordExportServiceImpl-addReceiptRecord", JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 订单数据赋值到收款记录数据
     *
     * @param newSalesOrderVO
     * @param newReceiptRecordVO
     */
    private void newSaleOrderCoverTOReceiptRecord(NewSalesOrderVO newSalesOrderVO, NewReceiptRecordVO newReceiptRecordVO,NewReceiptRecordVO vo) {
        // 获取收款单编号
        String receiptRecordNumber = newOrderNumberExportService.getOrderNumber(OrderConstants.ORDER_TYPE_RECEIPT, newSalesOrderVO.getStoreNumber());
        newReceiptRecordVO.setReceiptRecordNumber(receiptRecordNumber);
        newReceiptRecordVO.setReceiptRecordId(vo.getReceiptRecordId());
        newReceiptRecordVO.setReceiptAmount(newSalesOrderVO.getSalesOrderAmount());
        newReceiptRecordVO.setPaymentStatusId(vo.getPaymentStatusId());
        newReceiptRecordVO.setPaymentStatusNumber(vo.getPaymentStatusNumber());
        newReceiptRecordVO.setPaymentStatusName(vo.getPaymentStatusName());
        newReceiptRecordVO.setSalesOrderResourceId(newSalesOrderVO.getSalesOrderSourceId());
        newReceiptRecordVO.setSalesOrderResourceNumber(newSalesOrderVO.getSalesOrderSourceNumber());
        newReceiptRecordVO.setSalesOrderResourceName(newSalesOrderVO.getSalesOrderSourceName());
        newReceiptRecordVO.setCreateUserId(vo.getCreateUserId());
        newReceiptRecordVO.setCreateUserName(vo.getCreateUserName());
        newReceiptRecordVO.setUpdateUserId(vo.getUpdateUserId());
        newReceiptRecordVO.setUpdateUserName(vo.getUpdateUserName());
    }

    @Override
    public ExecuteResult<List<NewReceiptRecordVO>> queryReceiptList(NewReceiptRecordVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReceiptRecordExportServiceImpl-queryReceiptList", JSONObject.toJSONString(vo));
        ExecuteResult<List<NewReceiptRecordVO>> result = new ExecuteResult<>();
        try {
            ExecuteResult<List<NewReceiptRecordVO>> executeResult = newReceiptRecordService.queryList(vo);
            if (Utility.isNotEmpty(executeResult.getResult())) {
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
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewReceiptRecordExportServiceImpl-queryReceiptList", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReceiptRecordExportServiceImpl-queryReceiptList", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public String getMaxReceiptRecordNumber(String nowDate) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReceiptRecordExportServiceImpl-getMaxReceiptRecordNumber", nowDate);
        String result = "";
        try {
            result = newReceiptRecordService.getMaxReceiptRecordNumber(nowDate);
        } catch (Exception e) {
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewReceiptRecordExportServiceImpl-getMaxReceiptRecordNumber", e.getMessage());
            return Constants.ERROR_CODE;
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReceiptRecordExportServiceImpl-getMaxReceiptRecordNumber", result);
        return result;
    }

    /**
     * @param vo
     * @return
     * @Description 必需参数非空判断
     * @author xupengfei
     * @Date 2019年4月10日
     */
    public String validateNotEmpty(NewReceiptRecordVO vo) {
        if (Utility.isEmpty(vo.getSalesOrderNumber()) || Utility.isEmpty(vo.getSalesOrderId())) {
            return "订单号为空";
        } else if (Utility.isEmpty(vo.getCreateUserId()) || Utility.isEmpty(vo.getCreateUserName())) {
            return "创建人为空";
        }else if(Utility.isEmpty(vo.getPaymentStatusId())||Utility.isEmpty(vo.getPaymentStatusNumber())||Utility.isEmpty(vo.getPaymentStatusName())){
            return "支付状态为空,请稍后重试";
        }else if(Utility.isEmpty(vo.getSalesOrderStatusId())||Utility.isEmpty(vo.getSalesOrderStatusNumber())||Utility.isEmpty(vo.getSalesOrderStatusName())){
            return "订单状态为空,请稍后重试";
        }else{
            return Constants.SUCCESS_CODE;
        }
    }

    /**
     * 权限信息
     *
     * @param vo
     */
    private void queryAuthorize(NewReceiptRecordVO vo) {
        // 权限信息
        if (Utility.isNotEmpty(vo.getOrgStr())) {
            // 管控区域id集合
            List<Integer> listIds = Arrays.asList(vo.getOrgStr().split(",")).stream().map(e -> Integer.valueOf(e.trim())).collect(Collectors.toList());
            vo.setOrgList(listIds);
        }
        // 管控门店集合
        if (Utility.isNotEmpty(vo.getStoreStr())) {
            List<Integer> listIds = Arrays.asList(vo.getStoreStr().split(",")).stream().map(e -> Integer.valueOf(e.trim())).collect(Collectors.toList());
            vo.setStoreList(listIds);
        }
    }

}
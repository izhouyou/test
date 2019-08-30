package com.camelot.order.service.impl.export;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.FeginConstants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.AjaxInfoConstants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.domain.NewSalesOrderDomain;
import com.camelot.order.export.CustomerExportService;
import com.camelot.order.export.service.NewReturnOrderExportService;
import com.camelot.order.export.service.NewSalesOrderExportService;
import com.camelot.order.export.service.NewSalesOrderGoodsExportService;
import com.camelot.order.export.service.NewSalesOrderLogExportService;
import com.camelot.order.export.snapshot.*;
import com.camelot.order.export.vo.*;
import com.camelot.order.feign.*;
import com.camelot.order.feign.vo.InboundDetailVO;
import com.camelot.order.feign.vo.OutboundDetailVO;
import com.camelot.order.feign.vo.WarehouseVO;
import com.camelot.order.service.NewReturnOrderService;
import com.camelot.order.service.NewSalesOrderService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author hudya
 */
@Service("newSalesOrderExportService")
public class NewSalesOrderExportServiceImpl extends BaseServiceImpl<NewSalesOrderVO, NewSalesOrderDomain> implements NewSalesOrderExportService {

    private static Logger log = Log.get(NewSalesOrderExportServiceImpl.class);

    /**
     * 销售订单service
     */
    @Autowired
    private NewSalesOrderService newSalesOrderService;

    /**
     * 退货订单service
     */
    @Autowired
    private NewReturnOrderService newReturnOrderService;
    /**
     * 销售订单商品service
     */
    @Autowired
    private NewSalesOrderGoodsExportService newSalesOrderGoodsExportService;
    /**
     * 日志service
     */
    @Autowired
    private NewSalesOrderLogExportService newSalesOrderLogExportService;
    /**
     * 消费者service
     */
    @Autowired
    private CustomerExportService customerExportService;
    /**
     * 事务
     */
    @Autowired
    TransactionTemplate transactionTemplate;
    /**
     * 商品价格
     */
    @Autowired
    private FeignGoodsPriceService feignGoodsPriceService;
    /**
     * 优惠吗service
     */
    @Autowired
    private FeignCouponService feignCouponService;
    /**
     * 编号service
     */
    @Autowired
    private NewOrderNumberExportService newOrderNumberExportService;
    /**
     * 编号service
     */
    @Autowired
    private FeignActivityCouponService feignActivityCouponService;
    /**
     * 退单service
     */
    @Autowired
    private NewReturnOrderExportService newReturnOrderExportService;
    /**
     * 门店信息
     */
    @Autowired
    private FeignStoreService feignStoreService;
    /**
     * 库存信息
     */
    @Autowired
    private FeignWarhouseGoodsService feignWarhouseGoodsService;
    /**
     * 出入库
     */
    @Autowired
    private FeignBoundService feignBoundService;
    /**
     * 仓库信息
     */
    @Autowired
    private FeignWarhouseService feignWarhouseService;


    @Override
    public ExecuteResult<PageInfo<NewSalesOrderVO>> querySalesOrderList(NewSalesOrderVO vo, Page<NewSalesOrderVO> page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-querySalesOrderList", JSONObject.toJSONString(vo), JSONObject.toJSONString(page));
        ExecuteResult<PageInfo<NewSalesOrderVO>> result = new ExecuteResult<>();
        try {
            queryAuthorize(vo);
            if (Utility.isNotEmpty(vo.getGoodsSn()) || Utility.isNotEmpty(vo.getGoodsFrameNumber()) || Utility.isNotEmpty(vo.getGoodsBarcode())) {
                NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
                newSalesOrderGoodsVO.setGoodsSn(vo.getGoodsSn());
                newSalesOrderGoodsVO.setGoodsFrameNumber(vo.getGoodsFrameNumber());
                newSalesOrderGoodsVO.setGoodsBarcode(vo.getGoodsBarcode());
                ExecuteResult<List<NewSalesOrderGoodsVO>> orderGoodsResult = newSalesOrderGoodsExportService.queryOrderGoods(newSalesOrderGoodsVO);
                if (Utility.isNotEmpty(orderGoodsResult.getResult())) {
                    List<NewSalesOrderGoodsVO> newSalesOrderGoodsVOList = orderGoodsResult.getResult();
                    // 销售订单id
                    List<Long> fieldList = newSalesOrderGoodsVOList.stream().map(e -> e.getSalesOrderId()).collect(Collectors.toList());
                    vo.setSalesOrderIdList(fieldList);
                }else{
                    result.setCode(Constants.EMPTY_CODE);
                    result.setResultMessage(Constants.QUERY_SUCCESS);
                    return result;
                }
            }
            vo.setDelFlg(Constants.DELFLG_NORMAL);
            ExecuteResult<PageInfo<NewSalesOrderVO>> executeResult = newSalesOrderService.queryListByPage(vo, page);
            if (Utility.isNotEmpty(executeResult.getResult())) {
                List<NewSalesOrderVO> list = executeResult.getResult().getList();
                List<Long> fieldList = list.stream().map(e -> e.getSalesOrderId()).collect(Collectors.toList());

                NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
                newSalesOrderGoodsVO.setSalesOrderIdList(fieldList);
                ExecuteResult<List<NewSalesOrderGoodsVO>> orderGoodsResult = newSalesOrderGoodsExportService.queryOrderGoods(newSalesOrderGoodsVO);
                if (Utility.isNotEmpty(orderGoodsResult.getResult())) {
                    List<NewSalesOrderGoodsVO> newSalesOrderGoodsVOList = orderGoodsResult.getResult();
                    for (NewSalesOrderVO newSalesOrderVO : list) {
                        ArrayList<NewSalesOrderGoodsVO> newSalesOrderGoodsVOS = new ArrayList<>();
                        for (NewSalesOrderGoodsVO orderGoodsVO : newSalesOrderGoodsVOList) {
                            if (newSalesOrderVO.getSalesOrderId().equals(orderGoodsVO.getSalesOrderId())) {
                                newSalesOrderGoodsVOS.add(orderGoodsVO);
                            }
                        }
                        newSalesOrderVO.setGoodsList(newSalesOrderGoodsVOS);
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
    public ExecuteResult<NewSalesOrderVO> searchSalesOrderDetail(Long id) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-searchSalesOrderDetail", JSONObject.toJSONString(id));
        ExecuteResult<NewSalesOrderVO> result = new ExecuteResult<>();
        try {
            if (Utility.isEmpty(id)) {
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage(Constants.NET_DELAY);
                return result;
            }
            ExecuteResult<NewSalesOrderVO> executeResult = newSalesOrderService.queryById(id);
            if (Utility.isNotEmpty(executeResult.getResult())) {
                //订单信息
                NewSalesOrderVO newSalesOrderVO = executeResult.getResult();
                // 查询该订单商品信息
                NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
                newSalesOrderGoodsVO.setSalesOrderId(id);
                ExecuteResult<List<NewSalesOrderGoodsVO>> orderGoodsResult = newSalesOrderGoodsExportService.queryOrderGoods(newSalesOrderGoodsVO);
                if (Utility.isNotEmpty(orderGoodsResult.getResult())) {
                    List<NewSalesOrderGoodsVO> newSalesOrderGoodsVOList = orderGoodsResult.getResult();
                    // 金额保留两位小数
                    DecimalFormat format = new DecimalFormat("0.00");
                    for (NewSalesOrderGoodsVO orderGoodsVO : newSalesOrderGoodsVOList) {
                        // 指导零售价
                        BigDecimal retailPrice = orderGoodsVO.getRetailPrice();
                        // 实销单价
                        BigDecimal actualPrice = orderGoodsVO.getActualPrice();
                        // 小计:零售单价*数量
                        if (Utility.isNotEmpty(orderGoodsVO.getOrderAmount())) {
                            BigDecimal amount = new BigDecimal(orderGoodsVO.getOrderAmount());
                            // 实付金额:实销单价*数量
                            BigDecimal actualPriceSubtotal = actualPrice.multiply(amount);
                            orderGoodsVO.setActualPriceSubtotal(format.format(actualPriceSubtotal));
                            if (Utility.isNotEmpty(retailPrice)) {
                                BigDecimal retailPriceSubtotal = retailPrice.multiply(amount);
                                orderGoodsVO.setRetailPriceSubtotal(format.format(retailPriceSubtotal));
                                // 零售差异
                                BigDecimal retailDifference = retailPriceSubtotal.subtract(actualPriceSubtotal);
                                orderGoodsVO.setRetailDifference(format.format(retailDifference));
                            }
                        }
                    }
                    newSalesOrderVO.setGoodsList(newSalesOrderGoodsVOList);
                }
                // 订单小日志
                NewSalesOrderLogVO newSalesOrderLogVO = new NewSalesOrderLogVO();
                newSalesOrderLogVO.setSalesOrderId(id);
                ExecuteResult<List<NewSalesOrderLogVO>> saleOrderLogResult = newSalesOrderLogExportService.queryList(newSalesOrderLogVO);
                if(Utility.isNotEmpty(saleOrderLogResult.getResult())){
                    List<NewSalesOrderLogVO> newSalesOrderLogVOList = saleOrderLogResult.getResult().stream().sorted(Comparator.comparing(NewSalesOrderLogVO::getOperationTime).reversed()).collect(toList());//依id降序排序
                    newSalesOrderVO.setNewSalesOrderLogVOS(newSalesOrderLogVOList);
                }

                result.setResult(newSalesOrderVO);
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
    public ExecuteResult<NewSalesOrderVO> addSalesOrder(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-addSalesOrder", JSONObject.toJSONString(vo));
        ExecuteResult<NewSalesOrderVO> result = new ExecuteResult<>();

        // 判断高伟优惠吗是否核销  暂时不用
//        if (Utility.isNotEmpty(vo.getCouponCode())) {
//            // 判断高伟的活动码是否已经核销
//            AjaxInfo couponInfo = feignActivityCouponService.verifyCode(String.valueOf(vo.getCouponCode()));
//            Map<String, Object> couponMap = (Map<String, Object>) couponInfo.getData();
//            if (couponMap.containsKey("status")) {
//                Integer status = (Integer) couponMap.get("status");
//                // 本次活动的优惠码数据
//                if (status.equals(Constants.VERIFY_CODE_200) || status.equals(Constants.VERIFY_CODE_201)) { //不存在
//                    // 放行
//                } else if (status.equals(Constants.VERIFY_CODE_202) || status.equals(Constants.VERIFY_CODE_203) || status.equals(Constants.VERIFY_CODE_204) || status.equals(Constants.VERIFY_CODE_205) || status.equals(Constants.VERIFY_CODE_206) || status.equals(Constants.VERIFY_CODE_500)) {
//                    result.setCode(Constants.ERROR_CODE);
//                    result.setResultMessage(couponMap.get("message").toString());
//                    Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-submitSalesOrder", result);
//                    return result;
//                }
//            }
//        }

        // 下单判空校验
        String validateResult = validateSalesOrderNotEmpty(vo);
        if (!Constants.SUCCESS_CODE.equals(validateResult)) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(validateResult);
            Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-submitSalesOrder", result);
            return result;
        }
        // 销售订单重复性校验
        NewSalesOrderVO repeatVO = new NewSalesOrderVO();
        repeatVO.setTransactionId(vo.getTransactionId());
        ExecuteResult<List<NewSalesOrderVO>> repeatResult = newSalesOrderService.queryList(repeatVO);
        if (Utility.isNotEmpty(repeatResult.getResult())) {
            NewSalesOrderVO repeatso = repeatResult.getResult().get(0);
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage("该订单" + repeatso.getSalesOrderStatusName());
            Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-submitSalesOrder", result);
            return result;
        }
        // 判断有序商品是否已经下单
        if (Utility.isNotEmpty(vo.getGoodsList())) {
            // 查询订单SN对应的商品信息
            List<NewSalesOrderGoodsVO> goodsList = vo.getGoodsList();
            for (NewSalesOrderGoodsVO ng : goodsList) {
                if (Utility.isNotEmpty(ng.getGoodsSn()) && Utility.isNotEmpty(ng.getGoodsFrameNumber())) {
                    NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
                    newSalesOrderGoodsVO.setGoodsSn(ng.getGoodsSn());
                    newSalesOrderGoodsVO.setGoodsFrameNumber(ng.getGoodsFrameNumber());
                    ExecuteResult<List<NewSalesOrderGoodsVO>> goodsRepeatResult = newSalesOrderGoodsExportService.queryList(newSalesOrderGoodsVO);
                    if (Utility.isNotEmpty(goodsRepeatResult.getResult())) {
                        // 查询该商品信息的订单
                        List<NewSalesOrderGoodsVO> goodsRepeaList = goodsRepeatResult.getResult();
                        List<Long> orderIdList = goodsRepeaList.stream().map(e -> e.getSalesOrderId()).collect(Collectors.toList());
                        NewSalesOrderVO orderRepeatQuery = new NewSalesOrderVO();
                        orderRepeatQuery.setSalesOrderIdList(orderIdList);
                        ExecuteResult<List<NewSalesOrderVO>> orderRepeatQueryResult = newSalesOrderService.queryList(orderRepeatQuery);
                        if (Utility.isNotEmpty(orderRepeatQueryResult.getResult())) {
                            List<NewSalesOrderVO> orderRepeatQueryList = orderRepeatQueryResult.getResult();
                            for (NewSalesOrderVO salesOrderVO : orderRepeatQueryList) {
                                Integer salesOrderStatusId = salesOrderVO.getSalesOrderStatusId();
                                Integer paymentStatusId = salesOrderVO.getPaymentStatusId();
                                Integer returnStatusId = salesOrderVO.getReturnStatusId();

                                // 已提交 未支付 未退货 不能再被下单
                                if (OrderConstants.ORDER_STATUS_SUBMISSION.equals(salesOrderStatusId) && OrderConstants.ORDER_DICT_RUTURNNO.equals(returnStatusId)) {
                                    result.setCode(Constants.ERROR_CODE);
                                    result.setResultMessage("该商品已提交订单，请勿重复提交");
                                    return result;
                                } else if (OrderConstants.ORDER_STATUS_FINISH.equals(salesOrderStatusId) && OrderConstants.ORDER_DICT_RUTURNNO.equals(returnStatusId)) {
                                    result.setCode(Constants.ERROR_CODE);
                                    result.setResultMessage(goodsRepeaList.get(0).getGoodsSn() + "该商品已上报");
                                    return result;
                                }
                            }
                        }
                    }
                }
            }
        }
        // 获取销售订单id
        ExecuteResult<String> genid = newOrderNumberExportService.getGenid();
        if (Utility.isEmpty(genid.getResult())) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage("网络延迟,请稍后重试!");
            Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-submitSalesOrder", result);
            return result;
        }

        // 销售订单id获取
        String salesOrderId = genid.getResult();
        vo.setSalesOrderId(Long.valueOf(salesOrderId));
        // 获取销售订单编号
        String newOrderNumber = newOrderNumberExportService.getOrderNumber(OrderConstants.ORDER_TYPE_SALES, vo.getStoreNumber());
        vo.setSalesOrderNumber(newOrderNumber);
        // 事务控制
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    // 数据转换
                    voCoverData();
                    // 保存消费者信息
                    CustomerVO customerVO = BeanUtil.copyPropertes(CustomerVO.class, vo);
                    ExecuteResult<CustomerVO> customerAddResult = customerExportService.addCustomer(customerVO);
                    if (Utility.isEmpty(customerAddResult.getResult())) {
                        result.setCode(Constants.ERROR_CODE);
                        result.setResultMessage(Constants.SAVE_FAILURE);
                        // 事务回滚
                        status.setRollbackOnly();
                    }
                    CustomerVO queryCustomer = customerAddResult.getResult();
                    vo.setCustomerId(queryCustomer.getCustomerId());

                    //保存商品订单表信息
                    List<NewSalesOrderGoodsVO> goodsList = vo.getGoodsList();
                    // 整车商品数据 ---备注:心酸啊,还是分类虑重吧,避免数据平台商品数据名称一样造成虑重后数据丢失
                    List<NewSalesOrderGoodsVO> carList = goodsList.stream().filter(item -> !"".equals(item.getGoodsSn()) && !"".equals(item.getGoodsFrameNumber())).collect(Collectors.toList());
                    List<NewSalesOrderGoodsVO> carUniqueList = new ArrayList<>();
                    if (Utility.isNotEmpty(carList)) {
                        // 整车虑重
                        carUniqueList = carList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(item -> item.getGoodsSn() + ";" + item.getGoodsFrameNumber()))), ArrayList::new));
                    }
                    // 周边数据根据69吗虑重,前端下单同一个69吗只允许数量添加,不允许有多个记录
                    List<NewSalesOrderGoodsVO> barCodeList = goodsList.stream().filter(item -> !"".equals(item.getGoodsBarcode())).collect(Collectors.toList());
                    List<NewSalesOrderGoodsVO> barUniqueList = new ArrayList<>();
                    if (Utility.isNotEmpty(barCodeList)) {
                        // 周边商品虑重
                        barUniqueList = barCodeList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(item -> item.getGoodsBarcode()))), ArrayList::new));
                    }
                    // 牛油保商品数据 添加一个transactionId作为分组依据,同时保证69吗为"",不允许为null
                    List<NewSalesOrderGoodsVO> baoList = goodsList.stream().filter(item -> null != item.getTransactionId()).collect(Collectors.toList());
                    List<NewSalesOrderGoodsVO> baoUniqueList = new ArrayList<>();
                    if (Utility.isNotEmpty(baoList)) {
                        // 牛油保虑重
                        baoUniqueList = baoList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(item -> item.getTransactionId()))), ArrayList::new));
                    }
                    // 集合数据取并集
                    List<NewSalesOrderGoodsVO> listAllObject = carUniqueList.parallelStream().collect(toList());
                    List<NewSalesOrderGoodsVO> listAllObject2 = barUniqueList.parallelStream().collect(toList());
                    List<NewSalesOrderGoodsVO> listAllObject3 = baoUniqueList.parallelStream().collect(toList());
                    listAllObject.addAll(listAllObject2);
                    listAllObject.addAll(listAllObject3);

                    // 保存订单信息
                    BigDecimal saleOrderAmount = listAllObject.stream().map(NewSalesOrderGoodsVO::getActualPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                    vo.setSalesOrderAmount(saleOrderAmount);
                    // status
                    vo.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_SUBMISSION);
                    vo.setSalesOrderStatusName(OrderConstants.ORDER_STATUS_SUBMISSION_NAME);
                    //pay
                    vo.setPaymentStatusId(OrderConstants.DEFAULT_RECEIPT_NOPAY);
                    vo.setPaymentStatusName(OrderConstants.DEFAULT_RECEIPT_NOPAY_NAME);
                    //return
                    vo.setReturnStatusId(OrderConstants.ORDER_DICT_RUTURNNO);
                    vo.setReturnStatusName(OrderConstants.ORDER_DICT_RUTURNNO_NAME);
                    ExecuteResult<NewSalesOrderVO> salesOrderVOExecuteResult = newSalesOrderService.add(vo);
                    if (Utility.isEmpty(salesOrderVOExecuteResult.getResult())) {
                        result.setCode(Constants.ERROR_CODE);
                        result.setResultMessage(Constants.SAVE_FAILURE);
                        // 事务回滚
                        status.setRollbackOnly();
                    }

                    for (NewSalesOrderGoodsVO newSalesOrderGoodsVO : listAllObject) {
                        // 判断销售指导价是否为空
                        if (Utility.isEmpty(newSalesOrderGoodsVO.getRetailPrice())) {
                            newSalesOrderGoodsVO.setRetailPrice(new BigDecimal(0));
                        }
                        if (Utility.isEmpty(newSalesOrderGoodsVO.getActualPrice())) {
                            newSalesOrderGoodsVO.setActualPrice(new BigDecimal(0));
                        }
                        newSalesOrderGoodsVO.setSalesOrderId(Long.valueOf(salesOrderId));
                        ExecuteResult<NewSalesOrderGoodsVO> addOrderGoods = newSalesOrderGoodsExportService.addOrderGoods(newSalesOrderGoodsVO);
                        if (Utility.isEmpty(addOrderGoods.getResult())) {
                            result.setCode(Constants.ERROR_CODE);
                            result.setResultMessage(Constants.SAVE_FAILURE);
                            // 事务回滚
                            status.setRollbackOnly();
                        }
                    }

                    // 注销优惠码
                    if (Utility.isNotEmpty(vo.getCouponId())) {
                        Map<String, Object> couponMap = new HashMap<String, Object>();
                        couponMap.put("tCouponId", vo.getCouponId());
                        couponMap.put("isWriteOff", Constants.DELFLG_DELETE);
                        AjaxInfo couponInfo = feignCouponService.destroy(couponMap);
                        if (AjaxInfoConstants.ERROR_CODE.equals(couponInfo.getCode())) {
                            result.setCode(Constants.ERROR_CODE);
                            result.setResultMessage("优惠码注销失败");
                            result.setResult(null);
                            // 事务回滚
                            status.setRollbackOnly();
                        }
                    }

                    // 核销高伟优惠吗 暂时不用 --- 直接核销rms活动优惠吗
//                    if (Utility.isNotEmpty(vo.getCouponCode())) {
//                        // 判断高伟的活动码是否已经核销
//                        AjaxInfo gwInfo = feignActivityCouponService.verifyCode(vo.getCouponCode());
//                        Map<String, Object> verifyCodeMap = (Map<String, Object>) gwInfo.getData();
//                        if (verifyCodeMap.containsKey("status")) {
//                            Integer statusVerify = (Integer) verifyCodeMap.get("status");
//                            // 本次活动的优惠码数据
//                            if (statusVerify.equals(Constants.VERIFY_CODE_200)) {
//                                AjaxInfo ajaxInfo = feignActivityCouponService.applyCode(vo.getCouponCode());
//                                Map<String, Object> applyCodeMap = (Map<String, Object>) gwInfo.getData();
//                                if (applyCodeMap.containsKey("status")) {
//                                    Integer statusApply = (Integer) verifyCodeMap.get("status");
//                                    // 本次活动的优惠码数据
//                                    if (!statusApply.equals(Constants.VERIFY_CODE_200)) {
//                                        result.setCode(Constants.ERROR_CODE);
//                                        result.setResultMessage("优惠吗已使用");
//                                        // 事务回滚
//                                        status.setRollbackOnly();
//                                    }
//                                }
//                            }
//                        }
//                    }

                    // 注销优惠码
                    if (Utility.isNotEmpty(vo.getCouponId())) {
                        Map<String, Object> couponMap = new HashMap<String, Object>();
                        couponMap.put("tCouponId", vo.getCouponId());
                        couponMap.put("isWriteOff", Constants.DELFLG_DELETE);
                        AjaxInfo couponInfo = feignCouponService.destroy(couponMap);
                        if (AjaxInfoConstants.ERROR_CODE.equals(couponInfo.getCode())) {
                            result.setCode(Constants.ERROR_CODE);
                            result.setResultMessage("优惠码注销失败");
                            result.setResult(null);
                            // 事务回滚
                            status.setRollbackOnly();
                        }
                    }

                    // 添加日志信息
                    NewSalesOrderLogVO newSalesOrderLogVO = BeanUtil.copyPropertes(NewSalesOrderLogVO.class, vo);
                    newSalesOrderLogVO.setSalesOrderNumber(newOrderNumber);
                    newSalesOrderLogVO.setOperatorId(vo.getCreateUserId());
                    newSalesOrderLogVO.setOperatorName(vo.getCreateUserName());
                    newSalesOrderLogVO.setOperatorNumber(vo.getOperatorNumber());
                    ExecuteResult<NewSalesOrderLogVO> addSalesOrderLog = newSalesOrderLogExportService.addSalesOrderLog(newSalesOrderLogVO);
                    if (Utility.isEmpty(addSalesOrderLog.getResult())) {
                        result.setCode(Constants.ERROR_CODE);
                        result.setResultMessage("添加日志失败");
                        result.setResult(null);
                        // 事务回滚
                        status.setRollbackOnly();
                    }
                    NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
                    newSalesOrderVO.setSalesOrderId(Long.valueOf(salesOrderId));
                    newSalesOrderVO.setSalesOrderNumber(newOrderNumber);
                    result.setResult(newSalesOrderVO);
                    result.setCode(Constants.SUCCESS_CODE);
                    result.setResultMessage(Constants.SAVE_SUCCESS);

                } catch (Exception e) {
                    result.setCode(Constants.ERROR_CODE);
                    result.setResultMessage("网络延迟,请稍后重试!");
                    Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-submitSalesOrder", e.getMessage());
                    // 事务回滚
                    status.setRollbackOnly();
                }
            }

            // 数据转换
            private void voCoverData() {
                vo.setCreateDate(new Date());
                vo.setModifyDate(new Date());
                vo.setDelFlg(Constants.DELFLG_NORMAL);
                vo.setSalesOrderVersion(OrderConstants.ORDER_DEFAULT_VERSION);
                vo.setUpdateUserId(vo.getCreateUserId());

            }
        });
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-searchSalesOrderDetail", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<NewSalesOrderVO> cancelSalesOrder(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-cancelSalesOrder", JSONObject.toJSONString(vo));
        ExecuteResult<NewSalesOrderVO> result = new ExecuteResult<>();
        try {
            // 必有参数非空校验
            if (Utility.isEmpty(vo.getSalesOrderNumber())||Utility.isEmpty(vo.getSalesOrderId())) {
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage("订单编号为空");
            } else if (Utility.isEmpty(vo.getUpdateUserId()) || Utility.isEmpty(vo.getUpdateUserName())) {
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage("操作人为空");
            } else if (Utility.isEmpty(vo.getSalesOrderStatusId()) || Utility.isEmpty(vo.getSalesOrderStatusNumber()) || Utility.isEmpty(vo.getSalesOrderStatusName())) {
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage("订单状态为空,请稍后重试");
            } else if (Utility.isEmpty(vo.getCancelReasonId()) || Utility.isEmpty(vo.getCancelReasonNumber()) || Utility.isEmpty(vo.getCancelReasonName())) {
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage("取消原因为空");
            } else {
                Long salesOrderId = vo.getSalesOrderId();
                ExecuteResult<NewSalesOrderVO> newSalesOrderVOExecuteResult = newSalesOrderService.queryById(salesOrderId);
                if (Utility.isNotEmpty(newSalesOrderVOExecuteResult.getResult())) {
                    NewSalesOrderVO newSalesOrderVO = newSalesOrderVOExecuteResult.getResult();

                    Integer salesOrderStatusId = newSalesOrderVO.getSalesOrderStatusId();//订单状态
                    Integer paymentStatusId = newSalesOrderVO.getPaymentStatusId();//支付状态

                    if (salesOrderStatusId.equals(OrderConstants.ORDER_STATUS_CANCLE) && paymentStatusId.equals(OrderConstants.DEFAULT_RECEIPT_NOPAY)) {
                        result.setCode(Constants.ERROR_CODE);
                        result.setResultMessage("该订单已取消");
                        return result;
                    }
                    // 销售订单状态
                    newSalesOrderVO.setSalesOrderStatusId(vo.getSalesOrderStatusId());//OrderConstants.ORDER_STATUS_CANCLE
                    newSalesOrderVO.setSalesOrderStatusNumber(vo.getSalesOrderStatusNumber());
                    newSalesOrderVO.setSalesOrderStatusName(vo.getSalesOrderStatusName());
                    newSalesOrderVO.setModifyDate(new Date());
                    newSalesOrderVO.setUpdateUserId(vo.getUpdateUserId());
                    newSalesOrderVO.setUpdateUserName(vo.getUpdateUserName());
                    newSalesOrderVO.setCancelReasonId(vo.getCancelReasonId());
                    newSalesOrderVO.setCancelReasonNumber(vo.getCancelReasonNumber());
                    newSalesOrderVO.setCancelReasonName(vo.getCancelReasonName());
                    newSalesOrderVO.setModifyDate(new Date());

                    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(TransactionStatus status) {
                            try {

                                // status
                                newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_CANCLE);
                                newSalesOrderVO.setSalesOrderStatusName(OrderConstants.ORDER_STATUS_CANCLE_NAME);
                                //pay
                                newSalesOrderVO.setPaymentStatusId(OrderConstants.DEFAULT_RECEIPT_NOPAY);
                                newSalesOrderVO.setPaymentStatusName(OrderConstants.DEFAULT_RECEIPT_NOPAY_NAME);
                                //return
                                newSalesOrderVO.setReturnStatusId(OrderConstants.ORDER_DICT_RUTURNNO);
                                newSalesOrderVO.setReturnStatusName(OrderConstants.ORDER_DICT_RUTURNNO_NAME);

                                // 取消订单
                                ExecuteResult<NewSalesOrderVO> executeResult = newSalesOrderService.update(newSalesOrderVO);
                                if (Utility.isEmpty(executeResult.getResult())) {
                                    result.setCode(Constants.ERROR_CODE);
                                    result.setResultMessage(Constants.UPDATE_FAILURE);
                                    status.setRollbackOnly();
                                }
                                // 添加日志
                                NewSalesOrderVO newOrder = executeResult.getResult();
                                NewSalesOrderLogVO newSalesOrderLogVO = BeanUtil.copyPropertes(NewSalesOrderLogVO.class, newOrder);
                                newSalesOrderLogVO.setOperatorId(vo.getUpdateUserId());
                                newSalesOrderLogVO.setOperatorName(vo.getUpdateUserName());
                                newSalesOrderLogVO.setOperatorNumber(vo.getOperatorNumber());
                                ExecuteResult<NewSalesOrderLogVO> addSalesOrderLog = newSalesOrderLogExportService.addSalesOrderLog(newSalesOrderLogVO);
                                if (Utility.isEmpty(addSalesOrderLog.getResult())) {
                                    result.setCode(Constants.ERROR_CODE);
                                    result.setResultMessage("退单失败");
                                    status.isRollbackOnly();
                                }

                                // 启用优惠吗
                                if (Constants.SUCCESS_CODE.equals(executeResult.getCode()) && Utility.isNotEmpty(newSalesOrderVO.getCouponId())) {
                                    if (Utility.isNotEmpty(newSalesOrderVO.getCouponId())) {
                                        Map<String, Object> couponMap = new HashMap<String, Object>();
                                        couponMap.put("tCouponId", newSalesOrderVO.getCouponId());
                                        couponMap.put("isWriteOff", Constants.DELFLG_NORMAL);
                                        AjaxInfo couponInfo = feignCouponService.destroy(couponMap);
                                        if (AjaxInfoConstants.ERROR_CODE.equals(couponInfo.getCode())) {
                                            result.setCode(Constants.ERROR_CODE);
                                            result.setResultMessage("优惠码启用失败");
                                            status.setRollbackOnly();
                                        }
                                    }
                                    result.setResult(newSalesOrderVO);
                                    result.setCode(Constants.SUCCESS_CODE);
                                    result.setResultMessage(Constants.UPDATE_SUCCESS);
                                }
                                result.setCode(Constants.SUCCESS_CODE);
                                result.setResult(vo);
                                result.setResultMessage(Constants.UPDATE_SUCCESS);
                            } catch (Exception e) {
                                result.setCode(Constants.ERROR_CODE);
                                result.setResultMessage(Constants.UPDATE_FAILURE);
                                Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-cancelSalesOrder", e.getMessage());
                                status.setRollbackOnly();
                            }
                        }
                    });
                } else {
                    result.setCode(Constants.ERROR_CODE);
                    result.setResultMessage("销售订单不存在");
                }
            }

        } catch (Exception e) {
            result.setResult(null);
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.UPDATE_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-cancelSalesOrder", e.getMessage());
        }
        result.setCode(Constants.SUCCESS_CODE);
        result.setResultMessage(Constants.UPDATE_SUCCESS);
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-cancelSalesOrder", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<OrderCustomerVO> statisticOrderWithCustomer(OrderCustomerVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-statisticOrderWithCustomer", JSONObject.toJSONString(vo));
        ExecuteResult<OrderCustomerVO> result = new ExecuteResult<>();
        try {
            ExecuteResult<OrderCustomerVO> executeResult = newSalesOrderService.statisticsOrderWithCustomer(vo);
            if (Utility.isNotEmpty(executeResult.getResult())) {
                result.setResult(executeResult.getResult());
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            } else {
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_FAILURE);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-statisticOrderWithCustomer", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-statisticOrderWithCustomer", JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 权限信息
     *
     * @param vo
     */
    private void queryAuthorize(NewSalesOrderVO vo) {
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

    public String validateSalesOrderNotEmpty(NewSalesOrderVO vo) {

        if (Utility.isEmpty(vo.getCustomerName())) {
            return "消费者姓名为空";
        } else if (Utility.isEmpty(vo.getCustomerPhoneNumber())) {
            return "消费者手机号为空";
        } else if (Utility.isEmpty(vo.getCustomerSourceId()) || Utility.isEmpty(vo.getCustomerSourceNumber()) || Utility.isEmpty(vo.getCustomerSourceName())) {
            return "消费者来源为空";
        } else if (Utility.isEmpty(vo.getStoreId()) || Utility.isEmpty(vo.getStoreNumber()) || Utility.isEmpty(vo.getStoreName())) {
            return "门店为空";
        } else if (Utility.isEmpty(vo.getThirdOrgId()) || Utility.isEmpty(vo.getThirdOrgNumber()) || Utility.isEmpty(vo.getThirdOrgName())) {
            return "区域信息为空";
        } else if (Utility.isEmpty(vo.getSecondOrgId()) || Utility.isEmpty(vo.getSecondOrgNumber()) || Utility.isEmpty(vo.getSecondOrgName())) {
            return "区域信息为空";
        }else if (Utility.isEmpty(vo.getFirstOrgId()) || Utility.isEmpty(vo.getFirstOrgNumber()) || Utility.isEmpty(vo.getFirstOrgName())) {
            return "区域信息为空";
        }else if (Utility.isEmpty(vo.getGoodsList())) {
            return "商品信息为空";
        } else if (Utility.isEmpty(vo.getPartnerId()) || Utility.isEmpty(vo.getPartnerNumber()) || Utility.isEmpty(vo.getPartnerName())) {
            return "合伙人为空";
        } else if (Utility.isEmpty(vo.getCreateUserId())||Utility.isEmpty(vo.getCreateUserName())) {
            return "销售人员为空";
        } else if (Utility.isEmpty(vo.getTransactionId())) {
            return "订单流水号为空";
        } else if (Utility.isEmpty(vo.getSalesOrderStatusId()) || Utility.isEmpty(vo.getSalesOrderStatusNumber()) || Utility.isEmpty(vo.getSalesOrderStatusName())) {
            return "订单状态为空";
        }else if (Utility.isEmpty(vo.getPaymentStatusId()) || Utility.isEmpty(vo.getPaymentStatusNumber()) || Utility.isEmpty(vo.getPaymentStatusName())) {
            return "支付状态为空";
        }else if (Utility.isEmpty(vo.getReturnStatusId()) || Utility.isEmpty(vo.getReturnStatusNumber()) || Utility.isEmpty(vo.getReturnStatusName())) {
            return "退货状态为空";
        } else {
            return Constants.SUCCESS_CODE;
        }

    }

    @Override
    public ExecuteResult<List<NewSalesOrderVO>> queryListActivityData(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryListActivityData", JSONObject.toJSONString(vo));
        ExecuteResult<List<NewSalesOrderVO>> result = new ExecuteResult<>();
        try {
            result = newSalesOrderService.queryListActivityData(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryListActivityData", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryListActivityData", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<Long> queryCount(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryCount", JSONObject.toJSONString(vo));
        ExecuteResult<Long> result = new ExecuteResult<>();
        try {
            result = newSalesOrderService.queryCount(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryCount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryCount", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<SalesVolumeVO> queryCountAmount(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryCountAmount", JSONObject.toJSONString(vo));
        ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
        try {
            result = newSalesOrderService.queryCountAmount(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryCountAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryCountAmount", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<Long>> queryListSalesOrderId(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryListSalesOrderId", JSONObject.toJSONString(vo));
        ExecuteResult<List<Long>> result = new ExecuteResult<>();
        try {
            result = newSalesOrderService.queryListSalesOrderId(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryListSalesOrderId", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryListSalesOrderId", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<Map<String, Object>>> queryListToDayOrderTrend(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryListToDayOrderTrend", JSONObject.toJSONString(vo));
        ExecuteResult<List<Map<String, Object>>> result = new ExecuteResult<>();
        try {
            result = newSalesOrderService.queryListToDayOrderTrend(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryListToDayOrderTrend", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryListToDayOrderTrend", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<Map<String, Object>>> statisticsOrderTrend(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-statisticsOrderTrend", JSONObject.toJSONString(vo));
        ExecuteResult<List<Map<String, Object>>> result = new ExecuteResult<>();
        try {
            result = newSalesOrderService.statisticsOrderTrend(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-statisticsOrderTrend", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-statisticsOrderTrend", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsNotBuyVO>> queryListCancleTotal(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryListCancleTotal", JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsNotBuyVO>> result = new ExecuteResult<>();
        try {
            result = newSalesOrderService.queryListCancleTotal(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryListCancleTotal", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryListCancleTotal", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsSourceVO>> queryListCustomerSource(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryListCustomerSource", JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        try {
            result = newSalesOrderService.queryListCustomerSource(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryListCustomerSource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryListCustomerSource", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsSourceVO>> queryListNotBuyCause(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryListNotBuyCause", JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        try {
            result = newSalesOrderService.queryListNotBuyCause(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryListNotBuyCause", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryListNotBuyCause", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public String getMaxSalesOrderNumber(String nowDate) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-getMaxSalesOrderNumber", nowDate);
        String result = "";
        try {
            result = newSalesOrderService.getMaxSalesOrderNumber(nowDate);
        } catch (Exception e) {
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-getMaxSalesOrderNumber", e.getMessage());
            return Constants.ERROR_CODE;
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-getMaxSalesOrderNumber", result);
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsSalesVO>> queryStatsSalesOrderPage(NewSalesOrderVO newSalesOrderParamVO) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryStatsSalesOrderPage", JSONObject.toJSONString(newSalesOrderParamVO));
        ExecuteResult<List<StatisticsSalesVO>> result = new ExecuteResult<>();
        try {
            result = newSalesOrderService.queryStatsSalesOrderPage(newSalesOrderParamVO);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryStatsSalesOrderPage", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryStatsSalesOrderPage", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsSourceVO>> queryStatsSalesByType(NewSalesOrderVO vo, String type) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-queryListCustomerSource", JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        try {
            // result = newSalesOrderService.queryListCustomerSource(vo);
            if (OrderConstants.STATS_BY_SOURCE.equals(type)) {
                result = newSalesOrderService.queryStatsSalesBySource(vo);
            } else if (OrderConstants.STATS_BY_CATEGORY.equals(type)) {
                result = newSalesOrderService.queryStatsSaleByCategory(vo);
            } else if (OrderConstants.STATS_BY_ACTIVE.equals(type)) {
                result = newSalesOrderService.queryStatsSaleByActive(vo);
            }

        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-queryListCustomerSource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-queryListCustomerSource", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<NewSalesOrderVO>> queryamountList(NewSalesOrderVO vo) {
        ExecuteResult<List<NewSalesOrderVO>> result = new ExecuteResult<>();
        ExecuteResult<List<NewSalesOrderVO>> saleOrderResult = newSalesOrderService.queryList(vo);
        List<NewSalesOrderVO> list = new ArrayList<>();
        if (Utility.isEmpty(saleOrderResult.getResult())) {
            result.setResult(list);
            result.setCode(Constants.EMPTY_CODE);
            result.setResultMessage(Constants.QUERY_SUCCESS);
            return result;
        }
        List<NewSalesOrderVO> orderVOList = saleOrderResult.getResult();

        for (NewSalesOrderVO newSalesOrderVO : orderVOList) {
            Long salesOrderId = newSalesOrderVO.getSalesOrderId();
            BigDecimal salesOrderAmount = newSalesOrderVO.getSalesOrderAmount();
            NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
            newSalesOrderGoodsVO.setSalesOrderId(salesOrderId);
            ExecuteResult<List<NewSalesOrderGoodsVO>> goodsResuslt = newSalesOrderGoodsExportService.queryList(newSalesOrderGoodsVO);
            List<NewSalesOrderGoodsVO> orderGoodsVOList = goodsResuslt.getResult();
            BigDecimal totalMoney = orderGoodsVOList.stream().map(NewSalesOrderGoodsVO::getActualPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (salesOrderAmount.compareTo(totalMoney) != 0) {
                newSalesOrderVO.setTotalMoney(totalMoney);
                list.add(newSalesOrderVO);
            }
        }
        result.setResult(list);
        result.setCode(Constants.SUCCESS_CODE);
        result.setResultMessage(Constants.QUERY_SUCCESS);
        return result;
    }

    @Override
    public ExecuteResult<List<NewSalesOrderVO>> updateamountList(NewSalesOrderVO vo) {
        ExecuteResult<List<NewSalesOrderVO>> result = new ExecuteResult<>();
        ExecuteResult<List<NewSalesOrderVO>> saleOrderResult = newSalesOrderService.queryList(vo);
        List<NewSalesOrderVO> list = new ArrayList<>();
        if (Utility.isEmpty(saleOrderResult.getResult())) {
            result.setResult(list);
            result.setCode(Constants.EMPTY_CODE);
            result.setResultMessage(Constants.QUERY_SUCCESS);
            return result;
        }
        List<NewSalesOrderVO> orderVOList = saleOrderResult.getResult();

        for (NewSalesOrderVO newSalesOrderVO : orderVOList) {
            Long salesOrderId = newSalesOrderVO.getSalesOrderId();
            BigDecimal salesOrderAmount = newSalesOrderVO.getSalesOrderAmount();
            NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
            newSalesOrderGoodsVO.setSalesOrderId(salesOrderId);
            ExecuteResult<List<NewSalesOrderGoodsVO>> goodsResuslt = newSalesOrderGoodsExportService.queryList(newSalesOrderGoodsVO);
            List<NewSalesOrderGoodsVO> orderGoodsVOList = goodsResuslt.getResult();
            BigDecimal totalMoney = orderGoodsVOList.stream().map(NewSalesOrderGoodsVO::getActualPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (salesOrderAmount.compareTo(totalMoney) != 0) {
                newSalesOrderVO.setTotalMoney(totalMoney);
                list.add(newSalesOrderVO);
            }
        }
        if (Utility.isNotEmpty(list)) {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        boolean flag = true;
                        // 修改订单金额
                        for (NewSalesOrderVO newSalesOrderVO : list) {
                            newSalesOrderVO.setSalesOrderAmount(newSalesOrderVO.getTotalMoney());
                            ExecuteResult<NewSalesOrderVO> update = newSalesOrderService.update(newSalesOrderVO);
                            if (Utility.isEmpty(update.getResult())) {
                                flag = false;
                                result.setCode(Constants.ERROR_CODE);
                                result.setResultMessage(Constants.UPDATE_FAILURE);
                                status.setRollbackOnly();
                            }
                        }
                        // 修改退单金额
                        List<NewSalesOrderVO> returnSaleOrderList = list.stream().filter(item -> item.getReturnStatusId().equals(OrderConstants.ORDER_DICT_RUTURNYES)).collect(toList());
                        for (NewSalesOrderVO newSalesOrderVO : returnSaleOrderList) {
                            Long salesOrderId = newSalesOrderVO.getSalesOrderId();
                            NewReturnOrderVO newReturnOrderVO = new NewReturnOrderVO();
                            newReturnOrderVO.setSalesOrderId(salesOrderId);

                            ExecuteResult<List<NewReturnOrderVO>> returnResult = newReturnOrderExportService.queryList(newReturnOrderVO);
                            Long returnOrderId = returnResult.getResult().get(0).getReturnOrderId();
                            newReturnOrderVO.setReturnOrderId(returnOrderId);
                            newReturnOrderVO.setReturnOrderAmount(newSalesOrderVO.getTotalMoney());
                            ExecuteResult<NewReturnOrderVO> update = newReturnOrderExportService.update(newReturnOrderVO);
                            if (Utility.isEmpty(update.getResult())) {
                                flag = false;
                                result.setCode(Constants.ERROR_CODE);
                                result.setResultMessage(Constants.UPDATE_FAILURE);
                                status.setRollbackOnly();
                            }
                        }
                        if (flag) {
                            result.setResult(list);
                            result.setCode(Constants.SUCCESS_CODE);
                            result.setResultMessage(Constants.QUERY_SUCCESS);
                        } else {
                            result.setCode(Constants.ERROR_CODE);
                            result.setResultMessage(Constants.UPDATE_FAILURE);
                        }
                    } catch (Exception e) {
                        Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-updateamountList", e.getMessage());
                        status.setRollbackOnly();
                    }
                }
            });
        }
        return result;
    }

    @Override
    public ExecuteResult<String> reduceWarhouseGoodsAmount(NewSalesOrderVO newSalesOrderVO){
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-reduceWarhouseGoodsAmount", JSONObject.toJSONString(newSalesOrderVO));
        ExecuteResult<String> result = new ExecuteResult<>();
        AjaxInfo storeInfo = feignStoreService.getStoreById(Long.valueOf(newSalesOrderVO.getStoreId()));
        if (storeInfo.getCode().equals(Constants.DELFLG_NORMAL)) {
            Map<String, Object> storeMap = (Map<String, Object>) storeInfo.getData();
            Integer verify = (Integer) storeMap.get("verify");
            if (Constants.SUCCESS_CODE.equals(String.valueOf(verify))) {  // 强校验
                // 销售订单id
                Long salesOrderId = newSalesOrderVO.getSalesOrderId();
                // 查询商品数据
                NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
                newSalesOrderGoodsVO.setSalesOrderId(salesOrderId);
                ExecuteResult<List<NewSalesOrderGoodsVO>> salesGoodsResult = newSalesOrderGoodsExportService.queryList(newSalesOrderGoodsVO);
                List<NewSalesOrderGoodsVO> orderGoodsVOList = salesGoodsResult.getResult();

                // map调用出入库
                AjaxInfo info = new AjaxInfo();
                Map<String, Object> data = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Map<String, Object> warhouseMap = new HashMap<>();
                warhouseMap.put("storesId",newSalesOrderVO.getStoreId());
                AjaxInfo warhouseInfo = feignWarhouseService.queryWarhouse(warhouseMap);
                if (warhouseInfo.getCode().equals(Constants.DELFLG_NORMAL)) {
                    List<WarehouseVO> warhouseInfoData = (List<WarehouseVO>) warhouseInfo.getData();
                    String jsonData = JSONObject.toJSONString(warhouseInfoData);
                    List<WarehouseVO> warehouseVOS = JSONObject.parseArray(jsonData, WarehouseVO.class);
                    WarehouseVO warehouseVO = warehouseVOS.get(0);
                    data.put("warehouseId", warehouseVO.getId());
                    data.put("warehouseName", warehouseVO.getWarehouseName());
                    data.put("warehouseCode", warehouseVO.getWarehouseCode());
                }
                data.put("firstOrgId", newSalesOrderVO.getFirstOrgId());
                data.put("firstOrgNumber", newSalesOrderVO.getFirstOrgNumber());
                data.put("firstOrgName", newSalesOrderVO.getFirstOrgName());
                data.put("secondOrgId", newSalesOrderVO.getSecondOrgId());
                data.put("secondOrgNumber", newSalesOrderVO.getSecondOrgNumber());
                data.put("secondOrgName", newSalesOrderVO.getSecondOrgName());
                data.put("thirdOrgId", newSalesOrderVO.getThirdOrgId());
                data.put("thirdOrgNumber", newSalesOrderVO.getThirdOrgNumber());
                data.put("thirdOrgName", newSalesOrderVO.getThirdOrgName());
                data.put("storeId", newSalesOrderVO.getStoreId());
                data.put("storeNumber", newSalesOrderVO.getStoreNumber());
                data.put("storeName", newSalesOrderVO.getStoreName());
                data.put("storeChannelNumber", newSalesOrderVO.getStoreChannelNumber());
                data.put("deliveryTimeStr",sdf.format(new Date()));
                data.put("createUserId", newSalesOrderVO.getCreateUserId());
                data.put("createUserName", newSalesOrderVO.getCreateUserName());
                data.put("deliveryPerId", newSalesOrderVO.getCreateUserId());
                data.put("deliveryPerName", newSalesOrderVO.getCreateUserName());
                data.put("checkInventoryFlag",1);
                try {
                    if (newSalesOrderVO.getSalesOrderStatusId().equals(OrderConstants.ORDER_STATUS_SUBMISSION)) { // 支付状态前判断已提交 扣除库存

                        List<OutboundDetailVO> listOutboundDetail = orderGoodsVOList.stream().map(origin -> {
                            OutboundDetailVO outboundDetailVO = BeanUtil.copyPropertes(OutboundDetailVO.class, origin);
                            outboundDetailVO.setOutboundNum(origin.getOrderAmount());
                            return outboundDetailVO;
                        }).collect(toList());

                        data.put("outboundBillsNumber", newSalesOrderVO.getSalesOrderNumber());
                        data.put("deliveryNumber", newSalesOrderVO.getSalesOrderNumber());
                        data.put("outboundStatus", FeginConstants.OUTBOUND_STATUS_Y);
                        data.put("outboundType", FeginConstants.OUTBOUND_STATUS_SALES);
                        data.put("listOutboundDetail", listOutboundDetail);
                        Log.debug(log, "\n 方法[{}]，info：[{}]", "NewSalesOrderExportServiceImpl-reduceWarhouseGoodsAmount", JSONObject.toJSONString(data));
                        info = feignBoundService.addOutBound(data);
                    } else if (newSalesOrderVO.getSalesOrderStatusId().equals(OrderConstants.ORDER_STATUS_FINISH)) { // 退货状态前订单状态已完成 增加库存

                        List<InboundDetailVO> listInboundDetail = orderGoodsVOList.stream().map(origin -> {
                            InboundDetailVO inboundDetailVO = BeanUtil.copyPropertes(InboundDetailVO.class, origin);
                            inboundDetailVO.setInboundNum(origin.getOrderAmount());
                            return inboundDetailVO;
                        }).collect(toList());

                        data.put("deliveryNumber", newSalesOrderVO.getReturnOrderNumber());
                        data.put("sourceNo", newSalesOrderVO.getReturnOrderNumber());
                        data.put("storeCode", newSalesOrderVO.getStoreNumber());
                        data.put("listInboundDetail",listInboundDetail);
                        data.put("inboundStatus",FeginConstants.INBOUND_STATUS_Y);
                        data.put("inboundType",FeginConstants.INBOUND_STATUS_SALES);
                        data.put("listInboundDetail", listInboundDetail);
                        data.put("takePerId", newSalesOrderVO.getUpdateUserId());
                        data.put("takePerName", newSalesOrderVO.getUpdateUserName());
                        data.put("takeTime", sdf.format(new Date()));
                        Log.debug(log, "\n 方法[{}]，info：[{}]", "NewSalesOrderExportServiceImpl-reduceWarhouseGoodsAmount", JSONObject.toJSONString(data));
                        info = feignBoundService.addInBound(data);
                    }
                } catch (Exception e) {
                    result.setCode(Constants.ERROR_CODE);
                    result.setResultMessage("出入库服务异常");
                }
                if (!Constants.SUCCESS_CODE.equals(String.valueOf(info.getCode()))) {
                    result.setCode(Constants.ERROR_CODE);
                    result.setResultMessage(info.getMsg());
                    Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewReceiptRecordExportServiceImpl-reduceWarhouseGoodsAmount", result);
                    return result;
                }else{
                    result.setCode(Constants.SUCCESS_CODE);
                    result.setResultMessage(info.getMsg());
                }
            } else {
                result.setResult(String.valueOf(newSalesOrderVO.getSalesOrderId()));
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage("库存不校验订单号:" + newSalesOrderVO.getSalesOrderId());
            }
        } else {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage("门店服务异常");
        }
        return result;
    }

 /*   @Override
    public ExecuteResult<List<StatisticsSalesVO>> queryStatsReturnOrderPage(NewReturnOrderVO newSalesOrderParamVO){
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
    }*/

}

package com.camelot.order.service.impl.export;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.common.utils.BeanUtil;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.domain.CustomerDomain;
import com.camelot.order.export.CustomerExportService;
import com.camelot.order.export.service.NewNotBuyExportService;
import com.camelot.order.export.service.NewSalesOrderExportService;
import com.camelot.order.export.snapshot.CustomerVO;
import com.camelot.order.export.snapshot.NewNotBuyVO;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
import com.camelot.order.export.vo.excelvo.CustomerExcelVO;
import com.camelot.order.service.CustomerService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service("customerExportService")
public class CustomerExportServiceImpl extends BaseServiceImpl<CustomerVO, CustomerDomain> implements CustomerExportService {

    private static Logger log = Log.get(CustomerExportServiceImpl.class);

    /**
     * 消费者service
     */
    @Autowired
    private CustomerService customerService;
    /**
     * 订单service
     */
    @Autowired
    private NewSalesOrderExportService newSalesOrderExportService;
    /**
     * 未购买上报
     */
    @Autowired
    private NewNotBuyExportService newNotBuyExportService;

    /**
     * 编号series
     */
    @Autowired
    private NewOrderNumberExportService newOrderNumberExportService;

    @Override
    public ExecuteResult<PageInfo<CustomerVO>> queryCustomerPage(CustomerVO vo, Page<CustomerVO> page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "CustomerExportServiceImpl-queryCustomerPage", JSONObject.toJSONString(vo), JSONObject.toJSONString(page));
        ExecuteResult<PageInfo<CustomerVO>> result = new ExecuteResult<>();
        try {
            // 查询订单下的消费者ids
            NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
            // 查询未购买上报的消费者ids
            NewNotBuyVO newNotBuyVO = new NewNotBuyVO();
            // 内部用户添加权限
            if (Utility.isNotEmpty(vo.getOrgStr())) {
                List<Integer> orgList = Arrays.asList(vo.getOrgStr().split(",")).stream().map(e -> Integer.valueOf(e.trim())).collect(Collectors.toList());
                newSalesOrderVO.setOrgList(orgList);
                newNotBuyVO.setOrgList(orgList);
            }
            //外部用户添加权限
            if (Utility.isNotEmpty(vo.getStoreStr())) {
                List<Integer> storeList = Arrays.asList(vo.getStoreStr().split(",")).stream().map(e -> Integer.valueOf(e.trim())).collect(Collectors.toList());
                newSalesOrderVO.setStoreList(storeList);
                newNotBuyVO.setStoreList(storeList);
            }

            // 订单未滤重消费者id集合
            List<Long> customerOrderIdList = new ArrayList<>();
            // 查询销售订单
            ExecuteResult<List<NewSalesOrderVO>> saleOrderResult = newSalesOrderExportService.queryList(newSalesOrderVO);
            if (Utility.isNotEmpty(saleOrderResult.getResult())) {
                List<NewSalesOrderVO> orderVOList = saleOrderResult.getResult();
                customerOrderIdList = orderVOList.stream().distinct().map(e -> e.getCustomerId()).collect(Collectors.toList());
            }

            // 未购买上报未虑重消费者id集合
            List<Long> customerNotBuyIdList = new ArrayList<>();
            // 查询未购买上报
            ExecuteResult<List<NewNotBuyVO>> noBuyResult = newNotBuyExportService.queryList(newNotBuyVO);
            if (Utility.isNotEmpty(noBuyResult.getResult())) {
                List<NewNotBuyVO> notBuyVOList = noBuyResult.getResult();
                customerNotBuyIdList = notBuyVOList.stream().distinct().map(e -> e.getCustomerId()).collect(Collectors.toList());
            }
            // 订单滤重消费者id集合
            List<Long> distinctCustomerOrderIdList = customerOrderIdList.stream().distinct().collect(Collectors.toList());
            // 未购买上报虑重消费者id集合
            List<Long> distinctCustomerNotBuyIdList = customerNotBuyIdList.stream().distinct().collect(Collectors.toList());

            // 订单下的消费者id和未购买上报的消费者id集合数据
            List<Long> listAll = distinctCustomerOrderIdList.parallelStream().collect(Collectors.toList());
            List<Long> listAll2 = distinctCustomerNotBuyIdList.parallelStream().collect(Collectors.toList());
            listAll.addAll(listAll2);// 权限下的消费者id集合
            vo.setCustomerIdList(listAll);
            ExecuteResult<PageInfo<CustomerVO>> executeResult = customerService.queryListByPage(vo, page);
            if(Utility.isNotEmpty(executeResult.getResult())){
                List<CustomerVO> customerList = executeResult.getResult().getList();
                if (Utility.isNotEmpty(saleOrderResult.getResult())) {
                    // 当前权限下的订单数据
                    List<NewSalesOrderVO> orderVOList = saleOrderResult.getResult();

                    // 退货订单状态
                    List<NewSalesOrderVO> returnOrderList = orderVOList.stream()
                            .filter(e -> e.getSalesOrderStatusId().equals(OrderConstants.ORDER_STATUS_FINISH))
                            .filter(e -> e.getPaymentStatusId().equals(OrderConstants.DEFAULT_RECEIPT_STATUS))
                            .filter(e -> e.getReturnStatusId().equals(OrderConstants.ORDER_DICT_RUTURNYES))
                            .collect(Collectors.toList());
                    Map<Long, List<NewSalesOrderVO>> returnOrderGroup = returnOrderList.stream().collect(Collectors.groupingBy(NewSalesOrderVO::getCustomerId));
                    if (returnOrderGroup.size()>=1) {
                        returnOrderGroup.forEach((key, value) -> {
                            for (CustomerVO customerVO : customerList) {
                                if (customerVO.getCustomerId().equals(key)) {
                                    Long numberTotal = Long.valueOf(value.size());
                                    BigDecimal priceTotal = value.stream().map(NewSalesOrderVO::getSalesOrderAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                                    customerVO.setReturnNumberTotal(numberTotal.intValue()); //退单数量
                                    customerVO.setRefundAmount(priceTotal); // 退单金额
                                    break;
                                }
                            }
                        });
                    }

                    // 订单状态 统计已完成订单
                    List<NewSalesOrderVO> orderList = orderVOList.stream().filter(e -> e.getSalesOrderStatusId().equals(OrderConstants.ORDER_STATUS_FINISH)).collect(Collectors.toList());
                    Map<Long, List<NewSalesOrderVO>> orderByGroup = orderList.stream().collect(Collectors.groupingBy(NewSalesOrderVO::getCustomerId));

                    if (orderByGroup.size()>=1) {
                        orderByGroup.forEach((key, value) -> {
                            for (CustomerVO customerVO : customerList) {
                                if (customerVO.getCustomerId().equals(key)) {
                                    Long numberTotal = Long.valueOf(value.size());
                                    BigDecimal priceTotal = value.stream().map(NewSalesOrderVO::getSalesOrderAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                                    Integer returnNumberTotal = customerVO.getReturnNumberTotal();//退单数量
                                    BigDecimal refundAmount = customerVO.getRefundAmount();// 退货金额
                                    customerVO.setNumberTotal(numberTotal.intValue()); // 已完成订单数量
                                    customerVO.setPriceTotal(priceTotal); // 已完成订单金额
                                    if(Utility.isNotEmpty(refundAmount)){
                                        customerVO.setEffectiveAmount(priceTotal.subtract(refundAmount)); //有效订单金额
                                    }else{
                                        customerVO.setEffectiveAmount(priceTotal); //有效订单金额
                                    }
                                    if(Utility.isNotEmpty(returnNumberTotal)){
                                        customerVO.setEffectiveNumberTotal(Integer.valueOf(value.size())-returnNumberTotal); //有效订单数量
                                    }else{
                                        customerVO.setEffectiveNumberTotal(Integer.valueOf(value.size())); //有效订单数量
                                    }
                                    break;
                                }
                            }
                        });
                    }else{
                        for (CustomerVO customerVO : customerList) {
                            customerVO.setNumberTotal(0); // 订单数量
                            customerVO.setPriceTotal(new BigDecimal(0)); //订单金额
                            customerVO.setReturnNumberTotal(0); //退单数量
                            customerVO.setRefundAmount(new BigDecimal(0)); // 退单金额
                            customerVO.setEffectiveNumberTotal(0); // 有效订单数量
                            customerVO.setEffectiveAmount(new BigDecimal(0)); // 有效订单金额
                        }
                    }

                    for (CustomerVO customerVO : customerList) {
                        if(Utility.isEmpty(customerVO.getNumberTotal())){
                            customerVO.setNumberTotal(0);
                        }
                        if(Utility.isEmpty(customerVO.getPriceTotal())){
                            customerVO.setPriceTotal(new BigDecimal(0));
                        }
                        if(Utility.isEmpty(customerVO.getReturnNumberTotal())){
                            customerVO.setReturnNumberTotal(0);
                        }
                        if(Utility.isEmpty(customerVO.getRefundAmount())){
                            customerVO.setRefundAmount(new BigDecimal(0));
                        }
                        if(Utility.isEmpty(customerVO.getEffectiveNumberTotal())){
                            customerVO.setEffectiveNumberTotal(0);
                        }
                        if(Utility.isEmpty(customerVO.getEffectiveAmount())){
                            customerVO.setEffectiveAmount(new BigDecimal(0));
                        }
                    }
                    result.setResult(executeResult.getResult());
                    result.setCode(Constants.EMPTY_CODE);
                    result.setResultMessage(Constants.QUERY_SUCCESS);
                }else{
                    for (CustomerVO customerVO : customerList) {
                        customerVO.setNumberTotal(0);
                        customerVO.setPriceTotal(new BigDecimal(0));
                    }
                }
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setResult(null);
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "CustomerExportServiceImpl-queryCustomerPage", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "CustomerExportServiceImpl-queryCustomerPage", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<CustomerExcelVO>> exportList(List<CustomerVO> customerList) {
        ExecuteResult<List<CustomerExcelVO>> result = new ExecuteResult<>();
        List<CustomerExcelVO> list = new ArrayList<>();
        try {
            if (Utility.isNotEmpty(customerList)) {
                for (CustomerVO obvo : customerList) {
                    CustomerExcelVO vo = new CustomerExcelVO();
                    vo = BeanUtil.copyPropertes(CustomerExcelVO.class, obvo);
                    //转成货币
                    vo.setPriceTotal(DecimalFormat.getCurrencyInstance().format(vo.getPriceTotal()));
                    list.add(vo);
                }
                result.setResult(list);
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
        }
        return result;
    }

    @Override
    public ExecuteResult<CustomerVO> addCustomer(CustomerVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "CustomerExportServiceImpl-addCustomer", JSONObject.toJSONString(vo));
        ExecuteResult<CustomerVO> result = new ExecuteResult<>();
        try {
            // 根据手机号码和姓名判断消费者是否存在
            CustomerVO customer_query = new CustomerVO();
            customer_query.setCustomerPhoneNumber(vo.getCustomerPhoneNumber());
            customer_query.setCustomerName(vo.getCustomerName());
            ExecuteResult<List<CustomerVO>> queryResult = customerService.queryList(customer_query);
            ExecuteResult<CustomerVO> executeResult = null;
            if (Utility.isNotEmpty(queryResult.getResult())) {
                CustomerVO customerVO = queryResult.getResult().get(0);
                result.setCode(Constants.SUCCESS_CODE);
                result.setResult(customerVO);
                return result;
            } else {
                ExecuteResult<String> genidResult = newOrderNumberExportService.getGenid();
                if(Utility.isEmpty(genidResult.getResult())){
                    result.setCode(Constants.ERROR_CODE);
                    result.setResultMessage(Constants.SAVE_FAILURE);
                    return result;
                }
                String number = genidResult.getResult();
                vo.setCustomerId(Long.valueOf(number));
                vo.setCreateDate(new Date());
                vo.setModifyDate(new Date());
                vo.setUpdateUserId(vo.getCreateUserId());
                vo.setDelFlg(Constants.DELFLG_NORMAL);
                executeResult = customerService.add(vo);
            }
            if (Utility.isNotEmpty(executeResult.getResult())) {
                result.setResult(executeResult.getResult());
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.SAVE_SUCCESS);
            } else {
                result.setResult(null);
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage(Constants.SAVE_FAILURE);
            }
        } catch (Exception e) {
            result.setResult(null);
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "CustomerExportServiceImpl-addCustomer", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "CustomerExportServiceImpl-addCustomer", JSONObject.toJSONString(result));
        return result;
    }


}

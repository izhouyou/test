package com.camelot.order.service.impl.export;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.domain.NewNotBuyDomain;
import com.camelot.order.export.CustomerExportService;
import com.camelot.order.export.service.NewNotBuyExportService;
import com.camelot.order.export.snapshot.CustomerVO;
import com.camelot.order.export.snapshot.NewNotBuyVO;
import com.camelot.order.export.vo.StatisticsNotBuyVO;
import com.camelot.order.export.vo.StatisticsSourceVO;
import com.camelot.order.service.NewNotBuyService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author hudya
 */
@Service("newNotBuyExportService")
public class NewNotBuyExportServiceImpl extends BaseServiceImpl<NewNotBuyVO, NewNotBuyDomain> implements NewNotBuyExportService {

    private static Logger log = Log.get(NewNotBuyExportServiceImpl.class);

    /**
     * 事务
     */
    @Autowired
    TransactionTemplate transactionTemplate;
    /**
     * 未购买上报service
     */
    @Autowired
    private NewNotBuyService newNotBuyService;
    /**
     * 消费者service
     */
    @Autowired
    private CustomerExportService customerExportService;
    /**
     * 编号series
     */
    @Autowired
    private NewOrderNumberExportService newOrderNumberExportService;


    @Override
    public ExecuteResult<PageInfo<NewNotBuyVO>> queryNotBuyByPage(NewNotBuyVO vo, Page page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyExportServiceImpl-queryNotBuyByPage", JSONObject.toJSONString(vo), JSONObject.toJSONString(page));
        ExecuteResult<PageInfo<NewNotBuyVO>> result = new ExecuteResult<>();
        try {
            vo.setDelFlg(Constants.DELFLG_NORMAL);
            ExecuteResult<PageInfo<NewNotBuyVO>> executeResult = newNotBuyService.queryListByPage(vo, page);
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
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewNotBuyExportServiceImpl-queryNotBuyByPage", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewNotBuyExportServiceImpl-queryNotBuyByPage", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<NewNotBuyVO> addNotBuy(NewNotBuyVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyExportServiceImpl-queryNotBuyByPage", JSONObject.toJSONString(vo));
        ExecuteResult<NewNotBuyVO> result = new ExecuteResult<>();
        // 必输字段非空校验
        String validateResult = validateEmpty(vo);
        if (!Constants.SUCCESS_CODE.equals(validateResult)) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(validateResult);
            Log.debug(log, "\n 方法[{}]，出参：[{}]", "NotBuyExportServiceImpl-submitNotBuy", result);
            return result;
        }
        ExecuteResult<String> genidResult = newOrderNumberExportService.getGenid();
        if(Utility.isEmpty(genidResult.getResult())){
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.SAVE_FAILURE);
            return result;
        }
        String number = genidResult.getResult();

        ExecuteResult<NewNotBuyVO> query = newNotBuyService.queryById(Long.valueOf(number));
        if(Utility.isNotEmpty(query.getResult())){
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage("未购买数据已添加成功");
            return result;
        }

        // 事务控制
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    // 添加消费者数据
                    vo.setDelFlg(Constants.DELFLG_NORMAL);
                    vo.setCreateDate(new Date());
                    CustomerVO customerVO = BeanUtil.copyPropertes(CustomerVO.class, vo);
                    if (Utility.isNotEmpty(customerVO.getCustomerName()) && Utility.isNotEmpty(customerVO.getCustomerPhoneNumber())) {
                        ExecuteResult<CustomerVO> addCustomer = customerExportService.addCustomer(customerVO);
                        if (Utility.isEmpty(addCustomer.getResult())) {
                            result.setCode(addCustomer.getCode());
                            result.setResultMessage(addCustomer.getResultMessage());
                            status.setRollbackOnly();
                            Log.error(log, "\n 方法[{}]，异常：[{}]", "NotBuyExportServiceImpl-addNotBuy", addCustomer.getResultMessage());
                        } else {
                            CustomerVO customerAdd = addCustomer.getResult();
                            vo.setCustomerId(customerAdd.getCustomerId());
                        }
                    }
                    // 添加未购买上报数据
                    vo.setNotBuyId(Long.valueOf(number));
                    ExecuteResult<NewNotBuyVO> addNewNotBuy = newNotBuyService.add(vo);
                    if (Utility.isEmpty(addNewNotBuy.getResult())) {
                        result.setCode(addNewNotBuy.getCode());
                        result.setResultMessage(addNewNotBuy.getResultMessage());
                        Log.error(log, "\n 方法[{}]，异常：[{}]", "NotBuyExportServiceImpl-addNotBuy", addNewNotBuy.getResultMessage());
                        // 事务回滚
                        status.setRollbackOnly();
                    }
                    result.setResult(addNewNotBuy.getResult());
                    result.setCode(Constants.SUCCESS_CODE);
                    result.setResultMessage(Constants.SAVE_SUCCESS);
                } catch (Exception e) {
                    result.setCode(Constants.ERROR_CODE);
                    result.setResultMessage(Constants.SAVE_FAILURE);
                    Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-submitSalesOrder", e.getMessage());
                    // 事务回滚
                    status.setRollbackOnly();
                }
            }
        });
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewNotBuyExportServiceImpl-queryNotBuyByPage", JSONObject.toJSONString(result));
        return result;
    }


    /**
     * @param vo
     * @return
     * @Description 新增时, 必输字段的非空校验
     * @author xupengfei
     * @Date 2019年4月17日
     */
    public String validateEmpty(NewNotBuyVO vo) {
        if (Utility.isEmpty(vo.getVehicleThirdCategoryId()) || Utility.isEmpty(vo.getVehicleThirdCategoryNumber()) || Utility.isEmpty(vo.getVehicleThirdCategoryName())) {
            return "意向车型为空";
        } else if (Utility.isEmpty(vo.getNotBuyReasonId()) || Utility.isEmpty(vo.getNotBuyReasonNumber()) || Utility.isEmpty(vo.getNotBuyReasonName())) {
            return "未购买原因为空";
        } else if (Utility.isEmpty(vo.getStoreId()) || Utility.isEmpty(vo.getStoreNumber()) || Utility.isEmpty(vo.getStoreName())) {
            return "门店为空";
        } else if (Utility.isEmpty(vo.getCreateUserId())) {
            return "上报人ID为空";
        } else if (Utility.isEmpty(vo.getFirstOrgId()) || Utility.isEmpty(vo.getFirstOrgNumber()) || Utility.isEmpty(vo.getFirstOrgName())) {
            return "大区为空";
        } else if (Utility.isEmpty(vo.getSecondOrgId()) || Utility.isEmpty(vo.getSecondOrgNumber()) || Utility.isEmpty(vo.getSecondOrgName())) {
            return "区域为空";
        } else if (Utility.isEmpty(vo.getThirdOrgId()) || Utility.isEmpty(vo.getThirdOrgNumber()) || Utility.isEmpty(vo.getThirdOrgName())) {
            return "区域城市为空";
        } else {
            return Constants.SUCCESS_CODE;
        }
    }

    @Override
    public ExecuteResult<List<StatisticsNotBuyVO>> queryListNotBuyTotal(NewNotBuyVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyExportServiceImpl-queryListNotBuyTotal", JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsNotBuyVO>> result = new ExecuteResult<>();
        try {
            result = newNotBuyService.queryListNotBuyTotal(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewNotBuyExportServiceImpl-queryListNotBuyTotal", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewNotBuyExportServiceImpl-queryListNotBuyTotal", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsSourceVO>> queryListCustomerSource(NewNotBuyVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyExportServiceImpl-queryListCustomerSource", JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        try {
            result = newNotBuyService.queryListCustomerSource(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewNotBuyExportServiceImpl-queryListCustomerSource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewNotBuyExportServiceImpl-queryListCustomerSource", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsSourceVO>> queryListNotBuyCause(NewNotBuyVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyExportServiceImpl-queryListNotBuyCause", JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        try {
            result = newNotBuyService.queryListNotBuyCause(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewNotBuyExportServiceImpl-queryListNotBuyCause", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewNotBuyExportServiceImpl-queryListNotBuyCause", JSONObject.toJSONString(result));
        return result;
    }


}

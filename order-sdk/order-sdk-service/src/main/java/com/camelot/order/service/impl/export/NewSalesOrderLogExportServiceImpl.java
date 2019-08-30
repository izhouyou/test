package com.camelot.order.service.impl.export;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.domain.NewSalesOrderLogDomain;
import com.camelot.order.export.service.NewSalesOrderLogExportService;
import com.camelot.order.export.snapshot.NewSalesOrderLogVO;
import com.camelot.order.service.NewSalesOrderLogService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hudya
 */
@Service("newSalesOrderLogExportService")
public class NewSalesOrderLogExportServiceImpl extends BaseServiceImpl<NewSalesOrderLogVO, NewSalesOrderLogDomain> implements NewSalesOrderLogExportService {

    private static Logger log = Log.get(NewSalesOrderLogExportServiceImpl.class);

    @Autowired
    private NewSalesOrderLogService newSalesOrderLogService;
    @Autowired
    private NewOrderNumberExportService newOrderNumberExportService;

    @Override
    public ExecuteResult<PageInfo<NewSalesOrderLogVO>> querySalesOrderLogPage(NewSalesOrderLogVO vo, Page<NewSalesOrderLogVO> page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderExportServiceImpl-querySalesOrderLogPage", JSONObject.toJSONString(vo),JSONObject.toJSONString(page));
        ExecuteResult<PageInfo<NewSalesOrderLogVO>> result = new ExecuteResult<>();
        try {
            ExecuteResult<PageInfo<NewSalesOrderLogVO>> executeResult = newSalesOrderLogService.queryListByPage(vo, page);
            if(Utility.isNotEmpty(executeResult.getResult().getList())){
                result.setResult(executeResult.getResult());
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }else{
                result.setResult(null);
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setResult(null);
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderExportServiceImpl-querySalesOrderLogPage", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-querySalesOrderLogPage", JSONObject.toJSONString(result));
        return result;
    }


    @Override
    public ExecuteResult<NewSalesOrderLogVO> addSalesOrderLog(NewSalesOrderLogVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderLogExportServiceImpl-addSalesOrderLog", JSONObject.toJSONString(vo));
        ExecuteResult<NewSalesOrderLogVO> result = new ExecuteResult<>();
        try {

            ExecuteResult<String> genid = newOrderNumberExportService.getGenid();
            if(Utility.isEmpty(genid.getResult())){
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage("网络延迟,请稍后重试");
            }
            String number = genid.getResult();//19
            vo.setSalesOrderLogId(Long.valueOf(number));
            vo.setDelFlg(Constants.DELFLG_NORMAL);
            vo.setOperationTime(new Date());
            ExecuteResult<NewSalesOrderLogVO> executeResult = newSalesOrderLogService.add(vo);
            if(Utility.isNotEmpty(executeResult.getResult())){
                result.setResult(executeResult.getResult());
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.SAVE_SUCCESS);
            }else{
                result.setResult(null);
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.SAVE_FAILURE);
            }
        } catch (Exception e) {
            result.setResult(null);
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.SAVE_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderLogExportServiceImpl-addSalesOrderLog", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderLogExportServiceImpl-addSalesOrderLog", JSONObject.toJSONString(result));
        return result;
    }
}

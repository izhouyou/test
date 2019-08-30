package com.camelot.order.service.impl.export;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.domain.NewReturnOrderGoodsDomain;
import com.camelot.order.export.service.NewReturnOrderGoodsExportService;
import com.camelot.order.export.snapshot.NewReturnOrderGoodsVO;
import com.camelot.order.service.NewReturnOrderGoodsService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hudya
 */
@Service("newReturnOrderGoodsExportService")
public class NewReturnOrderGoodsExportServiceImpl extends BaseServiceImpl<NewReturnOrderGoodsVO, NewReturnOrderGoodsDomain> implements NewReturnOrderGoodsExportService {

    private static Logger log = Log.get(NewReturnOrderGoodsExportServiceImpl.class);

    /**
     * 退货订单service
     */
    @Autowired
    private NewReturnOrderGoodsService newReturnOrderGoodsService;
    @Autowired
    private NewOrderNumberExportService newOrderNumberExportService;

    @Override
    public ExecuteResult<PageInfo<NewReturnOrderGoodsVO>> queryPageReturnGoods(NewReturnOrderGoodsVO paramVO, Page page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReturnOrderGoodsExportServiceImpl-queryPageReturnGoods", JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<NewReturnOrderGoodsVO>> result = new ExecuteResult<>();
        try {
            result = newReturnOrderGoodsService.queryPageReturnGoods(paramVO, page);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewReturnOrderGoodsExportServiceImpl-queryPageReturnGoods", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReturnOrderGoodsExportServiceImpl-queryPageReturnGoods", JSONObject.toJSONString(result));
        return result;

    }

    @Override
    public ExecuteResult<List<NewReturnOrderGoodsVO>> queryReturnOrderGoods(NewReturnOrderGoodsVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryReturnOrderGoods", JSONObject.toJSONString(vo));
        ExecuteResult<List<NewReturnOrderGoodsVO>> result = new ExecuteResult<>();
        try {
            ExecuteResult<List<NewReturnOrderGoodsVO>> executeResult = newReturnOrderGoodsService.queryList(vo);
            if(Utility.isNotEmpty(executeResult.getResult())) {
                result.setResult(executeResult.getResult());
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryReturnOrderGoods", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryReturnOrderGoods", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<NewReturnOrderGoodsVO> addNewReturnGoods(NewReturnOrderGoodsVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsExportServiceImpl-addNewReturnGoods", JSONObject.toJSONString(vo));
        ExecuteResult<NewReturnOrderGoodsVO> result = new ExecuteResult<>();
        try {
            ExecuteResult<String> genid = newOrderNumberExportService.getGenid();
            if(Utility.isEmpty(genid.getResult())){
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage(Constants.SAVE_FAILURE);
                Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-addNewReturnGoods", JSONObject.toJSONString(result));
                return result;
            }
            String number = genid.getResult();
            vo.setReturnOrderGoodsId(Long.valueOf(number));
            ExecuteResult<NewReturnOrderGoodsVO> executeResult = newReturnOrderGoodsService.add(vo);
            if(Utility.isNotEmpty(executeResult.getResult())) {
                result.setResult(executeResult.getResult());
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.SAVE_FAILURE);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.SAVE_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-addNewReturnGoods", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsExportServiceImpl-addNewReturnGoods", JSONObject.toJSONString(result));
        return result;
    }

}

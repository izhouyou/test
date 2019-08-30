package com.camelot.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.utils.BeanUtil;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.NewReturnOrderDAO;
import com.camelot.order.domain.NewReturnOrderDomain;
import com.camelot.order.domain.NewSalesOrderGoodsDomain;
import com.camelot.order.export.snapshot.NewReturnOrderVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisticsSalesVO;
import com.camelot.order.export.vo.StatisticsSourceVO;
import com.camelot.order.service.NewReturnOrderService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author hudya
 */
@Service("newReturnOrderService")
public class NewReturnOrderServiceImpl extends BaseServiceImpl<NewReturnOrderVO, NewReturnOrderDomain> implements NewReturnOrderService {

	private static Logger log = Log.get(NewReturnOrderServiceImpl.class);
	
	@Autowired
	private NewReturnOrderDAO newReturnOrderDAO;
	
	@Override
	public ExecuteResult<BigDecimal> queryReturnAmount(NewReturnOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReturnOrderServiceImpl-queryReturnAmount",  JSONObject.toJSONString(vo));
        ExecuteResult<BigDecimal> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewReturnOrderDomain domain = BeanUtil.copyPropertes(NewReturnOrderDomain.class, vo);
        	BigDecimal queryResult= newReturnOrderDAO.queryReturnAmount(domain);
            if(Utility.isNotEmpty(queryResult)) {
            	result.setResult(queryResult);
            	flag = true;
            }
            if(flag) {
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            } else {
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewReturnOrderServiceImpl-queryReturnAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReturnOrderServiceImpl-queryReturnAmount",  JSONObject.toJSONString(result));
        return result;
	}

    @Override
    public String getMaxReturnOrderNumber(String nowDate) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReturnOrderServiceImpl-getMaxReturnOrderNumber", nowDate);
        String result = "";
        try {
            result = newReturnOrderDAO.getMaxReturnOrderNumber(nowDate);
        } catch (Exception e) {
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewReturnOrderServiceImpl-getMaxReturnOrderNumber", e.getMessage());
            return Constants.ERROR_CODE;
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReturnOrderServiceImpl-getMaxReturnOrderNumber", result);
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsSalesVO>> queryStatsReturnOrderPage(NewReturnOrderVO newSalesOrderParamVO){
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryStatsReturnOrderPage", JSONObject.toJSONString(newSalesOrderParamVO));
        ExecuteResult<List<StatisticsSalesVO>> result = new ExecuteResult<List<StatisticsSalesVO>>();
        try {

            NewReturnOrderDomain dom = (NewReturnOrderDomain)BeanUtil.copyPropertes(NewReturnOrderDomain.class, newSalesOrderParamVO);
            //PageHelper.startPage(page.getPageNum(), page.getPageSize(), true);
            List<Map<String, Object>> queryResult= newReturnOrderDAO.queryStatsReturnOrderPage(dom);
            if(queryResult.size() > 0){
                PageInfo pageInfo = new PageInfo<>(queryResult);
                List<StatisticsSalesVO> rtnList = (List<StatisticsSalesVO>)BeanUtil.copyList(StatisticsSalesVO.class,queryResult);

                result.setResult(rtnList);
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_FAILURE);
                return result;
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryStatsReturnOrderPage", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryStatsReturnOrderPage", JSONObject.toJSONString(result));
        return result;
    }



    @Override
    public ExecuteResult<List<StatisticsSourceVO>> queryStatsReturnByReason(NewReturnOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyServiceImpl-queryStatsReturnByReason",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
            NewReturnOrderDomain dom = (NewReturnOrderDomain)BeanUtil.copyPropertes(NewReturnOrderDomain.class, vo);
            List<Map<String, Object>> queryResult = newReturnOrderDAO.queryStatsReturnByReason(dom);
            if(Utility.isNotEmpty(queryResult)) {
                List<StatisticsSourceVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult), StatisticsSourceVO.class);
                result.setResult(resultList);
                flag = true;
            }
            if(flag) {
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            } else {
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryStatsReturnByReason", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryStatsReturnByReason",  JSONObject.toJSONString(result));
        return result;
    }

	@Override
	public ExecuteResult<SalesVolumeVO> queryCountAmount(NewReturnOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryCountAmount",  JSONObject.toJSONString(vo));
        ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewReturnOrderDomain domain = BeanUtil.copyPropertes(NewReturnOrderDomain.class, vo);
            Map<String, Object> queryResult= newReturnOrderDAO.queryCountAmount(domain);
            if(queryResult.size() > 0){
            	SalesVolumeVO salesVolumeVO = JSONObject.parseObject(JSONObject.toJSONString(queryResult), SalesVolumeVO.class);
                result.setResult(salesVolumeVO);
                flag = true;
            }
            if(flag) {
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            } else {
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsServiceImpl-queryCountAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsServiceImpl-queryCountAmount",  JSONObject.toJSONString(result));
        return result;
	}


}

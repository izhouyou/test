package com.camelot.order.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.NotBuyDAO;
import com.camelot.order.dao.SalesOrderDAO;
import com.camelot.order.export.vo.QueryParamVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisticsSourceVO;
import com.camelot.order.service.StatisticsService;
import com.camelot.order.service.impl.export.StatisticsExportServiceImpl;

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
	
	private static Logger log = Log.get(StatisticsExportServiceImpl.class);
	
	@Resource
	private SalesOrderDAO salesOrderDAO;
	@Autowired
	private NotBuyDAO notBuyDAO;

	@Override
	public List<StatisticsSourceVO> queryNotBuySource(QueryParamVO paramVO) {
		List<StatisticsSourceVO> result = new ArrayList<>();
		try {
			List<Map<String, Object>> queryList = notBuyDAO.queryCustomerIdSet(paramVO.getStoreIds(), paramVO.getStartDate(), paramVO.getEndDate());
			if(Utility.isNotEmpty(queryList)) {
				result = JSONObject.parseArray(JSONObject.toJSONString(queryList), StatisticsSourceVO.class);
			}
		} catch (Exception e) {
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsServiceImpl-queryNotBuySource", e.toString());
		}
		return result;
	}

	@Override
	public List<StatisticsSourceVO> queryOrderSource(QueryParamVO paramVO) {
		List<StatisticsSourceVO> result = new ArrayList<>();
		try {
			List<Map<String, Object>> queryList = salesOrderDAO.queryCustomerIdSet(paramVO.getStoreIds(), paramVO.getStartDate(), paramVO.getEndDate(), paramVO.getSalesOrderStatus());
			if(Utility.isNotEmpty(queryList)) {
				result = JSONObject.parseArray(JSONObject.toJSONString(queryList), StatisticsSourceVO.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ExecuteResult<List<Map<String, Object>>> queryNotBuyCause(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsServiceImpl-queryCancelCause", JSONObject.toJSONString(paramVO));
		ExecuteResult<List<Map<String, Object>>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			if(Utility.isNotEmpty(paramVO)) {
				List<Map<String, Object>> queryResult = new ArrayList<>();
				// 门店id
				String storeIds = paramVO.getStoreIds();
				// 开始时间
				String startDate = paramVO.getStartDate();
				// 结束时间
				String endDate = paramVO.getEndDate();
				// 已取消订单数量
				queryResult = notBuyDAO.queryNotBuyCause(storeIds,startDate,endDate);
				if(Utility.isNotEmpty(queryResult)) {
					result.setResult(queryResult);
					flag = true;
				}
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
			result.setResultMessage(e.getMessage());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsServiceImpl-queryNotBuyCause", e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsServiceImpl-queryNotBuyCause", JSONObject.toJSONString(result));
		return result;
	}
	
	@Override
	public ExecuteResult<List<Map<String, Object>>> queryCancelCause(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsServiceImpl-queryCancelCause", JSONObject.toJSONString(paramVO));
		ExecuteResult<List<Map<String, Object>>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			if(Utility.isNotEmpty(paramVO)) {
				List<Map<String, Object>> queryResult = new ArrayList<>();
				// 门店id
				String storeIds = paramVO.getStoreIds();
				// 开始时间
				String startDate = paramVO.getStartDate();
				// 结束时间
				String endDate = paramVO.getEndDate();
				// 订单状态
				String salesOrderStatus = paramVO.getSalesOrderStatus();
				queryResult = salesOrderDAO.queryCancelCause(storeIds,startDate,endDate,salesOrderStatus);
				if(Utility.isNotEmpty(queryResult)) {
					result.setResult(queryResult);
				}
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
			result.setResultMessage(e.getMessage());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsServiceImpl-queryCancelCause", e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsServiceImpl-queryCancelCause", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<SalesVolumeVO> toDayAmount(QueryParamVO paramVO) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "ClassName-methodName", JSONObject.toJSONString(paramVO));
        ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	String salesOrderStatus = paramVO.getSalesOrderStatus();
        	HashSet<String> salesOrderStatusSet = null;
        	// 订单状态
        	if(Utility.isNotEmpty(salesOrderStatus)) {
        		salesOrderStatusSet = new HashSet<>(Arrays.asList(salesOrderStatus.split(",")));
        	}
        	Map<String, Object> queryResult = salesOrderDAO.toDayAmount(salesOrderStatusSet,paramVO.getUserId(),paramVO.getStoreIds());
            if(Utility.isNotEmpty(queryResult)) {
            	SalesVolumeVO resultVO = JSONObject.parseObject(JSONObject.toJSONString(queryResult),SalesVolumeVO.class);
            	resultVO.setSalesAmountValueTotal(Utility.bigDecimalToString(resultVO.getSalesAmountTotal()));
            	result.setResult(resultVO);
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
            result.setResultMessage(e.getMessage());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "Name", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "Name", JSONObject.toJSONString(result));
        return result;
	}

}

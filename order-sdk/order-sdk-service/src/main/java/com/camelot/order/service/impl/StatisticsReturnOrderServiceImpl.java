package com.camelot.order.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.StatisticsReturnOrderDAO;
import com.camelot.order.domain.StatisticsReturnOrderDomain;
import com.camelot.order.export.vo.QueryParamVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisticsReturnOrderVO;
import com.camelot.order.service.StatisticsReturnOrderService;
import com.camelot.order.service.base.BaseServiceImpl;


@Service("statisticsReturnOrderService")
public class StatisticsReturnOrderServiceImpl extends BaseServiceImpl<StatisticsReturnOrderVO, StatisticsReturnOrderDomain> implements StatisticsReturnOrderService {

	private static Logger log = Log.get(StatisticsReturnOrderServiceImpl.class);
	
	@Resource
	private StatisticsReturnOrderDAO dao;

	@Override
	public ExecuteResult<Map<String, BigDecimal>> queryStatisticsResult(QueryParamVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsReturnOrderServiceImpl-queryStatisticsResult", JSONObject.toJSONString(vo));
        ExecuteResult<Map<String, BigDecimal>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	String orgIds = vo.getOrgIds();
        	String storeIds = vo.getStoreIds();
        	HashSet<String> orgIdSet = null;
        	HashSet<String> storeIdsSet = null;
        	if (Utility.isNotEmpty(orgIds)) {
        		orgIdSet = new HashSet<>(Arrays.asList(orgIds.split(",")));
    		}
    		if (Utility.isNotEmpty(storeIds)) {
    			storeIdsSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
    		}
			Map<String, BigDecimal> queryStatisticsResult = dao.queryStatisticsResult(orgIdSet,storeIdsSet,vo.getStartDate(),vo.getEndDate(),vo.getUserId());
			if(Utility.isNotEmpty(queryStatisticsResult)) {
				result.setResult(queryStatisticsResult);
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
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "StatisticsReturnOrderServiceImpl-queryStatisticsResult", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsReturnOrderServiceImpl-queryStatisticsResult", JSONObject.toJSONString(result));
		return result;
	}


}
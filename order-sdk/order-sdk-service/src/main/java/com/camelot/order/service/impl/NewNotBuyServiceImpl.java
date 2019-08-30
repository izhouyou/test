package com.camelot.order.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.NewNotBuyDAO;
import com.camelot.order.domain.NewNotBuyDomain;
import com.camelot.order.export.snapshot.NewNotBuyVO;
import com.camelot.order.export.vo.StatisticsNotBuyVO;
import com.camelot.order.export.vo.StatisticsSourceVO;
import com.camelot.order.service.NewNotBuyService;
import com.camelot.order.service.base.BaseServiceImpl;

/**
 * @author hudya
 */
@Service("newNotBuyService")
public class NewNotBuyServiceImpl extends BaseServiceImpl<NewNotBuyVO, NewNotBuyDomain> implements NewNotBuyService {
	
	private static Logger log = Log.get(NewNotBuyServiceImpl.class);
	
	@Autowired
	private NewNotBuyDAO newNotBuyDAO;

	@Override
	public ExecuteResult<List<StatisticsNotBuyVO>> queryListNotBuyTotal(NewNotBuyVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyServiceImpl-queryListNotBuyTotal",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsNotBuyVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewNotBuyDomain dom = (NewNotBuyDomain)BeanUtil.copyPropertes(NewNotBuyDomain.class, vo);
        	List<Map<String, Object>> queryResult = newNotBuyDAO.queryListNotBuyTotal(dom);
        	if(Utility.isNotEmpty(queryResult)) {
        		List<StatisticsNotBuyVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult), StatisticsNotBuyVO.class);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewNotBuyServiceImpl-queryListNotBuyTotal", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewNotBuyServiceImpl-queryListNotBuyTotal",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<StatisticsSourceVO>> queryListCustomerSource(NewNotBuyVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyServiceImpl-queryListCustomerSource",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewNotBuyDomain dom = (NewNotBuyDomain)BeanUtil.copyPropertes(NewNotBuyDomain.class, vo);
        	List<Map<String, Object>> queryResult = newNotBuyDAO.queryListCustomerSource(dom);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewNotBuyServiceImpl-queryListCustomerSource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewNotBuyServiceImpl-queryListCustomerSource",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<StatisticsSourceVO>> queryListNotBuyCause(NewNotBuyVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyServiceImpl-queryListNotBuyCause",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewNotBuyDomain dom = (NewNotBuyDomain)BeanUtil.copyPropertes(NewNotBuyDomain.class, vo);
        	List<Map<String, Object>> queryResult = newNotBuyDAO.queryListNotBuyCause(dom);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewNotBuyServiceImpl-queryListNotBuyCause", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewNotBuyServiceImpl-queryListNotBuyCause",  JSONObject.toJSONString(result));
        return result;
	}


}
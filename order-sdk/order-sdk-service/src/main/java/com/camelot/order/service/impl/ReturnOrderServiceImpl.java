package com.camelot.order.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.dao.ReturnOrderDAO;
import com.camelot.order.domain.ReturnOrderDomain;
import com.camelot.order.export.vo.ReturnOrderVO;
import com.camelot.order.service.ReturnOrderService;
import com.camelot.order.service.base.BaseServiceImpl;


@Service("returnOrderService")
public class ReturnOrderServiceImpl extends BaseServiceImpl<ReturnOrderVO, ReturnOrderDomain> implements ReturnOrderService {

	private static Logger log = Log.get(ReturnOrderServiceImpl.class);
	
	@Autowired
	private ReturnOrderDAO returnOrderDAO;
	
	@Override
	public ExecuteResult<List<ReturnOrderVO>> queryListByDate(String startDate, String endDate) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ReturnOrderServiceImpl-queryListByDate", JSONObject.toJSONString(startDate+"--"+endDate));
		ExecuteResult<List<ReturnOrderVO>> result = new ExecuteResult<>();
		try {
			List<ReturnOrderDomain> queryResult = returnOrderDAO.queryListByDate(startDate,endDate);
            if(queryResult.size() > 0){
                List<ReturnOrderVO> rtnList = (List<ReturnOrderVO>)BeanUtil.copyList(ReturnOrderVO.class,queryResult);
                result.setResult(rtnList);
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_FAILURE);
            }
			
		} catch (Exception e) {
			result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.error(log, "\n 方法[{}]，异常：[{}]", "ReturnOrderServiceImpl-queryListByDate", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "ReturnOrderServiceImpl-queryListByDate", JSONObject.toJSONString(result));
		return result;
	}


}
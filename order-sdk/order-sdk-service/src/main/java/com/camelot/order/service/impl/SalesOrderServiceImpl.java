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
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.SalesOrderDAO;
import com.camelot.order.domain.OrderCustomerDomain;
import com.camelot.order.domain.SalesOrderDomain;
import com.camelot.order.export.vo.OrderCustomerVO;
import com.camelot.order.export.vo.SalesOrderVO;
import com.camelot.order.service.SalesOrderService;
import com.camelot.order.service.base.BaseServiceImpl;

@Service("salesOrderService")
public class SalesOrderServiceImpl extends BaseServiceImpl<SalesOrderVO, SalesOrderDomain> implements SalesOrderService {

	private static Logger log = Log.get(SalesOrderServiceImpl.class);
	
	@Autowired
	private SalesOrderDAO salesOrderDAO;
	
	@Override
	public ExecuteResult<List<SalesOrderVO>> queryListByDate(String createDate, String endDate, List<Integer> orderStatusList) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderServiceImpl-queryYesterdayList", JSONObject.toJSONString(createDate));
		ExecuteResult<List<SalesOrderVO>> result = new ExecuteResult<List<SalesOrderVO>>();
		try {
			List<SalesOrderDomain> queryResult = salesOrderDAO.queryListByDate(createDate, endDate, orderStatusList);
			if(queryResult.size() > 0){
                List<SalesOrderVO> rtnList = (List<SalesOrderVO>)BeanUtil.copyList(SalesOrderVO.class,queryResult);
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
            Log.error(log, "\n 方法[{}]，异常：[{}]", "SalesOrderServiceImpl-queryYesterdayList", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderServiceImpl-queryYesterdayList", JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<OrderCustomerVO> statisticOrderWithCustomer(OrderCustomerVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderServiceImpl-statisticOrderWithCustomer", JSONObject.toJSONString(vo));
		ExecuteResult<OrderCustomerVO> result = new ExecuteResult<OrderCustomerVO>();
		try{
			OrderCustomerDomain dom = (OrderCustomerDomain) BeanUtil.copyPropertes(OrderCustomerDomain.class, vo);
			OrderCustomerDomain queryResult = salesOrderDAO.statisticsOrderWithCustomer(dom);
			if(Utility.isNotEmpty(queryResult.getCustomerId())){
	               OrderCustomerVO rtnvo = (OrderCustomerVO)BeanUtil.copyPropertes(OrderCustomerVO.class,queryResult);
	               result.setResult(rtnvo);
	               result.setCode(Constants.SUCCESS_CODE);
	               result.setResultMessage(Constants.QUERY_SUCCESS);
	           }else{
	               result.setCode(Constants.EMPTY_CODE);
	               result.setResultMessage(Constants.QUERY_FAILURE);
	           }
		}catch(Exception e){
			result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.error(log, "\n 方法[{}]，异常：[{}]", "SalesOrderServiceImpl-statisticOrderWithCustomer", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderServiceImpl-statisticOrderWithCustomer", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<List<SalesOrderVO>> queryOrderByStoreIds(SalesOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderServiceImpl-queryOrderByStoreIds", JSONObject.toJSONString(vo));
        ExecuteResult<List<SalesOrderVO>> result = new ExecuteResult<List<SalesOrderVO>>();
        try {
        	SalesOrderDomain dom = (SalesOrderDomain)BeanUtil.copyPropertes(SalesOrderDomain.class, vo);
            List<SalesOrderDomain> queryResult= salesOrderDAO.queryOrderByStoreIds(dom);
            if(queryResult.size() > 0){
                List<SalesOrderVO> rtnList = (List<SalesOrderVO>)BeanUtil.copyList(SalesOrderVO.class,queryResult);
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
            Log.error(log, "\n 方法[{}]，异常：[{}]", "SalesOrderServiceImpl-queryOrderByStoreIds", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderServiceImpl-queryOrderByStoreIds", JSONObject.toJSONString(result));
        return result;
	}
	
}
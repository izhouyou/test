package com.camelot.order.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.OrderGoodsDAO;
import com.camelot.order.dao.SalesOrderDAO;
import com.camelot.order.domain.OrderGoodsDomain;
import com.camelot.order.domain.SalesOrderDomain;
import com.camelot.order.export.vo.OrderGoodsVO;
import com.camelot.order.export.vo.QueryParamVO;
import com.camelot.order.export.vo.SalesOrderVO;
import com.camelot.order.export.vo.param.DifferenceParamVO;
import com.camelot.order.export.vo.param.ReturnGoodsParamVO;
import com.camelot.order.service.ComplianceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ComplianceServiceImpl implements ComplianceService {
	
	private static Logger log = Log.get(ComplianceServiceImpl.class);
	
	@Autowired
	private SalesOrderDAO salesOrderDAO;
	@Autowired
	private OrderGoodsDAO orderGoodsDAO;

	@Override
	public ExecuteResult<List<SalesOrderVO>> queryByStoreIds(String orgIds, String storeIds, String salesOrderNumber, String startDate,String endDate, String salesOrderStatus, String activityIds, Integer customerId) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceServiceImpl-queryByStoreIds", JSONObject.toJSONString(storeIds));
        ExecuteResult<List<SalesOrderVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	HashSet<String> orgIdSet = null;
        	HashSet<String> storeIdsSet = null;
    		HashSet<String> salesOrderStatusSet = null;
    		HashSet<String> activityIdSet = null;
    		if (Utility.isNotEmpty(orgIds)) {
    			orgIdSet = new HashSet<>(Arrays.asList(orgIds.split(",")));
			}
    		if (Utility.isNotEmpty(storeIds)) {
				storeIdsSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
			}
			if (Utility.isNotEmpty(salesOrderStatus)) {
				salesOrderStatusSet = new HashSet<>(Arrays.asList(salesOrderStatus.split(",")));
			}
			if (Utility.isNotEmpty(activityIds)) {
				activityIdSet = new HashSet<>(Arrays.asList(activityIds.split(",")));
			}
			List<SalesOrderDomain> queryResult = salesOrderDAO.queryByStoreIds(orgIdSet,storeIdsSet,salesOrderNumber,startDate,endDate,salesOrderStatusSet,activityIdSet,customerId);
			if(Utility.isNotEmpty(queryResult)) {
				List<SalesOrderVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult),SalesOrderVO.class);
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
            result.setResultMessage(e.getMessage());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceServiceImpl-queryByStoreIds", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceServiceImpl-queryByStoreIds", JSONObject.toJSONString(result));
        return result;
		
	}
	
	@Override
	public ExecuteResult<PageInfo<SalesOrderVO>> queryPageByStoreIds(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceServiceImpl-queryByStoreIds", JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<SalesOrderVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	String storeIds = paramVO.getStoreIds();
        	String salesOrderNumber = paramVO.getSalesOrderNumber();
        	String startDate = paramVO.getStartDate();
        	String endDate = paramVO.getEndDate();
        	String salesOrderStatus = paramVO.getSalesOrderStatus();
        	String activityIds = paramVO.getActivityIds();
        	String orgIds = paramVO.getOrgIds();
        	HashSet<String> orgIdSet = null;
        	HashSet<String> storeIdsSet = null;
    		HashSet<String> salesOrderStatusSet = null;
    		HashSet<String> activityIdSet = null;
    		if (Utility.isNotEmpty(orgIds)) {
    			orgIdSet = new HashSet<>(Arrays.asList(orgIds.split(",")));
			}
    		if (Utility.isNotEmpty(storeIds)) {
				storeIdsSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
			}
			if (Utility.isNotEmpty(salesOrderStatus)) {
				salesOrderStatusSet = new HashSet<>(Arrays.asList(salesOrderStatus.split(",")));
			}
			if (Utility.isNotEmpty(activityIds)) {
				activityIdSet = new HashSet<>(Arrays.asList(activityIds.split(",")));
			}
			PageHelper.startPage(paramVO.getPage().getPageNum(), paramVO.getPage().getPageSize(), true);
			List<SalesOrderDomain> queryResult = salesOrderDAO.queryByStoreIds(orgIdSet,storeIdsSet,salesOrderNumber,startDate,endDate,salesOrderStatusSet,activityIdSet,paramVO.getCustomerId());
			if(Utility.isNotEmpty(queryResult)) {
				PageInfo pageInfo = new PageInfo<>(queryResult);
				List<SalesOrderVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult),SalesOrderVO.class);
				pageInfo.setList(resultList);
				result.setResult(pageInfo);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceServiceImpl-queryByStoreIds", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceServiceImpl-queryByStoreIds", JSONObject.toJSONString(result));
        return result;
		
	}
	
	@Override
	public ExecuteResult<PageInfo<SalesOrderVO>> queryPageActivityOrder(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceServiceImpl-queryByStoreIds", JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<SalesOrderVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	String orgIds = paramVO.getOrgIds();
        	String storeIds = paramVO.getStoreIds();
        	String salesOrderNumber = paramVO.getSalesOrderNumber();
        	String startDate = paramVO.getStartDate();
        	String endDate = paramVO.getEndDate();
        	String salesOrderStatus = paramVO.getSalesOrderStatus();
        	String activityIds = paramVO.getActivityIds();
        	HashSet<String> orgIdSet = null;
        	HashSet<String> storeIdsSet = null;
    		HashSet<String> salesOrderStatusSet = null;
    		HashSet<String> activityIdSet = null;
    		if (Utility.isNotEmpty(orgIds)) {
				orgIdSet = new HashSet<>(Arrays.asList(orgIds.split(",")));
			}
    		if (Utility.isNotEmpty(storeIds)) {
				storeIdsSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
			}
			if (Utility.isNotEmpty(salesOrderStatus)) {
				salesOrderStatusSet = new HashSet<>(Arrays.asList(salesOrderStatus.split(",")));
			}
			if (Utility.isNotEmpty(activityIds)) {
				activityIdSet = new HashSet<>(Arrays.asList(activityIds.split(",")));
			}
			PageHelper.startPage(paramVO.getPage().getPageNum(), paramVO.getPage().getPageSize(), true);
			List<SalesOrderDomain> queryResult = salesOrderDAO.queryActivityOrder(orgIdSet,storeIdsSet,salesOrderNumber,startDate,endDate,salesOrderStatusSet,activityIdSet,paramVO.getCustomerId());
			if(Utility.isNotEmpty(queryResult)) {
				PageInfo pageInfo = new PageInfo<>(queryResult);
				List<SalesOrderVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult),SalesOrderVO.class);
				pageInfo.setList(resultList);
				result.setResult(pageInfo);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceServiceImpl-queryByStoreIds", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceServiceImpl-queryByStoreIds", JSONObject.toJSONString(result));
        return result;
		
	}
	
	@Override
	public ExecuteResult<List<SalesOrderVO>> queryActivityOrder(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceServiceImpl-queryActivityOrder", JSONObject.toJSONString(paramVO));
        ExecuteResult<List<SalesOrderVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	// 城市ids
        	String orgIds = paramVO.getOrgIds();
        	// 门店ids
        	String storeIds = paramVO.getStoreIds();
        	// 订单编号
        	String salesOrderNumber = paramVO.getSalesOrderNumber();
        	// 开始时间
        	String startDate = paramVO.getStartDate();
        	// 结束时间
        	String endDate = paramVO.getEndDate();
        	// 订单状态
        	String salesOrderStatus = paramVO.getSalesOrderStatus();
        	// 活动ids
        	String activityIds = paramVO.getActivityIds();
        	HashSet<String> orgIdSet = null;
        	HashSet<String> storeIdsSet = null;
    		HashSet<String> salesOrderStatusSet = null;
    		HashSet<String> activityIdSet = null;
    		if (Utility.isNotEmpty(orgIds)) {
    			orgIdSet = new HashSet<>(Arrays.asList(orgIds.split(",")));
			}
    		if (Utility.isNotEmpty(storeIds)) {
				storeIdsSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
			}
			if (Utility.isNotEmpty(salesOrderStatus)) {
				salesOrderStatusSet = new HashSet<>(Arrays.asList(salesOrderStatus.split(",")));
			}
			if (Utility.isNotEmpty(activityIds)) {
				activityIdSet = new HashSet<>(Arrays.asList(activityIds.split(",")));
			}
			List<SalesOrderDomain> queryResult = salesOrderDAO.queryActivityOrder(orgIdSet,storeIdsSet,salesOrderNumber,startDate,endDate,salesOrderStatusSet,activityIdSet,paramVO.getCustomerId());
			if(Utility.isNotEmpty(queryResult)) {
				List<SalesOrderVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult),SalesOrderVO.class);
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
            result.setResultMessage(e.getMessage());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceServiceImpl-queryActivityOrder", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceServiceImpl-queryActivityOrder", JSONObject.toJSONString(result));
        return result;
		
	}

	@Override
	public ExecuteResult<List<OrderGoodsVO>> queryListByOrderId(HashSet<Integer> orderIds, BigDecimal minAmount,
			BigDecimal maxAmount, String goodsSn, String goodsFrameNumber) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ClassName-methodName", JSONObject.toJSONString(orderIds));
        ExecuteResult<List<OrderGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	List<OrderGoodsDomain> queryResult = orderGoodsDAO.queryListByOrderId(orderIds,minAmount,maxAmount, goodsSn, goodsFrameNumber);
			if(Utility.isNotEmpty(queryResult)) {
				List<OrderGoodsVO> queryList = JSONObject.parseArray(JSONObject.toJSONString(queryResult),OrderGoodsVO.class);
				result.setResult(queryList);
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
	@Override
	public ExecuteResult<PageInfo<OrderGoodsVO>> queryPageByOrderId(HashSet<Integer> orderIds, BigDecimal minAmount,
			BigDecimal maxAmount, String goodsSn, String goodsFrameNumber, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceServiceImpl-queryByStoreIds", JSONObject.toJSONString(orderIds));
        ExecuteResult<PageInfo<OrderGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
    		PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<OrderGoodsDomain> queryResult = orderGoodsDAO.querySalesDifference(orderIds,minAmount,maxAmount, goodsSn, goodsFrameNumber);
			if (Utility.isNotEmpty(queryResult)) {
				PageInfo pageInfo = new PageInfo<>(queryResult);
				List<OrderGoodsVO> rtnList = JSONObject.parseArray(JSONObject.toJSONString(queryResult),
						OrderGoodsVO.class);
				pageInfo.setList(rtnList);
				result.setResult(pageInfo);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceServiceImpl-queryByStoreIds", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceServiceImpl-queryByStoreIds", JSONObject.toJSONString(result));
        return result;
	}
	@Override
	public ExecuteResult<List<OrderGoodsVO>> queryDifferenceList(HashSet<Integer> orderIds, BigDecimal minAmount,
			BigDecimal maxAmount, String goodsSn, String goodsFrameNumber) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceServiceImpl-queryByStoreIds", JSONObject.toJSONString(orderIds));
        ExecuteResult<List<OrderGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
			List<OrderGoodsDomain> queryResult = orderGoodsDAO.querySalesDifference(orderIds,minAmount,maxAmount, goodsSn, goodsFrameNumber);
			if (Utility.isNotEmpty(queryResult)) {
				List<OrderGoodsVO> rtnList = JSONObject.parseArray(JSONObject.toJSONString(queryResult),
						OrderGoodsVO.class);
				result.setResult(rtnList);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceServiceImpl-queryByStoreIds", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceServiceImpl-queryByStoreIds", JSONObject.toJSONString(result));
        return result;
	}
	
	@Override
	public Long queryReturnGoodsCount(HashSet<Integer> orderIdsSet, String goodsSn) {
		return orderGoodsDAO.queryReturnGoodsCount(orderIdsSet, goodsSn);
	}

	@Override
	public ExecuteResult<List<OrderGoodsVO>> queryReturnGoods(ReturnGoodsParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceServiceImpl-queryReturnGoods", JSONObject.toJSONString(paramVO));
        ExecuteResult<List<OrderGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	List<OrderGoodsDomain> queryResult = orderGoodsDAO.queryReturnGoods(paramVO.getSalesOrderIdSet(), paramVO.getGoodsSn(), paramVO.getGoodsFrameNumber());
            if(Utility.isNotEmpty(queryResult)) {
            	List<OrderGoodsVO> rtnList = JSONObject.parseArray(JSONObject.toJSONString(queryResult),
            			OrderGoodsVO.class);
            	result.setResult(rtnList);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceServiceImpl-queryReturnGoods", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceServiceImpl-queryReturnGoods", JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<PageInfo<OrderGoodsVO>> queryPageDifference(DifferenceParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceServiceImpl-queryPageDifference", JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<OrderGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	OrderGoodsDomain domain = new OrderGoodsDomain();
        	domain.setMinAmount(paramVO.getMinAmount());
        	domain.setMaxAmount(paramVO.getMaxAmount());
        	domain.setSalesOrderIdSet(paramVO.getSalesOrderIdSet());
    		PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<OrderGoodsDomain> queryResult = orderGoodsDAO.queryDifferenceList(domain);
			if (Utility.isNotEmpty(queryResult)) {
				PageInfo pageInfo = new PageInfo<>(queryResult);
				List<OrderGoodsVO> rtnList = JSONObject.parseArray(JSONObject.toJSONString(queryResult),
						OrderGoodsVO.class);
				pageInfo.setList(rtnList);
				result.setResult(pageInfo);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceServiceImpl-queryPageDifference", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceServiceImpl-queryPageDifference", JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<OrderGoodsVO>> queryListDifference(DifferenceParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceServiceImpl-queryListDifference", JSONObject.toJSONString(paramVO));
        ExecuteResult<List<OrderGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	OrderGoodsDomain domain = new OrderGoodsDomain();
        	domain.setMinAmount(paramVO.getMinAmount());
        	domain.setMaxAmount(paramVO.getMaxAmount());
        	domain.setSalesOrderIdSet(paramVO.getSalesOrderIdSet());
			List<OrderGoodsDomain> queryResult = orderGoodsDAO.queryDifferenceList(domain);
			if (Utility.isNotEmpty(queryResult)) {
				List<OrderGoodsVO> rtnList = JSONObject.parseArray(JSONObject.toJSONString(queryResult),
						OrderGoodsVO.class);
				result.setResult(rtnList);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceServiceImpl-queryListDifference", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceServiceImpl-queryListDifference", JSONObject.toJSONString(result));
        return result;
	}


}

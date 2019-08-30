package com.camelot.order.service.impl;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.NotBuyDAO;
import com.camelot.order.dao.SalesOrderDAO;
import com.camelot.order.dao.StatisticsTotalDAO;
import com.camelot.order.domain.StatisticsTotalDomain;
import com.camelot.order.export.vo.QueryParamVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisitcsGoodsSalesVO;
import com.camelot.order.export.vo.StatisticsGoodsVO;
import com.camelot.order.export.vo.StatisticsNotBuyVO;
import com.camelot.order.export.vo.StatisticsTotalVO;
import com.camelot.order.service.StatisticsTotalService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service("statisticsTotalService")
public class StatisticsTotalServiceImpl extends BaseServiceImpl<StatisticsTotalVO, StatisticsTotalDomain> implements StatisticsTotalService {

	private static Logger log = Log.get(StatisticsTotalServiceImpl.class);
	
	@Resource
	private StatisticsTotalDAO statisticsTotalDAO;
	@Autowired
	private NotBuyDAO notBuyDAO;
	@Autowired
	private SalesOrderDAO salesOrderDAO;

	@Override
	public ExecuteResult<Long> queryCount(StatisticsTotalVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsTotalServiceImpl-queryCount", JSONObject.toJSONString(vo));
        ExecuteResult<Long> result = new ExecuteResult<Long>();
		try {
			StatisticsTotalDomain dom = (StatisticsTotalDomain) BeanUtil.copyPropertes(StatisticsTotalDomain.class, vo);
			Long countResult = statisticsTotalDAO.queryCount(dom);
			result.setResult(countResult);
			result.setCode(Constants.SUCCESS_CODE);
            result.setResultMessage(Constants.QUERY_SUCCESS);
		} catch (Exception e) {
			result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "StatisticsTotalServiceImpl-queryCount", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsTotalServiceImpl-queryCount", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<SalesVolumeVO> queryStatisticsResult(QueryParamVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsTotalServiceImpl-queryStatisticsResult", JSONObject.toJSONString(vo));
        ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
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
			Map<String, Object> queryStatisticsResult = statisticsTotalDAO.queryStatisticsResult(orgIdSet,storeIdsSet,vo.getStartDate(),vo.getEndDate(),vo.getUserId(),OrderConstants.GOODS_FIRST_CATEGORY_ID,OrderConstants.MERCH_CATEGORY_ID);
			if(Utility.isNotEmpty(queryStatisticsResult)) {
				SalesVolumeVO resultVO = JSONObject.parseObject(JSONObject.toJSONString(queryStatisticsResult), SalesVolumeVO.class);
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
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "StatisticsTotalServiceImpl-queryStatisticsResult", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsTotalServiceImpl-queryStatisticsResult", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> queryGoodsVolume(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsTotalServiceImpl-queryGoodsVolume", JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	String orgIds = paramVO.getOrgIds();
        	String storeIds = paramVO.getStoreIds();
        	HashSet<String> storeIdsSet = null;
        	HashSet<String> orgIdSet = null;
        	if (Utility.isNotEmpty(orgIds)) {
        		orgIdSet = new HashSet<>(Arrays.asList(orgIds.split(",")));
    		}
    		if (Utility.isNotEmpty(storeIds)) {
    			storeIdsSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
    		}
    		if(Utility.isNotEmpty(paramVO.getPage())) {
    			PageHelper.startPage(paramVO.getPage().getPageNum(), paramVO.getPage().getPageSize(),true);
    		}
    		List<Map<String, Object>> queryStatisticsResult = statisticsTotalDAO.queryGoodsVolume(orgIdSet,storeIdsSet,paramVO.getStartDate(),paramVO.getEndDate(),paramVO.getGoodsFirstCategoryId());
			if(Utility.isNotEmpty(queryStatisticsResult)) {
				PageInfo pageInfo = new PageInfo<>(queryStatisticsResult);
				List<StatisitcsGoodsSalesVO> resultVO = JSONObject.parseArray(JSONObject.toJSONString(queryStatisticsResult), StatisitcsGoodsSalesVO.class);
				for (StatisitcsGoodsSalesVO statisitcsGoodsSalesVO : resultVO) {
					String retailValuePrice = Utility.bigDecimalToString(statisitcsGoodsSalesVO.getActualPrice());
					statisitcsGoodsSalesVO.setActualValuePrice(retailValuePrice);
				}
				pageInfo.setList(resultVO);
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
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "StatisticsTotalServiceImpl-queryGoodsVolume", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsTotalServiceImpl-queryGoodsVolume", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<List<Map<String, Object>>> queryOrderStatistics(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsTotalServiceImpl-queryOrderStatistics", JSONObject.toJSONString(paramVO));
        ExecuteResult<List<Map<String, Object>>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	String orgIds = paramVO.getOrgIds();
        	String storeIds = paramVO.getStoreIds();
        	HashSet<String> orgIdSet = null;
        	HashSet<String> storeIdsSet = null;
    		if (Utility.isNotEmpty(orgIds)) {
    			orgIdSet = new HashSet<>(Arrays.asList(orgIds.split(",")));
    		}
    		if (Utility.isNotEmpty(storeIds)) {
    			storeIdsSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
    		}
    		List<Map<String, Object>> queryStatisticsResult = statisticsTotalDAO.queryOrderStatistics(orgIdSet,storeIdsSet,paramVO.getStartDate(),paramVO.getEndDate(),paramVO.getUserId());
    		if(Utility.isEmpty(queryStatisticsResult)) {
    			Map<String, Object> empty = new HashMap<>();
    			String startDate = paramVO.getStartDate();
    			empty.put("statisticsOrderAmount", 0);
    			empty.put("statisticsAmount", "0.00");
    			empty.put("statisticsDate", startDate);
    			empty.put("statisticsDay", startDate.substring(startDate.length()-2, startDate.length()));
    			queryStatisticsResult.add(empty);
    			result.setResult(queryStatisticsResult);
    			result.setCode(Constants.EMPTY_CODE);
			}else {
				result.setResult(queryStatisticsResult);
				result.setCode(Constants.SUCCESS_CODE);
			}
    		result.setResultMessage(Constants.QUERY_SUCCESS);
		} catch (Exception e) {
			result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "StatisticsTotalServiceImpl-queryOrderStatistics", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsTotalServiceImpl-queryOrderStatistics", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public List<StatisticsNotBuyVO> queryNotBuy(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsTotalServiceImpl-queryNotBuy", JSONObject.toJSONString(paramVO));
		List<StatisticsNotBuyVO> result = new ArrayList<>();
		try {
			String storeIds = paramVO.getStoreIds();
        	HashSet<String> storeIdsSet = null;
    		if (Utility.isNotEmpty(storeIds)) {
    			storeIdsSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
    			List<Map<String, Object>> queryStatisticsResult = notBuyDAO.queryNotBuy(storeIdsSet,paramVO.getStartDate(),paramVO.getEndDate());
    			if(Utility.isNotEmpty(queryStatisticsResult)) {
    				result = JSONObject.parseArray(JSONObject.toJSONString(queryStatisticsResult), StatisticsNotBuyVO.class);
    			}
    		}
		} catch (Exception e) {
	        Log.error(log, "\n 方法[{}]，异常：[{}]", "StatisticsTotalServiceImpl-queryNotBuy", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsTotalServiceImpl-queryNotBuy", JSONObject.toJSONString(result));
		return result;
	}
	
	@Override
	public List<StatisticsNotBuyVO> queryOrderAmount(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsTotalServiceImpl-queryOrderAmount", JSONObject.toJSONString(paramVO));
		List<StatisticsNotBuyVO> result = new ArrayList<>();
		try {
			String storeIds = paramVO.getStoreIds();
			String orgIds = paramVO.getOrgIds();
			HashSet<String> storeIdsSet = null;
			HashSet<String> salesOrderStatusSet = null;
			HashSet<String> orgIdSet = null;
			if (Utility.isNotEmpty(paramVO.getSalesOrderStatus())) {
				salesOrderStatusSet = new HashSet<>(Arrays.asList(paramVO.getSalesOrderStatus().split(",")));
			}
			if (Utility.isNotEmpty(storeIds)) {
    			storeIdsSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
			}
    		if(Utility.isNotEmpty(orgIds)) {
    			orgIdSet = new HashSet<>(Arrays.asList(orgIds.split(",")));
    		}
			List<Map<String, Object>> queryStatisticsResult = salesOrderDAO.queryCancelOrder(orgIdSet,storeIdsSet,paramVO.getStartDate(),paramVO.getEndDate(), salesOrderStatusSet);
			if(Utility.isNotEmpty(queryStatisticsResult)) {
				result = JSONObject.parseArray(JSONObject.toJSONString(queryStatisticsResult), StatisticsNotBuyVO.class);
			}
		} catch (Exception e) {
	        Log.error(log, "\n 方法[{}]，异常：[{}]", "StatisticsTotalServiceImpl-queryOrderAmount", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsTotalServiceImpl-queryOrderAmount", JSONObject.toJSONString(result));
		return result;
	}

	@Override
    public ExecuteResult<PageInfo<Map<String, Object>>> queryCarAssort(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsTotalServiceImpl-queryCarAssort", JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<Map<String, Object>>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	StatisticsTotalDomain statisticsTotalDomain = new StatisticsTotalDomain();
        	statisticsTotalDomain.setGoodsSecondCategoryId(paramVO.getGoodsSecondCategoryId());
        	statisticsTotalDomain.setGoodsThirdCategoryId(paramVO.getGoodsThirdCategoryId());
        	statisticsTotalDomain.setGoodsAttributeId(paramVO.getGoodsAttributeId());
        	statisticsTotalDomain.setFirstOrgId(paramVO.getFirstOrgId());
        	statisticsTotalDomain.setSecondOrgId(paramVO.getSecondOrgId());
        	statisticsTotalDomain.setThirdOrgId(paramVO.getThirdOrgId());
        	statisticsTotalDomain.setPartnerId(paramVO.getPartnerId());
        	statisticsTotalDomain.setFranchiseeId(paramVO.getFranchiseeId());
        	statisticsTotalDomain.setCustomerSourceId(paramVO.getSourceId());
        	statisticsTotalDomain.setGoodsFirstCategoryId(OrderConstants.GOODS_FIRST_CATEGORY_ID);
        	HashSet<String> storeIds = null;
        	HashSet<String> orgIdSet = null;
        	if(Utility.isNotEmpty(paramVO.getStoreIds())) {
        		storeIds = new HashSet<>(Arrays.asList(paramVO.getStoreIds().split(",")));
        	}
        	if(Utility.isNotEmpty(paramVO.getOrgIds())) {
        		orgIdSet = new HashSet<>(Arrays.asList(paramVO.getOrgIds().split(",")));
        	}
        	PageHelper.startPage(paramVO.getPage().getPageNum(), paramVO.getPage().getPageSize(),true);
        	List<Map<String, Object>> queryResult = statisticsTotalDAO.queryCarAssort(statisticsTotalDomain,paramVO.getStartDate(),paramVO.getEndDate(),storeIds,orgIdSet);
            if(Utility.isNotEmpty(queryResult)) {
            	PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(queryResult);
            	flag = true;
            	result.setResult(pageInfo);
            }
        	if(flag) {
            result.setCode(Constants.SUCCESS_CODE);
            result.setResultMessage(Constants.QUERY_SUCCESS);
            } else {
            result.setCode(Constants.EMPTY_CODE);
            result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	result.setCode(Constants.ERROR_CODE);
        	result.setResultMessage(e.getMessage());
        	Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsTotalServiceImpl-queryCarAssort", e.toString());
       	}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsTotalServiceImpl-queryCarAssort", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<List<Map<String, Object>>> queryToDayOrderStatistics(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsTotalServiceImpl-queryToDayOrderStatistics", JSONObject.toJSONString(paramVO));
        ExecuteResult<List<Map<String, Object>>> result = new ExecuteResult<>();
        try {
        	String orgIds = paramVO.getOrgIds();
        	String storeIds = paramVO.getStoreIds();
        	HashSet<String> orgIdSet = null;
        	HashSet<String> storeIdsSet = null;
    		if (Utility.isNotEmpty(orgIds)) {
    			orgIdSet = new HashSet<>(Arrays.asList(orgIds.split(",")));
    		}
    		if (Utility.isNotEmpty(storeIds)) {
    			storeIdsSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
    		}
    		String endDate = paramVO.getStartDate() + " 23:59:59";
    		String startDate = paramVO.getStartDate() + " 00:00:00";
    		List<Map<String, Object>> queryStatisticsResult = statisticsTotalDAO.queryToDayOrderStatistics(orgIdSet,storeIdsSet,startDate,endDate,paramVO.getUserId());
    		if(Utility.isEmpty(queryStatisticsResult)) {
    			Map<String, Object> empty = new HashMap<>();
    			empty.put("statisticsOrderAmount", 0);
    			empty.put("statisticsAmount", "0.00");
    			empty.put("statisticsDate", 0);
    			empty.put("statisticsDay", 0);
    			queryStatisticsResult.add(empty);
    			result.setResult(queryStatisticsResult);
    			result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
			}else {
				result.setResult(queryStatisticsResult);
				result.setCode(Constants.SUCCESS_CODE);
			}
            result.setResultMessage(Constants.QUERY_SUCCESS);
		} catch (Exception e) {
			result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "StatisticsTotalServiceImpl-queryToDayOrderStatistics", e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsTotalServiceImpl-queryToDayOrderStatistics", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisticsGoodsVO>> queryPageGoodsList(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsTotalServiceImpl-queryPageGoodsList", JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<StatisticsGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	// 分页
        	PageHelper.startPage(paramVO.getPage().getPageNum(), paramVO.getPage().getPageSize(), true);
        	// DAO查询
        	List<Map<String, Object>> statisticsTotalList = statisticsTotalDAO.queryGoodsList(paramVO.getStoreIds(),paramVO.getStartDate(),paramVO.getEndDate(),paramVO.getSortFlag(),paramVO.getUserId());
            if(Utility.isNotEmpty(statisticsTotalList)) {
            	PageInfo pageInfo = new PageInfo<>(statisticsTotalList);
            	List<StatisticsGoodsVO> statisticsGoodsList = JSONObject.parseArray(JSONObject.toJSONString(statisticsTotalList),StatisticsGoodsVO.class);
            	pageInfo.setList(statisticsGoodsList);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsTotalServiceImpl-queryPageGoodsList", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsTotalServiceImpl-queryPageGoodsList", JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<String> queryMaxDate() {
        ExecuteResult<String> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	String queryResult = statisticsTotalDAO.queryMaxDate();
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
            result.setResultMessage(e.getMessage());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsTotalServiceImpl-queryMaxDate", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsTotalServiceImpl-queryMaxDate", JSONObject.toJSONString(result));
        return result;
	}
	
}
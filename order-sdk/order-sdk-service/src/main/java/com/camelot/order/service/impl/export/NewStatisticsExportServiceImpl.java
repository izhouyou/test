package com.camelot.order.service.impl.export;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.camelot.common.bean.AjaxInfo;
import com.camelot.order.export.vo.*;
import com.camelot.order.export.vo.feignvo.StoreVO;
import com.camelot.order.export.vo.param.*;
import com.camelot.order.feign.FeignStoreService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.common.utils.BeanUtil;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.service.DictValueExportService;
import com.camelot.order.export.service.FeignCouponExportService;
import com.camelot.order.export.service.NewNotBuyExportService;
import com.camelot.order.export.service.NewReturnOrderExportService;
import com.camelot.order.export.service.NewSalesOrderExportService;
import com.camelot.order.export.service.NewSalesOrderGoodsExportService;
import com.camelot.order.export.service.NewStatisticsExportService;
import com.camelot.order.export.snapshot.NewNotBuyVO;
import com.camelot.order.export.snapshot.NewReturnOrderVO;
import com.camelot.order.export.snapshot.NewSalesOrderGoodsVO;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
import com.camelot.order.export.vo.feignvo.FeignCouponBatchVO;
import com.github.pagehelper.PageInfo;

@Service("newStatisticsExportService")
public class NewStatisticsExportServiceImpl implements NewStatisticsExportService {
	
	private static Logger log = Log.get(NewStatisticsExportServiceImpl.class);
	
	@Autowired
	private NewSalesOrderExportService newSalesOrderExportService;
	@Autowired
	private NewReturnOrderExportService newReturnOrderExportService;
	@Autowired
	private NewSalesOrderGoodsExportService newSalesOrderGoodsExportService;
	@Autowired
	private NewNotBuyExportService newNotBuyExportService;
	
	@Autowired
	private FeignCouponExportService feignCouponExportService;
	
	@Autowired
	DictValueExportService dictValueExportService;


    @Autowired
    FeignStoreService feignStoreService;

	@Override
	public ExecuteResult<PageInfo<SalesReportVO>> statisticsPageSalesReport(SalesReportParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageSalesReport",  JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<SalesReportVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderVO newSalesOrderParamVO = new NewSalesOrderVO();
        	List<NewSalesOrderGoodsVO> newSalesOrderGoodsList = null;
        	// 如果SN或车架号不为空,优先查询订单商品信息
        	if(Utility.isNotEmpty(paramVO.getGoodsSn()) || Utility.isNotEmpty(paramVO.getGoodsFrameNumber())) {
        		NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
        		// SN
        		newSalesOrderGoodsVO.setGoodsSn(paramVO.getGoodsSn());
        		// 车架号
        		newSalesOrderGoodsVO.setGoodsFrameNumber(paramVO.getGoodsFrameNumber());
        		ExecuteResult<List<NewSalesOrderGoodsVO>> newSalesOrderGoodsExecuteResult = newSalesOrderGoodsExportService.queryList(newSalesOrderGoodsVO);
        		newSalesOrderGoodsList = newSalesOrderGoodsExecuteResult.getResult();
        		if(Utility.isEmpty(newSalesOrderGoodsList)) {
        			result.setCode(Constants.EMPTY_CODE);
                    result.setResultMessage(Constants.QUERY_SUCCESS);
                    return result;
        		}
        		// 销售订单Id
        		HashSet<Long> salesOrderIdSet = new HashSet<>();
        		for (NewSalesOrderGoodsVO newSalesOrderGoods : newSalesOrderGoodsList) {
        			salesOrderIdSet.add(newSalesOrderGoods.getSalesOrderId());
				}
        		List<Long> salesOrderIdList = new ArrayList<>(salesOrderIdSet);
        		newSalesOrderParamVO.setSalesOrderIdList(salesOrderIdList);
        	}
        	// 城市IdList
        	newSalesOrderParamVO.setOrgList(paramVO.getOrgIdList());
        	// 门店IdList
    		if(Utility.isNotEmpty(paramVO.getStoreIds())) { // 查询条件(门店)不为空
    			List<Integer> storeIdList = Utility.stringToList(paramVO.getStoreIds());
    			if(Utility.isNotEmpty(paramVO.getStoreIdList())) {
    				if(Utility.isNotEmpty(paramVO.getStoreIdList())) {
    					storeIdList.retainAll(paramVO.getStoreIdList());
    				}
    			}
    			newSalesOrderParamVO.setStoreList(storeIdList);
    		} else {
    			newSalesOrderParamVO.setStoreList(paramVO.getStoreIdList());
    		}
        	// 起止时间
    		newSalesOrderParamVO.setStartCreateDate(paramVO.getStartDate());
    		newSalesOrderParamVO.setEndCreateDate(paramVO.getEndDate());
        	// 订单状态
    		newSalesOrderParamVO.setSalesOrderStatusId(paramVO.getSalesOrderStatus());
    		// 订单-退货状态
    		newSalesOrderParamVO.setReturnStatusId(paramVO.getReturnStatusId());
    		// 订单-退货状态
    		newSalesOrderParamVO.setPaymentStatusId(paramVO.getPaymentStatusId());
        	// 活动查询条件
    		newSalesOrderParamVO.setActivitySearch(paramVO.getCodeOrName());
    		// 消费者id
    		newSalesOrderParamVO.setCustomerId(paramVO.getCustomerId());
    		// 查询销售订单信息
        	ExecuteResult<PageInfo<NewSalesOrderVO>> queryPageResult = newSalesOrderExportService.queryListByPage(newSalesOrderParamVO, page);
        	if(Utility.isNotEmpty(queryPageResult.getResult())){
				List<NewSalesOrderVO> queryResult = queryPageResult.getResult().getList();
				List<SalesReportVO> resultList = new ArrayList<>();
				// 销售订单id集合
				List<Long> salesOrderIdList = new ArrayList<>();
				for (NewSalesOrderVO newSalesOrderVO : queryResult) {
					salesOrderIdList.add(newSalesOrderVO.getSalesOrderId());
					SalesReportVO salesReportVO = (SalesReportVO)BeanUtil.copyPropertes(SalesReportVO.class, newSalesOrderVO);
					resultList.add(salesReportVO);
				}
				if(Utility.isNotEmpty(salesOrderIdList)) {
					NewReturnOrderVO newReturnOrderVO = new NewReturnOrderVO();
					newReturnOrderVO.setSalesOrderIdList(salesOrderIdList);
					ExecuteResult<List<NewReturnOrderVO>> queryNewReturnOrderList = newReturnOrderExportService.queryList(newReturnOrderVO);
					List<NewReturnOrderVO> newReturnOrderList = queryNewReturnOrderList.getResult();
					if(Utility.isNotEmpty(newReturnOrderList)) {
						HashMap<Long, NewReturnOrderVO> newReturnOrderMap = new HashMap<>();
						for (NewReturnOrderVO newReturnOrder : newReturnOrderList) {
							newReturnOrderMap.put(newReturnOrder.getSalesOrderId(), newReturnOrder);
						}
						for(SalesReportVO salesReportVO : resultList) {
							Long salesOrderId = salesReportVO.getSalesOrderId();
							if(newReturnOrderMap.containsKey(salesOrderId)) {
								Long tSalesOrderId = newReturnOrderMap.get(salesOrderId).getReturnOrderId();
								salesReportVO.setReturnOrderId(tSalesOrderId);
							}
						}
						
					}
				}
				// 门店销售报表信息
				@SuppressWarnings("rawtypes")
				PageInfo pageInfo = queryPageResult.getResult();
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
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsPageSalesReport", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageSalesReport",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<NewSalesReportVO>> statisticsListSalesReport(SalesReportParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsListSalesReport",  JSONObject.toJSONString(paramVO));
        ExecuteResult<List<NewSalesReportVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderVO newSalesOrderParamVO = new NewSalesOrderVO();
        	List<NewSalesOrderGoodsVO> newSalesOrderGoodsList = null;
        	// 如果SN或车架号不为空,优先查询订单商品信息
        	if(Utility.isNotEmpty(paramVO.getGoodsSn()) || Utility.isNotEmpty(paramVO.getGoodsFrameNumber())) {
        		NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
        		// SN
        		newSalesOrderGoodsVO.setGoodsSn(paramVO.getGoodsSn());
        		// 车架号
        		newSalesOrderGoodsVO.setGoodsFrameNumber(paramVO.getGoodsFrameNumber());
        		ExecuteResult<List<NewSalesOrderGoodsVO>> newSalesOrderGoodsExecuteResult = newSalesOrderGoodsExportService.queryList(newSalesOrderGoodsVO);
        		newSalesOrderGoodsList = newSalesOrderGoodsExecuteResult.getResult();
        		if(Utility.isEmpty(newSalesOrderGoodsList)) {
        			result.setCode(Constants.EMPTY_CODE);
                    result.setResultMessage(Constants.QUERY_SUCCESS);
                    return result;
        		}
        		// 销售订单Id
        		HashSet<Long> salesOrderIdSet = new HashSet<>();
        		for (NewSalesOrderGoodsVO newSalesOrderGoods : newSalesOrderGoodsList) {
        			salesOrderIdSet.add(newSalesOrderGoods.getSalesOrderId());
				}
        		List<Long> salesOrderIdList = new ArrayList<>(salesOrderIdSet);
        		newSalesOrderParamVO.setSalesOrderIdList(salesOrderIdList);
        	}
        	// 城市IdList
        	newSalesOrderParamVO.setOrgList(paramVO.getOrgIdList());
        	// 门店IdList
    		if(Utility.isNotEmpty(paramVO.getStoreIds())) { // 查询条件(门店)不为空
    			List<Integer> storeIdList = Utility.stringToList(paramVO.getStoreIds());
    			if(Utility.isNotEmpty(paramVO.getStoreIdList())) {
	    			storeIdList.retainAll(paramVO.getStoreIdList());
	    			newSalesOrderParamVO.setStoreList(storeIdList);
    			} else {
    				newSalesOrderParamVO.setStoreList(storeIdList);
    			}
    		} else {
    			newSalesOrderParamVO.setStoreList(paramVO.getStoreIdList());
    		}
        	// 起止时间
    		newSalesOrderParamVO.setStartCreateDate(paramVO.getStartDate());
    		newSalesOrderParamVO.setEndCreateDate(paramVO.getEndDate());
    		// 订单状态-已完成
    		newSalesOrderParamVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
    		// 订单状态
    		newSalesOrderParamVO.setSalesOrderStatusId(paramVO.getSalesOrderStatus());
    		// 订单-退货状态
    		newSalesOrderParamVO.setReturnStatusId(paramVO.getReturnStatusId());
    		// 订单-退货状态
    		newSalesOrderParamVO.setPaymentStatusId(paramVO.getPaymentStatusId());
        	/// 活动查询条件
    		newSalesOrderParamVO.setActivitySearch(paramVO.getCodeOrName());
    		// 消费者id
    		newSalesOrderParamVO.setCustomerId(paramVO.getCustomerId());
    		// 查询销售订单信息
        	ExecuteResult<List<NewSalesOrderVO>> queryListResult = newSalesOrderExportService.queryList(newSalesOrderParamVO);
        	if(Utility.isNotEmpty(queryListResult.getResult())){
				List<NewSalesOrderVO> queryResult = queryListResult.getResult();
				// 销售订单Map
				HashMap<Long, NewSalesOrderVO> newSalesOrderMap = new HashMap<>();
				// 销售订单id集合
				HashSet<Long> newSalesOrderIdSet = new HashSet<>();
				for(NewSalesOrderVO newSalesOrderVO : queryResult) {
					newSalesOrderIdSet.add(newSalesOrderVO.getSalesOrderId());
					newSalesOrderMap.put(newSalesOrderVO.getSalesOrderId(), newSalesOrderVO);
				}
				List<NewSalesReportVO> resultList = new ArrayList<>();
				// 包含SN和车架号查询条件
				if(Utility.isNotEmpty(newSalesOrderGoodsList)) {
					for (NewSalesOrderGoodsVO newSalesOrderGoods : newSalesOrderGoodsList) {
						if(newSalesOrderMap.containsKey(newSalesOrderGoods.getSalesOrderId())) {
							NewSalesOrderVO newSalesOrderVO = newSalesOrderMap.get(newSalesOrderGoods.getSalesOrderId());
							NewSalesReportVO newSalesReportVO = (NewSalesReportVO)BeanUtil.copyPropertes(NewSalesReportVO.class, newSalesOrderVO);
							// SN
							newSalesReportVO.setGoodsSn(newSalesOrderGoods.getGoodsSn());
							// 车架号
							newSalesReportVO.setGoodsFrameNumber(newSalesOrderGoods.getGoodsFrameNumber());
							// 商品名称
							newSalesReportVO.setGoodsName(newSalesOrderGoods.getGoodsName());
							// 商品三级级分类名称
							newSalesReportVO.setThirdCategoryName(newSalesOrderGoods.getThirdCategoryName());
							// 商品实销单价
							newSalesReportVO.setActualPriceValue(Utility.bigDecimalToString(newSalesOrderGoods.getActualPrice()));
							resultList.add(newSalesReportVO);
						}
					}
				} else {
					// 销售订单id集合
					List<Long> newSalesOrderIdList = new ArrayList<>(newSalesOrderIdSet);
					NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
					newSalesOrderGoodsVO.setSalesOrderIdList(newSalesOrderIdList);
					// 查询销售订单商品信息
					ExecuteResult<List<NewSalesOrderGoodsVO>> newSalesOrderGoodsExecuteResult = newSalesOrderGoodsExportService.queryList(newSalesOrderGoodsVO);
					newSalesOrderGoodsList = newSalesOrderGoodsExecuteResult.getResult();
					if(Utility.isNotEmpty(newSalesOrderGoodsList)) {
						for (NewSalesOrderGoodsVO newSalesOrderGoods : newSalesOrderGoodsList) {
							if(newSalesOrderMap.containsKey(newSalesOrderGoods.getSalesOrderId())) {
								NewSalesOrderVO newSalesOrderVO = newSalesOrderMap.get(newSalesOrderGoods.getSalesOrderId());
								NewSalesReportVO newSalesReportVO = (NewSalesReportVO)BeanUtil.copyPropertes(NewSalesReportVO.class, newSalesOrderVO);
								// SN
								newSalesReportVO.setGoodsSn(newSalesOrderGoods.getGoodsSn());
								// 车架号
								newSalesReportVO.setGoodsFrameNumber(newSalesOrderGoods.getGoodsFrameNumber());
								// 商品名称
								newSalesReportVO.setGoodsName(newSalesOrderGoods.getGoodsName());
								// 商品三级级分类名称
								newSalesReportVO.setThirdCategoryName(newSalesOrderGoods.getThirdCategoryName());
								// 商品实销单价
								newSalesReportVO.setActualPriceValue(Utility.bigDecimalToString(newSalesOrderGoods.getActualPrice()));
								resultList.add(newSalesReportVO);
							}
						}
					}
				}
				// 门店销售报表信息
				if(Utility.isNotEmpty(resultList)) {
					result.setResult(resultList);
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
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListSalesReport", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListSalesReport",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<PageInfo<ActivityDataVO>> statisticsPageActivityReport(ActivityReportPramVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageActivityReport",  JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<ActivityDataVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
            NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
            // 区域id集合
            newSalesOrderVO.setOrgList(paramVO.getOrgIdList());
            // 门店id集合
            newSalesOrderVO.setStoreList(paramVO.getStoreIdList());
            // 活动编号
            newSalesOrderVO.setActivityNumber(paramVO.getActivityNumber());
            // 活动名称
            newSalesOrderVO.setActivityName(paramVO.getActivityName());
            // 门店名称
            newSalesOrderVO.setStoreName(paramVO.getStoreName());
            // 门店编号
            newSalesOrderVO.setStoreNumber(paramVO.getStoreNumber());
            // 活动id
            newSalesOrderVO.setActivityId(paramVO.getActivityId());
            // 订单状态-已完成
            newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
            // 订单退货状态-未退货
            newSalesOrderVO.setReturnStatusId(OrderConstants.ORDER_DICT_RUTURNNO);
            // 查询参加活动的销售订单信息
            ExecuteResult<List<NewSalesOrderVO>> executeNewSalesOrderVO = newSalesOrderExportService.queryListActivityData(newSalesOrderVO);
            if(Utility.isNotEmpty(executeNewSalesOrderVO.getResult())) {
            	List<NewSalesOrderVO> listNewSalesOrderVO = executeNewSalesOrderVO.getResult();
            	List<Long> salesOrderIdList = new ArrayList<>();
            	Map<Long, NewSalesOrderVO> salesOrderMap = new HashMap<>();
            	for (NewSalesOrderVO newSalesOrder : listNewSalesOrderVO) {
            		salesOrderMap.put(newSalesOrder.getSalesOrderId(), newSalesOrder);
            		salesOrderIdList.add(newSalesOrder.getSalesOrderId());
				}
            	NewSalesOrderGoodsVO newSalesOrderGoodsParamVO = new NewSalesOrderGoodsVO();
            	newSalesOrderGoodsParamVO.setSalesOrderIdList(salesOrderIdList);
            	// 商品一级分类id-整车
            	if(Utility.isEmpty(paramVO.getAllFlag())) {
            		newSalesOrderGoodsParamVO.setFirstCategoryId(OrderConstants.GOODS_FIRST_CATEGORY_ID);
            	}
            	ExecuteResult<PageInfo<NewSalesOrderGoodsVO>> executeSalesOrderGoods = newSalesOrderGoodsExportService.queryListByPage(newSalesOrderGoodsParamVO, page);
            	PageInfo pageInfo = executeSalesOrderGoods.getResult();
				if(Utility.isNotEmpty(pageInfo)) {
            		List<NewSalesOrderGoodsVO> listSalesOrderGoodsVO = pageInfo.getList();
            		List<ActivityDataVO> listResult = new ArrayList<>();
            		for (NewSalesOrderGoodsVO newSalesOrderGoodsVO : listSalesOrderGoodsVO) {
            			ActivityDataVO activityDataVO = new ActivityDataVO();
            			NewSalesOrderVO newSalesOrder = salesOrderMap.get(newSalesOrderGoodsVO.getSalesOrderId());
            			// 订单编号
            			activityDataVO.setSalesOrderNumber(newSalesOrder.getSalesOrderNumber());
            			// 活动编号
            			activityDataVO.setActivityCode(newSalesOrder.getActivityNumber());
            			// 活动名称
            			activityDataVO.setActivityName(newSalesOrder.getActivityName());
            			// 大区
            			activityDataVO.setFirstOrgName(newSalesOrder.getFirstOrgName());
            			// 区域
            			activityDataVO.setSecondOrgName(newSalesOrder.getSecondOrgName());
            			// 城市
            			activityDataVO.setThirdOrgName(newSalesOrder.getThirdOrgName());
            			// 门店编号
            			activityDataVO.setStoreNumber(newSalesOrder.getStoreNumber());
            			// 门店名称
            			activityDataVO.setStoreName(newSalesOrder.getStoreName());
            			// 销售上报时间
            			activityDataVO.setCreateDate(newSalesOrder.getCreateDate());
            			// 车架号
            			activityDataVO.setGoodsFrameNumber(newSalesOrderGoodsVO.getGoodsFrameNumber());
            			// SN号
            			activityDataVO.setGoodsSn(newSalesOrderGoodsVO.getGoodsSn());
            			// 商品名称
            			activityDataVO.setGoodsName(newSalesOrderGoodsVO.getGoodsName());
            			// 消费者来源
            			activityDataVO.setCustomerSourceValue(newSalesOrder.getCustomerSourceName());
            			// 使用活动码
            			activityDataVO.setCouponCode(newSalesOrder.getCouponCode());
            			// 活动码类型
            			FeignCouponBatchVO feignCouponBatchVO = feignCouponExportService.queryCouponBatch(newSalesOrder.getActivityBrachId());
            			if(Utility.isNotEmpty(feignCouponBatchVO)) {
            				activityDataVO.setCouponType(dictValueExportService.getDictValueNameById(OrderConstants.ACT_DICT_COUPON_TYPE, feignCouponBatchVO.getCouponType()));
            			}
            			// 活动图片
            			activityDataVO.setActivityPicture(newSalesOrder.getActivityPicture());
            			// 备注
            			activityDataVO.setOrderRemark(newSalesOrder.getOrderRemark());
            			listResult.add(activityDataVO);
					}
            		if(Utility.isNotEmpty(listResult)) {
            			pageInfo.setList(listResult);
                		result.setResult(pageInfo);
                		flag = true;
            		}
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
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsPageActivityReport", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageActivityReport",  JSONObject.toJSONString(result));
        return result;
	}
	
	@Override
	public ExecuteResult<List<ActivityDataVO>> statisticsListActivityReport(ActivityReportPramVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsListActivityReport",  JSONObject.toJSONString(paramVO));
        ExecuteResult<List<ActivityDataVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
            NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
            // 区域id集合
            newSalesOrderVO.setOrgList(paramVO.getOrgIdList());
            // 门店id集合
            newSalesOrderVO.setStoreList(paramVO.getStoreIdList());
            // 活动编号
            newSalesOrderVO.setActivityNumber(paramVO.getActivityNumber());
            // 活动名称
            newSalesOrderVO.setActivityName(paramVO.getActivityName());
            // 门店名称
            newSalesOrderVO.setStoreName(paramVO.getStoreName());
            // 门店编号
            newSalesOrderVO.setStoreNumber(paramVO.getStoreNumber());
            // 活动id
            newSalesOrderVO.setActivityId(paramVO.getActivityId());
            // 订单状态-已完成
            newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
            // 订单退货状态-未退货
            newSalesOrderVO.setReturnStatusId(OrderConstants.ORDER_DICT_RUTURNNO);
            // 查询参加活动的销售订单信息
            ExecuteResult<List<NewSalesOrderVO>> executeNewSalesOrderVO = newSalesOrderExportService.queryListActivityData(newSalesOrderVO);
            if(Utility.isNotEmpty(executeNewSalesOrderVO.getResult())) {
            	List<NewSalesOrderVO> listNewSalesOrderVO = executeNewSalesOrderVO.getResult();
            	List<Long> salesOrderIdList = new ArrayList<>();
            	Map<Long, NewSalesOrderVO> salesOrderMap = new HashMap<>();
            	for (NewSalesOrderVO newSalesOrder : listNewSalesOrderVO) {
            		salesOrderMap.put(newSalesOrder.getSalesOrderId(), newSalesOrder);
            		salesOrderIdList.add(newSalesOrder.getSalesOrderId());
				}
            	NewSalesOrderGoodsVO newSalesOrderGoodsParamVO = new NewSalesOrderGoodsVO();
            	newSalesOrderGoodsParamVO.setSalesOrderIdList(salesOrderIdList);
            	// 商品一级分类id-整车
            	if(Utility.isEmpty(paramVO.getAllFlag())) {
            		newSalesOrderGoodsParamVO.setFirstCategoryId(OrderConstants.GOODS_FIRST_CATEGORY_ID);
            	}
            	ExecuteResult<List<NewSalesOrderGoodsVO>> executeSalesOrderGoods = newSalesOrderGoodsExportService.queryList(newSalesOrderGoodsParamVO);
            	List<NewSalesOrderGoodsVO> listSalesOrderGoodsVO = executeSalesOrderGoods.getResult();
				if(Utility.isNotEmpty(listSalesOrderGoodsVO)) {
            		List<ActivityDataVO> listResult = new ArrayList<>();
            		for (NewSalesOrderGoodsVO newSalesOrderGoodsVO : listSalesOrderGoodsVO) {
            			ActivityDataVO activityDataVO = new ActivityDataVO();
            			NewSalesOrderVO newSalesOrder = salesOrderMap.get(newSalesOrderGoodsVO.getSalesOrderId());
            			// 订单编号
            			activityDataVO.setSalesOrderNumber(newSalesOrder.getSalesOrderNumber());
            			// 活动编号
            			activityDataVO.setActivityCode(newSalesOrder.getActivityNumber());
            			// 活动名称
            			activityDataVO.setActivityName(newSalesOrder.getActivityName());
            			// 大区
            			activityDataVO.setFirstOrgName(newSalesOrder.getFirstOrgName());
            			// 区域
            			activityDataVO.setSecondOrgName(newSalesOrder.getSecondOrgName());
            			// 城市
            			activityDataVO.setThirdOrgName(newSalesOrder.getThirdOrgName());
            			// 门店编号
            			activityDataVO.setStoreNumber(newSalesOrder.getStoreNumber());
            			// 门店名称
            			activityDataVO.setStoreName(newSalesOrder.getStoreName());
            			// 销售上报时间
            			activityDataVO.setCreateDate(newSalesOrder.getCreateDate());
            			// 车架号
            			activityDataVO.setGoodsFrameNumber(newSalesOrderGoodsVO.getGoodsFrameNumber());
            			// SN号
            			activityDataVO.setGoodsSn(newSalesOrderGoodsVO.getGoodsSn());
            			// 商品名称
            			activityDataVO.setGoodsName(newSalesOrderGoodsVO.getGoodsName());
            			// 消费者来源
            			activityDataVO.setCustomerSourceValue(newSalesOrder.getCustomerSourceName());
            			// 使用活动码
            			activityDataVO.setCouponCode(newSalesOrder.getCouponCode());
            			// 活动码类型
            			FeignCouponBatchVO feignCouponBatchVO = feignCouponExportService.queryCouponBatch(newSalesOrder.getActivityBrachId());
            			if(Utility.isNotEmpty(feignCouponBatchVO)) {
            				activityDataVO.setCouponType(dictValueExportService.getDictValueNameById(OrderConstants.ACT_DICT_COUPON_TYPE, feignCouponBatchVO.getCouponType()));
            			}
            			// 活动图片
            			activityDataVO.setActivityPicture(newSalesOrder.getActivityPicture());
            			// 备注
            			activityDataVO.setOrderRemark(newSalesOrder.getOrderRemark());
            			// 门店渠道编码
            			activityDataVO.setStoreChannelNumber(newSalesOrder.getStoreChannelNumber());
            			// 合伙人编码
            			activityDataVO.setPartnerNumber(newSalesOrder.getPartnerNumber());
            			listResult.add(activityDataVO);
					}
            		if(Utility.isNotEmpty(listResult)) {
                		result.setResult(listResult);
                		flag = true;
            		}
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
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListActivityReport", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListActivityReport",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<SalesVolumeVO> statisticsOrderAmount(StatisticsAnalysisParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsOrderAmount",  JSONObject.toJSONString(paramVO));
        ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
        	// 城市id集合
        	newSalesOrderVO.setOrgList(paramVO.getOrgIdList());
        	// 门店id集合
        	newSalesOrderVO.setStoreList(paramVO.getStoreIdList());
        	// 创建人id
        	newSalesOrderVO.setCreateUserId(paramVO.getUserId());
        	// 开始时间
        	newSalesOrderVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderVO.setEndCreateDate(paramVO.getEndDate());
        	// 订单状态-已完成
        	newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
        	// 查询订单销量/销售金额
        	ExecuteResult<SalesVolumeVO> executeSalesVolumeVO = newSalesOrderExportService.queryCountAmount(newSalesOrderVO);
        	SalesVolumeVO salesVolumeResult = executeSalesVolumeVO.getResult();
        	// 查询销售订单id集合
        	ExecuteResult<List<Long>> salesOrderIdList = newSalesOrderExportService.queryListSalesOrderId(newSalesOrderVO);
        	if(Utility.isNotEmpty(salesOrderIdList.getResult())) {
	        	// 参数vo
	        	NewSalesOrderGoodsVO newSalesOrderGoodsParamVO = new NewSalesOrderGoodsVO();
	        	// 销售订单id集合
	        	newSalesOrderGoodsParamVO.setSalesOrderIdList(salesOrderIdList.getResult());
	        	// 商品一级分类id-整车
	        	newSalesOrderGoodsParamVO.setVehicleCategoryId(OrderConstants.GOODS_FIRST_CATEGORY_ID);
	        	// 商品一级分类id-周边
	        	newSalesOrderGoodsParamVO.setMerchCategoryId(OrderConstants.MERCH_CATEGORY_ID);
	        	// 查询整车/周边销量
	        	ExecuteResult<SalesVolumeVO> executevehicleTotal = newSalesOrderGoodsExportService.queryCountTotal(newSalesOrderGoodsParamVO);
	        	SalesVolumeVO salesVolumeVO = executevehicleTotal.getResult();
	        	if(Utility.isNotEmpty(salesVolumeVO)) {
	        		// 整车销量
	        		salesVolumeResult.setVehicleTotal(salesVolumeVO.getVehicleTotal());
	        		// 周边销量
	        		salesVolumeResult.setMerchTotal(salesVolumeVO.getMerchTotal());
	        	}
	        	NewReturnOrderVO newReturnOrderParamVO = new NewReturnOrderVO();
	        	// 城市id集合
	        	newReturnOrderParamVO.setOrgList(paramVO.getOrgIdList());
	        	// 门店id集合
	        	newReturnOrderParamVO.setStoreList(paramVO.getStoreIdList());
	        	// 门店id
	        	newReturnOrderParamVO.setStoreId(Utility.stringToInteger(paramVO.getStoreIds()));
	        	// 创建人id
	        	newReturnOrderParamVO.setCreateUserId(paramVO.getUserId());
	        	// 开始时间
	        	newReturnOrderParamVO.setStartCreateDate(paramVO.getStartDate());
	        	// 结束时间
	        	newReturnOrderParamVO.setEndCreateDate(paramVO.getEndDate());
	        	// 查询退货金额
//	        	ExecuteResult<BigDecimal> returnAmount = newReturnOrderExportService.queryReturnAmount(newReturnOrderParamVO);
//	        	salesVolumeResult.setReturnAmountTotal(returnAmount.getResult());
	        	// 查询退货订单统计信息
	        	ExecuteResult<SalesVolumeVO> executeReturnSalesVolume = newReturnOrderExportService.queryCountAmount(newReturnOrderParamVO);
	        	SalesVolumeVO returnSalesVolumeVO = executeReturnSalesVolume.getResult();
	        	if(Utility.isNotEmpty(returnSalesVolumeVO)) {
	        		// 退货金额
	        		salesVolumeResult.setReturnAmountTotal(returnSalesVolumeVO.getReturnAmountTotal());
	        		// 退货订单总数
	        		salesVolumeResult.setReturnOrderTotal(returnSalesVolumeVO.getReturnOrderTotal());
	        		BigDecimal validSalesAmountTotal = (salesVolumeResult.getSalesAmountTotal()).subtract(returnSalesVolumeVO.getReturnAmountTotal());
	        		// 有效销售总额
	        		salesVolumeResult.setValidSalesAmountValueTotal(Utility.bigDecimalToString(validSalesAmountTotal));
	        		// 有效销售总额(万元)
	        		BigDecimal wsalesAmountTotalTemp = validSalesAmountTotal.divide(new BigDecimal(10000));
	        		String str = Utility.bigDecimalToString(wsalesAmountTotalTemp.setScale(1, BigDecimal.ROUND_HALF_UP));
	        		salesVolumeResult.setWvalidSalesAmountValueTotal(str.substring(0, str.length()-1) + OrderConstants.MONEY_UNIT);
	        	}
        		result.setResult(salesVolumeResult);
        		flag = true;
        	} else {
        		result.setResult(salesVolumeResult);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsOrderAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsOrderAmount",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<SalesVolumeVO> statisticsToDayAmount(StatisticsAnalysisParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsToDayAmount",  JSONObject.toJSONString(paramVO));
        ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
        try {
        	NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
        	// 当前时间
        	Date nowDate = new Date();
        	// 门店id
        	newSalesOrderVO.setStoreId(Utility.stringToInteger(paramVO.getStoreStr()));
        	// 创建人id
        	newSalesOrderVO.setCreateUserId(paramVO.getUserId());
        	// 开始时间
        	newSalesOrderVO.setStartCreateDate(nowDate);
        	// 结束时间
        	newSalesOrderVO.setEndCreateDate(nowDate);
        	// 订单状态-已完成
        	newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
        	// 查询订单销量/销售金额
        	result = newSalesOrderExportService.queryCountAmount(newSalesOrderVO);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsToDayAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsToDayAmount",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> statisticsPageGoodsVolume(StatisticsAnalysisParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageGoodsVolume",  JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
        	// 城市id集合
        	newSalesOrderVO.setOrgList(paramVO.getOrgIdList());
        	// 门店id集合
        	newSalesOrderVO.setStoreList(paramVO.getStoreIdList());
        	// 创建人id
        	newSalesOrderVO.setCreateUserId(paramVO.getUserId());
        	// 开始时间
        	newSalesOrderVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderVO.setEndCreateDate(paramVO.getEndDate());
        	// 订单状态-已完成
        	newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
        	// 查询销售订单id集合
        	ExecuteResult<List<Long>> salesOrderIdList = newSalesOrderExportService.queryListSalesOrderId(newSalesOrderVO);
        	if(Utility.isNotEmpty(salesOrderIdList.getResult())) {
        		// 参数vo
	        	NewSalesOrderGoodsVO newSalesOrderGoodsParamVO = new NewSalesOrderGoodsVO();
	        	// 销售订单id集合
	        	newSalesOrderGoodsParamVO.setSalesOrderIdList(salesOrderIdList.getResult());
	        	// 商品一级大类id
	        	newSalesOrderGoodsParamVO.setFirstCategoryId(paramVO.getGoodsFirstCategoryId());
	        	// 排序标记
	        	newSalesOrderGoodsParamVO.setSortFlag(paramVO.getSortFlag());
	        	result = newSalesOrderGoodsExportService.queryPageGoodsVolume(newSalesOrderGoodsParamVO, page);
	        	flag = true;
        	}
        	if(!flag) {
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsPageGoodsVolume", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageGoodsVolume",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> statisticsPageGoodsAmount(StatisticsAnalysisParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageGoodsAmount",  JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
        	// 门店id
        	newSalesOrderVO.setStoreId(Utility.stringToInteger(paramVO.getStoreStr()));
        	// 创建人id
        	newSalesOrderVO.setCreateUserId(paramVO.getUserId());
        	// 开始时间
        	newSalesOrderVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderVO.setEndCreateDate(paramVO.getEndDate());
        	// 订单状态-已完成
        	newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
        	// 查询销售订单id集合
        	ExecuteResult<List<Long>> salesOrderIdList = newSalesOrderExportService.queryListSalesOrderId(newSalesOrderVO);
        	if(Utility.isNotEmpty(salesOrderIdList.getResult())) {
        		// 参数vo
	        	NewSalesOrderGoodsVO newSalesOrderGoodsParamVO = new NewSalesOrderGoodsVO();
	        	// 销售订单id集合
	        	newSalesOrderGoodsParamVO.setSalesOrderIdList(salesOrderIdList.getResult());
	        	// 商品一级大类id
	        	newSalesOrderGoodsParamVO.setFirstCategoryId(paramVO.getGoodsFirstCategoryId());
	        	// 排序标记
	        	newSalesOrderGoodsParamVO.setSortFlag(paramVO.getSortFlag());
	        	result = newSalesOrderGoodsExportService.queryPageGoodsVolume(newSalesOrderGoodsParamVO, page);
	        	flag = true;
        	}
        	if(!flag) {
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsPageGoodsAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageGoodsAmount",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisticsGoodsCategoryVO>> statisticsPageVehicleAmount(StatisticsAnalysisParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageVehicleAmount",  JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<StatisticsGoodsCategoryVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
        	// 城市id集合
        	newSalesOrderVO.setOrgList(paramVO.getOrgIdList());
        	// 门店id集合
        	if(Utility.isNotEmpty(paramVO.getStoreIds())) { // 查询条件(门店)不为空
    			List<Integer> storeIdList = Utility.stringToList(paramVO.getStoreIds());
    			if(Utility.isNotEmpty(paramVO.getStoreIdList())) {
    				if(Utility.isNotEmpty(paramVO.getStoreIdList())) {
    					storeIdList.retainAll(paramVO.getStoreIdList());
    				}
    			}
    			newSalesOrderVO.setStoreList(storeIdList);
    		} else {
    			newSalesOrderVO.setStoreList(paramVO.getStoreIdList());
    		}
        	// 开始时间
        	newSalesOrderVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderVO.setEndCreateDate(paramVO.getEndDate());
        	// 订单状态-已完成
        	newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
        	// 消费者来源
        	newSalesOrderVO.setCustomerSourceId(paramVO.getSourceId());
        	// 查询销售订单信息
        	ExecuteResult<List<NewSalesOrderVO>> executeSalesOrderVO = newSalesOrderExportService.queryList(newSalesOrderVO);
        	List<NewSalesOrderVO> listSalesOrderVO = executeSalesOrderVO.getResult();
        	List<StatisticsGoodsCategoryVO> listResult = new ArrayList<>();
        	if(Utility.isNotEmpty(listSalesOrderVO)) {
        		Map<Integer, NewSalesOrderVO> mapNewSalesOrderVO = new HashMap<>();
        		HashSet<Integer> setStoreId = new HashSet<>();
        		for(NewSalesOrderVO newSalesOrder : listSalesOrderVO) {
        			setStoreId.add(newSalesOrder.getStoreId());
        			mapNewSalesOrderVO.put(newSalesOrder.getStoreId(), newSalesOrder);
        		}
        		// 参数vo
	        	NewSalesOrderGoodsVO newSalesOrderGoodsParamVO = new NewSalesOrderGoodsVO();
        		// 颜色
	        	newSalesOrderGoodsParamVO.setGoodsAttributeId(paramVO.getGoodsAttributeId());
	        	// 商品一级大类id-整车
	        	newSalesOrderGoodsParamVO.setFirstCategoryId(OrderConstants.GOODS_FIRST_CATEGORY_ID);
	        	// 商品分类维度
	        	Integer goodsCategoryGrade = paramVO.getGoodsCategoryGrade();
	    		if(Utility.isNotEmpty(goodsCategoryGrade)) {
	    			switch (goodsCategoryGrade) {
	    			case 2:
	    				newSalesOrderGoodsParamVO.setSecondCategoryId(paramVO.getGoodsCategoryId());
	    				break;
	    			case 3:
	    				newSalesOrderGoodsParamVO.setThirdCategoryId(paramVO.getGoodsCategoryId());
	    				break;
	    			default:
	    				newSalesOrderGoodsParamVO.setFirstCategoryId(paramVO.getGoodsCategoryId());
	    				break;
	    			}
	    		}
        		for(Integer storeId : setStoreId) {
        			// 机构维度
        			NewSalesOrderVO newSalesOrder = null;
        			if(mapNewSalesOrderVO.containsKey(storeId)) {
        				newSalesOrder = mapNewSalesOrderVO.get(storeId);
        			}
	        		// 门店id
	        		newSalesOrderVO.setStoreId(storeId);
		        	// 查询销售订单id集合
		        	ExecuteResult<List<Long>> salesOrderIdList = newSalesOrderExportService.queryListSalesOrderId(newSalesOrderVO);
		        	if(Utility.isNotEmpty(salesOrderIdList.getResult())) {
			        	// 销售订单id集合
			        	newSalesOrderGoodsParamVO.setSalesOrderIdList(salesOrderIdList.getResult());
			        	// 查询有效订单整车分类统计
			        	ExecuteResult<List<StatisticsGoodsCategoryVO>> executeGoodsCategory = newSalesOrderGoodsExportService.queryListVehicleAmount(newSalesOrderGoodsParamVO);
			        	List<StatisticsGoodsCategoryVO> listGoodsCategory = executeGoodsCategory.getResult();
			        	if(Utility.isNotEmpty(listGoodsCategory)) {
			        		for(StatisticsGoodsCategoryVO statisticsGoodsCategoryVO : listGoodsCategory) {
			        			if(Utility.isNotEmpty(newSalesOrder)) {
				        			// 大区
				        			statisticsGoodsCategoryVO.setFirstOrgName(newSalesOrder.getFirstOrgName());
				        			// 区域
				        			statisticsGoodsCategoryVO.setSecondOrgName(newSalesOrder.getSecondOrgName());
				        			// 城市
				        			statisticsGoodsCategoryVO.setThirdOrgName(newSalesOrder.getThirdOrgName());
				        			// 合伙人
				        			statisticsGoodsCategoryVO.setPartnerName(newSalesOrder.getPartnerName());
				        			// 加盟商
				        			statisticsGoodsCategoryVO.setFranchiseeName(newSalesOrder.getTraderName());
				        			// 门店
				        			statisticsGoodsCategoryVO.setStoreName(newSalesOrder.getStoreName());
				        			// 消费者来源
				        			if(Utility.isNotEmpty(paramVO.getSourceId())) {
				        				statisticsGoodsCategoryVO.setCustomerSource(newSalesOrder.getCustomerSourceName());
				        			}
			        			}
			        			listResult.add(statisticsGoodsCategoryVO);
			        		}
			        	}
		        	}
        		}
        		if(Utility.isNotEmpty(listResult)) {
    				// 当前页
    				int pageNum = page.getPageNum();
    				// 一页条数
    				int pageSize = page.getPageSize();
    				// 总数
    				int totalCount=listResult.size();
    				// 总页数
    				int pageCount = 1;
    				if(totalCount > pageSize) {
    					pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);
    				}
    				// 起始条数
    				int start=(pageNum-1) * pageSize;
    				// 结尾条数
    				int end = pageNum==pageCount ? totalCount : pageNum * pageSize;
    				List<StatisticsGoodsCategoryVO> statisticsNotBuyList = null;
    				if (end > totalCount) {
    					statisticsNotBuyList = listResult.subList(start,totalCount);
    				} else {
    					statisticsNotBuyList = listResult.subList(start,end);
    				}
    				PageInfo<StatisticsGoodsCategoryVO> pageInfo = new PageInfo<>(statisticsNotBuyList);
    				pageInfo.setTotal(totalCount);
    				pageInfo.setPageNum(pageNum);
    				pageInfo.setPageSize(pageSize);
    				result.setResult(pageInfo);
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
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsPageVehicleAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageVehicleAmount",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<Map<String, Object>> statisticsListToDayOrderTrend(StatisticsAnalysisParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsListToDayOrderTrend", JSONObject.toJSONString(paramVO));
		ExecuteResult<Map<String, Object>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
        	// 城市id集合
        	newSalesOrderVO.setOrgList(paramVO.getOrgIdList());
        	// 门店id集合
        	newSalesOrderVO.setStoreList(paramVO.getStoreIdList());
        	// 创建人id
        	newSalesOrderVO.setCreateUserId(paramVO.getUserId());
        	// 开始时间
        	newSalesOrderVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderVO.setEndCreateDate(paramVO.getEndDate());
        	// 订单状态-已完成
        	newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
			// 查询今日订单/销售统计(图)信息
			ExecuteResult<List<Map<String, Object>>> executeResult = newSalesOrderExportService.queryListToDayOrderTrend(newSalesOrderVO);
			List<Map<String, Object>> executeResultListMap = executeResult.getResult();
			HashSet<Integer> hourSet = new HashSet<>();
			if(Utility.isNotEmpty(executeResultListMap)) {
				for (Map<String, Object> map : executeResultListMap) {
					hourSet.add(Integer.parseInt(Utility.objectToString(map.get("statisticsDay"))));
				}
				//设置日期格式
				SimpleDateFormat df = new SimpleDateFormat("HH");
				int newHour = Integer.parseInt(df.format(new Date()));
				for(int i = 0; i <= newHour; i++) {
					if(!hourSet.contains(Integer.valueOf(i))) {
						Map<String, Object> map = new HashMap<>();
						map.put("statisticsOrderAmount", 0);
						map.put("statisticsAmount", 0);
						map.put("statisticsValueAmount", "0.00");
						map.put("statisticsDay", i);
						map.put("statisticsDate", i);
						executeResultListMap.add(map);
					}
				}
				List<StatisticsFigureVO> statisticsFigure = JSONObject.parseArray(JSONObject.toJSONString(executeResult.getResult()),StatisticsFigureVO.class);
				List<StatisticsFigureVO> statisticsFigureList = new ArrayList<>();
				if(Utility.isNotEmpty(statisticsFigure)) {
					Collections.sort(statisticsFigure, new Comparator<StatisticsFigureVO>(){
			            public int compare(StatisticsFigureVO p1, StatisticsFigureVO p2) {
			                //按照StatisticsFigureVO的时间进行升序排列
			            	int a = Integer.valueOf(p1.getStatisticsDay());
			            	int b = Integer.valueOf(p2.getStatisticsDay());
			                if(a > b){
			                    return 1;
			                }
			                if(a == b){
			                    return 0;
			                }
			                return -1;
			            }
			        });
					for (StatisticsFigureVO statisticsFigureVO : statisticsFigure) {
						int time = Integer.valueOf(statisticsFigureVO.getStatisticsDay());
						//设置日期格式
						SimpleDateFormat sdf = new SimpleDateFormat("dd");
						int day = Integer.parseInt(sdf.format(new Date()));
						statisticsFigureVO.setStatisticsDate(time+":00");
						statisticsFigureVO.setStatisticsDay( time + "点");
						statisticsFigureVO.setPeriod(day + "日 " + time + ":00-" + (time+1) + ":00");
						String statisticsAmount = Utility.bigDecimalToString(statisticsFigureVO.getStatisticsAmount());
						statisticsFigureVO.setStatisticsValueAmount(statisticsAmount);
						// 销售总额(万)
						BigDecimal wsalesAmountTotalTemp = statisticsFigureVO.getStatisticsAmount().divide(new BigDecimal(10000));
						statisticsFigureVO.setWstatisticsAmount(wsalesAmountTotalTemp.setScale(1, BigDecimal.ROUND_HALF_UP));
						statisticsFigureList.add(statisticsFigureVO);
					}
					Map<String, Object> resultMap = new HashMap<>();
					// 订单总数 
					Integer orderTotal = 0;
					// 销量总值
					BigDecimal salesTotal = new BigDecimal(0);
					for (StatisticsFigureVO StatisticsFigure: statisticsFigureList) {
						orderTotal += StatisticsFigure.getStatisticsOrderAmount();
						salesTotal = salesTotal.add(StatisticsFigure.getStatisticsAmount());
					}
					// 销售订单总数
					resultMap.put("orderTotal", orderTotal);
					// 销售总额
					resultMap.put("salesTotal", salesTotal);
					// 销售总额(万)
					String wsalesTotal = Utility.bigDecimalToString(salesTotal.divide(new BigDecimal(10000)).setScale(1, BigDecimal.ROUND_HALF_UP));
					resultMap.put("wsalesTotal", wsalesTotal.substring(0, wsalesTotal.length()-1) + OrderConstants.MONEY_UNIT);
					resultMap.put("statisticsFigureList", statisticsFigureList);
					result.setResult(resultMap);
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
			result.setResultMessage(e.toString());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListToDayOrderTrend", e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListToDayOrderTrend", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<Map<String, Object>> statisticsOrderTrend(StatisticsAnalysisParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsOrderTrend", paramVO.toString());
		ExecuteResult<Map<String, Object>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			// 参数vo
			NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
        	// 城市id集合
        	newSalesOrderVO.setOrgList(paramVO.getOrgIdList());
        	// 门店id集合
        	newSalesOrderVO.setStoreList(paramVO.getStoreIdList());
        	// 创建人id
        	newSalesOrderVO.setCreateUserId(paramVO.getUserId());
        	// 开始时间
        	newSalesOrderVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderVO.setEndCreateDate(paramVO.getEndDate());
        	// 订单状态-已完成
        	newSalesOrderVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
			// 查询近7天/近30天订单/销售统计(图)信息
			ExecuteResult<List<Map<String, Object>>> executeResultOne = newSalesOrderExportService.statisticsOrderTrend(newSalesOrderVO);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = paramVO.getStartDate();
			Date endDate = paramVO.getEndDate();
			Long newDay = startDate.getTime();
			Long endDay = endDate.getTime();
			int day = (int) ((endDate.getTime() - startDate.getTime())/(24*60*60*1000))+1;
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			startCalendar.add(Calendar.DAY_OF_YEAR,-day);
			startDate = startCalendar.getTime();
			newSalesOrderVO.setStartCreateDate(startDate);// day天前开始时间
			endCalendar.setTime(endDate);
			endCalendar.add(Calendar.DAY_OF_YEAR,-day);
			newSalesOrderVO.setEndCreateDate(endCalendar.getTime());// day天前结束时间
			ExecuteResult<List<Map<String, Object>>> executeResultTwo = newSalesOrderExportService.statisticsOrderTrend(newSalesOrderVO);
			List<Map<String, Object>> executeMapOne = executeResultOne.getResult();
			if(Utility.isNotEmpty(executeMapOne)) {
				HashSet<Long> hourSet = new HashSet<>();
				for (Map<String, Object> map : executeMapOne) {
					if(map.containsKey("statisticsDate")) {
						long time = simpleDateFormat.parse(Utility.objectToString(map.get("statisticsDate"))).getTime();
						hourSet.add(time);
						String formatDate = simpleDateFormat.format(new Date(time));
						Date parse = simpleDateFormat.parse(formatDate);
						Calendar calendar = Calendar.getInstance(); // 获得一个日历
						calendar.setTime(parse);
						int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
						int dayNum = Integer.parseInt(formatDate.substring(formatDate.length()-2, formatDate.length()));
						map.put("statisticsDay", dayNum + "号");
						map.put("period", dayNum + "日" +OrderConstants.WEEK_DAYS[week]);
					}
				}
				while(newDay <= endDay) {
					if(!hourSet.contains(newDay)) {
						String formatDate = simpleDateFormat.format(new Date(newDay));
						Date parse = simpleDateFormat.parse(formatDate);
						Calendar calendar = Calendar.getInstance(); // 获得一个日历
						calendar.setTime(parse);
						int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
						int dayNum = Integer.parseInt(formatDate.substring(formatDate.length()-2, formatDate.length()));
						Map<String, Object> map = new HashMap<>();
						map.put("statisticsOrderAmount", 0);
						map.put("statisticsAmount", 0);
						map.put("statisticsValueAmount", "0.00");
						map.put("statisticsDate", formatDate);
						map.put("statisticsDay", dayNum + "号");
						map.put("period", dayNum + "日" +OrderConstants.WEEK_DAYS[week]);
						executeMapOne.add(map);
					}
					newDay += 24*60*60*1000;
				}
				List<StatisticsFigureVO> statisticsFigureOne = JSONObject.parseArray(JSONObject.toJSONString(executeMapOne), StatisticsFigureVO.class);
				if(Utility.isNotEmpty(statisticsFigureOne)) {
					Collections.sort(statisticsFigureOne, new Comparator<StatisticsFigureVO>(){
			            public int compare(StatisticsFigureVO p1, StatisticsFigureVO p2)  {
			                try {
				            	//按照StatisticsFigureVO的时间进行升序排列
				            	long a = simpleDateFormat.parse(p1.getStatisticsDate()).getTime();
				            	long b = simpleDateFormat.parse(p2.getStatisticsDate()).getTime();
				                if(a > b){
				                    return 1;
				                }
				                if(a == b){
				                    return 0;
				                }
				                return -1;
			                }catch (Exception e) {
			                	return 0;
							}
			            }
			        });
				}
				Map<String, Object> resultMap = new HashMap<>();
				// 订单总数 
				Integer orderTotal = 0;
				// 销量总值
				BigDecimal salesTotal = new BigDecimal(0);
				// 订单总数 
				Integer oldOrderTotal = 0;
				// 销量总值
				BigDecimal oldSalesTotal =  new BigDecimal(0);
				for (StatisticsFigureVO StatisticsFigure: statisticsFigureOne) {
					String statisticsAmount = Utility.bigDecimalToString(StatisticsFigure.getStatisticsAmount());
					orderTotal += StatisticsFigure.getStatisticsOrderAmount();
					salesTotal = salesTotal.add(StatisticsFigure.getStatisticsAmount());
					StatisticsFigure.setStatisticsValueAmount(statisticsAmount);
					// 销售总额(万)
					BigDecimal wstatisticsAmount = StatisticsFigure.getStatisticsAmount().divide(new BigDecimal(10000)).setScale(1, BigDecimal.ROUND_HALF_UP);
					StatisticsFigure.setWstatisticsAmount(wstatisticsAmount);
				}
				List<StatisticsFigureVO> statisticsFigureTwo = null;
				if(Utility.isNotEmpty(executeResultTwo.getResult())) {
					statisticsFigureTwo = JSONObject.parseArray(JSONObject.toJSONString(executeResultTwo.getResult()), StatisticsFigureVO.class);
					for (StatisticsFigureVO StatisticsFigure: statisticsFigureTwo) {
						oldOrderTotal += StatisticsFigure.getStatisticsOrderAmount();
						oldSalesTotal = oldSalesTotal.add(StatisticsFigure.getStatisticsAmount());
					}
				}
				String orderRatio = "100.00%";
				String salesRatio = "100.00%";
				int orderFlag = 1; // 1上升0下降
				int salesFlag = 1; // 1上升0下降
				// 订单总数比率
				if(oldOrderTotal != 0) {
					if(orderTotal < oldOrderTotal) {
						orderFlag = 0;
						orderRatio = String.valueOf(String.format("%.2f", 100*(((double)oldOrderTotal-orderTotal)/oldOrderTotal)))+"%";
					} else {
						orderRatio = String.valueOf(String.format("%.2f", 100*(((double)orderTotal-oldOrderTotal)/oldOrderTotal)))+"%";
					}
				}
				// 销量总值比率
				if(oldSalesTotal.compareTo(BigDecimal.ZERO) != 0) {
					if(salesTotal.compareTo(oldSalesTotal) == -1) {
						salesFlag = 0;
						salesRatio = String.valueOf(String.format("%.2f", 100*((oldSalesTotal.doubleValue()-salesTotal.doubleValue())/oldSalesTotal.doubleValue()))) + "%";
					} else {
						salesRatio = String.valueOf(String.format("%.2f", 100*((salesTotal.doubleValue()-oldSalesTotal.doubleValue())/oldSalesTotal.doubleValue()))) + "%";
					}
				}
				// 销售订单总数上升下降标记
				resultMap.put("orderflag", orderFlag);
				// 销售总额上升下降标记
				resultMap.put("salesflag", salesFlag);
				// 每天的销售订单总数/销售总额
				resultMap.put("statisticsFigureOne", statisticsFigureOne);
				// 销售订单总数
				resultMap.put("orderTotal", orderTotal);
				// 销售总额
				resultMap.put("salesTotal", Utility.bigDecimalToString(salesTotal));
				// 销售总额(万)
				String wsalesTotal = Utility.bigDecimalToString(salesTotal.divide(new BigDecimal(10000).setScale(1, BigDecimal.ROUND_HALF_UP)));
				resultMap.put("wsalesTotal", wsalesTotal.substring(0, wsalesTotal.length()-1) + OrderConstants.MONEY_UNIT);
				// 订单总数比率
				resultMap.put("orderRatio", orderRatio);
				// 销量总值比率
				resultMap.put("salesRatio", salesRatio);
				flag = true;
				result.setResult(resultMap);
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
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsOrderTrend", e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsOrderTrend", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisticsNotBuyVO>> statisticsPageNotBuy(NotBuyParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsListNotBuy",  JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<StatisticsNotBuyVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	// 参数vo
        	NewNotBuyVO newNotBuyParamVO = new NewNotBuyVO();

			//dealParamVO(paramVO);

        	// 城市id集合
        	newNotBuyParamVO.setOrgList(paramVO.getOrgIdList());
        	// 门店id集合
        	newNotBuyParamVO.setStoreList(paramVO.getStoreIdList());
        	// 开始时间
        	newNotBuyParamVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newNotBuyParamVO.setEndCreateDate(paramVO.getEndDate());
        	// 门店名称
        	newNotBuyParamVO.setStoreName(paramVO.getStoreName());
        	// 门店编号
        	newNotBuyParamVO.setStoreNumber(paramVO.getStoreNumber());

        	//大区
			newNotBuyParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newNotBuyParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newNotBuyParamVO.setThirdOrgId(paramVO.getThirdOrgId());

        	// 查询未购买上报统计信息
        	ExecuteResult<List<StatisticsNotBuyVO>> executeListNotBuy = newNotBuyExportService.queryListNotBuyTotal(newNotBuyParamVO);
        	// 参数vo
        	NewSalesOrderVO newSalesOrderParamVO = new NewSalesOrderVO();
        	// 城市id集合
        	newSalesOrderParamVO.setOrgList(paramVO.getOrgIdList());
        	// 门店id集合
        	newSalesOrderParamVO.setStoreList(paramVO.getStoreIdList());
        	// 订单状态
        	newSalesOrderParamVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_CANCLE);
        	// 开始时间
        	newSalesOrderParamVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderParamVO.setEndCreateDate(paramVO.getEndDate());
        	// 门店名称
        	newSalesOrderParamVO.setStoreName(paramVO.getStoreName());
        	// 门店编号
        	newSalesOrderParamVO.setStoreNumber(paramVO.getStoreNumber());

			//大区
			newSalesOrderParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newSalesOrderParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newSalesOrderParamVO.setThirdOrgId(paramVO.getThirdOrgId());

        	// 查询取消订单信息
        	ExecuteResult<List<StatisticsNotBuyVO>> executeListCancleTotal = newSalesOrderExportService.queryListCancleTotal(newSalesOrderParamVO);
        	Map<String, StatisticsNotBuyVO> notBuyMap = new HashMap<>();
        	List<StatisticsNotBuyVO> listNotBuy = executeListNotBuy.getResult();
        	List<StatisticsNotBuyVO> listCancleTotal = executeListCancleTotal.getResult();
        	List<StatisticsNotBuyVO> listResult = new ArrayList<>();


        	if(Utility.isNotEmpty(listNotBuy)) {
        		if(Utility.isNotEmpty(listCancleTotal)) {
        			for (StatisticsNotBuyVO statisticsNotBuyVO : listNotBuy) {
            			notBuyMap.put(statisticsNotBuyVO.getStoreName(), statisticsNotBuyVO);
    				}
        			for(StatisticsNotBuyVO statisticsNotBuyVO : listCancleTotal) {
        				String storeName = statisticsNotBuyVO.getStoreName();
        				if(notBuyMap.containsKey(storeName)) {
        					Integer totalAmount = notBuyMap.get(storeName).getNotBuyAmount() + statisticsNotBuyVO.getNotBuyAmount();
        					statisticsNotBuyVO.setNotBuyAmount(totalAmount);
        					notBuyMap.put(storeName, statisticsNotBuyVO);
        				} else {
        					notBuyMap.put(storeName, statisticsNotBuyVO);
        				}
        			}


        			for (StatisticsNotBuyVO statisticsNotBuyVO : notBuyMap.values()) {
                        //total = total+ statisticsNotBuyVO.getNotBuyAmount();
						//storeIDs = storeIDs+statisticsNotBuyVO.getStoreId()+",";
        				listResult.add(statisticsNotBuyVO);
        			}



        		} else {
        			listResult = listNotBuy;
        		}
        	} else {
        		listResult = listCancleTotal;
        	}
        	if(Utility.isNotEmpty(listResult)) {

				Collections.sort(listResult, new Comparator<StatisticsNotBuyVO>() {
					public int compare(StatisticsNotBuyVO s1, StatisticsNotBuyVO s2) {
						//降序
						int a = s1.getNotBuyAmount() - s2.getNotBuyAmount();
						if (a != 0) {
							return a > 0 ? -1 : 1;
						}
						//升序
						a = s1.getStoreNumber().compareTo(s2.getStoreNumber());
						if(a != 0){
							return a > 0 ? 1 : -1;
						}
						return 0;
					}
				});


        		// 当前页
				int pageNum = page.getPageNum();
				// 一页条数
				int pageSize = page.getPageSize();
				// 总数
				int totalCount = listResult.size();
				// 总页数
				int pageCount = 1;
				if(totalCount > pageSize) {
					pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);
				}
				// 起始条数
				int start=(pageNum-1) * pageSize;
				// 结尾条数
				int end = pageNum==pageCount ? totalCount : pageNum * pageSize;
				List<StatisticsNotBuyVO> StatisticsNotBuyList = null;
				if (end > totalCount) {
					StatisticsNotBuyList = listResult.subList(start,totalCount);
				} else {
					StatisticsNotBuyList = listResult.subList(start,end);
				}

				int total = 0;
				String storeIDs = "";
				for (StatisticsNotBuyVO statisticsNotBuyVO : listResult) {
					total = total+ statisticsNotBuyVO.getNotBuyAmount();
					storeIDs = storeIDs+statisticsNotBuyVO.getStoreId()+",";
				}
				//添加行：“全部”
				List<StatisticsNotBuyVO> statisticsNotBuyAllList = addNotByALL(StatisticsNotBuyList,total,storeIDs);

				PageInfo<StatisticsNotBuyVO> pageInfo = new PageInfo<>(statisticsNotBuyAllList);
				pageInfo.setTotal(totalCount);
				pageInfo.setPageNum(pageNum);
				pageInfo.setPageSize(pageSize);
				pageInfo.setLastPage(pageCount);

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
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListNotBuy", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListNotBuy",  JSONObject.toJSONString(result));
        return result;
	}

	/**
	 * 预处理 paramVO
	 * 如果orgstr不为空那么 内部用户，需要和筛选条件求交集
	 * @param paramVO
	 */
	private void dealParamVO(BaseParamVO paramVO){
		List<Integer> orgIdListCross = new ArrayList();

		//如果orgstr不为空那么 内部用户，需要和筛选条件求交集
		if(Utility.isNotEmpty(paramVO.getOrgStr())&&Utility.isNotEmpty(paramVO.getOrgIdList())&&
				Utility.isNotEmpty(paramVO.getOrgSearch())){
			List<Integer> orgList = paramVO.getOrgIdList();
			String orgSearch = paramVO.getOrgSearch();
			for(int i = 0; i <orgList.size();i++){
				Integer orgID = orgList.get(i);
				String orgIDStr = String.valueOf(orgID);
				if(orgSearch.indexOf(orgIDStr)!= -1){
					orgIdListCross.add(orgID);
				}
			}
			//如果求交集为空 那么 要赋予一个 -1 永远也查不到
			if(Utility.isEmpty(orgIdListCross)){
				orgIdListCross.add(-1);
			}
			paramVO.setOrgIdList(orgIdListCross);
		}
		//如果orgstr为空 同时storeStr不为空那么 是合伙人  把orgSearch赋予给orgStr和orgList
		if(Utility.isEmpty(paramVO.getOrgStr())&&Utility.isNotEmpty(paramVO.getStoreStr())){
			paramVO.setOrgStr(paramVO.getOrgSearch());
		}

	}

	/**
	 * 添加全部行
	 * @param statisticsNotBuyList
	 * @param total
	 * @return
	 */
	private List<StatisticsNotBuyVO> addNotByALL(List<StatisticsNotBuyVO> statisticsNotBuyList,int total,String storeIDs){
        //added by cuijd,添加门店渠道编码 及 全部信息
        Map<Integer,Integer> storeID2ListIndexMap = new HashMap();
        String ids = "";
        for(int i = 0 ; i < statisticsNotBuyList.size() ;i++){
            StatisticsNotBuyVO snbVO = statisticsNotBuyList.get(i);
            if("".equals(ids)){
                ids = String.valueOf(snbVO.getStoreId());
            }else{
                ids = ids+","+snbVO.getStoreId();
            }
            storeID2ListIndexMap.put(snbVO.getStoreId(),i);
        }

        AjaxInfo ainfo = feignStoreService.queryStoreList(ids);

        if(Utility.isNotEmpty(ainfo.getData())) {
            List<com.camelot.order.export.vo.feignvo.StoreVO> feignStore = JSONObject.parseArray(JSONObject.toJSONString(ainfo.getData()), com.camelot.order.export.vo.feignvo.StoreVO.class);
            for (StoreVO storeVO : feignStore) {
                if (Utility.isNotEmpty(storeVO)) {
                    Integer storeID = storeVO.getStoreId();
                    Integer listIndex = storeID2ListIndexMap.get(storeID);
                    if(listIndex == null){
                        continue;
                    }
                    StatisticsNotBuyVO snbVO = statisticsNotBuyList.get(listIndex);
                    snbVO.setStoreChannelNumber(storeVO.getStoreChannelNumber());

                }
            }
        }

        //添加全部
        StatisticsNotBuyVO statisticsNotBuyALLVO = new  StatisticsNotBuyVO();
        statisticsNotBuyALLVO.setStoreId(Constants.STORE_ID_ALL);
        statisticsNotBuyALLVO.setStoreNumber("全部");
        statisticsNotBuyALLVO.setStoreName("全部");
        statisticsNotBuyALLVO.setStoreChannelNumber("全部");
        statisticsNotBuyALLVO.setNotBuyAmount(total);
		statisticsNotBuyALLVO.setExtraData(storeIDs);
        List<StatisticsNotBuyVO> statisticsNotBuyListAll = new ArrayList();
        statisticsNotBuyListAll.add(0,statisticsNotBuyALLVO);
        statisticsNotBuyListAll.addAll(statisticsNotBuyList);


        return statisticsNotBuyListAll;
    }

	@Override
	public ExecuteResult<List<StatisticsSourceVO>> statisticsListCustomerSource(BaseParamVO paramVO) {
    	Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource",  JSONObject.toJSONString(paramVO));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	// 参数vo
        	NewNotBuyVO newNotBuyParamVO = new NewNotBuyVO();

			//dealParamVO(paramVO);
            // 城市id集合
            newNotBuyParamVO.setOrgList(paramVO.getOrgIdList());
            // 门店id
            if(Constants.STORE_ID_ALL.equals(paramVO.getStoreId())){
                newNotBuyParamVO.setStoreList(paramVO.getStoreIdList());
            }else{
                newNotBuyParamVO.setStoreId(paramVO.getStoreId());
            }
        	// 开始时间
        	newNotBuyParamVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newNotBuyParamVO.setEndCreateDate(paramVO.getEndDate());


			//大区
			newNotBuyParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newNotBuyParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newNotBuyParamVO.setThirdOrgId(paramVO.getThirdOrgId());
        	// 查询未购买原因分析(图)信息
        	ExecuteResult<List<StatisticsSourceVO>> executeListNotBuySource = newNotBuyExportService.queryListCustomerSource(newNotBuyParamVO);
        	// 参数vo
        	NewSalesOrderVO newSalesOrderParamVO = new NewSalesOrderVO();
            // 门店id
            if(Constants.STORE_ID_ALL.equals(paramVO.getStoreId())){
                newSalesOrderParamVO.setStoreList(paramVO.getStoreIdList());
            }else{
                newSalesOrderParamVO.setStoreId(paramVO.getStoreId());
            }
        	// 订单状态
        	newSalesOrderParamVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_CANCLE);
        	// 开始时间
        	newSalesOrderParamVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderParamVO.setEndCreateDate(paramVO.getEndDate());

			//大区
			newSalesOrderParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newSalesOrderParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newSalesOrderParamVO.setThirdOrgId(paramVO.getThirdOrgId());
        	// 查询取消订单原因分析(图)信息
        	ExecuteResult<List<StatisticsSourceVO>> executeListCancleSource = newSalesOrderExportService.queryListCustomerSource(newSalesOrderParamVO);
        	Map<String, StatisticsSourceVO> notBuyMap = new HashMap<>();
        	List<StatisticsSourceVO> listNotBuy = executeListNotBuySource.getResult();
        	List<StatisticsSourceVO> listCancleTotal = executeListCancleSource.getResult();
        	List<StatisticsSourceVO> listResult = new ArrayList<>();
        	if(Utility.isNotEmpty(listNotBuy)) {
        		if(Utility.isNotEmpty(listCancleTotal)) {
        			for (StatisticsSourceVO statisticsSourceVO : listNotBuy) {
            			notBuyMap.put(statisticsSourceVO.getName(), statisticsSourceVO);
    				}
        			for(StatisticsSourceVO statisticsSourceVO : listCancleTotal) {
        				String name = statisticsSourceVO.getName();
        				if(notBuyMap.containsKey(name)) {
        					Integer totalAmount = notBuyMap.get(name).getAmount() + statisticsSourceVO.getAmount();
        					statisticsSourceVO.setAmount(totalAmount);
        					notBuyMap.put(name, statisticsSourceVO);
        				} else {
        					notBuyMap.put(name, statisticsSourceVO);
        				}
        			}
        			for (StatisticsSourceVO statisticsSourceVO : notBuyMap.values()) {
        				listResult.add(statisticsSourceVO);
        			}
        		} else {
        			listResult = listNotBuy;
        		}
        	} else {
        		listResult = listCancleTotal;
        	}
        	if(Utility.isNotEmpty(listResult)) {
        		result.setResult(listResult);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<StatisticsSourceVO>> statisticsListNotBuyCause(BaseParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource",  JSONObject.toJSONString(paramVO));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	// 参数vo
        	NewNotBuyVO newNotBuyParamVO = new NewNotBuyVO();
			//dealParamVO(paramVO);
            // 城市id集合
            newNotBuyParamVO.setOrgList(paramVO.getOrgIdList());
            // 门店id
            if(Constants.STORE_ID_ALL.equals(paramVO.getStoreId())){
                newNotBuyParamVO.setStoreList(paramVO.getStoreIdList());
            }
            else{
                newNotBuyParamVO.setStoreId(paramVO.getStoreId());
            }
        	// 开始时间
        	newNotBuyParamVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newNotBuyParamVO.setEndCreateDate(paramVO.getEndDate());
			//大区
			newNotBuyParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newNotBuyParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newNotBuyParamVO.setThirdOrgId(paramVO.getThirdOrgId());
        	// 查询未购买原因分析(图)信息
        	ExecuteResult<List<StatisticsSourceVO>> executeListNotBuyCause = newNotBuyExportService.queryListNotBuyCause(newNotBuyParamVO);
        	// 参数vo
        	NewSalesOrderVO newSalesOrderParamVO = new NewSalesOrderVO();
            // 门店id
            if(Constants.STORE_ID_ALL.equals(paramVO.getStoreId())){
                newSalesOrderParamVO.setStoreList(paramVO.getStoreIdList());
            }else{
                newSalesOrderParamVO.setStoreId(paramVO.getStoreId());
            }
        	// 订单状态
        	newSalesOrderParamVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_CANCLE);
        	// 开始时间
        	newSalesOrderParamVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderParamVO.setEndCreateDate(paramVO.getEndDate());
			//大区
			newSalesOrderParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newSalesOrderParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newSalesOrderParamVO.setThirdOrgId(paramVO.getThirdOrgId());
        	// 查询取消原因分析(图)信息
        	ExecuteResult<List<StatisticsSourceVO>> executeListCancleCause = newSalesOrderExportService.queryListNotBuyCause(newSalesOrderParamVO);
        	Map<String, StatisticsSourceVO> notBuyMap = new HashMap<>();
        	List<StatisticsSourceVO> listNotBuy = executeListNotBuyCause.getResult();
        	List<StatisticsSourceVO> listCancleTotal = executeListCancleCause.getResult();
        	List<StatisticsSourceVO> listResult = new ArrayList<>();
        	if(Utility.isNotEmpty(listNotBuy)) {
        		if(Utility.isNotEmpty(listCancleTotal)) {
        			for (StatisticsSourceVO statisticsSourceVO : listNotBuy) {
            			notBuyMap.put(statisticsSourceVO.getName(), statisticsSourceVO);
    				}
        			for(StatisticsSourceVO statisticsSourceVO : listCancleTotal) {
        				String name = statisticsSourceVO.getName();
        				if(notBuyMap.containsKey(name)) {
        					Integer totalAmount = notBuyMap.get(name).getAmount() + statisticsSourceVO.getAmount();
        					statisticsSourceVO.setAmount(totalAmount);
        					notBuyMap.put(name, statisticsSourceVO);
        				} else {
        					notBuyMap.put(name, statisticsSourceVO);
        				}
        			}
        			for (StatisticsSourceVO statisticsSourceVO : notBuyMap.values()) {
        				listResult.add(statisticsSourceVO);
        			}
        		} else {
        			listResult = listNotBuy;
        		}
        	} else {
        		listResult = listCancleTotal;
        	}
        	if(Utility.isNotEmpty(listResult)) {
        		result.setResult(listResult);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<PageInfo<NotBuyCustomerVO>> statisticsPageNotBuyCustomer(BaseParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageNotBuyCustomer",  JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<NotBuyCustomerVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	// 参数vo
        	NewNotBuyVO newNotBuyParamVO = new NewNotBuyVO();
			//dealParamVO(paramVO);
            // 门店id
            if(Constants.STORE_ID_ALL.equals(paramVO.getStoreId())){
                newNotBuyParamVO.setStoreList(paramVO.getStoreIdList());
            }else{
                newNotBuyParamVO.setStoreId(paramVO.getStoreId());
            }
        	// 开始时间
        	newNotBuyParamVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newNotBuyParamVO.setEndCreateDate(paramVO.getEndDate());

			//大区
			newNotBuyParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newNotBuyParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newNotBuyParamVO.setThirdOrgId(paramVO.getThirdOrgId());

        	// 查询未购买消费者统计信息
        	ExecuteResult<List<NewNotBuyVO>> executeNotBuyCustomer = newNotBuyExportService.queryList(newNotBuyParamVO);
        	// 参数vo
        	NewSalesOrderVO newSalesOrderParamVO = new NewSalesOrderVO();
            // 门店id
            if(Constants.STORE_ID_ALL.equals(paramVO.getStoreId())){
                newSalesOrderParamVO.setStoreList(paramVO.getStoreIdList());
            }
            else{
                newSalesOrderParamVO.setStoreId(paramVO.getStoreId());
            }
        	// 订单状态
        	newSalesOrderParamVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_CANCLE);
        	// 开始时间
        	newSalesOrderParamVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderParamVO.setEndCreateDate(paramVO.getEndDate());
			//大区
			newSalesOrderParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newSalesOrderParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newSalesOrderParamVO.setThirdOrgId(paramVO.getThirdOrgId());
        	// 查询未购买消费者统计信息
        	ExecuteResult<List<NewSalesOrderVO>> executeCancleCustomer = newSalesOrderExportService.queryList(newSalesOrderParamVO);
        	List<NotBuyCustomerVO> notBuyCustomerList = new ArrayList<>();
        	if(Utility.isNotEmpty(executeNotBuyCustomer.getResult())) {
    			notBuyCustomerList = BeanUtil.copyList(NotBuyCustomerVO.class, executeNotBuyCustomer.getResult());
    		}
        	if(Utility.isNotEmpty(executeCancleCustomer.getResult())) {
	        	// 取消订单id集合
	        	ExecuteResult<List<Long>> executeSalesOrderId = newSalesOrderExportService.queryListSalesOrderId(newSalesOrderParamVO);
	        	// 查询参数vo
	        	NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
	        	// 销售订单id集合
	        	newSalesOrderGoodsVO.setSalesOrderIdList(executeSalesOrderId.getResult());
	        	// 商品一级分类-整车
	        	newSalesOrderGoodsVO.setFirstCategoryId(OrderConstants.GOODS_FIRST_CATEGORY_ID);
	        	// 查询订单商品信息
	        	ExecuteResult<List<NewSalesOrderGoodsVO>> executeSalesOrderGoods = newSalesOrderGoodsExportService.queryList(newSalesOrderGoodsVO);
	        	if(Utility.isNotEmpty(executeSalesOrderGoods.getResult())) {
	        		Map<Long, NewSalesOrderVO> mapNewSalesOrderVO = new HashMap<>();
	        		for(NewSalesOrderVO newSalesOrderVO : executeCancleCustomer.getResult()) {
	        			mapNewSalesOrderVO.put(newSalesOrderVO.getSalesOrderId(), newSalesOrderVO);
	        		}
	        		for(NewSalesOrderGoodsVO newSalesOrderGoods : executeSalesOrderGoods.getResult()) {
	        			if(mapNewSalesOrderVO.containsKey(newSalesOrderGoods.getSalesOrderId())) {
	        				NotBuyCustomerVO NotBuyCustomerVO = BeanUtil.copyPropertes(NotBuyCustomerVO.class, mapNewSalesOrderVO.get(newSalesOrderGoods.getSalesOrderId()));
	        				NotBuyCustomerVO.setIntentionVehicleName(newSalesOrderGoods.getThirdCategoryName());
	        				notBuyCustomerList.add(NotBuyCustomerVO);
	        			}
	        		}
	        	}
        	}
			if(Utility.isNotEmpty(notBuyCustomerList)) {
				// 当前页
				int pageNum = page.getPageNum();
				// 一页条数
				int pageSize = page.getPageSize();
				// 总数
				int totalCount=notBuyCustomerList.size();
				// 总页数
				int pageCount = 1;
				if(totalCount > pageSize) {
					pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);
				}
				// 起始条数
				int start=(pageNum-1) * pageSize;
				// 结尾条数
				int end = pageNum==pageCount ? totalCount : pageNum * pageSize;
				List<NotBuyCustomerVO> statisticsNotBuyList = null;
				if (end > totalCount) {
					statisticsNotBuyList = notBuyCustomerList.subList(start,totalCount);
				} else {
					statisticsNotBuyList = notBuyCustomerList.subList(start,end);
				}
				PageInfo<NotBuyCustomerVO> pageInfo = new PageInfo<>(statisticsNotBuyList);
				pageInfo.setTotal(totalCount);
				pageInfo.setPageNum(pageNum);
				pageInfo.setPageSize(pageSize);
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
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsPageNotBuyCustomer", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsPageNotBuyCustomer",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<NotBuyCustomerVO>> statisticsListNotBuyCustomer(BaseParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsListNotBuyCustomer",  JSONObject.toJSONString(paramVO));
        ExecuteResult<List<NotBuyCustomerVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	// 参数vo
        	NewNotBuyVO newNotBuyParamVO = new NewNotBuyVO();
			//dealParamVO(paramVO);
            // 门店id
            if(Constants.STORE_ID_ALL.equals(paramVO.getStoreId())){
                newNotBuyParamVO.setStoreList(paramVO.getStoreIdList());
            }else{
                newNotBuyParamVO.setStoreId(paramVO.getStoreId());
            }
        	// 开始时间
        	newNotBuyParamVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newNotBuyParamVO.setEndCreateDate(paramVO.getEndDate());
			//大区
			newNotBuyParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newNotBuyParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newNotBuyParamVO.setThirdOrgId(paramVO.getThirdOrgId());
        	// 查询未购买消费者统计信息
        	ExecuteResult<List<NewNotBuyVO>> executeNotBuyCustomer = newNotBuyExportService.queryList(newNotBuyParamVO);
        	// 参数vo
        	NewSalesOrderVO newSalesOrderParamVO = new NewSalesOrderVO();
        	// 门店id
        	newSalesOrderParamVO.setStoreId(paramVO.getStoreId());
        	// 订单状态
        	newSalesOrderParamVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_CANCLE);
        	// 开始时间
        	newSalesOrderParamVO.setStartCreateDate(paramVO.getStartDate());
        	// 结束时间
        	newSalesOrderParamVO.setEndCreateDate(paramVO.getEndDate());
			//大区
			newSalesOrderParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newSalesOrderParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newSalesOrderParamVO.setThirdOrgId(paramVO.getThirdOrgId());
        	// 查询未购买消费者统计信息
        	ExecuteResult<List<NewSalesOrderVO>> executeCancleCustomer = newSalesOrderExportService.queryList(newSalesOrderParamVO);
        	List<NotBuyCustomerVO> notBuyCustomerList = new ArrayList<>();
        	if(Utility.isNotEmpty(executeNotBuyCustomer.getResult())) {
    			notBuyCustomerList = BeanUtil.copyList(NotBuyCustomerVO.class, executeNotBuyCustomer.getResult());
    		}
        	if(Utility.isNotEmpty(executeCancleCustomer.getResult())) {
	        	// 取消订单id集合
	        	ExecuteResult<List<Long>> executeSalesOrderId = newSalesOrderExportService.queryListSalesOrderId(newSalesOrderParamVO);
		        if(Utility.isNotEmpty(executeSalesOrderId.getResult())) {
		        	// 查询参数vo
		        	NewSalesOrderGoodsVO newSalesOrderGoodsVO = new NewSalesOrderGoodsVO();
		        	// 销售订单id集合
		        	newSalesOrderGoodsVO.setSalesOrderIdList(executeSalesOrderId.getResult());
		        	// 商品一级分类-整车
		        	newSalesOrderGoodsVO.setFirstCategoryId(OrderConstants.GOODS_FIRST_CATEGORY_ID);
		        	// 查询订单商品信息
		        	ExecuteResult<List<NewSalesOrderGoodsVO>> executeSalesOrderGoods = newSalesOrderGoodsExportService.queryList(newSalesOrderGoodsVO);
		        	if(Utility.isNotEmpty(executeSalesOrderGoods.getResult())) {
		        		Map<Long, NewSalesOrderVO> mapNewSalesOrderVO = new HashMap<>();
		        		for(NewSalesOrderVO newSalesOrderVO : executeCancleCustomer.getResult()) {
		        			mapNewSalesOrderVO.put(newSalesOrderVO.getSalesOrderId(), newSalesOrderVO);
		        		}
		        		for(NewSalesOrderGoodsVO newSalesOrderGoods : executeSalesOrderGoods.getResult()) {
		        			if(mapNewSalesOrderVO.containsKey(newSalesOrderGoods.getSalesOrderId())) {
		        				NotBuyCustomerVO NotBuyCustomerVO = BeanUtil.copyPropertes(NotBuyCustomerVO.class, mapNewSalesOrderVO.get(newSalesOrderGoods.getSalesOrderId()));
		        				NotBuyCustomerVO.setIntentionVehicleName(newSalesOrderGoods.getThirdCategoryName());
		        				notBuyCustomerList.add(NotBuyCustomerVO);
		        			}
		        		}
		        	}
	        	}
        	}
			if(Utility.isNotEmpty(notBuyCustomerList)) {
				result.setResult(notBuyCustomerList);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListNotBuyCustomer", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListNotBuyCustomer",  JSONObject.toJSONString(result));
        return result;
	}


	@Override
	public ExecuteResult<PageInfo<StatisticsSalesVO>> statsSalesOrderPage(StatsOrderParamVO paramVO, Page page){
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statsSalesOrderPage",  JSONObject.toJSONString(paramVO));
		//ExecuteResult<List<StatisticsSalesVO>> result = new ExecuteResult<>();
		ExecuteResult<PageInfo<StatisticsSalesVO>> result = new ExecuteResult<>();
		boolean flag = false;
		try {

			// 参数vo
			NewSalesOrderVO newSalesOrderParamVO = new NewSalesOrderVO();
			//dealParamVO(paramVO);
			// 城市id集合
			newSalesOrderParamVO.setOrgList(paramVO.getOrgIdList());
			// 门店id集合
			newSalesOrderParamVO.setStoreList(paramVO.getStoreIdList());
			// 订单状态
			//newSalesOrderParamVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
			newSalesOrderParamVO.setSalesOrderStatusId(paramVO.getSalesOrderStatusId());
			// 开始时间
			newSalesOrderParamVO.setStartCreateDate(paramVO.getStartDate());
			// 结束时间
			newSalesOrderParamVO.setEndCreateDate(paramVO.getEndDate());
			// 门店名称
			newSalesOrderParamVO.setStoreName(paramVO.getStoreName());
			// 门店编号
			newSalesOrderParamVO.setStoreNumber(paramVO.getStoreNumber());

			//大区
			newSalesOrderParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newSalesOrderParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newSalesOrderParamVO.setThirdOrgId(paramVO.getThirdOrgId());

			// 统计销售订单及商品
			ExecuteResult<List<StatisticsSalesVO>> resultNoPage = newSalesOrderExportService.queryStatsSalesOrderPage(newSalesOrderParamVO);
			List<StatisticsSalesVO> resultDataList = resultNoPage.getResult();

			if(Utility.isNotEmpty(resultDataList)) {
				int total = 0;
				String storeIDs = "";
				for(int i = 0 ; i<resultDataList.size();i++){
					StatisticsSalesVO vo = resultDataList.get(i);
					total = total + vo.getSaleAmount();
					storeIDs = storeIDs+vo.getStoreId()+",";
				}


				// 当前页
				int pageNum = page.getPageNum();
				// 一页条数
				int pageSize = page.getPageSize();
				// 总数
				int totalCount = resultDataList.size();
				// 总页数
				int pageCount = 1;
				if(totalCount > pageSize) {
					pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);
				}
				// 起始条数
				int start=(pageNum-1) * pageSize;
				// 结尾条数
				int end = pageNum==pageCount ? totalCount : pageNum * pageSize;
				List<StatisticsSalesVO> statisticsSalesList = null;
				if (end > totalCount) {
					statisticsSalesList = resultDataList.subList(start,totalCount);
				} else {
					statisticsSalesList = resultDataList.subList(start,end);
				}

				//添加行：“全部”
				//List<StatisticsNotBuyVO> statisticsNotBuyAllList = addNotByALL(StatisticsSalesList,total);

				//添加全部
				StatisticsSalesVO statisticsSalesALLVO = new  StatisticsSalesVO();
				statisticsSalesALLVO.setStoreId(Constants.STORE_ID_ALL);
				statisticsSalesALLVO.setStoreNumber("全部");
				statisticsSalesALLVO.setStoreName("全部");
				statisticsSalesALLVO.setStoreChannelNumber("全部");
				statisticsSalesALLVO.setSaleAmount(total);
				statisticsSalesALLVO.setExtraData(storeIDs);
				List<StatisticsSalesVO> statisticsSalesListAll = new ArrayList();
				statisticsSalesListAll.add(0,statisticsSalesALLVO);
				statisticsSalesListAll.addAll(statisticsSalesList);

				PageInfo<StatisticsSalesVO> pageInfo = new PageInfo<StatisticsSalesVO>(statisticsSalesListAll);
				pageInfo.setTotal(totalCount);
				pageInfo.setPageNum(pageNum);
				pageInfo.setPageSize(pageSize);
				result.setResult(pageInfo);
				pageInfo.setLastPage(pageCount);

				result.setCode(Constants.SUCCESS_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			}
			else {
				result.setCode(Constants.EMPTY_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			}


		} catch (Exception e) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.toString());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListNotBuy", e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListNotBuy",  JSONObject.toJSONString(result));
		return result;
	}
	@Override
	public ExecuteResult<List<StatisticsSourceVO>> statsSalesByType(BaseParamVO paramVO,String type) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource",  JSONObject.toJSONString(paramVO));
		ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
		boolean flag = false;
		try {

			// 参数vo
			NewSalesOrderVO newSalesOrderParamVO = new NewSalesOrderVO();
			//dealParamVO(paramVO);
            // 城市id集合
            newSalesOrderParamVO.setOrgList(paramVO.getOrgIdList());
			// 门店id
			if(Constants.STORE_ID_ALL.equals(paramVO.getStoreId())){
				newSalesOrderParamVO.setStoreList(paramVO.getStoreIdList());
			}else{
				newSalesOrderParamVO.setStoreId(paramVO.getStoreId());
			}

			// 订单状态
			//newSalesOrderParamVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
			newSalesOrderParamVO.setSalesOrderStatusId(paramVO.getSalesOrderStatusId());
			// 开始时间
			newSalesOrderParamVO.setStartCreateDate(paramVO.getStartDate());
			// 结束时间
			newSalesOrderParamVO.setEndCreateDate(paramVO.getEndDate());
			// 查询取消订单原因分析(图)信息

			//大区
			newSalesOrderParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newSalesOrderParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newSalesOrderParamVO.setThirdOrgId(paramVO.getThirdOrgId());

			result = newSalesOrderExportService.queryStatsSalesByType(newSalesOrderParamVO,type);

		} catch (Exception e) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.toString());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource", e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource",  JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisticsSalesVO>> statsReturnOrderPage(StatsOrderParamVO paramVO, Page page){
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statsSalesOrderPage",  JSONObject.toJSONString(paramVO));
		ExecuteResult<PageInfo<StatisticsSalesVO>> result = new ExecuteResult<>();
		ExecuteResult<List<StatisticsSalesVO>> resultNoPage = new ExecuteResult<>();
		boolean flag = false;
		try {

			// 参数vo
            NewReturnOrderVO newReturnOrderParamVO = new NewReturnOrderVO();
			//dealParamVO(paramVO);
			// 城市id集合
			newReturnOrderParamVO.setOrgList(paramVO.getOrgIdList());
			// 门店id集合
			newReturnOrderParamVO.setStoreList(paramVO.getStoreIdList());
			// 订单状态
			//newReturnOrderParamVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
            newReturnOrderParamVO.setReturnOrderStatusId(OrderConstants.ORDER_DICT_RUTURNYES);
			// 开始时间
			newReturnOrderParamVO.setStartCreateDate(paramVO.getStartDate());
			// 结束时间
			newReturnOrderParamVO.setEndCreateDate(paramVO.getEndDate());
			// 门店名称
			newReturnOrderParamVO.setStoreName(paramVO.getStoreName());
			// 门店编号
			newReturnOrderParamVO.setStoreNumber(paramVO.getStoreNumber());
			// 统计销售订单及商品

			//大区
			newReturnOrderParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newReturnOrderParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newReturnOrderParamVO.setThirdOrgId(paramVO.getThirdOrgId());

			resultNoPage = newReturnOrderExportService.queryStatsReturnOrderPage(newReturnOrderParamVO);

			List<StatisticsSalesVO> resultDataList = resultNoPage.getResult();


			if(Utility.isNotEmpty(resultDataList)) {

				int total = 0;
				String storeIDs = "";
				for(int i = 0 ; i<resultDataList.size();i++){
					StatisticsSalesVO vo = resultDataList.get(i);
					total = total + vo.getSaleAmount();
					storeIDs = storeIDs+vo.getStoreId()+",";
				}


				// 当前页
				int pageNum = page.getPageNum();
				// 一页条数
				int pageSize = page.getPageSize();
				// 总数
				int totalCount = resultDataList.size();
				// 总页数
				int pageCount = 1;
				if(totalCount > pageSize) {
					pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);
				}
				// 起始条数
				int start=(pageNum-1) * pageSize;
				// 结尾条数
				int end = pageNum==pageCount ? totalCount : pageNum * pageSize;
				List<StatisticsSalesVO> statisticsSalesList = null;
				if (end > totalCount) {
					statisticsSalesList = resultDataList.subList(start,totalCount);
				} else {
					statisticsSalesList = resultDataList.subList(start,end);
				}

				//添加全部
				StatisticsSalesVO statisticsSalesALLVO = new  StatisticsSalesVO();
				statisticsSalesALLVO.setStoreId(Constants.STORE_ID_ALL);
				statisticsSalesALLVO.setStoreNumber("全部");
				statisticsSalesALLVO.setStoreName("全部");
				statisticsSalesALLVO.setStoreChannelNumber("全部");
				statisticsSalesALLVO.setSaleAmount(total);
				statisticsSalesALLVO.setExtraData(storeIDs);
				List<StatisticsSalesVO> statisticsSalesListAll = new ArrayList();
				statisticsSalesListAll.add(0,statisticsSalesALLVO);
				statisticsSalesListAll.addAll(statisticsSalesList);

				PageInfo<StatisticsSalesVO> pageInfo = new PageInfo<StatisticsSalesVO>(statisticsSalesListAll);
				pageInfo.setTotal(totalCount);
				pageInfo.setPageNum(pageNum);
				pageInfo.setPageSize(pageSize);
				result.setResult(pageInfo);
				pageInfo.setLastPage(pageCount);
				result.setCode(Constants.SUCCESS_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			}
			else {
				result.setCode(Constants.EMPTY_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			}



		} catch (Exception e) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.toString());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListNotBuy", e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListNotBuy",  JSONObject.toJSONString(result));
		return result;
	}
	@Override
	public ExecuteResult<List<StatisticsSourceVO>> statsReturnByReason(BaseParamVO paramVO,String type) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource",  JSONObject.toJSONString(paramVO));
		ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
		boolean flag = false;
		try {

			// 参数vo
			NewReturnOrderVO newReturnOrderParamVO = new NewReturnOrderVO();
			//dealParamVO(paramVO);
			// 城市id集合
			newReturnOrderParamVO.setOrgList(paramVO.getOrgIdList());
			// 门店id
			if(Constants.STORE_ID_ALL.equals(paramVO.getStoreId())){
				newReturnOrderParamVO.setStoreList(paramVO.getStoreIdList());
			}else{
				newReturnOrderParamVO.setStoreId(paramVO.getStoreId());
			}

			// 订单状态
			//newReturnOrderParamVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
			//newReturnOrderParamVO.setReturnOrderStatusId(paramVO.getSalesOrderStatusId());
			newReturnOrderParamVO.setReturnOrderStatusId(OrderConstants.ORDER_DICT_RUTURNYES);
			// 开始时间
			newReturnOrderParamVO.setStartCreateDate(paramVO.getStartDate());
			// 结束时间
			newReturnOrderParamVO.setEndCreateDate(paramVO.getEndDate());

			//大区
			newReturnOrderParamVO.setFirstOrgId(paramVO.getFirstOrgId());
			//区域
			newReturnOrderParamVO.setSecondOrgId(paramVO.getSecondOrgId());
			//城市
			newReturnOrderParamVO.setThirdOrgId(paramVO.getThirdOrgId());
			// 查询取消订单原因分析(图)信息

			result = newReturnOrderExportService.queryStatsReturnByReason(newReturnOrderParamVO);

		} catch (Exception e) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.toString());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource", e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewStatisticsExportServiceImpl-statisticsListCustomerSource",  JSONObject.toJSONString(result));
		return result;
	}
}

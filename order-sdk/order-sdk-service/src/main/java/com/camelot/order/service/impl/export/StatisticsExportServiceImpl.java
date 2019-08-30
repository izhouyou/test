package com.camelot.order.service.impl.export;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.service.DictValueExportService;
import com.camelot.order.export.service.NotBuyExportService;
import com.camelot.order.export.service.OrderGoodsExportService;
import com.camelot.order.export.service.SalesOrderExportService;
import com.camelot.order.export.service.StatisticsExportService;
import com.camelot.order.export.vo.ActivityDataVO;
import com.camelot.order.export.vo.NotBuyVO;
import com.camelot.order.export.vo.OrderGoodsVO;
import com.camelot.order.export.vo.QueryParamVO;
import com.camelot.order.export.vo.ReturnOrderVO;
import com.camelot.order.export.vo.SalesOrderVO;
import com.camelot.order.export.vo.SalesReportVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisitcsGoodsSalesVO;
import com.camelot.order.export.vo.StatisticsFigureVO;
import com.camelot.order.export.vo.StatisticsGoodsCategoryVO;
import com.camelot.order.export.vo.StatisticsGoodsVO;
import com.camelot.order.export.vo.StatisticsNotBuyVO;
import com.camelot.order.export.vo.StatisticsSourceVO;
import com.camelot.order.export.vo.feignvo.FeignActivityVO;
import com.camelot.order.export.vo.feignvo.FeignGoodsCategoryVO;
import com.camelot.order.export.vo.feignvo.FeignStoreVO;
import com.camelot.order.feign.FeignActivityService;
import com.camelot.order.feign.FeignCouponService;
import com.camelot.order.feign.FeignCustomerService;
import com.camelot.order.feign.FeignGoodsCategoryService;
import com.camelot.order.feign.FeignGoodsService;
import com.camelot.order.feign.FeignStoreService;
import com.camelot.order.service.ComplianceService;
import com.camelot.order.service.ReturnOrderService;
import com.camelot.order.service.StatisticsReturnOrderService;
import com.camelot.order.service.StatisticsService;
import com.camelot.order.service.StatisticsTotalService;
import com.github.pagehelper.PageInfo;

@Service
public class StatisticsExportServiceImpl implements StatisticsExportService {
	
	private static Logger log = Log.get(StatisticsExportServiceImpl.class);
	
	@Autowired
	private FeignActivityService feignActivityService;
	@Autowired
	private ComplianceService complianceService;
	@Autowired
	private DictValueExportService dictValueExportService;
	@Autowired
	private FeignStoreService feignStoreService;
	@Autowired
	private ReturnOrderService returnOrderService;
	@Autowired
	private FeignCustomerService feignCustomerService;
	@Autowired
	private StatisticsTotalService statisticsTotalService;
	@Autowired
	private StatisticsReturnOrderService statisticsReturnOrderService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private SalesOrderExportService salesOrderExportService;
	@Autowired
	private OrderGoodsExportService orderGoodsExportService;
	@Autowired
	private FeignCouponService feignCouponService;
	@Autowired
	private NotBuyExportService notBuyExportService;
	@Autowired
	private FeignGoodsService feignGoodsService;
	@Autowired
	private FeignGoodsCategoryService feignGoodsCategoryService;

	// 门店销售报表
	@Override
	public ExecuteResult<PageInfo<SalesReportVO>> querySalesReportList(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-querySalesReportList", paramVO.toString());
		ExecuteResult<PageInfo<SalesReportVO>> result = new ExecuteResult<>();
		List<SalesReportVO> salesReportList = new ArrayList<>();
		Boolean flag = false;
		try {
			String activityName = paramVO.getActivityName();
			String activityNumber = paramVO.getActivityNumber();
			Map<Integer, String> activityMap = new HashMap<>();
			String activityIds = "";
			if(Utility.isNotEmpty(activityName) || Utility.isNotEmpty(activityNumber)) {
				Map<String, Object> feignActivityVO = new HashMap<>();
				feignActivityVO.put("activityName",activityName);
				feignActivityVO.put("activityId",activityNumber);
				// 调用营销服务,根据活动编码or名称,查询活动信息
				AjaxInfo activityAjax = feignActivityService.queryActivityAllList(feignActivityVO);
				if(Utility.isEmpty(activityAjax.getData())) {
					Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-querySalesReportList", "无此活动");
					result.setCode(Constants.EMPTY_CODE);
					result.setResultMessage(Constants.QUERY_SUCCESS);
					return result;
				}
				List<FeignActivityVO> activityList = JSONObject.parseArray(JSONObject.toJSONString(activityAjax.getData()), FeignActivityVO.class);
				if(Utility.isNotEmpty(activityList)) {
					for(FeignActivityVO activity : activityList) {
						activityMap.put(activity.gettActivityId(), activity.getActivityName());
						activityIds += "," +activity.gettActivityId();
					}
					paramVO.setActivityIds(activityIds);
				}
			}
			// 查询订单数据
			ExecuteResult<PageInfo<SalesOrderVO>> orderExecuteResult = complianceService.queryPageByStoreIds(paramVO);
			if (Utility.isNotEmpty(orderExecuteResult.getResult())) {
				List<SalesOrderVO> salesOrderList = orderExecuteResult.getResult().getList();
				// 调用其他服务，补全门店销售报表信息
				salesReportList = salesReportDetails(salesOrderList,activityMap);
				PageInfo pageInfo = orderExecuteResult.getResult();
				pageInfo.setList(salesReportList);
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
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsExportServiceImpl-querySalesReportList", e.toString());
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.getMessage());
			return result;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-querySalesReportList", JSON.toJSONString(result));
		return result;
	}
	
	// 门店销售报表导出
	@Override
	public ExecuteResult<List<SalesReportVO>> exportSalesReport(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-exportSalesReport", paramVO.toString());
		ExecuteResult<List<SalesReportVO>> result = new ExecuteResult<>();
		List<SalesReportVO> salesReportList = new ArrayList<>();
		Boolean flag = false;
		try {
			String activityName = paramVO.getActivityName();
			String activityNumber = paramVO.getActivityNumber();
			Map<Integer, String> activityMap = new HashMap<>();
			String activityIds = "";
			if(Utility.isNotEmpty(activityName) || Utility.isNotEmpty(activityNumber)) {
				Map<String, Object> feignActivityVO = new HashMap<>();
				feignActivityVO.put("activityName",activityName);
				feignActivityVO.put("activityId",activityNumber);
				// 调用营销服务,根据活动编码or名称,查询活动信息
				AjaxInfo activityAjax = feignActivityService.queryActivityAllList(feignActivityVO);
				if(Utility.isEmpty(activityAjax.getData())) {
					Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-exportSalesReport", "无此活动");
					result.setCode(Constants.EMPTY_CODE);
					result.setResultMessage(Constants.QUERY_SUCCESS);
					return result;
				}
				List<FeignActivityVO> activityList = JSONObject.parseArray(JSONObject.toJSONString(activityAjax.getData()), FeignActivityVO.class);
				if(Utility.isNotEmpty(activityList)) {
					for(FeignActivityVO activity : activityList) {
						activityMap.put(activity.gettActivityId(), activity.getActivityName());
						activityIds += "," +activity.gettActivityId();
					}
					paramVO.setActivityIds(activityIds);
				}
			}
			// 查询订单数据
			ExecuteResult<List<SalesOrderVO>> orderExecuteResult = complianceService.queryByStoreIds(paramVO.getOrgIds(),paramVO.getStoreIds(),paramVO.getSalesOrderNumber(),paramVO.getStartDate(),paramVO.getEndDate(),paramVO.getSalesOrderStatus(),paramVO.getActivityIds(),paramVO.getCustomerId());
			List<SalesOrderVO> salesOrderList = orderExecuteResult.getResult();
			if (Utility.isNotEmpty(salesOrderList)) {
				// 调用其他服务，补全门店销售报表信息
				salesReportList = salesReportDetails(salesOrderList,activityMap);
				result.setResult(salesReportList);
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
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsExportServiceImpl-exportSalesReport", e.toString());
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-exportSalesReport", JSON.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<SalesVolumeVO> queryStatisticsResult(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-querySalesReportList", paramVO.toString());
		ExecuteResult<SalesVolumeVO> result = statisticsTotalService.queryStatisticsResult(paramVO);
		ExecuteResult<Map<String, BigDecimal>> statisticsReturnOrder = statisticsReturnOrderService.queryStatisticsResult(paramVO);
		if(Utility.isNotEmpty(result.getResult())) {
			if(Utility.isNotEmpty(statisticsReturnOrder.getResult())) {
				BigDecimal returnOrderAmount = statisticsReturnOrder.getResult().get("returnOrderAmount");
				String returnOrderValueAmount = Utility.bigDecimalToString(returnOrderAmount);
				result.getResult().setReturnAmountValueTotal(returnOrderValueAmount);
				result.getResult().setReturnAmountTotal(returnOrderAmount);
			}
			String salesAmountValueTotal = Utility.bigDecimalToString(result.getResult().getSalesAmountTotal());
			result.getResult().setSalesAmountValueTotal(salesAmountValueTotal);
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-queryStatisticsResult", JSON.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> queryGoodsVolume(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-queryGoodsVolume", paramVO.toString());
		ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> result = statisticsTotalService.queryGoodsVolume(paramVO);
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-queryGoodsVolume", JSON.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<Map<String, Object>> queryOrderStatistics(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-queryOrderStatistics", paramVO.toString());
		ExecuteResult<Map<String, Object>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			ExecuteResult<List<Map<String, Object>>> executeResultOne = statisticsTotalService.queryOrderStatistics(paramVO);
			QueryParamVO copyParamVO = BeanUtil.copyPropertes(QueryParamVO.class, paramVO);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = simpleDateFormat.parse(paramVO.getStartDate());
			Date endDate = simpleDateFormat.parse(paramVO.getEndDate());
			Long newDay = startDate.getTime();
			Long endDay = endDate.getTime();
			int day = (int) ((endDate.getTime() - startDate.getTime())/(24*60*60*1000))+1;
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			startCalendar.add(Calendar.DAY_OF_YEAR,-day);
			startDate = startCalendar.getTime();
			copyParamVO.setStartDate(simpleDateFormat.format(startCalendar.getTime()));// day天前开始时间
			endCalendar.setTime(endDate);
			endCalendar.add(Calendar.DAY_OF_YEAR,-day);
			copyParamVO.setEndDate(simpleDateFormat.format(endCalendar.getTime()));// day天前结束时间
			ExecuteResult<List<Map<String, Object>>> executeResultTwo = statisticsTotalService.queryOrderStatistics(copyParamVO);
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
			            @Override
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
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-queryOrderStatistics", JSON.toJSONString(result));
		return result;
	}


	@Override
	public ExecuteResult<PageInfo<StatisticsNotBuyVO>> queryNotBuy(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-queryNotBuy", paramVO.toString());
		ExecuteResult<PageInfo<StatisticsNotBuyVO>> result = new ExecuteResult<>();
		List<StatisticsNotBuyVO> resultList = new ArrayList<>();
		Boolean flag = false;
		try {
			List<FeignStoreVO> feignStoreVOList = null;
			// 如果有门店编号或门店名称的查询条件,先调用基础服务查询门店信息
			if(Utility.isNotEmpty(paramVO.getStoreNumber()) || Utility.isNotEmpty(paramVO.getStoreName())) {
				Map<String, Object> storeMap = new HashMap<>();
				storeMap.put("storeNumber", paramVO.getStoreNumber());
				storeMap.put("storeName", paramVO.getStoreName());
				// 调用基础服务查询门店信息
				AjaxInfo storeAjax = feignStoreService.queryList(storeMap);
				if(Utility.isNotEmpty(storeAjax.getData())) {
					HashSet<String> feignStoreIdSet = new HashSet<>();
					feignStoreVOList = JSONObject.parseArray(JSONObject.toJSONString(storeAjax.getData()), FeignStoreVO.class);
					for (FeignStoreVO feignStoreVO : feignStoreVOList) {
						// 将门店id添加到SET集合
						feignStoreIdSet.add(String.valueOf(feignStoreVO.getStoreId()));
					}
					if(Utility.isNotEmpty(feignStoreVOList)) {
						String storeIds = paramVO.getStoreIds();
						HashSet<String> storeIdSet = null;
						if (Utility.isNotEmpty(storeIds)) {
							storeIdSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
							// 求门店id的交集
							storeIdSet.retainAll(feignStoreIdSet);
							String storeIdStr = Utility.objectToString(storeIdSet).replaceAll(" ", "");
							paramVO.setStoreIds(storeIdStr.substring(1, storeIdStr.length()-1));
						}
					}
				} else {
					result.setCode(Constants.EMPTY_CODE);
					result.setResultMessage(Constants.QUERY_SUCCESS);
					return result;
				}
			}
			
			// 查询未购买上报数据(门店id,未购买量)
			List<StatisticsNotBuyVO> notBuyList = statisticsTotalService.queryNotBuy(paramVO);
			// 查询取消订单数据(门店id,取消订单数)
			List<StatisticsNotBuyVO> orderAmountList = statisticsTotalService.queryOrderAmount(paramVO);
			// 合并两个List集合
			Map<Integer, Integer> statisticsNotBuyListMap = new HashMap<>();
			List<StatisticsNotBuyVO> notBuyResult = new ArrayList<>();
			if(Utility.isNotEmpty(notBuyList)) {
				for (StatisticsNotBuyVO statisticsNotBuyVO : notBuyList) {
					statisticsNotBuyListMap.put(statisticsNotBuyVO.getStoreId(), statisticsNotBuyVO.getNotBuyAmount());
				}
			}
			if(Utility.isNotEmpty(orderAmountList)) {
				for (StatisticsNotBuyVO orderAmountVO : orderAmountList) {
					Integer storeId = orderAmountVO.getStoreId();
					if(Utility.isNotEmpty(storeId)) {
						if(statisticsNotBuyListMap.containsKey(storeId)) {
							Integer totalAmount = statisticsNotBuyListMap.get(storeId) + orderAmountVO.getNotBuyAmount();
							statisticsNotBuyListMap.put(storeId, totalAmount);
						} else {
							statisticsNotBuyListMap.put(storeId, orderAmountVO.getNotBuyAmount());
						}
					}
				}
			}
			HashSet<Integer> storeIds = new HashSet<>();
			for(Integer storeId : statisticsNotBuyListMap.keySet()) {
				StatisticsNotBuyVO statisticsNotBuyVO = new StatisticsNotBuyVO();
				storeIds.add(storeId);
				statisticsNotBuyVO.setStoreId(storeId);
				statisticsNotBuyVO.setNotBuyAmount(statisticsNotBuyListMap.get(storeId));
				notBuyResult.add(statisticsNotBuyVO);
			}
			String storeIdStr = Utility.objectToString(storeIds).replaceAll(" ", "");
			String ids = storeIdStr.substring(1, storeIdStr.length()-1);
			// 调用基础服务查询门店数据
			AjaxInfo storeAjax = feignStoreService.queryStoreList(ids);
			if(Utility.isNotEmpty(storeAjax.getData())) {
				List<StatisticsNotBuyVO> feignStore = JSONObject.parseArray(JSONObject.toJSONString(storeAjax.getData()), StatisticsNotBuyVO.class);
				for (StatisticsNotBuyVO statisticsNotBuyVO : feignStore) {
					if(Utility.isNotEmpty(notBuyResult)) {
						for (StatisticsNotBuyVO statisticsNotBuyVO2 : notBuyResult) {
							if(statisticsNotBuyVO.getStoreId().equals(statisticsNotBuyVO2.getStoreId())) {
								statisticsNotBuyVO.setNotBuyAmount(statisticsNotBuyVO2.getNotBuyAmount());
								resultList.add(statisticsNotBuyVO);
							}
						}
					} else {
						resultList.add(statisticsNotBuyVO);
					}
				}
				// 当前页
				int pageNum = paramVO.getPage().getPageNum();
				// 一页条数
				int pageSize = paramVO.getPage().getPageSize();
				// 总数
				int totalCount=resultList.size();
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
					StatisticsNotBuyList = resultList.subList(start,totalCount);
				} else {
					StatisticsNotBuyList = resultList.subList(start,end);
				}
				PageInfo<StatisticsNotBuyVO> pageInfo = new PageInfo<>(StatisticsNotBuyList);
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
			result.setResultMessage(e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-queryNotBuy", JSON.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<List<StatisticsSourceVO>> queryCustomerSource(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-queryCustomerSource", paramVO.toString());
		ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			// 消费者id
			String customerIds = "";
			// 未购买消费者数据
			List<StatisticsSourceVO> notBuySourceList = statisticsService.queryNotBuySource(paramVO);
			// 取消订单消费者数据
			List<StatisticsSourceVO> canceSourcelList = statisticsService.queryOrderSource(paramVO);
			// 将未购买消费者id拼接
			if(Utility.isNotEmpty(notBuySourceList)) {
				for (StatisticsSourceVO notBuySourceVO : notBuySourceList) {
					customerIds += notBuySourceVO.getName() + ",";
				}
			}
			// 将取消订单消费者id拼接
			if(Utility.isNotEmpty(canceSourcelList)) {
				for (StatisticsSourceVO notBuySourceVO : canceSourcelList) {
						customerIds += notBuySourceVO.getName() + ",";
				}
			}
			if(Utility.isNotEmpty(customerIds)) {
				// 请求基础服务,获取客户来源统计数据
				AjaxInfo resultAjax = feignCustomerService.queryCustomerSource(customerIds.replaceAll(" ", ""));
				List<StatisticsSourceVO> statisticsSourceVO = JSONObject.parseArray(JSONObject.toJSONString(resultAjax.getData()),StatisticsSourceVO.class);
				result.setResult(statisticsSourceVO);
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
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsExportServiceImpl-queryCustomerSource", e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-queryCustomerSource", JSON.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<List<StatisticsSourceVO>> queryNotBuyCause(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-queryNotBuyCause", paramVO.toString());
		ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
		// 未购买统计结果
		List<StatisticsSourceVO> listResult = new ArrayList<>();
		boolean flag = false;
		try {
			// 门店未购买数据
			ExecuteResult<List<Map<String, Object>>> notBuyCauseResult = statisticsService.queryNotBuyCause(paramVO);
			List<Map<String, Object>> notBuyCauseListMap = notBuyCauseResult.getResult();
			// 取消订单数据
			ExecuteResult<List<Map<String, Object>>> cancelCauseResult = statisticsService.queryCancelCause(paramVO);
			List<Map<String, Object>> cancelCauseListMap = cancelCauseResult.getResult();
			// 未购买原因集合
			List<StatisticsSourceVO> statisticsSourceList = new ArrayList<>();
			// 汇总Map
			Map<String, Object> causeListMap = new HashMap<>();
			// 将未购买List<Map>加入汇总Map
			if(Utility.isNotEmpty(notBuyCauseListMap)) {
				for(Map<String, Object> notBuyCauseMap : notBuyCauseListMap) {
					if(notBuyCauseMap.containsKey("name") && notBuyCauseMap.containsKey("amount")) {
						String name = Utility.objectToString(notBuyCauseMap.get("name"));
						Integer amount = Integer.valueOf(Utility.objectToString(notBuyCauseMap.get("amount")));
						causeListMap.put(name, amount);
					}
				}
			}
			// 将取消订单List<Map>加入汇总Map
			if(Utility.isNotEmpty(cancelCauseListMap)) {
				for(Map<String, Object> cancelCauseMap : cancelCauseListMap) {
					if(cancelCauseMap.containsKey("name") && cancelCauseMap.containsKey("amount")) {
						String key = Utility.objectToString(cancelCauseMap.get("name"));
						if(causeListMap.containsKey(key)) {
							Integer amount = Integer.valueOf(Utility.objectToString(causeListMap.get(key)))+Integer.valueOf(Utility.objectToString(cancelCauseMap.get("amount")));
							causeListMap.put(key, amount);
						} else {
							causeListMap.put(key, cancelCauseMap.get("amount"));
						}
					}
				}
			}
			// 将汇总Map转化为List对象
			if(Utility.isNotEmpty(causeListMap)) {
				for(String name : causeListMap.keySet()) {
					StatisticsSourceVO statisticsSourceVO = new StatisticsSourceVO();
					statisticsSourceVO.setName(name);
					statisticsSourceVO.setAmount(Integer.valueOf(Utility.objectToString(causeListMap.get(name))));
					statisticsSourceList.add(statisticsSourceVO);
				}
			}
			// 未购买原因or订单取消原因字典值
			for(StatisticsSourceVO statisticsSourceVO : statisticsSourceList) {
							// 将字典id转为字典值
							statisticsSourceVO.setName(dictValueExportService.getDictValueNameById(OrderConstants.ORDER_DICT_NOT_BUY_REASON, Integer.parseInt(statisticsSourceVO.getName())));
							// add结果集合
							listResult.add(statisticsSourceVO);
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
			result.setResultMessage(e.getMessage());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsExportServiceImpl-queryNotBuyCause", e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-queryNotBuyCause", JSON.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisticsGoodsCategoryVO>> queryCarAssort(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-queryCarAssort", JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<StatisticsGoodsCategoryVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	ExecuteResult<PageInfo<Map<String, Object>>> executeResult = statisticsTotalService.queryCarAssort(paramVO);
        	if(Utility.isNotEmpty(executeResult.getResult())) {
        		List<Map<String, Object>> queryMap = executeResult.getResult().getList();
        		if(Utility.isNotEmpty(queryMap)) {
        			List<StatisticsGoodsCategoryVO> queryList = JSONObject.parseArray(JSONObject.toJSONString(queryMap), StatisticsGoodsCategoryVO.class);
        			PageInfo pageInfo = executeResult.getResult();
        			pageInfo.setList(queryList);
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
        	e.printStackTrace();
        	result.setCode(Constants.ERROR_CODE);
        	result.setResultMessage(e.getMessage());
        	Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsExportServiceImpl-queryCarAssort", e.toString());
       	}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-queryCarAssort", JSONObject.toJSONString(result));
		return result;
	}
	
	@Override
	public ExecuteResult<PageInfo<ActivityDataVO>> queryPageByActivity(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderExportServiceImpl-queryPageByActivity", JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<ActivityDataVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	if(Utility.isNotEmpty(paramVO.getActivityName()) || Utility.isNotEmpty(paramVO.getActivityNumber())) {
        		Map<String, Object> feignActivityVO = new HashMap<>();
        		// 查询条件活动名称
				feignActivityVO.put("activityName",paramVO.getActivityName());
				// 查询条件活动编号
				feignActivityVO.put("activityId",paramVO.getActivityNumber());
				// 调用营销服务,根据活动编码or名称,查询活动信息
				AjaxInfo activityAjax = feignActivityService.queryActivityAllList(feignActivityVO);
				if (Utility.isNotEmpty(activityAjax.getData())) {
					List<FeignActivityVO> activityList = JSONObject.parseArray(JSONObject.toJSONString(activityAjax.getData()), FeignActivityVO.class);
					String activityIds = "";
					for(FeignActivityVO activity : activityList) {
						activityIds += "," +activity.gettActivityId();
					}
					paramVO.setActivityIds(activityIds);
				} else {
					result.setCode(Constants.EMPTY_CODE);
					result.setResultMessage(Constants.QUERY_SUCCESS);
					return result;
				}
        	}
        	if(Utility.isNotEmpty(paramVO.getStoreName()) || Utility.isNotEmpty(paramVO.getStoreNumber())) {
        		Map<String, Object> vo = new HashMap<>();
        		vo.put("storeName", paramVO.getStoreName());
        		vo.put("storeNumber", paramVO.getStoreNumber());
        		AjaxInfo storeAjax = feignStoreService.queryList(vo);
        		if(Utility.isNotEmpty(storeAjax.getData())) {
        			List<FeignStoreVO> storeList = JSONObject.parseArray(JSONObject.toJSONString(storeAjax.getData()), FeignStoreVO.class);
        			HashSet<String> storeIdRetainSet = new HashSet<>();
        			HashSet<String> storeIdSet = new HashSet<>();
        			if(Utility.isNotEmpty(storeList)) {
	        			for (FeignStoreVO feignStore : storeList) {
	        				storeIdSet.add(Utility.objectToString(feignStore.getStoreId()));
						}
	        			String storeIds = paramVO.getStoreIds();
	        			HashSet<String> userStoreIdSet = null;
	        			if (Utility.isNotEmpty(storeIds)) {
	        				userStoreIdSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
	        				storeIdRetainSet.addAll(userStoreIdSet);
	        				storeIdRetainSet.retainAll(storeIdSet);
	        			} else {
	        				storeIdRetainSet.addAll(storeIdSet);
	        			}
	        			String storeidStr = Utility.objectToString(storeIdRetainSet).replaceAll(" ", "");
	        			paramVO.setStoreIds(storeidStr.substring(1, storeidStr.length()));
        			} else {
        				result.setCode(Constants.EMPTY_CODE);
    					result.setResultMessage(Constants.QUERY_SUCCESS);
    					return result;
        			}
        		} else {
        			result.setCode(Constants.EMPTY_CODE);
					result.setResultMessage(Constants.QUERY_SUCCESS);
					return result;
        		}
        	}
        	// 查询参见活动的订单数据
        	ExecuteResult<List<SalesOrderVO>> executeResult = complianceService.queryActivityOrder(paramVO);
        	// 订单id集合，用于查询商品订单数据
        	if(Utility.isNotEmpty(executeResult.getResult())) {
        		List<SalesOrderVO> orderPageList = executeResult.getResult();
        		List<SalesOrderVO> salesOrderList = salesOrderExportService.handleSalesOrderList(orderPageList);
        		if(Utility.isNotEmpty(salesOrderList)) {
        			// 补全零售活动报表数据
        			List<ActivityDataVO> activityDataList = activityDataDetails(salesOrderList);
        			if(Utility.isNotEmpty(activityDataList)) {
        				// 当前页
        				int pageNum = paramVO.getPage().getPageNum();
        				// 一页条数
        				int pageSize = paramVO.getPage().getPageSize();
        				// 总数
        				int totalCount=activityDataList.size();
        				// 总页数
        				int pageCount = 1;
        				if(totalCount > pageSize) {
        					pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);
        				}
        				// 起始条数
        				int start=(pageNum-1) * pageSize;
        				// 结尾条数
        				int end = pageNum==pageCount ? totalCount : pageNum * pageSize;
        				List<ActivityDataVO> resultList = null;
        				if (end > totalCount) {
        					resultList = activityDataList.subList(start,totalCount);
        				} else {
        					resultList = activityDataList.subList(start,end);
        				}
        				PageInfo<ActivityDataVO> pageInfo = new PageInfo<>(resultList);
        				pageInfo.setTotal(totalCount);
        				pageInfo.setPageNum(pageNum);
        				pageInfo.setPageSize(pageSize);
        				result.setResult(pageInfo);
        				
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "SalesOrderExportServiceImpl-queryPageByActivity", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-queryPageByActivity", JSONObject.toJSONString(result));
        return result;
	}
	
	
	@Override
	public ExecuteResult<List<StatisticsFigureVO>> queryToDayOrderStatistics(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-queryOrderStatistics", paramVO.toString());
		ExecuteResult<List<StatisticsFigureVO>> result = new ExecuteResult<>();
		Boolean flag = false;
		try {
			// 获取订单统计信息
			ExecuteResult<List<Map<String, Object>>> executeResult = statisticsTotalService.queryToDayOrderStatistics(paramVO);
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
			            @Override
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
						statisticsFigureList.add(statisticsFigureVO);
					}
					result.setResult(statisticsFigureList);
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
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-queryOrderStatistics", JSON.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisticsGoodsVO>> queryGoodsList(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "StatisticsExportServiceImpl-queryGoodsList", JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<StatisticsGoodsVO>> result = new ExecuteResult<>();
        List<StatisticsGoodsVO> resultList = new ArrayList<>();
        boolean flag = false;
        try {
        	ExecuteResult<PageInfo<StatisticsGoodsVO>> goodsExecuteResult = statisticsTotalService.queryPageGoodsList(paramVO);
        	
        	if(Utility.isNotEmpty(goodsExecuteResult.getResult())) {
        		List<StatisticsGoodsVO> statisticsGoodsList = goodsExecuteResult.getResult().getList();
        		for (StatisticsGoodsVO statisticsGoodsVO : statisticsGoodsList) {
        			String actualValuePrice = Utility.bigDecimalToString(statisticsGoodsVO.getActualPrice());
        			String totalValuePrice = Utility.bigDecimalToString(statisticsGoodsVO.getTotalPrice());
        			statisticsGoodsVO.setActualValuePrice(actualValuePrice);
        			statisticsGoodsVO.setTotalValuePrice(totalValuePrice);
        			resultList.add(statisticsGoodsVO);
				}
        		PageInfo pageInfo = goodsExecuteResult.getResult();
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "StatisticsExportServiceImpl-queryGoodsList", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "StatisticsExportServiceImpl-queryGoodsList", JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<ActivityDataVO>> queryByActivity(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderExportServiceImpl-queryByActivity", JSONObject.toJSONString(paramVO));
        ExecuteResult<List<ActivityDataVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	if(Utility.isNotEmpty(paramVO.getActivityName()) || Utility.isNotEmpty(paramVO.getActivityNumber())) {
        		Map<String, Object> feignActivityVO = new HashMap<>();
        		// 查询条件活动名称
				feignActivityVO.put("activityName",paramVO.getActivityName());
				// 查询条件活动编号
				feignActivityVO.put("activityId",paramVO.getActivityNumber());
				// 调用营销服务,根据活动编码or名称,查询活动信息
				AjaxInfo activityAjax = feignActivityService.queryActivityAllList(feignActivityVO);
				if (Utility.isNotEmpty(activityAjax.getData())) {
					List<FeignActivityVO> activityList = JSONObject.parseArray(JSONObject.toJSONString(activityAjax.getData()), FeignActivityVO.class);
					String activityIds = "";
					for(FeignActivityVO activity : activityList) {
						activityIds += "," +activity.gettActivityId();
					}
					paramVO.setActivityIds(activityIds);
				}
        	}
        	if(Utility.isNotEmpty(paramVO.getStoreName()) || Utility.isNotEmpty(paramVO.getStoreNumber())) {
        		Map<String, Object> vo = new HashMap<>();
        		vo.put("storeName", paramVO.getStoreName());
        		vo.put("storeNumber", paramVO.getStoreNumber());
        		// 调用基础服务查询门店信息
        		AjaxInfo storeAjax = feignStoreService.queryList(vo);
        		if(Utility.isNotEmpty(storeAjax.getData())) {
        			List<FeignStoreVO> storeList = JSONObject.parseArray(JSONObject.toJSONString(storeAjax.getData()), FeignStoreVO.class);
        			HashSet<String> storeIdRetainSet = new HashSet<>();
        			HashSet<String> storeIdSet = new HashSet<>();
        			for (FeignStoreVO feignStore : storeList) {
        				storeIdSet.add(Utility.objectToString(feignStore.getStoreId()));
					}
        			String storeIds = paramVO.getStoreIds();
        			HashSet<String> userStoreIdSet = null;
        			if (Utility.isNotEmpty(storeIds)) {
        				userStoreIdSet = new HashSet<>(Arrays.asList(storeIds.split(",")));
        			}
        			storeIdRetainSet.addAll(storeIdSet);
        			storeIdRetainSet.retainAll(userStoreIdSet);
        			String storeidStr = Utility.objectToString(storeIdRetainSet).trim();
        			paramVO.setStoreIds(storeidStr.substring(1, storeidStr.length()));
        		}
        	}
        	// 查询订单数据
        	ExecuteResult<List<SalesOrderVO>> executeResult = complianceService.queryActivityOrder(paramVO);
        	
        	if(Utility.isNotEmpty(executeResult.getResult())) {
        		List<SalesOrderVO> orderPageList = executeResult.getResult();
        		List<SalesOrderVO> salesOrderList = salesOrderExportService.handleSalesOrderList(orderPageList);
        		if(Utility.isNotEmpty(salesOrderList)) {
        			// 补全零售活动报表数据
        			List<ActivityDataVO> activityDataList = activityDataDetails(salesOrderList);
            		result.setResult(activityDataList);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "SalesOrderExportServiceImpl-queryByActivity", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-queryByActivity", JSONObject.toJSONString(result));
        return result;
	}
	
	/**
	 * <p>Description: 补全门店销售报表信息</p>
	 * @param salesOrderList 订单数据
	 * @param activityMap 活动数据
	 * @return
	 */
	private List<SalesReportVO> salesReportDetails(List<SalesOrderVO> salesOrderList, Map<Integer, String> activityMap) {
		List<SalesReportVO> result = new ArrayList<>();
		for(SalesOrderVO salesOrder : salesOrderList) {
			if(Utility.isNotEmpty(salesOrder.getActivityId()) && Utility.isEmpty(activityMap)) {
				// 调用营销服务,根据活动id,查询活动信息
				AjaxInfo activityAjax = feignActivityService.queryById(salesOrder.getActivityId());
				if(Utility.isNotEmpty(activityAjax.getData())) {
					FeignActivityVO activity = JSONObject.parseObject(JSONObject.toJSONString(activityAjax.getData()), FeignActivityVO.class);
					if(Utility.isNotEmpty(activity)) {
						activityMap.put(activity.gettActivityId(), activity.getActivityName());
					}
				}
			}
			SalesReportVO salesReport = new SalesReportVO();
			// 活动名称
			if(activityMap.containsKey(salesOrder.getActivityId())) {
				if(Utility.isNotEmpty(activityMap)) {
					salesReport.setActivityName(activityMap.get(salesOrder.getActivityId()));
				}
			}
			// 订单金额
			salesReport.setSalesOrderAmount(salesOrder.getSalesOrderAmount());
			salesReport.setSalesOrderAmountValue(Utility.bigDecimalToString(salesOrder.getSalesOrderAmount()));
			// 提交时间
			salesReport.setCreateDate(salesOrder.getCreateDate());
			// 订单编码
			salesReport.setSalesOrderNumber(salesOrder.getSalesOrderNumber());
			// 支付方式
			if(Utility.isNotEmpty(salesOrder.getPaymentWay())) {
				salesReport.setPaymentWayName(dictValueExportService.getDictValueNameById(OrderConstants.ORDER_DICT_PAYMENT,salesOrder.getPaymentWay()));
			}
			// 订单来源
			if(Utility.isNotEmpty(salesOrder.getSalesOrderSource())) {
				salesReport.setSalesOrderSourceName(dictValueExportService.getDictValueNameById(OrderConstants.ORDER_DICT_ORDER_SOURCE,salesOrder.getSalesOrderSource()));
			}
			// 订单状态/退货订单编号
			if(Utility.isNotEmpty(salesOrder.getSalesOrderStatus())) {
				String orderStatus = dictValueExportService.getDictValueNameById(OrderConstants.ORDER_DICT_ORDER_STATUS,salesOrder.getSalesOrderStatus());
				// 订单状态
				salesReport.setSalesOrderStatusName(orderStatus);
				// 退货订单编号
				if(OrderConstants.ORDER_STATUS_RETURN.equals(salesOrder.getSalesOrderStatus())) {
					ReturnOrderVO returnOrderVO = new ReturnOrderVO();
					returnOrderVO.setSalesOrderId(salesOrder.getSalesOrderId());
					ExecuteResult<List<ReturnOrderVO>> returnOrderList = returnOrderService.queryList(returnOrderVO);
					if(Utility.isNotEmpty(returnOrderList.getResult())) {
						salesReport.setReturnOrderNumber(returnOrderList.getResult().get(0).getReturnOrderNumber());
						
					}
				}
			}
			// 门店名称
			if (Utility.isNotEmpty(salesOrder.getStoreId())) {
				AjaxInfo storeInfo = feignStoreService.getStoreById(salesOrder.getStoreId().longValue());
				if (Utility.isNotEmpty(storeInfo.getData())) {
					@SuppressWarnings("unchecked")
					Map<String, Object> map = (Map<String, Object>) storeInfo.getData();
					if (map.containsKey("storeName")) {
						salesReport.setStoreName(Utility.objectToString(map.get("storeName")));
					}
				}
			}
			// 消费者手机/名字
			if(Utility.isNotEmpty(salesOrder.getCustomerId())) {
				Map<String,Object> customerMap = new HashMap<String,Object>();
				customerMap.put("customerId",salesOrder.getCustomerId());
				AjaxInfo customerInfo = feignCustomerService.queryCustomerList(customerMap);
				if(Utility.isNotEmpty(customerInfo.getData())){
					Map<String,Object> customerResultMap = ((List<Map<String,Object>>)customerInfo.getData()).get(0);
					//消费者姓名
					if(customerResultMap.containsKey("customerName")){
						salesReport.setCustomerName(Utility.objectToString(customerResultMap.get("customerName")));
					}
					//消费者手机号
					if(customerResultMap.containsKey("customerPhoneNumber")){
						salesReport.setCustomerPhoneNumber(Utility.objectToString(customerResultMap.get("customerPhoneNumber")));
					}
				}
			}
			result.add(salesReport);
		}
		return result;
	}

	// 补全零售活动报表数据
	private List<ActivityDataVO> activityDataDetails(List<SalesOrderVO> salesOrderList) {
		List<ActivityDataVO> result = new ArrayList<>();
		for (SalesOrderVO salesOrderVO : salesOrderList) {
			OrderGoodsVO vo = new OrderGoodsVO();
			ActivityDataVO activityDataVO = new ActivityDataVO();
			// 活动图片url
			activityDataVO.setActivityPicture(salesOrderVO.getActivityPicture());
			// 大区名称
			activityDataVO.setFirstOrgName(salesOrderVO.getFirstOrgName());
			// 区域名称
			activityDataVO.setSecondOrgName(salesOrderVO.getSecondOrgName());
			// 城市名称
			activityDataVO.setThirdOrgName(salesOrderVO.getThirdOrgName());
			// 门店编号
			activityDataVO.setStoreNumber(salesOrderVO.getStoreNumber());
			// 门店名称
			activityDataVO.setStoreName(salesOrderVO.getStoreName());
			// 销售上报时间
			activityDataVO.setCreateDate(salesOrderVO.getCreateDate());
			// 获知来源
			activityDataVO.setCustomerSourceValue(salesOrderVO.getCustomerSource());
			vo.setOrderId(salesOrderVO.getSalesOrderId());
			// 优惠码
			if(Utility.isNotEmpty(salesOrderVO.getCouponId())) {
				AjaxInfo couponAjax = feignCouponService.queryById(salesOrderVO.getCouponId());
				Object data = couponAjax.getData();
				String couponCode = "";
				String couponType = "";
				if(Utility.isNotEmpty(data)) {
					HashMap<String, Object> couponVO = (HashMap<String, Object>)data;
					if(couponVO.containsKey("couponCode")) {
						couponCode = Utility.objectToString(couponVO.get("couponCode"));
					}
					AjaxInfo couponBatchAjax = feignCouponService.queryPage(Integer.parseInt(Utility.objectToString(couponVO.get("tCouponBatchId"))), null);
					if(Utility.isNotEmpty(couponBatchAjax.getData())) {
						PageInfo couponBatchVO = JSONObject.parseObject(JSONObject.toJSONString(couponBatchAjax.getData()), PageInfo.class);
						if(Utility.isNotEmpty(couponBatchVO.getList())) {
							Map<String, Object> couponBatchMap = (Map<String, Object>)couponBatchVO.getList().get(0);
							if(couponBatchMap.containsKey("couponType")) {
								Integer couponTypeId = Integer.valueOf(Utility.objectToString(couponBatchMap.get("couponType")));
								couponType = dictValueExportService.getDictValueNameById(OrderConstants.ACTIVITY_TYPE, couponTypeId);
							}
						}
					}
				}
				// 优惠码
				activityDataVO.setCouponCode(couponCode);
				// 优惠券类型
				activityDataVO.setCouponType(couponType);
			}
			// 活动编号/活动名称
			if(Utility.isNotEmpty(salesOrderVO.getActivityId())) {
				AjaxInfo activityAjax = feignActivityService.queryById(salesOrderVO.getActivityId());
				Object data = activityAjax.getData();
				String activityCode = "";
				String activityName = "";
				if(Utility.isNotEmpty(data)) {
					HashMap<String, Object> activityVO = (HashMap<String, Object>)data;
					if(activityVO.containsKey("activityId")) {
						activityCode = Utility.objectToString(activityVO.get("activityId"));
					}
					if(activityVO.containsKey("activityName")) {
						activityName = Utility.objectToString(activityVO.get("activityName"));
					}
				}
				activityDataVO.setActivityCode(activityCode);
				activityDataVO.setActivityName(activityName);
			}
			OrderGoodsVO orderGoodsVO = new OrderGoodsVO();
			orderGoodsVO.setOrderId(salesOrderVO.getSalesOrderId());
			// 根据订单id查询订单商品数据
			ExecuteResult<List<OrderGoodsVO>> orderGoodsExcuteResult = orderGoodsExportService.queryList(orderGoodsVO);
			if(Utility.isNotEmpty(orderGoodsExcuteResult.getResult())) {
				List<OrderGoodsVO> orderGoodsList = orderGoodsExcuteResult.getResult();
				for (OrderGoodsVO orderGoods : orderGoodsList) {
					if(Utility.isNotEmpty(orderGoods.getGoodsFrameNumber())) {
						// 车架号
						activityDataVO.setGoodsFrameNumber(orderGoods.getGoodsFrameNumber());
						// 商品SN码
						activityDataVO.setGoodsSn(orderGoods.getGoodsSn());
						// 零售活动报表基础数据
						result.add(activityDataVO);
					}
				}
			}
		}
		return result;
	}

	@Override
	public ExecuteResult<PageInfo<NotBuyVO>> queryNotBuyCustomer(NotBuyVO notBuyVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderExportServiceImpl-queryNotBuyCustomer", JSONObject.toJSONString(notBuyVO));
		ExecuteResult<PageInfo<NotBuyVO>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			// 未购买消费者统计
			List<NotBuyVO> notBuyCustomerList = notBuyCustomerDetails(notBuyVO);
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
				List<NotBuyVO> StatisticsNotBuyList = null;
				if (end > totalCount) {
					StatisticsNotBuyList = notBuyCustomerList.subList(start,totalCount);
				} else {
					StatisticsNotBuyList = notBuyCustomerList.subList(start,end);
				}
				PageInfo<NotBuyVO> pageInfo = new PageInfo<>(StatisticsNotBuyList);
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
				result.setCode(Constants.ERROR_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			}
		} catch (Exception e) {
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "SalesOrderExportServiceImpl-queryNotBuyCustomer", JSONObject.toJSONString(e.toString()));
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-queryNotBuyCustomer", JSONObject.toJSONString(result));
		return result;
	}
	
	// 未购买消费者统计信息补全
	public List<NotBuyVO> notBuyCustomerDetails(NotBuyVO notBuyVO) {
		// 未购买消费者统计
		List<NotBuyVO> notBuyCustomerList = new ArrayList<>();;
		try {
			// 未购买消费者
			ExecuteResult<List<NotBuyVO>> notBuyCustomerExecuteList = notBuyExportService.queryList(notBuyVO);
			SalesOrderVO salesOrderVO = new SalesOrderVO();
			salesOrderVO.setBeginSearchDate(notBuyVO.getBeginSearchDate());
			salesOrderVO.setEndSearchDate(notBuyVO.getEndSearchDate());
			salesOrderVO.setStoreId(notBuyVO.getStoreId());
			salesOrderVO.setSalesOrderStatus(OrderConstants.ORDER_STATUS_CANCLE);
			// 已取消订单消费者
			ExecuteResult<List<SalesOrderVO>> cancleCustomerExecuteList = salesOrderExportService.queryList(salesOrderVO);
			notBuyCustomerList = new ArrayList<>();
			// 补全未购买消费者信息
			if (Utility.isNotEmpty(notBuyCustomerExecuteList.getResult())) {
				notBuyCustomerList = notBuyExportService.handleNotBuyList(notBuyCustomerExecuteList.getResult());
				if(Utility.isNotEmpty(notBuyCustomerList)) {
					for (NotBuyVO vo : notBuyCustomerList) {
						vo.setReportType("未购买上报");
					}
				}
			}
			// 已取消订单消费者信息
			List<NotBuyVO> cancleCustomerList = new ArrayList<>();
			if(Utility.isNotEmpty(cancleCustomerExecuteList.getResult())) {
				for ( SalesOrderVO vo : cancleCustomerExecuteList.getResult()) {
					ExecuteResult<List<OrderGoodsVO>> vehicleExecuteList = orderGoodsExportService.queryVehicle(vo.getSalesOrderId());
					List<OrderGoodsVO> vehicleList = vehicleExecuteList.getResult();
					if(Utility.isNotEmpty(vehicleList)) {
						NotBuyVO notBuy = new NotBuyVO();
						Integer goodsId = 0;
						String intentionVehicleName = "";
						for( OrderGoodsVO orderGoodsVO : vehicleList ) {
							// 消费者id
							notBuy.setCustomerId(vo.getCustomerId());
							// 门店id
							notBuy.setStoreId(vo.getStoreId());
							// 取消原因id
							notBuy.setNotBuyReason(vo.getCancelReason());
							// 整车(车架号不为空)
							if (Utility.isNotEmpty(orderGoodsVO.getGoodsFrameNumber()) && !goodsId.equals(orderGoodsVO.getGoodsId())) {
								goodsId = orderGoodsVO.getGoodsId();
								AjaxInfo goodsAjax = feignGoodsService
										.queryGoodsById(orderGoodsVO.getGoodsId().longValue());
								Object feignResult = goodsAjax.getData();
								if (Utility.isNotEmpty(goodsAjax.getData())) {
									FeignGoodsCategoryVO feignGoodsVO = JSONObject
											.parseObject(JSONObject.toJSONString(feignResult), FeignGoodsCategoryVO.class);
									if (Utility.isNotEmpty(feignGoodsVO)) {
										AjaxInfo vehicleNameAjax = feignGoodsCategoryService.queryVehicleByCategoryId(feignGoodsVO.getGoodsThirdCategoryId());
										if (Utility.isNotEmpty(vehicleNameAjax.getData())) {
											String vehicleName = Utility.objectToString(vehicleNameAjax.getData());
											if (Utility.isNotEmpty(intentionVehicleName)) {
												intentionVehicleName += "\n" + vehicleName;
											} else {
												intentionVehicleName = vehicleName;
											}
										}
									}
								}
							} 
							notBuy.setIntentionVehicleName(intentionVehicleName);
						}
						cancleCustomerList.add(notBuy);
					}
					
				}
			}
			if(Utility.isNotEmpty(cancleCustomerList)) {
				List<NotBuyVO> customerList = notBuyExportService.handleNotBuyList(cancleCustomerList);
				if(Utility.isNotEmpty(customerList)) {
					for (NotBuyVO vo : customerList) {
						vo.setReportType("已取消订单");
						notBuyCustomerList.add(vo);
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notBuyCustomerList;
	}

	@Override
	public ExecuteResult<List<NotBuyVO>> exportNotBuyCustomer(NotBuyVO notBuyVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderExportServiceImpl-exportNotBuyCustomer", JSONObject.toJSONString(notBuyVO));
		ExecuteResult<List<NotBuyVO>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			// 未购买消费者统计
			List<NotBuyVO> notBuyCustomerList = notBuyCustomerDetails(notBuyVO);
			if(Utility.isNotEmpty(notBuyCustomerList)) {
				result.setResult(notBuyCustomerList);
				flag = true;
			}
			if(flag) {
				result.setCode(Constants.SUCCESS_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			} else {
				result.setCode(Constants.ERROR_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			}
		} catch (Exception e) {
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "SalesOrderExportServiceImpl-exportNotBuyCustomer", JSONObject.toJSONString(e.toString()));
			e.printStackTrace();
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-exportNotBuyCustomer", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<SalesVolumeVO> toDayAmount(QueryParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderExportServiceImpl-toDayAmount", JSONObject.toJSONString(paramVO));
		ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
		try {
			// 今日销售额和订单数
			result = statisticsService.toDayAmount(paramVO);
		} catch (Exception e) {
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "SalesOrderExportServiceImpl-toDayAmount", JSONObject.toJSONString(e.toString()));
			e.printStackTrace();
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-toDayAmount", JSONObject.toJSONString(result));
		return result;
	}

}

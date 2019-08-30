//package com.camelot.order.api;
//
//import com.camelot.common.bean.AjaxInfo;
//import com.camelot.common.bean.ExecuteResult;
//import com.camelot.common.bean.Page;
//import com.camelot.order.common.Constants;
//import com.camelot.order.common.OrderConstants;
//import com.camelot.order.common.utils.CheckUtil;
//import com.camelot.order.common.utils.ResultUtil;
//import com.camelot.order.common.utils.Utility;
//import com.camelot.order.common.utils.excel.ExportExcel;
//import com.camelot.order.export.service.StatisticsExportService;
//import com.camelot.order.export.vo.*;
//import com.camelot.order.export.vo.excelvo.ActivityDataExportVO;
//import com.camelot.order.export.vo.excelvo.NotBuyExportVO;
//import com.camelot.order.export.vo.excelvo.SalesReportExportVO;
//import com.github.pagehelper.PageInfo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.web.bind.ServletRequestDataBinder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.regex.Pattern;
//
///**
// * <p>Description: [订单-统计]</p>
// *
// * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
// * @version 1.0
// * 北京柯莱特科技有限公司 易用云
// * @ClassName ApiStatistics.java
// * Created on 2019年4月16日.
// */
//@RestController
//@RequestMapping("statistics")
//@Api(value="统计", tags="订单统计", description="订单统计")
//public class ApiStatistics {
//
//	@Autowired
//	private StatisticsExportService statisticsExportService;
//
//	@RequestMapping(value = "storeSales", method = RequestMethod.GET)
//	@ApiOperation(value="门店销售报表列表")
//	public AjaxInfo queryStoreSales(@RequestParam(value="orgStr",required=false) String orgStr, @RequestParam(value = "storeStr", required = false) String storeIds,@RequestParam(value = "storeIds", required = false) String storeId, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "codeOrName", required = false) String codeOrName, @ModelAttribute Page page, @RequestParam(value = "salesOrderStatus", required = false) String salesOrderStatus, @RequestParam(value = "customerId", required = false) String customerId) {
//		QueryParamVO paramVO = new QueryParamVO();
//		if(Utility.isNotEmpty(codeOrName)) {
//			String regEx = OrderConstants.ACTIVITY_CODE_PREFIX+".*";
//			if(Pattern.matches(regEx, codeOrName)) {
//				paramVO.setActivityNumber(codeOrName);
//			} else {
//				paramVO.setActivityName(codeOrName);
//			}
//		}
//		// 城市ids
//		paramVO.setOrgIds(orgStr);
//		// 门店ids
//		paramVO.setStoreIds(storeIds);
//		if(Utility.isNotEmpty(storeId)) {
//			if(Utility.isNotEmpty(storeIds)) {
//				List<String> storeIdsList = Arrays.asList(storeIds.split(","));
//				List<String> storeIdList = Arrays.asList(storeId.split(","));
//				List<String> storeIdAll = new ArrayList<>();
//				storeIdAll.addAll(storeIdsList);
//				storeIdAll.retainAll(storeIdList);
//				String storeIdRetain = Utility.objectToString(storeIdAll).replaceAll(" ", "");
//				paramVO.setStoreIds(storeIdRetain.substring(1, storeIdRetain.length()-1));
//			} else {
//				paramVO.setStoreIds(storeId);
//			}
//		}
//		// 开始时间
//		paramVO.setStartDate(startDate);
//		// 结束时间
//		paramVO.setEndDate(endDate);
//		// 分页
//		paramVO.setPage(page);
//		// 消费者Id
//		if(Utility.isNotEmpty(customerId)) {
//			paramVO.setCustomerId(Integer.parseInt(customerId));
//		}
//		if(Utility.isNotEmpty(salesOrderStatus)) {
//			paramVO.setSalesOrderStatus(salesOrderStatus);
//		} else {
//			paramVO.setSalesOrderStatus(OrderConstants.ORDER_STATUS_FINISH+","+OrderConstants.ORDER_STATUS_RETURN);
//		}
//		ExecuteResult<PageInfo<SalesReportVO>> querySalesReportList = statisticsExportService.querySalesReportList(paramVO);
//		AjaxInfo result = ResultUtil.getSelectPageListAjaxInfo(querySalesReportList);
//		return result;
//	}
//
//	@RequestMapping(value = "salesReportExcel", method = RequestMethod.GET)
//	@ApiOperation(value="门店销售报表导出")
//	public void exportSalesReport(HttpServletResponse response, @RequestParam(value="orgStr",required=false) String orgStr, @RequestParam(value = "storeStr", required = false) String storeIds, @RequestParam(value = "storeIds", required = false) String storeId, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "codeOrName", required = false) String codeOrName, @RequestParam(value = "salesOrderStatus", required = false) String salesOrderStatus, @RequestParam(value = "customerId", required = false) String customerId) {
//		QueryParamVO paramVO = new QueryParamVO();
//		if(Utility.isNotEmpty(codeOrName)) {
//			String regEx = OrderConstants.ACTIVITY_CODE_PREFIX+".*";
//			if(Pattern.matches(regEx, codeOrName)) {
//				paramVO.setActivityNumber(codeOrName);
//			} else {
//				paramVO.setActivityName(codeOrName);
//			}
//		}
//		paramVO.setOrgIds(orgStr);
//		paramVO.setStoreIds(storeIds);
//		if(Utility.isNotEmpty(storeId)) {
//			if(Utility.isNotEmpty(storeIds)) {
//				List<String> storeIdsList = Arrays.asList(storeIds.split(","));
//				List<String> storeIdList = Arrays.asList(storeId.split(","));
//				List<String> storeIdAll = new ArrayList<>();
//				storeIdAll.addAll(storeIdsList);
//				storeIdAll.retainAll(storeIdList);
//				String storeIdRetain = Utility.objectToString(storeIdAll).replaceAll(" ", "");
//				paramVO.setStoreIds(storeIdRetain.substring(1, storeIdRetain.length()-1));
//			} else {
//				paramVO.setStoreIds(storeId);
//			}
//		}
//		paramVO.setStartDate(startDate);
//		paramVO.setEndDate(endDate);
//		if(Utility.isNotEmpty(salesOrderStatus)) {
//			paramVO.setSalesOrderStatus(salesOrderStatus);
//		} else {
//			paramVO.setSalesOrderStatus(OrderConstants.ORDER_STATUS_FINISH+","+OrderConstants.ORDER_STATUS_RETURN);
//		}
//		// 消费者Id
//		if(Utility.isNotEmpty(customerId)) {
//			paramVO.setCustomerId(Integer.parseInt(customerId));
//		}
//		ExecuteResult<List<SalesReportVO>> queryResult = statisticsExportService.exportSalesReport(paramVO);
//		if (CheckUtil.checkResultListIsNotNull(queryResult)) {
//			String fileName = "门店销售报表导出";
//			try {
//	    		//writeFile:将导出数据输出到指定的文件
//	    		//write：将数据输出到客户端（需要HttpServletResponse对象）
//				new ExportExcel(fileName,SalesReportExportVO.class, 1).setDataList(queryResult.getResult()).write(response, fileName +".xlsx").dispose();
//	    	} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 根据门店ids/活动名称/活动编号/门店名称/门店编号,查询数据
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value = "activity",method = RequestMethod.GET)
//	@ApiOperation(value="零售活动报表")
//	public AjaxInfo queryByActivityId(@ModelAttribute Page page, @RequestParam(value="orgStr",required=false) String orgStr, @RequestParam(value = "storeStr", required = false) String storeIds, @RequestParam(value = "activityName", required = false) String activityName, @RequestParam(value = "activityNumber", required = false) String activityNumber, @RequestParam(value = "storeName", required = false) String storeName, @RequestParam(value = "storeNumber", required = false) String storeNumber){
//	   QueryParamVO paramVO = new QueryParamVO();
//	   // 分页
//	   paramVO.setPage(page);
//	   // 城市ids
//	   paramVO.setOrgIds(orgStr);
//	   // 门店ids
//	   paramVO.setStoreIds(storeIds);
//	   // 活动编号
//	   paramVO.setActivityNumber(activityNumber);
//	   // 活动名称
//	   paramVO.setActivityName(activityName);
//	   // 门店编号
//	   paramVO.setStoreNumber(storeNumber);
//	   // 门店名称
//	   paramVO.setStoreName(storeName);
//	   ExecuteResult<PageInfo<ActivityDataVO>> result = statisticsExportService.queryPageByActivity(paramVO);
//	   AjaxInfo info=ResultUtil.getSelectPageListAjaxInfo(result);
//	   return info;
//	}
//
//	@RequestMapping(value = "retailActivityExcel",method = RequestMethod.GET)
//	@ApiOperation(value="零售活动报表导出")
//	public void exportRetailActivity(HttpServletResponse response, @RequestParam(value="orgStr",required=false) String orgStr, @RequestParam(value = "storeStr", required = false) String storeIds, @RequestParam(value = "activityName", required = false) String activityName, @RequestParam(value = "activityNumber", required = false) String activityNumber, @RequestParam(value = "storeName", required = false) String storeName, @RequestParam(value = "storeNumber", required = false) String storeNumber){
//	   QueryParamVO paramVO = new QueryParamVO();
//	   // 城市ids
//	   paramVO.setOrgIds(orgStr);
//	   // 门店ids
//	   paramVO.setStoreIds(storeIds);
//	   // 活动编号
//	   paramVO.setActivityNumber(activityNumber);
//	   // 活动名称
//	   paramVO.setActivityName(activityName);
//	   // 门店编号
//	   paramVO.setStoreNumber(storeNumber);
//	   // 门店名称
//	   paramVO.setStoreName(storeName);
//	   ExecuteResult<List<ActivityDataVO>> queryResult = statisticsExportService.queryByActivity(paramVO);
//	   if (!Constants.ERROR_CODE.equals(queryResult.getCode())) {
//			String fileName = "零售活动报表导出";
//			try {
//	    		//writeFile:将导出数据输出到指定的文件
//	    		//write：将数据输出到客户端（需要HttpServletResponse对象）
//				new ExportExcel(fileName,ActivityDataExportVO.class, 1).setDataList(queryResult.getResult()).write(response, fileName +".xlsx").dispose();
//	    	} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@RequestMapping(value = "salesOrder", method = RequestMethod.GET)
//	@ApiOperation(value="销售订单统计")
//	public AjaxInfo querySalesOrder(@RequestParam(value="orgStr",required=false) String orgStr, @RequestParam(value = "storeStr", required = false) String storeIds, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam(value="userId",required=false) String userId) {
//		QueryParamVO paramVO = new QueryParamVO();
//		// 城市ids
//		paramVO.setOrgIds(orgStr);
//		// 门店ids
//		paramVO.setStoreIds(storeIds);
//		// 开始时间
//		paramVO.setStartDate(startDate);
//		// 结束时间
//		paramVO.setEndDate(endDate);
//		// 用户id
//		if(Utility.isNotEmpty(userId)) {
//			paramVO.setUserId(Integer.parseInt(userId));
//		}
//		ExecuteResult<SalesVolumeVO> executeResult = statisticsExportService.queryStatisticsResult(paramVO);
//		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
//		return result;
//	}
//
//	@RequestMapping(value = "goodsVolume", method = RequestMethod.GET)
//	@ApiOperation(value="销售订单商品销量统计列表")
//	public AjaxInfo queryGoodsVolume(@RequestParam(value="orgStr",required=false) String orgStr, @RequestParam(value = "storeStr", required = false) String storeIds, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam(value = "goodsFirstCategoryId", required = false) String goodsFirstCategoryId, @ModelAttribute Page page) {
//		QueryParamVO paramVO = new QueryParamVO();
//		// 一级分类id
//		if(Utility.isNotEmpty(goodsFirstCategoryId)) {
//			paramVO.setGoodsFirstCategoryId(Integer.parseInt(goodsFirstCategoryId));
//		}
//		// 分页
//		paramVO.setPage(page);
//		// 城市ids
//		paramVO.setOrgIds(orgStr);
//		// 门店ids
//		paramVO.setStoreIds(storeIds);
//		// 开始时间
//		paramVO.setStartDate(startDate);
//		// 结束
//		paramVO.setEndDate(endDate);
//		ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> executeResult = statisticsExportService.queryGoodsVolume(paramVO);
//		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
//		return result;
//	}
//
//	@RequestMapping(value = "order", method = RequestMethod.GET)
//	@ApiOperation(value="查询订单统计(图)")
//	public AjaxInfo queryOrderStatistics(@RequestParam(value="orgStr",required=false) String orgStr, @RequestParam(value = "storeStr", required = false) String storeIds, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam(value="userId",required=false) String userId) {
//		QueryParamVO paramVO = new QueryParamVO();
//		// 城市ids
//		paramVO.setOrgIds(orgStr);
//		// 门店ids
//		paramVO.setStoreIds(storeIds);
//		// 开始时间
//		paramVO.setStartDate(startDate);
//		// 结束
//		paramVO.setEndDate(endDate);
//		// 用户id
//		if(Utility.isNotEmpty(userId)) {
//			paramVO.setUserId(Integer.parseInt(userId));
//		}
//		ExecuteResult<Map<String, Object>> executeResult = statisticsExportService.queryOrderStatistics(paramVO);
//		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
//		return result;
//	}
//
//	@RequestMapping(value = "toDayOrder", method = RequestMethod.GET)
//	@ApiOperation(value="查询今日订单统计(图)")
//	public AjaxInfo queryToDayOrderStatistics(@RequestParam(value="orgStr",required=false) String orgStr, @RequestParam(value = "storeStr", required = false) String storeIds, @RequestParam("startDate") String startDate, @RequestParam(value="userId",required=false) String userId) {
//		QueryParamVO paramVO = new QueryParamVO();
//		// 城市ids
//		paramVO.setOrgIds(orgStr);
//		// 门店ids
//		paramVO.setStoreIds(storeIds);
//		// 某天
//		paramVO.setStartDate(startDate);
//		// 用户id
//		if(Utility.isNotEmpty(userId)) {
//			paramVO.setUserId(Integer.parseInt(userId));
//		}
//		ExecuteResult<List<StatisticsFigureVO>> executeResult = statisticsExportService.queryToDayOrderStatistics(paramVO);
//		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
//		return result;
//	}
//
//	@RequestMapping(value = "goods", method = RequestMethod.GET)
//	@ApiOperation(value="手机端查询商品统计")
//	public AjaxInfo queryGoodsList(@ModelAttribute Page page, @RequestParam(value = "storeStr", required = false) String storeIds, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam(value = "sortFlag",defaultValue = "1") Integer sortFlag, @RequestParam(value="userId",required=false) String userId) {
//		QueryParamVO paramVO = new QueryParamVO();
//		// 门店ids
//		paramVO.setStoreIds(storeIds);
//		// 开始时间
//		paramVO.setStartDate(startDate);
//		// 结束时间
//		paramVO.setEndDate(endDate);
//		// 分页
//		paramVO.setPage(page);
//		// 排序标记(0零售价升,1降,2升,3降)
//		paramVO.setSortFlag(sortFlag);
//		// 用户id
//		if(Utility.isNotEmpty(userId)) {
//			paramVO.setUserId(Integer.parseInt(userId));
//		}
//		ExecuteResult<PageInfo<StatisticsGoodsVO>> executeResult = statisticsExportService.queryGoodsList(paramVO);
//		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
//		return result;
//	}
//
//	@RequestMapping(value = "notBuy", method = RequestMethod.GET)
//	@ApiOperation(value="查询未购买上报统计")
//	public AjaxInfo queryNotBuy(@RequestParam(value="orgStr",required=false) String orgStr, @RequestParam(value = "storeStr", required = false) String storeIds, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam(value = "storeName",required = false) String storeName, @RequestParam(value = "storeNumber",required = false) String storeNumber, @ModelAttribute Page page) {
//		QueryParamVO paramVO = new QueryParamVO();
//		// 城市ids
//		paramVO.setOrgIds(orgStr);
//		// 门店ids
//		paramVO.setStoreIds(storeIds);
//		// 开始时间
//		paramVO.setStartDate(startDate);
//		// 结束
//		paramVO.setEndDate(endDate);
//		// 门店名称
//		paramVO.setStoreName(storeName);
//		// 门店编号
//		paramVO.setStoreNumber(storeNumber);
//		// 分页
//		paramVO.setPage(page);
//		// 订单状态已取消
//		paramVO.setSalesOrderStatus(String.valueOf(OrderConstants.ORDER_STATUS_CANCLE));
//		ExecuteResult<PageInfo<StatisticsNotBuyVO>> executeResult = statisticsExportService.queryNotBuy(paramVO);
//		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
//		return result;
//	}
//
//	@RequestMapping(value = "source", method = RequestMethod.GET)
//	@ApiOperation(value="门店顾客来源分析")
//	public AjaxInfo queryCustomerSource(@RequestParam("storeId") String storeId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
//		QueryParamVO paramVO = new QueryParamVO();
//		// 门店ids
//		paramVO.setStoreIds(storeId);
//		// 开始时间
//		paramVO.setStartDate(startDate);
//		// 结束
//		paramVO.setEndDate(endDate);
//		// 订单状态已取消
//		paramVO.setSalesOrderStatus(String.valueOf(OrderConstants.ORDER_STATUS_CANCLE));
//		ExecuteResult<List<StatisticsSourceVO>> executeResult = statisticsExportService.queryCustomerSource(paramVO);
//		return ResultUtil.getSelectListAjaxInfo(executeResult);
//	}
//
//	@RequestMapping(value = "cause", method = RequestMethod.GET)
//	@ApiOperation(value="未购买原因分析")
//	public AjaxInfo queryNotBuyCause(@RequestParam("storeId") String storeId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
//		QueryParamVO paramVO = new QueryParamVO();
//		// 门店ids
//		paramVO.setStoreIds(storeId);
//		// 开始时间
//		paramVO.setStartDate(startDate);
//		// 结束
//		paramVO.setEndDate(endDate);
//		// 订单状态已取消
//		paramVO.setSalesOrderStatus(String.valueOf(OrderConstants.ORDER_STATUS_CANCLE));
//		ExecuteResult<List<StatisticsSourceVO>> executeResult = statisticsExportService.queryNotBuyCause(paramVO);
//		AjaxInfo result = ResultUtil.getSelectListAjaxInfo(executeResult);
//		return result;
//	}
//
//	@RequestMapping(value = "carAssort", method = RequestMethod.GET)
//	@ApiOperation(value="查询有效订单整车分类统计列表")
//	public AjaxInfo queryCarAssort(@RequestParam(value="orgStr",required=false) String orgStr, @RequestParam(value = "storeStr", required = false) String storeIds,
//			@RequestParam(value = "storeIds", required = false) String storeId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
//			@RequestParam(value = "goodsCategoryId", required = false) String goodsCategoryId, @RequestParam(value = "orgId", required = false) String orgId,
//			@RequestParam(value = "sourceId", required = false) String sourceId, @RequestParam(value = "goodsCategoryGrade", required = false) String goodsCategoryGrade,
//			@RequestParam(value = "orgCategoryGrade", required = false) String orgCategoryGrade, @ModelAttribute Page page, @RequestParam(value = "goodsAttributeId", required = false) String goodsAttributeId) {
//		QueryParamVO paramVO = new QueryParamVO();
//		// 分页
//		paramVO.setPage(page);
//		// 城市ids
//		paramVO.setOrgIds(orgStr);
//		// 门店ids
//		paramVO.setStoreIds(storeIds);
//		if(Utility.isNotEmpty(storeId)) {
//			if(Utility.isNotEmpty(storeIds)) {
//				List<String> storeIdsList = Arrays.asList(storeIds.split(","));
//				List<String> storeIdList = Arrays.asList(storeId.split(","));
//				List<String> storeIdAll = new ArrayList<>();
//				storeIdAll.addAll(storeIdsList);
//				storeIdAll.retainAll(storeIdList);
//				String storeIdRetain = Utility.objectToString(storeIdAll).replaceAll(" ", "");
//				paramVO.setStoreIds(storeIdRetain.substring(1, storeIdRetain.length()-1));
//			} else {
//				paramVO.setStoreIds(storeId);
//			}
//		}
//		// 开始时间
//		paramVO.setStartDate(startDate);
//		// 结束
//		paramVO.setEndDate(endDate);
//		// 机构维度
//		if(Utility.isNotEmpty(orgId)) {
//			paramVO.setOrgId(Integer.parseInt(orgId));
//		}
//		// 消费者来源
//		if(Utility.isNotEmpty(sourceId)) {
//			paramVO.setSourceId(Integer.parseInt(sourceId));
//		}
//		// 颜色
//		if(Utility.isNotEmpty(goodsAttributeId)) {
//			paramVO.setGoodsAttributeId(Integer.parseInt(goodsAttributeId));
//		}
//		// 商品分类维度
//		if(Utility.isNotEmpty(goodsCategoryGrade)) {
//			switch (goodsCategoryGrade) {
//			case "2":
//				paramVO.setGoodsSecondCategoryId(Integer.parseInt(goodsCategoryId));
//				break;
//			case "3":
//				paramVO.setGoodsThirdCategoryId(Integer.parseInt(goodsCategoryId));
//				break;
//			default:
//				paramVO.setGoodsFirstCategoryId(Integer.parseInt(goodsCategoryId));
//				break;
//			}
//		}
//		// 机构分类维度
//		if(Utility.isNotEmpty(orgCategoryGrade)) {
//			switch (orgCategoryGrade) {
//			case "1":
//				paramVO.setFirstOrgId(Integer.parseInt(orgId));
//				break;
//			case "2":
//				paramVO.setSecondOrgId(Integer.parseInt(orgId));
//				break;
//			case "3":
//				paramVO.setThirdOrgId(Integer.parseInt(orgId));
//				break;
//			case "4":
//				paramVO.setPartnerId(Integer.parseInt(orgId));
//				break;
//			default:
//				paramVO.setFranchiseeId(Integer.parseInt(orgId));
//				break;
//			}
//		}
//		ExecuteResult<PageInfo<StatisticsGoodsCategoryVO>> executeResult = statisticsExportService.queryCarAssort(paramVO);
//		AjaxInfo result = ResultUtil.getSelectPageListAjaxInfo(executeResult);
//		return result;
//	}
//
//	@RequestMapping(value="notBuyCustomer", method=RequestMethod.GET)
//	@ApiOperation(value="未购买消费者统计分页查询")
//	public AjaxInfo queryNotBuyCustomer(@ModelAttribute NotBuyVO vo,@ModelAttribute Page page){
//		AjaxInfo info = new AjaxInfo();
//		ExecuteResult<PageInfo<NotBuyVO>> result = statisticsExportService.queryNotBuyCustomer(vo, page);
//		info=ResultUtil.getSelectAjaxInfo(result);
//		return info;
//	}
//
//	@RequestMapping(value="notBuyCustomerExcel", method=RequestMethod.GET)
//	@ApiOperation(value="未购买消费者统计导出")
//	public void exportNotBuyCustomer(HttpServletResponse response, @ModelAttribute NotBuyVO vo){
//		ExecuteResult<List<NotBuyVO>> queryResult = statisticsExportService.exportNotBuyCustomer(vo);
//		if (!Constants.ERROR_CODE.equals(queryResult.getCode())) {
//			String fileName = "未购买消费者统计导出";
//			try {
//	    		//writeFile:将导出数据输出到指定的文件
//	    		//write：将数据输出到客户端（需要HttpServletResponse对象）
//				new ExportExcel(fileName,NotBuyExportVO.class, 1).setDataList(queryResult.getResult()).write(response, fileName +".xlsx").dispose();
//	    	} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@RequestMapping(value="toDayAmount", method=RequestMethod.GET)
//	@ApiOperation(value="今日销售额和订单数")
//	public AjaxInfo toDayAmount(@RequestParam(value = "storeStr", required = false) String storeStr, @RequestParam(value="userId",required=false) String userId) {
//		QueryParamVO paramVO = new QueryParamVO();
//		// 门店ids
//		paramVO.setStoreIds(storeStr);
//		// 用户id
//		if(Utility.isNotEmpty(userId)) {
//			paramVO.setUserId(Integer.parseInt(userId));
//		}
//		// 订单状态
//		paramVO.setSalesOrderStatus(OrderConstants.ORDER_STATUS_FINISH + "," + OrderConstants.ORDER_STATUS_RETURN);
//		ExecuteResult<SalesVolumeVO> executeResult = statisticsExportService.toDayAmount(paramVO);
//		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
//		return result;
//	}
//
//	/**
//	 *@Description 对前端传值的String类型转换为Date类型
//	 */
//	@InitBinder
//    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//    }
//
//}

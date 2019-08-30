package com.camelot.order.service.impl.export;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.service.ComplianceExportService;
import com.camelot.order.export.service.SalesOrderExportService;
import com.camelot.order.export.vo.DifferenceOrderVO;
import com.camelot.order.export.vo.GoodsVO;
import com.camelot.order.export.vo.OrderGoodsVO;
import com.camelot.order.export.vo.PartnerTraderVO;
import com.camelot.order.export.vo.ReturnGoodsVO;
import com.camelot.order.export.vo.SalesOrderVO;
import com.camelot.order.export.vo.SameAgencyVO;
import com.camelot.order.export.vo.StoreVO;
import com.camelot.order.export.vo.param.BaseParamVO;
import com.camelot.order.export.vo.param.DifferenceParamVO;
import com.camelot.order.export.vo.param.ReturnGoodsParamVO;
import com.camelot.order.export.vo.param.SameNameParamVO;
import com.camelot.order.feign.config.FeignGoodsService;
import com.camelot.order.feign.config.FeignStoreService;
import com.camelot.order.service.ComplianceService;
import com.github.pagehelper.PageInfo;

@Service
public class ComplianceExportServiceImpl implements ComplianceExportService {
	
	private static Logger log = Log.get(ComplianceExportServiceImpl.class);
	
	@Autowired
	private ComplianceService complianceService;
	@Autowired
	private FeignStoreService feignStoreService;
	@Autowired
	private FeignGoodsService feignGoodsService;
	@Autowired
	private SalesOrderExportService salesOrderExportService;

	@Override
	public ExecuteResult<PageInfo<DifferenceOrderVO>> compliancePageDifference(DifferenceParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceExportServiceImpl-complianceDifference", JSONObject.toJSONString(paramVO));
		ExecuteResult<PageInfo<DifferenceOrderVO>> result = new ExecuteResult<>();
		boolean flag = false;
		List<DifferenceOrderVO> resultList = new ArrayList<>();
		Map<Integer, SalesOrderVO> salesOrderMap = new HashMap<>();
		Map<Integer, StoreVO> storeMap = new HashMap<>();
		HashSet<Integer> salesOrderIdSet = new HashSet<>();
		HashSet<Integer> storeIdsSet = new HashSet<>();
		try {
			// 订单状态IdList
			List<Integer> salesOrderStatus = new ArrayList<>();
			// 已完成
			salesOrderStatus.add(OrderConstants.ORDER_STATUS_FINISH);
			// 已退货
			salesOrderStatus.add(OrderConstants.ORDER_STATUS_RETURN);
			paramVO.setSalesOrderStatusList(salesOrderStatus);
			// 查询销售订单数据List
			List<SalesOrderVO> salesOrderList = queryListSalesOrder(paramVO);
			if(Utility.isNotEmpty(salesOrderList)) {
				for(SalesOrderVO domain : salesOrderList) {
					salesOrderMap.put(domain.getSalesOrderId(), domain);
					salesOrderIdSet.add(domain.getSalesOrderId());
					storeIdsSet.add(domain.getStoreId());
				}
				// 销售订单IdSet
				paramVO.setSalesOrderIdSet(salesOrderIdSet);
				// 调用基础服务的门店接口，获取门店名称/所属加盟商/所属合伙人信息
				String requestStoreIds = storeIdsSet.toString().replace(" ", "");
				AjaxInfo storeAjax = feignStoreService.queryStoreList(requestStoreIds.substring(1, requestStoreIds.length()-1));
				if(Utility.isNotEmpty(storeAjax.getData())) {
					String jsonStr = JSONObject.toJSONString(storeAjax.getData());
					List<StoreVO> storeList = JSONObject.parseArray(jsonStr,StoreVO.class);
					if(Utility.isNotEmpty(storeList)) {
						for(StoreVO storeVO: storeList) {
							storeMap.put(storeVO.getStoreId(), storeVO);
						}
						// 查询商品订单数据
						ExecuteResult<PageInfo<OrderGoodsVO>> executeResult = complianceService.queryPageDifference(paramVO,page);
						if(Utility.isNotEmpty(executeResult.getResult())){
							List<OrderGoodsVO> queryResult = executeResult.getResult().getList();
							// 差异商品订单详细信息补全
							resultList = detailDifference(salesOrderMap, storeMap, queryResult);
							@SuppressWarnings("rawtypes")
							PageInfo pageInfo = executeResult.getResult();
				            pageInfo.setList(resultList);
				            result.setResult(pageInfo);
				            flag = true;
						}
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
            Log.error(log, "\n 方法[{}]，异常：[{}]", "ComplianceExportServiceImpl-complianceDifference", e.getMessage());
            return result;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceExportServiceImpl-complianceDifference", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<List<DifferenceOrderVO>> complianceListDifference(DifferenceParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceExportServiceImpl-complianceDifferenceExcel", JSONObject.toJSONString(paramVO));
		ExecuteResult<List<DifferenceOrderVO>> result = new ExecuteResult<>();
		boolean flag = false;
		List<DifferenceOrderVO> resultList = new ArrayList<>();
		Map<Integer, SalesOrderVO> salesOrderMap = new HashMap<>();
		Map<Integer, StoreVO> storeMap = new HashMap<>();
		HashSet<Integer> salesOrderIdSet = new HashSet<>();
		HashSet<Integer> storeIdsSet = new HashSet<>();
		try {
			// 订单状态IdList
			List<Integer> salesOrderStatus = new ArrayList<>();
			// 已完成
			salesOrderStatus.add(OrderConstants.ORDER_STATUS_FINISH);
			// 已退货
			salesOrderStatus.add(OrderConstants.ORDER_STATUS_RETURN);
			paramVO.setSalesOrderStatusList(salesOrderStatus);
			// 查询销售订单数据
			List<SalesOrderVO> salesOrderList = queryListSalesOrder(paramVO);
			if(Utility.isNotEmpty(salesOrderList)) {
				for(SalesOrderVO domain : salesOrderList) {
					salesOrderMap.put(domain.getSalesOrderId(), domain);
					salesOrderIdSet.add(domain.getSalesOrderId());
					storeIdsSet.add(domain.getStoreId());
				}
				// 销售订单IdSet
				paramVO.setSalesOrderIdSet(salesOrderIdSet);
				// 调用基础服务的门店接口，获取门店名称/所属加盟商/所属合伙人信息
				String requestStoreIds = storeIdsSet.toString().replace(" ", "");
				AjaxInfo storeAjax = feignStoreService.queryStoreList(requestStoreIds.substring(1, requestStoreIds.length()-1));
				if(Utility.isNotEmpty(storeAjax) && Utility.isNotEmpty(storeAjax.getData())) {
					String jsonStr = JSONObject.toJSONString(storeAjax.getData());
					List<StoreVO> storeList = JSONObject.parseArray(jsonStr,StoreVO.class);
					if(Utility.isNotEmpty(storeList)) {
						for(StoreVO storeVO: storeList) {
							storeMap.put(storeVO.getStoreId(), storeVO);
						}
						// 查询商品订单数据
						ExecuteResult<List<OrderGoodsVO>> executeResult = complianceService.queryListDifference(paramVO);
						List<OrderGoodsVO> queryResult = executeResult.getResult();
						if(Utility.isNotEmpty(queryResult)){
							// 差异商品订单详细信息补全
							resultList = detailDifference(salesOrderMap, storeMap, queryResult);
				            result.setResult(resultList);
				            flag = true;
						}
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
	        result.setResultMessage(e.getMessage());
	        Log.error(log, "\n 方法[{}]，异常：[{}]", "ComplianceExportServiceImpl-complianceDifferenceExcel", e.getMessage());
	        return result;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceExportServiceImpl-complianceDifferenceExcel", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<PageInfo<ReturnGoodsVO>> complianceReturnGoods(ReturnGoodsParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceExportServiceImpl-complianceReturnGoods", JSONObject.toJSONString(paramVO));
		ExecuteResult<PageInfo<ReturnGoodsVO>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			List<ReturnGoodsVO> returnGoodsList = new ArrayList<>();
			Map<Integer, SalesOrderVO> orderMap = new HashMap<>();
			HashSet<Integer> orderIdsSet = new HashSet<>();
			// 订单状态IdList
			List<Integer> salesOrderStatus = new ArrayList<>();
			// 已退货
			salesOrderStatus.add(OrderConstants.ORDER_STATUS_RETURN);
			paramVO.setSalesOrderStatusList(salesOrderStatus);
			// 查询销售订单数据List
			List<SalesOrderVO> queryOrderListResult = queryListSalesOrder(paramVO);
			// 根据门店ids查询退货订单
			if(Utility.isNotEmpty(queryOrderListResult)) {
				for(SalesOrderVO orderDomain : queryOrderListResult) {
					orderMap.put(orderDomain.getSalesOrderId(), orderDomain);
					orderIdsSet.add(orderDomain.getSalesOrderId());
				}
				// 销售订单IdSet
				paramVO.setSalesOrderIdSet(orderIdsSet);
				// 根据订单ids/商品SN码/车架号,查询商品订单
				ExecuteResult<List<OrderGoodsVO>> orderGoodsExecuteResult = complianceService.queryReturnGoods(paramVO);
				List<OrderGoodsVO> orderGoodsList = orderGoodsExecuteResult.getResult();
				if(Utility.isNotEmpty(orderGoodsList)) {
					for(OrderGoodsVO orderGoods : orderGoodsList) {
						if(Utility.isNotEmpty(orderGoods.getGoodsSn())) {
							// 调用商品服务的商品接口，根据商品id，获取商品服务的商品详细信息
							AjaxInfo goodsAjax = feignGoodsService.queryGoodsList(orderGoods.getGoodsId().toString());
							if(Utility.isNotEmpty(goodsAjax) && Utility.isNotEmpty(goodsAjax.getData())) {
								GoodsVO goods = JSONObject.parseObject(JSONObject.toJSONString(goodsAjax.getData()),GoodsVO.class);
								if(Utility.isEmpty(paramVO.getGoodsName()) || paramVO.getGoodsName().equals(goods.getGoodsName())) {
									ReturnGoodsVO returnGoods = new ReturnGoodsVO();
									/** 商品Id */
									returnGoods.setGoodsId(orderGoods.getGoodsId());
									/** goodsName商品名称*/
									returnGoods.setGoodsName(goods.getGoodsName());
									/** goodsNumber商品编号*/
									returnGoods.setGoodsNumber(goods.getGoodsNumber());
									/** 商品69码 */
									returnGoods.setGoodsBarcode(goods.getGoodsBarcode());
									/** 商品SN */
									returnGoods.setGoodsSn(orderGoods.getGoodsSn());
									/** 车架号 */
									returnGoods.setGoodsFrameNumber(orderGoods.getGoodsFrameNumber());
									/** 生产日期 */
									returnGoods.setProductionDate(goods.getProductionDate());
									/** 退货次数 */
									returnGoods.setReturnNumber(complianceService.queryReturnGoodsCount(orderIdsSet, orderGoods.getGoodsSn()));
									returnGoodsList.add(returnGoods);
								}
							}
						}
					}
					// 当前页
					int pageNum = page.getPageNum();
					// 一页条数
					int pageSize = page.getPageSize();
					// 总数
					int totalCount=returnGoodsList.size();
					// 总页数
					int pageCount = 1;
					if(totalCount > pageSize) {
						pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);
					}
					// 起始条数
					int start=(pageNum-1) * pageSize;
					// 结尾条数
					int end = pageNum==pageCount ? totalCount : pageNum * pageSize;
					List<ReturnGoodsVO> resultList = null;
					if (end > totalCount) {
						resultList = returnGoodsList.subList(start,totalCount);
					} else {
						resultList = returnGoodsList.subList(start,end);
					}
					PageInfo<ReturnGoodsVO> pageInfo = new PageInfo<>(resultList);
					pageInfo.setTotal(totalCount);
					pageInfo.setPageNum(pageNum);
					pageInfo.setPageSize(pageSize);
					result.setResult(pageInfo);
					flag = true;
				}
			}
			if(flag){
				result.setCode(Constants.SUCCESS_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			} else {
				result.setCode(Constants.EMPTY_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			}
		} catch (Exception e) {
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceExportServiceImpl-complianceReturnGoods", e.getMessage());
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.toString());
			e.printStackTrace();
			return result;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceExportServiceImpl-complianceReturnGoods", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<PageInfo<SameAgencyVO>> complianceSameAgency(SameNameParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceExportServiceImpl-complianceSameAgency", JSONObject.toJSONString(paramVO));
		ExecuteResult<PageInfo<SameAgencyVO>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			// 调用基础服务,根据区域ids,查询合伙人/加盟商信息
			AjaxInfo partnerAjax = feignStoreService.queryPartnerList(paramVO.getOrgStr());
			if(Utility.isNotEmpty(partnerAjax.getData())) {
				List<PartnerTraderVO> partnerAllList = JSONObject.parseArray(JSONObject.toJSONString(partnerAjax.getData()),PartnerTraderVO.class);
				if(partnerAllList != null && partnerAllList.size() > 0) {
					// 获取归属机构相同数据
					List<SameAgencyVO> sameAgencyList = partnerTraderDetail(paramVO, partnerAllList);
					// 当前页
					int pageNum = page.getPageNum();
					// 一页条数
					int pageSize = page.getPageSize();
					// 总数
					int totalCount = sameAgencyList.size();
					// 总页数
					int pageCount = 1;
					if(totalCount > pageSize) {
						pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);
					}
					// 起始条数
					int start=(pageNum-1) * pageSize;
					// 结尾条数
					int end = pageNum==pageCount ? totalCount : pageNum * pageSize;
					List<SameAgencyVO> resultList = null;
					if(end > totalCount) {
						resultList = sameAgencyList.subList(start,totalCount);
					} else {
						resultList = sameAgencyList.subList(start,end);
					}
					PageInfo<SameAgencyVO> pageInfo = new PageInfo<>(resultList);
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
			result.setResultMessage(e.getMessage());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceExportServiceImpl-complianceSameAgency", e.getMessage());
			return result;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceExportServiceImpl-complianceSameAgency", result);
		return result;
	}

	@Override
	public ExecuteResult<List<SameAgencyVO>> exportSameName(SameNameParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ComplianceExportServiceImpl-complianceSameAgency", JSONObject.toJSONString(paramVO));
		ExecuteResult<List<SameAgencyVO>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			// 调用基础服务,根据区域ids,查询合伙人/加盟商信息
			AjaxInfo partnerAjax = feignStoreService.queryPartnerList(paramVO.getOrgStr());
			if(Utility.isNotEmpty(partnerAjax) && Utility.isNotEmpty(partnerAjax.getData())) {
				String jsonStr = JSONObject.toJSONString(partnerAjax.getData());
				List<PartnerTraderVO> partnerAllList = JSONObject.parseArray(jsonStr,PartnerTraderVO.class);
				if(partnerAllList != null && partnerAllList.size() > 0) {
					// 获取归属机构相同数据
					List<SameAgencyVO> sameAgencyList = partnerTraderDetail(paramVO, partnerAllList);
					result.setResult(sameAgencyList);
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
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "ComplianceExportServiceImpl-complianceSameAgency", e.getMessage());
			return result;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "ComplianceExportServiceImpl-complianceSameAgency", result);
		return result;
	}

	/**
	 * <p>Description: 查询销售订单数据List</p>
	 * @param paramVO 城市IdList/门店IdList/订单状态IdList/销售编号/开始时间/结束时间
	 * @return 销售订单数据List
	 * @author zhouyou
	 * @date 2019年5月14日
	 */
	private List<SalesOrderVO> queryListSalesOrder(BaseParamVO paramVO){
		// 查询订单数据VO
		SalesOrderVO salesOrderVO = new SalesOrderVO();
		// 城市IdList
		salesOrderVO.setOrgIdList(paramVO.getOrgIdList());
		// 门店IdList
		if(Utility.isNotEmpty(paramVO.getStoreIds())) { // 查询条件(门店)不为空
			List<Integer> storeIdList = Utility.stringToList(paramVO.getStoreIds());
			storeIdList.retainAll(paramVO.getStoreIdList());
			salesOrderVO.setStoreIdList(storeIdList);
		} else {
			salesOrderVO.setStoreIdList(paramVO.getStoreIdList());
		}
		salesOrderVO.setStatusList(paramVO.getSalesOrderStatusList());
		// 销售编号
		salesOrderVO.setSalesOrderNumber(paramVO.getSalesOrderNumber());
		// 开始时间
		salesOrderVO.setBeginSearchDate(paramVO.getStartDate());
		// 结束时间
		salesOrderVO.setEndSearchDate(paramVO.getEndDate());
		// 查询销售订单数据
		ExecuteResult<List<SalesOrderVO>> OrderExecuteResult = salesOrderExportService.queryList(salesOrderVO);
		List<SalesOrderVO> salesOrderList = OrderExecuteResult.getResult();
		return salesOrderList;
	}

	/**
	 * <p>Description: 补全零售差异订单信息</p>
	 * @param salesOrderMap 销售订单Map<Id,VO>
	 * @param storeMap 门店Map<Id,VO>
	 * @param queryResult 商品订单List
	 * @return 零售差异订单详情
	 * @author zhouyou
	 * @date 2019年5月14日
	 */
	private List<DifferenceOrderVO> detailDifference(Map<Integer, SalesOrderVO> salesOrderMap, Map<Integer, StoreVO> storeMap, List<OrderGoodsVO> queryResult){
		List<DifferenceOrderVO> resultList = new ArrayList<>();
		for(OrderGoodsVO orderGoodsDomain: queryResult) {
			DifferenceOrderVO differenceOrderVO = new DifferenceOrderVO();
			// 调用商品服务的商品接口，根据商品id获取商品服务的商品编号/商品名称
			AjaxInfo goodsAjax = feignGoodsService.queryGoodsList(orderGoodsDomain.getGoodsId().toString());
			if(Utility.isNotEmpty(goodsAjax) && Utility.isNotEmpty(goodsAjax.getData())) {
				GoodsVO goodsVO = JSONObject.parseObject(JSONObject.toJSONString(goodsAjax.getData()),GoodsVO.class);
				differenceOrderVO.setGoodsName(goodsVO.getGoodsName()); // 商品名称
				differenceOrderVO.setGoodsNumber(goodsVO.getGoodsNumber()); // 商品编号
			}
			BigDecimal actualPrice = orderGoodsDomain.getActualPrice().multiply(new BigDecimal(orderGoodsDomain.getOrderAmount().toString()));
			BigDecimal retailPrice = orderGoodsDomain.getRetailPrice().multiply(new BigDecimal(orderGoodsDomain.getOrderAmount().toString()));
			// 根据门店id集合获取基础服务返回的StoreVO对象
			StoreVO store = storeMap.get(salesOrderMap.get(orderGoodsDomain.getOrderId()).getStoreId());
			differenceOrderVO.setGoodsId(orderGoodsDomain.getGoodsId()); // 商品id
			differenceOrderVO.setOrderId(Long.parseLong(orderGoodsDomain.getOrderId().toString())); // 订单id
			differenceOrderVO.setRetailPrice(retailPrice); // 应付金额
			differenceOrderVO.setRetailPriceValue(Utility.bigDecimalToString(retailPrice)); // 应付金额Value
			differenceOrderVO.setActualPrice(actualPrice); // 实付金额
			differenceOrderVO.setActualPriceValue(Utility.bigDecimalToString(actualPrice)); // 实付金额Value
			differenceOrderVO.setDifference(retailPrice.subtract(actualPrice)); // 差异
			differenceOrderVO.setDifferenceValue(Utility.bigDecimalToString(retailPrice.subtract(actualPrice))); // 差异Value
			differenceOrderVO.setOrderNumber(salesOrderMap.get(orderGoodsDomain.getOrderId()).getSalesOrderNumber()); // 订单编号
			differenceOrderVO.setCreateDate(salesOrderMap.get(orderGoodsDomain.getOrderId()).getCreateDate()); // 创建时间
			differenceOrderVO.setStoreName(store.getStoreName()); // 门店名称
			if(Utility.isNotEmpty(store.getPartnerTraderVO().getPartnerTraderVO())) {
				// 合伙人名称
				differenceOrderVO.setPartnerName(store.getPartnerTraderVO().getPartnerTraderVO().getPartnerName());
				// 加盟商名称
				differenceOrderVO.setFranchiseeName(store.getPartnerTraderVO().getPartnerName());
			} else {
				differenceOrderVO.setPartnerName(store.getPartnerTraderVO().getPartnerName()); // 合伙人名称
			}
			resultList.add(differenceOrderVO);
		}
		return resultList;
	}

	private List<SameAgencyVO> partnerTraderDetail(SameNameParamVO paramVO, List<PartnerTraderVO> partnerAllList){
		List<SameAgencyVO> sameAgencyList = new ArrayList<>();
		List<PartnerTraderVO> partnerList = new ArrayList<>();
		List<PartnerTraderVO> franchiseeList = new ArrayList<>();
		for(PartnerTraderVO  partnerTrader : partnerAllList) {
			if(partnerTrader.getPersonType().equals(OrderConstants.PARTNER_PERSON_TYPE)) {
				// 合伙人
				if(Utility.isNotEmpty(paramVO.getPartnerNumber())) {
					if(partnerTrader.getPartnerNumber().equals(paramVO.getPartnerNumber())){
						partnerList.add(partnerTrader);
					}
				} else {
					partnerList.add(partnerTrader);
				}
			} else {
				// 加盟商
				if(Utility.isNotEmpty(paramVO.getFranchiseeNumber())) {
					if(partnerTrader.getPartnerNumber().equals(paramVO.getFranchiseeNumber())){
						franchiseeList.add(partnerTrader);
					}
				} else {
					franchiseeList.add(partnerTrader);
				}
				
			}
		}
		for(PartnerTraderVO partner : partnerList) {
			for(PartnerTraderVO franchisee : franchiseeList) {
				String partnerCompanyPhone = partner.getMobilePhone()+partner.getCompanyName();
				String franchiseeCompanyPhone = franchisee.getMobilePhone()+franchisee.getCompanyName();
				if(Utility.isNotEmpty(paramVO.getMobilePhone())) {
					if(partnerCompanyPhone.equals(franchiseeCompanyPhone) && partner.getMobilePhone().equals(paramVO.getMobilePhone())) {
						if(Utility.isNotEmpty(paramVO.getCompanyName())) {
							if(paramVO.getCompanyName().equals(partner.getCompanyName())) {
								SameAgencyVO sameAgency = new SameAgencyVO();
								sameAgency.setPartnerNumber(partner.getPartnerNumber());
								sameAgency.setPartnerName(partner.getPartnerName());
								sameAgency.setCompanyName(partner.getCompanyName());
								sameAgency.setMobilePhone(partner.getMobilePhone());
								sameAgency.setFranchiseeName(franchisee.getPartnerName());
								sameAgency.setFranchiseeNumber(franchisee.getPartnerNumber());
								sameAgencyList.add(sameAgency);
							}
						} else {
							SameAgencyVO sameAgency = new SameAgencyVO();
							sameAgency.setPartnerNumber(partner.getPartnerNumber());
							sameAgency.setPartnerName(partner.getPartnerName());
							sameAgency.setCompanyName(partner.getCompanyName());
							sameAgency.setMobilePhone(partner.getMobilePhone());
							sameAgency.setFranchiseeName(franchisee.getPartnerName());
							sameAgency.setFranchiseeNumber(franchisee.getPartnerNumber());
							sameAgencyList.add(sameAgency);
						}
					}
				} else {
					if(partnerCompanyPhone.equals(franchiseeCompanyPhone)) {
						if(Utility.isNotEmpty(paramVO.getCompanyName())) {
							if(paramVO.getCompanyName().equals(partner.getCompanyName())) {
								SameAgencyVO sameAgency = new SameAgencyVO();
								sameAgency.setPartnerNumber(partner.getPartnerNumber());
								sameAgency.setPartnerName(partner.getPartnerName());
								sameAgency.setCompanyName(partner.getCompanyName());
								sameAgency.setMobilePhone(partner.getMobilePhone());
								sameAgency.setFranchiseeName(franchisee.getPartnerName());
								sameAgency.setFranchiseeNumber(franchisee.getPartnerNumber());
								sameAgencyList.add(sameAgency);
							}
						} else {
							SameAgencyVO sameAgency = new SameAgencyVO();
							sameAgency.setPartnerNumber(partner.getPartnerNumber());
							sameAgency.setPartnerName(partner.getPartnerName());
							sameAgency.setCompanyName(partner.getCompanyName());
							sameAgency.setMobilePhone(partner.getMobilePhone());
							sameAgency.setFranchiseeName(franchisee.getPartnerName());
							sameAgency.setFranchiseeNumber(franchisee.getPartnerNumber());
							sameAgencyList.add(sameAgency);
						}
					}
				}
			}
		}
		return sameAgencyList;
	}
	
}

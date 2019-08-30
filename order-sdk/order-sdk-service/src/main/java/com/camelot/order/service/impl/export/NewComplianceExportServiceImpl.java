package com.camelot.order.service.impl.export;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.common.utils.BeanUtil;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.service.NewComplianceExportService;
import com.camelot.order.export.service.NewReturnOrderExportService;
import com.camelot.order.export.service.NewReturnOrderGoodsExportService;
import com.camelot.order.export.service.NewSalesOrderExportService;
import com.camelot.order.export.service.NewSalesOrderGoodsExportService;
import com.camelot.order.export.snapshot.NewReturnOrderGoodsVO;
import com.camelot.order.export.snapshot.NewReturnOrderVO;
import com.camelot.order.export.snapshot.NewSalesOrderGoodsVO;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
import com.camelot.order.export.vo.DifferenceOrderVO;
import com.camelot.order.export.vo.PartnerTraderVO;
import com.camelot.order.export.vo.ReturnGoodsVO;
import com.camelot.order.export.vo.SameAgencyVO;
import com.camelot.order.export.vo.param.BaseParamVO;
import com.camelot.order.export.vo.param.DifferenceParamVO;
import com.camelot.order.export.vo.param.ReturnGoodsParamVO;
import com.camelot.order.export.vo.param.SameNameParamVO;
import com.camelot.order.feign.config.FeignStoreService;
import com.github.pagehelper.PageInfo;

@Service("newComplianceExportService")
public class NewComplianceExportServiceImpl implements NewComplianceExportService {
	
	private static Logger log = Log.get(NewComplianceExportServiceImpl.class);
	
	@Autowired
	private NewSalesOrderExportService newSalesOrderExportService;
	@Autowired
	private NewSalesOrderGoodsExportService newSalesOrderGoodsExportService;
	@Autowired
	private NewReturnOrderGoodsExportService newReturnOrderGoodsExportService;
	@Autowired
	private NewReturnOrderExportService newReturnOrderExportService;
	
	@Autowired
	private FeignStoreService feignStoreService;

	@Override
	public ExecuteResult<PageInfo<DifferenceOrderVO>> compliancePageDifference(DifferenceParamVO paramVO, Page page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewComplianceExportServiceImpl-compliancePageDifference",  JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<DifferenceOrderVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	// 订单状态-已完成
			paramVO.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
			// 查询销售订单数据List
			List<NewSalesOrderVO> salesOrderList = queryListNewSalesOrder(paramVO);
			if(Utility.isNotEmpty(salesOrderList)) {
				Map<Long, NewSalesOrderVO> salesOrderMap = new HashMap<>();
				List<Long> salesOrderIdList = new ArrayList<>();
				for(NewSalesOrderVO newSalesOrderVO : salesOrderList) {
					salesOrderMap.put(newSalesOrderVO.getSalesOrderId(), newSalesOrderVO);
					salesOrderIdList.add(newSalesOrderVO.getSalesOrderId());
				}
				// 查询订单商品Page数据
				NewSalesOrderGoodsVO newSalesOrderGoods = new NewSalesOrderGoodsVO();
				// 销售订单id集合
				newSalesOrderGoods.setSalesOrderIdList(salesOrderIdList);
				// 最小差异金额
				newSalesOrderGoods.setMinAmount(paramVO.getMinAmount());
				// 最大差异金额
				newSalesOrderGoods.setMaxAmount(paramVO.getMaxAmount());
				ExecuteResult<PageInfo<NewSalesOrderGoodsVO>> queryPageDifference = newSalesOrderGoodsExportService.queryPageDifference(newSalesOrderGoods, page);
				if(Utility.isNotEmpty(queryPageDifference.getResult())){
					List<NewSalesOrderGoodsVO> queryResult = queryPageDifference.getResult().getList();
					// 差异商品订单详细信息补全
					List<DifferenceOrderVO> resultList = detailDifference(salesOrderMap, queryResult);
					@SuppressWarnings("rawtypes")
					PageInfo pageInfo = queryPageDifference.getResult();
		            pageInfo.setList(resultList);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewComplianceExportServiceImpl-compliancePageDifference", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewComplianceExportServiceImpl-compliancePageDifference",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<DifferenceOrderVO>> complianceListDifference(DifferenceParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewComplianceExportServiceImpl-complianceListDifference",  JSONObject.toJSONString(paramVO));
        ExecuteResult<List<DifferenceOrderVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	// 付款状态-已支付
			paramVO.setPaymentStatusId(OrderConstants.PAYMENT_STATUS_PAID);
			// 查询销售订单数据List
			List<NewSalesOrderVO> salesOrderList = queryListNewSalesOrder(paramVO);
			if(Utility.isNotEmpty(salesOrderList)) {
				Map<Long, NewSalesOrderVO> salesOrderMap = new HashMap<>();
				List<Long> salesOrderIdList = new ArrayList<>();
				for(NewSalesOrderVO newSalesOrderVO : salesOrderList) {
					salesOrderMap.put(newSalesOrderVO.getSalesOrderId(), newSalesOrderVO);
					salesOrderIdList.add(newSalesOrderVO.getSalesOrderId());
				}
				// 查询订单商品Page数据
				NewSalesOrderGoodsVO newSalesOrderGoods = new NewSalesOrderGoodsVO();
				// 销售订单id集合
				newSalesOrderGoods.setSalesOrderIdList(salesOrderIdList);
				// 最小差异金额
				newSalesOrderGoods.setMinAmount(paramVO.getMinAmount());
				// 最大差异金额
				newSalesOrderGoods.setMaxAmount(paramVO.getMaxAmount());
				ExecuteResult<List<NewSalesOrderGoodsVO>> queryPageDifference = newSalesOrderGoodsExportService.queryListDifference(newSalesOrderGoods);
				if(Utility.isNotEmpty(queryPageDifference.getResult())){
					List<NewSalesOrderGoodsVO> queryResult = queryPageDifference.getResult();
					// 差异商品订单详细信息补全
					List<DifferenceOrderVO> resultList = detailDifference(salesOrderMap, queryResult);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewComplianceExportServiceImpl-complianceListDifference", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewComplianceExportServiceImpl-complianceListDifference",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<PageInfo<ReturnGoodsVO>> compliancePageReturnGoods(ReturnGoodsParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewComplianceExportServiceImpl-compliancePageReturnGoods",  JSONObject.toJSONString(paramVO));
        ExecuteResult<PageInfo<ReturnGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewReturnOrderVO newReturnOrderParam = new NewReturnOrderVO();
        	// 城市orgStr
        	newReturnOrderParam.setOrgList(paramVO.getOrgIdList());
        	// 门店storeStr
        	newReturnOrderParam.setStoreList(paramVO.getStoreIdList());
			// 查询退货订单数据List
			ExecuteResult<List<NewReturnOrderVO>> listNewReturnOrderVO = newReturnOrderExportService.queryList(newReturnOrderParam);
			List<NewReturnOrderVO> resultNewReturnOrder = listNewReturnOrderVO.getResult();
			if(Utility.isNotEmpty(resultNewReturnOrder)) {
				Map<Long, NewReturnOrderVO> newReturnOrderMap = new HashMap<>();
				List<Long> newReturnOrderIdList = new ArrayList<>();
				for(NewReturnOrderVO newReturnOrderVO : resultNewReturnOrder) {
					newReturnOrderMap.put(newReturnOrderVO.getSalesOrderId(), newReturnOrderVO);
					newReturnOrderIdList.add(newReturnOrderVO.getReturnOrderId());
				}
				// 查询退货订单商品Page数据
				NewReturnOrderGoodsVO newReturnOrderGoodsVO = new NewReturnOrderGoodsVO();
				// 销售订单id集合
				newReturnOrderGoodsVO.setReturnOrderIdList(newReturnOrderIdList);
				// 商品名称
				newReturnOrderGoodsVO.setGoodsName(paramVO.getGoodsName());
				// 商品SN码
				newReturnOrderGoodsVO.setGoodsSn(paramVO.getGoodsSn());
				// 车架号
				newReturnOrderGoodsVO.setGoodsFrameNumber(paramVO.getGoodsFrameNumber());
				ExecuteResult<PageInfo<NewReturnOrderGoodsVO>> queryPageReturnGoods = newReturnOrderGoodsExportService.queryPageReturnGoods(newReturnOrderGoodsVO, page);
				if(Utility.isNotEmpty(queryPageReturnGoods.getResult())){
					List<NewReturnOrderGoodsVO> queryResult = queryPageReturnGoods.getResult().getList();
					// 退货有序商品订单信息
					List<ReturnGoodsVO> resultList = (List<ReturnGoodsVO>)BeanUtil.copyList(ReturnGoodsVO.class, queryResult);
					@SuppressWarnings("rawtypes")
					PageInfo pageInfo = queryPageReturnGoods.getResult();
		            pageInfo.setList(resultList);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewComplianceExportServiceImpl-compliancePageReturnGoods", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewComplianceExportServiceImpl-compliancePageReturnGoods",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<PageInfo<SameAgencyVO>> compliancePageSameAgency(SameNameParamVO paramVO, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewComplianceExportServiceImpl-compliancePageSameAgency", JSONObject.toJSONString(paramVO));
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
			result.setResultMessage(e.toString());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewComplianceExportServiceImpl-compliancePageSameAgency", e.toString());
			return result;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewComplianceExportServiceImpl-compliancePageSameAgency", result);
		return result;
	}

	@Override
	public ExecuteResult<List<SameAgencyVO>> complianceListSameAgency(SameNameParamVO paramVO) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewComplianceExportServiceImpl-complianceListSameAgency", JSONObject.toJSONString(paramVO));
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
			result.setResultMessage(e.toString());
			Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewComplianceExportServiceImpl-complianceListSameAgency", e.toString());
			return result;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewComplianceExportServiceImpl-complianceListSameAgency", result);
		return result;
	}
	
	
	
	/**
	 * <p>Description: 查询销售订单数据List</p>
	 * @param paramVO 城市IdList/门店IdList/订单状态IdList/销售编号/开始时间/结束时间
	 * @return 销售订单数据List
	 * @author zhouyou
	 * @date 2019年5月14日
	 */
	private List<NewSalesOrderVO> queryListNewSalesOrder(BaseParamVO paramVO){
		// 查询订单数据VO
		NewSalesOrderVO salesOrderVO = new NewSalesOrderVO();
		// 城市IdList
		salesOrderVO.setOrgList(paramVO.getOrgIdList());
		// 门店IdList
		if(Utility.isNotEmpty(paramVO.getStoreIds())) { // 查询条件(门店)不为空
			List<Integer> storeIdList = Utility.stringToList(paramVO.getStoreIds());
			if(Utility.isNotEmpty(paramVO.getStoreIdList())) {
				storeIdList.retainAll(paramVO.getStoreIdList());
				salesOrderVO.setStoreList(storeIdList);
			} else {
				salesOrderVO.setStoreList(storeIdList);
			}
		} else {
			salesOrderVO.setStoreList(paramVO.getStoreIdList());
		}
		// 订单状态id-已支付
		salesOrderVO.setSalesOrderStatusId(paramVO.getSalesOrderStatusId());
		// 销售编号
		salesOrderVO.setSalesOrderNumber(paramVO.getSalesOrderNumber());
		// 开始时间
		salesOrderVO.setStartCreateDate(paramVO.getStartDate());
		// 结束时间
		salesOrderVO.setEndCreateDate(paramVO.getEndDate());
		// 查询销售订单数据
		ExecuteResult<List<NewSalesOrderVO>> OrderExecuteResult = newSalesOrderExportService.queryList(salesOrderVO);
		List<NewSalesOrderVO> salesOrderList = OrderExecuteResult.getResult();
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
	private List<DifferenceOrderVO> detailDifference(Map<Long, NewSalesOrderVO> salesOrderMap, List<NewSalesOrderGoodsVO> queryResult){
		List<DifferenceOrderVO> resultList = new ArrayList<>();
		for(NewSalesOrderGoodsVO newSalesOrderGoodsVO: queryResult) {
			DifferenceOrderVO differenceOrderVO = new DifferenceOrderVO();
			// 实付金额
			BigDecimal actualPrice = newSalesOrderGoodsVO.getActualPrice().multiply(new BigDecimal(newSalesOrderGoodsVO.getOrderAmount().toString()));
			// 应付金额
			BigDecimal retailPrice = newSalesOrderGoodsVO.getRetailPrice().multiply(new BigDecimal(newSalesOrderGoodsVO.getOrderAmount().toString()));
			// 订单ID
			differenceOrderVO.setOrderId(newSalesOrderGoodsVO.getSalesOrderId());
			// 销售订单编号
			differenceOrderVO.setOrderNumber(salesOrderMap.get(newSalesOrderGoodsVO.getSalesOrderId()).getSalesOrderNumber());
			// 商品编号
			differenceOrderVO.setGoodsNumber(newSalesOrderGoodsVO.getGoodsNumber());
			// 商品名称
			differenceOrderVO.setGoodsName(newSalesOrderGoodsVO.getGoodsName());
			// 提交时间
			differenceOrderVO.setCreateDate(salesOrderMap.get(newSalesOrderGoodsVO.getSalesOrderId()).getCreateDate()); 
			// 合伙人
			differenceOrderVO.setPartnerName(salesOrderMap.get(newSalesOrderGoodsVO.getSalesOrderId()).getPartnerName()); 
			// 加盟商
			differenceOrderVO.setFranchiseeName(salesOrderMap.get(newSalesOrderGoodsVO.getSalesOrderId()).getTraderName()); 
			// 门店名称
			differenceOrderVO.setStoreName(salesOrderMap.get(newSalesOrderGoodsVO.getSalesOrderId()).getStoreName()); 
			// 应付金额
			differenceOrderVO.setRetailPrice(retailPrice);
			// 应付金额Value
			differenceOrderVO.setRetailPriceValue(Utility.bigDecimalToString(retailPrice));
			// 实付金额
			differenceOrderVO.setActualPrice(actualPrice);
			// 实付金额Value
			differenceOrderVO.setActualPriceValue(Utility.bigDecimalToString(actualPrice)); 
			// 零售差异
			differenceOrderVO.setDifference(retailPrice.subtract(actualPrice));
			// 零售差异Value
			differenceOrderVO.setDifferenceValue(Utility.bigDecimalToString(retailPrice.subtract(actualPrice))); 
			resultList.add(differenceOrderVO);
		}
		return resultList;
	}

	/**
	 * <p>Title: partnerTraderDetail</p>
	 * <p>Description: 机构归属详细信息</p>
	 * @param paramVO
	 * @param partnerAllList
	 * @return
	 * @author zhouyou
	 * @date 2019年5月20日
	 */
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

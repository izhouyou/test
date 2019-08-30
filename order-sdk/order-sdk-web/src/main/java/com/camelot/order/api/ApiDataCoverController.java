package com.camelot.order.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.utils.BeanUtil;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.ResultUtil;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.service.FeignCouponExportService;
import com.camelot.order.export.service.NewNotBuyExportService;
import com.camelot.order.export.service.NewReceiptRecordExportService;
import com.camelot.order.export.service.NewReturnOrderExportService;
import com.camelot.order.export.service.NewReturnOrderGoodsExportService;
import com.camelot.order.export.service.NewSalesOrderExportService;
import com.camelot.order.export.service.NewSalesOrderGoodsExportService;
import com.camelot.order.export.service.NotBuyExportService;
import com.camelot.order.export.service.OrderGoodsExportService;
import com.camelot.order.export.service.ReceiptRecordExportService;
import com.camelot.order.export.service.ReturnOrderExportService;
import com.camelot.order.export.service.SalesOrderExportService;
import com.camelot.order.export.snapshot.NewNotBuyVO;
import com.camelot.order.export.snapshot.NewReceiptRecordVO;
import com.camelot.order.export.snapshot.NewReturnOrderGoodsVO;
import com.camelot.order.export.snapshot.NewReturnOrderVO;
import com.camelot.order.export.snapshot.NewSalesOrderGoodsVO;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
import com.camelot.order.export.vo.DictValueVO;
import com.camelot.order.export.vo.NotBuyVO;
import com.camelot.order.export.vo.OrderGoodsVO;
import com.camelot.order.export.vo.ReceiptRecordVO;
import com.camelot.order.export.vo.ReturnOrderVO;
import com.camelot.order.export.vo.SalesOrderVO;
import com.camelot.order.export.vo.feignvo.CustomerVO;
import com.camelot.order.export.vo.feignvo.FeignCouponBatchVO;
import com.camelot.order.export.vo.feignvo.GoodsCategoryVO;
import com.camelot.order.export.vo.feignvo.GoodsVO;
import com.camelot.order.export.vo.feignvo.StoreVO;
import com.camelot.order.feign.FeignActivityService;
import com.camelot.order.feign.FeignCouponService;
import com.camelot.order.feign.FeignCustomerService;
import com.camelot.order.feign.FeignDictService;
import com.camelot.order.feign.FeignGoodsCategoryService;
import com.camelot.order.feign.FeignGoodsService;
import com.camelot.order.feign.FeignStoreService;
import com.camelot.order.feign.FeignUserService;

import io.swagger.annotations.Api;

/**
 * @author linjianping
 * @date 2019-05-22
 */
@RestController
@RequestMapping("dataCover")
@Api(value="数据迁移", tags="数据迁移", description="数据迁移")
public class ApiDataCoverController {

	@Autowired
	SalesOrderExportService salesOrderExportService;
	
	@Autowired
	NewSalesOrderExportService newSalesOrderExportService;
	
	@Autowired
	FeignStoreService feignStoreService;
	
	@Autowired
	FeignDictService feignDictService;
	
	@Autowired
	FeignCustomerService feignCustomerService;
	
	@Autowired
	FeignActivityService feignActivityService;
	
	@Autowired
	FeignCouponService feignCouponService;
	
	@Autowired
	FeignUserService feignUserService;
	
	@Autowired
	ReturnOrderExportService returnOrderExportService;
	
	@Autowired
	NewReturnOrderExportService newReturnOrderExportService;
	
	@Autowired
	FeignCouponExportService feignCouponExportService;
	
	@Autowired
	ReceiptRecordExportService receiptRecordExportService;
	
	@Autowired
	NewReceiptRecordExportService newReceiptRecordExportService;
	
	@Autowired
	FeignGoodsService feignGoodsService;
	
	@Autowired
	FeignGoodsCategoryService feignGoodsCategoryService;
	
	@Autowired
	OrderGoodsExportService orderGoodsExportService;
	
	@Autowired
	NewSalesOrderGoodsExportService newSalesOrderGoodsExportService;
	
	@Autowired
	NewReturnOrderGoodsExportService newReturnOrderGoodsExportService;
	
	@Autowired
	NotBuyExportService notBuyExportService;
	
	@Autowired
	NewNotBuyExportService newNotBuyExportService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "salesOrder", method = RequestMethod.GET)
	public AjaxInfo salesOrder() {
		AjaxInfo info = new AjaxInfo();
		ExecuteResult<List<NewSalesOrderVO>> result = new ExecuteResult<>();
		try {
			ExecuteResult<List<SalesOrderVO>> salesOrderResult = salesOrderExportService.queryList(null);
			if(Utility.isNotEmpty(salesOrderResult.getResult())) {
				List<SalesOrderVO> salesOrderList = salesOrderResult.getResult();
				Map<Long, SalesOrderVO> salesOrderMap = new HashMap<>();
				for (SalesOrderVO salesOrderVO : salesOrderList) {
					salesOrderMap.put(salesOrderVO.getSalesOrderId().longValue(), salesOrderVO);
				}
				List<NewSalesOrderVO> list = BeanUtil.copyList(NewSalesOrderVO.class, salesOrderList);
				for (NewSalesOrderVO vo : list) {
					SalesOrderVO salesOrderVO = salesOrderMap.get(vo.getSalesOrderId());
					//基础服务--大区区域城市合伙人加盟商门店信息
					AjaxInfo queryStoreList = feignStoreService.queryStoreList(String.valueOf(vo.getStoreId()));
					List<StoreVO> storeList = JSONObject.parseArray(JSONObject.toJSONString(queryStoreList.getData()), StoreVO.class);
					if(Utility.isNotEmpty(storeList)) {
						StoreVO storeVO = storeList.get(0);
						vo.setFirstOrgNumber(storeVO.getFirstOrgVO().getOrgCode());//大区编码
						vo.setFirstOrgName(storeVO.getFirstOrgVO().getOrgName());//大区名称
						vo.setSecondOrgNumber(storeVO.getSecondOrgVO().getOrgCode());
						vo.setSecondOrgName(storeVO.getSecondOrgVO().getOrgName());
						vo.setThirdOrgNumber(storeVO.getThirdOrgVO().getOrgCode());
						vo.setThirdOrgName(storeVO.getThirdOrgVO().getOrgName());
						vo.setStoreNumber(storeVO.getStoreNumber());
						if(Utility.isNotEmpty(storeVO.getStoreChannelNumber())) {
							vo.setStoreChannelNumber(storeVO.getStoreChannelNumber());
						}
						vo.setStoreName(storeVO.getStoreName());
						vo.setPartnerId(storeVO.getPartnerVO().getPartnerId());
						vo.setPartnerNumber(storeVO.getPartnerVO().getPartnerNumber());
						vo.setPartnerName(storeVO.getPartnerVO().getPartnerName());
						if(Utility.isNotEmpty(storeVO.getTraderVO())) {
							vo.setTraderId(storeVO.getTraderVO().getPartnerId());
							vo.setTraderNumber(storeVO.getTraderVO().getPartnerNumber());
							vo.setTraderName(storeVO.getTraderVO().getPartnerName());
						}
						vo.setPaymentWayId(salesOrderVO.getPaymentWay());
						DictValueVO paymentWay = getDictValueVO(salesOrderVO.getPaymentWay());
						if(Utility.isNotEmpty(paymentWay)) {
							vo.setPaymentWayNumber(paymentWay.getDictValueNumber());
							vo.setPaymentWayName(paymentWay.getDictValueName());
						}
						
						vo.setSalesOrderSourceId(salesOrderVO.getSalesOrderSource());
						if(Utility.isNotEmpty(getDictValueVO(salesOrderVO.getSalesOrderSource()))) {
							DictValueVO salesOrderSource = getDictValueVO(salesOrderVO.getSalesOrderSource());
							vo.setSalesOrderSourceNumber(salesOrderSource.getDictValueNumber());
							vo.setSalesOrderSourceName(salesOrderSource.getDictValueName());
						}
						Map<String, Object> customerMap = new HashMap<>();
						customerMap.put("customerId", vo.getCustomerId());
						AjaxInfo customerInfo = feignCustomerService.queryCustomerList(customerMap);
						List<CustomerVO> customerList = JSONObject.parseArray(JSONObject.toJSONString(customerInfo.getData()), CustomerVO.class);
						if(Utility.isNotEmpty(customerList)) {
							CustomerVO customerVO = customerList.get(0);
							vo.setCustomerNumber(customerVO.getCustomerNunber());
							vo.setCustomerPhoneNumber(customerVO.getCustomerPhoneNumber());
							vo.setCustomerName(customerVO.getCustomerName());
							vo.setCustomerSourceId(customerVO.getCustomerSource());
							DictValueVO customerSource = getDictValueVO(customerVO.getCustomerSource());
							if(Utility.isNotEmpty(customerSource)) {
								vo.setCustomerSourceNumber(customerSource.getDictValueNumber());
								vo.setCustomerSourceName(customerSource.getDictValueName());
							}
						}
						if(Utility.isNotEmpty(salesOrderVO.getActivityId())) {
							AjaxInfo activityInfo = feignActivityService.queryById(salesOrderVO.getActivityId());
							if(Utility.isNotEmpty(activityInfo.getData())) {
								Map<String, Object> activityMap = (Map<String, Object>) activityInfo.getData();
								if(activityMap.containsKey("activityId")) {
									vo.setActivityNumber(Utility.objectToString(activityMap.get("activityId")));
								}
								if(activityMap.containsKey("activityName")) {
									vo.setActivityName(Utility.objectToString(activityMap.get("activityName")));
								}
							}
						}
						if(Utility.isNotEmpty(salesOrderVO.getCouponId())) {
							AjaxInfo couponInfo = feignCouponService.queryById(salesOrderVO.getCouponId());
							if (Utility.isNotEmpty(couponInfo.getData())) {
								Map<String, Object> couponMap = (Map<String, Object>) couponInfo.getData();
								if (couponMap.containsKey("couponCode")) {
									vo.setCouponCode(Utility.objectToString(couponMap.get("couponCode")));
								}
								if(couponMap.containsKey("tCouponBatchId")) {
									if(Utility.isNotEmpty(couponMap.get("tCouponBatchId"))) {
										vo.setActivityBrachId(Integer.valueOf(couponMap.get("tCouponBatchId").toString()));
										FeignCouponBatchVO queryCouponBatch = feignCouponExportService.queryCouponBatch(vo.getActivityBrachId(), null);
										if(Utility.isNotEmpty(queryCouponBatch)) {
											vo.setActivityBrachName(queryCouponBatch.getCouponName());
										}
									}
								}
							}
						}
						if(Utility.isNotEmpty(salesOrderVO.getCancelReason())) {
							DictValueVO cancelReason = getDictValueVO(salesOrderVO.getCancelReason());
							if(Utility.isNotEmpty(cancelReason)) {
								vo.setCancelReasonId(salesOrderVO.getCancelReason());
								vo.setCancelReasonNumber(cancelReason.getDictValueNumber());
								vo.setCancelReasonName(cancelReason.getDictValueName());
							}
						}
					}
					vo.setCreateUserName(getUserRealName(salesOrderVO.getCreateUserId()));
					vo.setUpdateUserName(getUserRealName(salesOrderVO.getUpdateUserId()));
					
					if(salesOrderVO.getSalesOrderStatus().equals(OrderConstants.ORDER_STATUS_SUBMISSION)) {
						vo.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_SUBMISSION);
						vo.setPaymentStatusId(103);
						vo.setReturnStatusId(226);
					}else if (salesOrderVO.getSalesOrderStatus().equals(OrderConstants.ORDER_STATUS_NOPAY)) {
						vo.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_SUBMISSION);
						vo.setPaymentStatusId(103);
						vo.setReturnStatusId(226);
					}else if (salesOrderVO.getSalesOrderStatus().equals(OrderConstants.ORDER_STATUS_CANCLE)) {
						vo.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_CANCLE);
						vo.setPaymentStatusId(103);
						vo.setReturnStatusId(226);
					}else if (salesOrderVO.getSalesOrderStatus().equals(OrderConstants.ORDER_STATUS_FINISH)) {
						vo.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
						vo.setPaymentStatusId(OrderConstants.DEFAULT_RECEIPT_STATUS);
						vo.setReturnStatusId(226);
					}else if (salesOrderVO.getSalesOrderStatus().equals(OrderConstants.ORDER_STATUS_RETURN)) {
						vo.setSalesOrderStatusId(OrderConstants.ORDER_STATUS_FINISH);
						vo.setPaymentStatusId(OrderConstants.DEFAULT_RECEIPT_STATUS);
						vo.setReturnStatusId(227);
						
						ReturnOrderVO returnOrderVO = new ReturnOrderVO();
						returnOrderVO.setSalesOrderId(salesOrderVO.getSalesOrderId());
						ExecuteResult<List<ReturnOrderVO>> queryList = returnOrderExportService.queryList(returnOrderVO);
						if(Utility.isNotEmpty(queryList.getResult())) {
							vo.setReturnOrderNumber(queryList.getResult().get(0).getReturnOrderNumber());
						}
					}
					DictValueVO paymentStatus = getDictValueVO(vo.getPaymentStatusId());
					if(Utility.isNotEmpty(paymentStatus)) {
						vo.setPaymentStatusNumber(paymentStatus.getDictValueNumber());
						vo.setPaymentStatusName(paymentStatus.getDictValueName());
					}
					
					DictValueVO salesOrderStatus = getDictValueVO(vo.getSalesOrderStatusId());
					if(Utility.isNotEmpty(salesOrderStatus)) {
						vo.setSalesOrderStatusNumber(salesOrderStatus.getDictValueNumber());
						vo.setSalesOrderStatusName(salesOrderStatus.getDictValueName());
					}
					
					DictValueVO returnStatus = getDictValueVO(vo.getReturnStatusId());
					if(Utility.isNotEmpty(returnStatus)) {
						vo.setReturnStatusNumber(returnStatus.getDictValueNumber());
						vo.setReturnStatusName(returnStatus.getDictValueName());
					}
					ExecuteResult<NewSalesOrderVO> query = newSalesOrderExportService.queryById(vo.getSalesOrderId());
					if(Utility.isNotEmpty(query.getResult())) {
						ExecuteResult<NewSalesOrderVO> update = newSalesOrderExportService.update(vo);
						System.out.println("更新结果：" + JSONObject.toJSONString(update));
					}else {
						ExecuteResult<NewSalesOrderVO> add = newSalesOrderExportService.add(vo);
						System.out.println("新增结果：" + JSONObject.toJSONString(add));
					}
				}
				result.setResult(list);
				result.setCode(Constants.SUCCESS_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
				info = ResultUtil.getSelectAjaxInfo(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	@RequestMapping(value = "returnOrder", method = RequestMethod.GET)
	public AjaxInfo returnOrder() {
		AjaxInfo info = new AjaxInfo();
		ExecuteResult<List<NewReturnOrderVO>> result = new ExecuteResult<>();
		try {
			ExecuteResult<List<ReturnOrderVO>> returnOrderResult = returnOrderExportService.queryList(null);
			if(Utility.isNotEmpty(returnOrderResult.getResult())) {
				 List<ReturnOrderVO> returnOrderList = returnOrderResult.getResult();
				 Map<Long, ReturnOrderVO> returnOrderMap = new HashMap<>();
				 for (ReturnOrderVO returnOrderVO : returnOrderList) {
					 returnOrderMap.put(returnOrderVO.getReturnOrderId().longValue(), returnOrderVO);
				}
				 List<NewReturnOrderVO> newReturnOrderVOList = new ArrayList<>();
				 
				 List<NewReturnOrderVO> list = BeanUtil.copyList(NewReturnOrderVO.class, returnOrderList);
				 for (NewReturnOrderVO vo : list) {
					ReturnOrderVO returnOrderVO = returnOrderMap.get(vo.getReturnOrderId());
					
					ExecuteResult<NewSalesOrderVO> newSalesOrderResult = newSalesOrderExportService.queryById(vo.getSalesOrderId());
					if(Utility.isNotEmpty(newSalesOrderResult.getResult())) {
						NewSalesOrderVO newSalesOrderVO = newSalesOrderResult.getResult();
						//NewReturnOrderVO newReturnOrderVO = BeanUtil.copyPropertes(NewReturnOrderVO.class, newSalesOrderVO);
						BeanUtils.copyProperties(newSalesOrderVO, vo, "returnOrderNumber", "createUserId", "delFlg", "createDate");
						vo.setReturnOrderStatusId(227);
						vo.setReturnOrderStatusNumber("2");
						vo.setReturnOrderStatusName("已退货");
						vo.setCreateUserName(getUserRealName(vo.getCreateUserId()));
						if(Utility.isNotEmpty(returnOrderVO.getReturnReason())) {
							vo.setReturnReasonId(returnOrderVO.getReturnReason());
							DictValueVO returnReason = getDictValueVO(returnOrderVO.getReturnReason());
							if(Utility.isNotEmpty(returnReason)) {
								vo.setReturnReasonNumber(returnReason.getDictValueNumber());
								vo.setReturnReasonName(returnReason.getDictValueName());
							}
						}
						
						System.out.println("vo: " + JSONObject.toJSONString(vo));
						newReturnOrderVOList.add(vo);
						ExecuteResult<NewReturnOrderVO> queryById = newReturnOrderExportService.queryById(vo.getReturnOrderId());
						if(Utility.isNotEmpty(queryById.getResult())) {
							ExecuteResult<NewReturnOrderVO> update = newReturnOrderExportService.update(vo);
							System.out.println("update: " + JSONObject.toJSONString(update));
						}else {
							ExecuteResult<NewReturnOrderVO> add = newReturnOrderExportService.add(vo);
							System.out.println("add: " + JSONObject.toJSONString(add));
						}
					}
				}
				 result.setResult(newReturnOrderVOList);
				 result.setCode(Constants.SUCCESS_CODE);
				 result.setResultMessage(Constants.QUERY_SUCCESS);
				 info = ResultUtil.getSelectListAjaxInfo(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	@RequestMapping(value = "receiptRecord", method = RequestMethod.GET)
	public AjaxInfo receiptRecord() {
		AjaxInfo info = new AjaxInfo();
		try {
			ExecuteResult<List<NewReceiptRecordVO>> result = new ExecuteResult<>();
			ExecuteResult<List<ReceiptRecordVO>> receiptRecordResult = receiptRecordExportService.queryList(null);
			if(Utility.isNotEmpty(receiptRecordResult.getResult())) {
				List<ReceiptRecordVO> receiptRecordList = receiptRecordResult.getResult();
				Map<Long, ReceiptRecordVO> receiptRecordMap = new HashMap<>();
				for (ReceiptRecordVO receiptRecordVO : receiptRecordList) {
					receiptRecordMap.put(receiptRecordVO.getReceiptRecordId().longValue(), receiptRecordVO);
				}
				
				List<NewReceiptRecordVO> list = BeanUtil.copyList(NewReceiptRecordVO.class, receiptRecordList);
				List<NewReceiptRecordVO> newReceiptRecordList = new ArrayList<>(); 
				for (NewReceiptRecordVO vo : list) {
					ReceiptRecordVO receiptRecordVO = receiptRecordMap.get(vo.getReceiptRecordId());
					
					ExecuteResult<NewSalesOrderVO> newSalesOrderResult = newSalesOrderExportService.queryById(vo.getSalesOrderId());
					if(Utility.isNotEmpty(newSalesOrderResult.getResult())) {
						NewSalesOrderVO newSalesOrderVO = newSalesOrderResult.getResult();
						BeanUtils.copyProperties(newSalesOrderVO, vo, "createUserId", "delFlg", "createDate", "updateUserId", "modifyDate");
						vo.setCreateUserName(getUserRealName(vo.getCreateUserId()));
						vo.setUpdateUserName(getUserRealName(vo.getUpdateUserId()));
						if(Utility.isNotEmpty(receiptRecordVO.getPaymentChannel())) {
							vo.setPaymentChannelId(receiptRecordVO.getPaymentChannel());
							DictValueVO paymentChannel = getDictValueVO(receiptRecordVO.getPaymentChannel());
							if(Utility.isNotEmpty(paymentChannel)) {
								vo.setPaymentChannelNumber(paymentChannel.getDictValueNumber());
								vo.setPaymentChannelName(paymentChannel.getDictValueName());
							}
						}
						if(Utility.isNotEmpty(newSalesOrderVO.getSalesOrderSourceId())) {
							vo.setSalesOrderResourceId(newSalesOrderVO.getSalesOrderSourceId());
							vo.setSalesOrderResourceNumber(newSalesOrderVO.getSalesOrderSourceNumber());
							vo.setSalesOrderResourceName(newSalesOrderVO.getSalesOrderSourceName());
						}
						
						newReceiptRecordList.add(vo);
						ExecuteResult<NewReceiptRecordVO> queryById = newReceiptRecordExportService.queryById(vo.getReceiptRecordId());
						if(Utility.isNotEmpty(queryById.getResult())) {
							ExecuteResult<NewReceiptRecordVO> update = newReceiptRecordExportService.update(vo);
							System.out.println("update: " + JSONObject.toJSONString(update));
						}else {
							ExecuteResult<NewReceiptRecordVO> add = newReceiptRecordExportService.add(vo);
							System.out.println("add: " + JSONObject.toJSONString(add));
						}
					}
				}
				result.setResult(newReceiptRecordList);
				result.setCode(Constants.SUCCESS_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
				info = ResultUtil.getSelectListAjaxInfo(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	@RequestMapping(value = "salesOrderGoods", method = RequestMethod.GET)
	public AjaxInfo salesOrderGoods() {
		AjaxInfo info = new AjaxInfo();
		ExecuteResult<List<NewSalesOrderGoodsVO>> result = new ExecuteResult<>();
		try {
			ExecuteResult<List<OrderGoodsVO>> orderGoodsResult = orderGoodsExportService.querySales();
			if(Utility.isNotEmpty(orderGoodsResult.getResult())) {
				List<OrderGoodsVO> orderGoodsList = orderGoodsResult.getResult();
				Map<Long, OrderGoodsVO> orderGoodsVOMap = new HashMap<>();
				for (OrderGoodsVO orderGoodsVO : orderGoodsList) {
					orderGoodsVOMap.put(orderGoodsVO.getOrderGoodsId().longValue(), orderGoodsVO);
				}
				List<NewSalesOrderGoodsVO> list = BeanUtil.copyList(NewSalesOrderGoodsVO.class, orderGoodsList);
				List<NewSalesOrderGoodsVO> newSalesOrderGoodsVOList = new ArrayList<>(); 
				for (NewSalesOrderGoodsVO vo : list) {
					OrderGoodsVO orderGoodsVO = orderGoodsVOMap.get(vo.getSalesOrderGoodsId());
					vo.setSalesOrderId(orderGoodsVO.getOrderId().longValue());
					
					AjaxInfo queryGoodsById = feignGoodsService.queryGoodsById(vo.getGoodsId().longValue());
					GoodsVO goodsVO = JSONObject.parseObject(JSONObject.toJSONString(queryGoodsById.getData()), GoodsVO.class);
					if(Utility.isNotEmpty(goodsVO)) {
						BeanUtils.copyProperties(goodsVO, vo, "delFlg");
						vo.setFirstCategoryId(goodsVO.getGoodsFirstCategoryId().intValue());
						vo.setSecondCategoryId(goodsVO.getGoodsSecondCategoryId().intValue());
						vo.setThirdCategoryId(goodsVO.getGoodsThirdCategoryId().intValue());
						
						GoodsCategoryVO first = getGoodsCategory(vo.getFirstCategoryId());
						if(Utility.isNotEmpty(first)) {
							vo.setFirstCategoryNumber(first.getCategoryNumber());
							vo.setFirstCategoryName(first.getCategoryName());
						}
						GoodsCategoryVO second = getGoodsCategory(vo.getSecondCategoryId());
						if(Utility.isNotEmpty(second)) {
							vo.setSecondCategoryNumber(second.getCategoryNumber());
							vo.setSecondCategoryName(second.getCategoryName());
						}
						GoodsCategoryVO third = getGoodsCategory(vo.getThirdCategoryId());
						if(Utility.isNotEmpty(third)) {
							vo.setThirdCategoryNumber(third.getCategoryNumber());
							vo.setThirdCategoryName(third.getCategoryName());
						}
						vo.setGoodsAttributeId(goodsVO.getGoodsAttribute());
						DictValueVO goodsAttribute = getDictValueVO(goodsVO.getGoodsAttribute());
						if(Utility.isNotEmpty(goodsAttribute)) {
							vo.setGoodsAttributeNumber(goodsAttribute.getDictValueNumber());
							vo.setGoodsAttributeName(goodsAttribute.getDictValueName());
						}
					}
					newSalesOrderGoodsVOList.add(vo);
					ExecuteResult<NewSalesOrderGoodsVO> queryById = newSalesOrderGoodsExportService.queryById(vo.getSalesOrderGoodsId().longValue());
					if(Utility.isNotEmpty(queryById.getResult())) {
						ExecuteResult<NewSalesOrderGoodsVO> update = newSalesOrderGoodsExportService.update(vo);
						System.out.println("update: " + JSONObject.toJSONString(update));
					}else {
						ExecuteResult<NewSalesOrderGoodsVO> add = newSalesOrderGoodsExportService.add(vo);
						System.out.println("add: " + JSONObject.toJSONString(add));
					}
				}
				result.setResult(newSalesOrderGoodsVOList);
				result.setCode(Constants.SUCCESS_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
				info = ResultUtil.getSelectListAjaxInfo(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	@RequestMapping(value = "returnOrderGoods", method = RequestMethod.GET)
	public AjaxInfo returnOrderGoods() {
		AjaxInfo info = new AjaxInfo();
		ExecuteResult<List<NewReturnOrderGoodsVO>> result = new ExecuteResult<>();
		try {
			ExecuteResult<List<OrderGoodsVO>> orderGoodsResult = orderGoodsExportService.queryReturn();
			if(Utility.isNotEmpty(orderGoodsResult.getResult())) {
				List<OrderGoodsVO> orderGoodsList = orderGoodsResult.getResult();
				Map<Long, OrderGoodsVO> orderGoodsVOMap = new HashMap<>();
				for (OrderGoodsVO orderGoodsVO : orderGoodsList) {
					orderGoodsVOMap.put(orderGoodsVO.getOrderGoodsId().longValue(), orderGoodsVO);
				}
				List<NewReturnOrderGoodsVO> list = BeanUtil.copyList(NewReturnOrderGoodsVO.class, orderGoodsList);
				List<NewReturnOrderGoodsVO> newReturnOrderGoodsVOList = new ArrayList<>(); 
				for (NewReturnOrderGoodsVO vo : list) {
					OrderGoodsVO orderGoodsVO = orderGoodsVOMap.get(vo.getReturnOrderGoodsId());
					ReturnOrderVO returnOrderVO = new ReturnOrderVO();
					returnOrderVO.setSalesOrderId(orderGoodsVO.getOrderId());
					ExecuteResult<List<ReturnOrderVO>> returnOrderList = returnOrderExportService.queryList(returnOrderVO);
					if(Utility.isNotEmpty(returnOrderList.getResult())) {
						vo.setReturnOrderId(returnOrderList.getResult().get(0).getReturnOrderId().longValue());
					}
					
					AjaxInfo queryGoodsById = feignGoodsService.queryGoodsById(vo.getGoodsId().longValue());
					GoodsVO goodsVO = JSONObject.parseObject(JSONObject.toJSONString(queryGoodsById.getData()), GoodsVO.class);
					if(Utility.isNotEmpty(goodsVO)) {
						BeanUtils.copyProperties(goodsVO, vo, "delFlg");
						vo.setFirstCategoryId(goodsVO.getGoodsFirstCategoryId().intValue());
						vo.setSecondCategoryId(goodsVO.getGoodsSecondCategoryId().intValue());
						vo.setThirdCategoryId(goodsVO.getGoodsThirdCategoryId().intValue());
						
						GoodsCategoryVO first = getGoodsCategory(vo.getFirstCategoryId());
						if(Utility.isNotEmpty(first)) {
							vo.setFirstCategoryNumber(first.getCategoryNumber());
							vo.setFirstCategoryName(first.getCategoryName());
						}
						GoodsCategoryVO second = getGoodsCategory(vo.getSecondCategoryId());
						if(Utility.isNotEmpty(second)) {
							vo.setSecondCategoryNumber(second.getCategoryNumber());
							vo.setSecondCategoryName(second.getCategoryName());
						}
						GoodsCategoryVO third = getGoodsCategory(vo.getThirdCategoryId());
						if(Utility.isNotEmpty(third)) {
							vo.setThirdCategoryNumber(third.getCategoryNumber());
							vo.setThirdCategoryName(third.getCategoryName());
						}
						vo.setGoodsAttributeId(goodsVO.getGoodsAttribute());
						DictValueVO goodsAttribute = getDictValueVO(goodsVO.getGoodsAttribute());
						if(Utility.isNotEmpty(goodsAttribute)) {
							vo.setGoodsAttributeNumber(goodsAttribute.getDictValueNumber());
							vo.setGoodsAttributeName(goodsAttribute.getDictValueName());
						}
					}
					newReturnOrderGoodsVOList.add(vo);
					ExecuteResult<NewReturnOrderGoodsVO> queryById = newReturnOrderGoodsExportService.queryById(vo.getReturnOrderGoodsId().longValue());
					if(Utility.isNotEmpty(queryById.getResult())) {
						ExecuteResult<NewReturnOrderGoodsVO> update = newReturnOrderGoodsExportService.update(vo);
						System.out.println("update: " + JSONObject.toJSONString(update));
					}else {
						ExecuteResult<NewReturnOrderGoodsVO> add = newReturnOrderGoodsExportService.add(vo);
						System.out.println("add: " + JSONObject.toJSONString(add));
					}
				}
				result.setResult(newReturnOrderGoodsVOList);
				result.setCode(Constants.SUCCESS_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
				info = ResultUtil.getSelectListAjaxInfo(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	@RequestMapping(value = "notBuy", method = RequestMethod.GET)
	public AjaxInfo notBuy() {
		AjaxInfo info = new AjaxInfo();
		ExecuteResult<List<NewNotBuyVO>> result = new ExecuteResult<>();
		try {
			ExecuteResult<List<NotBuyVO>> notBuyResult = notBuyExportService.queryList(null);
			if(Utility.isNotEmpty(notBuyResult.getResult())) {
				List<NotBuyVO> notBuyList = notBuyResult.getResult();
				Map<Long, NotBuyVO> notBuyMap = new HashMap<>();
				for (NotBuyVO notBuyVO : notBuyList) {
					notBuyMap.put(notBuyVO.getNotBuyId().longValue(), notBuyVO);
				}
				List<NewNotBuyVO> list = BeanUtil.copyList(NewNotBuyVO.class, notBuyList);
				for (NewNotBuyVO vo : list) {
					NotBuyVO notBuyVO = notBuyMap.get(vo.getNotBuyId());
					
					AjaxInfo queryStoreList = feignStoreService.queryStoreList(String.valueOf(vo.getStoreId()));
					List<StoreVO> storeList = JSONObject.parseArray(JSONObject.toJSONString(queryStoreList.getData()), StoreVO.class);
					if(Utility.isNotEmpty(storeList)) {
						StoreVO storeVO = storeList.get(0);
						vo.setFirstOrgId(storeVO.getFirstOrgVO().getId().intValue());
						vo.setFirstOrgNumber(storeVO.getFirstOrgVO().getOrgCode());//大区编码
						vo.setFirstOrgName(storeVO.getFirstOrgVO().getOrgName());//大区名称
						vo.setSecondOrgId(storeVO.getSecondOrgVO().getId().intValue());
						vo.setSecondOrgNumber(storeVO.getSecondOrgVO().getOrgCode());
						vo.setSecondOrgName(storeVO.getSecondOrgVO().getOrgName());
						vo.setThirdOrgId(storeVO.getThirdOrgVO().getId().intValue());
						vo.setThirdOrgNumber(storeVO.getThirdOrgVO().getOrgCode());
						vo.setThirdOrgName(storeVO.getThirdOrgVO().getOrgName());
						vo.setStoreNumber(storeVO.getStoreNumber());
						vo.setStoreName(storeVO.getStoreName());
						vo.setPartnerId(storeVO.getPartnerVO().getPartnerId());
						vo.setPartnerNumber(storeVO.getPartnerVO().getPartnerNumber());
						vo.setPartnerName(storeVO.getPartnerVO().getPartnerName());
						if(Utility.isNotEmpty(storeVO.getTraderVO())) {
							vo.setTraderId(storeVO.getTraderVO().getPartnerId());
							vo.setTraderNumber(storeVO.getTraderVO().getPartnerNumber());
							vo.setTraderName(storeVO.getTraderVO().getPartnerName());
						}
					}
					
					if(Utility.isNotEmpty(notBuyVO.getNotBuyReason())) {
						vo.setNotBuyReasonId(notBuyVO.getNotBuyReason());
						DictValueVO notBuyReason = getDictValueVO(notBuyVO.getNotBuyReason());
						if(Utility.isNotEmpty(notBuyReason)) {
							vo.setNotBuyReasonNumber(notBuyReason.getDictValueNumber());
							vo.setNotBuyReasonName(notBuyReason.getDictValueName());
						}
					}
					
					Map<String, Object> customerMap = new HashMap<>();
					customerMap.put("customerId", vo.getCustomerId());
					AjaxInfo customerInfo = feignCustomerService.queryCustomerList(customerMap);
					List<CustomerVO> customerList = JSONObject.parseArray(JSONObject.toJSONString(customerInfo.getData()), CustomerVO.class);
					if(Utility.isNotEmpty(customerList)) {
						CustomerVO customerVO = customerList.get(0);
						vo.setCustomerPhoneNumber(customerVO.getCustomerPhoneNumber());
						vo.setCustomerName(customerVO.getCustomerName());
						vo.setCustomerSourceId(customerVO.getCustomerSource());
						DictValueVO customerSource = getDictValueVO(customerVO.getCustomerSource());
						if(Utility.isNotEmpty(customerSource)) {
							vo.setCustomerSourceNumber(customerSource.getDictValueNumber());
							vo.setCustomerSourceName(customerSource.getDictValueName());
						}
					}
					
					if(Utility.isNotEmpty(notBuyVO.getIntentionVehicle())) {
						vo.setVehicleThirdCategoryId(notBuyVO.getIntentionVehicle());
						GoodsCategoryVO third = getGoodsCategory(notBuyVO.getIntentionVehicle());
						if(Utility.isNotEmpty(third)) {
							vo.setVehicleThirdCategoryNumber(third.getCategoryNumber());
							vo.setVehicleThirdCategoryName(third.getCategoryName());
							if(Utility.isNotEmpty(third.getParentId())) {
								GoodsCategoryVO second = getGoodsCategory(third.getParentId().intValue());
								if(Utility.isNotEmpty(second)) {
									vo.setVehicleSecondCategoryId(second.getGoodsCategoryId().intValue());
									vo.setVehicleSecondCategoryNumber(second.getCategoryNumber());
									vo.setVehicleSecondCategoryName(second.getCategoryName());
								}
							}
						}
					}
					vo.setCreateUserName(getUserRealName(vo.getCreateUserId()));
					
					ExecuteResult<NewNotBuyVO> queryById = newNotBuyExportService.queryById(vo.getNotBuyId().longValue());
					if(Utility.isNotEmpty(queryById.getResult())) {
						ExecuteResult<NewNotBuyVO> update = newNotBuyExportService.update(vo);
						System.out.println("update: " + JSONObject.toJSONString(update));
					}else {
						ExecuteResult<NewNotBuyVO> add = newNotBuyExportService.add(vo);
						System.out.println("add: " + JSONObject.toJSONString(add));
					}
				}
				result.setResult(list);
				result.setCode(Constants.SUCCESS_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
				info = ResultUtil.getSelectListAjaxInfo(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	public DictValueVO getDictValueVO(Integer dictValueId) {
		if(Utility.isNotEmpty(dictValueId)) {
			AjaxInfo info = feignDictService.queryValueById(dictValueId);
			DictValueVO dictValueVO = JSONObject.parseObject(JSONObject.toJSONString(info.getData()), DictValueVO.class);
			return dictValueVO;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String getUserRealName(Integer userId) {
		if(Utility.isNotEmpty(userId)) {
			AjaxInfo info = feignUserService.queryByUser(String.valueOf(userId));
			if(Utility.isNotEmpty(info.getData())) {
				Map<String, Object> userMap = (Map<String, Object>) info.getData();
				if (userMap.containsKey("realName")) {
					return Utility.objectToString(userMap.get("realName"));
				}
			}
		}
		return null;
	}
	
	public GoodsCategoryVO getGoodsCategory(Integer categoryId) {
		if(Utility.isNotEmpty(categoryId)) {
			Map<String, Object> map = new HashMap<>();
			map.put("goodsCategoryId", categoryId);
			AjaxInfo info = feignGoodsCategoryService.queryList(map);
			if(Utility.isNotEmpty(info.getData())) {
				List<GoodsCategoryVO> goodsCategoryVO = JSONObject.parseArray(JSONObject.toJSONString(info.getData()), GoodsCategoryVO.class);
				return goodsCategoryVO.get(0);
			}
		}
		return null;
	}
}

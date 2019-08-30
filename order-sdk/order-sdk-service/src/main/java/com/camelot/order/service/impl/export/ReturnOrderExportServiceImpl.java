package com.camelot.order.service.impl.export;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.AjaxInfoConstants;
import com.camelot.order.common.utils.CheckUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.ReturnOrderDAO;
import com.camelot.order.domain.ReturnOrderDomain;
import com.camelot.order.export.service.DictValueExportService;
import com.camelot.order.export.service.ReturnOrderExportService;
import com.camelot.order.export.service.SalesOrderExportService;
import com.camelot.order.export.vo.*;
import com.camelot.order.export.vo.feignvo.FeignGoodsVO;
import com.camelot.order.feign.*;
import com.camelot.order.service.OrderGoodsService;
import com.camelot.order.service.SalesOrderService;
import com.camelot.order.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("returnOrderExportService")
public class ReturnOrderExportServiceImpl extends BaseServiceImpl<ReturnOrderVO, ReturnOrderDomain>
		implements ReturnOrderExportService {
	private static Logger log = Log.get(ReturnOrderExportServiceImpl.class);

	@Autowired
	ReturnOrderDAO returnOrderDAO;

	@Autowired
	FeignOrgService feignOrgService;

	@Autowired
	FeignStoreService feignStoreService;

	@Autowired
	DictValueExportService dictValueExportService;

	@Autowired
	FeignCustomerService feignCustomerService;

	@Autowired
	FeignGoodsPriceService feignGoodsPriceService;

	@Autowired
	OrderGoodsService orderGoodsService;

	@Autowired
	FeignActivityService feignActivityService;

	@Autowired
	FeignCouponService feignCouponService;

	@Autowired
	FeignGoodsService feignGoodsService;

	@Autowired
	FeignUserService feignUserService;

	@Autowired
	SalesOrderService salesOrderService;
	
	@Autowired
	SalesOrderExportService salesOrderExportService;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Override
	public List<ReturnOrderVO> handleReturnOrderList(List<ReturnOrderVO> list) {
		if (Utility.isNotEmpty(list)) {
			for (ReturnOrderVO vo : list) {
				// 由关联订单ID获取对应订单信息
				if (Utility.isNotEmpty(vo.getSalesOrderId())) {
					SalesOrderVO so = salesOrderService.queryById(vo.getSalesOrderId().longValue()).getResult();
					if (Utility.isNotEmpty(so)) {
						// 订单编码
						vo.setSalesOrderNumber(so.getSalesOrderNumber());
						// 大区名称
						if (Utility.isNotEmpty(so.getFirstOrgId())) {
							vo.setFirstOrgName(getOrgNameById(so.getFirstOrgId().longValue()));
						}
						// 区域名称
						if (Utility.isNotEmpty(so.getSecondOrgId())) {
							vo.setSecondOrgName(getOrgNameById(so.getSecondOrgId().longValue()));
						}
						// 城市名称
						if (Utility.isNotEmpty(so.getThirdOrgId())) {
							vo.setThirdOrgName(getOrgNameById(so.getThirdOrgId().longValue()));
						}
						// 门店名称
						if (Utility.isNotEmpty(so.getStoreId())) {
							AjaxInfo storeInfo = feignStoreService.getStoreById(so.getStoreId().longValue());
							if (Utility.isNotEmpty(storeInfo.getData())) {
								@SuppressWarnings("unchecked")
								Map<String, Object> map = (Map) storeInfo.getData();
								if (map.containsKey("storeName")) {
									vo.setStoreName(Utility.objectToString(map.get("storeName")));
								}
							}
						}
						// 订单金额处理:保留两位小数
						if (Utility.isNotEmpty(vo.getReturnOrderAmount())) {
							DecimalFormat format = new DecimalFormat("0.00");
							vo.setReturnOrderAmountValue(format.format(vo.getReturnOrderAmount()));
						}
						// 字典值处理
						// 订单来源
						if (Utility.isNotEmpty(so.getSalesOrderSource())) {
							vo.setSalesOrderSourceValue(dictValueExportService.getDictValueNameById(
									OrderConstants.ORDER_DICT_ORDER_SOURCE, so.getSalesOrderSource()));
						}
						// 订单状态
						if (Utility.isNotEmpty(vo.getReturnOrderState())) {
							vo.setReturnOrderStateValue(dictValueExportService.getDictValueNameById(
									OrderConstants.ORDER_DICT_ORDER_STATUS, vo.getReturnOrderState()));
						}
						// 退货原因
						if (Utility.isNotEmpty(vo.getReturnReason())) {
							vo.setReturnReasonValue(dictValueExportService.getDictValueNameById(
									OrderConstants.ORDER_DICT_RETURN_REASON, vo.getReturnReason()));
						}
					}

				}

			}

		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReturnOrderDetailVO handleReturnOrderDetail(ReturnOrderVO vo) {
		ReturnOrderDetailVO result = new ReturnOrderDetailVO();
		// 由关联订单ID获取对应订单信息
		// 订单状态(退货订单)
		if (Utility.isNotEmpty(vo.getReturnOrderState())) {
			result.setReturnOrderState(dictValueExportService
					.getDictValueNameById(OrderConstants.ORDER_DICT_ORDER_STATUS, vo.getReturnOrderState()));
		}
		// 退货原因
		if (Utility.isNotEmpty(vo.getReturnReason())) {
			result.setReturnReasonValue(dictValueExportService
					.getDictValueNameById(OrderConstants.ORDER_DICT_RETURN_REASON, vo.getReturnReason()));
		}
		// 退款金额
		// 金额保留两位小数
		DecimalFormat format = new DecimalFormat("0.00");
		if (Utility.isNotEmpty(vo.getReturnOrderAmount())) {
			result.setReturnOrderAmount(format.format(vo.getReturnOrderAmount()));
		}
		if (Utility.isNotEmpty(vo.getSalesOrderId())) {
			SalesOrderVO so = salesOrderService.queryById(vo.getSalesOrderId().longValue()).getResult();
			if (Utility.isNotEmpty(so)) {
				result.setActivityPicture(so.getActivityPicture());
				// 订单基础信息
				// 退单编码
				result.setReturnOrderNumber(vo.getReturnOrderNumber());
				// 对应订单编码
				result.setSalesOrderNumber(so.getSalesOrderNumber());
				// 订单来源
				if (Utility.isNotEmpty(so.getSalesOrderSource())) {
					result.setSalesOrderSource(dictValueExportService
							.getDictValueNameById(OrderConstants.ORDER_DICT_ORDER_SOURCE, so.getSalesOrderSource()));
				}
				// 支付方式
				if (Utility.isNotEmpty(so.getPaymentWay())) {
					result.setPaymentWay(dictValueExportService.getDictValueNameById(OrderConstants.ORDER_DICT_PAYMENT,
							so.getPaymentWay()));
				}
				// 门店信息
				if (Utility.isNotEmpty(so.getStoreId())) {
					AjaxInfo storeInfo = feignStoreService.getStoreById(so.getStoreId().longValue());
					if (Utility.isNotEmpty(storeInfo.getData())) {
						@SuppressWarnings("unchecked")
						Map<String, Object> map = (Map) storeInfo.getData();
						if (map.containsKey("storeNumber")) {
							result.setStoreNumber(Utility.objectToString(map.get("storeNumber")));
						}
						if (map.containsKey("storeName")) {
							result.setStoreName(Utility.objectToString(map.get("storeName")));
						}
					}
				}
				// 消费者信息
				if (Utility.isNotEmpty(so.getCustomerId())) {
					Map<String, Object> customerMap = new HashMap<String, Object>();
					customerMap.put("customerId", so.getCustomerId());
					AjaxInfo customerInfo = feignCustomerService.queryCustomerList(customerMap);
					if (Utility.isNotEmpty(customerInfo.getData())) {
						Map<String, Object> customerResultMap = ((List<Map<String, Object>>) customerInfo.getData())
								.get(0);
						// 消费者姓名
						if (customerResultMap.containsKey("customerName")) {
							result.setCustomerName(Utility.objectToString(customerResultMap.get("customerName")));
						}
						// 消费者手机号
						if (customerResultMap.containsKey("customerPhoneNumber")) {
							result.setCustomerPhoneNumber(Utility.objectToString(customerResultMap.get("customerPhoneNumber")));
						}
						// 消费者来源
						if (customerResultMap.containsKey("customerSource")) {
							Integer customerSourceId = Utility.stringToInteger(Utility.objectToString(customerResultMap.get("customerSource")));
							if (Utility.isNotEmpty(customerSourceId)) {
								result.setCustomerSource(dictValueExportService.getDictValueNameById(
										OrderConstants.ORDER_DICT_CUSTOMER_SOURCE, customerSourceId));
							}
						}
					}
				}
				// 活动名称
				if (Utility.isNotEmpty(so.getActivityId())) {
					AjaxInfo activityInfo = feignActivityService.queryById(so.getActivityId());
					if (Utility.isNotEmpty(activityInfo.getData())) {
						Map<String, Object> activityMap = (Map<String, Object>) activityInfo.getData();
						if (activityMap.containsKey("activityName")) {
							result.setActivityName(Utility.objectToString(activityMap.get("activityName")));
						}
					}
				}
				// 活动码
				if (Utility.isNotEmpty(so.getCouponId())) {
					AjaxInfo couponInfo = feignCouponService.queryById(so.getCouponId());
					if (Utility.isNotEmpty(couponInfo.getData())) {
						Map<String, Object> couponMap = (Map<String, Object>) couponInfo.getData();
						if (couponMap.containsKey("couponCode")) {
							result.setCouponNumber(Utility.objectToString(couponMap.get("couponCode")));
						}
					}
				}
				// 商品信息
				OrderGoodsVO og = new OrderGoodsVO();
				og.setOrderId(so.getSalesOrderId());
				List<OrderGoodsVO> ogList = orderGoodsService.queryList(og).getResult();
				if (Utility.isNotEmpty(ogList)) {
					List<FeignGoodsVO> goodsList = new ArrayList<FeignGoodsVO>();
					// 实付合计:各个商品实付金额 总和
					BigDecimal actualTotal = new BigDecimal(0);
					for (OrderGoodsVO o : ogList) {
						FeignGoodsVO fg = new FeignGoodsVO();
						// 由id获取商品信息
						AjaxInfo goodsInfo = feignGoodsService.queryGoodsById(o.getGoodsId().longValue());
						if (Utility.isNotEmpty(goodsInfo.getData())) {
							Map<String, Object> goodsMap = (Map<String, Object>) goodsInfo.getData();
							// 商品编号
							if (goodsMap.containsKey("goodsNumber")) {
								fg.setGoodsNumber(Utility.objectToString(goodsMap.get("goodsNumber")));
							}
							// 商品名称
							if (goodsMap.containsKey("goodsName")) {
								fg.setGoodsName(Utility.objectToString(goodsMap.get("goodsName")));
							}
							// 商品69码
							if (goodsMap.containsKey("goodsBarcode")) {
								fg.setGoodsBarcode(Utility.objectToString(goodsMap.get("goodsBarcode")));
							}
						}
						// 商品SN码
						fg.setGoodsSN(o.getGoodsSn());
						// 商品车架号
						fg.setGoodsFramecode(o.getGoodsFrameNumber());
						// 指导零售价
						BigDecimal retailPrice = o.getRetailPrice();
						fg.setRetailPrice(format.format(retailPrice));
						// 实销单价
						BigDecimal actualPrice = o.getActualPrice();
						fg.setActualPrice(format.format(actualPrice));
						// 数量
						fg.setOrderAmount(o.getOrderAmount());
						// 小计:零售单价*数量
						BigDecimal amount = new BigDecimal(o.getOrderAmount());
						BigDecimal retailPriceSubtotal = retailPrice.multiply(amount);
						fg.setRetailPriceSubtotal(format.format(retailPriceSubtotal));
						// 实付金额:实销单价*数量
						BigDecimal actualPriceSubtotal = actualPrice.multiply(amount);
						fg.setActualPriceSubtotal(format.format(actualPriceSubtotal));
						// 零售差异
						BigDecimal retailDifference = retailPriceSubtotal.subtract(actualPriceSubtotal);
						fg.setRetailDifference(format.format(retailDifference));
						actualTotal = actualTotal.add(actualPriceSubtotal);
						goodsList.add(fg);
					}
					// 商品集合
					result.setGoodsList(goodsList);
					// 实付金额
					result.setActualTotal(format.format(actualTotal));
				}
				// 操作时间
				result.setCreateDate(vo.getCreateDate());
				// 操作人
				if (Utility.isNotEmpty(vo.getCreateUserId())) {
					AjaxInfo userInfo = feignUserService.queryByUser(vo.getCreateUserId().toString());
					if (Utility.isNotEmpty(userInfo.getData())) {
						Map<String, Object> userMap = (Map<String, Object>) userInfo.getData();
						if (userMap.containsKey("realName")) {
							if (Utility.isNotEmpty(userMap.get("realName"))) {
								result.setCreateUserName(userMap.get("realName").toString());
							}
						}

					}
				}
			}
		}
		return result;
	}

	/**
	 * @Description 根据区域id获取区域名称
	 * @author xupengfei
	 * @Data 2019年4月5日
	 * @param id
	 * @return
	 */
	public String getOrgNameById(Long id) {
		String name = "";
		AjaxInfo info = new AjaxInfo();
		info = feignOrgService.queryById(id);
		if (Utility.isNotEmpty(info.getData())) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map) info.getData();
			if (map.containsKey("orgName")) {
				name = Utility.objectToString(map.get("orgName"));
			}
		}
		return name;
	}

	@Override
	public String getMaxReturnOrderNumber(String nowDate) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ReturnOrderExportServiceImpl-getMaxReturnOrderNumber", nowDate);
		String result = "";
		try {
			result = returnOrderDAO.getMaxReturnOrderNumber(nowDate);
		} catch (Exception e) {
			Log.error(log, "\n 方法[{}]，异常：[{}]", "ReturnOrderExportServiceImpl-getMaxReturnOrderNumber", e.getMessage());
			return Constants.ERROR_CODE;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "ReturnOrderExportServiceImpl-getMaxReturnOrderNumber", result);
		return result;
	}

	@Override
	public ExecuteResult<String> submitReturnOrder(ReturnOrderVO vo) throws Exception {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ReturnOrderExportServiceImpl-submitReturnOrder",
				JSONObject.toJSONString(vo));
		ExecuteResult<String> result = new ExecuteResult<String>();
		String validateResult = validateReturnOrderNotEmpty(vo);
		// 参数非空校验
		if (!Constants.SUCCESS_CODE.equals(validateResult)) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(validateResult);
			Log.debug(log, "\n 方法[{}]，出参：[{}]", "ReturnOrderExportServiceImpl-submitReturnOrder", result);
			return result;
		}
		//退货订单重复性校验
		ReturnOrderVO repeatVO = new ReturnOrderVO();
		repeatVO.setSalesOrderId(vo.getSalesOrderId());
		ExecuteResult<List<ReturnOrderVO>> repeatResult = queryList(repeatVO);
		if(CheckUtil.checkResultListIsNotNull(repeatResult)){
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("该订单已退货完成!");
			Log.debug(log, "\n 方法[{}]，出参：[{}]", "ReturnOrderExportServiceImpl-submitReturnOrder", result);
			return result;
		}
		// 获取订单信息
		SalesOrderVO salesOrder = salesOrderService.queryById(vo.getSalesOrderId().longValue()).getResult();
		if (Utility.isNotEmpty(salesOrder)) {
			// 订单版本号
			Integer version = salesOrder.getSalesOrderVersion();
			// 获取退单编号
			String number = OrderNumberExportService.getOrderNumber(OrderConstants.ORDER_TYPE_RETURN,
					vo.getStoreNumber());
			if (Utility.isNotEmpty(number)) {
				vo.setReturnOrderNumber(number);
				// 设置订单状态:已退货
				// TODO 一期:整单退
				vo.setReturnOrderState(OrderConstants.ORDER_STATUS_RETURN);
				// TODO 一期退单金额=订单金额
				if (Utility.isEmpty(vo.getReturnOrderAmount())) {
					vo.setReturnOrderAmount(salesOrder.getSalesOrderAmount());
				}
				// 基础字段
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date nowDate = df.parse(df.format(new Date()));
				// 设置时间
				vo.setCreateDate(nowDate);
				vo.setModifyDate(nowDate);
				// 设置del_flg和版本号
				vo.setDelFlg(Constants.DELFLG_NORMAL);
				// 设置创建人
				vo.setCreateUserId(vo.getCreateUserId());
				vo.setUpdateUserId(vo.getCreateUserId());
				// 新增退货订单
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						try {
							ExecuteResult<ReturnOrderVO> addResult = add(vo);
							if (Constants.SUCCESS_CODE.equals(addResult.getCode())) {
								// 修改销售订单状态(带版本号)
								SalesOrderVO so = new SalesOrderVO();
								so.setSalesOrderId(vo.getSalesOrderId());
								so.setSalesOrderStatus(OrderConstants.ORDER_STATUS_RETURN);
								so.setSalesOrderVersion(version);
								// 修改人和修改时间
								so.setUpdateUserId(vo.getCreateUserId());
								so.setModifyDate(nowDate);
								ExecuteResult<SalesOrderVO> updateResult = salesOrderService.update(so);
								if (Constants.ERROR_CODE.equals(updateResult.getCode())) {
									result.setCode(Constants.ERROR_CODE);
									result.setResultMessage("该订单已被占用");
									// 事务回滚
									status.setRollbackOnly();
								} else {
									//启用优惠码
									if(Utility.isNotEmpty(salesOrder.getCouponId())){
										Map<String,Object> couponMap = new HashMap<String,Object>();
										couponMap.put("tCouponId",salesOrder.getCouponId());
										couponMap.put("isWriteOff",Constants.DELFLG_NORMAL);
										AjaxInfo couponInfo = feignCouponService.destroy(couponMap);
										if(AjaxInfoConstants.ERROR_CODE.equals(couponInfo.getCode())){
											result.setCode(Constants.ERROR_CODE);
											result.setResultMessage("优惠码启用失败");
											//事务回滚
											result.setResult(null);
											status.setRollbackOnly();
										}else{
											result.setCode(Constants.SUCCESS_CODE);
											result.setResultMessage(Constants.SAVE_SUCCESS);
											result.setResult(number);
										}
									}else{
										result.setCode(Constants.SUCCESS_CODE);
										result.setResultMessage(Constants.SAVE_SUCCESS);
										result.setResult(number);
									}
								}
							} else {
								result.setCode(Constants.ERROR_CODE);
								result.setResultMessage(addResult.getResultMessage());
								status.setRollbackOnly();
							}
						} catch (Exception e) {
							result.setCode(Constants.ERROR_CODE);
							result.setResultMessage(e.toString());
							result.setResult(null);
							Log.error(log, "\n 方法[{}]，异常：[{}]", "ReturnOrderExportServiceImpl-submitReturnOrder",
									e.getMessage());
							// 事务回滚
							status.setRollbackOnly();
						}
					}
				});
			} else {
				result.setCode(Constants.ERROR_CODE);
				result.setResultMessage("退单编码获取失败");
			}

		} else {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("销售订单不存在");
		}

		Log.debug(log, "\n 方法[{}]，出参：[{}]", "ReturnOrderExportServiceImpl-submitReturnOrder", result);
		return result;
	}

	/**
	 * @Description 校验退单必须参数是否为空
	 * @author xupengfei
	 * @Date 2019年4月9日
	 * @param vo
	 * @return
	 */
	public String validateReturnOrderNotEmpty(ReturnOrderVO vo) {
		if (Utility.isEmpty(vo.getSalesOrderId())) {
			return "订单ID为空";
		} /*
			 * else if (Utility.isEmpty(vo.getReturnOrderAmount())) { return
			 * "订单金额为空"; }
			 */ else if (Utility.isEmpty(vo.getReturnReason())) {
			return "退货原因为空";
		} else if (Utility.isEmpty(vo.getCreateUserId())) {
			return "操作人为空";
		} else if (Utility.isEmpty(vo.getStoreNumber())) {
			return "门店编码为空";
		} else {
			return Constants.SUCCESS_CODE;
		}
	}

	@Override
	public List<ReturnOrderMobileVO> handleMobileReturnOrderList(List<ReturnOrderVO> list) {
		List<ReturnOrderMobileVO> result = new ArrayList<ReturnOrderMobileVO>();
		if (Utility.isNotEmpty(list)) {
			for (ReturnOrderVO ro : list) {
				ReturnOrderMobileVO rom = new ReturnOrderMobileVO();
				// 订单ID
				rom.setReturnOrderId(ro.getReturnOrderId());
				// 订单号
				rom.setReturnOrderNumber(ro.getReturnOrderNumber());
				// 退货时间
				rom.setModifyDate(ro.getModifyDate());
				if (Utility.isNotEmpty(ro.getSalesOrderId())) {
					// 关联订单信息
					SalesOrderVO so = salesOrderService.queryById(ro.getSalesOrderId().longValue()).getResult();
					if (Utility.isNotEmpty(so)) {
						// 消费者信息
						if (Utility.isNotEmpty(so.getCustomerId())) {
							Map<String, Object> customerMap = new HashMap<String, Object>();
							customerMap.put("customerId", so.getCustomerId());
							AjaxInfo customerInfo = feignCustomerService.queryCustomerList(customerMap);
							if (Utility.isNotEmpty(customerInfo.getData())) {
								Map<String, Object> customerResultMap = ((List<Map<String, Object>>) customerInfo
										.getData()).get(0);
								// 消费者姓名
								if (customerResultMap.containsKey("customerName")) {
									rom.setCustomerName(Utility.objectToString(customerResultMap.get("customerName")));
								}
								// 消费者手机号
								if (customerResultMap.containsKey("customerPhoneNumber")) {
									rom.setCustomerPhoneNumber(Utility.objectToString(customerResultMap.get("customerPhoneNumber")));
								}
								// 消费者来源
								if (customerResultMap.containsKey("customerSource")) {
									Integer customerSourceId = Utility.stringToInteger(Utility.objectToString(customerResultMap.get("customerSource")));
									if (Utility.isNotEmpty(customerSourceId)) {
										rom.setCustomerSource(dictValueExportService.getDictValueNameById(
												OrderConstants.ORDER_DICT_CUSTOMER_SOURCE, customerSourceId));
									}
								}
							}
						}
						// 商品名称集合
						// 商品信息
						OrderGoodsVO og = new OrderGoodsVO();
						og.setOrderId(so.getSalesOrderId());
						List<OrderGoodsVO> ogList = orderGoodsService.queryList(og).getResult();
						if (Utility.isNotEmpty(ogList)) {
							List<String> goodsList = new ArrayList<String>();
							for (OrderGoodsVO o : ogList) {
								// 由id获取商品信息
								AjaxInfo goodsInfo = feignGoodsService.queryGoodsById(o.getGoodsId().longValue());
								if (Utility.isNotEmpty(goodsInfo.getData())) {
									Map<String, Object> goodsMap = (Map<String, Object>) goodsInfo.getData();
									// 商品名称
									if (goodsMap.containsKey("goodsName")) {
										goodsList.add(Utility.objectToString(goodsMap.get("goodsName")));
									}
								}
							}
							rom.setGoodsList(goodsList);
						}
					}
				}
				result.add(rom);
			}
		}
		return result;
	}

	@Override
	public ReturnOrderVO handleVOBeforeQuery(ReturnOrderVO vo) {
		// 是否需要根据订单表查询
		boolean isSalesOrder = false;
		SalesOrderVO so = new SalesOrderVO();
		//权限处理
		if(Utility.isNotEmpty(vo.getStoreStr()) || Utility.isNotEmpty(vo.getOrgStr()) || Utility.isNotEmpty(vo.getSearchStoreStr())){
			isSalesOrder = true;
			so.setOrgStr(vo.getOrgStr());
			so.setStoreStr(vo.getStoreStr());
			so.setSearchStoreStr(vo.getSearchStoreStr());
			so=salesOrderExportService.handleDataPermission(so);
		}
		//商品sn码查询
		if(Utility.isNotEmpty(vo.getGoodsSn())){
			isSalesOrder = true;
			OrderGoodsVO og = new OrderGoodsVO();
			og.setGoodsSn(vo.getGoodsSn());
			List<OrderGoodsVO> ogList = orderGoodsService.queryList(og).getResult();
			if(Utility.isNotEmpty(ogList)){
				List<Integer> salesOrderIdList = new ArrayList<Integer>();
				for(OrderGoodsVO ogv : ogList){
					salesOrderIdList.add(ogv.getOrderId());
				}
				so.setSalesOrderIdList(salesOrderIdList);
			}
		}
		// 由单号开头判断是否是是销售订单编号
		if (Utility.isNotEmpty(vo.getSalesOrderNumber())) {
			isSalesOrder = true;
			so.setSalesOrderNumber(vo.getSalesOrderNumber());
		}
		// 门店ID
		if (Utility.isNotEmpty(vo.getStoreId())) {
			isSalesOrder = true;
			so.setStoreId(vo.getStoreId());
		}
		// 订单来源
		if (Utility.isNotEmpty(vo.getSalesOrderSource())) {
			isSalesOrder = true;
			so.setSalesOrderSource(vo.getSalesOrderSource());
		}
		//区域信息
		if(Utility.isNotEmpty(vo.getFirstOrgId())){
			isSalesOrder = true;
			so.setFirstOrgId(vo.getFirstOrgId());
		}
		if(Utility.isNotEmpty(vo.getSecondOrgId())){
			isSalesOrder = true;
			so.setSecondOrgId(vo.getSecondOrgId());
		}
		if(Utility.isNotEmpty(vo.getThirdOrgId())){
			isSalesOrder = true;
			so.setThirdOrgId(vo.getThirdOrgId());
		}
		// 如果涉及到销售订单则查询出符合的销售订单id
		if (isSalesOrder) {
			List<Integer> idList = new ArrayList<Integer>();
			List<SalesOrderVO> soList = salesOrderService.queryList(so).getResult();
			if (Utility.isNotEmpty(soList)) {
				for (SalesOrderVO sv : soList) {
					idList.add(sv.getSalesOrderId());
				}
				vo.setSalesOrderIdList(idList);
			} else {
				idList.add(-1);
				vo.setSalesOrderIdList(idList);
			}
		}

		return vo;
	}

	@Override
	public ReturnOrderVO handleMobileVOBeforeQuery(ReturnOrderVO vo) {
		//本门店id
		Integer localStoreId = vo.getStoreId();
		List<Integer> salesOrderIdList = new ArrayList<Integer>();
		if(Utility.isNotEmpty(vo.getStoreId())){
			// 查询对应的本门店销售订单ID集合
			SalesOrderVO salesOrder = new SalesOrderVO();
			salesOrder.setStoreId(vo.getStoreId());
			List<SalesOrderVO> salesOrderList = salesOrderService.queryList(salesOrder).getResult();
			if (Utility.isNotEmpty(salesOrderList)) {
				for (SalesOrderVO sov : salesOrderList) {
					salesOrderIdList.add(sov.getSalesOrderId());
				}
			} else {
				//本店订单为空
				salesOrderIdList.add(-1);
				vo.setSalesOrderIdList(salesOrderIdList);
				return vo;
			}
		}
		// 是否需要调用Order-Goods
		boolean isCode = false;
		// 是否需要调用消费者信息
		boolean isCustomer = false;
		OrderGoodsVO og = new OrderGoodsVO();
		// sn码
		if (Utility.isNotEmpty(vo.getGoodsSn())) {
			isCode = true;
			og.setGoodsSn(vo.getGoodsSn());
		}
		// 车架号
		if (Utility.isNotEmpty(vo.getGoodsFrameNumber())) {
			isCode = true;
			og.setGoodsFrameNumber(vo.getGoodsFrameNumber());
		}
		// 69码
		if (Utility.isNotEmpty(vo.getGoodsBarcode())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("goodsBarcode", vo.getGoodsBarcode());
			AjaxInfo goodsInfo = feignGoodsService.queryGoodsList(map);
			if (Utility.isNotEmpty(goodsInfo.getData())) {
				Map<String, Object> goodsMap = ((List<Map<String, Object>>) goodsInfo.getData()).get(0);
				if (goodsMap.containsKey("goodsId")) {
					isCode = true;
					og.setGoodsId(Utility.stringToInteger(Utility.objectToString(goodsMap.get("goodsId"))));
				}
			}
		}
		// 消费者作查询条件
		Map<String, Object> customerMap = new HashMap<String, Object>();
		if (Utility.isNotEmpty(vo.getCustomerName())) {
			customerMap.put("customerName", vo.getCustomerName());
			isCustomer = true;
		}

		if (Utility.isNotEmpty(vo.getCustomerPhoneNumber())) {
			customerMap.put("customerPhoneNumber", vo.getCustomerPhoneNumber());
			isCustomer = true;
		}
		// 涉及相关商品的条件从Order-Goods里获取订单ID集合
		List<Integer> idList = new ArrayList<Integer>();
		List<Integer> salesOrderIdList1 = new ArrayList<Integer>();
		List<Integer> salesOrderIdList2 = new ArrayList<Integer>();
		if (isCode) {
			List<OrderGoodsVO> ogList = orderGoodsService.queryList(og).getResult();
			if (Utility.isNotEmpty(ogList)) {
				for (OrderGoodsVO ogv : ogList) {
					//查询是否符合门店ID
					SalesOrderVO so = salesOrderService.queryById(ogv.getOrderId().longValue()).getResult();
					if(Utility.isNotEmpty(so) && localStoreId.equals(so.getStoreId())){
						salesOrderIdList1.add(ogv.getOrderId());
					}
				}
				if(Utility.isEmpty(salesOrderIdList1)){
					//相关订单为空
					salesOrderIdList1.add(-1);
					vo.setSalesOrderIdList(salesOrderIdList1);
					return vo;
				}
			} else {
				//相关订单为空
				salesOrderIdList1.add(-1);
				vo.setSalesOrderIdList(salesOrderIdList1);
				return vo;
			}
		}
		// 涉及消费者相关商品条件从销售订单获取订单ID集合
		if (isCustomer) {
			List<Integer> customerIdList = new ArrayList<Integer>();
			AjaxInfo customerInfo = feignCustomerService.queryCustomerList(customerMap);
			if (Utility.isNotEmpty(customerInfo.getData())) {
				List<Map<String, Object>> customerList = (List<Map<String, Object>>) customerInfo.getData();
				for (Map<String, Object> map : customerList) {
					if (map.containsKey("customerId")) {
						customerIdList.add(Utility.stringToInteger(Utility.objectToString(map.get("customerId"))));
					}
				}
				// 查询对应的销售订单ID集合
				SalesOrderVO so = new SalesOrderVO();
				so.setCustomerIdList(customerIdList);
				//门店ID
				so.setStoreId(localStoreId);
				List<SalesOrderVO> soList = salesOrderService.queryList(so).getResult();
				if (Utility.isNotEmpty(soList)) {
					for (SalesOrderVO sov : soList) {
						salesOrderIdList2.add(sov.getSalesOrderId());
					}
				} else {
					salesOrderIdList2.add(-1);
					vo.setSalesOrderIdList(salesOrderIdList2);
					return vo;
				}
			} else {
				salesOrderIdList2.add(-1);
				vo.setSalesOrderIdList(salesOrderIdList2);
				return vo;
			}
		}
		// 合并交集
		if (isCode && !isCustomer) {
			idList = salesOrderIdList1;
		} else if (!isCode && isCustomer) {
			idList = salesOrderIdList2;
		} else if (isCode && isCustomer) {
			salesOrderIdList1.retainAll(salesOrderIdList2);
			if (Utility.isNotEmpty(salesOrderIdList1)) {
				idList = salesOrderIdList1;
			} else {
				idList.add(-1);
			}
		} else {
			idList = salesOrderIdList;
		}
		vo.setSalesOrderIdList(idList);
		return vo;
	}

}

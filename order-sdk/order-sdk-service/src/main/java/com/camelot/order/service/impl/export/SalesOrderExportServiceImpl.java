package com.camelot.order.service.impl.export;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.*;
import com.camelot.order.dao.SalesOrderDAO;
import com.camelot.order.domain.SalesOrderDomain;
import com.camelot.order.export.service.DictValueExportService;
import com.camelot.order.export.service.SalesOrderExportService;
import com.camelot.order.export.vo.*;
import com.camelot.order.export.vo.feignvo.FeignGoodsVO;
import com.camelot.order.feign.*;
import com.camelot.order.service.NotBuyService;
import com.camelot.order.service.OrderGoodsService;
import com.camelot.order.service.ReturnOrderService;
import com.camelot.order.service.SalesOrderService;
import com.camelot.order.service.base.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service("salesOrderExportService")
public class SalesOrderExportServiceImpl extends BaseServiceImpl<SalesOrderVO, SalesOrderDomain>
		implements SalesOrderExportService {

	private static Logger log = Log.get(SalesOrderExportServiceImpl.class);

	@Autowired
	SalesOrderDAO salesOrderDAO;

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
	TransactionTemplate transactionTemplate;

	@Autowired
	SalesOrderService salesOrderService;

	@Autowired
	ReturnOrderService returnOrderService;

	@Autowired
	NotBuyService notBuyService;
	@Autowired
    FeignActivityCouponService feignActivityCouponService;

	@Override
	public String getMaxSalesOrderNumber(String nowDate) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderExportServiceImpl-getMaxSalesOrderNumber", nowDate);
		String result = "";
		try {
			result = salesOrderDAO.getMaxSalesOrderNumber(nowDate);
		} catch (Exception e) {
			Log.error(log, "\n 方法[{}]，异常：[{}]", "SalesOrderExportServiceImpl-getMaxSalesOrderNumber", e.getMessage());
			return Constants.ERROR_CODE;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-getMaxSalesOrderNumber", result);
		return result;
	}

	@Override
	public List<SalesOrderVO> handleSalesOrderList(List<SalesOrderVO> list) {
		if (Utility.isNotEmpty(list)) {
			for (SalesOrderVO vo : list) {
				// 大区名称
				if (Utility.isNotEmpty(vo.getFirstOrgId())) {
					vo.setFirstOrgName(getOrgNameById(vo.getFirstOrgId().longValue()));
				}
				// 区域名称
				if (Utility.isNotEmpty(vo.getSecondOrgId())) {
					vo.setSecondOrgName(getOrgNameById(vo.getSecondOrgId().longValue()));
				}
				// 城市名称
				if (Utility.isNotEmpty(vo.getThirdOrgId())) {
					vo.setThirdOrgName(getOrgNameById(vo.getThirdOrgId().longValue()));
				}
				// 门店名称/门店编码
				if (Utility.isNotEmpty(vo.getStoreId())) {
					AjaxInfo storeInfo = feignStoreService.getStoreById(vo.getStoreId().longValue());
					if (Utility.isNotEmpty(storeInfo.getData())) {
						@SuppressWarnings("unchecked")
						Map<String, Object> map = (Map) storeInfo.getData();
						if (map.containsKey("storeName")) {
							vo.setStoreName(Utility.objectToString(map.get("storeName")));
						}
						if (map.containsKey("storeNumber")) {
							vo.setStoreNumber(Utility.objectToString(map.get("storeNumber")));
						}
					}
				}
				// 消费者来源
				if (Utility.isNotEmpty(vo.getCustomerId())) {
					Map<String, Object> customerMap = new HashMap<String, Object>();
					customerMap.put("customerId", vo.getCustomerId());
					AjaxInfo customerInfo = feignCustomerService.queryCustomerList(customerMap);
					if (Utility.isNotEmpty(customerInfo.getData())) {
						Map<String, Object> customerResultMap = ((List<Map<String, Object>>) customerInfo.getData())
								.get(0);
						if (customerResultMap.containsKey("customerSource")) {
							Integer customerSourceId = Utility
									.stringToInteger(Utility.objectToString(customerResultMap.get("customerSource")));
							if (Utility.isNotEmpty(customerSourceId)) {
								vo.setCustomerSource(dictValueExportService.getDictValueNameById(
										OrderConstants.ORDER_DICT_CUSTOMER_SOURCE, customerSourceId));
							}
						}
					}
				}
				// 订单金额处理:保留两位小数
				if (Utility.isNotEmpty(vo.getSalesOrderAmount())) {
					DecimalFormat format = new DecimalFormat("0.00");
					vo.setSalesOrderAmountValue(format.format(vo.getSalesOrderAmount()));
				}
				// 字典值处理
				// 支付方式
				if (Utility.isNotEmpty(vo.getPaymentWay())) {
					vo.setPaymentWayValue(dictValueExportService.getDictValueNameById(OrderConstants.ORDER_DICT_PAYMENT,
							vo.getPaymentWay()));
				}
				// 订单来源
				if (Utility.isNotEmpty(vo.getSalesOrderSource())) {
					vo.setSalesOrderSourceValue(dictValueExportService
							.getDictValueNameById(OrderConstants.ORDER_DICT_ORDER_SOURCE, vo.getSalesOrderSource()));
				}
				// 订单状态
				if (Utility.isNotEmpty(vo.getSalesOrderStatus())) {
					vo.setSalesOrderStatusValue(dictValueExportService
							.getDictValueNameById(OrderConstants.ORDER_DICT_ORDER_STATUS, vo.getSalesOrderStatus()));
				}
				// 已退货订单状态改为已退货则补充退单编号,并展示为已完成
				if (OrderConstants.ORDER_STATUS_RETURN.equals(vo.getSalesOrderStatus())) {
					vo.setSalesOrderStatusValue(OrderConstants.ORDER_STATUS_FINISH_VALUE);
					// TODO 一期整单退:展示第一条退货订单编号
					if (Utility.isNotEmpty(vo.getSalesOrderId())) {
						ReturnOrderVO returnOrderVO = new ReturnOrderVO();
						returnOrderVO.setSalesOrderId(vo.getSalesOrderId());
						ExecuteResult<List<ReturnOrderVO>> returnResult = returnOrderService.queryList(returnOrderVO);
						if (CheckUtil.checkResultListIsNotNull(returnResult)) {
							ReturnOrderVO ro = returnResult.getResult().get(0);
							vo.setReturnOrderNumber(ro.getReturnOrderNumber());
						}
					}

				}

			}

		}
		return list;
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

	@SuppressWarnings("unchecked")
	@Override
	public ExecuteResult<String> submitSalesOrder(SalesOrderViewVO vo) throws Exception {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderExportServiceImpl-submitSalesOrder",
				JSONObject.toJSONString(vo));
		ExecuteResult<String> result = new ExecuteResult<String>();
		// 校验必填字段
		String validateResult = validateSalesOrderNotEmpty(vo);
		if (!Constants.SUCCESS_CODE.equals(validateResult)) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(validateResult);
			Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-submitSalesOrder", result);
			return result;
		}
		// TODO 校验优惠吗是否已经核销
		// 这次活动先校验本地活动,再调用接口校验,下单完成核销本地再调接口核销接口
        if (Utility.isNotEmpty(vo.getCouponNumber())) {
            // 判断高伟的活动码是否已经核销
            AjaxInfo couponInfo = feignActivityCouponService.verifyCode(vo.getCouponNumber());
            Map<String, Object> verifyCodeMap = (Map<String, Object>) couponInfo.getData();
            if (verifyCodeMap.containsKey("status")) {
                Integer status = (Integer) verifyCodeMap.get("status");
                // 本次活动的优惠码数据
                if (status.equals(Constants.VERIFY_CODE_200)||status.equals(Constants.VERIFY_CODE_201)) { //不存在
                    // 放行
                } else if(status.equals(Constants.VERIFY_CODE_202)||status.equals(Constants.VERIFY_CODE_203)||status.equals(Constants.VERIFY_CODE_204)||status.equals(Constants.VERIFY_CODE_205)||status.equals(Constants.VERIFY_CODE_206)||status.equals(Constants.VERIFY_CODE_500)){
                    result.setCode(Constants.ERROR_CODE);
                    result.setResultMessage(verifyCodeMap.get("message").toString());
                    Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-submitSalesOrder", result);
                    return result;
                }
            }
        }
		// 销售订单重复性校验
		SalesOrderVO repeatVO = new SalesOrderVO();
		repeatVO.setTransactionId(vo.getTransactionId());
		ExecuteResult<List<SalesOrderVO>> repeatResult = queryList(repeatVO);
		if (CheckUtil.checkResultListIsNotNull(repeatResult)) {
			SalesOrderVO repeatso = repeatResult.getResult().get(0);
			String status = dictValueExportService.getDictValueNameById(OrderConstants.ORDER_DICT_ORDER_STATUS,
					repeatso.getSalesOrderStatus());
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("该订单" + status);
			Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-submitSalesOrder", result);
			return result;
		}
		Map<String, Object> customerMap = new HashMap<String, Object>();
		// 消费者姓名
		customerMap.put("customerName", vo.getCustomerName());
		// 消费者手机号
		customerMap.put("customerPhoneNumber", vo.getCustomerPhoneNumber());
		// 消费者来源
		customerMap.put("customerSource", vo.getCustomerSource());
		// 创建者id
		customerMap.put("createUserId", vo.getCreateUserId());
		// 区域(城市ID)
		customerMap.put("gid", vo.getCityId());
		AjaxInfo customerInfo = feignCustomerService.saveCustomer(customerMap);
		if (Utility.isNotEmpty(customerInfo.getData())) {
			Map<String, Object> customerIdMap = (Map) customerInfo.getData();
			if (customerIdMap.containsKey("customerId")) {
				vo.setCustomerId(Utility.stringToInteger(Utility.objectToString(customerIdMap.get("customerId"))));
			}
		} else {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("消费者信息插入失败");
			Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-submitSalesOrder", result);
			return result;
		}
		// 新增订单信息
		// 将前端传回对象转换为订单实体类
		SalesOrderVO so = (SalesOrderVO) BeanUtil.copyPropertes(SalesOrderVO.class, vo);
		// 设置订单状态:已提交
		so.setSalesOrderStatus(OrderConstants.ORDER_STATUS_SUBMISSION);
		// 设置订单来源:一阶段:线下门店订单
		if (Utility.isEmpty(so.getSalesOrderSource())) {
			so.setSalesOrderSource(OrderConstants.ORDER_SOURCE_STORE);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = df.parse(df.format(new Date()));
		// 设置时间
		so.setCreateDate(nowDate);
		so.setModifyDate(nowDate);
		// 设置del_flg和版本号
		so.setDelFlg(Constants.DELFLG_NORMAL);
		so.setSalesOrderVersion(OrderConstants.ORDER_DEFAULT_VERSION);
		// 设置创建人
		so.setCreateUserId(vo.getCreateUserId());
		so.setUpdateUserId(vo.getCreateUserId());
		// 由城市ID查询三级区域信息
		if (Utility.isNotEmpty(vo.getCityId())) {
			so.setThirdOrgId(vo.getCityId());
			AjaxInfo orgInfo = feignOrgService.queryById(vo.getCityId().longValue());
			if (Utility.isNotEmpty(orgInfo.getData())) {
				Map<String, Object> orgMap = (Map<String, Object>) orgInfo.getData();
				if (orgMap.containsKey("parentorgId")) {
					so.setSecondOrgId(Utility.stringToInteger(Utility.objectToString(orgMap.get("parentorgId"))));
					orgInfo = feignOrgService.queryById(Long.parseLong(Utility.objectToString(orgMap.get("parentorgId"))));
					if (Utility.isNotEmpty(orgInfo.getData())) {
						orgMap = (Map<String, Object>) orgInfo.getData();
						if (orgMap.containsKey("parentorgId")) {
							so.setFirstOrgId(Utility.stringToInteger(Utility.objectToString(orgMap.get("parentorgId"))));
						}
					}
				}

			}
		}
		// 获取订单号
		String storeNumber = vo.getStoreNumber();
		if (Utility.isNotEmpty(storeNumber)) {
			String number = OrderNumberExportService.getOrderNumber(OrderConstants.ORDER_TYPE_SALES, storeNumber);
			if (Utility.isNotEmpty(number)) {
				so.setSalesOrderNumber(number);
				// 处理商品的订单的关联信息
				List<OrderGoodsVO> ogList = new ArrayList<OrderGoodsVO>();
				for (FeignGoodsVO fg : vo.getGoodsList()) {
					OrderGoodsVO og = new OrderGoodsVO();
					// 商品ID
					og.setGoodsId(fg.getGoodsId().intValue());
					// SN码
					if (Utility.isNotEmpty(fg.getGoodsSN())) {
						og.setGoodsSn(fg.getGoodsSN());
					}
					// 车架号
					if (Utility.isNotEmpty(fg.getGoodsFramecode())) {
						og.setGoodsFrameNumber(fg.getGoodsFramecode());
					}
					// 下单数量:为空则默认为1
					if (Utility.isNotEmpty(fg.getOrderAmount())) {
						og.setOrderAmount(fg.getOrderAmount());
					} else {
						og.setOrderAmount(1);
					}
					// 实销单价
					og.setActualPrice(new BigDecimal(fg.getActualPrice()));
					// 零售指导价
					Map<String, Object> priceMap = new HashMap<String, Object>();
					priceMap.put("goodsId", fg.getGoodsId());
					priceMap.put("orgId", vo.getCityId());
					priceMap.put("partnerId", vo.getPartnerId());
					AjaxInfo priceInfo = feignGoodsPriceService.queryGoodsNowPrice(priceMap);
					if (Utility.isNotEmpty(priceInfo.getData())) {
						Map<String, Object> priceResultMap = (Map<String, Object>) priceInfo.getData();
						if (priceResultMap.containsKey("retailPrice")) {
							String retailPrice = Utility.objectToString(priceResultMap.get("retailPrice"));
							if (Utility.isNotEmpty(retailPrice)) {
								og.setRetailPrice(new BigDecimal(retailPrice));
							}
						}
					} else {
						og.setRetailPrice(new BigDecimal(0));
					}
					// 基础字段
					og.setDelFlg(Constants.DELFLG_NORMAL);
					og.setCreateUserId(vo.getCreateUserId());
					og.setUpdateUserId(vo.getCreateUserId());
					og.setCreateDate(nowDate);
					og.setModifyDate(nowDate);
					ogList.add(og);
				}
				// 事务控制
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						try {
							ExecuteResult<SalesOrderVO> addResult = add(so);
							if (Constants.SUCCESS_CODE.equals(addResult.getCode())) {
								label: for (OrderGoodsVO ogv : ogList) {
									// 校验有序商品是否未下单
									if (Utility.isNotEmpty(ogv.getGoodsSn())) {
										OrderGoodsVO orderGoodsVO = new OrderGoodsVO();
										orderGoodsVO.setGoodsSn(ogv.getGoodsSn());
										List<OrderGoodsVO> goodsRepeat = orderGoodsService.queryList(orderGoodsVO)
												.getResult();
										// 遍历查询出来的Order-Goods:订单状态为为已提交\未支付\已完成则无法再次下单
										if (Utility.isNotEmpty(goodsRepeat)) {
											for (OrderGoodsVO rp : goodsRepeat) {
												if (Utility.isNotEmpty(rp.getOrderId())) {
													SalesOrderVO rpSalesOrder = queryById(rp.getOrderId().longValue())
															.getResult();
													// 判断订单状态
													if (Utility.isNotEmpty(rpSalesOrder)) {
														if (OrderConstants.ORDER_STATUS_FINISH
																.equals(rpSalesOrder.getSalesOrderStatus())
																|| OrderConstants.ORDER_STATUS_NOPAY
																		.equals(rpSalesOrder.getSalesOrderStatus())
																|| OrderConstants.ORDER_STATUS_SUBMISSION
																		.equals(rpSalesOrder.getSalesOrderStatus())) {
															result.setCode(Constants.ERROR_CODE);
															result.setResultMessage("该商品已下单");
															result.setResult(ogv.getGoodsSn());
															// 事务回滚
															status.setRollbackOnly();
															break label;
														}
													}
												}
											}
										}
									}
									// 订单ID
									ogv.setOrderId(addResult.getResult().getSalesOrderId());
									// insert
									ExecuteResult<OrderGoodsVO> ogAddResult = orderGoodsService.add(ogv);
									if (Constants.ERROR_CODE.equals(ogAddResult.getCode())) {
										result.setCode(ogAddResult.getCode());
										result.setResultMessage(ogAddResult.getResultMessage());
										// 事务回滚
										status.setRollbackOnly();
										break;
									}
									// 返回订单号
									result.setResult(number);
									result.setCode(Constants.SUCCESS_CODE);
									result.setResultMessage(Constants.SAVE_SUCCESS);
								}
								// 注销优惠码
								if (Utility.isNotEmpty(so.getCouponId())) {
									Map<String, Object> couponMap = new HashMap<String, Object>();
									couponMap.put("tCouponId", so.getCouponId());
									couponMap.put("isWriteOff", Constants.DELFLG_DELETE);
									AjaxInfo couponInfo = feignCouponService.destroy(couponMap);
									if (AjaxInfoConstants.ERROR_CODE.equals(couponInfo.getCode())) {
										result.setCode(Constants.ERROR_CODE);
										result.setResultMessage("优惠码注销失败");
										result.setResult(null);
										// 事务回滚
										status.setRollbackOnly();
									}
								}
                                // 判断高伟的活动码是否已经核销
                                if (Utility.isNotEmpty(vo.getCouponNumber())) {
                                    // 判断高伟的活动码是否已经核销
                                    AjaxInfo gwInfo = feignActivityCouponService.verifyCode(vo.getCouponNumber());
                                    Map<String, Object> verifyCodeMap = (Map<String, Object>) gwInfo.getData();
                                    if (verifyCodeMap.containsKey("status")) {
                                        Integer status1 = (Integer) verifyCodeMap.get("status");
                                        // 本次活动的优惠码数据
                                        if (status1.equals(Constants.VERIFY_CODE_200)) {
                                            AjaxInfo ajaxInfo = feignActivityCouponService.applyCode(vo.getCouponNumber());
                                        }
                                    }
                                }

							} else {
								result.setCode(addResult.getCode());
								result.setResultMessage(addResult.getResultMessage());
								// 事务回滚
								status.setRollbackOnly();
							}
						} catch (Exception e) {
							result.setCode(Constants.ERROR_CODE);
							result.setResultMessage(e.toString());
							result.setResult(null);
							Log.error(log, "\n 方法[{}]，异常：[{}]", "SalesOrderExportServiceImpl-submitSalesOrder",
									e.getMessage());
							// 事务回滚
							status.setRollbackOnly();
						}
					}
				});

			} else {
				result.setCode(Constants.ERROR_CODE);
				result.setResultMessage("订单编码获取失败");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} else {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("门店编码为空");
		}

		Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-submitSalesOrder", result);
		return result;
	}

	/**
	 * @Description 校验必须字段是否为空
	 * @author xupengfei
	 * @Data 2019年4月8日
	 * @param vo
	 * @return
	 */
	public String validateSalesOrderNotEmpty(SalesOrderViewVO vo) {

		if (Utility.isEmpty(vo.getCustomerName())) {
			return "消费者姓名为空";
		} else if (Utility.isEmpty(vo.getCustomerPhoneNumber())) {
			return "消费者手机号为空";
		} else if (Utility.isEmpty(vo.getCustomerSource())) {
			return "消费者来源为空";
		} else if (Utility.isEmpty(vo.getStoreId())) {
			return "门店Id为空";
		} else if (Utility.isEmpty(vo.getStoreNumber())) {
			return "门店编码为空";
		} else if (Utility.isEmpty(vo.getGoodsList())) {
			return "商品信息为空";
		} else if (Utility.isEmpty(vo.getCityId())) {
			return "城市Id为空";
		} else if (Utility.isEmpty(vo.getPartnerId())) {
			return "合伙人Id为空";
		} else if (Utility.isEmpty(vo.getCreateUserId())) {
			return "销售人员Id为空";
		} else if (Utility.isEmpty(vo.getTransactionId())) {
			return "订单流水号为空";
		} else {
			return Constants.SUCCESS_CODE;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public SalesOrderDetailVO handleSalesOrderDetail(SalesOrderVO vo) {
		SalesOrderDetailVO result = new SalesOrderDetailVO();
		// 订单基础信息
		// 订单id
		if (Utility.isNotEmpty(vo.getSalesOrderId())) {
			result.setSalesOrderId(vo.getSalesOrderId());
			result.setActivityPicture(vo.getActivityPicture());
		}
		// 订单状态
		if (Utility.isNotEmpty(vo.getSalesOrderStatus())) {
			result.setSalesOrderStatus(dictValueExportService
					.getDictValueNameById(OrderConstants.ORDER_DICT_ORDER_STATUS, vo.getSalesOrderStatus()));
		}
		// 已退货订单状态改为已退货则补充退单编号,并展示为已完成
		if (OrderConstants.ORDER_STATUS_RETURN.equals(vo.getSalesOrderStatus())) {
			result.setSalesOrderStatus(OrderConstants.ORDER_STATUS_FINISH_VALUE);
			// TODO 一期整单退:展示第一条退货订单编号
			if (Utility.isNotEmpty(vo.getSalesOrderId())) {
				ReturnOrderVO returnOrderVO = new ReturnOrderVO();
				returnOrderVO.setSalesOrderId(vo.getSalesOrderId());
				ExecuteResult<List<ReturnOrderVO>> returnResult = returnOrderService.queryList(returnOrderVO);
				if (CheckUtil.checkResultListIsNotNull(returnResult)) {
					ReturnOrderVO ro = returnResult.getResult().get(0);
					result.setReturnOrderNumber(ro.getReturnOrderNumber());
				}
			}

		}
		// 订单编码
		result.setSalesOrderNumber(vo.getSalesOrderNumber());
		// 订单金额
		// 金额保留两位小数
		DecimalFormat format = new DecimalFormat("0.00");
		result.setSalesOrderAmount(format.format(vo.getSalesOrderAmount()));
		// 订单来源
		if (Utility.isNotEmpty(vo.getSalesOrderSource())) {
			result.setSalesOrderSource(dictValueExportService
					.getDictValueNameById(OrderConstants.ORDER_DICT_ORDER_SOURCE, vo.getSalesOrderSource()));
		}
		// 支付方式
		if (Utility.isNotEmpty(vo.getPaymentWay())) {
			result.setPaymentWay(
					dictValueExportService.getDictValueNameById(OrderConstants.ORDER_DICT_PAYMENT, vo.getPaymentWay()));
		}
		// 取消原因
		if (Utility.isNotEmpty(vo.getCancelReason())) {
			result.setCancelReason(dictValueExportService.getDictValueNameById(OrderConstants.ORDER_DICT_NOT_BUY_REASON,
					vo.getCancelReason()));
		}
		// 门店信息
		if (Utility.isNotEmpty(vo.getStoreId())) {
			AjaxInfo storeInfo = feignStoreService.getStoreById(vo.getStoreId().longValue());
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
		if (Utility.isNotEmpty(vo.getCustomerId())) {
			Map<String, Object> customerMap = new HashMap<String, Object>();
			customerMap.put("customerId", vo.getCustomerId());
			AjaxInfo customerInfo = feignCustomerService.queryCustomerList(customerMap);
			if (Utility.isNotEmpty(customerInfo.getData())) {
				Map<String, Object> customerResultMap = ((List<Map<String, Object>>) customerInfo.getData()).get(0);
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
					Integer customerSourceId = Utility
							.stringToInteger(Utility.objectToString(customerResultMap.get("customerSource")));
					if (Utility.isNotEmpty(customerSourceId)) {
						result.setCustomerSource(dictValueExportService
								.getDictValueNameById(OrderConstants.ORDER_DICT_CUSTOMER_SOURCE, customerSourceId));
					}
				}
			}
		}
		// 活动名称
		if (Utility.isNotEmpty(vo.getActivityId())) {
			AjaxInfo activityInfo = feignActivityService.queryById(vo.getActivityId());
			if (Utility.isNotEmpty(activityInfo.getData())) {
				Map<String, Object> activityMap = (Map<String, Object>) activityInfo.getData();
				if (activityMap.containsKey("activityName")) {
					result.setActivityName(Utility.objectToString(activityMap.get("activityName")));
				}
			}
		}
		// 活动码
		if (Utility.isNotEmpty(vo.getCouponId())) {
			AjaxInfo couponInfo = feignCouponService.queryById(vo.getCouponId());
			if (Utility.isNotEmpty(couponInfo.getData())) {
				Map<String, Object> couponMap = (Map<String, Object>) couponInfo.getData();
				if (couponMap.containsKey("couponCode")) {
					result.setCouponNumber(Utility.objectToString(couponMap.get("couponCode")));
				}
			}
		}
		// 商品信息
		OrderGoodsVO og = new OrderGoodsVO();
		og.setOrderId(vo.getSalesOrderId());
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
		result.setCreateDate(vo.getModifyDate());
		// 操作人
		if (Utility.isNotEmpty(vo.getCreateUserId())) {
			AjaxInfo userInfo = feignUserService.queryByUser(vo.getCreateUserId().toString());
			if (Utility.isNotEmpty(userInfo.getData())) {
				Map<String, Object> userMap = (Map<String, Object>) userInfo.getData();
				if (userMap.containsKey("realName")) {
					if (Utility.isNotEmpty(userMap.get("realName"))) {
						result.setCreateUserName(Utility.objectToString(userMap.get("realName")));
					}
				}

			}
		}

		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ExecuteResult<SalesOrderVO> cancelSalesOrder(SalesOrderVO vo) throws Exception {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderExportServiceImpl-cancelSalesOrder",
				JSONObject.toJSONString(vo));
		ExecuteResult<SalesOrderVO> result = new ExecuteResult<SalesOrderVO>();
		// 必有参数非空校验
		if (Utility.isEmpty(vo.getSalesOrderNumber())) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("订单编号为空");
		} else if (Utility.isEmpty(vo.getUpdateUserId())) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("操作人为空");
		} else if (Utility.isEmpty(vo.getCancelReason())) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("取消原因为空");
		} else {
			// 获取订单信息
			SalesOrderVO so = new SalesOrderVO();
			so.setSalesOrderNumber(vo.getSalesOrderNumber());
			List<SalesOrderVO> soList = queryList(so).getResult();
			if (Utility.isNotEmpty(soList)) {
				SalesOrderVO salesOrder = soList.get(0);
				// 校验订单状态:仅已提交/未支付可以取消
				if (!OrderConstants.ORDER_STATUS_SUBMISSION.equals(salesOrder.getSalesOrderStatus())
						&& !OrderConstants.ORDER_STATUS_NOPAY.equals(salesOrder.getSalesOrderStatus())) {
					if (OrderConstants.ORDER_STATUS_CANCLE.equals(salesOrder.getSalesOrderStatus())) {
						result.setCode(Constants.ERROR_CODE);
						result.setResultMessage("该订单已取消");
					} else if (OrderConstants.ORDER_STATUS_FINISH.equals(salesOrder.getSalesOrderStatus())) {
						result.setCode(Constants.ERROR_CODE);
						result.setResultMessage("该订单已付款完成,无法取消");
					} else if (OrderConstants.ORDER_STATUS_RETURN.equals(salesOrder.getSalesOrderStatus())) {
						result.setCode(Constants.ERROR_CODE);
						result.setResultMessage("该订单已退单,无法取消");
					}
					Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-cancelSalesOrder", result);
					return result;
				}
				// 订单版本号
				Integer version = salesOrder.getSalesOrderVersion();
				// 修改销售订单状态(带版本号)
				vo.setSalesOrderStatus(OrderConstants.ORDER_STATUS_CANCLE);
				vo.setSalesOrderVersion(version);
				vo.setSalesOrderId(salesOrder.getSalesOrderId());
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date nowDate = df.parse(df.format(new Date()));
				vo.setModifyDate(nowDate);
				result = update(vo);
				if (Constants.SUCCESS_CODE.equals(result.getCode()) && Utility.isNotEmpty(salesOrder.getCouponId())) {
					if (Utility.isNotEmpty(salesOrder.getCouponId())) {
						Map<String, Object> couponMap = new HashMap<String, Object>();
						couponMap.put("tCouponId", salesOrder.getCouponId());
						couponMap.put("isWriteOff", Constants.DELFLG_NORMAL);
						AjaxInfo couponInfo = feignCouponService.destroy(couponMap);
						if (AjaxInfoConstants.ERROR_CODE.equals(couponInfo.getCode())) {
							result.setCode(Constants.ERROR_CODE);
							result.setResultMessage("优惠码启用失败");
							result.setResult(null);
							// 回滚事务
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						}
					}
				}

			} else {
				result.setCode(Constants.ERROR_CODE);
				result.setResultMessage("销售订单不存在");
			}
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-cancelSalesOrder", result);
		return result;
	}

	@Override
	public List<SalesOrderMobileVO> handleMobileSalesOrderList(List<SalesOrderVO> list) {
		List<SalesOrderMobileVO> result = new ArrayList<SalesOrderMobileVO>();
		if (Utility.isNotEmpty(list)) {
			for (SalesOrderVO so : list) {
				SalesOrderMobileVO som = new SalesOrderMobileVO();
				// 订单ID
				som.setSalesOrderId(so.getSalesOrderId());
				// 订单号
				som.setSalesOrderNumber(so.getSalesOrderNumber());
				// 销售时间
				som.setModifyDate(so.getModifyDate());
				// 退货订单为退货标识赋值
				if (OrderConstants.ORDER_STATUS_RETURN.equals(so.getSalesOrderStatus())) {
					som.setReturnFlg(Constants.DELFLG_DELETE);
				} else {
					som.setReturnFlg(Constants.DELFLG_NORMAL);
				}
				// 取消原因
				if (Utility.isNotEmpty(so.getCancelReason())) {
					som.setCancelReason(dictValueExportService
							.getDictValueNameById(OrderConstants.ORDER_DICT_NOT_BUY_REASON, so.getCancelReason()));
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
							som.setCustomerName(Utility.objectToString(customerResultMap.get("customerName")));
						}
						// 消费者手机号
						if (customerResultMap.containsKey("customerPhoneNumber")) {
							som.setCustomerPhoneNumber(
									Utility.objectToString(customerResultMap.get("customerPhoneNumber")));
						}
						// 消费者来源
						if (customerResultMap.containsKey("customerSource")) {
							Integer customerSourceId = Utility
									.stringToInteger(Utility.objectToString(customerResultMap.get("customerSource")));
							if (Utility.isNotEmpty(customerSourceId)) {
								som.setCustomerSource(dictValueExportService.getDictValueNameById(
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
					som.setGoodsList(goodsList);
				}
				result.add(som);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SalesOrderVO handleMobileVOBeforeQuery(SalesOrderVO vo) {

		// 已完成的订单查询:已完成和已退货两种状态
		if (Utility.isNotEmpty(vo.getSalesOrderStatus())
				&& OrderConstants.ORDER_STATUS_FINISH.equals(vo.getSalesOrderStatus())) {
			vo.setSalesOrderStatus(null);
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(OrderConstants.ORDER_STATUS_FINISH);
			statusList.add(OrderConstants.ORDER_STATUS_RETURN);
			vo.setStatusList(statusList);
		}
		// 是否需要调用Order-Goods
		boolean isCode = false;
		// 是否需要调用
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
		// 涉及相关商品的条件从Order-Goods里获取订单ID集合
		if (isCode) {
			List<Integer> idList = new ArrayList<Integer>();
			List<OrderGoodsVO> ogList = orderGoodsService.queryList(og).getResult();
			if (Utility.isNotEmpty(ogList)) {
				for (OrderGoodsVO ogv : ogList) {
					idList.add(ogv.getOrderId());
				}
			} else {
				idList.add(-1);
			}
			vo.setSalesOrderIdList(idList);
		}

		Map<String, Object> customerMap = new HashMap<String, Object>();
		if (Utility.isNotEmpty(vo.getCustomerName())) {
			customerMap.put("customerName", vo.getCustomerName());
			isCustomer = true;
		}

		if (Utility.isNotEmpty(vo.getCustomerPhoneNumber())) {
			customerMap.put("customerPhoneNumber", vo.getCustomerPhoneNumber());
			isCustomer = true;
		}

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
			} else {
				customerIdList.add(-1);
			}
			vo.setCustomerIdList(customerIdList);
		}

		return vo;
	}

	@Override
	public ExecuteResult<List<OrderCustomerVO>> statisticOrderWithCustomer(String ids) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderExportServiceImpl-statisticOrderWithCustomer",
				JSONObject.toJSONString(ids));
		ExecuteResult<List<OrderCustomerVO>> result = new ExecuteResult<List<OrderCustomerVO>>();
		// 字符串转数组
		if (Utility.isEmpty(ids)) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("门店ID集合为空");
			Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-statisticOrderWithCustomer", result);
			return result;
		}
		try {
			// List类型转换
			String[] array = ids.split(",");
			List<String> list = Arrays.asList(array);
			List<Integer> idList = new ArrayList<Integer>();
			CollectionUtils.collect(list, new Transformer() {
				@Override
				public Object transform(Object o) {
					return Integer.valueOf(o.toString());
				}
			}, idList);
			if (Utility.isNotEmpty(idList)) {
				// 根据门店ID查询订单数据
				SalesOrderVO salesOrderVO = new SalesOrderVO();
				salesOrderVO.setStoreIdList(idList);
				List<SalesOrderVO> soList = salesOrderService.queryOrderByStoreIds(salesOrderVO).getResult();
				List<OrderCustomerVO> ocList = new ArrayList<OrderCustomerVO>();
				if (Utility.isNotEmpty(soList)) {
					for (SalesOrderVO so : soList) {
						OrderCustomerVO vo = new OrderCustomerVO();
						if(Utility.isNotEmpty(so.getCustomerId())){
							//消费者ID
							vo.setCustomerId(so.getCustomerId());
							// 订单状态已完成
							vo.setSalesOrderStatus(OrderConstants.ORDER_STATUS_FINISH);
							//权限:store的ID集合
							vo.setStoreIdList(idList);
							ExecuteResult<OrderCustomerVO> queryResult = salesOrderService.statisticOrderWithCustomer(vo);
							if (Utility.isNotEmpty(queryResult.getResult())) {
								ocList.add(queryResult.getResult());
							}else{
								//查询结果为空表示对应消费者的订单数和金额为0
								OrderCustomerVO oc = new OrderCustomerVO();
								oc.setNumberTotal(0);
								oc.setPriceTotal(new BigDecimal(0));
								oc.setCustomerId(so.getCustomerId());
								ocList.add(oc);
							}
						}
					}
				}
				//根据门店id集合查询未购买上报数据
				NotBuyVO notBuyVO = new NotBuyVO();
				notBuyVO.setStoreIdList(idList);
				List<NotBuyVO> nbList = notBuyService.queryList(notBuyVO).getResult();
				if(Utility.isNotEmpty(nbList)){
					//未购买上报的订单数和金额为0
					OrderCustomerVO oc = new OrderCustomerVO();
					oc.setNumberTotal(0);
					oc.setPriceTotal(new BigDecimal(0));
					for(NotBuyVO nb : nbList){
						if(Utility.isNotEmpty(nb.getCustomerId())){
							oc.setCustomerId(nb.getCustomerId());
							ocList.add(oc);
						}
					}
				}
				if(Utility.isNotEmpty(ocList)){
					//去重
					List<OrderCustomerVO> rsList = ocList.stream().collect(
			                Collectors.collectingAndThen(
			                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(OrderCustomerVO::getCustomerId))), ArrayList::new)
			        );
					if(Utility.isNotEmpty(rsList)){
						result.setCode(Constants.SUCCESS_CODE);
						result.setResultMessage(Constants.QUERY_SUCCESS);
						result.setResult(rsList);
					}else{
						result.setCode(Constants.EMPTY_CODE);
						result.setResultMessage(Constants.QUERY_SUCCESS);
					}
				}else{
					result.setCode(Constants.EMPTY_CODE);
					result.setResultMessage(Constants.QUERY_FAILURE);
				}
			} else {
				result.setCode(Constants.ERROR_CODE);
				result.setResultMessage(Constants.QUERY_FAILURE);
			}
		} catch (Exception e) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.toString());
			Log.error(log, "\n 方法[{}]，异常：[{}]", "SalesOrderExportServiceImpl-statisticOrderWithCustomer",
					e.getMessage());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "SalesOrderExportServiceImpl-statisticOrderWithCustomer", result);
		return result;
	}

	@Override
	public SalesOrderVO handleDataPermission(SalesOrderVO vo) {

		List<Integer> storeSearchIdList = new ArrayList<Integer>();
		List<Integer> storeIdList = new ArrayList<Integer>();
		// 如果查询条件包含门店则使用该门店ID集合做查询条件
		if (Utility.isNotEmpty(vo.getSearchStoreStr())) {
			String[] searchStoreArray = vo.getSearchStoreStr().split(",");
			List<String> searchStoreList = Arrays.asList(searchStoreArray);
			CollectionUtils.collect(searchStoreList, new Transformer() {
				@Override
				public Object transform(Object o) {
					return Integer.valueOf(o.toString());
				}
			}, storeSearchIdList);
		}

		// 内部用户:根据区域选取数据
		if (Utility.isNotEmpty(vo.getOrgStr())) {
			String[] orgArray = vo.getOrgStr().split(",");
			List<String> orgList = Arrays.asList(orgArray);
			List<Integer> orgIdList = new ArrayList<Integer>();
			CollectionUtils.collect(orgList, new Transformer() {
				@Override
				public Object transform(Object o) {
					return Integer.valueOf(o.toString());
				}
			}, orgIdList);
			if (Utility.isNotEmpty(orgIdList)) {
				vo.setOrgIdList(orgIdList);
			}

		}
		// 内部用户:根据门店选取数据
		if (Utility.isNotEmpty(vo.getStoreStr())) {
			String[] storeArray = vo.getStoreStr().split(",");
			List<String> storeList = Arrays.asList(storeArray);
			CollectionUtils.collect(storeList, new Transformer() {
				@Override
				public Object transform(Object o) {
					return Integer.valueOf(o.toString());
				}
			}, storeIdList);
		}
		// 如果查询条件有门店信息则使用该ID集合,否则使用权限门店集合
		if (Utility.isNotEmpty(storeSearchIdList)) {
			vo.setStoreIdList(storeSearchIdList);
		} else {
			if (Utility.isNotEmpty(storeIdList)) {
				vo.setStoreIdList(storeIdList);
			}
		}
		return vo;
	}

}

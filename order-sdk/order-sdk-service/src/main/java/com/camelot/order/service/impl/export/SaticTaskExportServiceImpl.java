//package com.camelot.order.service.impl.export;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.TransactionCallbackWithoutResult;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import com.alibaba.fastjson.JSONObject;
//import com.camelot.common.bean.AjaxInfo;
//import com.camelot.common.bean.ExecuteResult;
//import com.camelot.order.common.Constants;
//import com.camelot.order.common.OrderConstants;
//import com.camelot.order.common.utils.BeanUtil;
//import com.camelot.order.common.utils.Log;
//import com.camelot.order.common.utils.Utility;
//import com.camelot.order.export.service.DictValueExportService;
//import com.camelot.order.export.service.SaticTaskExportService;
//import com.camelot.order.export.vo.OrderGoodsVO;
//import com.camelot.order.export.vo.ReturnOrderVO;
//import com.camelot.order.export.vo.SalesOrderVO;
//import com.camelot.order.export.vo.StatisticsReturnOrderVO;
//import com.camelot.order.export.vo.StatisticsTotalVO;
//import com.camelot.order.export.vo.StoreVO;
//import com.camelot.order.feign.FeignCustomerService;
//import com.camelot.order.feign.FeignOrgService;
//import com.camelot.order.feign.config.FeignGoodsService;
//import com.camelot.order.feign.config.FeignStoreService;
//import com.camelot.order.service.OrderGoodsService;
//import com.camelot.order.service.ReturnOrderService;
//import com.camelot.order.service.SalesOrderService;
//import com.camelot.order.service.StatisticsReturnOrderService;
//import com.camelot.order.service.StatisticsTotalService;
//import com.github.pagehelper.PageInfo;
//
//@Service
//@Component
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
//public class SaticTaskExportServiceImpl implements SaticTaskExportService {
//	
//	@Autowired
//	private SalesOrderService salesOrderService;
//	@Autowired
//	private StatisticsTotalService statisticsTotalService;
//	@Autowired
//	private FeignStoreService feignStoreService;
//	@Autowired
//	private FeignOrgService feignOrgService;
//	@Autowired
//	private FeignCustomerService feignCustomerService;
//	@Autowired
//	private DictValueExportService dictValueExportService;
//	@Autowired
//	private OrderGoodsService orderGoodsService;
//	@Autowired
//	private FeignGoodsService feignGoodsService;
//	@Autowired
//	private ReturnOrderService returnOrderService;
//	@Autowired
//	private StatisticsReturnOrderService statisticsReturnOrderService;
//	@Autowired
//	private TransactionTemplate transactionTemplate;
//	
//	private static Logger log = Log.get(SaticTaskExportServiceImpl.class);
//
//	 //3.添加定时任务
//    @Scheduled(cron = OrderConstants.STATIC_TASK)
//	public synchronized ExecuteResult<String> statisticsStaticTask() {
//		ExecuteResult<String> result = new ExecuteResult<>();
//		try {
//			// 获取当前统计表，订单修改时间的最大值
//			ExecuteResult<String> maxDateExecuteResult = statisticsTotalService.queryMaxDate();
//			//设置日期格式
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			// 订单状态
//			List<Integer> orderStatusList = new ArrayList<>();
//			// 已完成
//			orderStatusList.add(OrderConstants.ORDER_STATUS_FINISH);
//			// 已退货
//			orderStatusList.add(OrderConstants.ORDER_STATUS_RETURN);
//			// 获取当前系统时间
//			long currentTimeMillis = System.currentTimeMillis();
//			// 最近的统计时间
//			String createDate = maxDateExecuteResult.getResult();
//			// 当前时间
//			String endDate = df.format(new Date(currentTimeMillis));
//			// 获取当前系统时间查询订单数据
//			ExecuteResult<List<SalesOrderVO>> queryResult = salesOrderService.queryListByDate(createDate, endDate, orderStatusList);
//			if(Utility.isNotEmpty(queryResult.getResult())) {
//				//事务控制
//				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//					protected void doInTransactionWithoutResult(TransactionStatus status) {
//						// 向统计汇总表插入数据
//						try {
//							List<SalesOrderVO> SalesOrderList = queryResult.getResult();
//							for(SalesOrderVO salesOrderVO : SalesOrderList) {
//								StatisticsTotalVO statisticsTotalVO = new StatisticsTotalVO();
//								// 订单id
//								statisticsTotalVO.setSalesOrderId(salesOrderVO.getSalesOrderId());
//								// 门店id
//								statisticsTotalVO.setStoreId(salesOrderVO.getStoreId());
//								// 订单创建时间
//								statisticsTotalVO.setOrderCreateDate(salesOrderVO.getCreateDate());
//								// 大区id
//								statisticsTotalVO.setFirstOrgId(salesOrderVO.getFirstOrgId());
//								// 区域id
//								statisticsTotalVO.setSecondOrgId(salesOrderVO.getSecondOrgId());
//								// 城市id
//								statisticsTotalVO.setThirdOrgId(salesOrderVO.getThirdOrgId());
//								// 订单创建人id
//								statisticsTotalVO.setCreateUserId(salesOrderVO.getCreateUserId());
//								// 大区名称
//								if (Utility.isNotEmpty(salesOrderVO.getFirstOrgId())) {
//									statisticsTotalVO.setFirstOrgName(getOrgNameById(salesOrderVO.getFirstOrgId().longValue()));
//								}
//								// 区域名称
//								if (Utility.isNotEmpty(salesOrderVO.getSecondOrgId())) {
//									statisticsTotalVO.setSecondOrgName(getOrgNameById(salesOrderVO.getSecondOrgId().longValue()));
//								}
//								// 城市名称
//								if (Utility.isNotEmpty(salesOrderVO.getThirdOrgId())) {
//									statisticsTotalVO.setThirdOrgName(getOrgNameById(salesOrderVO.getThirdOrgId().longValue()));
//								}
//								// 门店名称/所属加盟商/所属合伙人
//								if (Utility.isNotEmpty(salesOrderVO.getStoreId())) {
//									// 根据门店id集合获取基础服务返回的StoreVO对象
//									AjaxInfo storeAjax = feignStoreService.queryStoreList(salesOrderVO.getStoreId().toString());
//									if(Utility.isNotEmpty(storeAjax.getData())) {
//										String jsonStr = JSONObject.toJSONString(storeAjax.getData());
//										List<StoreVO> storeList = JSONObject.parseArray(jsonStr,StoreVO.class);
//										if(Utility.isNotEmpty(storeList)) {
//											StoreVO store = storeList.get(0);
//											// 门店名称
//											statisticsTotalVO.setStoreName(store.getStoreName());
//											if(Utility.isNotEmpty(store.getPartnerTraderVO().getPartnerTraderVO())) {
//												// 合伙人id
//												statisticsTotalVO.setPartnerId(store.getPartnerTraderVO().getPartnerTraderVO().getPartnerId());
//												// 合伙人名称
//												statisticsTotalVO.setPartnerName(store.getPartnerTraderVO().getPartnerTraderVO().getPartnerName());
//												// 加盟商id
//												statisticsTotalVO.setFranchiseeId(store.getPartnerTraderVO().getPartnerId());
//												// 加盟商名称
//												statisticsTotalVO.setFranchiseeName(store.getPartnerTraderVO().getPartnerName());
//											} else {
//												// 合伙人名称
//												statisticsTotalVO.setPartnerName(store.getPartnerTraderVO().getPartnerName());
//											}
//										}
//									}
//								}
//								// 消费者来源
//								if(Utility.isNotEmpty(salesOrderVO.getCustomerId())){
//									Map<String,Object> customerMap = new HashMap<String,Object>();
//									customerMap.put("customerId",salesOrderVO.getCustomerId());
//									AjaxInfo customerInfo = feignCustomerService.queryCustomerList(customerMap);
//									if(Utility.isNotEmpty(customerInfo.getData())){
//										Map<String,Object> customerResultMap = ((List<Map<String,Object>>)customerInfo.getData()).get(0);
//										if(customerResultMap.containsKey("customerSource")){
//											Integer customerSourceId = Integer.parseInt(customerResultMap.get("customerSource").toString());
//											if(Utility.isNotEmpty(customerSourceId)){
//												// 消费来源id
//												statisticsTotalVO.setCustomerSourceId(customerSourceId);
//												// 消费者来源
//												statisticsTotalVO.setCustomerSource(dictValueExportService.getDictValueNameById(OrderConstants.ORDER_DICT_CUSTOMER_SOURCE, customerSourceId));
//											}
//										}
//									}
//								}
//								// 订单商品信息
//								OrderGoodsVO orderGoodsVO = new OrderGoodsVO();
//								orderGoodsVO.setOrderId(salesOrderVO.getSalesOrderId());
//								// 获取订单商品数据
//								ExecuteResult<List<OrderGoodsVO>> orderGoodsResult = orderGoodsService.queryList(orderGoodsVO);
//								if(Utility.isNotEmpty(orderGoodsResult.getResult())) {
//									List<OrderGoodsVO> orderGoodsList = orderGoodsResult.getResult();
//									for(OrderGoodsVO orderGoods : orderGoodsList) {
//										StatisticsTotalVO statisticsTotal = BeanUtil.copyPropertes(StatisticsTotalVO.class, statisticsTotalVO);
//										// 实销单价
//										statisticsTotal.setActualPrice(orderGoods.getActualPrice());
//										// 指导零售单价
//										statisticsTotal.setRetailPrice(orderGoods.getRetailPrice());
//										// 下单数量
//										statisticsTotal.setOrderAmount(orderGoods.getOrderAmount());
//										// 实销总额
//										statisticsTotal.setTotalPrice(orderGoods.getActualPrice().multiply(BigDecimal.valueOf(orderGoods.getOrderAmount())));
//										HashMap<String, Object> orderGoodsMap = new HashMap<>();
//										orderGoodsMap.put("goodsId", orderGoods.getGoodsId());
//										// 调用商品服务获取商品信息
//										AjaxInfo goodsAjax = feignGoodsService.queryGoods(orderGoodsMap);
//										if(Utility.isNotEmpty(goodsAjax) && Utility.isNotEmpty(goodsAjax.getData())) {
//											PageInfo<Map<String, Object>> pageInfo = JSONObject.parseObject(JSONObject.toJSONString(goodsAjax.getData()), PageInfo.class);
//											Map<String, Object> GoodsCategoryMap = pageInfo.getList().get(0);
//											// 商品名称
//											statisticsTotal.setGoodsName(GoodsCategoryMap.get("goodsName").toString());
//											// 一级分类id
//											if(GoodsCategoryMap.containsKey("goodsFirstCategoryId")) {
//												statisticsTotal.setGoodsFirstCategoryId(Integer.parseInt(GoodsCategoryMap.get("goodsFirstCategoryId").toString()));
//											}
//											// 一级分类名称
//											if(GoodsCategoryMap.containsKey("goodsFirstCategoryName")) {
//												statisticsTotal.setGoodsFirstCategoryName(GoodsCategoryMap.get("goodsFirstCategoryName").toString());
//											}
//											// 二级分类id
//											if(GoodsCategoryMap.containsKey("goodsSecondCategoryId")) {
//												statisticsTotal.setGoodsSecondCategoryId(Integer.parseInt(GoodsCategoryMap.get("goodsSecondCategoryId").toString()));
//											}
//											// 二级分类名称
//											if(GoodsCategoryMap.containsKey("goodsSecondCategoryName")) {
//												statisticsTotal.setGoodsSecondCategoryName(GoodsCategoryMap.get("goodsSecondCategoryName").toString());
//											}
//											// 三级分类id
//											if(GoodsCategoryMap.containsKey("goodsThirdCategoryId")) {
//												statisticsTotal.setGoodsThirdCategoryId(Integer.parseInt(GoodsCategoryMap.get("goodsThirdCategoryId").toString()));
//											}
//											// 三级分类名称
//											if(GoodsCategoryMap.containsKey("goodsThirdCategoryName")) {
//												statisticsTotal.setGoodsThirdCategoryName(GoodsCategoryMap.get("goodsThirdCategoryName").toString());
//											}
//											// 属性字典id
//											if(GoodsCategoryMap.containsKey("goodsAttribute")) {
//												statisticsTotal.setGoodsAttributeId(Integer.parseInt(GoodsCategoryMap.get("goodsAttribute").toString()));
//											}
//											// 属性名称
//											if(Utility.isNotEmpty(statisticsTotal.getGoodsAttributeId())) {
//												statisticsTotal.setGoodsAttributeName(dictValueExportService.getDictValueNameById(OrderConstants.GOODS_DICT_GOODS_ATTRIBUTE, statisticsTotal.getGoodsAttributeId()));
//											}
//										}
//										Date nowDate = new Date();
//										//cuijiudong:统计表中检查 如果查询到统计表里面已经存在 本次统计的记录，那么更新本统计记录 --cjd
//										ExecuteResult<List<StatisticsTotalVO>> checkList = statisticsTotalService.queryList(statisticsTotal);
//										if(Utility.isNotEmpty(checkList.getResult())) {
//											statisticsTotal.setStatisticsId(checkList.getResult().get(0).getStatisticsId());
//											// 订单修改时间
//											statisticsTotal.setOrderUpdateDate(salesOrderVO.getModifyDate());
//											// 订单修改人id
//											statisticsTotal.setUpdateUserId(salesOrderVO.getUpdateUserId());
//											// 订单状态
//											statisticsTotal.setSalesOrderStatus(salesOrderVO.getSalesOrderStatus());
//											// 状态和修改时间相同则不进行操作
//											ExecuteResult<List<StatisticsTotalVO>> checkUpdateList = statisticsTotalService.queryList(statisticsTotal);
//											if(Utility.isEmpty(checkUpdateList.getResult())) {
//												// 修改时间
//												statisticsTotal.setModifyDate(nowDate);
//												// 修改统计汇总表数据
//												statisticsTotalService.update(statisticsTotal);
//											}
//										} else {
//											// cuijiudong:全新的统计记录 那么更新 TODO：是否考虑批量插入？ 
//											// 订单修改时间
//											statisticsTotal.setOrderUpdateDate(salesOrderVO.getModifyDate());
//											// 订单状态
//											statisticsTotal.setSalesOrderStatus(salesOrderVO.getSalesOrderStatus());
//											// 订单修改人id
//											statisticsTotal.setUpdateUserId(salesOrderVO.getUpdateUserId());
//											// 创建时间
//											statisticsTotal.setCreateDate(nowDate);
//											// 修改时间
//											statisticsTotal.setModifyDate(nowDate);
//											// 向统计汇总表插入数据
//											statisticsTotalService.add(statisticsTotal);
//										}
//									}
//								}
//							}
//							// 退货统计汇总表插入数据
//							StatisticsReturnOrderVO statisticsReturnOrderVO = new StatisticsReturnOrderVO();
//							// 获取前两个小时退货的订单数据
//							ExecuteResult<List<ReturnOrderVO>> returnOrderExecuteResult = returnOrderService.queryListByDate(createDate, endDate);
//							if(Utility.isNotEmpty(returnOrderExecuteResult.getResult())){
//								List<ReturnOrderVO> returnOrderList = returnOrderExecuteResult.getResult();
//								for(ReturnOrderVO returnOrder : returnOrderList) {
//									ExecuteResult<SalesOrderVO> queryVO = salesOrderService.queryById(returnOrder.getSalesOrderId().longValue());
//									SalesOrderVO salesOrderVO = queryVO.getResult();
//									if(Utility.isNotEmpty(salesOrderVO)) {
//										// 门店id
//										statisticsReturnOrderVO.setStoreId(salesOrderVO.getStoreId());
//										// 大区id
//										statisticsReturnOrderVO.setFirstOrgId(salesOrderVO.getFirstOrgId());
//										// 区域id
//										statisticsReturnOrderVO.setSecondOrgId(salesOrderVO.getSecondOrgId());
//										// 城市
//										statisticsReturnOrderVO.setThirdOrgId(salesOrderVO.getThirdOrgId());
//										// 订单创建人id
//										statisticsReturnOrderVO.setCreateUserId(salesOrderVO.getCreateUserId());
//									}
//									// 订单id
//									statisticsReturnOrderVO.setSalesOrderId(returnOrder.getSalesOrderId());
//									// 退货订单id
//									statisticsReturnOrderVO.setReturnOrderId(returnOrder.getReturnOrderId());
//									// 退单时间
//									statisticsReturnOrderVO.setReturnDate(returnOrder.getCreateDate());
//									// 退款金额
//									statisticsReturnOrderVO.setReturnOrderAmount(returnOrder.getReturnOrderAmount());
//									// 退货订单创建id
//									statisticsReturnOrderVO.setUpdateUserId(returnOrder.getCreateUserId());
//									
//									ExecuteResult<List<StatisticsReturnOrderVO>> chechList = statisticsReturnOrderService.queryList(statisticsReturnOrderVO);
//									if(Utility.isEmpty(chechList.getResult())) {
//										// 创建时间
//										statisticsReturnOrderVO.setCreateDate(new Date());
//										// 退货统计汇总表插入数据
//										statisticsReturnOrderService.add(statisticsReturnOrderVO);
//									}
//								}
//							}
//							result.setCode(Constants.SUCCESS_CODE);
//							result.setResultMessage(Constants.SAVE_SUCCESS);
//						} catch (Exception e) {
//							Log.debug(log, "\n 方法[{}]，异常：[{}]", "SaticTaskExportServiceImpl-addStatisticsTotal", e.toString());
//							result.setCode(Constants.ERROR_CODE);
//							result.setResultMessage(e.toString());
//							//事务回滚
//							status.setRollbackOnly();
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//		} catch (Exception e) {
//			Log.debug(log, "\n 方法[{}]，异常：[{}]", "SaticTaskExportServiceImpl-statisticsStaticTask", e.toString());
//			result.setCode(Constants.ERROR_CODE);
//			result.setResultMessage(e.toString());
//			e.printStackTrace();
//		}
//		Log.debug(log, "\n 方法[{}]，出参：[{}]", "SaticTaskExportServiceImpl-statisticsStaticTask", result.getResult());
//		return result;
//	}
//	
//	/**
//	 * @Description 根据区域id获取区域名称
//	 * @author xupengfei
//	 * @Data 2019年4月5日
//	 * @param id
//	 * @return
//	 */
//	public String getOrgNameById(Long id) {
//		String name = "";
//		AjaxInfo info = new AjaxInfo();
//		info = feignOrgService.queryById(id);
//		if (Utility.isNotEmpty(info.getData())) {
//			@SuppressWarnings("unchecked")
//			Map<String, Object> map = (Map) info.getData();
//			if (map.containsKey("orgName")) {
//				name = map.get("orgName").toString();
//			}
//		}
//		return name;
//	}
//
//}

package com.camelot.order.service.impl.export;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.domain.NotBuyDomain;
import com.camelot.order.export.service.DictValueExportService;
import com.camelot.order.export.service.NotBuyExportService;
import com.camelot.order.export.vo.NotBuyVO;
import com.camelot.order.feign.FeignCustomerService;
import com.camelot.order.feign.FeignGoodsCategoryService;
import com.camelot.order.feign.FeignGoodsService;
import com.camelot.order.feign.FeignStoreService;
import com.camelot.order.service.base.BaseServiceImpl;

@Service("notBuyExportService")
public class NotBuyExportServiceImpl extends BaseServiceImpl<NotBuyVO, NotBuyDomain> implements NotBuyExportService {

	private static Logger log = Log.get(NotBuyExportServiceImpl.class);

	@Autowired
	FeignCustomerService feignCustomerService;

	@Autowired
	DictValueExportService dictValueExportService;

	@Autowired
	FeignGoodsService feignGoodsService;

	@Autowired
	FeignGoodsCategoryService feignGoodsCategoryService;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	@Autowired
	FeignStoreService feignStoreService;

	@Override
	public ExecuteResult<NotBuyVO> submitNotBuy(NotBuyVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "SalesOrderExportServiceImpl-getMaxSalesOrderNumber",
				JSONObject.toJSONString(vo));
		ExecuteResult<NotBuyVO> result = new ExecuteResult<NotBuyVO>();
		// 必输字段非空校验
		String validateResult = validateEmpty(vo);
		if (!Constants.SUCCESS_CODE.equals(validateResult)) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(validateResult);
			Log.debug(log, "\n 方法[{}]，出参：[{}]", "NotBuyExportServiceImpl-submitNotBuy", result);
			return result;
		}
		// 消费者手机号和消费者姓名都不为空则添加至消费者列表中
		if (Utility.isNotEmpty(vo.getCustomerName()) && Utility.isNotEmpty(vo.getCustomerPhoneNumber())) {
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
				Log.debug(log, "\n 方法[{}]，出参：[{}]", "NotBuyExportServiceImpl-submitNotBuy", result);
				return result;
			}
		}
		// 初始化:修改人=创建人
		vo.setUpdateUserId(vo.getCreateUserId());
		// 时间
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date nowDate = df.parse(df.format(new Date()));
			// 设置时间
			vo.setCreateDate(nowDate);
			vo.setModifyDate(nowDate);
			// 设置delFlg
			vo.setDelFlg(Constants.DELFLG_NORMAL);
		} catch (Exception e) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.toString());
			Log.error(log, "\n 方法[{}]，异常：[{}]", "NotBuyExportServiceImpl-submitNotBuy", e.getMessage());
			return result;
		}
		// 事务控制
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					ExecuteResult<NotBuyVO> addResult = add(vo);
					result.setCode(addResult.getCode());
					result.setResult(addResult.getResult());
					result.setResultMessage(addResult.getResultMessage());
				} catch (Exception e) {
					result.setCode(Constants.ERROR_CODE);
					result.setResultMessage(e.toString());
					status.setRollbackOnly();
					Log.error(log, "\n 方法[{}]，异常：[{}]", "NotBuyExportServiceImpl-submitNotBuy", e.getMessage());
				}
			}
		});

		Log.debug(log, "\n 方法[{}]，出参：[{}]", "NotBuyExportServiceImpl-submitNotBuy", result);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NotBuyVO> handleNotBuyList(List<NotBuyVO> list) {
		if (Utility.isNotEmpty(list)) {
			for (NotBuyVO vo : list) {
				// 消费者
				if (Utility.isNotEmpty(vo.getCustomerId())) {
					Map<String, Object> customerMap = new HashMap<String, Object>();
					customerMap.put("customerId", vo.getCustomerId());
					AjaxInfo customerInfo = feignCustomerService.queryCustomerList(customerMap);
					if (Utility.isNotEmpty(customerInfo.getData())) {
						Map<String, Object> customerResultMap = ((List<Map<String, Object>>) customerInfo.getData())
								.get(0);
						// 消费者姓名
						if (customerResultMap.containsKey("customerName")) {
							vo.setCustomerName(Utility.objectToString(customerResultMap.get("customerName")));
						}
						// 消费者手机号
						if (customerResultMap.containsKey("customerPhoneNumber")) {
							vo.setCustomerPhoneNumber(Utility.objectToString(customerResultMap.get("customerPhoneNumber")));
						}
						// 消费者来源
						if (customerResultMap.containsKey("customerSource")) {
							String customerSource = Utility.objectToString(customerResultMap.get("customerSource"));
							if (Utility.isNotEmpty(customerSource)) {
								Integer customerSourceId = Integer.parseInt(customerSource);
								vo.setCustomerSourceValue(dictValueExportService.getDictValueNameById(
										OrderConstants.ORDER_DICT_CUSTOMER_SOURCE, customerSourceId));
							}
						}
					}
				}
				// 未购买原因
				if (Utility.isNotEmpty(vo.getNotBuyReason())) {
					vo.setNotBuyReasonValue(dictValueExportService
							.getDictValueNameById(OrderConstants.ORDER_DICT_NOT_BUY_REASON, vo.getNotBuyReason()));
				}
				// 意向车型
				if (Utility.isNotEmpty(vo.getIntentionVehicle())) {
					AjaxInfo vehicleInfo = feignGoodsCategoryService
							.queryVehicleByCategoryId(vo.getIntentionVehicle().longValue());
					if (Utility.isNotEmpty(vehicleInfo.getData())) {
						vo.setIntentionVehicleName(vehicleInfo.getData().toString());
					}
				}
				//门店信息
				if(Utility.isNotEmpty(vo.getStoreId())){
					AjaxInfo storeInfo = feignStoreService.getStoreById(vo.getStoreId().longValue());
					if (Utility.isNotEmpty(storeInfo.getData())) {
						@SuppressWarnings("unchecked")
						Map<String, Object> map = (Map) storeInfo.getData();
						if (map.containsKey("storeNumber")) {
							vo.setStoreNumber(Utility.objectToString(map.get("storeNumber")));
						}
						if (map.containsKey("storeName")) {
							vo.setStoreName(Utility.objectToString(map.get("storeName")));
						}
					}
				}
			}
		}

		return list;
	}

	/**
	 * @Description 新增时,必输字段的非空校验
	 * @author xupengfei
	 * @Date 2019年4月17日
	 * @param vo
	 * @return
	 */
	public String validateEmpty(NotBuyVO vo) {
		if (Utility.isEmpty(vo.getIntentionVehicle())) {
			return "意向车型为空";
		} else if (Utility.isEmpty(vo.getNotBuyReason())) {
			return "未购买原因为空";
		} else if (Utility.isEmpty(vo.getStoreId())) {
			return "门店ID为空";
		} else if (Utility.isEmpty(vo.getCreateUserId())) {
			return "上报人ID为空";
		} else if (Utility.isEmpty(vo.getCityId())) {
			return "城市(区域)ID为空";
		} else {
			return Constants.SUCCESS_CODE;
		}
	}

}

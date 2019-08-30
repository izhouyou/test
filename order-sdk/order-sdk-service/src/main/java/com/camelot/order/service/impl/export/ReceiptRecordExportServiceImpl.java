package com.camelot.order.service.impl.export;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.CheckUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.ReceiptRecordDAO;
import com.camelot.order.domain.NotBuyDomain;
import com.camelot.order.domain.ReceiptRecordDomain;
import com.camelot.order.export.service.DictValueExportService;
import com.camelot.order.export.service.NotBuyExportService;
import com.camelot.order.export.service.ReceiptRecordExportService;
import com.camelot.order.export.service.SalesOrderExportService;
import com.camelot.order.export.vo.NotBuyVO;
import com.camelot.order.export.vo.ReceiptRecordVO;
import com.camelot.order.export.vo.SalesOrderVO;
import com.camelot.order.feign.FeignCustomerService;
import com.camelot.order.feign.FeignGoodsService;
import com.camelot.order.feign.FeignStoreService;
import com.camelot.order.service.SalesOrderService;
import com.camelot.order.service.base.BaseServiceImpl;

@Service("receiptRecordExportService")
public class ReceiptRecordExportServiceImpl extends BaseServiceImpl<ReceiptRecordVO, ReceiptRecordDomain>
		implements ReceiptRecordExportService {
	private static Logger log = Log.get(ReceiptRecordExportServiceImpl.class);

	@Autowired
	SalesOrderService salesOrderService;

	@Autowired
	ReceiptRecordDAO receiptRecordDAO;

	@Autowired
	FeignStoreService feignStoreService;

	@Autowired
	DictValueExportService dictValueExportService;
	
	@Autowired
	SalesOrderExportService salesOrderExportService;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Override
	public String getMaxReceiptRecordNumber(String nowDate) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ReceiptRecordExportServiceImpl-getMaxReceiptRecordNumber", nowDate);
		String result = "";
		try {
			result = receiptRecordDAO.getMaxReceiptRecordNumber(nowDate);
		} catch (Exception e) {
			Log.error(log, "\n 方法[{}]，异常：[{}]", "ReceiptRecordExportServiceImpl-getMaxReceiptRecordNumber",
					e.getMessage());
			return Constants.ERROR_CODE;
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "ReceiptRecordExportServiceImpl-getMaxReceiptRecordNumber", result);
		return result;
	}

	@Override
	public ExecuteResult<ReceiptRecordVO> addReceiptRecord(ReceiptRecordVO vo) throws Exception {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ReceiptRecordExportServiceImpl-addReceiptRecord",
				JSONObject.toJSONString(vo));
		ExecuteResult<ReceiptRecordVO> result = new ExecuteResult<ReceiptRecordVO>();
		// 非空校验
		String validateResult = validateNotEmpty(vo);
		if (!Constants.SUCCESS_CODE.equals(validateResult)) {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(validateResult);
			Log.debug(log, "\n 方法[{}]，出参：[{}]", "ReceiptRecordExportServiceImpl-addReceiptRecord", result);
			return result;
		}
		// 获取订单ID
		SalesOrderVO so = new SalesOrderVO();
		so.setSalesOrderNumber(vo.getSalesOrderNumber());
		List<SalesOrderVO> soList = salesOrderService.queryList(so).getResult();
		Integer version;
		if (Utility.isNotEmpty(soList)) {
			// 获取版本号
			version = soList.get(0).getSalesOrderVersion();
			vo.setSalesOrderId(soList.get(0).getSalesOrderId());
			//收款单重复性校验
			ReceiptRecordVO repeatVO = new ReceiptRecordVO();
			repeatVO.setSalesOrderId(soList.get(0).getSalesOrderId());
			ExecuteResult<List<ReceiptRecordVO>> repeatResult = queryList(repeatVO);
			if(CheckUtil.checkResultListIsNotNull(repeatResult)){
				result.setCode(Constants.ERROR_CODE);
				result.setResultMessage("该订单已收款完成!");
				Log.debug(log, "\n 方法[{}]，出参：[{}]", "ReceiptRecordExportServiceImpl-addReceiptRecord", result);
				return result;
			}
			// 收款单金额(一阶段为订单金额)
			if (Utility.isEmpty(vo.getReceiptAmount())) {
				vo.setReceiptAmount(soList.get(0).getSalesOrderAmount());
			}
		} else {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("订单编号有误");
			Log.debug(log, "\n 方法[{}]，出参：[{}]", "ReceiptRecordExportServiceImpl-addReceiptRecord", result);
			return result;
		}
		// 支付状态:默认为已支付
		if (Utility.isEmpty(vo.getPaymentStatus())) {
			vo.setPaymentStatus(OrderConstants.DEFAULT_RECEIPT_STATUS);
		}
		// 初始值:修改人=创建人
		vo.setUpdateUserId(vo.getCreateUserId());
		// del_flg
		vo.setDelFlg(Constants.DELFLG_NORMAL);
		// 创建时间\修改时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = df.parse(df.format(new Date()));
		// 设置时间
		vo.setCreateDate(nowDate);
		vo.setModifyDate(nowDate);
		// 获取收款单号
		// 获取收款单编号
		String number = OrderNumberExportService.getOrderNumber(OrderConstants.ORDER_TYPE_RECEIPT, vo.getStoreNumber());
		if (Utility.isNotEmpty(number)) {
			vo.setReceiptRecordNumber(number);
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						ExecuteResult<ReceiptRecordVO> addResult = add(vo);
						if (Constants.SUCCESS_CODE.equals(addResult.getCode())) {
							// 修改订单状态
							SalesOrderVO salesOrder = new SalesOrderVO();
							salesOrder.setSalesOrderId(vo.getSalesOrderId());
							salesOrder.setSalesOrderVersion(version);
							//修改时间
							salesOrder.setModifyDate(nowDate);
							//修改人
							salesOrder.setUpdateUserId(vo.getCreateUserId());
							// 设置状态为已完成
							salesOrder.setSalesOrderStatus(OrderConstants.ORDER_STATUS_FINISH);
							ExecuteResult<SalesOrderVO> updateResult = salesOrderService.update(salesOrder);
							if (Constants.ERROR_CODE.equals(updateResult.getCode())) {
								result.setCode(Constants.ERROR_CODE);
								result.setResultMessage(updateResult.getResultMessage());
								// 事务回滚
								status.setRollbackOnly();
							} else {
								result.setCode(Constants.SUCCESS_CODE);
								result.setResultMessage(Constants.SAVE_SUCCESS);
								result.setResult(vo);
							}
						} else {
							result.setCode(Constants.ERROR_CODE);
							result.setResultMessage(addResult.getResultMessage());
							status.setRollbackOnly();
						}
					} catch (Exception e) {
						result.setCode(Constants.ERROR_CODE);
						result.setResultMessage(e.toString());
						Log.error(log, "\n 方法[{}]，异常：[{}]", "ReceiptRecordExportServiceImpl-addReceiptRecord",
								e.getMessage());
						status.setRollbackOnly();
					}
				}
			});
		} else {
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage("收款单号获取失败");
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "ReceiptRecordExportServiceImpl-addReceiptRecord", result);
		return result;
	}

	/**
	 * @Description 必需参数非空判断
	 * @author xupengfei
	 * @Date 2019年4月10日
	 * @param vo
	 * @return
	 */
	public String validateNotEmpty(ReceiptRecordVO vo) {
		if (Utility.isEmpty(vo.getSalesOrderNumber())) {
			return "订单号为空";
		} else if (Utility.isEmpty(vo.getCreateUserId())) {
			return "创建人为空";
		} else if (Utility.isEmpty(vo.getStoreNumber())) {
			return "门店编码为空";
		} else {
			return Constants.SUCCESS_CODE;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiptRecordVO> handleReceiptRecordList(List<ReceiptRecordVO> list) {
		if (Utility.isNotEmpty(list)) {
			for (ReceiptRecordVO vo : list) {
				// 订单相关信息
				if (Utility.isNotEmpty(vo.getSalesOrderId())) {
					SalesOrderVO so = salesOrderService.queryById(vo.getSalesOrderId().longValue()).getResult();
					if (Utility.isNotEmpty(so)) {
						// 订单编号
						vo.setSalesOrderNumber(so.getSalesOrderNumber());
						// 门店名称
						// 门店名称
						if (Utility.isNotEmpty(so.getStoreId())) {
							AjaxInfo storeInfo = feignStoreService.getStoreById(so.getStoreId().longValue());
							if (Utility.isNotEmpty(storeInfo.getData())) {
								Map<String, Object> map = (Map) storeInfo.getData();
								if (map.containsKey("storeName")) {
									vo.setStoreName(Utility.objectToString(map.get("storeName")));
								}
							}
						}
						// 支付方式
						if (Utility.isNotEmpty(so.getPaymentWay())) {
							vo.setPaymentWayValue(dictValueExportService
									.getDictValueNameById(OrderConstants.ORDER_DICT_PAYMENT, so.getPaymentWay()));
						}
						// 订单来源
						if (Utility.isNotEmpty(so.getSalesOrderSource())) {
							vo.setSalesOrderSourceValue(dictValueExportService.getDictValueNameById(
									OrderConstants.ORDER_DICT_ORDER_SOURCE, so.getSalesOrderSource()));
						}

					}
				}
				// 收款单金额:保留两位小数
				if (Utility.isNotEmpty(vo.getReceiptAmount())) {
					DecimalFormat format = new DecimalFormat("0.00");
					vo.setReceiptAmountValue(format.format(vo.getReceiptAmount()));
				}
				// 支付渠道
				if (Utility.isNotEmpty(vo.getPaymentChannel())) {
					vo.setPaymentChannelValue(dictValueExportService
							.getDictValueNameById(OrderConstants.ORDER_DICT_PAYCHANNEL, vo.getPaymentChannel()));
				}

				// 支付状态
				if (Utility.isNotEmpty(vo.getPaymentStatus())) {
					vo.setPaymentStatusValue(dictValueExportService
							.getDictValueNameById(OrderConstants.ORDER_DICT_PAYMENT_STATUS, vo.getPaymentStatus()));
				}
			}

		}

		return list;
	}

	// TODO 如何处理存在订单表里的查询条件
	@Override
	public ReceiptRecordVO handleVOBeforeQuery(ReceiptRecordVO vo) {
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
		// 由单号开头判断是否是是销售订单编号
		if (Utility.isNotEmpty(vo.getReceiptRecordNumber())
				&& vo.getReceiptRecordNumber().startsWith(OrderConstants.ORDER_TYPE_SALES)) {
			isSalesOrder = true;
			so.setSalesOrderNumber(vo.getReceiptRecordNumber());
			vo.setReceiptRecordNumber(null);
		}
		// 门店ID
		if (Utility.isNotEmpty(vo.getStoreId())) {
			isSalesOrder = true;
			so.setStoreId(vo.getStoreId());
		}
		// 支付方式
		if (Utility.isNotEmpty(vo.getPaymentWay())) {
			isSalesOrder = true;
			so.setPaymentWay(vo.getPaymentWay());
		}
		// 订单来源
		if (Utility.isNotEmpty(vo.getSalesOrderSource())) {
			isSalesOrder = true;
			so.setSalesOrderSource(vo.getSalesOrderSource());
		}
		// 如果涉及到销售订单则查询出符合的销售订单id
		if (isSalesOrder) {
			List<SalesOrderVO> soList = salesOrderService.queryList(so).getResult();
			List<Integer> idList = new ArrayList<Integer>();
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

}

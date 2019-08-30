package com.camelot.order.service.impl.export;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.service.ReceiptRecordExportService;
import com.camelot.order.export.service.ReturnOrderExportService;
import com.camelot.order.export.service.SalesOrderExportService;

@Service
public class OrderNumberExportService{

	@Autowired
	SalesOrderExportService salesOrderExportService;
	
	@Autowired
	ReturnOrderExportService returnOrderExportService;
	
	@Autowired
	ReceiptRecordExportService receiptRecordExportService;
	
	@Autowired
	RedisIDService redisIDService;
	

	private static OrderNumberExportService service;

	private static Logger log = Log.get(OrderNumberExportService.class);

	@PostConstruct
	public void init() {
		service = this;
		service.salesOrderExportService = this.salesOrderExportService;
		service.returnOrderExportService = this.returnOrderExportService;
		service.receiptRecordExportService=this.receiptRecordExportService;
		service.redisIDService=this.redisIDService;
	}

	/*
	 * 单据（全部两位字母+15位数字）： 销售订单号： XS 1234（门店编码）56（年）78（月）910（日） 11-15（序列号） 退货订单号：
	 * TH 1234（门店编码）56（年）78（月）910（日） 11-15（序列号） 收款单号： SK
	 * 1234（门店编码）56（年）78（月）910（日） 11-15（序列号）
	 */

	/**
	 * @Description 生成单号(根据类型和门店编码)
	 * @author xupengfei
	 * @Data 2019年4月4日
	 * @param type
	 * @param storeNumber
	 * @return
	 */
	public static synchronized String getOrderNumber(String type, String storeNumber) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "OrderNumberExportService-getOrderNumber", type);
		String number = "";
		// 获取当前日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = "";
		try {
			nowDate = df.format(new Date());
		} catch (Exception e) {
			Log.error(log, "\n 方法[{}]，异常：[{}]", "OrderNumberExportService-getOrderNumber", e.getMessage());
			return number;
		}
		//是否从数据库查询最大的单号
		boolean isFromDb = false;
		// 销售订单
		String maxNumber = "";
		if (OrderConstants.ORDER_TYPE_SALES.equals(type)) {
			// 获取当前天订单最大订单号(Redis)
			maxNumber = service.redisIDService.getOrderId();
			//获取失败则从数据库查询单天最大订单号
			if(Utility.isEmpty(maxNumber)){
				maxNumber = service.salesOrderExportService.getMaxSalesOrderNumber(nowDate);
				isFromDb = true;
			}
		}
		// 退货订单
		else if (OrderConstants.ORDER_TYPE_RETURN.equals(type)) {
			maxNumber=service.redisIDService.getReturnOrderId();
			if(Utility.isEmpty(maxNumber)){
				maxNumber = service.returnOrderExportService.getMaxReturnOrderNumber(nowDate);	
				isFromDb = true;
			}
		}
		// 收款单
		else if (OrderConstants.ORDER_TYPE_RECEIPT.equals(type)) {
			maxNumber = service.redisIDService.getReceiptId();
			if(Utility.isEmpty(maxNumber)){
				maxNumber = service.receiptRecordExportService.getMaxReceiptRecordNumber(nowDate);
				isFromDb = true;
			}

		}

		if (!Constants.ERROR_CODE.equals(maxNumber)) {
			//截取门店号码为四位
			storeNumber = storeNumber.substring(storeNumber.length()-4);
			String prefix = type + storeNumber + nowDate.replaceAll("-", "").substring(2);
			if (Utility.isNotEmpty(maxNumber)) {
				try {
					String newSerialNumber = "";
					//从DB查询出的需要处理位数并+1
					if(isFromDb){
						//转为int
						int serialNumber = Integer.parseInt(maxNumber);
						// 序列号+1转为String
					    newSerialNumber = String.valueOf((serialNumber + 1));
						int len = newSerialNumber.length();
						// 补0凑齐五位序列号
						for (int i = 0; i < 5 - len; i++) {
							newSerialNumber = "0" + newSerialNumber;
						}
					}else{
						newSerialNumber=maxNumber;
					}
					// 拼接订单号:
					number = prefix + newSerialNumber;
				} catch (Exception e) {
					Log.error(log, "\n 方法[{}]，异常：[{}]", "OrderNumberExportService-getOrderNumber", e.getMessage());
					return number;
				}
			} else {
				// 当天无订单从00001开始
				number = prefix + "00001";
			}

		}
		return number;
	}

}

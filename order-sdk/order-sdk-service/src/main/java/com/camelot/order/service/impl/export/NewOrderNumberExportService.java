package com.camelot.order.service.impl.export;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.HttpsClients;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.service.NewReceiptRecordExportService;
import com.camelot.order.export.service.NewReturnOrderExportService;
import com.camelot.order.export.service.NewSalesOrderExportService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NewOrderNumberExportService {

	@Autowired
	private NewSalesOrderExportService newSalesOrderExportService;

	@Autowired
	private NewReturnOrderExportService newReturnOrderExportService;

	@Autowired
	private NewReceiptRecordExportService newReceiptRecordExportService;

	@Autowired
	private RedisIDService redisIDService;

	private HttpClient httpclient = HttpsClients.newHttpsClient();


	private static NewOrderNumberExportService service;

	private static Logger log = Log.get(NewOrderNumberExportService.class);


	@Value(value = "${genid}")
	private String wholeGenid;




	@PostConstruct
	public void init() {
		service = this;
		service.newSalesOrderExportService = this.newSalesOrderExportService;
		service.newReturnOrderExportService = this.newReturnOrderExportService;
		service.newReceiptRecordExportService=this.newReceiptRecordExportService;
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
	public synchronized String getOrderNumber(String type, String storeNumber) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewOrderNumberExportService-getOrderNumber", type);
		String number = "";
		// 获取当前日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = "";
		try {
			nowDate = df.format(new Date());
		} catch (Exception e) {
			Log.error(log, "\n 方法[{}]，异常：[{}]", "NewOrderNumberExportService-getOrderNumber", e.getMessage());
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
				maxNumber = service.newSalesOrderExportService.getMaxSalesOrderNumber(nowDate);
				isFromDb = true;
			}
		}
		// 退货订单
		else if (OrderConstants.ORDER_TYPE_RETURN.equals(type)) {
			maxNumber=service.redisIDService.getReturnOrderId();
			if(Utility.isEmpty(maxNumber)){
				maxNumber = service.newReturnOrderExportService.getMaxReturnOrderNumber(nowDate);
				isFromDb = true;
			}
		}
		// 收款单
		else if (OrderConstants.ORDER_TYPE_RECEIPT.equals(type)) {
			maxNumber = service.redisIDService.getReceiptId();
			if(Utility.isEmpty(maxNumber)){
				maxNumber = service.newReceiptRecordExportService.getMaxReceiptRecordNumber(nowDate);
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
					Log.error(log, "\n 方法[{}]，异常：[{}]", "NewOrderNumberExportService-getOrderNumber", e.getMessage());
					return number;
				}
			} else {
				// 当天无订单从00001开始
				number = prefix + "00001";
			}

		}
		return number;
	}

	public synchronized ExecuteResult<String> getGenid() {
		ExecuteResult<String> results = new ExecuteResult<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startExecute = new Date();
		String startExecuteStr = "";
		String param = "";
		try {
			StringEntity entity = new StringEntity(param, "utf-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			//发送post请求
			HttpPost importDetailResumePost = new HttpPost(wholeGenid);
			importDetailResumePost.setEntity(entity);
			startExecuteStr = sdf.format(new Date());
			Log.info("开始执行时间：" + startExecuteStr + "-----请求地址为 :" + importDetailResumePost.getURI()+ "-----请求参数为 :" + param);
			HttpResponse response = httpclient.execute(importDetailResumePost);
			Date endExecute = new Date();
			String endExecuteStr = sdf.format(endExecute);
			Long time = endExecute.getTime() - startExecute.getTime();
			//获取结果实体
			HttpEntity importDetailEntity = response.getEntity();
			String importDetailEntityStr = EntityUtils.toString(importDetailEntity, "utf-8");
			Log.info("执行结束时间：" + endExecuteStr + "-------执行耗时" + time + "毫秒。----返回参数为 :" + importDetailEntityStr);
			//关闭连接
			importDetailResumePost.releaseConnection();
			results.setResult(importDetailEntityStr);
			results.setCode(Constants.SUCCESS_CODE);
		} catch (Exception e) {
			Date endExecute = new Date();
			String endExecuteStr = sdf.format(endExecute);
			Long time = endExecute.getTime() - startExecute.getTime();
			Log.info("开始执行时间：" + startExecuteStr + "------执行异常结束时间：" + endExecuteStr + "-------执行耗时" + time + "毫秒。----请求参数为 :" + param);
			results.setCode(Constants.ERROR_CODE);
			results.setResultMessage(Constants.QUERY_FAILURE);
			Log.error(log, "\n 方法[{}]，异常：[{}]", "UserExportServiceImpl-checkMessage", e.getMessage());
		}
		Log.info(log, "\n 方法[{}]，出参：[{}]", "UserExportServiceImpl-checkMessage", JSONObject.toJSONString(results));
		return results;
	}

}

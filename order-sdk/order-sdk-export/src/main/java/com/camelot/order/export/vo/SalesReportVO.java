package com.camelot.order.export.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.camelot.order.common.utils.Utility;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * <p>Description: [门店销售报表]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName SalesReportVO.java
 * Created on 2019年4月8日.
 */
public class SalesReportVO implements Serializable {
	/**主键*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long salesOrderId;
	/** 退货订单id */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long returnOrderId;
	/**订单编号*/
	private String salesOrderNumber;
	/** 退货订单编号*/
	private String returnOrderNumber;
	/**门店名称*/
	private String storeName;
	/**订单金额*/
	private BigDecimal salesOrderAmount;
	/**订单金额(String)*/
	private String salesOrderAmountValue;
	/**支付方式名称*/
	private String paymentWayName;
	/**订单来源名称*/
	private String salesOrderSourceName;
	/**订单状态名称*/
	private String salesOrderStatusName;
	/**支付方式oldName*/
	private String paymentWayValue;
	/**订单来源oldName*/
	private String salesOrderSourceValue;
	/**订单状态oldName*/
	private String salesOrderStatusValue;
	/**消费者电话*/
	private String customerPhoneNumber;
	/**消费者名称*/
	private String customerName;
	/**活动编码*/
	private String activityNumber;
	/**活动名称*/
	private String activityName;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createDate;
	
	public Long getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	public Long getReturnOrderId() {
		return returnOrderId;
	}
	public void setReturnOrderId(Long returnOrderId) {
		this.returnOrderId = returnOrderId;
	}
	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}
	public String getReturnOrderNumber() {
		return returnOrderNumber;
	}
	public void setReturnOrderNumber(String returnOrderNumber) {
		this.returnOrderNumber = returnOrderNumber;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public BigDecimal getSalesOrderAmount() {
		return salesOrderAmount;
	}
	public void setSalesOrderAmount(BigDecimal salesOrderAmount) {
		this.salesOrderAmount = salesOrderAmount;
		this.salesOrderAmountValue = Utility.bigDecimalToString(salesOrderAmount);
	}
	public String getSalesOrderAmountValue() {
		return salesOrderAmountValue;
	}
	public void setSalesOrderAmountValue(String salesOrderAmountValue) {
		this.salesOrderAmountValue = salesOrderAmountValue;
	}
	public String getPaymentWayName() {
		return paymentWayName;
	}
	public void setPaymentWayName(String paymentWayName) {
		this.paymentWayName = paymentWayName;
		this.paymentWayValue = paymentWayName;
	}
	public String getSalesOrderSourceName() {
		return salesOrderSourceName;
	}
	public void setSalesOrderSourceName(String salesOrderSourceName) {
		this.salesOrderSourceName = salesOrderSourceName;
		this.salesOrderSourceValue = salesOrderSourceName;
	}
	public String getSalesOrderStatusName() {
		return salesOrderStatusName;
	}
	public void setSalesOrderStatusName(String salesOrderStatusName) {
		this.salesOrderStatusName = salesOrderStatusName;
		this.salesOrderStatusValue = salesOrderStatusName;
	}
	public String getPaymentWayValue() {
		return paymentWayValue;
	}
	public void setPaymentWayValue(String paymentWayValue) {
		this.paymentWayValue = paymentWayValue;
	}
	public String getSalesOrderSourceValue() {
		return salesOrderSourceValue;
	}
	public void setSalesOrderSourceValue(String salesOrderSourceValue) {
		this.salesOrderSourceValue = salesOrderSourceValue;
	}
	public String getSalesOrderStatusValue() {
		return salesOrderStatusValue;
	}
	public void setSalesOrderStatusValue(String salesOrderStatusValue) {
		this.salesOrderStatusValue = salesOrderStatusValue;
	}
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityNumber() {
		return activityNumber;
	}
	public void setActivityNumber(String activityNumber) {
		this.activityNumber = activityNumber;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}

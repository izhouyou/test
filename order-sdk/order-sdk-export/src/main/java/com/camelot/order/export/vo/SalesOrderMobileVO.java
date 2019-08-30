package com.camelot.order.export.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SalesOrderMobileVO implements Serializable {

	/**主键*/
	private Integer salesOrderId;
	/**订单编号*/
	private String salesOrderNumber;
	/**消费者来源*/
	private String customerSource;
	/**消费者姓名*/
	private String customerName;
	/**消费者手机号*/
	private String customerPhoneNumber;
	/**订单取消原因*/
	private String cancelReason;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createDate;
	/**修改时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date modifyDate;
	/**商品名称集合*/
	private List<String> goodsList;
	/**退货标识*/
	private Integer returnFlg;
	public Integer getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(Integer salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}
	public String getCustomerSource() {
		return customerSource;
	}
	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public List<String> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<String> goodsList) {
		this.goodsList = goodsList;
	}
	public Integer getReturnFlg() {
		return returnFlg;
	}
	public void setReturnFlg(Integer returnFlg) {
		this.returnFlg = returnFlg;
	}
	
	
	
}

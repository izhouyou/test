package com.camelot.order.export.vo;

import java.io.Serializable;

import com.camelot.order.common.utils.Utility;

public class NotBuyCustomerVO implements Serializable {
	
	/** 门店编号*/
	private String storeNumber;
	/** 门店名称*/
	private String storeName;
	/**消费者姓名*/
	private String customerName;
	/**消费者手机号*/
	private String customerPhoneNumber;
	/**消费者来源(value)*/
	private String customerSourceValue;
	/**消费者来源名称*/
	private String customerSourceName;
	/**意向车型名称*/
	private String intentionVehicleName;
	/**整车三级分类名称*/
	private String vehicleThirdCategoryName;
	/**未购买原因(value)*/
	private String notBuyReasonValue;
	/**未购买原因名称*/
	private String notBuyReasonName;
	/**订单取消名称*/
	private String cancelReasonName;
	
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
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
	public String getCustomerSourceValue() {
		return customerSourceValue;
	}
	public void setCustomerSourceValue(String customerSourceValue) {
		this.customerSourceValue = customerSourceValue;
	}
	public String getCustomerSourceName() {
		return customerSourceName;
	}
	public void setCustomerSourceName(String customerSourceName) {
		this.customerSourceName = customerSourceName;
		if(Utility.isEmpty(this.customerSourceValue)) {
			this.customerSourceValue = customerSourceName;
		}
	}
	public String getIntentionVehicleName() {
		return intentionVehicleName;
	}
	public void setIntentionVehicleName(String intentionVehicleName) {
		this.intentionVehicleName = intentionVehicleName;
	}
	public String getVehicleThirdCategoryName() {
		return vehicleThirdCategoryName;
	}
	public void setVehicleThirdCategoryName(String vehicleThirdCategoryName) {
		this.vehicleThirdCategoryName = vehicleThirdCategoryName;
		if(Utility.isEmpty(this.intentionVehicleName)) {
			this.intentionVehicleName = vehicleThirdCategoryName;
		}
	}
	public String getNotBuyReasonValue() {
		return notBuyReasonValue;
	}
	public void setNotBuyReasonValue(String notBuyReasonValue) {
		this.notBuyReasonValue = notBuyReasonValue;
	}
	public String getNotBuyReasonName() {
		return notBuyReasonName;
	}
	public void setNotBuyReasonName(String notBuyReasonName) {
		this.notBuyReasonName = notBuyReasonName;
		if(Utility.isEmpty(this.notBuyReasonValue)) {
			this.notBuyReasonValue = notBuyReasonName;
		}
	}
	public String getCancelReasonName() {
		return cancelReasonName;
	}
	public void setCancelReasonName(String cancelReasonName) {
		this.cancelReasonName = cancelReasonName;
		if(Utility.isEmpty(this.notBuyReasonValue)) {
			this.notBuyReasonValue = cancelReasonName;
		}
	}
	
}

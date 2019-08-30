package com.camelot.order.export.vo.excelvo;

import java.io.Serializable;

import com.camelot.order.common.utils.excel.ExcelField;

public class NotBuyExportVO implements Serializable {
	
	/** 门店编号*/
	@ExcelField(title = "门店编号", sort = 5 )
	private String storeNumber;
	/** 门店名称*/
	@ExcelField(title = "门店名称", sort = 10 )
	private String storeName;
	/**消费者姓名*/
	@ExcelField(title = "消费者姓名", sort = 15 )
	private String customerName;
	/**消费者手机号*/
	@ExcelField(title = "消费者手机号码", sort = 20 )
	private String customerPhoneNumber;
	/**消费者来源(value)*/
	@ExcelField(title = "获知来源", sort = 25 )
	private String customerSourceValue;
	/**未购买原型(value)*/
	@ExcelField(title = "未购买原因", sort = 30 )
	private String notBuyReasonValue;
	/**意向车型名称*/
	@ExcelField(title = "意向车型", sort = 35 )
	private String intentionVehicleName;
	
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
	public String getNotBuyReasonValue() {
		return notBuyReasonValue;
	}
	public void setNotBuyReasonValue(String notBuyReasonValue) {
		this.notBuyReasonValue = notBuyReasonValue;
	}
	public String getIntentionVehicleName() {
		return intentionVehicleName;
	}
	public void setIntentionVehicleName(String intentionVehicleName) {
		this.intentionVehicleName = intentionVehicleName;
	}
	
}

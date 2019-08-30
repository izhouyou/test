package com.camelot.order.export.vo.excelvo;

import com.camelot.order.common.utils.excel.ExcelField;
import java.io.Serializable;

public class CustomerExcelVO implements Serializable{

	/**用户id*/
	@ExcelField(title = "用户ID", sort = 1 )
	private Integer userId;
	/**用户编号*/
	@ExcelField(title = "用户编号", sort = 2 )
	private String customerNunber;
	/**顾客手机号*/
	@ExcelField(title = "消费者手机号码", sort = 10 )
	private String customerPhoneNumber;
	/**客户姓名*/
	@ExcelField(title = "消费者姓名", sort = 15 )
	private String customerName;
	/**消费金额*/
	@ExcelField(title = "消费金额", sort = 20 )
	private String priceTotal;
	/**订单数量*/
	@ExcelField(title = "订单数量", sort = 25 )
	private Integer numberTotal;

	public String getCustomerNunber() {
		return customerNunber;
	}
	public void setCustomerNunber(String customerNunber) {
		this.customerNunber = customerNunber;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String getPriceTotal() {
		return priceTotal;
	}
	public void setPriceTotal(String priceTotal) {
		this.priceTotal = priceTotal;
	}
	public Integer getNumberTotal() {
		return numberTotal;
	}
	public void setNumberTotal(Integer numberTotal) {
		this.numberTotal = numberTotal;
	}

	
}

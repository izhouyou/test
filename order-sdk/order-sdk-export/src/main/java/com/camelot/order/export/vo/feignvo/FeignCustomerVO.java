package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;
import java.util.Date;

public class FeignCustomerVO implements Serializable{
	/**主键*/
	private Integer customerId;
	/**消费者编号*/
	private String customerNunber;
	/**客户姓名*/
	private String customerName;
	/**顾客手机号*/
	private String customerPhoneNumber;
	/**获知来源(字典id)*/
	private Integer customerSource;
	/**获知来源(value)*/
	private String customerSourceValue;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Integer createUserId;
	/**修改人id*/
	private Integer updateUserId;
	/**创建时间*/
	private Date createDate;
	/**修改时间*/
	private Date modifyDate;
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerNunber() {
		return customerNunber;
	}
	public void setCustomerNunber(String customerNunber) {
		this.customerNunber = customerNunber;
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
	public Integer getCustomerSource() {
		return customerSource;
	}
	public void setCustomerSource(Integer customerSource) {
		this.customerSource = customerSource;
	}
	public String getCustomerSourceValue() {
		return customerSourceValue;
	}
	public void setCustomerSourceValue(String customerSourceValue) {
		this.customerSourceValue = customerSourceValue;
	}
	public Integer getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(Integer delFlg) {
		this.delFlg = delFlg;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
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
	
	
}

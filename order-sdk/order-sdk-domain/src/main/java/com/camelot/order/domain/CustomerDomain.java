package com.camelot.order.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CustomerDomain implements Serializable {

	/**主键*/
	private Long customerId;
	/**消费者编号*/
	private String customerNunber;
	/**客户姓名*/
	private String customerName;
	/**顾客手机号*/
	private String customerPhoneNumber;
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
	/**区域集合*/
	private List<Integer> orgList;
	/**门店集合*/
	private List<Integer> storeList;
	/**消费者id集合*/
	private List<Long> customerIdList;
	/*搜索开始时间*/
	private Date startDate;
	/*搜索结束时间*/
	private Date endDate;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
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

	public List<Integer> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Integer> orgList) {
		this.orgList = orgList;
	}

	public List<Integer> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Integer> storeList) {
		this.storeList = storeList;
	}

	public List<Long> getCustomerIdList() {
		return customerIdList;
	}

	public void setCustomerIdList(List<Long> customerIdList) {
		this.customerIdList = customerIdList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}

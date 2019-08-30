package com.camelot.order.export.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NotBuyVO implements Serializable {

	/**主键*/
	private Integer notBuyId;
	/**消费者id*/
	private Integer customerId;
	/**消费者姓名*/
	private String customerName;
	/**消费者手机号*/
	private String customerPhoneNumber;
	/**消费者来源*/
	private Integer customerSource;
	/**消费者来源(value)*/
	private String customerSourceValue;
	/**意向车型*/
	private Integer intentionVehicle;
	/**意向车型名称*/
	private String intentionVehicleName;
	/**门店id*/
	private Integer storeId;
	/**区域(城市)id*/
	private Integer cityId;
	/**未购买原因(字典id)*/
	private Integer notBuyReason;
	/**未购买原型(value)*/
	private String notBuyReasonValue;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Integer createUserId;
	/**修改人id*/
	private Integer updateUserId;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createDate;
	/**修改时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date modifyDate;
	
	/**开始搜索时间*/
	private Date beginSearchDate;
	/**截止搜索时间*/
	private Date endSearchDate;
	/** 门店编号*/
	private String storeNumber;
	/** 门店名称*/
	private String storeName;
	/** 上报类型*/
	private String reportType;
	/**门店ID集合*/
	private List<Integer> storeIdList;
	
	public Integer getNotBuyId(){
		return notBuyId;
	}
	
	public void setNotBuyId(Integer notBuyId){
		this.notBuyId = notBuyId;
	}

	public Integer getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId(Integer customerId){
		this.customerId = customerId;
	}
	public Integer getStoreId(){
		return storeId;
	}
	
	public void setStoreId(Integer storeId){
		this.storeId = storeId;
	}

	public Integer getNotBuyReason(){
		return notBuyReason;
	}
	
	public void setNotBuyReason(Integer notBuyReason){
		this.notBuyReason = notBuyReason;
	}

	public Integer getDelFlg(){
		return delFlg;
	}
	
	public void setDelFlg(Integer delFlg){
		this.delFlg = delFlg;
	}

	public Integer getCreateUserId(){
		return createUserId;
	}
	
	public void setCreateUserId(Integer createUserId){
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId(){
		return updateUserId;
	}
	
	public void setUpdateUserId(Integer updateUserId){
		this.updateUserId = updateUserId;
	}

	public Date getCreateDate(){
		return createDate;
	}
	
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getModifyDate(){
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
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

	public String getNotBuyReasonValue() {
		return notBuyReasonValue;
	}

	public void setNotBuyReasonValue(String notBuyReasonValue) {
		this.notBuyReasonValue = notBuyReasonValue;
	}

	public Integer getIntentionVehicle() {
		return intentionVehicle;
	}

	public void setIntentionVehicle(Integer intentionVehicle) {
		this.intentionVehicle = intentionVehicle;
	}

	public String getIntentionVehicleName() {
		return intentionVehicleName;
	}

	public void setIntentionVehicleName(String intentionVehicleName) {
		this.intentionVehicleName = intentionVehicleName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Date getBeginSearchDate() {
		return beginSearchDate;
	}

	public void setBeginSearchDate(Date beginSearchDate) {
		this.beginSearchDate = beginSearchDate;
	}

	public Date getEndSearchDate() {
		return endSearchDate;
	}

	public void setEndSearchDate(Date endSearchDate) {
		this.endSearchDate = endSearchDate;
	}

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

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public List<Integer> getStoreIdList() {
		return storeIdList;
	}

	public void setStoreIdList(List<Integer> storeIdList) {
		this.storeIdList = storeIdList;
	}
	

}

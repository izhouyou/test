package com.camelot.order.export.snapshot;


import com.camelot.order.export.vo.param.BaseVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hudya
 */
public class NewNotBuyVO extends BaseVO implements Serializable {

	/**主键*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long notBuyId;
	/**消费者id*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long customerId;
	/**消费者手机号*/
	private String customerPhoneNumber;
	/**消费者姓名*/
	private String customerName;
	/**消费者来源id*/
	private Integer customerSourceId;
	/**消费者来源编码*/
	private String customerSourceNumber;
	/**消费者来源名称*/
	private String customerSourceName;
	/**整车二级分类id*/
	private Integer vehicleSecondCategoryId;
	/**整车二级分类编码*/
	private String vehicleSecondCategoryNumber;
	/**整车二级分类名称*/
	private String vehicleSecondCategoryName;
	/**整车三级分类id*/
	private Integer vehicleThirdCategoryId;
	/**整车三级分类编码*/
	private String vehicleThirdCategoryNumber;
	/**整车三级分类名称*/
	private String vehicleThirdCategoryName;
	/**大区id*/
	private Integer firstOrgId;
	/**大区编号*/
	private String firstOrgNumber;
	/**大区名称*/
	private String firstOrgName;
	/**区域id*/
	private Integer secondOrgId;
	/**区域编号*/
	private String secondOrgNumber;
	/**区域名称*/
	private String secondOrgName;
	/**城市id*/
	private Integer thirdOrgId;
	/**城市编码*/
	private String thirdOrgNumber;
	/**城市名称*/
	private String thirdOrgName;
	/**合伙人id*/
	private Integer partnerId;
	/**合伙人编号*/
	private String partnerNumber;
	/**合伙人名称*/
	private String partnerName;
	/**加盟商id*/
	private Integer traderId;
	/**加盟商编号*/
	private String traderNumber;
	/**加盟商名称*/
	private String traderName;
	/**门店id*/
	private Integer storeId;
	/**门店编码*/
	private String storeNumber;
	/**门店名称*/
	private String storeName;
	/**未购买原因(字典id)*/
	private Integer notBuyReasonId;
	/**未购买原因编码*/
	private String notBuyReasonNumber;
	/**未购买原因名称*/
	private String notBuyReasonName;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Integer createUserId;
	/**创建人姓名*/
	private String createUserName;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createDate;
//	/**区域ids*/
//	private String orgStr;
//	/**门店ids*/
//	private String storeStr;
	/**区域集合*/
	private List<Integer> orgList;
	/**门店集合*/
	private List<Integer> storeList;
	/**开始搜索时间(创建时间)*/
	private Date startCreateDate;
	/**截止搜索时间(创建时间)*/
	private Date endCreateDate;

	public Long getNotBuyId() {
		return notBuyId;
	}

	public void setNotBuyId(Long notBuyId) {
		this.notBuyId = notBuyId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public Integer getCustomerSourceId() {
		return customerSourceId;
	}

	public void setCustomerSourceId(Integer customerSourceId) {
		this.customerSourceId = customerSourceId;
	}

	public String getCustomerSourceNumber() {
		return customerSourceNumber;
	}

	public void setCustomerSourceNumber(String customerSourceNumber) {
		this.customerSourceNumber = customerSourceNumber;
	}

	public String getCustomerSourceName() {
		return customerSourceName;
	}

	public void setCustomerSourceName(String customerSourceName) {
		this.customerSourceName = customerSourceName;
	}

	public Integer getVehicleSecondCategoryId() {
		return vehicleSecondCategoryId;
	}

	public void setVehicleSecondCategoryId(Integer vehicleSecondCategoryId) {
		this.vehicleSecondCategoryId = vehicleSecondCategoryId;
	}

	public String getVehicleSecondCategoryNumber() {
		return vehicleSecondCategoryNumber;
	}

	public void setVehicleSecondCategoryNumber(String vehicleSecondCategoryNumber) {
		this.vehicleSecondCategoryNumber = vehicleSecondCategoryNumber;
	}

	public String getVehicleSecondCategoryName() {
		return vehicleSecondCategoryName;
	}

	public void setVehicleSecondCategoryName(String vehicleSecondCategoryName) {
		this.vehicleSecondCategoryName = vehicleSecondCategoryName;
	}

	public Integer getVehicleThirdCategoryId() {
		return vehicleThirdCategoryId;
	}

	public void setVehicleThirdCategoryId(Integer vehicleThirdCategoryId) {
		this.vehicleThirdCategoryId = vehicleThirdCategoryId;
	}

	public String getVehicleThirdCategoryNumber() {
		return vehicleThirdCategoryNumber;
	}

	public void setVehicleThirdCategoryNumber(String vehicleThirdCategoryNumber) {
		this.vehicleThirdCategoryNumber = vehicleThirdCategoryNumber;
	}

	public String getVehicleThirdCategoryName() {
		return vehicleThirdCategoryName;
	}

	public void setVehicleThirdCategoryName(String vehicleThirdCategoryName) {
		this.vehicleThirdCategoryName = vehicleThirdCategoryName;
	}

	public Integer getFirstOrgId() {
		return firstOrgId;
	}

	public void setFirstOrgId(Integer firstOrgId) {
		this.firstOrgId = firstOrgId;
	}

	public String getFirstOrgNumber() {
		return firstOrgNumber;
	}

	public void setFirstOrgNumber(String firstOrgNumber) {
		this.firstOrgNumber = firstOrgNumber;
	}

	public String getFirstOrgName() {
		return firstOrgName;
	}

	public void setFirstOrgName(String firstOrgName) {
		this.firstOrgName = firstOrgName;
	}

	public Integer getSecondOrgId() {
		return secondOrgId;
	}

	public void setSecondOrgId(Integer secondOrgId) {
		this.secondOrgId = secondOrgId;
	}

	public String getSecondOrgNumber() {
		return secondOrgNumber;
	}

	public void setSecondOrgNumber(String secondOrgNumber) {
		this.secondOrgNumber = secondOrgNumber;
	}

	public String getSecondOrgName() {
		return secondOrgName;
	}

	public void setSecondOrgName(String secondOrgName) {
		this.secondOrgName = secondOrgName;
	}

	public Integer getThirdOrgId() {
		return thirdOrgId;
	}

	public void setThirdOrgId(Integer thirdOrgId) {
		this.thirdOrgId = thirdOrgId;
	}

	public String getThirdOrgNumber() {
		return thirdOrgNumber;
	}

	public void setThirdOrgNumber(String thirdOrgNumber) {
		this.thirdOrgNumber = thirdOrgNumber;
	}

	public String getThirdOrgName() {
		return thirdOrgName;
	}

	public void setThirdOrgName(String thirdOrgName) {
		this.thirdOrgName = thirdOrgName;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerNumber() {
		return partnerNumber;
	}

	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public String getTraderNumber() {
		return traderNumber;
	}

	public void setTraderNumber(String traderNumber) {
		this.traderNumber = traderNumber;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
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

	public Integer getNotBuyReasonId() {
		return notBuyReasonId;
	}

	public void setNotBuyReasonId(Integer notBuyReasonId) {
		this.notBuyReasonId = notBuyReasonId;
	}

	public String getNotBuyReasonNumber() {
		return notBuyReasonNumber;
	}

	public void setNotBuyReasonNumber(String notBuyReasonNumber) {
		this.notBuyReasonNumber = notBuyReasonNumber;
	}

	public String getNotBuyReasonName() {
		return notBuyReasonName;
	}

	public void setNotBuyReasonName(String notBuyReasonName) {
		this.notBuyReasonName = notBuyReasonName;
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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

//	public String getOrgStr() {
//		return orgStr;
//	}
//
//	public void setOrgStr(String orgStr) {
//		this.orgStr = orgStr;
//	}
//
//	public String getStoreStr() {
//		return storeStr;
//	}
//
//	public void setStoreStr(String storeStr) {
//		this.storeStr = storeStr;
//	}

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

	public Date getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(Date startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
}

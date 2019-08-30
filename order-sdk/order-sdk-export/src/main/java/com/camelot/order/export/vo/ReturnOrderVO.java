package com.camelot.order.export.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReturnOrderVO implements Serializable {

	/**主键*/
	private Integer returnOrderId;
	/**订单编号*/
	private String returnOrderNumber;
	/**对应销售订单ID*/
	private Integer salesOrderId;
	/**订单金额*/
	private java.math.BigDecimal returnOrderAmount;
	/**订单金额(value)*/
	private String returnOrderAmountValue;
	/**订单状态（字典id）*/
	private Integer returnOrderState;
	/**订单状态(value)*/
	private String returnOrderStateValue;
	/**退货原因(字典id)*/
	private Integer returnReason;
	/**退货原因(value)*/
	private String returnReasonValue;
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
	/**来自销售订单数据*/
	/**订单编号*/
	private String salesOrderNumber;
	/**大区id*/
	private Integer firstOrgId;
	/**大区名称*/
	private String firstOrgName;
	/**区域id*/
	private Integer secondOrgId;
	/**区域名称*/
	private String secondOrgName;
	/**城市id*/
	private Integer thirdOrgId;
	/**城市名称*/
	private String thirdOrgName;
	/**门店id*/
	private Integer storeId;
	/**门店名称*/
	private String storeName;
	/**门店编码*/
	private String storeNumber;
	/**订单来源（字典值id）*/
	private Integer salesOrderSource;
	/**订单来源(value)*/
	private String salesOrderSourceValue;
	/**商品编码(69码)*/
	private String goodsBarcode;
	/**商品SN号*/
	private String goodsSn;
	/**商品车架号*/
	private String goodsFrameNumber;
	/**销售订单ID集合*/
	private List<Integer> salesOrderIdList;
	/**消费者姓名*/
	private String customerName;
	/**消费者手机号*/
	private String customerPhoneNumber;
	/**区域id集合字符串*/
	private String orgStr;
	/**门店id集合字符串*/
	private String storeStr;
	/**查询条件-门店ID集合*/
	private String searchStoreStr;
	/**活动图片URL*/
	private String activityPicture;
	
	public Integer getReturnOrderId(){
		return returnOrderId;
	}
	
	public void setReturnOrderId(Integer returnOrderId){
		this.returnOrderId = returnOrderId;
	}

	public String getReturnOrderNumber(){
		return returnOrderNumber;
	}
	
	public void setReturnOrderNumber(String returnOrderNumber){
		this.returnOrderNumber = returnOrderNumber;
	}

	public Integer getSalesOrderId(){
		return salesOrderId;
	}
	
	public void setSalesOrderId(Integer salesOrderId){
		this.salesOrderId = salesOrderId;
	}

	public java.math.BigDecimal getReturnOrderAmount(){
		return returnOrderAmount;
	}
	
	public void setReturnOrderAmount(java.math.BigDecimal returnOrderAmount){
		this.returnOrderAmount = returnOrderAmount;
	}

	public Integer getReturnOrderState(){
		return returnOrderState;
	}
	
	public void setReturnOrderState(Integer returnOrderState){
		this.returnOrderState = returnOrderState;
	}

	public Integer getReturnReason(){
		return returnReason;
	}
	
	public void setReturnReason(Integer returnReason){
		this.returnReason = returnReason;
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

	public String getReturnOrderAmountValue() {
		return returnOrderAmountValue;
	}

	public void setReturnOrderAmountValue(String returnOrderAmountValue) {
		this.returnOrderAmountValue = returnOrderAmountValue;
	}

	public String getReturnOrderStateValue() {
		return returnOrderStateValue;
	}

	public void setReturnOrderStateValue(String returnOrderStateValue) {
		this.returnOrderStateValue = returnOrderStateValue;
	}

	public String getReturnReasonValue() {
		return returnReasonValue;
	}

	public void setReturnReasonValue(String returnReasonValue) {
		this.returnReasonValue = returnReasonValue;
	}

	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}

	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}

	public Integer getFirstOrgId() {
		return firstOrgId;
	}

	public void setFirstOrgId(Integer firstOrgId) {
		this.firstOrgId = firstOrgId;
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

	public String getThirdOrgName() {
		return thirdOrgName;
	}

	public void setThirdOrgName(String thirdOrgName) {
		this.thirdOrgName = thirdOrgName;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getSalesOrderSource() {
		return salesOrderSource;
	}

	public void setSalesOrderSource(Integer salesOrderSource) {
		this.salesOrderSource = salesOrderSource;
	}

	public String getSalesOrderSourceValue() {
		return salesOrderSourceValue;
	}

	public void setSalesOrderSourceValue(String salesOrderSourceValue) {
		this.salesOrderSourceValue = salesOrderSourceValue;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public List<Integer> getSalesOrderIdList() {
		return salesOrderIdList;
	}

	public void setSalesOrderIdList(List<Integer> salesOrderIdList) {
		this.salesOrderIdList = salesOrderIdList;
	}

	public String getGoodsBarcode() {
		return goodsBarcode;
	}

	public void setGoodsBarcode(String goodsBarcode) {
		this.goodsBarcode = goodsBarcode;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getGoodsFrameNumber() {
		return goodsFrameNumber;
	}

	public void setGoodsFrameNumber(String goodsFrameNumber) {
		this.goodsFrameNumber = goodsFrameNumber;
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

	public String getOrgStr() {
		return orgStr;
	}

	public void setOrgStr(String orgStr) {
		this.orgStr = orgStr;
	}

	public String getStoreStr() {
		return storeStr;
	}

	public void setStoreStr(String storeStr) {
		this.storeStr = storeStr;
	}

	public String getSearchStoreStr() {
		return searchStoreStr;
	}

	public void setSearchStoreStr(String searchStoreStr) {
		this.searchStoreStr = searchStoreStr;
	}

	public String getActivityPicture() {
		return activityPicture;
	}

	public void setActivityPicture(String activityPicture) {
		this.activityPicture = activityPicture;
	}
}

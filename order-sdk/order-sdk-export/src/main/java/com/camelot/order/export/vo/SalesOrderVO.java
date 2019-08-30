package com.camelot.order.export.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SalesOrderVO implements Serializable {

	/**主键*/
	private Integer salesOrderId;
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
	/**订单金额*/
	private BigDecimal salesOrderAmount;
	/**订单金额(String)*/
	private String salesOrderAmountValue;
	/**支付方式（字典值id）*/
	private Integer paymentWay;
	/**支付方式(value)*/
	private String paymentWayValue;
	/**订单来源（字典值id）*/
	private Integer salesOrderSource;
	/**订单来源(value)*/
	private String salesOrderSourceValue;
	/**订单状态（字典值id）*/
	private Integer salesOrderStatus;
	/**订单状态(value)*/
	private String salesOrderStatusValue;
	/**退货标识（字典值ID）*/
	private Integer returnFlg;
	/**顾客id*/
	private Integer customerId;
	/**消费者来源*/
	private String customerSource;
	/**活动id*/
	private Integer activityId;
	/**活动优惠码id*/
	private Integer couponId;
	/**活动图片URL*/
	private String activityPicture;
	/**付款二维码URL*/
	private String paymentQrCode;
	/**订单取消原因*/
	private Integer cancelReason;
	/**订单取消原因(value)*/
	private String cancelReasonValue;
	/**版本号*/
	private Integer salesOrderVersion;
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
	/**当前日期*/
	private Date nowDate;
	/**开始搜索时间*/
	private Date beginSearchDate;
	/**截止搜索时间*/
	private Date endSearchDate;
	/**商品编码(69码)*/
	private String goodsBarcode;
	/**商品SN号*/
	private String goodsSn;
	/**商品车架号*/
	private String goodsFrameNumber;
	/**订单id集合*/
	private List<Integer> salesOrderIdList;
	/**消费者id集合*/
	private List<Integer> customerIdList;
	/**消费者姓名*/
	private String customerName;
	/**消费者手机号*/
	private String customerPhoneNumber;
	/**流水号*/
	private Long transactionId;
	/**区域ID集合*/
	private List<Integer> orgIdList;
	/**门店ID集合*/
	private List<Integer> storeIdList;
	/**区域id集合字符串*/
	private String orgStr;
	/**门店id集合字符串*/
	private String storeStr;
	/** 门店编号*/
	private String StoreNumber;
	/**查询条件-门店ID集合*/
	private String searchStoreStr;
	/**订单状态集合*/
	private List<Integer> statusList;
	/**退货订单编号*/
	private String returnOrderNumber;
	public Integer getSalesOrderId(){
		return salesOrderId;
	}
	
	public void setSalesOrderId(Integer salesOrderId){
		this.salesOrderId = salesOrderId;
	}

	public String getSalesOrderNumber(){
		return salesOrderNumber;
	}
	
	public void setSalesOrderNumber(String salesOrderNumber){
		this.salesOrderNumber = salesOrderNumber;
	}

	public Integer getFirstOrgId(){
		return firstOrgId;
	}
	
	public void setFirstOrgId(Integer firstOrgId){
		this.firstOrgId = firstOrgId;
	}

	public Integer getSecondOrgId(){
		return secondOrgId;
	}
	
	public void setSecondOrgId(Integer secondOrgId){
		this.secondOrgId = secondOrgId;
	}

	public Integer getThirdOrgId(){
		return thirdOrgId;
	}
	
	public void setThirdOrgId(Integer thirdOrgId){
		this.thirdOrgId = thirdOrgId;
	}

	public Integer getStoreId(){
		return storeId;
	}
	
	public void setStoreId(Integer storeId){
		this.storeId = storeId;
	}

	public java.math.BigDecimal getSalesOrderAmount(){
		return salesOrderAmount;
	}
	
	public void setSalesOrderAmount(java.math.BigDecimal salesOrderAmount){
		this.salesOrderAmount = salesOrderAmount;
	}

	public Integer getPaymentWay(){
		return paymentWay;
	}
	
	public void setPaymentWay(Integer paymentWay){
		this.paymentWay = paymentWay;
	}

	public Integer getSalesOrderSource(){
		return salesOrderSource;
	}
	
	public void setSalesOrderSource(Integer salesOrderSource){
		this.salesOrderSource = salesOrderSource;
	}

	public Integer getSalesOrderStatus(){
		return salesOrderStatus;
	}
	
	public void setSalesOrderStatus(Integer salesOrderStatus){
		this.salesOrderStatus = salesOrderStatus;
	}

	public Integer getReturnFlg(){
		return returnFlg;
	}
	
	public void setReturnFlg(Integer returnFlg){
		this.returnFlg = returnFlg;
	}

	public Integer getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId(Integer customerId){
		this.customerId = customerId;
	}

	public Integer getCouponId(){
		return couponId;
	}
	
	public void setCouponId(Integer couponId){
		this.couponId = couponId;
	}

	public String getActivityPicture(){
		return activityPicture;
	}
	
	public void setActivityPicture(String activityPicture){
		this.activityPicture = activityPicture;
	}

	public String getPaymentQrCode(){
		return paymentQrCode;
	}
	
	public void setPaymentQrCode(String paymentQrCode){
		this.paymentQrCode = paymentQrCode;
	}

	public Integer getSalesOrderVersion(){
		return salesOrderVersion;
	}
	
	public void setSalesOrderVersion(Integer salesOrderVersion){
		this.salesOrderVersion = salesOrderVersion;
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

	public java.util.Date getCreateDate(){
		return createDate;
	}
	
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	public java.util.Date getModifyDate(){
		return modifyDate;
	}
	
	public void setModifyDate(java.util.Date modifyDate){
		this.modifyDate = modifyDate;
	}

	public Date getNowDate() {
		return nowDate;
	}

	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}

	public String getFirstOrgName() {
		return firstOrgName;
	}

	public void setFirstOrgName(String firstOrgName) {
		this.firstOrgName = firstOrgName;
	}

	public String getSecondOrgName() {
		return secondOrgName;
	}

	public void setSecondOrgName(String secondOrgName) {
		this.secondOrgName = secondOrgName;
	}

	public String getThirdOrgName() {
		return thirdOrgName;
	}

	public void setThirdOrgName(String thirdOrgName) {
		this.thirdOrgName = thirdOrgName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public String getSalesOrderAmountValue() {
		return salesOrderAmountValue;
	}

	public void setSalesOrderAmountValue(String salesOrderAmountValue) {
		this.salesOrderAmountValue = salesOrderAmountValue;
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

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public Integer getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(Integer cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelReasonValue() {
		return cancelReasonValue;
	}

	public void setCancelReasonValue(String cancelReasonValue) {
		this.cancelReasonValue = cancelReasonValue;
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

	public List<Integer> getSalesOrderIdList() {
		return salesOrderIdList;
	}

	public void setSalesOrderIdList(List<Integer> salesOrderIdList) {
		this.salesOrderIdList = salesOrderIdList;
	}

	public List<Integer> getCustomerIdList() {
		return customerIdList;
	}

	public void setCustomerIdList(List<Integer> customerIdList) {
		this.customerIdList = customerIdList;
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

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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

	public List<Integer> getOrgIdList() {
		return orgIdList;
	}

	public void setOrgIdList(List<Integer> orgIdList) {
		this.orgIdList = orgIdList;
	}

	public List<Integer> getStoreIdList() {
		return storeIdList;
	}

	public void setStoreIdList(List<Integer> storeIdList) {
		this.storeIdList = storeIdList;
	}

	public String getStoreNumber() {
		return StoreNumber;
	}

	public void setStoreNumber(String storeNumber) {
		StoreNumber = storeNumber;
	}

	public String getSearchStoreStr() {
		return searchStoreStr;
	}

	public void setSearchStoreStr(String searchStoreStr) {
		this.searchStoreStr = searchStoreStr;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public String getReturnOrderNumber() {
		return returnOrderNumber;
	}

	public void setReturnOrderNumber(String returnOrderNumber) {
		this.returnOrderNumber = returnOrderNumber;
	}

	
	
}

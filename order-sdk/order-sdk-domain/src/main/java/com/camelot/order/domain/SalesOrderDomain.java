package com.camelot.order.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SalesOrderDomain implements Serializable {

	/**主键*/
	private Integer salesOrderId;
	/**订单编号*/
	private String salesOrderNumber;
	/**大区id*/
	private Integer firstOrgId;
	/**区域id*/
	private Integer secondOrgId;
	/**城市id*/
	private Integer thirdOrgId;
	/**门店id*/
	private Integer storeId;
	/**订单金额*/
	private java.math.BigDecimal salesOrderAmount;
	/**支付方式（字典值id）*/
	private Integer paymentWay;
	/**订单来源（字典值id）*/
	private Integer salesOrderSource;
	/**订单状态（字典值id）*/
	private Integer salesOrderStatus;
	/**退货标识（字典值ID）*/
	private Integer returnFlg;
	/**顾客id*/
	private Integer customerId;
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
	/**版本号*/
	private Integer salesOrderVersion;
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
	/**开始搜索时间*/
	private Date beginSearchDate;
	/**截止搜索时间*/
	private Date endSearchDate;
	/**销售订单ID集合*/
	private List<Integer> salesOrderIdList;
	/**消费者id集合*/
	private List<Integer> customerIdList;
	/**流水号*/
	private Long transactionId;
	/**区域ID集合*/
	private List<Integer> orgIdList;
	/**门店ID集合*/
	private List<Integer> storeIdList;
	/**订单状态集合*/
	private List<Integer> statusList;
	
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

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
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

	public Integer getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(Integer cancelReason) {
		this.cancelReason = cancelReason;
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

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

}

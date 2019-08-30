package com.camelot.order.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hudya
 */
public class NewReturnOrderDomain implements Serializable {

	/**主键*/
	private Long returnOrderId;
	/**订单编号*/
	private String returnOrderNumber;
	/**对应退货订单ID*/
	private Long salesOrderId;
	/**对应订单编号*/
	private String salesOrderNumber;
	/**大区id*/
	private Integer firstOrgId;
	/**大区编码*/
	private String firstOrgNumber;
	/**大区名称*/
	private String firstOrgName;
	/**区域id*/
	private Integer secondOrgId;
	/**区域编码*/
	private String secondOrgNumber;
	/**区域名称*/
	private String secondOrgName;
	/**城市id*/
	private Integer thirdOrgId;
	/**城市编码*/
	private String thirdOrgNumber;
	/**城市名称*/
	private String thirdOrgName;
	/**门店id*/
	private Integer storeId;
	/**门店编码*/
	private String storeNumber;
	/**门店名称*/
	private String storeName;
	/**小牛机构id*/
	private Integer niuOrgId;
	/**小牛机构编码*/
	private String niuOrgNumber;
	/**小牛机构名称*/
	private String niuOrgName;
	/**合伙人id*/
	private Integer partnerId;
	/**合伙人编码*/
	private String partnerNumber;
	/**合伙人名称*/
	private String partnerName;
	/**加盟商id*/
	private Integer traderId;
	/**加盟商编码*/
	private String traderNumber;
	/**加盟商名称*/
	private String traderName;
	/**订单金额*/
	private java.math.BigDecimal returnOrderAmount;
	/**退单状态（待讨论)*/
	private Integer returnOrderStatusId;
	/**退单状态编码*/
	private String returnOrderStatusNumber;
	/**退单状态名称(已退货)*/
	private String returnOrderStatusName;
	/**退款状态id(待讨论)*/
	private Integer paymentStatusId;
	/**退款状态编码*/
	private String paymentStatusNumber;
	/**退款状态名称(已退款/未退款)*/
	private String paymentStatusName;
	/**退货原因(字典id)*/
	private Integer returnReasonId;
	/**退货原因编码*/
	private String returnReasonNumber;
	/**退货原因名称*/
	private String returnReasonName;
	/**消费者id*/
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
	/**活动id*/
	private Integer activityId;
	/**活动编码*/
	private String activityNumber;
	/**活动名称*/
	private String activityName;
	/**活动优惠码id*/
	private Integer couponId;
	/**活动优惠码*/
	private String couponCode;
	/**创建人id*/
	private Integer createUserId;
	/**创建人姓名*/
	private String createUserName;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建时间*/
	private Date createDate;
	/**区域集合*/
	private List<Integer> orgList;
	/**门店集合*/
	private List<Integer> storeList;
	/**订单来源（字典值id）*/
	private Integer salesOrderSourceId;
	/**订单来源编码*/
	private String salesOrderSourceNumber;
	/**订单来源名称*/
	private String salesOrderSourceName;
	/**开始搜索时间*/
	private Date beginSearchDate;
	/**截止搜索时间*/
	private Date endSearchDate;
	/**支付方式（字典值id）*/
	private Integer paymentWayId;
	/**支付方式编码*/
	private String paymentWayNumber;
	/**支付方式名称*/
	private String paymentWayName;
	/**退货订单id集合*/
	private List<Long> newReturnOrderIdList;
	/**开始搜索时间(创建时间)*/
	private Date startCreateDate;
	/**截止搜索时间(创建时间)*/
	private Date endCreateDate;
	/**门店搜索*/
	private String storeSearch;
	/**门店渠道编码*/
	private String storeChannelNumber;
	/** 对应销售订单ID集合 */
	private List<Long> salesOrderIdList;

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
	
	public Long getReturnOrderId(){
		return returnOrderId;
	}
	
	public void setReturnOrderId(Long returnOrderId){
		this.returnOrderId = returnOrderId;
	}

	public String getReturnOrderNumber(){
		return returnOrderNumber;
	}
	
	public void setReturnOrderNumber(String returnOrderNumber){
		this.returnOrderNumber = returnOrderNumber;
	}

	public Long getSalesOrderId(){
		return salesOrderId;
	}
	
	public void setSalesOrderId(Long salesOrderId){
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

	public String getFirstOrgNumber(){
		return firstOrgNumber;
	}
	
	public void setFirstOrgNumber(String firstOrgNumber){
		this.firstOrgNumber = firstOrgNumber;
	}

	public String getFirstOrgName(){
		return firstOrgName;
	}
	
	public void setFirstOrgName(String firstOrgName){
		this.firstOrgName = firstOrgName;
	}

	public Integer getSecondOrgId(){
		return secondOrgId;
	}
	
	public void setSecondOrgId(Integer secondOrgId){
		this.secondOrgId = secondOrgId;
	}

	public String getSecondOrgNumber(){
		return secondOrgNumber;
	}
	
	public void setSecondOrgNumber(String secondOrgNumber){
		this.secondOrgNumber = secondOrgNumber;
	}

	public String getSecondOrgName(){
		return secondOrgName;
	}
	
	public void setSecondOrgName(String secondOrgName){
		this.secondOrgName = secondOrgName;
	}

	public Integer getThirdOrgId(){
		return thirdOrgId;
	}
	
	public void setThirdOrgId(Integer thirdOrgId){
		this.thirdOrgId = thirdOrgId;
	}

	public String getThirdOrgNumber(){
		return thirdOrgNumber;
	}
	
	public void setThirdOrgNumber(String thirdOrgNumber){
		this.thirdOrgNumber = thirdOrgNumber;
	}

	public String getThirdOrgName(){
		return thirdOrgName;
	}
	
	public void setThirdOrgName(String thirdOrgName){
		this.thirdOrgName = thirdOrgName;
	}

	public Integer getStoreId(){
		return storeId;
	}
	
	public void setStoreId(Integer storeId){
		this.storeId = storeId;
	}

	public String getStoreNumber(){
		return storeNumber;
	}
	
	public void setStoreNumber(String storeNumber){
		this.storeNumber = storeNumber;
	}

	public String getStoreName(){
		return storeName;
	}
	
	public void setStoreName(String storeName){
		this.storeName = storeName;
	}

	public Integer getNiuOrgId(){
		return niuOrgId;
	}
	
	public void setNiuOrgId(Integer niuOrgId){
		this.niuOrgId = niuOrgId;
	}

	public String getNiuOrgNumber(){
		return niuOrgNumber;
	}
	
	public void setNiuOrgNumber(String niuOrgNumber){
		this.niuOrgNumber = niuOrgNumber;
	}

	public String getNiuOrgName(){
		return niuOrgName;
	}
	
	public void setNiuOrgName(String niuOrgName){
		this.niuOrgName = niuOrgName;
	}

	public Integer getPartnerId(){
		return partnerId;
	}
	
	public void setPartnerId(Integer partnerId){
		this.partnerId = partnerId;
	}

	public String getPartnerNumber(){
		return partnerNumber;
	}
	
	public void setPartnerNumber(String partnerNumber){
		this.partnerNumber = partnerNumber;
	}

	public String getPartnerName(){
		return partnerName;
	}
	
	public void setPartnerName(String partnerName){
		this.partnerName = partnerName;
	}

	public Integer getTraderId(){
		return traderId;
	}
	
	public void setTraderId(Integer traderId){
		this.traderId = traderId;
	}

	public String getTraderNumber(){
		return traderNumber;
	}
	
	public void setTraderNumber(String traderNumber){
		this.traderNumber = traderNumber;
	}

	public String getTraderName(){
		return traderName;
	}
	
	public void setTraderName(String traderName){
		this.traderName = traderName;
	}

	public java.math.BigDecimal getReturnOrderAmount(){
		return returnOrderAmount;
	}
	
	public void setReturnOrderAmount(java.math.BigDecimal returnOrderAmount){
		this.returnOrderAmount = returnOrderAmount;
	}

	public Integer getReturnOrderStatusId(){
		return returnOrderStatusId;
	}
	
	public void setReturnOrderStatusId(Integer returnOrderStatusId){
		this.returnOrderStatusId = returnOrderStatusId;
	}

	public String getReturnOrderStatusNumber(){
		return returnOrderStatusNumber;
	}
	
	public void setReturnOrderStatusNumber(String returnOrderStatusNumber){
		this.returnOrderStatusNumber = returnOrderStatusNumber;
	}

	public String getReturnOrderStatusName(){
		return returnOrderStatusName;
	}
	
	public void setReturnOrderStatusName(String returnOrderStatusName){
		this.returnOrderStatusName = returnOrderStatusName;
	}

	public Integer getPaymentStatusId(){
		return paymentStatusId;
	}
	
	public void setPaymentStatusId(Integer paymentStatusId){
		this.paymentStatusId = paymentStatusId;
	}

	public String getPaymentStatusNumber(){
		return paymentStatusNumber;
	}
	
	public void setPaymentStatusNumber(String paymentStatusNumber){
		this.paymentStatusNumber = paymentStatusNumber;
	}

	public String getPaymentStatusName(){
		return paymentStatusName;
	}
	
	public void setPaymentStatusName(String paymentStatusName){
		this.paymentStatusName = paymentStatusName;
	}

	public Integer getReturnReasonId(){
		return returnReasonId;
	}
	
	public void setReturnReasonId(Integer returnReasonId){
		this.returnReasonId = returnReasonId;
	}

	public String getReturnReasonNumber(){
		return returnReasonNumber;
	}
	
	public void setReturnReasonNumber(String returnReasonNumber){
		this.returnReasonNumber = returnReasonNumber;
	}

	public String getReturnReasonName(){
		return returnReasonName;
	}
	
	public void setReturnReasonName(String returnReasonName){
		this.returnReasonName = returnReasonName;
	}

	public Long getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}

	public String getCustomerPhoneNumber(){
		return customerPhoneNumber;
	}
	
	public void setCustomerPhoneNumber(String customerPhoneNumber){
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public String getCustomerName(){
		return customerName;
	}
	
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}

	public Integer getCustomerSourceId(){
		return customerSourceId;
	}
	
	public void setCustomerSourceId(Integer customerSourceId){
		this.customerSourceId = customerSourceId;
	}

	public String getCustomerSourceNumber(){
		return customerSourceNumber;
	}
	
	public void setCustomerSourceNumber(String customerSourceNumber){
		this.customerSourceNumber = customerSourceNumber;
	}

	public String getCustomerSourceName(){
		return customerSourceName;
	}
	
	public void setCustomerSourceName(String customerSourceName){
		this.customerSourceName = customerSourceName;
	}

	public Integer getActivityId(){
		return activityId;
	}
	
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}

	public String getActivityNumber(){
		return activityNumber;
	}
	
	public void setActivityNumber(String activityNumber){
		this.activityNumber = activityNumber;
	}

	public String getActivityName(){
		return activityName;
	}
	
	public void setActivityName(String activityName){
		this.activityName = activityName;
	}

	public Integer getCouponId(){
		return couponId;
	}
	
	public void setCouponId(Integer couponId){
		this.couponId = couponId;
	}

	public String getCouponCode(){
		return couponCode;
	}
	
	public void setCouponCode(String couponCode){
		this.couponCode = couponCode;
	}

	public Integer getCreateUserId(){
		return createUserId;
	}
	
	public void setCreateUserId(Integer createUserId){
		this.createUserId = createUserId;
	}

	public String getCreateUserName(){
		return createUserName;
	}
	
	public void setCreateUserName(String createUserName){
		this.createUserName = createUserName;
	}

	public Integer getDelFlg(){
		return delFlg;
	}
	
	public void setDelFlg(Integer delFlg){
		this.delFlg = delFlg;
	}

	public java.util.Date getCreateDate(){
		return createDate;
	}
	
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	public Integer getSalesOrderSourceId() {
		return salesOrderSourceId;
	}

	public void setSalesOrderSourceId(Integer salesOrderSourceId) {
		this.salesOrderSourceId = salesOrderSourceId;
	}

	public String getSalesOrderSourceNumber() {
		return salesOrderSourceNumber;
	}

	public void setSalesOrderSourceNumber(String salesOrderSourceNumber) {
		this.salesOrderSourceNumber = salesOrderSourceNumber;
	}

	public String getSalesOrderSourceName() {
		return salesOrderSourceName;
	}

	public void setSalesOrderSourceName(String salesOrderSourceName) {
		this.salesOrderSourceName = salesOrderSourceName;
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

	public Integer getPaymentWayId() {
		return paymentWayId;
	}

	public void setPaymentWayId(Integer paymentWayId) {
		this.paymentWayId = paymentWayId;
	}

	public String getPaymentWayNumber() {
		return paymentWayNumber;
	}

	public void setPaymentWayNumber(String paymentWayNumber) {
		this.paymentWayNumber = paymentWayNumber;
	}

	public String getPaymentWayName() {
		return paymentWayName;
	}

	public void setPaymentWayName(String paymentWayName) {
		this.paymentWayName = paymentWayName;
	}

	public List<Long> getNewReturnOrderIdList() {
		return newReturnOrderIdList;
	}

	public void setNewReturnOrderIdList(List<Long> newReturnOrderIdList) {
		this.newReturnOrderIdList = newReturnOrderIdList;
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

	public String getStoreSearch() {
		return storeSearch;
	}

	public void setStoreSearch(String storeSearch) {
		this.storeSearch = storeSearch;
	}

	public String getStoreChannelNumber() {
		return storeChannelNumber;
	}

	public void setStoreChannelNumber(String storeChannelNumber) {
		this.storeChannelNumber = storeChannelNumber;
	}

	public List<Long> getSalesOrderIdList() {
		return salesOrderIdList;
	}

	public void setSalesOrderIdList(List<Long> salesOrderIdList) {
		this.salesOrderIdList = salesOrderIdList;
	}
}

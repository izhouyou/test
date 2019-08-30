package com.camelot.order.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hudya
 */
public class NewReceiptRecordDomain implements Serializable {

	/**主键id*/
	private Long receiptRecordId;
	/**收款单号*/
	private String receiptRecordNumber;
	/**支付流水号*/
	private String receiptSerialNumber;
	/**订单ID*/
	private Long salesOrderId;
	/**销售订单编号*/
	private String salesOrderNumber;
	/**订单金额*/
	private java.math.BigDecimal receiptAmount;
	/**门店id*/
	private Integer storeId;
	/**门店编码*/
	private String storeNumber;
	/**门店名称*/
	private String storeName;
	/**支付渠道（字典id）*/
	private Integer paymentChannelId;
	/**支付渠道编码*/
	private String paymentChannelNumber;
	/**支付渠道名称*/
	private String paymentChannelName;
	/**支付状态(字典id）*/
	private Integer paymentStatusId;
	/**支付状态编码*/
	private String paymentStatusNumber;
	/**支付状态名称(未支付/支付中/支付失败/已支付)*/
	private String paymentStatusName;
	/**支付方式id*/
	private Integer paymentWayId;
	/**支付方式编码*/
	private String paymentWayNumber;
	/**支付方式名称*/
	private String paymentWayName;
	/**订单来源id*/
	private Integer salesOrderResourceId;
	/**订单来源编码*/
	private String salesOrderResourceNumber;
	/**订单来源名称*/
	private String salesOrderResourceName;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Integer createUserId;
	/**创建人名称*/
	private String createUserName;
	/**修改人id*/
	private Integer updateUserId;
	/**修改人名称*/
	private String updateUserName;
	/**创建时间*/
	private Date createDate;
	/**修改时间*/
	private Date modifyDate;
	/**区域ids*/
	private String orgStr;
	/**门店ids*/
	private String storeStr;
	/**区域id集合*/
	private List<Integer> orgList;
	/**门店id集合*/
	private List<Integer> storeList;
	/**开始搜索时间*/
	private Date beginSearchDate;
	/**截止搜索时间*/
	private Date endSearchDate;
	/**区域搜索条件*/
	private String orgArrStr;
	/**城市id*/
	private Integer thirdOrgId;
	/**城市编码*/
	private String thirdOrgNumber;
	/**城市名称*/
	private String thirdOrgName;
	/**门店搜索*/
	private String storeSearch;
	/** 单号搜索 */
	private String numberSearch;
	
	public Long getReceiptRecordId(){
		return receiptRecordId;
	}
	
	public void setReceiptRecordId(Long receiptRecordId){
		this.receiptRecordId = receiptRecordId;
	}

	public String getReceiptRecordNumber(){
		return receiptRecordNumber;
	}
	
	public void setReceiptRecordNumber(String receiptRecordNumber){
		this.receiptRecordNumber = receiptRecordNumber;
	}

	public String getReceiptSerialNumber(){
		return receiptSerialNumber;
	}
	
	public void setReceiptSerialNumber(String receiptSerialNumber){
		this.receiptSerialNumber = receiptSerialNumber;
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

	public java.math.BigDecimal getReceiptAmount(){
		return receiptAmount;
	}
	
	public void setReceiptAmount(java.math.BigDecimal receiptAmount){
		this.receiptAmount = receiptAmount;
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

	public Integer getPaymentChannelId(){
		return paymentChannelId;
	}
	
	public void setPaymentChannelId(Integer paymentChannelId){
		this.paymentChannelId = paymentChannelId;
	}

	public String getPaymentChannelNumber(){
		return paymentChannelNumber;
	}
	
	public void setPaymentChannelNumber(String paymentChannelNumber){
		this.paymentChannelNumber = paymentChannelNumber;
	}

	public String getPaymentChannelName(){
		return paymentChannelName;
	}
	
	public void setPaymentChannelName(String paymentChannelName){
		this.paymentChannelName = paymentChannelName;
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

	public Integer getPaymentWayId(){
		return paymentWayId;
	}
	
	public void setPaymentWayId(Integer paymentWayId){
		this.paymentWayId = paymentWayId;
	}

	public String getPaymentWayNumber(){
		return paymentWayNumber;
	}
	
	public void setPaymentWayNumber(String paymentWayNumber){
		this.paymentWayNumber = paymentWayNumber;
	}

	public String getPaymentWayName(){
		return paymentWayName;
	}
	
	public void setPaymentWayName(String paymentWayName){
		this.paymentWayName = paymentWayName;
	}

	public Integer getSalesOrderResourceId(){
		return salesOrderResourceId;
	}
	
	public void setSalesOrderResourceId(Integer salesOrderResourceId){
		this.salesOrderResourceId = salesOrderResourceId;
	}

	public String getSalesOrderResourceNumber(){
		return salesOrderResourceNumber;
	}
	
	public void setSalesOrderResourceNumber(String salesOrderResourceNumber){
		this.salesOrderResourceNumber = salesOrderResourceNumber;
	}

	public String getSalesOrderResourceName(){
		return salesOrderResourceName;
	}
	
	public void setSalesOrderResourceName(String salesOrderResourceName){
		this.salesOrderResourceName = salesOrderResourceName;
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

	public String getCreateUserName(){
		return createUserName;
	}
	
	public void setCreateUserName(String createUserName){
		this.createUserName = createUserName;
	}

	public Integer getUpdateUserId(){
		return updateUserId;
	}
	
	public void setUpdateUserId(Integer updateUserId){
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName(){
		return updateUserName;
	}
	
	public void setUpdateUserName(String updateUserName){
		this.updateUserName = updateUserName;
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

	public String getOrgArrStr() {
		return orgArrStr;
	}

	public void setOrgArrStr(String orgArrStr) {
		this.orgArrStr = orgArrStr;
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

	public String getStoreSearch() {
		return storeSearch;
	}

	public void setStoreSearch(String storeSearch) {
		this.storeSearch = storeSearch;
	}

	public String getNumberSearch() {
		return numberSearch;
	}

	public void setNumberSearch(String numberSearch) {
		this.numberSearch = numberSearch;
	}
}

package com.camelot.order.export.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReceiptRecordVO implements Serializable{

	/**主键id*/
	private Integer receiptRecordId;
	/**收款单号*/
	private String receiptRecordNumber;
	/**支付流水号*/
	private String receiptSerialNumber;
	/**订单ID*/
	private Integer salesOrderId;
	/**订单编号*/
	private String salesOrderNumber;
	/**门店名称*/
	private String storeName;
	/**订单金额*/
	private java.math.BigDecimal receiptAmount;
	/**订单金额*/
	private String receiptAmountValue;
	/**支付方式*/
	private Integer paymentWay;
	/**支付方式*/
	private String paymentWayValue;
	/**支付渠道（字典id）*/
	private Integer paymentChannel;
	/**支付渠道*/
	private String paymentChannelValue;
	/**支付状态(字典id）*/
	private Integer paymentStatus;
	/**支付状态(value)*/
	private String paymentStatusValue;
	/**订单来源*/
	private Integer salesOrderSource;
	/**订单来源*/
	private String  salesOrderSourceValue;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Integer createUserId;
	/**修改人id*/
	private Integer updateUserId;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**修改时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyDate;
	/**门店ID*/
	private Integer storeId;
	/**门店编码*/
	private String storeNumber;
	/**开始搜索时间*/
	private Date beginSearchDate;
	/**截止搜索时间*/
	private Date endSearchDate;
	/**销售订单ID集合*/
	private List<Integer> salesOrderIdList; 
	/**区域id集合字符串*/
	private String orgStr;
	/**门店id集合字符串*/
	private String storeStr;
	/**查询条件-门店ID集合*/
	private String searchStoreStr;
	public Integer getReceiptRecordId(){
		return receiptRecordId;
	}
	
	public void setReceiptRecordId(Integer receiptRecordId){
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

	public Integer getSalesOrderId(){
		return salesOrderId;
	}
	
	public void setSalesOrderId(Integer salesOrderId){
		this.salesOrderId = salesOrderId;
	}

	public java.math.BigDecimal getReceiptAmount(){
		return receiptAmount;
	}
	
	public void setReceiptAmount(java.math.BigDecimal receiptAmount){
		this.receiptAmount = receiptAmount;
	}

	public Integer getPaymentChannel(){
		return paymentChannel;
	}
	
	public void setPaymentChannel(Integer paymentChannel){
		this.paymentChannel = paymentChannel;
	}

	public Integer getPaymentStatus(){
		return paymentStatus;
	}
	
	public void setPaymentStatus(Integer paymentStatus){
		this.paymentStatus = paymentStatus;
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

	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}

	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
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

	public String getReceiptAmountValue() {
		return receiptAmountValue;
	}

	public void setReceiptAmountValue(String receiptAmountValue) {
		this.receiptAmountValue = receiptAmountValue;
	}

	public String getPaymentWayValue() {
		return paymentWayValue;
	}

	public void setPaymentWayValue(String paymentWayValue) {
		this.paymentWayValue = paymentWayValue;
	}

	public String getPaymentChannelValue() {
		return paymentChannelValue;
	}

	public void setPaymentChannelValue(String paymentChannelValue) {
		this.paymentChannelValue = paymentChannelValue;
	}

	public String getPaymentStatusValue() {
		return paymentStatusValue;
	}

	public void setPaymentStatusValue(String paymentStatusValue) {
		this.paymentStatusValue = paymentStatusValue;
	}

	public String getSalesOrderSourceValue() {
		return salesOrderSourceValue;
	}

	public void setSalesOrderSourceValue(String salesOrderSourceValue) {
		this.salesOrderSourceValue = salesOrderSourceValue;
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

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(Integer paymentWay) {
		this.paymentWay = paymentWay;
	}

	public Integer getSalesOrderSource() {
		return salesOrderSource;
	}

	public void setSalesOrderSource(Integer salesOrderSource) {
		this.salesOrderSource = salesOrderSource;
	}

	public List<Integer> getSalesOrderIdList() {
		return salesOrderIdList;
	}

	public void setSalesOrderIdList(List<Integer> salesOrderIdList) {
		this.salesOrderIdList = salesOrderIdList;
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
	
	

}

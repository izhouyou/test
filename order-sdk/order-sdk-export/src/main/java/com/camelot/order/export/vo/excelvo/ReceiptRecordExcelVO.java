package com.camelot.order.export.vo.excelvo;

import java.util.Date;

import com.camelot.order.common.utils.excel.ExcelField;

public class ReceiptRecordExcelVO {

	/**主键id*/
	private Integer receiptRecordId;
	/**收款单号*/
	@ExcelField(title = "收款单号", sort = 2 )
	private String receiptRecordNumber;
	/**支付流水号*/
	@ExcelField(title = "支付流水号", sort = 3 )
	private String receiptSerialNumber;
	/**订单编号*/
	@ExcelField(title = "订单编号", sort = 4 )
	private String salesOrderNumber;
	/**门店名称*/
	@ExcelField(title = "门店名称", sort = 6 )
	private String storeName;
	/**订单金额*/
	private java.math.BigDecimal receiptAmount;
	/**订单金额*/
	@ExcelField(title = "订单金额", sort = 7 )
	private String receiptAmountValue;
	/**支付方式*/
	@ExcelField(title = "支付方式", sort = 8 )
	private String paymentWayValue;
	/**支付渠道（字典id）*/
	private Integer paymentChannel;
	/**支付渠道*/
	@ExcelField(title = "支付渠道", sort = 9 )
	private String paymentChannelValue;
	/**支付状态(字典id）*/
	private Integer paymentStatus;
	/**支付状态(value)*/
	@ExcelField(title = "支付状态", sort = 11 )
	private String paymentStatusValue;
	/**订单来源*/
	@ExcelField(title = "订单来源", sort = 10 )
	private String  salesOrderSourceValue;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Integer createUserId;
	/**修改人id*/
	private Integer updateUserId;
	/**创建时间*/
	@ExcelField(title = "提交时间", sort = 5 )
	private Date createDate;
	/**修改时间*/
	private Date modifyDate;
	/**门店编码*/
	private String storeNumber;
	/**开始搜索时间*/
	private Date beginSearchDate;
	/**截止搜索时间*/
	private Date endSearchDate;
	
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
	
	

}

package com.camelot.order.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReceiptRecordDomain implements Serializable{

	/**主键id*/
	private Integer receiptRecordId;
	/**收款单号*/
	private String receiptRecordNumber;
	/**支付流水号*/
	private String receiptSerialNumber;
	/**订单ID*/
	private Integer salesOrderId;
	/**订单金额*/
	private java.math.BigDecimal receiptAmount;
	/**支付渠道（字典id）*/
	private Integer paymentChannel;
	/**支付状态(字典id）*/
	private Integer paymentStatus;
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

	public List<Integer> getSalesOrderIdList() {
		return salesOrderIdList;
	}

	public void setSalesOrderIdList(List<Integer> salesOrderIdList) {
		this.salesOrderIdList = salesOrderIdList;
	}


}

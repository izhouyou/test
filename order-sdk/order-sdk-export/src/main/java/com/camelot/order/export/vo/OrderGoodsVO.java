package com.camelot.order.export.vo;

import java.io.Serializable;

public class OrderGoodsVO implements Serializable {

	/**主键id*/
	private Integer orderGoodsId;
	/**订单id*/
	private Integer orderId;
	/**商品id*/
	private Integer goodsId;
	/**商品sn码*/
	private String goodsSn;
	/**整车车架号*/
	private String goodsFrameNumber;
	/**下单数量*/
	private Integer orderAmount;
	/**退单数量*/
	private Integer returnAmount;
	/**零售指导价*/
	private java.math.BigDecimal retailPrice;
	/**商品实销单价*/
	private java.math.BigDecimal actualPrice;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Integer createUserId;
	/**修改人id*/
	private Integer updateUserId;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改时间*/
	private java.util.Date modifyDate;
	/**订单号*/
	private String salesOrderNumber;
	/**订单状态*/
	private String salesOrderStatusValue;
	
	public Integer getOrderGoodsId(){
		return orderGoodsId;
	}
	
	public void setOrderGoodsId(Integer orderGoodsId){
		this.orderGoodsId = orderGoodsId;
	}

	public Integer getOrderId(){
		return orderId;
	}
	
	public void setOrderId(Integer orderId){
		this.orderId = orderId;
	}

	public Integer getGoodsId(){
		return goodsId;
	}
	
	public void setGoodsId(Integer goodsId){
		this.goodsId = goodsId;
	}

	public String getGoodsSn(){
		return goodsSn;
	}
	
	public void setGoodsSn(String goodsSn){
		this.goodsSn = goodsSn;
	}

	public String getGoodsFrameNumber(){
		return goodsFrameNumber;
	}
	
	public void setGoodsFrameNumber(String goodsFrameNumber){
		this.goodsFrameNumber = goodsFrameNumber;
	}

	public Integer getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Integer orderAmount){
		this.orderAmount = orderAmount;
	}

	public Integer getReturnAmount(){
		return returnAmount;
	}
	
	public void setReturnAmount(Integer returnAmount){
		this.returnAmount = returnAmount;
	}

	public java.math.BigDecimal getRetailPrice(){
		return retailPrice;
	}
	
	public void setRetailPrice(java.math.BigDecimal retailPrice){
		this.retailPrice = retailPrice;
	}

	public java.math.BigDecimal getActualPrice(){
		return actualPrice;
	}
	
	public void setActualPrice(java.math.BigDecimal actualPrice){
		this.actualPrice = actualPrice;
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

	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}

	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}

	public String getSalesOrderStatusValue() {
		return salesOrderStatusValue;
	}

	public void setSalesOrderStatusValue(String salesOrderStatusValue) {
		this.salesOrderStatusValue = salesOrderStatusValue;
	}

}

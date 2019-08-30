package com.camelot.order.export.vo;

import java.io.Serializable;

public class StatisticsReturnOrderVO implements Serializable {

	/**主键id*/
	private Integer statisticsReturnId;
	/**门店id*/
	private Integer storeId;
	/**门店名称*/
	private String storeName;
	/**订单id*/
	private Integer salesOrderId;
	/**退单数量*/
	private Integer returnAmount;
	/**退单id*/
	private Integer returnOrderId;
	/**退单时间*/
	private java.util.Date returnDate;
	/**退款金额*/
	private java.math.BigDecimal returnOrderAmount;
	/**创建人id*/
	private Integer createUserId;
	/**修改人id*/
	private Integer updateUserId;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改时间*/
	private java.util.Date modifyDate;
	/** 大区*/
	private Integer firstOrgId;
	/** 区域*/
	private Integer secondOrgId;
	/** 城市*/
	private Integer thirdOrgId;
	
	public Integer getStatisticsReturnId(){
		return statisticsReturnId;
	}
	
	public void setStatisticsReturnId(Integer statisticsReturnId){
		this.statisticsReturnId = statisticsReturnId;
	}

	public Integer getStoreId(){
		return storeId;
	}
	
	public void setStoreId(Integer storeId){
		this.storeId = storeId;
	}

	public String getStoreName(){
		return storeName;
	}
	
	public void setStoreName(String storeName){
		this.storeName = storeName;
	}

	public Integer getSalesOrderId(){
		return salesOrderId;
	}
	
	public void setSalesOrderId(Integer salesOrderId){
		this.salesOrderId = salesOrderId;
	}

	public Integer getReturnAmount(){
		return returnAmount;
	}
	
	public void setReturnAmount(Integer returnAmount){
		this.returnAmount = returnAmount;
	}

	public Integer getReturnOrderId(){
		return returnOrderId;
	}
	
	public void setReturnOrderId(Integer returnOrderId){
		this.returnOrderId = returnOrderId;
	}

	public java.util.Date getReturnDate(){
		return returnDate;
	}
	
	public void setReturnDate(java.util.Date returnDate){
		this.returnDate = returnDate;
	}

	public java.math.BigDecimal getReturnOrderAmount(){
		return returnOrderAmount;
	}
	
	public void setReturnOrderAmount(java.math.BigDecimal returnOrderAmount){
		this.returnOrderAmount = returnOrderAmount;
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

	public Integer getFirstOrgId() {
		return firstOrgId;
	}

	public void setFirstOrgId(Integer firstOrgId) {
		this.firstOrgId = firstOrgId;
	}

	public Integer getSecondOrgId() {
		return secondOrgId;
	}

	public void setSecondOrgId(Integer secondOrgId) {
		this.secondOrgId = secondOrgId;
	}

	public Integer getThirdOrgId() {
		return thirdOrgId;
	}

	public void setThirdOrgId(Integer thirdOrgId) {
		this.thirdOrgId = thirdOrgId;
	}

}

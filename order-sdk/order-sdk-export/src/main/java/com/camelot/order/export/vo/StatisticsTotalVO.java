package com.camelot.order.export.vo;

import java.io.Serializable;

public class StatisticsTotalVO implements Serializable {

	/**主键id*/
	private Integer statisticsId;
	/**门店id*/
	private Integer storeId;
	/**门店名称*/
	private String storeName;
	/**大区*/
	private Integer firstOrgId;
	/**区域*/
	private Integer secondOrgId;
	/**城市*/
	private Integer thirdOrgId;
	/**大区名称*/
	private String firstOrgName;
	/**区域名称*/
	private String secondOrgName;
	/**城市名称*/
	private String thirdOrgName;
	/**合伙人简称*/
	private String partnerName;
	/**加盟商简称*/
	private String franchiseeName;
	/**合伙人id*/
	private Integer partnerId;
	/**加盟商id*/
	private Integer franchiseeId;
	/**一级分类*/
	private Integer goodsFirstCategoryId;
	/**二级分类*/
	private Integer goodsSecondCategoryId;
	/**三级分类*/
	private Integer goodsThirdCategoryId;
	/**一级分类名称*/
	private String goodsFirstCategoryName;
	/**二级分类名称*/
	private String goodsSecondCategoryName;
	/**三级分类名称*/
	private String goodsThirdCategoryName;
	/**商品名称*/
	private String goodsName;
	/**订单id*/
	private Integer salesOrderId;
	/**订单时间*/
	private java.util.Date orderCreateDate;
	/**订单修改时间*/
	private java.util.Date orderUpdateDate;
	/**指导零售价*/
	private java.math.BigDecimal retailPrice;
	/**实销单价*/
	private java.math.BigDecimal actualPrice;
	/**下单数量*/
	private Integer orderAmount;
	/**消费者来源id*/
	private Integer customerSourceId;
	/**消费者来源*/
	private String customerSource;
	/**订单状态*/
	private Integer salesOrderStatus;
	/**创建人id*/
	private Integer createUserId;
	/**修改人id*/
	private Integer updateUserId;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改时间*/
	private java.util.Date modifyDate;
	/**实销总额*/
	private java.math.BigDecimal totalPrice;
	/**属性id*/
	private Integer goodsAttributeId;
	/**属性名称*/
	private String goodsAttributeName;
	
	public Integer getStatisticsId(){
		return statisticsId;
	}
	
	public void setStatisticsId(Integer statisticsId){
		this.statisticsId = statisticsId;
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

	public String getFirstOrgName(){
		return firstOrgName;
	}
	
	public void setFirstOrgName(String firstOrgName){
		this.firstOrgName = firstOrgName;
	}

	public String getSecondOrgName(){
		return secondOrgName;
	}
	
	public void setSecondOrgName(String secondOrgName){
		this.secondOrgName = secondOrgName;
	}

	public String getThirdOrgName(){
		return thirdOrgName;
	}
	
	public void setThirdOrgName(String thirdOrgName){
		this.thirdOrgName = thirdOrgName;
	}

	public String getPartnerName(){
		return partnerName;
	}
	
	public void setPartnerName(String partnerName){
		this.partnerName = partnerName;
	}

	public String getFranchiseeName(){
		return franchiseeName;
	}
	
	public void setFranchiseeName(String franchiseeName){
		this.franchiseeName = franchiseeName;
	}

	public Integer getPartnerId(){
		return partnerId;
	}
	
	public void setPartnerId(Integer partnerId){
		this.partnerId = partnerId;
	}

	public Integer getFranchiseeId(){
		return franchiseeId;
	}
	
	public void setFranchiseeId(Integer franchiseeId){
		this.franchiseeId = franchiseeId;
	}

	public Integer getGoodsFirstCategoryId(){
		return goodsFirstCategoryId;
	}
	
	public void setGoodsFirstCategoryId(Integer goodsFirstCategoryId){
		this.goodsFirstCategoryId = goodsFirstCategoryId;
	}

	public Integer getGoodsSecondCategoryId(){
		return goodsSecondCategoryId;
	}
	
	public void setGoodsSecondCategoryId(Integer goodsSecondCategoryId){
		this.goodsSecondCategoryId = goodsSecondCategoryId;
	}

	public Integer getGoodsThirdCategoryId(){
		return goodsThirdCategoryId;
	}
	
	public void setGoodsThirdCategoryId(Integer goodsThirdCategoryId){
		this.goodsThirdCategoryId = goodsThirdCategoryId;
	}

	public String getGoodsFirstCategoryName(){
		return goodsFirstCategoryName;
	}
	
	public void setGoodsFirstCategoryName(String goodsFirstCategoryName){
		this.goodsFirstCategoryName = goodsFirstCategoryName;
	}

	public String getGoodsSecondCategoryName(){
		return goodsSecondCategoryName;
	}
	
	public void setGoodsSecondCategoryName(String goodsSecondCategoryName){
		this.goodsSecondCategoryName = goodsSecondCategoryName;
	}

	public String getGoodsThirdCategoryName(){
		return goodsThirdCategoryName;
	}
	
	public void setGoodsThirdCategoryName(String goodsThirdCategoryName){
		this.goodsThirdCategoryName = goodsThirdCategoryName;
	}

	public String getGoodsName(){
		return goodsName;
	}
	
	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}

	public Integer getSalesOrderId(){
		return salesOrderId;
	}
	
	public void setSalesOrderId(Integer salesOrderId){
		this.salesOrderId = salesOrderId;
	}

	public java.util.Date getOrderCreateDate(){
		return orderCreateDate;
	}
	
	public void setOrderCreateDate(java.util.Date orderCreateDate){
		this.orderCreateDate = orderCreateDate;
	}

	public java.util.Date getOrderUpdateDate(){
		return orderUpdateDate;
	}
	
	public void setOrderUpdateDate(java.util.Date orderUpdateDate){
		this.orderUpdateDate = orderUpdateDate;
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

	public Integer getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Integer orderAmount){
		this.orderAmount = orderAmount;
	}

	public Integer getCustomerSourceId(){
		return customerSourceId;
	}
	
	public void setCustomerSourceId(Integer customerSourceId){
		this.customerSourceId = customerSourceId;
	}

	public String getCustomerSource(){
		return customerSource;
	}
	
	public void setCustomerSource(String customerSource){
		this.customerSource = customerSource;
	}

	public Integer getSalesOrderStatus(){
		return salesOrderStatus;
	}
	
	public void setSalesOrderStatus(Integer salesOrderStatus){
		this.salesOrderStatus = salesOrderStatus;
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

	public java.math.BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(java.math.BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getGoodsAttributeId() {
		return goodsAttributeId;
	}

	public void setGoodsAttributeId(Integer goodsAttributeId) {
		this.goodsAttributeId = goodsAttributeId;
	}

	public String getGoodsAttributeName() {
		return goodsAttributeName;
	}

	public void setGoodsAttributeName(String goodsAttributeName) {
		this.goodsAttributeName = goodsAttributeName;
	}

}

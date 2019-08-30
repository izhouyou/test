package com.camelot.order.export.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Description: [门店销售报表]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName SalesReportVO.java
 * Created on 2019年4月8日.
 */
public class NewSalesReportVO implements Serializable {
	
	/**主键*/
	private String salesOrderId;
	/**订单编号*/
	private String salesOrderNumber;
	/**大区名称*/
	private String firstOrgName;
	/**区域名称*/
	private String secondOrgName;
	/**城市名称*/
	private String thirdOrgName;
	/**门店编号*/
	private String storeNumber;
	/**门店渠道编号*/
	private String storeChannelNumber;
	/**门店名称*/
	private String storeName;
	/**合伙人编号*/
	private String partnerNumber;
	/**订单状态名称*/
	private String salesOrderStatusName;
	/**付款状态名称*/
	private String paymentStatusName;
	/**退货状态名称(已退货;未退货)*/
	private String returnStatusName;
	/** 商品SN */
	private String goodsSn;
	/** 车架号 */
	private String goodsFrameNumber;
	/** 商品名称 */
	private String goodsName;
	/** 商品三级级分类名称 */
	private String thirdCategoryName;
	/** 商品实销单价 */
	private String actualPriceValue;
	/** 消费者来源名称 */
	private String customerSourceName;
	/** 创建日期 */
	private Date createDate;
	/** 修改时间 */
	private Date modifyDate;
	/** 退货订单编号*/
	private String returnOrderNumber;
	/**活动名称*/
	private String activityName;
	/**活动优惠码*/
	private String couponCode;
	/** 备注 */
	private String orderRemark;
	
	public String getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
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
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	public String getStoreChannelNumber() {
		return storeChannelNumber;
	}
	public void setStoreChannelNumber(String storeChannelNumber) {
		this.storeChannelNumber = storeChannelNumber;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getPartnerNumber() {
		return partnerNumber;
	}
	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}
	public String getSalesOrderStatusName() {
		return salesOrderStatusName;
	}
	public void setSalesOrderStatusName(String salesOrderStatusName) {
		this.salesOrderStatusName = salesOrderStatusName;
	}
	public String getPaymentStatusName() {
		return paymentStatusName;
	}
	public void setPaymentStatusName(String paymentStatusName) {
		this.paymentStatusName = paymentStatusName;
	}
	public String getReturnStatusName() {
		return returnStatusName;
	}
	public void setReturnStatusName(String returnStatusName) {
		this.returnStatusName = returnStatusName;
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
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getThirdCategoryName() {
		return thirdCategoryName;
	}
	public void setThirdCategoryName(String thirdCategoryName) {
		this.thirdCategoryName = thirdCategoryName;
	}
	public String getActualPriceValue() {
		return actualPriceValue;
	}
	public void setActualPriceValue(String actualPriceValue) {
		this.actualPriceValue = actualPriceValue;
	}
	public String getCustomerSourceName() {
		return customerSourceName;
	}
	public void setCustomerSourceName(String customerSourceName) {
		this.customerSourceName = customerSourceName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getReturnOrderNumber() {
		return returnOrderNumber;
	}
	public void setReturnOrderNumber(String returnOrderNumber) {
		this.returnOrderNumber = returnOrderNumber;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	
}

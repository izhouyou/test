package com.camelot.order.export.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>Description: [零售活动报表]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName ActivityDataVO.java
 * Created on 2019年4月17日.
 */
public class ActivityDataVO implements Serializable {
	
	/**订单编号*/
	private String salesOrderNumber;
	/**活动编号*/
	private String activityCode;
	/**活动名称*/
	private String activityName;
	/**大区名称*/
	private String firstOrgName;
	/**区域名称*/
	private String secondOrgName;
	/**城市名称*/
	private String thirdOrgName;
	/**门店编号*/
	private String storeNumber;
	/**门店名称*/
	private String storeName;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createDate;
	/** 车架号 */
	private String goodsFrameNumber;
	/** 商品SN */
	private String goodsSn;
	/**获知来源(value)*/
	private String customerSourceValue;
	/**优惠码*/
    private String couponCode;
    /**优惠券类型*/
    private String couponType;
    /**活动图片URL*/
	private String activityPicture;
	/** 备注 */
	private String orderRemark;
	/**门店渠道编号*/
	private String storeChannelNumber;
	/**合伙人编号*/
	private String partnerNumber;
	/** 商品名称 */
	private String goodsName;
	
	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getGoodsFrameNumber() {
		return goodsFrameNumber;
	}
	public void setGoodsFrameNumber(String goodsFrameNumber) {
		this.goodsFrameNumber = goodsFrameNumber;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getCustomerSourceValue() {
		return customerSourceValue;
	}
	public void setCustomerSourceValue(String customerSourceValue) {
		this.customerSourceValue = customerSourceValue;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getActivityPicture() {
		return activityPicture;
	}
	public void setActivityPicture(String activityPicture) {
		this.activityPicture = activityPicture;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public String getStoreChannelNumber() {
		return storeChannelNumber;
	}
	public void setStoreChannelNumber(String storeChannelNumber) {
		this.storeChannelNumber = storeChannelNumber;
	}
	public String getPartnerNumber() {
		return partnerNumber;
	}
	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
}

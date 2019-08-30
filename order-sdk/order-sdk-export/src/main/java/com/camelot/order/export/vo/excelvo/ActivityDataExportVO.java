package com.camelot.order.export.vo.excelvo;

import java.io.Serializable;
import java.util.Date;

import com.camelot.order.common.utils.excel.ExcelField;
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
public class ActivityDataExportVO implements Serializable {
	
	/**活动编号*/
	@ExcelField(title = "活动编号", sort = 1 )
	private String activityCode;
	/**活动名称*/
	@ExcelField(title = "活动名称", sort = 5 )
	private String activityName;
	/**大区名称*/
	@ExcelField(title = "大区", sort = 10 )
	private String firstOrgName;
	/**区域名称*/
	@ExcelField(title = "区域", sort = 15 )
	private String secondOrgName;
	/**城市名称*/
	@ExcelField(title = "城市", sort = 20 )
	private String thirdOrgName;
	/**门店编号*/
	@ExcelField(title = "门店编号", sort = 25 )
	private String storeNumber;
	/**门店渠道编号*/
	@ExcelField(title = "门店渠道编号", sort = 26 )
	private String storeChannelNumber;
	/**门店名称*/
	@ExcelField(title = "门店名称", sort = 30 )
	private String storeName;
	/**合伙人编号*/
	@ExcelField(title = "合伙人编号", sort = 31 )
	private String partnerNumber;
	/**订单编号*/
	@ExcelField(title = "销售订单号", sort = 34 )
	private String salesOrderNumber;
	/**销售上报时间*/
	@ExcelField(title = "销售上报时间", sort = 35 )
	private Date createDate;
	/** 车架号 */
	@ExcelField(title = "车架号", sort = 40 )
	private String goodsFrameNumber;
	/** 商品SN */
	@ExcelField(title = "SN号", sort = 45 )
	private String goodsSn;
	/**获知来源(value)*/
	@ExcelField(title = "消费者来源", sort = 50 )
	private String customerSourceValue;
	/**优惠码*/
	@ExcelField(title = "使用活动码", sort = 55 )
    private String couponCode;
    /**优惠券类型*/
	@ExcelField(title = "活动码类型", sort = 60 )
    private String couponType;
    /**活动图片URL*/
	@ExcelField(title = "活动图片URL", sort = 61 )
	private String activityPicture;
	/** 备注 */
	@ExcelField(title = "备注", sort = 65 )
	private String orderRemark;
	/** 商品名称 */
	@ExcelField(title = "商品名称", sort = 46 )
	private String goodsName;
	
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
	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
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

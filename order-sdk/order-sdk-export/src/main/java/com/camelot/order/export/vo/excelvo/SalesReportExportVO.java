package com.camelot.order.export.vo.excelvo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.camelot.order.common.utils.excel.ExcelField;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>Description: [门店销售报表]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName SalesReportVO.java
 * Created on 2019年4月8日.
 */
public class SalesReportExportVO implements Serializable {
	
	/**主键*/
	@ExcelField(title = "销售订单ID", sort = 1 )
	private String salesOrderId;
	/**订单编号*/
	@ExcelField(title = "销售订单编号", sort = 5 )
	private String salesOrderNumber;
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
	@ExcelField(title = "门店渠道编号", sort = 30 )
	private String storeChannelNumber;
	/**门店名称*/
	@ExcelField(title = "门店名称", sort = 35 )
	private String storeName;
	/**合伙人编号*/
	@ExcelField(title = "合伙人编码", sort = 40 )
	private String partnerNumber;
	/**订单状态名称*/
	@ExcelField(title = "订单状态名称", sort = 45 )
	private String salesOrderStatusName;
	/**付款状态名称*/
	@ExcelField(title = "付款状态名称", sort = 50 )
	private String paymentStatusName;
	/**退货状态名称*/
	@ExcelField(title = "退货状态名称", sort = 51 )
	private String returnStatusName;
	/** 商品SN */
	@ExcelField(title = "SN", sort = 55 )
	private String goodsSn;
	/** 车架号 */
	@ExcelField(title = "车架号", sort = 60 )
	private String goodsFrameNumber;
	/** 商品名称 */
	@ExcelField(title = "商品名称", sort = 65 )
	private String goodsName;
	/** 商品三级级分类名称 */
	@ExcelField(title = "商品三级分类名称", sort = 70 )
	private String thirdCategoryName;
	/** 商品实销单价 */
	@ExcelField(title = "商品实销单价", sort = 75 )
	private String actualPriceValue;
	/** 消费者来源名称 */
	@ExcelField(title = "消费者来源名称", sort = 80 )
	private String customerSourceName;
	/** 创建日期 */
	@ExcelField(title = "创建日期", sort = 85 )
	private Date createDate;
	/** 修改时间 */
	@ExcelField(title = "修改时间", sort = 90 )
	private Date modifyDate;
	/** 退货订单编号*/
	@ExcelField(title = "退货单号", sort = 95 )
	private String returnOrderNumber;
	/**活动名称*/
	@ExcelField(title = "参与活动", sort = 100 )
	private String activityName;
	/**活动优惠码*/
	@ExcelField(title = "活动码", sort = 105 )
	private String couponCode;
	/** 备注 */
	@ExcelField(title = "活动备注", sort = 110 )
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

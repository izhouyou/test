package com.camelot.order.export.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * <p>Description: [零售差异订单]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName DifferenceOrderVO.java
 * Created on 2019年4月2日.
 */
public class DifferenceOrderVO implements Serializable {
	
	/** 订单ID */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long orderId;
	/** 销售订单编号 */
	private String orderNumber;
	/**商品id*/
	private Integer goodsId;
	/** 商品编号 */
	private String goodsNumber;
	/** 商品名称 */
	private String goodsName;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private java.util.Date createDate;
	/** 合伙人 */
	private String partnerName;
	/** 加盟商 */
	private String franchiseeName;
	/** 门店名称 */
	private String storeName;
	/**应付金额*/
	private java.math.BigDecimal retailPrice;
	/**实付金额*/
	private java.math.BigDecimal actualPrice;
	/** 零售差异金额 */
	private java.math.BigDecimal difference;
	/**零售指导价*/
	private String retailPriceValue;
	/**商品实销单价*/
	private String actualPriceValue;
	/** 零售差异金额 */
	private String differenceValue;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getFranchiseeName() {
		return franchiseeName;
	}
	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public java.math.BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(java.math.BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public java.math.BigDecimal getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(java.math.BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}
	public java.math.BigDecimal getDifference() {
		return difference;
	}
	public void setDifference(java.math.BigDecimal difference) {
		this.difference = difference;
	}
	public String getRetailPriceValue() {
		return retailPriceValue;
	}
	public void setRetailPriceValue(String retailPriceValue) {
		this.retailPriceValue = retailPriceValue;
	}
	public String getActualPriceValue() {
		return actualPriceValue;
	}
	public void setActualPriceValue(String actualPriceValue) {
		this.actualPriceValue = actualPriceValue;
	}
	public String getDifferenceValue() {
		return differenceValue;
	}
	public void setDifferenceValue(String differenceValue) {
		this.differenceValue = differenceValue;
	}
	
}

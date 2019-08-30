package com.camelot.order.export.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.camelot.order.common.utils.Utility;

public class StatisitcsGoodsSalesVO implements Serializable {
	
	/**一级分类名称*/
	private String goodsFirstCategoryName;
	/**商品名称*/
	private String goodsName;
	/**下单数量*/
	private Integer amountTotal;
	/**下单数量*/
	private Integer orderAmount;
	/**实销单价*/
	private java.math.BigDecimal actualPrice;
	/**实销单价Value*/
	private String actualValuePrice;
	/**实销单价old*/
	private BigDecimal retailPrice;
	/**实销单价oldValue*/
	private String retailValuePrice;
	/** 实销金额*/
	private BigDecimal totalPrice;
	/** 实销金额Value*/
	private String totalValuePrice;
	
	public String getGoodsFirstCategoryName() {
		return goodsFirstCategoryName;
	}
	public void setGoodsFirstCategoryName(String goodsFirstCategoryName) {
		this.goodsFirstCategoryName = goodsFirstCategoryName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getAmountTotal() {
		return amountTotal;
	}
	public void setAmountTotal(Integer amountTotal) {
		this.amountTotal = amountTotal;
		this.orderAmount = amountTotal;
	}
	public Integer getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	public java.math.BigDecimal getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(java.math.BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
		this.actualValuePrice = Utility.bigDecimalToString(actualPrice);
		this.retailValuePrice = Utility.bigDecimalToString(actualPrice);
	}
	public String getActualValuePrice() {
		return actualValuePrice;
	}
	public void setActualValuePrice(String actualValuePrice) {
		this.actualValuePrice = actualValuePrice;
	}
	public String getRetailValuePrice() {
		return retailValuePrice;
	}
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
		if(Utility.isNotEmpty(retailPrice)) {
            this.retailValuePrice = Utility.bigDecimalToString(retailPrice);
        }
	}
	public void setRetailValuePrice(String retailValuePrice) {
		this.retailValuePrice = retailValuePrice;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
		this.totalValuePrice = Utility.bigDecimalToString(totalPrice);
	}
	public String getTotalValuePrice() {
		return totalValuePrice;
	}
	public void setTotalValuePrice(String totalValuePrice) {
		this.totalValuePrice = totalValuePrice;
	}
	

}

package com.camelot.order.export.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class StatisticsGoodsVO implements Serializable {
	/** 商品名称*/
	private String goodsName;
	/** 实销单价*/
	private BigDecimal actualPrice;
	/** 商品数量*/
	private Integer orderAmount;
	/** 实销金额*/
	private BigDecimal totalPrice;
	/** 实销单价Value*/
	private String actualValuePrice;
	/** 实销金额Value*/
	private String totalValuePrice;
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public BigDecimal getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}
	public Integer getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getActualValuePrice() {
		return actualValuePrice;
	}
	public void setActualValuePrice(String actualValuePrice) {
		this.actualValuePrice = actualValuePrice;
	}
	public String getTotalValuePrice() {
		return totalValuePrice;
	}
	public void setTotalValuePrice(String totalValuePrice) {
		this.totalValuePrice = totalValuePrice;
	}
	
}

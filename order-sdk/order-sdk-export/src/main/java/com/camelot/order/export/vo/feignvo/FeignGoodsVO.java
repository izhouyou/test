package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FeignGoodsVO implements Serializable{
	
	/**主键*/
	private Long goodsId;
	/**商品编号*/
	private String goodsNumber;
	/**商品名称*/
	private String goodsName;
	/**下单数量*/
	private Integer orderAmount;
	/**退单数量*/
	private Integer returnAmount;
	/**实销单价*/
	private String actualPrice;
	/**零售单价*/
	private String retailPrice;
	/**商品69码*/
	private String goodsBarcode;
	/**商品SN码*/
	private String goodsSN;
	/**商品车架号*/
	private String goodsFramecode;
	/**小计:小计=指导零售价*数量*/
	private String retailPriceSubtotal;
	/**实付金额:实付金额=实销单价*数量*/
	private String actualPriceSubtotal;
	/**零售差异:零售差异=小计-实付金额*/
	private String RetailDifference;
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
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
	public Integer getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Integer getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(Integer returnAmount) {
		this.returnAmount = returnAmount;
	}
	public String getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(String actualPrice) {
		this.actualPrice = actualPrice;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public String getGoodsBarcode() {
		return goodsBarcode;
	}
	public void setGoodsBarcode(String goodsBarcode) {
		this.goodsBarcode = goodsBarcode;
	}
	public String getGoodsSN() {
		return goodsSN;
	}
	public void setGoodsSN(String goodsSN) {
		this.goodsSN = goodsSN;
	}
	public String getGoodsFramecode() {
		return goodsFramecode;
	}
	public void setGoodsFramecode(String goodsFramecode) {
		this.goodsFramecode = goodsFramecode;
	}
	public String getRetailPriceSubtotal() {
		return retailPriceSubtotal;
	}
	public void setRetailPriceSubtotal(String retailPriceSubtotal) {
		this.retailPriceSubtotal = retailPriceSubtotal;
	}
	public String getActualPriceSubtotal() {
		return actualPriceSubtotal;
	}
	public void setActualPriceSubtotal(String actualPriceSubtotal) {
		this.actualPriceSubtotal = actualPriceSubtotal;
	}
	public String getRetailDifference() {
		return RetailDifference;
	}
	public void setRetailDifference(String retailDifference) {
		RetailDifference = retailDifference;
	}
	
	
	
	
}

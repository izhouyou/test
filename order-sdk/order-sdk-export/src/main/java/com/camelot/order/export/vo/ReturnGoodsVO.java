package com.camelot.order.export.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class ReturnGoodsVO implements Serializable {
	
	/** goodsId商品id */
	private Integer goodsId;
	/** goodsName商品名称*/
	private String goodsName;
	/** goodsNumber商品编号*/
	private String goodsNumber;
	/** 商品69码 */
	private String goodsBarcode;
	/** 商品SN */
	private String goodsSn;
	/** 车架号 */
	private String goodsFrameNumber;
	/** 生产日期 */
	private Date productionDate;
	/** 退货次数 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long returnNumber;
	
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public String getGoodsBarcode() {
		return goodsBarcode;
	}
	public void setGoodsBarcode(String goodsBarcode) {
		this.goodsBarcode = goodsBarcode;
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
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public Long getReturnNumber() {
		return returnNumber;
	}
	public void setReturnNumber(Long returnNumber) {
		this.returnNumber = returnNumber;
	}
	@Override
	public String toString() {
		return "ReturnGoodsVO [goodsName=" + goodsName + ", goodsNumber=" + goodsNumber + ", goodsBarcode="
				+ goodsBarcode + ", goodsSn=" + goodsSn + ", goodsFrameNumber=" + goodsFrameNumber + ", productionDate="
				+ productionDate + ", returnNumber=" + returnNumber + "]";
	}
}

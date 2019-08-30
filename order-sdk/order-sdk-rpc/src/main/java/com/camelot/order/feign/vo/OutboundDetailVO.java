package com.camelot.order.feign.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class OutboundDetailVO implements Serializable {

	/**id*/
	@ApiModelProperty(value = "出库单明细主键id", required = false)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long outboundDetailId;
	/**出库单主键id*/
	@ApiModelProperty(value = "出库单主键id", required = false)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long outboundBillsId;
	/**出库单号*/
	@ApiModelProperty(value = "出库单号", required = false)
	private String outboundBillsNumber;
	/**商品id*/
	@ApiModelProperty(value = "商品id", required = false)
	private Integer goodsId;
	/**商品编号*/
	@ApiModelProperty(value = "商品编号", required = false)
	private String goodsNumber;
	/**商品名称*/
	@ApiModelProperty(value = "商品名称", required = false)
	private String goodsName;
	/**商品SN*/
	@ApiModelProperty(value = "商品SN", required = false)
	private String goodsSn;
	/**商品69码*/
	@ApiModelProperty(value = "商品69码", required = false)
	private String goodsBarcode;
	/**车架号*/
	@ApiModelProperty(value = "车架号", required = false)
	private String goodsFrameNumber;
	/**已出库数量*/
	@ApiModelProperty(value = "已出库数量", required = false)
	private Integer outboundNum;
	
	public Long getOutboundDetailId(){
		return outboundDetailId;
	}
	
	public void setOutboundDetailId(Long outboundDetailId){
		this.outboundDetailId = outboundDetailId;
	}

	public Long getOutboundBillsId(){
		return outboundBillsId;
	}
	
	public void setOutboundBillsId(Long outboundBillsId){
		this.outboundBillsId = outboundBillsId;
	}

	public String getOutboundBillsNumber(){
		return outboundBillsNumber;
	}
	
	public void setOutboundBillsNumber(String outboundBillsNumber){
		this.outboundBillsNumber = outboundBillsNumber;
	}

	public Integer getGoodsId(){
		return goodsId;
	}
	
	public void setGoodsId(Integer goodsId){
		this.goodsId = goodsId;
	}

	public String getGoodsNumber(){
		return goodsNumber;
	}
	
	public void setGoodsNumber(String goodsNumber){
		this.goodsNumber = goodsNumber;
	}

	public String getGoodsName(){
		return goodsName;
	}
	
	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}

	public String getGoodsSn(){
		return goodsSn;
	}
	
	public void setGoodsSn(String goodsSn){
		this.goodsSn = goodsSn;
	}

	public String getGoodsBarcode(){
		return goodsBarcode;
	}
	
	public void setGoodsBarcode(String goodsBarcode){
		this.goodsBarcode = goodsBarcode;
	}

	public String getGoodsFrameNumber(){
		return goodsFrameNumber;
	}
	
	public void setGoodsFrameNumber(String goodsFrameNumber){
		this.goodsFrameNumber = goodsFrameNumber;
	}

	public Integer getOutboundNum(){
		return outboundNum;
	}
	
	public void setOutboundNum(Integer outboundNum){
		this.outboundNum = outboundNum;
	}

}

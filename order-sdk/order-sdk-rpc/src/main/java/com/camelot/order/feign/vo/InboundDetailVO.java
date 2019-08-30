package com.camelot.order.feign.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class InboundDetailVO implements Serializable {

	/**入库单明细主键id*/
	@ApiModelProperty(value = "入库单明细主键id", required = false)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long inboundDetailId;
	/**入库单主键id*/
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "入库单主键id", required = false)
	private Long inboundBillsId;
	/**入库单号*/
	@ApiModelProperty(value = "入库单号", required = false)
	private String inboundNumber;
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
	/**车架号*/
	@ApiModelProperty(value = "车架号", required = false)
	private String goodsFrameNumber;
	/**已入库数量*/
	@ApiModelProperty(value = "已入库数量", required = false)
	private Integer inboundNum;
	/**商品69码*/
	@ApiModelProperty(value = "商品69码", required = false)
	private String goodsBarcode;
	
	public Long getInboundDetailId(){
		return inboundDetailId;
	}
	
	public void setInboundDetailId(Long inboundDetailId){
		this.inboundDetailId = inboundDetailId;
	}

	public Long getInboundBillsId(){
		return inboundBillsId;
	}
	
	public void setInboundBillsId(Long inboundBillsId){
		this.inboundBillsId = inboundBillsId;
	}

	public String getInboundNumber(){
		return inboundNumber;
	}
	
	public void setInboundNumber(String inboundNumber){
		this.inboundNumber = inboundNumber;
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

	public String getGoodsFrameNumber(){
		return goodsFrameNumber;
	}
	
	public void setGoodsFrameNumber(String goodsFrameNumber){
		this.goodsFrameNumber = goodsFrameNumber;
	}

	public Integer getInboundNum(){
		return inboundNum;
	}
	
	public void setInboundNum(Integer inboundNum){
		this.inboundNum = inboundNum;
	}

	public String getGoodsBarcode(){
		return goodsBarcode;
	}
	
	public void setGoodsBarcode(String goodsBarcode){
		this.goodsBarcode = goodsBarcode;
	}

}

package com.camelot.order.export.vo.param;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 退货有序商品查询参数</p>
 * @author zhouyou
 * @date 2019年5月14日
 */
public class ReturnGoodsParamVO extends BaseParamVO implements Serializable {
	
	/** 商品名称 */
	@ApiModelProperty(value = "商品名称", required = false)
	private String goodsName;
	/** 商品SN码 */
	@ApiModelProperty(value = "商品SN码", required = false)
	private String goodsSn;
	/** 车架号 */
	@ApiModelProperty(value = "车架号", required = false)
	private String goodsFrameNumber;
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	
}

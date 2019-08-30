package com.camelot.order.export.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [商品信息]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName GoodsVO.java
 * Created on 2019年4月8日.
 */
public class GoodsVO implements Serializable {
	
	/** goodsId商品id*/
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
		return "GoodsVO [goodsId=" + goodsId + ", goodsName=" + goodsName + ", goodsNumber=" + goodsNumber + "]";
	}
	
}

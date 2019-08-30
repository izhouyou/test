package com.camelot.order.export.vo.excelvo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.camelot.order.common.utils.excel.ExcelField;

/**
 * <p>Description: [零售差异订单]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName DifferenceOrderVO.java
 * Created on 2019年4月2日.
 */
public class DifferenceOrderExcelVO implements Serializable {
	
	/** 订单ID */
	private String orderId;
	/** 销售订单编号 */
	@ExcelField(title = "销售订单编号", sort = 5 )
	private String orderNumber;
	/**商品id*/
	private String goodsId;
	/** 商品编号 */
	@ExcelField(title = "商品编号", sort = 10 )
	private String goodsNumber;
	/** 商品名称 */
	@ExcelField(title = "商品名称", sort = 15 )
	private String goodsName;
	/**创建时间*/
	@ExcelField(title = "提交时间", sort = 20 )
	private String createDate;
	/** 合伙人 */
	@ExcelField(title = "合伙人", sort = 25 )
	private String partnerName;
	/** 加盟商 */
	@ExcelField(title = "加盟商", sort = 30 )
	private String franchiseeName;
	/** 门店名称 */
	@ExcelField(title = "门店名称", sort = 35 )
	private String storeName;
	/**零售指导价*/
	@ExcelField(title = "零售指导价", sort = 40 )
	private String retailPriceValue;
	/**商品实销单价*/
	@ExcelField(title = "商品实销单价", sort = 45 )
	private String actualPriceValue;
	/** 零售差异金额 */
	@ExcelField(title = "零售差异金额", sort = 50 )
	private String differenceValue;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
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
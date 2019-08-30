package com.camelot.order.export.vo.excelvo;

import java.io.Serializable;

import com.camelot.order.common.utils.excel.ExcelField;

public class TransregionalInfoExportVO implements Serializable {

	/**商品编号*/
	@ExcelField(title = "商品编号", sort = 5 )
	private String goodsNumber;
	/**商品名称*/
	@ExcelField(title = "商品名称", sort = 10 )
	private String goodsName;
	/**商品SN*/
	@ExcelField(title = "商品SN", sort = 15 )
	private String goodsSn;
	/**车架号*/
	@ExcelField(title = "车架号", sort = 20 )
	private String goodsFrameNumber;
	/**是否激活 0:未激活;1激活*/
	@ExcelField(title = "是否激活", sort = 25 )
	private String activationFlagValue;
	/**销售后3天城市*/
	@ExcelField(title = "销售后3天城市", sort = 30 )
	private String cityThird;
	/**销售后15天城市*/
	@ExcelField(title = "销售后15天城市", sort = 35 )
	private String cityFifteen;
	/**销售订单编号*/
	@ExcelField(title = "销售订单编号", sort = 40 )
	private String salesOrderNumber;
	/**操作人姓名*/
	@ExcelField(title = "操作人", sort = 45 )
	private String operationUserName;
	/**提交时间*/
	@ExcelField(title = "提交时间", sort = 50 )
	private java.util.Date submitTime;
	/**销售门店名称*/
	@ExcelField(title = "销售门店名称", sort = 55 )
	private String storeName;
	/**销售合伙人名称*/
	@ExcelField(title = "销售合伙人", sort = 60 )
	private String spartnerName;
	/**采购合伙人名称*/
	@ExcelField(title = "采购合伙人", sort = 65 )
	private String cpartnerName;
	/**1:合伙人外部窜货*/
	@ExcelField(title = "问题类型", sort = 70 )
	private String problemTypeValue;
	
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
	public String getActivationFlagValue() {
		return activationFlagValue;
	}
	public void setActivationFlagValue(String activationFlagValue) {
		this.activationFlagValue = activationFlagValue;
	}
	public String getCityThird() {
		return cityThird;
	}
	public void setCityThird(String cityThird) {
		this.cityThird = cityThird;
	}
	public String getCityFifteen() {
		return cityFifteen;
	}
	public void setCityFifteen(String cityFifteen) {
		this.cityFifteen = cityFifteen;
	}
	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}
	public String getOperationUserName() {
		return operationUserName;
	}
	public void setOperationUserName(String operationUserName) {
		this.operationUserName = operationUserName;
	}
	public java.util.Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(java.util.Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getSpartnerName() {
		return spartnerName;
	}
	public void setSpartnerName(String spartnerName) {
		this.spartnerName = spartnerName;
	}
	public String getCpartnerName() {
		return cpartnerName;
	}
	public void setCpartnerName(String cpartnerName) {
		this.cpartnerName = cpartnerName;
	}
	public String getProblemTypeValue() {
		return problemTypeValue;
	}
	public void setProblemTypeValue(String problemTypeValue) {
		this.problemTypeValue = problemTypeValue;
	}
	
}

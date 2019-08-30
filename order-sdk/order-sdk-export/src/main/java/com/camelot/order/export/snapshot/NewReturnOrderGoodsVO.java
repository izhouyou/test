package com.camelot.order.export.snapshot;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author hudya
 */
public class NewReturnOrderGoodsVO implements Serializable {

	/**主键id*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long returnOrderGoodsId;
	/**退货订单id*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long returnOrderId;
	/**商品id*/
	private Integer goodsId;
	/**商品编码*/
	private String goodsNumber;
	/**商品名称*/
	private String goodsName;
	/**商品一级分类id*/
	private Integer firstCategoryId;
	/**商品一级分类编码*/
	private String firstCategoryNumber;
	/**商品一级分类名称*/
	private String firstCategoryName;
	/**商品二级分类id*/
	private Integer secondCategoryId;
	/**商品二级分类编码*/
	private String secondCategoryNumber;
	/**商品二级分类名称*/
	private String secondCategoryName;
	/**商品三级分类id*/
	private Integer thirdCategoryId;
	/**商品三级分类编码*/
	private String thirdCategoryNumber;
	/**商品三级级分类名称*/
	private String thirdCategoryName;
	/**商品品牌id*/
	private Integer goodsBrandId;
	/**商品品牌编码*/
	private String goodsBrandNumber;
	/**商品品牌名称*/
	private String goodsBrandName;
	/**商品颜色id*/
	private Integer goodsAttributeId;
	/**商品颜色编码*/
	private String goodsAttributeNumber;
	/**商品颜色名称*/
	private String goodsAttributeName;
	/**商品69码*/
	private String goodsBarcode;
	/**商品sn码*/
	private String goodsSn;
	/**整车车架号*/
	private String goodsFrameNumber;
	/**退单数量*/
	private Integer returnAmount;
	/**零售指导价*/
	private BigDecimal retailPrice;
	/**商品实销单价*/
	private BigDecimal actualPrice;
	/**退货单价*/
	private BigDecimal returnPrice;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/** 销售退货订单集合 */
	private List<Long> returnOrderIdList;
	/**退单数量*/
	private Integer orderAmount;
	/** 退货次数 */
	private Long returnNumber;
	/**小计:小计=指导零售价*数量*/
	private String retailPriceSubtotal;
	/**实付金额:实付金额=实销单价*数量*/
	private String actualPriceSubtotal;
	/**零售差异:零售差异=小计-实付金额*/
	private String RetailDifference;
	/**数据迁移必须字段，不要删除！！*/
	private Integer orderGoodsId;
	
	public Long getReturnOrderGoodsId(){
		return returnOrderGoodsId;
	}
	
	public void setReturnOrderGoodsId(Long returnOrderGoodsId){
		this.returnOrderGoodsId = returnOrderGoodsId;
	}

	public Long getReturnOrderId(){
		return returnOrderId;
	}
	
	public void setReturnOrderId(Long returnOrderId){
		this.returnOrderId = returnOrderId;
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

	public Integer getFirstCategoryId(){
		return firstCategoryId;
	}
	
	public void setFirstCategoryId(Integer firstCategoryId){
		this.firstCategoryId = firstCategoryId;
	}

	public String getFirstCategoryNumber(){
		return firstCategoryNumber;
	}
	
	public void setFirstCategoryNumber(String firstCategoryNumber){
		this.firstCategoryNumber = firstCategoryNumber;
	}

	public String getFirstCategoryName(){
		return firstCategoryName;
	}
	
	public void setFirstCategoryName(String firstCategoryName){
		this.firstCategoryName = firstCategoryName;
	}

	public Integer getSecondCategoryId(){
		return secondCategoryId;
	}
	
	public void setSecondCategoryId(Integer secondCategoryId){
		this.secondCategoryId = secondCategoryId;
	}

	public String getSecondCategoryNumber(){
		return secondCategoryNumber;
	}
	
	public void setSecondCategoryNumber(String secondCategoryNumber){
		this.secondCategoryNumber = secondCategoryNumber;
	}

	public String getSecondCategoryName(){
		return secondCategoryName;
	}
	
	public void setSecondCategoryName(String secondCategoryName){
		this.secondCategoryName = secondCategoryName;
	}

	public Integer getThirdCategoryId(){
		return thirdCategoryId;
	}
	
	public void setThirdCategoryId(Integer thirdCategoryId){
		this.thirdCategoryId = thirdCategoryId;
	}

	public String getThirdCategoryNumber(){
		return thirdCategoryNumber;
	}
	
	public void setThirdCategoryNumber(String thirdCategoryNumber){
		this.thirdCategoryNumber = thirdCategoryNumber;
	}

	public String getThirdCategoryName(){
		return thirdCategoryName;
	}
	
	public void setThirdCategoryName(String thirdCategoryName){
		this.thirdCategoryName = thirdCategoryName;
	}

	public Integer getGoodsBrandId(){
		return goodsBrandId;
	}
	
	public void setGoodsBrandId(Integer goodsBrandId){
		this.goodsBrandId = goodsBrandId;
	}

	public String getGoodsBrandNumber(){
		return goodsBrandNumber;
	}
	
	public void setGoodsBrandNumber(String goodsBrandNumber){
		this.goodsBrandNumber = goodsBrandNumber;
	}

	public String getGoodsBrandName(){
		return goodsBrandName;
	}
	
	public void setGoodsBrandName(String goodsBrandName){
		this.goodsBrandName = goodsBrandName;
	}

	public Integer getGoodsAttributeId(){
		return goodsAttributeId;
	}
	
	public void setGoodsAttributeId(Integer goodsAttributeId){
		this.goodsAttributeId = goodsAttributeId;
	}

	public String getGoodsAttributeNumber(){
		return goodsAttributeNumber;
	}
	
	public void setGoodsAttributeNumber(String goodsAttributeNumber){
		this.goodsAttributeNumber = goodsAttributeNumber;
	}

	public String getGoodsAttributeName(){
		return goodsAttributeName;
	}
	
	public void setGoodsAttributeName(String goodsAttributeName){
		this.goodsAttributeName = goodsAttributeName;
	}

	public String getGoodsBarcode(){
		return goodsBarcode;
	}
	
	public void setGoodsBarcode(String goodsBarcode){
		this.goodsBarcode = goodsBarcode;
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

	public Integer getReturnAmount(){
		return returnAmount;
	}
	
	public void setReturnAmount(Integer returnAmount){
		this.returnAmount = returnAmount;
	}

	public java.math.BigDecimal getRetailPrice(){
		return retailPrice;
	}
	
	public void setRetailPrice(java.math.BigDecimal retailPrice){
		this.retailPrice = retailPrice;
	}

	public java.math.BigDecimal getActualPrice(){
		return actualPrice;
	}
	
	public void setActualPrice(java.math.BigDecimal actualPrice){
		this.actualPrice = actualPrice;
	}

	public java.math.BigDecimal getReturnPrice(){
		return returnPrice;
	}
	
	public void setReturnPrice(java.math.BigDecimal returnPrice){
		this.returnPrice = returnPrice;
	}

	public Integer getDelFlg(){
		return delFlg;
	}
	
	public void setDelFlg(Integer delFlg){
		this.delFlg = delFlg;
	}

	public List<Long> getReturnOrderIdList() {
		return returnOrderIdList;
	}

	public void setReturnOrderIdList(List<Long> returnOrderIdList) {
		this.returnOrderIdList = returnOrderIdList;
	}

	public Long getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(Long returnNumber) {
		this.returnNumber = returnNumber;
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

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getOrderGoodsId() {
		return orderGoodsId;
	}

	public void setOrderGoodsId(Integer orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
		this.returnOrderGoodsId = orderGoodsId.longValue();
	}
}

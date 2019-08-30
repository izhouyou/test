package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GoodsVO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**主键*/
	private Long goodsId;
	/**商品编号*/
	private String goodsNumber;
	/**商品名称*/
	private String goodsName;
	/**商品分类*/
	private Long goodsCategory;
	/**商品一级分类（商品分类ID）*/
	private Long goodsFirstCategoryId;
	/**商品二级分类（分类ID）*/
	private Long goodsSecondCategoryId;
	/**商品三级级分类（分类ID）*/
	private Long goodsThirdCategoryId;
	/**商品一级分类名称*/
	private String goodsFirstCategoryName;
	/**商品二级分类名称*/
	private String goodsSecondCategoryName;
	/**商品三级分类名称*/
	private String goodsThirdCategoryName;
	/**属性(颜色)字段ID*/
	private Integer goodsAttribute;
	/**属性(颜色)value*/
	private String goodsAttributeValue;
	/**品牌id*/
	private Long goodsBrandId;
	/**品牌名称*/
	private String goodsBrandName;
	/**商品69码*/
	private String goodsBarcode;
	/**商品SN码*/
	private String goodsSN;
	/**商品车架号*/
	private String goodsFramecode;
	/**牛油保单人版商品id*/
	private Long safetySingleId;
	/**牛油保双人版商品id*/
	private Long safetyDoubleId;
	/**有无序列（1为有；0为无）*/
	private Integer isOrderly;
	/**有无序列值(Y,N)*/
	private String isOrderlyValue;
	/**是否独立销售（1为是；0为否）*/
	private Integer isIndependentSale;
	/**是否独立销售值(Y,N)*/
	private String isIndependentSaleValue;
	/**商品状态（0为启用；1为停用）*/
	private Integer goodsStatus;
	/**商品状态值(停用/启用)*/
	private String goodsStatusValue;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Long createUserId;
	/**创建人名称*/
	private String createUserName;
	/**修改人id*/
	private Long updateUserId;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createDate;
	/**修改时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date modifyDate;
	/**查询条件：时间控件的开始时间**/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date beginSearchDate;
	/**查询条件：时间控件的截止时间**/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date endSearchDate;
	/**商品关联订单编号**/
	private String salesOrderNumber;
	/**商品关联订单状态*/
	private String salesOrderStatusValue;
	
	
	public Long getGoodsId(){
		return goodsId;
	}
	
	public void setGoodsId(Long goodsId){
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

	public Long getGoodsFirstCategoryId(){
		return goodsFirstCategoryId;
	}
	
	public void setGoodsFirstCategoryId(Long goodsFirstCategoryId){
		this.goodsFirstCategoryId = goodsFirstCategoryId;
	}

	public Long getGoodsSecondCategoryId(){
		return goodsSecondCategoryId;
	}
	
	public void setGoodsSecondCategoryId(Long goodsSecondCategoryId){
		this.goodsSecondCategoryId = goodsSecondCategoryId;
	}

	public Long getGoodsThirdCategoryId(){
		return goodsThirdCategoryId;
	}
	
	public void setGoodsThirdCategoryId(Long goodsThirdCategoryId){
		this.goodsThirdCategoryId = goodsThirdCategoryId;
	}

	public Integer getGoodsAttribute(){
		return goodsAttribute;
	}
	
	public void setGoodsAttribute(Integer goodsAttribute){
		this.goodsAttribute = goodsAttribute;
	}

	public Long getGoodsBrandId(){
		return goodsBrandId;
	}
	
	public void setGoodsBrandId(Long goodsBrandId){
		this.goodsBrandId = goodsBrandId;
	}


	public String getGoodsBarcode(){
		return goodsBarcode;
	}
	
	public void setGoodsBarcode(String goodsBarcode){
		this.goodsBarcode = goodsBarcode;
	}

	public Integer getIsOrderly(){
		return isOrderly;
	}
	
	public void setIsOrderly(Integer isOrderly){
		this.isOrderly = isOrderly;
	}

	public Integer getIsIndependentSale(){
		return isIndependentSale;
	}
	
	public void setIsIndependentSale(Integer isIndependentSale){
		this.isIndependentSale = isIndependentSale;
	}

	public Integer getGoodsStatus(){
		return goodsStatus;
	}
	
	public void setGoodsStatus(Integer goodsStatus){
		this.goodsStatus = goodsStatus;
	}

	public Integer getDelFlg(){
		return delFlg;
	}
	
	public void setDelFlg(Integer delFlg){
		this.delFlg = delFlg;
	}

	public Long getCreateUserId(){
		return createUserId;
	}
	
	public void setCreateUserId(Long createUserId){
		this.createUserId = createUserId;
	}

	public Long getUpdateUserId(){
		return updateUserId;
	}
	
	public void setUpdateUserId(Long updateUserId){
		this.updateUserId = updateUserId;
	}

	public Date getCreateDate(){
		return createDate;
	}
	
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getModifyDate(){
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}

	public Date getBeginSearchDate() {
		return beginSearchDate;
	}

	public void setBeginSearchDate(Date beginSearchDate) {
		this.beginSearchDate = beginSearchDate;
	}

	public Date getEndSearchDate() {
		return endSearchDate;
	}

	public void setEndSearchDate(Date endSearchDate) {
		this.endSearchDate = endSearchDate;
	}

	public String getGoodsFirstCategoryName() {
		return goodsFirstCategoryName;
	}

	public void setGoodsFirstCategoryName(String goodsFirstCategoryName) {
		this.goodsFirstCategoryName = goodsFirstCategoryName;
	}

	public String getGoodsSecondCategoryName() {
		return goodsSecondCategoryName;
	}

	public void setGoodsSecondCategoryName(String goodsSecondCategoryName) {
		this.goodsSecondCategoryName = goodsSecondCategoryName;
	}

	public String getGoodsThirdCategoryName() {
		return goodsThirdCategoryName;
	}

	public void setGoodsThirdCategoryName(String goodsThirdCategoryName) {
		this.goodsThirdCategoryName = goodsThirdCategoryName;
	}

	public String getGoodsBrandName() {
		return goodsBrandName;
	}

	public void setGoodsBrandName(String goodsBrandName) {
		this.goodsBrandName = goodsBrandName;
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

	public Long getSafetySingleId() {
		return safetySingleId;
	}

	public void setSafetySingleId(Long safetySingleId) {
		this.safetySingleId = safetySingleId;
	}

	public Long getSafetyDoubleId() {
		return safetyDoubleId;
	}

	public void setSafetyDoubleId(Long safetyDoubleId) {
		this.safetyDoubleId = safetyDoubleId;
	}

	public Long getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(Long goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public String getIsOrderlyValue() {
		return isOrderlyValue;
	}

	public void setIsOrderlyValue(String isOrderlyValue) {
		this.isOrderlyValue = isOrderlyValue;
	}

	public String getIsIndependentSaleValue() {
		return isIndependentSaleValue;
	}

	public void setIsIndependentSaleValue(String isIndependentSaleValue) {
		this.isIndependentSaleValue = isIndependentSaleValue;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getGoodsStatusValue() {
		return goodsStatusValue;
	}

	public void setGoodsStatusValue(String goodsStatusValue) {
		this.goodsStatusValue = goodsStatusValue;
	}

	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}

	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}

	public String getSalesOrderStatusValue() {
		return salesOrderStatusValue;
	}

	public void setSalesOrderStatusValue(String salesOrderStatusValue) {
		this.salesOrderStatusValue = salesOrderStatusValue;
	}

	public String getGoodsAttributeValue() {
		return goodsAttributeValue;
	}

	public void setGoodsAttributeValue(String goodsAttributeValue) {
		this.goodsAttributeValue = goodsAttributeValue;
	}


}

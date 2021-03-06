package com.camelot.order.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author hudya
 */
public class NewSalesOrderGoodsDomain implements Serializable {

	/**主键id*/
	private Long salesOrderGoodsId;
	/**订单id*/
	private Long salesOrderId;
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
	/**下单数量*/
	private Integer orderAmount;
	/**退单累计数量*/
	private Integer returnTotalAmount;
	/**零售指导价*/
	private BigDecimal retailPrice;
	/**商品实销单价*/
	private BigDecimal actualPrice;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/** 销售订单集合 */
	private List<Long> salesOrderIdList;
	/** 最小差异金额*/
	private BigDecimal minAmount;
	/** 最大差异金额*/
	private BigDecimal maxAmount;
	/** 商品一级分类id-整车 */
	private Integer vehicleCategoryId;
	/** 商品一级分类id-周边 */
	private Integer merchCategoryId;
	/** 排序标记 */
	private Integer sortFlag;
	
	public Long getSalesOrderGoodsId(){
		return salesOrderGoodsId;
	}
	
	public void setSalesOrderGoodsId(Long salesOrderGoodsId){
		this.salesOrderGoodsId = salesOrderGoodsId;
	}

	public Long getSalesOrderId(){
		return salesOrderId;
	}
	
	public void setSalesOrderId(Long salesOrderId){
		this.salesOrderId = salesOrderId;
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

	public Integer getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Integer orderAmount){
		this.orderAmount = orderAmount;
	}

	public Integer getReturnTotalAmount(){
		return returnTotalAmount;
	}
	
	public void setReturnTotalAmount(Integer returnTotalAmount){
		this.returnTotalAmount = returnTotalAmount;
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

	public Integer getDelFlg(){
		return delFlg;
	}
	
	public void setDelFlg(Integer delFlg){
		this.delFlg = delFlg;
	}

	public List<Long> getSalesOrderIdList() {
		return salesOrderIdList;
	}

	public void setSalesOrderIdList(List<Long> salesOrderIdList) {
		this.salesOrderIdList = salesOrderIdList;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Integer getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(Integer vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	public Integer getMerchCategoryId() {
		return merchCategoryId;
	}

	public void setMerchCategoryId(Integer merchCategoryId) {
		this.merchCategoryId = merchCategoryId;
	}

	public Integer getSortFlag() {
		return sortFlag;
	}

	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}

}

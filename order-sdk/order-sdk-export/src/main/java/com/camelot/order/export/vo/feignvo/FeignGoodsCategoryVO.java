package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;

public class FeignGoodsCategoryVO implements Serializable {
	
	/**商品主键id*/
	private Long goodsId;
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
	/**属性*/
	private String goodsAttribute;
	
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Long getGoodsCategory() {
		return goodsCategory;
	}
	public void setGoodsCategory(Long goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	public Long getGoodsFirstCategoryId() {
		return goodsFirstCategoryId;
	}
	public void setGoodsFirstCategoryId(Long goodsFirstCategoryId) {
		this.goodsFirstCategoryId = goodsFirstCategoryId;
	}
	public Long getGoodsSecondCategoryId() {
		return goodsSecondCategoryId;
	}
	public void setGoodsSecondCategoryId(Long goodsSecondCategoryId) {
		this.goodsSecondCategoryId = goodsSecondCategoryId;
	}
	public Long getGoodsThirdCategoryId() {
		return goodsThirdCategoryId;
	}
	public void setGoodsThirdCategoryId(Long goodsThirdCategoryId) {
		this.goodsThirdCategoryId = goodsThirdCategoryId;
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
	public String getGoodsAttribute() {
		return goodsAttribute;
	}
	public void setGoodsAttribute(String goodsAttribute) {
		this.goodsAttribute = goodsAttribute;
	}
	@Override
	public String toString() {
		return "FeignGoodsCategoryVO [goodsId=" + goodsId + ", goodsName=" + goodsName + ", goodsCategory="
				+ goodsCategory + ", goodsFirstCategoryId=" + goodsFirstCategoryId + ", goodsSecondCategoryId="
				+ goodsSecondCategoryId + ", goodsThirdCategoryId=" + goodsThirdCategoryId + ", goodsFirstCategoryName="
				+ goodsFirstCategoryName + ", goodsSecondCategoryName=" + goodsSecondCategoryName
				+ ", goodsThirdCategoryName=" + goodsThirdCategoryName + ", goodsAttribute=" + goodsAttribute + "]";
	}
	
}

package com.camelot.order.export.vo.param;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 统计分析查询参数</p>
 * @author zhouyou
 * @date 2019年5月16日
 */
public class StatisticsAnalysisParamVO extends BaseParamVO implements Serializable {
	
	/** 用户Id */
	@ApiModelProperty(value = "用户Id", required = false)
	private Integer userId;
	/** 排序标记(0实销金额升,1实销金额降,2销售数量升,3销售数量降) */
	@ApiModelProperty(value = "排序标记", required = false)
	private Integer sortFlag;
	/** 门店名称 */
	@ApiModelProperty(value = "门店名称", required = false)
	private String storeName;
	/** 门店编号 */
	@ApiModelProperty(value = "门店编号", required = false)
	private String storeNumber;
	/** 商品一级分类 */
	@ApiModelProperty(value = "商品一级分类", required = false)
	private Integer goodsFirstCategoryId;
	/** 商品二级分类 */
	@ApiModelProperty(value = "商品二级分类", required = false)
	private Integer goodsSecondCategoryId;
	/** 商品三级分类 */
	@ApiModelProperty(value = "商品三级分类", required = false)
	private Integer goodsThirdCategoryId;
	/** 颜色Id */
	@ApiModelProperty(value = "颜色Id", required = false)
	private Integer goodsAttributeId;
	/** 商品分类id */
	@ApiModelProperty(value = "商品分类id", required = false)
	private Integer goodsCategoryId;
	/** 商品级别 */
	@ApiModelProperty(value = "商品分类级别", required = false)
	private Integer goodsCategoryGrade;
	/** 消费者来源id */
	@ApiModelProperty(value = "消费者来源id", required = false)
	private Integer sourceId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getSortFlag() {
		return sortFlag;
	}
	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	public Integer getGoodsFirstCategoryId() {
		return goodsFirstCategoryId;
	}
	public void setGoodsFirstCategoryId(Integer goodsFirstCategoryId) {
		this.goodsFirstCategoryId = goodsFirstCategoryId;
	}
	public Integer getGoodsSecondCategoryId() {
		return goodsSecondCategoryId;
	}
	public void setGoodsSecondCategoryId(Integer goodsSecondCategoryId) {
		this.goodsSecondCategoryId = goodsSecondCategoryId;
	}
	public Integer getGoodsThirdCategoryId() {
		return goodsThirdCategoryId;
	}
	public void setGoodsThirdCategoryId(Integer goodsThirdCategoryId) {
		this.goodsThirdCategoryId = goodsThirdCategoryId;
	}
	public Integer getGoodsAttributeId() {
		return goodsAttributeId;
	}
	public void setGoodsAttributeId(Integer goodsAttributeId) {
		this.goodsAttributeId = goodsAttributeId;
	}
	public Integer getGoodsCategoryId() {
		return goodsCategoryId;
	}
	public void setGoodsCategoryId(Integer goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}
	public Integer getGoodsCategoryGrade() {
		return goodsCategoryGrade;
	}
	public void setGoodsCategoryGrade(Integer goodsCategoryGrade) {
		this.goodsCategoryGrade = goodsCategoryGrade;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
}

package com.camelot.order.export.vo;

import java.math.BigDecimal;

import com.camelot.common.bean.Page;

/**
 * <p>Description: [查询条件]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName queryParamVO.java
 * Created on 2019年4月8日.
 */
public class QueryParamVO {
	
	/** 门店ids*/
	private String storeIds;
	/** 门店编号*/
	private String storeNumber;
	/** 门店名称*/
	private String storeName;
	/** 区域ids*/
	private String orgIds;
	/** 最小金额*/
	private BigDecimal minAmount;
	/** 最大金额*/
	private BigDecimal maxAmount;
	/** 分页*/
	private Page page;
	/** 开始时间*/
	private String startDate;
	/** 结束时间*/
	private String endDate;
	/** 订单编号*/
	private String salesOrderNumber;
	/** 订单状态*/
	private String salesOrderStatus;
	/** 商品名称*/
	private String goodsName;
	/** 商品SN码*/
	private String goodsSn;
	/** 车架号*/
	private String goodsFrameNumber;
	/** 合伙人编码*/
	private String partnerNumber;
	/** 加盟商编码*/
	private String franchiseeNumber;
	/** 公司名称*/
	private String companyName;
	/** 联系手机*/
	private String mobilePhone;
	/** 活动编码或名称*/
	private String actCodeOrName;
	/** 商品一级分类id*/
	private Integer goodsFirstCategoryId;
	/** 活动编码*/
	private String activityNumber;
	/** 活动名称*/
	private String activityName;
	/** 活动ids*/
	private String activityIds;
	/** 商品分类id*/
	private Integer goodsCategoryId;
	/** 机构维度id*/
	private Integer orgId;
	/** 消费者来源id*/
	private Integer sourceId;
	/** 商品分类级别*/
	private String goodsCategoryGrade;
	/** 机构分类级别*/
	private String orgCategoryGrade;
	/** 商品二级分类*/
	private Integer goodsSecondCategoryId;
	/** 商品三级分类*/
	private Integer goodsThirdCategoryId;
	/** 商品属性*/
	private Integer goodsAttributeId;
	/** 机构一级*/
	private Integer firstOrgId;
	/** 机构二级*/
	private Integer secondOrgId;
	/** 机构三级*/
	private Integer thirdOrgId;
	/** 合伙人*/
	private Integer partnerId;
	/** 加盟商*/
	private Integer franchiseeId;
	/** 排序标记*/
	private Integer sortFlag;
	/** 消费者id*/
	private Integer customerId;
	/** 用户Id */
	private Integer userId;
	
	public String getStoreIds() {
		return storeIds;
	}
	public void setStoreIds(String storeIds) {
		this.storeIds = storeIds;
	}
	
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
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
	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSalesOrderStatus() {
		return salesOrderStatus;
	}
	public void setSalesOrderStatus(String salesOrderStatus) {
		this.salesOrderStatus = salesOrderStatus;
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
	public String getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}
	public String getPartnerNumber() {
		return partnerNumber;
	}
	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}
	public String getFranchiseeNumber() {
		return franchiseeNumber;
	}
	public void setFranchiseeNumber(String franchiseeNumber) {
		this.franchiseeNumber = franchiseeNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getActCodeOrName() {
		return actCodeOrName;
	}
	public void setActCodeOrName(String actCodeOrName) {
		this.actCodeOrName = actCodeOrName;
	}
	
	public Integer getGoodsFirstCategoryId() {
		return goodsFirstCategoryId;
	}
	public void setGoodsFirstCategoryId(Integer goodsFirstCategoryId) {
		this.goodsFirstCategoryId = goodsFirstCategoryId;
	}
	public String getActivityNumber() {
		return activityNumber;
	}
	public void setActivityNumber(String activityNumber) {
		this.activityNumber = activityNumber;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Integer getGoodsCategoryId() {
		return goodsCategoryId;
	}
	public void setGoodsCategoryId(Integer goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public String getGoodsCategoryGrade() {
		return goodsCategoryGrade;
	}
	public void setGoodsCategoryGrade(String goodsCategoryGrade) {
		this.goodsCategoryGrade = goodsCategoryGrade;
	}
	public String getOrgCategoryGrade() {
		return orgCategoryGrade;
	}
	public void setOrgCategoryGrade(String orgCategoryGrade) {
		this.orgCategoryGrade = orgCategoryGrade;
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
	public Integer getFirstOrgId() {
		return firstOrgId;
	}
	public void setFirstOrgId(Integer firstOrgId) {
		this.firstOrgId = firstOrgId;
	}
	public Integer getSecondOrgId() {
		return secondOrgId;
	}
	public void setSecondOrgId(Integer secondOrgId) {
		this.secondOrgId = secondOrgId;
	}
	public Integer getThirdOrgId() {
		return thirdOrgId;
	}
	public void setThirdOrgId(Integer thirdOrgId) {
		this.thirdOrgId = thirdOrgId;
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public Integer getFranchiseeId() {
		return franchiseeId;
	}
	public void setFranchiseeId(Integer franchiseeId) {
		this.franchiseeId = franchiseeId;
	}
	public String getActivityIds() {
		return activityIds;
	}
	public void setActivityIds(String activityIds) {
		this.activityIds = activityIds;
	}
	public Integer getSortFlag() {
		return sortFlag;
	}
	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}

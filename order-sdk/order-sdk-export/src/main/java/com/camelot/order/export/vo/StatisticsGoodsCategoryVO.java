package com.camelot.order.export.vo;

import java.io.Serializable;

/**
 * <p>Description: [有效订单整车分类统计]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName StatisticsGoodsCategoryVO.java
 * Created on 2019年4月13日.
 */
public class StatisticsGoodsCategoryVO implements Serializable {
	
	/**大区名称*/
	private String firstOrgName;
	/**区域名称*/
	private String secondOrgName;
	/**城市名称*/
	private String thirdOrgName;
	/**合伙人简称*/
	private String partnerName;
	/**加盟商简称*/
	private String franchiseeName;
	/**二级分类名称*/
	private String goodsSecondCategoryName;
	/**三级分类名称*/
	private String goodsThirdCategoryName;
	/**属性名称*/
	private String goodsAttributeName;
	/**门店名称*/
	private String storeName;
	/**消费者来源*/
	private String customerSource = "全部";
	/**销售订单数*/
	private Integer salesOrderAmount;
	
	public String getFirstOrgName() {
		return firstOrgName;
	}
	public void setFirstOrgName(String firstOrgName) {
		this.firstOrgName = firstOrgName;
	}
	public String getSecondOrgName() {
		return secondOrgName;
	}
	public void setSecondOrgName(String secondOrgName) {
		this.secondOrgName = secondOrgName;
	}
	public String getThirdOrgName() {
		return thirdOrgName;
	}
	public void setThirdOrgName(String thirdOrgName) {
		this.thirdOrgName = thirdOrgName;
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
	public String getGoodsAttributeName() {
		return goodsAttributeName;
	}
	public void setGoodsAttributeName(String goodsAttributeName) {
		this.goodsAttributeName = goodsAttributeName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getCustomerSource() {
		return customerSource;
	}
	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	public Integer getSalesOrderAmount() {
		return salesOrderAmount;
	}
	public void setSalesOrderAmount(Integer salesOrderAmount) {
		this.salesOrderAmount = salesOrderAmount;
	}
}

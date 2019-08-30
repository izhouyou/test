package com.camelot.order.export.vo.param;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.camelot.order.common.utils.Utility;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 查询条件基础参数</p>
 * @author zhouyou
 * @date 2019年5月14日
 */
public class BaseParamVO extends BaseVO implements Serializable {
	
	/** 开始时间*/
	@ApiModelProperty(value = "开始时间", required = false)
	private Date startDate;
	/** 结束时间*/
	@ApiModelProperty(value = "结束时间", required = false)
	private Date endDate;
	/** 订单编号*/
	@ApiModelProperty(value = "订单编号", required = false)
	private String salesOrderNumber;
	/** 订单状态*/
	private Integer salesOrderStatusId;
	/** 订单状态id集合 */
	private List<Integer> salesOrderStatusList;
	/** 销售订单IdSet */
	private HashSet<Integer> salesOrderIdSet;
	/** 门店ids(查询条件)*/
	@ApiModelProperty(value = "门店ids(查询条件)", required = false)
	private String storeIds;
	/** 门店id(查询条件)*/
	@ApiModelProperty(value = "门店id(查询条件)", required = false)
	private Integer storeId;
	/** 付款状态id */
	private Integer paymentStatusId;

	/** 附加信息 目前用于全部显示的行*/
	@ApiModelProperty(value = "附加信息", required = false)
	private String extraData;

	/** 门店区域搜索条件*/
	@ApiModelProperty(value = "区域搜索条件", required = false)
	private String orgSearch;

	/** 大区id */
	@ApiModelProperty(value = "大区id ", required = false)
	private Integer firstOrgId;

	/** 区域id */
	@ApiModelProperty(value = "区域id ", required = false)
	private Integer secondOrgId;

	/** 城市id */
	@ApiModelProperty(value = "城市id ", required = false)
	private Integer thirdOrgId;

	public Integer getFirstOrgId() {
		return firstOrgId;
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

	public void setFirstOrgId(Integer firstOrgId) {
		this.firstOrgId = firstOrgId;
	}

	public String getOrgSearch() {
		return orgSearch;
	}

	public void setOrgSearch(String orgSearch) {
		this.orgSearch = orgSearch;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}
	public Integer getSalesOrderStatusId() {
		return salesOrderStatusId;
	}
	public void setSalesOrderStatusId(Integer salesOrderStatusId) {
		this.salesOrderStatusId = salesOrderStatusId;
	}
	public List<Integer> getSalesOrderStatusList() {
		return salesOrderStatusList;
	}
	public void setSalesOrderStatusList(List<Integer> salesOrderStatusList) {
		this.salesOrderStatusList = salesOrderStatusList;
	}
	public HashSet<Integer> getSalesOrderIdSet() {
		return salesOrderIdSet;
	}
	public void setSalesOrderIdSet(HashSet<Integer> salesOrderIdSet) {
		this.salesOrderIdSet = salesOrderIdSet;
	}
	public String getStoreIds() {
		return storeIds;
	}
	public void setStoreIds(String storeIds) {
		this.storeIds = storeIds;
	}
	public Integer getPaymentStatusId() {
		return paymentStatusId;
	}
	public void setPaymentStatusId(Integer paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getExtraData() {
		return extraData;
	}
	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}
}

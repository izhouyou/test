package com.camelot.order.export.vo.param;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 零售活动报表查询参数</p>
 * @author zhouyou
 * @date 2019年5月16日
 */
public class ActivityReportPramVO extends BaseParamVO implements Serializable {
	
	/**活动主键id*/
    private Integer activityId;
	/** 活动编号*/
	@ApiModelProperty(value = "活动编号", required = false)
	private String activityNumber;
	/** 活动名称*/
	@ApiModelProperty(value = "活动名称", required = false)
	private String activityName;
	/** 门店名称*/
	@ApiModelProperty(value = "门店名称", required = false)
	private String storeName;
	/** 门店编号*/
	@ApiModelProperty(value = "门店编号", required = false)
	private String storeNumber;
	/** 0全部订单,null整车 */
	private Integer allFlag;
	
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
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
	public Integer getAllFlag() {
		return allFlag;
	}
	public void setAllFlag(Integer allFlag) {
		this.allFlag = allFlag;
	}
	
}

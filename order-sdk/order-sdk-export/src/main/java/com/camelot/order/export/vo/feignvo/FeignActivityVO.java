package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;

public class FeignActivityVO implements Serializable {
	
	/**活动主键id*/
    private Integer tActivityId;
    /**活动名称*/
    private String activityName;
    /**活动码id*/
    private String activityId;
    /**优惠码*/
    private String couponCode;
    
	public Integer gettActivityId() {
		return tActivityId;
	}
	public void settActivityId(Integer tActivityId) {
		this.tActivityId = tActivityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
    
}

package com.camelot.order.export.vo.param;

import java.io.Serializable;
import java.util.List;

import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.vo.elasticsearch.OperationLog;

import io.swagger.annotations.ApiModelProperty;

public class BaseVO extends OperationLog implements Serializable {
	
	/** 门店idStr*/
	@ApiModelProperty(value = "门店idStr", required = false)
	private String storeStr;
	/** 城市idStr*/
	@ApiModelProperty(value = "城市idStr", required = false)
	private String orgStr;
	/** 门店idSet*/
	private List<Integer> storeIdList;
	/** 区域idSet*/
	private List<Integer> orgIdList;
	
	public String getStoreStr() {
		return storeStr;
	}
	public void setStoreStr(String storeStr) {
		this.storeStr = storeStr;
		this.storeIdList = Utility.stringToList(storeStr);
	}
	public String getOrgStr() {
		return orgStr;
	}
	public void setOrgStr(String orgStr) {
		this.orgStr = orgStr;
		this.orgIdList = Utility.stringToList(orgStr);
	}
	public List<Integer> getStoreIdList() {
		return storeIdList;
	}
	public void setStoreIdList(List<Integer> storeIdList) {
		this.storeIdList = storeIdList;
	}
	public List<Integer> getOrgIdList() {
		return orgIdList;
	}
	public void setOrgIdList(List<Integer> orgIdList) {
		this.orgIdList = orgIdList;
	}

}

package com.camelot.order.export.vo.param;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>Title: StatsOrderParamVO</p>
 * <p>Description: [销售统计查询参数]</p>
 * <p>Company: </p>
 * @author cuijiudong
 * @date 2019年6月17日
 * @version 1.0
 */
public class StatsOrderParamVO extends BaseParamVO implements Serializable {
	
	/** 门店名称*/
	@ApiModelProperty(value = "门店名称", required = false)
	private String storeName;
	/** 门店编号*/
	@ApiModelProperty(value = "门店编号", required = false)
	private String storeNumber;



	
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


}

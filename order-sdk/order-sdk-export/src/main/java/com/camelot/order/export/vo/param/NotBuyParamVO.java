package com.camelot.order.export.vo.param;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Title: NotBuyParamVO</p>
 * <p>Description: [未购买上报统计查询参数]</p>
 * <p>Company: Camelot</p>
 * @author zhouyou
 * @date 2019年5月22日
 * @version 1.0
 */
public class NotBuyParamVO extends BaseParamVO implements Serializable {
	
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

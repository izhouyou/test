package com.camelot.order.export.vo;

import java.io.Serializable;

public class StatisticsSalesVO implements Serializable {
	/**门店编号*/
	private String storeNumber;

	/**门店名称*/
	private String storeName;

	/**销售个数*/
	private Integer saleAmount;

	/**门店id*/
	private Integer storeId;

	/** 门店渠道编码*/
	private String storeChannelNumber;

	/** 附加信息 */
	private String extraData;

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public String getStoreChannelNumber() {
		return storeChannelNumber;
	}

	public void setStoreChannelNumber(String storeChannelNumber) {
		this.storeChannelNumber = storeChannelNumber;
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

	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public Integer getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(Integer saleAmount) {
		this.saleAmount = saleAmount;
	}
}

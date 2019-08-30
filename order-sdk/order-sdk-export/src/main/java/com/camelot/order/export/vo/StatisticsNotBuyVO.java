package com.camelot.order.export.vo;

import java.io.Serializable;

public class StatisticsNotBuyVO implements Serializable {
	/**门店编号*/
	private String storeNumber;
	/**门店名称*/
	private String storeName;
	/**未购买量*/
	private Integer notBuyAmount;
	/**门店id*/
	private Integer storeId;

	/**渠道编号**/
	private String storeChannelNumber;

	/**销售个数*/
	private Integer saleAmount;

	/** 附加信息 */
	private String extraData;
	
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
	public Integer getNotBuyAmount() {
		return notBuyAmount;
	}
	public void setNotBuyAmount(Integer notBuyAmount) {
		this.notBuyAmount = notBuyAmount;
		this.saleAmount = notBuyAmount;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreChannelNumber() {
		return storeChannelNumber;
	}

	public void setStoreChannelNumber(String storeChannelNumber) {
		this.storeChannelNumber = storeChannelNumber;
	}

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}
}

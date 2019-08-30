package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;
import java.util.Date;

public class FeignStoreVO implements Serializable{

	/**主键id*/
	private Integer storeId;
	/**门店编码*/
	private String storeNumber;
	/**门店名称*/
	private String storeName;
	
	public Integer getStoreId(){
		return storeId;
	}
	
	public void setStoreId(Integer storeId){
		this.storeId = storeId;
	}

	public String getStoreNumber(){
		return storeNumber;
	}
	
	public void setStoreNumber(String storeNumber){
		this.storeNumber = storeNumber;
	}

	public String getStoreName(){
		return storeName;
	}
	
	public void setStoreName(String storeName){
		this.storeName = storeName;
	}


}

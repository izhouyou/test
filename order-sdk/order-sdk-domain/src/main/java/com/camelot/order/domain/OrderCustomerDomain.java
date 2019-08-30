package com.camelot.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderCustomerDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 消费者关联订单数量
	 * 
	 */
	private Integer numberTotal;
	
	/**
	 * 消费者关联订单金额
	 * 
	 */
	private BigDecimal priceTotal;
	
	/**
	 * 消费者ID
	 * 
	 */
	private Integer customerId;
	
	
	/**
	 * 订单状态
	 * 
	 */
	private Integer salesOrderStatus;
	
	/**
	 * 门店ID集合
	 * 
	 */
	private List<Integer> storeIdList;
	/**
	 * 城市ID集合
	 *
	 */
	private List<Integer> orgIdList;

	public List<Integer> getOrgIdList() {
		return orgIdList;
	}

	public void setOrgIdList(List<Integer> orgIdList) {
		this.orgIdList = orgIdList;
	}

	public Integer getNumberTotal() {
		return numberTotal;
	}

	public void setNumberTotal(Integer numberTotal) {
		this.numberTotal = numberTotal;
	}

	public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public Integer getSalesOrderStatus() {
		return salesOrderStatus;
	}

	public void setSalesOrderStatus(Integer salesOrderStatus) {
		this.salesOrderStatus = salesOrderStatus;
	}

	public List<Integer> getStoreIdList() {
		return storeIdList;
	}

	public void setStoreIdList(List<Integer> storeIdList) {
		this.storeIdList = storeIdList;
	}
	
	
}

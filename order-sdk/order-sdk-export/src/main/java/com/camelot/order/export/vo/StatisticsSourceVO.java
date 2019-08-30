package com.camelot.order.export.vo;

import java.io.Serializable;

public class StatisticsSourceVO implements Serializable {
	
	/** 名称*/
	private String name;
	/** 数量统计*/
	private Integer amount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}

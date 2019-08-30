package com.camelot.order.export.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Utility;

public class StatisticsFigureVO implements Serializable {
	/** 日期*/
	private String statisticsDate;
	/** 订单数 */
	private Integer statisticsOrderAmount = 0;
	/** 销量值 */
	private BigDecimal statisticsAmount = new BigDecimal(0);
	/** 销量值 */
	private String statisticsValueAmount = "0.00";
	/** 移动端日期*/
	private String statisticsDay;
	/** 数据时间段*/
	private String period;
	/** 销量值(万) */
	private BigDecimal wstatisticsAmount = new BigDecimal(0);
	/** 销量值(万) */
	private String wstatisticsValueAmount = "0.0" + OrderConstants.MONEY_UNIT;
	
	public Integer getStatisticsOrderAmount() {
		return statisticsOrderAmount;
	}
	public void setStatisticsOrderAmount(Integer statisticsOrderAmount) {
		this.statisticsOrderAmount = statisticsOrderAmount;
	}
	public String getStatisticsDate() {
		return statisticsDate;
	}
	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}
	public BigDecimal getStatisticsAmount() {
		return statisticsAmount;
	}
	public void setStatisticsAmount(BigDecimal statisticsAmount) {
		this.statisticsAmount = statisticsAmount;
	}
	public String getStatisticsDay() {
		return statisticsDay;
	}
	public void setStatisticsDay(String statisticsDay) {
		this.statisticsDay = statisticsDay;
	}
	public String getStatisticsValueAmount() {
		return statisticsValueAmount;
	}
	public void setStatisticsValueAmount(String statisticsValueAmount) {
		this.statisticsValueAmount = statisticsValueAmount;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public BigDecimal getWstatisticsAmount() {
		return wstatisticsAmount;
	}
	public void setWstatisticsAmount(BigDecimal wstatisticsAmount) {
		this.wstatisticsAmount = wstatisticsAmount;
		String wstatisticsValueAmountTemp = Utility.bigDecimalToString(wstatisticsAmount);
		this.wstatisticsValueAmount = wstatisticsValueAmountTemp.substring(0, wstatisticsValueAmountTemp.length()-1) + OrderConstants.MONEY_UNIT;
	}
	public String getWstatisticsValueAmount() {
		return wstatisticsValueAmount;
	}
	public void setWstatisticsValueAmount(String wstatisticsValueAmount) {
		this.wstatisticsValueAmount = wstatisticsValueAmount;
	}
	
}

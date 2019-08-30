package com.camelot.order.export.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Utility;

public class SalesVolumeVO implements Serializable {
	
	/**销售总数*/
	private Integer salesOrderTotal = 0;
	/**销售总额*/
	private BigDecimal salesAmountTotal = new BigDecimal(0);
	/** 销售总额Value*/
	private String salesAmountValueTotal = "0.00";
	/**退货额*/
	private BigDecimal returnAmountTotal = new BigDecimal(0);
	/** 退货额Value*/
	private String returnAmountValueTotal = "0.00";
	/**整车销量*/
	private Integer vehicleTotal = 0;
	/**周边产品销量*/
	private Integer merchTotal = 0;
	/** 退货订单数量 */
	private Integer returnOrderTotal = 0;
	/**有效销售总额 */
	private String validSalesAmountValueTotal = "0.00";
	/**销售总额*/
	private BigDecimal wsalesAmountTotal = new BigDecimal(0);
	/**有效销售总额(万) */
	private String wvalidSalesAmountValueTotal = "0.0" + OrderConstants.MONEY_UNIT;
	/**退货额*/
	private BigDecimal wreturnAmountTotal = new BigDecimal(0);
	/** 退货额Value(万)*/
	private String wreturnAmountValueTotal = "0.0" + OrderConstants.MONEY_UNIT;
	/** 销售总额Value(万)*/
	private String wsalesAmountValueTotal = "0.0" + OrderConstants.MONEY_UNIT;
	
	public Integer getSalesOrderTotal() {
		return salesOrderTotal;
	}
	public void setSalesOrderTotal(Integer salesOrderTotal) {
		this.salesOrderTotal = salesOrderTotal;
	}
	public BigDecimal getSalesAmountTotal() {
		return salesAmountTotal;
	}
	public void setSalesAmountTotal(BigDecimal salesAmountTotal) {
		this.salesAmountTotal = salesAmountTotal;
		this.salesAmountValueTotal = Utility.bigDecimalToString(salesAmountTotal);
		BigDecimal wsalesAmountTotalTemp = salesAmountTotal.divide(new BigDecimal(10000));
		wsalesAmountTotalTemp = wsalesAmountTotalTemp.setScale(1, BigDecimal.ROUND_HALF_UP);
		setWsalesAmountTotal(wsalesAmountTotalTemp);
	}
	public BigDecimal getReturnAmountTotal() {
		return returnAmountTotal;
	}
	public void setReturnAmountTotal(BigDecimal returnAmountTotal) {
		this.returnAmountTotal = returnAmountTotal;
		this.returnAmountValueTotal = Utility.bigDecimalToString(returnAmountTotal);
		BigDecimal wreturnTotalTemp = returnAmountTotal.divide(new BigDecimal(10000));
		wreturnTotalTemp = wreturnTotalTemp.setScale(1, BigDecimal.ROUND_HALF_UP);
		setWreturnAmountTotal(wreturnTotalTemp);
	}
	public Integer getVehicleTotal() {
		return vehicleTotal;
	}
	public void setVehicleTotal(Integer vehicleTotal) {
		this.vehicleTotal = vehicleTotal;
	}
	public Integer getMerchTotal() {
		return merchTotal;
	}
	public void setMerchTotal(Integer merchTotal) {
		this.merchTotal = merchTotal;
	}
	public String getSalesAmountValueTotal() {
		return salesAmountValueTotal;
	}
	public void setSalesAmountValueTotal(String salesAmountValueTotal) {
		this.salesAmountValueTotal = salesAmountValueTotal;
	}
	public String getReturnAmountValueTotal() {
		return returnAmountValueTotal;
	}
	public void setReturnAmountValueTotal(String returnAmountValueTotal) {
		this.returnAmountValueTotal = returnAmountValueTotal;
	}
	public Integer getReturnOrderTotal() {
		return returnOrderTotal;
	}
	public void setReturnOrderTotal(Integer returnOrderTotal) {
		this.returnOrderTotal = returnOrderTotal;
	}
	public String getValidSalesAmountValueTotal() {
		return validSalesAmountValueTotal;
	}
	public void setValidSalesAmountValueTotal(String validSalesAmountValueTotal) {
		this.validSalesAmountValueTotal = validSalesAmountValueTotal;
	}
	public BigDecimal getWsalesAmountTotal() {
		return wsalesAmountTotal;
	}
	public void setWsalesAmountTotal(BigDecimal wsalesAmountTotal) {
		this.wsalesAmountTotal = wsalesAmountTotal;
		String str = Utility.bigDecimalToString(wsalesAmountTotal);
		this.wsalesAmountValueTotal = str.substring(0, str.length()-1) + OrderConstants.MONEY_UNIT;
	}
	public String getWvalidSalesAmountValueTotal() {
		return wvalidSalesAmountValueTotal;
	}
	public void setWvalidSalesAmountValueTotal(String wvalidSalesAmountValueTotal) {
		this.wvalidSalesAmountValueTotal = wvalidSalesAmountValueTotal;
	}
	public BigDecimal getWreturnAmountTotal() {
		return wreturnAmountTotal;
	}
	public void setWreturnAmountTotal(BigDecimal wreturnAmountTotal) {
		this.wreturnAmountTotal = wreturnAmountTotal;
		String str = Utility.bigDecimalToString(wreturnAmountTotal);
		this.wreturnAmountValueTotal = str.substring(0, str.length()-1) + OrderConstants.MONEY_UNIT;
	}
	public String getWreturnAmountValueTotal() {
		return wreturnAmountValueTotal;
	}
	public void setWreturnAmountValueTotal(String wreturnAmountValueTotal) {
		this.wreturnAmountValueTotal = wreturnAmountValueTotal;
	}
	public String getWsalesAmountValueTotal() {
		return wsalesAmountValueTotal;
	}
	public void setWsalesAmountValueTotal(String wsalesAmountValueTotal) {
		this.wsalesAmountValueTotal = wsalesAmountValueTotal;
	}
	
}

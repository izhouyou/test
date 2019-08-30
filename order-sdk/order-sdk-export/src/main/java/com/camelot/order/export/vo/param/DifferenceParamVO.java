package com.camelot.order.export.vo.param;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 零售差异订单查询参数</p>
 * @author zhouyou
 * @date 2019年5月14日
 */
public class DifferenceParamVO extends BaseParamVO implements Serializable {
	
	/** 最小金额*/
	@ApiModelProperty(value = "最小金额", required = false)
	private BigDecimal minAmount;
	/** 最大金额*/
	@ApiModelProperty(value = "最大金额", required = false)
	private BigDecimal maxAmount;
	
	public BigDecimal getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}
	public BigDecimal getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

}

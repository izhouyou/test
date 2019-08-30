package com.camelot.order.export.vo.param;

import java.io.Serializable;
import java.util.regex.Pattern;

import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Utility;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 门店销售报表查询参数</p>
 * @author zhouyou
 * @date 2019年5月16日
 */
public class SalesReportParamVO extends BaseParamVO implements Serializable {
	
	/** 活动编号或名称 */
	@ApiModelProperty(value = "活动编号或名称", required = false)
	private String codeOrName;
	/** 消费者Id */
	@ApiModelProperty(value = "消费者Id", required = false)
	private Long customerId;
	/** 活动编号 */
	@ApiModelProperty(value = "活动编号", required = false)
	private String activityNumber;
	/** 活动名称 */
	@ApiModelProperty(value = "活动名称", required = false)
	private String activityName;
	/** 销售订单状态 */
	@ApiModelProperty(value = "销售订单状态(已提交|已取消|已完成)", required = false)
	private Integer salesOrderStatus;
	/** 销售订单-退货状态 id*/
	@ApiModelProperty(value = "销售订单-退货状态(已退货|未退货)", required = false)
	private Integer returnStatusId;
	/** 销售订单-付款状态id */
	@ApiModelProperty(value = "销售订单-支付状态(已支付|未支付)", required = false)
	private Integer paymentStatusId;
	/**商品sn码*/
	@ApiModelProperty(value = "商品sn码", required = false)
	private String goodsSn;
	/**整车车架号*/
	@ApiModelProperty(value = "整车车架号", required = false)
	private String goodsFrameNumber;
	
	public String getCodeOrName() {
		return codeOrName;
	}
	public void setCodeOrName(String codeOrName) {
		this.codeOrName = codeOrName;
//		if(Utility.isNotEmpty(codeOrName)) {
//			String regEx = OrderConstants.ACTIVITY_CODE_PREFIX+".*";
//			if(Pattern.matches(regEx, codeOrName)) {
//				this.activityNumber = codeOrName;
//			} else {
//				this.activityName = codeOrName;
//			}
//		}
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getActivityNumber() {
		return activityNumber;
	}
	public void setActivityNumber(String activityNumber) {
		this.activityNumber = activityNumber;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Integer getSalesOrderStatus() {
		return salesOrderStatus;
	}
	public void setSalesOrderStatus(Integer salesOrderStatus) {
		this.salesOrderStatus = salesOrderStatus;
	}
	public Integer getReturnStatusId() {
		return returnStatusId;
	}
	public void setReturnStatusId(Integer returnStatusId) {
		this.returnStatusId = returnStatusId;
	}
	@Override
    public Integer getPaymentStatusId() {
		return paymentStatusId;
	}
	@Override
    public void setPaymentStatusId(Integer paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getGoodsFrameNumber() {
		return goodsFrameNumber;
	}
	public void setGoodsFrameNumber(String goodsFrameNumber) {
		this.goodsFrameNumber = goodsFrameNumber;
	}

}

package com.camelot.order.domain;


import java.io.Serializable;
import java.util.Date;
/**
 * @author hudya
 */
public class NewSalesOrderLogDomain implements Serializable {

	/**主键ID*/
	private Long salesOrderLogId;
	/**销售订单id*/
	private Long salesOrderId;
	/**销售订单编号*/
	private String salesOrderNumber;
	/**退单id*/
	private Long returnOrderId;
	/**退单编号*/
	private String returnOrderNumber;
	/**操作人id*/
	private Integer operatorId;
	/**操作人编号*/
	private String operatorNumber;
	/**操作人名称*/
	private String operatorName;
	/**操作人手机号*/
	private String operatorPhone;
	/**订单状态*/
	private Integer salesOrderStatusId;
	/**订单状态编码*/
	private String salesOrderStatusNumber;
	/**订单状态名称(已提交;已取消;已完成)*/
	private String salesOrderStatusName;
	/**付款状态id*/
	private Integer paymentStatusId;
	/**付款状态编码*/
	private String paymentStatusNumber;
	/**付款状态名称(已支付;未支付)*/
	private String paymentStatusName;
	/**退货状态id*/
	private Integer returnStatusId;
	/**退货状态编码*/
	private String returnStatusNumber;
	/**退货状态名称(已退货;未退货)*/
	private String returnStatusName;
	/**操作时间*/
	private Date operationTime;
	/**删除标记(1为删除;0为正常)*/
	private Integer delFlg;
	
	public Long getSalesOrderLogId(){
		return salesOrderLogId;
	}
	
	public void setSalesOrderLogId(Long salesOrderLogId){
		this.salesOrderLogId = salesOrderLogId;
	}

	public Long getSalesOrderId(){
		return salesOrderId;
	}
	
	public void setSalesOrderId(Long salesOrderId){
		this.salesOrderId = salesOrderId;
	}

	public String getSalesOrderNumber(){
		return salesOrderNumber;
	}
	
	public void setSalesOrderNumber(String salesOrderNumber){
		this.salesOrderNumber = salesOrderNumber;
	}

	public Long getReturnOrderId(){
		return returnOrderId;
	}
	
	public void setReturnOrderId(Long returnOrderId){
		this.returnOrderId = returnOrderId;
	}

	public String getReturnOrderNumber(){
		return returnOrderNumber;
	}
	
	public void setReturnOrderNumber(String returnOrderNumber){
		this.returnOrderNumber = returnOrderNumber;
	}

	public Integer getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(Integer operatorId){
		this.operatorId = operatorId;
	}

	public String getOperatorNumber(){
		return operatorNumber;
	}
	
	public void setOperatorNumber(String operatorNumber){
		this.operatorNumber = operatorNumber;
	}

	public String getOperatorName(){
		return operatorName;
	}
	
	public void setOperatorName(String operatorName){
		this.operatorName = operatorName;
	}

	public String getOperatorPhone(){
		return operatorPhone;
	}
	
	public void setOperatorPhone(String operatorPhone){
		this.operatorPhone = operatorPhone;
	}

	public Integer getSalesOrderStatusId(){
		return salesOrderStatusId;
	}
	
	public void setSalesOrderStatusId(Integer salesOrderStatusId){
		this.salesOrderStatusId = salesOrderStatusId;
	}

	public String getSalesOrderStatusNumber(){
		return salesOrderStatusNumber;
	}
	
	public void setSalesOrderStatusNumber(String salesOrderStatusNumber){
		this.salesOrderStatusNumber = salesOrderStatusNumber;
	}

	public String getSalesOrderStatusName(){
		return salesOrderStatusName;
	}
	
	public void setSalesOrderStatusName(String salesOrderStatusName){
		this.salesOrderStatusName = salesOrderStatusName;
	}

	public Integer getPaymentStatusId(){
		return paymentStatusId;
	}
	
	public void setPaymentStatusId(Integer paymentStatusId){
		this.paymentStatusId = paymentStatusId;
	}

	public String getPaymentStatusNumber(){
		return paymentStatusNumber;
	}
	
	public void setPaymentStatusNumber(String paymentStatusNumber){
		this.paymentStatusNumber = paymentStatusNumber;
	}

	public String getPaymentStatusName(){
		return paymentStatusName;
	}
	
	public void setPaymentStatusName(String paymentStatusName){
		this.paymentStatusName = paymentStatusName;
	}

	public Integer getReturnStatusId(){
		return returnStatusId;
	}
	
	public void setReturnStatusId(Integer returnStatusId){
		this.returnStatusId = returnStatusId;
	}

	public String getReturnStatusNumber(){
		return returnStatusNumber;
	}
	
	public void setReturnStatusNumber(String returnStatusNumber){
		this.returnStatusNumber = returnStatusNumber;
	}

	public String getReturnStatusName(){
		return returnStatusName;
	}
	
	public void setReturnStatusName(String returnStatusName){
		this.returnStatusName = returnStatusName;
	}

	public java.util.Date getOperationTime(){
		return operationTime;
	}
	
	public void setOperationTime(java.util.Date operationTime){
		this.operationTime = operationTime;
	}

	public Integer getDelFlg(){
		return delFlg;
	}
	
	public void setDelFlg(Integer delFlg){
		this.delFlg = delFlg;
	}

}

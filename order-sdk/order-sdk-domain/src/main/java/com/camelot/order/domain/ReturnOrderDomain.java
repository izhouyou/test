package com.camelot.order.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReturnOrderDomain implements Serializable {

	/**主键*/
	private Integer returnOrderId;
	/**订单编号*/
	private String returnOrderNumber;
	/**对应销售订单ID*/
	private Integer salesOrderId;
	/**订单金额*/
	private java.math.BigDecimal returnOrderAmount;
	/**订单状态（字典id）*/
	private Integer returnOrderState;
	/**退货原因(字典id)*/
	private Integer returnReason;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Integer createUserId;
	/**修改人id*/
	private Integer updateUserId;
	/**创建时间*/
	private Date createDate;
	/**修改时间*/
	private Date modifyDate;
	/**开始搜索时间*/
	private Date beginSearchDate;
	/**截止搜索时间*/
	private Date endSearchDate;
	/**销售订单ID集合*/
	private List<Integer> salesOrderIdList; 
	
	public Integer getReturnOrderId(){
		return returnOrderId;
	}
	
	public void setReturnOrderId(Integer returnOrderId){
		this.returnOrderId = returnOrderId;
	}

	public String getReturnOrderNumber(){
		return returnOrderNumber;
	}
	
	public void setReturnOrderNumber(String returnOrderNumber){
		this.returnOrderNumber = returnOrderNumber;
	}

	public Integer getSalesOrderId(){
		return salesOrderId;
	}
	
	public void setSalesOrderId(Integer salesOrderId){
		this.salesOrderId = salesOrderId;
	}

	public java.math.BigDecimal getReturnOrderAmount(){
		return returnOrderAmount;
	}
	
	public void setReturnOrderAmount(java.math.BigDecimal returnOrderAmount){
		this.returnOrderAmount = returnOrderAmount;
	}

	public Integer getReturnOrderState(){
		return returnOrderState;
	}
	
	public void setReturnOrderState(Integer returnOrderState){
		this.returnOrderState = returnOrderState;
	}

	public Integer getReturnReason(){
		return returnReason;
	}
	
	public void setReturnReason(Integer returnReason){
		this.returnReason = returnReason;
	}

	public Integer getDelFlg(){
		return delFlg;
	}
	
	public void setDelFlg(Integer delFlg){
		this.delFlg = delFlg;
	}

	public Integer getCreateUserId(){
		return createUserId;
	}
	
	public void setCreateUserId(Integer createUserId){
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId(){
		return updateUserId;
	}
	
	public void setUpdateUserId(Integer updateUserId){
		this.updateUserId = updateUserId;
	}

	public Date getCreateDate(){
		return createDate;
	}
	
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getModifyDate(){
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}

	public Date getBeginSearchDate() {
		return beginSearchDate;
	}

	public void setBeginSearchDate(Date beginSearchDate) {
		this.beginSearchDate = beginSearchDate;
	}

	public Date getEndSearchDate() {
		return endSearchDate;
	}

	public void setEndSearchDate(Date endSearchDate) {
		this.endSearchDate = endSearchDate;
	}

	public List<Integer> getSalesOrderIdList() {
		return salesOrderIdList;
	}

	public void setSalesOrderIdList(List<Integer> salesOrderIdList) {
		this.salesOrderIdList = salesOrderIdList;
	}

}

package com.camelot.order.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NotBuyDomain implements Serializable {

	/**主键*/
	private Integer notBuyId;
	/**顾客id*/
	private Integer customerId;
	/**意向车型*/
	private Integer intentionVehicle;
	/**门店id*/
	private Integer storeId;
	/**未购买原因(字典id)*/
	private Integer notBuyReason;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Integer createUserId;
	/**修改人id*/
	private Integer updateUserId;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改时间*/
	private java.util.Date modifyDate;
	/**开始搜索时间*/
	private Date beginSearchDate;
	/**截止搜索时间*/
	private Date endSearchDate;
	/**门店ID集合*/
	private List<Integer> storeIdList;
	
	public Integer getNotBuyId(){
		return notBuyId;
	}
	
	public void setNotBuyId(Integer notBuyId){
		this.notBuyId = notBuyId;
	}

	public Integer getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId(Integer customerId){
		this.customerId = customerId;
	}

	public Integer getStoreId(){
		return storeId;
	}
	
	public void setStoreId(Integer storeId){
		this.storeId = storeId;
	}

	public Integer getNotBuyReason(){
		return notBuyReason;
	}
	
	public void setNotBuyReason(Integer notBuyReason){
		this.notBuyReason = notBuyReason;
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

	public java.util.Date getCreateDate(){
		return createDate;
	}
	
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	public java.util.Date getModifyDate(){
		return modifyDate;
	}
	
	public void setModifyDate(java.util.Date modifyDate){
		this.modifyDate = modifyDate;
	}

	public Integer getIntentionVehicle() {
		return intentionVehicle;
	}

	public void setIntentionVehicle(Integer intentionVehicle) {
		this.intentionVehicle = intentionVehicle;
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

	public List<Integer> getStoreIdList() {
		return storeIdList;
	}

	public void setStoreIdList(List<Integer> storeIdList) {
		this.storeIdList = storeIdList;
	}

}

package com.camelot.order.feign.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

public class WarehouseVO implements Serializable{

	/**主键id*/
	private Long id;
	/**仓库名称*/
	private String warehouseName;
	/**仓库编码*/
	private String warehouseCode;
	/**仓库类型（1：小牛仓库；2：合伙人DC仓库；3：加盟商DC仓库；4：合伙人门店仓；5：加盟商门店仓）*/
	private Integer warehouseType;
	/**仓库地址*/
	private String warehouseAddress;
	/**所属机构*/
	private String orgName;
	/**所属机构编码*/
	private String orgCode;
	/**禁用 启用状态（0代表启用； 1代表禁用）*/
	private Integer disablelFlg;
	/**创建人id*/
	private Long createUserId;
	/**创建人姓名*/
	private String createUserName;
	/**修改人id*/
	private Long updateUserId;
	/**修改人姓名*/
	private String updateUserName;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private java.util.Date createDate;
	/**修改时间*/
	private java.util.Date modifyDate;
	/**门店ID*/
	private Integer storesId;
	/**门店编号*/
	private String storesNumber;
	/**门店名称*/
	private String storesName;
	/**备注*/
	private String note;
	/**仓库类型值*/
	private String warehouseTypeValue;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public String getWarehouseName(){
		return warehouseName;
	}
	
	public void setWarehouseName(String warehouseName){
		this.warehouseName = warehouseName;
	}

	public String getWarehouseCode(){
		return warehouseCode;
	}
	
	public void setWarehouseCode(String warehouseCode){
		this.warehouseCode = warehouseCode;
	}

	public Integer getWarehouseType(){
		return warehouseType;
	}
	
	public void setWarehouseType(Integer warehouseType){
		this.warehouseType = warehouseType;
	}

	public String getWarehouseAddress(){
		return warehouseAddress;
	}
	
	public void setWarehouseAddress(String warehouseAddress){
		this.warehouseAddress = warehouseAddress;
	}

	public String getOrgName(){
		return orgName;
	}
	
	public void setOrgName(String orgName){
		this.orgName = orgName;
	}

	public String getOrgCode(){
		return orgCode;
	}
	
	public void setOrgCode(String orgCode){
		this.orgCode = orgCode;
	}

	public Integer getDisablelFlg(){
		return disablelFlg;
	}
	
	public void setDisablelFlg(Integer disablelFlg){
		this.disablelFlg = disablelFlg;
	}

	public Long getCreateUserId(){
		return createUserId;
	}
	
	public void setCreateUserId(Long createUserId){
		this.createUserId = createUserId;
	}

	public String getCreateUserName(){
		return createUserName;
	}
	
	public void setCreateUserName(String createUserName){
		this.createUserName = createUserName;
	}

	public Long getUpdateUserId(){
		return updateUserId;
	}
	
	public void setUpdateUserId(Long updateUserId){
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName(){
		return updateUserName;
	}
	
	public void setUpdateUserName(String updateUserName){
		this.updateUserName = updateUserName;
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

	public Integer getStoresId(){
		return storesId;
	}
	
	public void setStoresId(Integer storesId){
		this.storesId = storesId;
	}

	public String getStoresNumber(){
		return storesNumber;
	}
	
	public void setStoresNumber(String storesNumber){
		this.storesNumber = storesNumber;
	}

	public String getStoresName(){
		return storesName;
	}
	
	public void setStoresName(String storesName){
		this.storesName = storesName;
	}

	public String getNote(){
		return note;
	}
	
	public void setNote(String note){
		this.note = note;
	}

	public String getWarehouseTypeValue() {
		return warehouseTypeValue;
	}

	public void setWarehouseTypeValue(String warehouseTypeValue) {
		this.warehouseTypeValue = warehouseTypeValue;
	}
}

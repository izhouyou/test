package com.camelot.order.export.vo;

import java.io.Serializable;
import java.util.Date;

public class StoreVO implements Serializable{

	/**主键id*/
	private Integer storeId;
	/**门店编码*/
	private String storeNumber;
	/**门店名称*/
	private String storeName;
	/**门店性质code值*/
	private Integer storeNature;
	/**门店类型*/
	private Integer storeType;
	/**门店地址省份*/
	private Integer storeAddressProvice;
	/**门店地址市*/
	private Integer storeAddressCity;
	/**门店地址区*/
	private Integer storeAddressDistrict;
	/**门店地址详细地址*/
	private String storeAddressDetail;
	/**门店状态*/
	private Integer storeStaus;
	/**开业时间*/
	private Date storeOpenTime;
	/**闭店时间*/
	private Date storeCloseTime;
	/**门店面积*/
	private String storeAcreage;
	/**所属区域id(城市级别的)*/
	private Long storeBelongId;
	/**门店对应仓库主键id*/
	private Long warehouseId;
	/**门店负责人*/
	private String storeHead;
	/**门店负责人联系电话*/
	private String storeHeadPhone;
	/**门店负责人联系邮箱*/
	private String storeEmail;
	/**所属合伙人/加盟商id*/
	private Integer partnerId;
	/**创建时间*/
	private Date createDate;
	/**修改时间*/
	private Date modifyDate;
	/**操作人用户id*/
	private Long operatorId;
	/**删除标志:0是可用,1是不可用*/
	private Integer delFlg;
	/**区域信息*/
	private OrgVO orgVO;
	/**合伙人||加盟商信息*/
	private PartnerTraderVO partnerTraderVO;
	
	public Integer getStoreId(){
		return storeId;
	}
	
	public void setStoreId(Integer storeId){
		this.storeId = storeId;
	}

	public String getStoreNumber(){
		return storeNumber;
	}
	
	public void setStoreNumber(String storeNumber){
		this.storeNumber = storeNumber;
	}

	public String getStoreName(){
		return storeName;
	}
	
	public void setStoreName(String storeName){
		this.storeName = storeName;
	}

	public Integer getStoreNature(){
		return storeNature;
	}
	
	public void setStoreNature(Integer storeNature){
		this.storeNature = storeNature;
	}

	public Integer getStoreType(){
		return storeType;
	}
	
	public void setStoreType(Integer storeType){
		this.storeType = storeType;
	}

	public Integer getStoreAddressProvice(){
		return storeAddressProvice;
	}
	
	public void setStoreAddressProvice(Integer storeAddressProvice){
		this.storeAddressProvice = storeAddressProvice;
	}

	public Integer getStoreAddressCity(){
		return storeAddressCity;
	}
	
	public void setStoreAddressCity(Integer storeAddressCity){
		this.storeAddressCity = storeAddressCity;
	}

	public Integer getStoreAddressDistrict(){
		return storeAddressDistrict;
	}
	
	public void setStoreAddressDistrict(Integer storeAddressDistrict){
		this.storeAddressDistrict = storeAddressDistrict;
	}

	public String getStoreAddressDetail(){
		return storeAddressDetail;
	}
	
	public void setStoreAddressDetail(String storeAddressDetail){
		this.storeAddressDetail = storeAddressDetail;
	}

	public Integer getStoreStaus(){
		return storeStaus;
	}
	
	public void setStoreStaus(Integer storeStaus){
		this.storeStaus = storeStaus;
	}

	public java.util.Date getStoreOpenTime(){
		return storeOpenTime;
	}
	
	public void setStoreOpenTime(java.util.Date storeOpenTime){
		this.storeOpenTime = storeOpenTime;
	}

	public java.util.Date getStoreCloseTime(){
		return storeCloseTime;
	}
	
	public void setStoreCloseTime(java.util.Date storeCloseTime){
		this.storeCloseTime = storeCloseTime;
	}

	public String getStoreAcreage(){
		return storeAcreage;
	}
	
	public void setStoreAcreage(String storeAcreage){
		this.storeAcreage = storeAcreage;
	}

	public Long getStoreBelongId(){
		return storeBelongId;
	}
	
	public void setStoreBelongId(Long storeBelongId){
		this.storeBelongId = storeBelongId;
	}

	public Long getWarehouseId(){
		return warehouseId;
	}
	
	public void setWarehouseId(Long warehouseId){
		this.warehouseId = warehouseId;
	}

	public String getStoreHead(){
		return storeHead;
	}
	
	public void setStoreHead(String storeHead){
		this.storeHead = storeHead;
	}

	public String getStoreHeadPhone(){
		return storeHeadPhone;
	}
	
	public void setStoreHeadPhone(String storeHeadPhone){
		this.storeHeadPhone = storeHeadPhone;
	}

	public String getStoreEmail(){
		return storeEmail;
	}
	
	public void setStoreEmail(String storeEmail){
		this.storeEmail = storeEmail;
	}

	public Integer getPartnerId(){
		return partnerId;
	}
	
	public void setPartnerId(Integer partnerId){
		this.partnerId = partnerId;
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

	public Long getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(Long operatorId){
		this.operatorId = operatorId;
	}

	public Integer getDelFlg(){
		return delFlg;
	}
	
	public void setDelFlg(Integer delFlg){
		this.delFlg = delFlg;
	}

	public OrgVO getOrgVO() {
		return orgVO;
	}

	public void setOrgVO(OrgVO orgVO) {
		this.orgVO = orgVO;
	}

	public PartnerTraderVO getPartnerTraderVO() {
		return partnerTraderVO;
	}

	public void setPartnerTraderVO(PartnerTraderVO partnerTraderVO) {
		this.partnerTraderVO = partnerTraderVO;
	}

}

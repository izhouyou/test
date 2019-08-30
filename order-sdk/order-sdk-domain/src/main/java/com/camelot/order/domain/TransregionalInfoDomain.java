package com.camelot.order.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TransregionalInfoDomain implements Serializable {

	/**主键ID*/
	private Long id;
	/**商品编号*/
	private String goodsNumber;
	/**商品名称*/
	private String goodsName;
	/**商品SN*/
	private String goodsSn;
	/**车架号*/
	private String goodsFrameNumber;
	/**是否激活 0:未激活;1激活*/
	private Integer activationFlag;
	/**销售后3天城市*/
	private String cityThird;
	/**销售后15天城市*/
	private String cityFifteen;
	/**销售订单编号*/
	private String salesOrderNumber;
	/**操作人*/
	private Integer operationUserId;
	/**操作人姓名*/
	private String operationUserName;
	/**提交时间*/
	private java.util.Date submitTime;
	/**销售门店ID*/
	private Integer storeId;
	/**销售门店编码*/
	private String storeNumber;
	/**销售门店名称*/
	private String storeName;
	/**销售合伙人ID*/
	private Integer spartnerId;
	/**销售合伙人编码*/
	private String spartnerNumber;
	/**销售合伙人名称*/
	private String spartnerName;
	/**采购合伙人ID*/
	private Integer cpartnerId;
	/**采购合伙人编码*/
	private String cpartnerNumber;
	/**采购合伙人名称*/
	private String cpartnerName;
	/**1:合伙人外部窜货*/
	private Integer problemType;
	/**流水号(防重复提交)*/
	private Long transactionId;
	/**版本号*/
	private Integer version;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Integer createUserId;
	/**创建人姓名*/
	private String createUserName;
	/**修改人id*/
	private Integer updateUserId;
	/**修改人姓名*/
	private String updateUserName;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改时间*/
	private java.util.Date modifyDate;
	/**商品id*/
	private Integer goodsId;
	/**大区id*/
	private Integer firstOrgId;
	/**大区编码*/
	private String firstOrgNumber;
	/**大区名称*/
	private String firstOrgName;
	/**区域id*/
	private Integer secondOrgId;
	/**区域编码*/
	private String secondOrgNumber;
	/**区域名称*/
	private String secondOrgName;
	/**城市id*/
	private Integer thirdOrgId;
	/**城市编码*/
	private String thirdOrgNumber;
	/**城市名称*/
	private String thirdOrgName;
	/**区域集合*/
	private List<Integer> orgList;
	/**门店集合*/
	private List<Integer> storeList;
	/**开始搜索时间(提交时间)*/
	private Date startCreateDate;
	/**截止搜索时间(提交时间)*/
	private Date endCreateDate;
	/** 门店搜索 */
	private String searchStore;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public String getGoodsNumber(){
		return goodsNumber;
	}
	
	public void setGoodsNumber(String goodsNumber){
		this.goodsNumber = goodsNumber;
	}

	public String getGoodsName(){
		return goodsName;
	}
	
	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}

	public String getGoodsSn(){
		return goodsSn;
	}
	
	public void setGoodsSn(String goodsSn){
		this.goodsSn = goodsSn;
	}

	public String getGoodsFrameNumber(){
		return goodsFrameNumber;
	}
	
	public void setGoodsFrameNumber(String goodsFrameNumber){
		this.goodsFrameNumber = goodsFrameNumber;
	}

	public Integer getActivationFlag(){
		return activationFlag;
	}
	
	public void setActivationFlag(Integer activationFlag){
		this.activationFlag = activationFlag;
	}

	public String getCityThird(){
		return cityThird;
	}
	
	public void setCityThird(String cityThird){
		this.cityThird = cityThird;
	}

	public String getCityFifteen(){
		return cityFifteen;
	}
	
	public void setCityFifteen(String cityFifteen){
		this.cityFifteen = cityFifteen;
	}

	public String getSalesOrderNumber(){
		return salesOrderNumber;
	}
	
	public void setSalesOrderNumber(String salesOrderNumber){
		this.salesOrderNumber = salesOrderNumber;
	}

	public Integer getOperationUserId(){
		return operationUserId;
	}
	
	public void setOperationUserId(Integer operationUserId){
		this.operationUserId = operationUserId;
	}

	public String getOperationUserName(){
		return operationUserName;
	}
	
	public void setOperationUserName(String operationUserName){
		this.operationUserName = operationUserName;
	}

	public java.util.Date getSubmitTime(){
		return submitTime;
	}
	
	public void setSubmitTime(java.util.Date submitTime){
		this.submitTime = submitTime;
	}

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

	public Integer getSpartnerId(){
		return spartnerId;
	}
	
	public void setSpartnerId(Integer spartnerId){
		this.spartnerId = spartnerId;
	}

	public String getSpartnerNumber(){
		return spartnerNumber;
	}
	
	public void setSpartnerNumber(String spartnerNumber){
		this.spartnerNumber = spartnerNumber;
	}

	public String getSpartnerName(){
		return spartnerName;
	}
	
	public void setSpartnerName(String spartnerName){
		this.spartnerName = spartnerName;
	}

	public Integer getCpartnerId(){
		return cpartnerId;
	}
	
	public void setCpartnerId(Integer cpartnerId){
		this.cpartnerId = cpartnerId;
	}

	public String getCpartnerNumber(){
		return cpartnerNumber;
	}
	
	public void setCpartnerNumber(String cpartnerNumber){
		this.cpartnerNumber = cpartnerNumber;
	}

	public String getCpartnerName(){
		return cpartnerName;
	}
	
	public void setCpartnerName(String cpartnerName){
		this.cpartnerName = cpartnerName;
	}

	public Integer getProblemType(){
		return problemType;
	}
	
	public void setProblemType(Integer problemType){
		this.problemType = problemType;
	}

	public Long getTransactionId(){
		return transactionId;
	}
	
	public void setTransactionId(Long transactionId){
		this.transactionId = transactionId;
	}

	public Integer getVersion(){
		return version;
	}
	
	public void setVersion(Integer version){
		this.version = version;
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

	public String getCreateUserName(){
		return createUserName;
	}
	
	public void setCreateUserName(String createUserName){
		this.createUserName = createUserName;
	}

	public Integer getUpdateUserId(){
		return updateUserId;
	}
	
	public void setUpdateUserId(Integer updateUserId){
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

	public Integer getGoodsId(){
		return goodsId;
	}
	
	public void setGoodsId(Integer goodsId){
		this.goodsId = goodsId;
	}

	public Integer getFirstOrgId(){
		return firstOrgId;
	}
	
	public void setFirstOrgId(Integer firstOrgId){
		this.firstOrgId = firstOrgId;
	}

	public String getFirstOrgNumber(){
		return firstOrgNumber;
	}
	
	public void setFirstOrgNumber(String firstOrgNumber){
		this.firstOrgNumber = firstOrgNumber;
	}

	public String getFirstOrgName(){
		return firstOrgName;
	}
	
	public void setFirstOrgName(String firstOrgName){
		this.firstOrgName = firstOrgName;
	}

	public Integer getSecondOrgId(){
		return secondOrgId;
	}
	
	public void setSecondOrgId(Integer secondOrgId){
		this.secondOrgId = secondOrgId;
	}

	public String getSecondOrgNumber(){
		return secondOrgNumber;
	}
	
	public void setSecondOrgNumber(String secondOrgNumber){
		this.secondOrgNumber = secondOrgNumber;
	}

	public String getSecondOrgName(){
		return secondOrgName;
	}
	
	public void setSecondOrgName(String secondOrgName){
		this.secondOrgName = secondOrgName;
	}

	public Integer getThirdOrgId(){
		return thirdOrgId;
	}
	
	public void setThirdOrgId(Integer thirdOrgId){
		this.thirdOrgId = thirdOrgId;
	}

	public String getThirdOrgNumber(){
		return thirdOrgNumber;
	}
	
	public void setThirdOrgNumber(String thirdOrgNumber){
		this.thirdOrgNumber = thirdOrgNumber;
	}

	public String getThirdOrgName(){
		return thirdOrgName;
	}
	
	public void setThirdOrgName(String thirdOrgName){
		this.thirdOrgName = thirdOrgName;
	}

	public List<Integer> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Integer> orgList) {
		this.orgList = orgList;
	}

	public List<Integer> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Integer> storeList) {
		this.storeList = storeList;
	}

	public Date getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(Date startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getSearchStore() {
		return searchStore;
	}

	public void setSearchStore(String searchStore) {
		this.searchStore = searchStore;
	}

}

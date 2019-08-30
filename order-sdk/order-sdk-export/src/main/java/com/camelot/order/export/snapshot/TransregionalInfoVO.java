package com.camelot.order.export.snapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.camelot.order.export.vo.param.BaseVO;

import io.swagger.annotations.ApiModelProperty;

public class TransregionalInfoVO extends BaseVO implements Serializable {

	/**主键ID*/
	private Long id;
	/**商品编号*/
	@ApiModelProperty(value = "商品编号", required = false)
	private String goodsNumber;
	/**商品名称*/
	@ApiModelProperty(value = "商品名称", required = false)
	private String goodsName;
	/**商品SN*/
	@ApiModelProperty(value = "商品SN", required = false)
	private String goodsSn;
	/**车架号*/
	@ApiModelProperty(value = "车架号", required = false)
	private String goodsFrameNumber;
	/**是否激活 0:未激活;1激活*/
	@ApiModelProperty(value = "是否激活 0:未激活;1激活", required = false)
	private Integer activationFlag;
	/**销售后3天城市*/
	@ApiModelProperty(value = "销售后3天城市", required = false)
	private String cityThird;
	/**销售后15天城市*/
	@ApiModelProperty(value = "销售后15天城市", required = false)
	private String cityFifteen;
	/**销售订单编号*/
	@ApiModelProperty(value = "销售订单编号", required = false)
	private String salesOrderNumber;
	/**操作人*/
	@ApiModelProperty(value = "操作人", required = false)
	private Integer operationUserId;
	/**操作人姓名*/
	@ApiModelProperty(value = "操作人姓名", required = false)
	private String operationUserName;
	/**提交时间*/
	@ApiModelProperty(value = "提交时间", required = false)
	private java.util.Date submitTime;
	/**销售门店ID*/
	@ApiModelProperty(value = "销售门店ID", required = false)
	private Integer storeId;
	/**销售门店编码*/
	@ApiModelProperty(value = "销售门店编码", required = false)
	private String storeNumber;
	/**销售门店名称*/
	@ApiModelProperty(value = "销售门店名称", required = false)
	private String storeName;
	/**销售合伙人ID*/
	@ApiModelProperty(value = "销售合伙人ID", required = false)
	private Integer spartnerId;
	/**销售合伙人编码*/
	@ApiModelProperty(value = "销售合伙人编码", required = false)
	private String spartnerNumber;
	/**销售合伙人名称*/
	@ApiModelProperty(value = "销售合伙人名称", required = false)
	private String spartnerName;
	/**采购合伙人ID*/
	@ApiModelProperty(value = "采购合伙人ID", required = false)
	private Integer cpartnerId;
	/**采购合伙人编码*/
	@ApiModelProperty(value = "采购合伙人编码", required = false)
	private String cpartnerNumber;
	/**采购合伙人名称*/
	@ApiModelProperty(value = "采购合伙人名称", required = false)
	private String cpartnerName;
	/**1:合伙人外部窜货*/
	@ApiModelProperty(value = "合伙人外部窜货", required = false)
	private Integer problemType;
	/**流水号(防重复提交)*/
	@ApiModelProperty(value = "流水号(防重复提交)", required = false)
	private Long transactionId;
	/**版本号*/
	@ApiModelProperty(value = "版本号", required = false)
	private Integer version;
	/**删除标识（0代表未删除； 1代表已删除）*/
	@ApiModelProperty(value = "删除标识（0代表未删除； 1代表已删除）", required = false)
	private Integer delFlg;
	/**创建人id*/
	@ApiModelProperty(value = "创建人id", required = false)
	private Integer createUserId;
	/**创建人姓名*/
	@ApiModelProperty(value = "创建人姓名", required = false)
	private String createUserName;
	/**修改人id*/
	@ApiModelProperty(value = "修改人id", required = false)
	private Integer updateUserId;
	/**修改人姓名*/
	@ApiModelProperty(value = "修改人姓名", required = false)
	private String updateUserName;
	/**创建时间*/
	@ApiModelProperty(value = "创建时间", required = false)
	private java.util.Date createDate;
	/**修改时间*/
	@ApiModelProperty(value = "修改时间", required = false)
	private java.util.Date modifyDate;
	/**商品id*/
	@ApiModelProperty(value = "商品id", required = false)
	private Integer goodsId;
	/**大区id*/
	@ApiModelProperty(value = "大区id", required = false)
	private Integer firstOrgId;
	/**大区编码*/
	@ApiModelProperty(value = "大区编码", required = false)
	private String firstOrgNumber;
	/**大区名称*/
	@ApiModelProperty(value = "大区名称", required = false)
	private String firstOrgName;
	/**区域id*/
	@ApiModelProperty(value = "区域id", required = false)
	private Integer secondOrgId;
	/**区域编码*/
	@ApiModelProperty(value = "区域编码", required = false)
	private String secondOrgNumber;
	/**区域名称*/
	@ApiModelProperty(value = "区域名称", required = false)
	private String secondOrgName;
	/**城市id*/
	@ApiModelProperty(value = "城市id", required = false)
	private Integer thirdOrgId;
	/**城市编码*/
	@ApiModelProperty(value = "城市编码", required = false)
	private String thirdOrgNumber;
	/**城市名称*/
	@ApiModelProperty(value = "城市名称", required = false)
	private String thirdOrgName;
	/**是否激活Value*/
	@ApiModelProperty(value = "是否激活Value", required = false)
	private String activationFlagValue;
	/** 问题类型Value */
	@ApiModelProperty(value = "问题类型Value", required = false)
	private String problemTypeValue;
	/**区域集合*/
	@ApiModelProperty(value = "区域集合", required = false)
	private List<Integer> orgList;
	/**门店集合*/
	@ApiModelProperty(value = "门店集合", required = false)
	private List<Integer> storeList;
	/**开始搜索时间(提交时间)*/
	@ApiModelProperty(value = "开始搜索时间(提交时间)", required = false)
	private Date startCreateDate;
	/**截止搜索时间(提交时间)*/
	@ApiModelProperty(value = "截止搜索时间(提交时间)", required = false)
	private Date endCreateDate;
	/** 门店搜索 */
	@ApiModelProperty(value = "门店搜索", required = false)
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

	public String getActivationFlagValue() {
		return activationFlagValue;
	}

	public void setActivationFlagValue(String activationFlagValue) {
		this.activationFlagValue = activationFlagValue;
	}

	public String getProblemTypeValue() {
		return problemTypeValue;
	}

	public void setProblemTypeValue(String problemTypeValue) {
		this.problemTypeValue = problemTypeValue;
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

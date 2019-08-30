package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PartnerTraderVO implements Serializable{

	/**主键id*/
	private Integer partnerId;
	/**编码*/
	private String partnerNumber;
	/**简称*/
	private String partnerSimpleName;
	/**全称*/
	private String partnerName;
	/**经营类型*/
	private Integer partnerType;
	/**等级code值*/
	private Integer partnerGrade;
	/**联系人*/
	private String connectName;
	/**联系地址省*/
	private Integer connectAddressProvince;
	/**联系地址市*/
	private Integer connectAddressCity;
	/**联系地址区*/
	private Integer connectAddressDistrict;
	/**联系详细地址*/
	private String connectAddressDetail;
	/**父id,合伙人partner_id*/
	private Long parentId;
	/**联系电话*/
	private String mobilePhone;
	/**联系邮箱*/
	private String partnerEmail;
	/**状态code值:0可用,1是不可用*/
	private Integer parentStatus;
	/**公司名称*/
	private String companyName;
	/**营业执照编号*/
	private String businessLicense;
	/**交易币种code值*/
	private Integer moneyType;
	/**合作开始时间*/
	private Date cooperationTime;
	/**税分类编号*/
	private String taxNumber;
	/**税分类名称*/
	private String taxName;
	/**是否含税code值:0是含税,1是不含税*/
	private Integer isTax;
	/**纳税人编号*/
	private String taxplayerNumber;
	/**付款条件编码*/
	private String payNumber;
	/**付款条件code值*/
	private Integer payConditions;
	/**银行账户*/
	private String bankAccount;
	/**开户银行代码*/
	private String bankCode;
	/**开户银行名称*/
	private String bankName;
	/**开户银行地址*/
	private String bankAddress;
	/**发票抬头*/
	private String invoiceHead;
	/**注册地址*/
	private String registerAddress;
	/**注册电话*/
	private String registerPhone;
	/**创建时间*/
	private Date createDate;
	/**修改时间*/
	private Date modifyDate;
	/**操作人用户id*/
	private Long operatorId;
	/**删除标志:0是可用,1是不可用*/
	private Integer delFlg;
	/**判断合伙人||加盟商*/
	private Integer personType;
	/** 合伙人/区域集合关系*/
	private List<OrgVO> orgList;
	/**根据加盟商查询合伙人信息vo*/
	private PartnerTraderVO partnerTraderVO;
	/**区域与合伙人关联关系、区域ids*/
	private String orgStr;
	/**根据合伙人查询加盟商信息volist*/
	private List<PartnerTraderVO> partnerTraderVOList;
	/**根据加盟商查询门店volist*/
	private List<StoreVO> storeVOList;
	/**用户主键id*/
	private String userId;
	/**区域大区、小区、城市集合*/
	private List<String> orgStrList;

	private String proviceCode;

	private String cityCode;

	private String districtCode;
	private String orgs;
	/**查询加盟商页面时传的合伙人名称*/
	private String parentName;
	/**前端所传合伙人id*/
	private String partnerStr;
	/**前端所传加盟商id*/
	private String traderStr;
	/**批量操作传的ids*/
	private String ids;
	/*合伙人/加盟商id集合*/
	private List<Integer> partnerTradeIdList;

	public List<Integer> getPartnerTradeIdList() {
		return partnerTradeIdList;
	}

	public void setPartnerTradeIdList(List<Integer> partnerTradeIdList) {
		this.partnerTradeIdList = partnerTradeIdList;
	}

	public String getOrgs() {
		return orgs;
	}

	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}

	public List<String> getOrgStrList() {
		return orgStrList;
	}

	public void setOrgStrList(List<String> orgStrList) {
		this.orgStrList = orgStrList;
	}

	public String getProviceCode() {
		return proviceCode;
	}

	public void setProviceCode(String proviceCode) {
		this.proviceCode = proviceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public List<OrgVO> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<OrgVO> orgList) {
		this.orgList = orgList;
	}

	public Integer getPartnerId(){
		return partnerId;
	}
	
	public void setPartnerId(Integer partnerId){
		this.partnerId = partnerId;
	}

	public String getPartnerNumber(){
		return partnerNumber;
	}
	
	public void setPartnerNumber(String partnerNumber){
		this.partnerNumber = partnerNumber;
	}

	public String getPartnerSimpleName(){
		return partnerSimpleName;
	}
	
	public void setPartnerSimpleName(String partnerSimpleName){
		this.partnerSimpleName = partnerSimpleName;
	}

	public String getPartnerName(){
		return partnerName;
	}
	
	public void setPartnerName(String partnerName){
		this.partnerName = partnerName;
	}

	public Integer getPartnerType(){
		return partnerType;
	}
	
	public void setPartnerType(Integer partnerType){
		this.partnerType = partnerType;
	}

	public Integer getPartnerGrade(){
		return partnerGrade;
	}
	
	public void setPartnerGrade(Integer partnerGrade){
		this.partnerGrade = partnerGrade;
	}

	public String getConnectName(){
		return connectName;
	}
	
	public void setConnectName(String connectName){
		this.connectName = connectName;
	}

	public Integer getConnectAddressProvince(){
		return connectAddressProvince;
	}
	
	public void setConnectAddressProvince(Integer connectAddressProvince){
		this.connectAddressProvince = connectAddressProvince;
	}

	public Integer getConnectAddressCity(){
		return connectAddressCity;
	}
	
	public void setConnectAddressCity(Integer connectAddressCity){
		this.connectAddressCity = connectAddressCity;
	}

	public Integer getConnectAddressDistrict(){
		return connectAddressDistrict;
	}
	
	public void setConnectAddressDistrict(Integer connectAddressDistrict){
		this.connectAddressDistrict = connectAddressDistrict;
	}

	public String getConnectAddressDetail(){
		return connectAddressDetail;
	}
	
	public void setConnectAddressDetail(String connectAddressDetail){
		this.connectAddressDetail = connectAddressDetail;
	}

	public Long getParentId(){
		return parentId;
	}
	
	public void setParentId(Long parentId){
		this.parentId = parentId;
	}

	public String getMobilePhone(){
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}

	public String getPartnerEmail(){
		return partnerEmail;
	}
	
	public void setPartnerEmail(String partnerEmail){
		this.partnerEmail = partnerEmail;
	}

	public Integer getParentStatus(){
		return parentStatus;
	}
	
	public void setParentStatus(Integer parentStatus){
		this.parentStatus = parentStatus;
	}

	public String getCompanyName(){
		return companyName;
	}
	
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getBusinessLicense(){
		return businessLicense;
	}
	
	public void setBusinessLicense(String businessLicense){
		this.businessLicense = businessLicense;
	}

	public Integer getMoneyType(){
		return moneyType;
	}
	
	public void setMoneyType(Integer moneyType){
		this.moneyType = moneyType;
	}

	public java.util.Date getCooperationTime(){
		return cooperationTime;
	}
	
	public void setCooperationTime(java.util.Date cooperationTime){
		this.cooperationTime = cooperationTime;
	}

	public String getTaxNumber(){
		return taxNumber;
	}
	
	public void setTaxNumber(String taxNumber){
		this.taxNumber = taxNumber;
	}

	public String getTaxName(){
		return taxName;
	}
	
	public void setTaxName(String taxName){
		this.taxName = taxName;
	}

	public Integer getIsTax(){
		return isTax;
	}
	
	public void setIsTax(Integer isTax){
		this.isTax = isTax;
	}

	public String getTaxplayerNumber(){
		return taxplayerNumber;
	}
	
	public void setTaxplayerNumber(String taxplayerNumber){
		this.taxplayerNumber = taxplayerNumber;
	}

	public String getPayNumber(){
		return payNumber;
	}
	
	public void setPayNumber(String payNumber){
		this.payNumber = payNumber;
	}

	public Integer getPayConditions(){
		return payConditions;
	}
	
	public void setPayConditions(Integer payConditions){
		this.payConditions = payConditions;
	}

	public String getBankAccount(){
		return bankAccount;
	}
	
	public void setBankAccount(String bankAccount){
		this.bankAccount = bankAccount;
	}

	public String getBankCode(){
		return bankCode;
	}
	
	public void setBankCode(String bankCode){
		this.bankCode = bankCode;
	}

	public String getBankName(){
		return bankName;
	}
	
	public void setBankName(String bankName){
		this.bankName = bankName;
	}

	public String getBankAddress(){
		return bankAddress;
	}
	
	public void setBankAddress(String bankAddress){
		this.bankAddress = bankAddress;
	}

	public String getInvoiceHead(){
		return invoiceHead;
	}
	
	public void setInvoiceHead(String invoiceHead){
		this.invoiceHead = invoiceHead;
	}

	public String getRegisterAddress(){
		return registerAddress;
	}
	
	public void setRegisterAddress(String registerAddress){
		this.registerAddress = registerAddress;
	}

	public String getRegisterPhone(){
		return registerPhone;
	}
	
	public void setRegisterPhone(String registerPhone){
		this.registerPhone = registerPhone;
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

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public PartnerTraderVO getPartnerTraderVO() {
		return partnerTraderVO;
	}

	public void setPartnerTraderVO(PartnerTraderVO partnerTraderVO) {
		this.partnerTraderVO = partnerTraderVO;
	}

	public List<PartnerTraderVO> getPartnerTraderVOList() {
		return partnerTraderVOList;
	}

	public void setPartnerTraderVOList(List<PartnerTraderVO> partnerTraderVOList) {
		this.partnerTraderVOList = partnerTraderVOList;
	}

	public List<StoreVO> getStoreVOList() {
		return storeVOList;
	}

	public void setStoreVOList(List<StoreVO> storeVOList) {
		this.storeVOList = storeVOList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgStr() {
		return orgStr;
	}

	public void setOrgStr(String orgStr) {
		this.orgStr = orgStr;
	}

	@Override
	public String toString() {
		return "PartnerTraderVO [partnerId=" + partnerId + ", partnerNumber=" + partnerNumber + ", partnerSimpleName="
				+ partnerSimpleName + ", partnerName=" + partnerName + ", partnerType=" + partnerType
				+ ", partnerGrade=" + partnerGrade + ", connectName=" + connectName + ", connectAddressProvince="
				+ connectAddressProvince + ", connectAddressCity=" + connectAddressCity + ", connectAddressDistrict="
				+ connectAddressDistrict + ", connectAddressDetail=" + connectAddressDetail + ", parentId=" + parentId
				+ ", mobilePhone=" + mobilePhone + ", partnerEmail=" + partnerEmail + ", parentStatus=" + parentStatus
				+ ", companyName=" + companyName + ", businessLicense=" + businessLicense + ", moneyType=" + moneyType
				+ ", cooperationTime=" + cooperationTime + ", taxNumber=" + taxNumber + ", taxName=" + taxName
				+ ", isTax=" + isTax + ", taxplayerNumber=" + taxplayerNumber + ", payNumber=" + payNumber
				+ ", payConditions=" + payConditions + ", bankAccount=" + bankAccount + ", bankCode=" + bankCode
				+ ", bankName=" + bankName + ", bankAddress=" + bankAddress + ", invoiceHead=" + invoiceHead
				+ ", registerAddress=" + registerAddress + ", registerPhone=" + registerPhone + ", createDate="
				+ createDate + ", modifyDate=" + modifyDate + ", operatorId=" + operatorId + ", delFlg=" + delFlg
				+ ", personType=" + personType + ", orgList=" + orgList + ", partnerTraderVO=" + partnerTraderVO
				+ ", orgStr=" + orgStr + ", partnerTraderVOList=" + partnerTraderVOList + ", storeVOList=" + storeVOList
				+ ", userId=" + userId + ", orgStrList=" + orgStrList + ", proviceCode=" + proviceCode + ", cityCode="
				+ cityCode + ", districtCode=" + districtCode + "]";
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getPartnerStr() {
		return partnerStr;
	}

	public void setPartnerStr(String partnerStr) {
		this.partnerStr = partnerStr;
	}

	public String getTraderStr() {
		return traderStr;
	}

	public void setTraderStr(String traderStr) {
		this.traderStr = traderStr;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
}

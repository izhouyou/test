package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StoreVO implements Serializable {

    /*主键id*/
    private Integer storeId;
    /*门店编码*/
    private String storeNumber;
    /*渠道编号*/
    private String storeChannelNumber;
    /*门店名称*/
    private String storeName;
    /*门店性质code值*/
    private Integer storeNature;
    /*门店类型*/
    private Integer storeType;
    /*门店地址省份*/
    private Integer storeAddressProvice;
    /*门店地址市*/
    private Integer storeAddressCity;
    /*门店地址区*/
    private Integer storeAddressDistrict;
    /*门店地址详细地址*/
    private String storeAddressDetail;
    /*门店状态*/
    private Integer storeStaus;
    /*开业时间*/
    private Date storeOpenTime;
    /*闭店时间*/
    private Date storeCloseTime;
    /*门店面积*/
    private String storeAcreage;
    /*所属区域id(城市级别的)*/
    private Long storeBelongId;
    /*门店对应仓库主键id*/
    private Long warehouseId;
    /*门店负责人*/
    private String storeHead;
    /*门店负责人联系电话*/
    private String storeHeadPhone;
    /*门店负责人联系邮箱*/
    private String storeEmail;
    /*所属合伙人/加盟商id*/
    private Integer partnerId;
    /*创建时间*/
    private Date createDate;
    /*修改时间*/
    private Date modifyDate;
    /*操作人用户id*/
    private Long operatorId;
    /*删除标志:0是可用,1是不可用*/
    private Integer delFlg;
    /*区域信息*/
    private OrgVO orgVO;
    /*合伙人信息*/
    private PartnerTraderVO partnerTraderVO;
    /*加盟商信息*/
    private PartnerTraderVO traderVO;
    /*门店所属合伙人信息*/
    private PartnerTraderVO partnerVO;
    /*区域ids*/
    private String orgStr;
    /*用户主键id*/
    private String userId;
    /*门店id集合*/
    private List<Long> storeList;
    /*区域城市+加盟商*/
    private String tradeStr;
    /*区域城市+加盟商+合伙人*/
    private String partnerStr;
    /*省份code值*/
    private String proviceCode;
    /*市区code值*/
    private String cityCode;
    /*县code值*/
    private String districtCode;
    /*所属大区*/
    private String firstOrgName;
    /*所属区域*/
    private String secondOrgName;
    /*所属城市*/
    private String thirdOrgName;
    /*外部用户管辖门店*/
    private String storeStr;
    /*前端所传所属机构*/
    private List<String> storeArrayStr;
    /*内部用户所属区域*/
    private List<String> orgArr;
    /*批量操作传的ids*/
    private String ids;
    /*最后登录标志符号*/
    private Integer lastStore;
    /*门店所属的加盟商的合伙人ID*/
    private Integer partnerTraderId;
    /* 门店所属合伙人*/
    private String partnerName;
    /*合伙人加盟商集合*/
    private List<Integer> partnerTradeList;
    /**门店所属大区实体*/
    private OrgVO firstOrgVO;
    /**门店所属区域实体*/
    private OrgVO secondOrgVO;
    /**门店所属城市实体*/
    private OrgVO thirdOrgVO;

    public List<Integer> getPartnerTradeList() {
        return partnerTradeList;
    }

    public void setPartnerTradeList(List<Integer> partnerTradeList) {
        this.partnerTradeList = partnerTradeList;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public OrgVO getFirstOrgVO() {
		return firstOrgVO;
	}

	public void setFirstOrgVO(OrgVO firstOrgVO) {
		this.firstOrgVO = firstOrgVO;
	}

	public OrgVO getSecondOrgVO() {
		return secondOrgVO;
	}

	public void setSecondOrgVO(OrgVO secondOrgVO) {
		this.secondOrgVO = secondOrgVO;
	}

	public OrgVO getThirdOrgVO() {
		return thirdOrgVO;
	}

	public void setThirdOrgVO(OrgVO thirdOrgVO) {
		this.thirdOrgVO = thirdOrgVO;
	}

	public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getStoreChannelNumber() {
        return storeChannelNumber;
    }

    public void setStoreChannelNumber(String storeChannelNumber) {
        this.storeChannelNumber = storeChannelNumber;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getStoreNature() {
        return storeNature;
    }

    public void setStoreNature(Integer storeNature) {
        this.storeNature = storeNature;
    }

    public Integer getStoreType() {
        return storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public Integer getStoreAddressProvice() {
        return storeAddressProvice;
    }

    public void setStoreAddressProvice(Integer storeAddressProvice) {
        this.storeAddressProvice = storeAddressProvice;
    }

    public Integer getStoreAddressCity() {
        return storeAddressCity;
    }

    public void setStoreAddressCity(Integer storeAddressCity) {
        this.storeAddressCity = storeAddressCity;
    }

    public Integer getStoreAddressDistrict() {
        return storeAddressDistrict;
    }

    public void setStoreAddressDistrict(Integer storeAddressDistrict) {
        this.storeAddressDistrict = storeAddressDistrict;
    }

    public String getStoreAddressDetail() {
        return storeAddressDetail;
    }

    public void setStoreAddressDetail(String storeAddressDetail) {
        this.storeAddressDetail = storeAddressDetail;
    }

    public Integer getStoreStaus() {
        return storeStaus;
    }

    public void setStoreStaus(Integer storeStaus) {
        this.storeStaus = storeStaus;
    }

    public Date getStoreOpenTime() {
        return storeOpenTime;
    }

    public void setStoreOpenTime(Date storeOpenTime) {
        this.storeOpenTime = storeOpenTime;
    }

    public Date getStoreCloseTime() {
        return storeCloseTime;
    }

    public void setStoreCloseTime(Date storeCloseTime) {
        this.storeCloseTime = storeCloseTime;
    }

    public String getStoreAcreage() {
        return storeAcreage;
    }

    public void setStoreAcreage(String storeAcreage) {
        this.storeAcreage = storeAcreage;
    }

    public Long getStoreBelongId() {
        return storeBelongId;
    }

    public void setStoreBelongId(Long storeBelongId) {
        this.storeBelongId = storeBelongId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getStoreHead() {
        return storeHead;
    }

    public void setStoreHead(String storeHead) {
        this.storeHead = storeHead;
    }

    public String getStoreHeadPhone() {
        return storeHeadPhone;
    }

    public void setStoreHeadPhone(String storeHeadPhone) {
        this.storeHeadPhone = storeHeadPhone;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
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

    public PartnerTraderVO getTraderVO() {
        return traderVO;
    }

    public void setTraderVO(PartnerTraderVO traderVO) {
        this.traderVO = traderVO;
    }

    public PartnerTraderVO getPartnerVO() {
        return partnerVO;
    }

    public void setPartnerVO(PartnerTraderVO partnerVO) {
        this.partnerVO = partnerVO;
    }

    public String getOrgStr() {
        return orgStr;
    }

    public void setOrgStr(String orgStr) {
        this.orgStr = orgStr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Long> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Long> storeList) {
        this.storeList = storeList;
    }

    public String getTradeStr() {
        return tradeStr;
    }

    public void setTradeStr(String tradeStr) {
        this.tradeStr = tradeStr;
    }

    public String getPartnerStr() {
        return partnerStr;
    }

    public void setPartnerStr(String partnerStr) {
        this.partnerStr = partnerStr;
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

    public String getFirstOrgName() {
        return firstOrgName;
    }

    public void setFirstOrgName(String firstOrgName) {
        this.firstOrgName = firstOrgName;
    }

    public String getSecondOrgName() {
        return secondOrgName;
    }

    public void setSecondOrgName(String secondOrgName) {
        this.secondOrgName = secondOrgName;
    }

    public String getThirdOrgName() {
        return thirdOrgName;
    }

    public void setThirdOrgName(String thirdOrgName) {
        this.thirdOrgName = thirdOrgName;
    }

    public String getStoreStr() {
        return storeStr;
    }

    public void setStoreStr(String storeStr) {
        this.storeStr = storeStr;
    }

    public List<String> getStoreArrayStr() {
        return storeArrayStr;
    }

    public void setStoreArrayStr(List<String> storeArrayStr) {
        this.storeArrayStr = storeArrayStr;
    }

    public List<String> getOrgArr() {
        return orgArr;
    }

    public void setOrgArr(List<String> orgArr) {
        this.orgArr = orgArr;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getLastStore() {
        return lastStore;
    }

    public void setLastStore(Integer lastStore) {
        this.lastStore = lastStore;
    }

    public Integer getPartnerTraderId() {
        return partnerTraderId;
    }

    public void setPartnerTraderId(Integer partnerTraderId) {
        this.partnerTraderId = partnerTraderId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
}

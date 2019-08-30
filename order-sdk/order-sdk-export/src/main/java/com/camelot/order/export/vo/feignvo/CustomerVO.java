package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CustomerVO implements Serializable{

	private static final long serialVersionUID = -3505074561470726407L;
	/**主键*/
	private Integer customerId;
	/**消费者编号*/
	private String customerNunber;
	/**客户姓名*/
	private String customerName;
	/**顾客手机号*/
	private String customerPhoneNumber;
	/**获知来源(字典id)*/
	private Integer customerSource;
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
	/**用户id*/
	private Integer userId;
	/**用户身份*/
	private Integer userType;
	private String orgStr;
	/**消费者所属区域*/
	private Integer gid;
	/**用户管束区域id*/
	private List<Integer> orgIdList;
	/**外部身份1合伙人2加盟商3门店4店员*/
	private Integer userIdentify;
	/**消费金额*/
	private BigDecimal priceTotal;
	/**订单数量*/
	private Integer numberTotal;
    /*搜索开始时间*/
	private String startDateStr;
	/*搜索结束时间*/
	private String endDateStr;
	/*搜索开始时间*/
	private Date startDate;
	/*搜索结束时间*/
	private Date endDate;
	/**消费者id集合*/
	private List<Integer> customerIdList;
	/*门店字符串集合*/
	private String storeStr;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNunber() {
        return customerNunber;
    }

    public void setCustomerNunber(String customerNunber) {
        this.customerNunber = customerNunber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public Integer getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(Integer customerSource) {
        this.customerSource = customerSource;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getOrgStr() {
        return orgStr;
    }

    public void setOrgStr(String orgStr) {
        this.orgStr = orgStr;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public List<Integer> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<Integer> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public Integer getUserIdentify() {
        return userIdentify;
    }

    public void setUserIdentify(Integer userIdentify) {
        this.userIdentify = userIdentify;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Integer getNumberTotal() {
        return numberTotal;
    }

    public void setNumberTotal(Integer numberTotal) {
        this.numberTotal = numberTotal;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getCustomerIdList() {
        return customerIdList;
    }

    public void setCustomerIdList(List<Integer> customerIdList) {
        this.customerIdList = customerIdList;
    }

    public String getStoreStr() {
        return storeStr;
    }

    public void setStoreStr(String storeStr) {
        this.storeStr = storeStr;
    }
}

package com.camelot.order.export.snapshot;


import com.camelot.order.export.vo.param.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CustomerVO extends BaseVO implements Serializable {

	/**主键*/
    @JsonSerialize(using = ToStringSerializer.class)
	private Long customerId;
	/**消费者编号*/
	private String customerNunber;
	/**客户姓名*/
	private String customerName;
	/**顾客手机号*/
	private String customerPhoneNumber;
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
//	/**区域ids*/
//	private String orgStr;
//	/**门店ids*/
//	private String storeStr;
	/**区域集合*/
	private List<Integer> orgList;
	/**门店集合*/
	private List<Integer> storeList;
	/**订单id集合*/
	private List<Long> orderId;
	/**未购买上报id集合*/
	private List<Long> notBuyIdList;
	/**消费者id集合*/
	private List<Long> customerIdList;
	/**搜索开始时间*/
	private String startDateStr;
	/**搜索结束时间*/
	private String endDateStr;
	/**搜索开始时间*/
	private Date startDate;
	/**搜索结束时间*/
	private Date endDate;

    /**消费金额---该消费者支付完成的金额*/
    private BigDecimal priceTotal;
    /**订单数量--该消费者有关的订单数量，点击数量跳转销售报表，并自动检索该消费者相关订单*/
    private Integer numberTotal;
	/**退货金额---该消费者退货完成的金额*/
	private BigDecimal refundAmount;
    /**退货单数量---该消费者的退货单数量*/
    private Integer returnNumberTotal;
	/**有效消费金额---有效消费金额=消费金额-退货金额*/
	private BigDecimal effectiveAmount;
    /**有效订单数量---有效订单数量=订单数量-退货单数量*/
    private Integer effectiveNumberTotal;

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getEffectiveAmount() {
        return effectiveAmount;
    }

    public void setEffectiveAmount(BigDecimal effectiveAmount) {
        this.effectiveAmount = effectiveAmount;
    }

    public Integer getReturnNumberTotal() {
        return returnNumberTotal;
    }

    public void setReturnNumberTotal(Integer returnNumberTotal) {
        this.returnNumberTotal = returnNumberTotal;
    }

    public Integer getEffectiveNumberTotal() {
        return effectiveNumberTotal;
    }

    public void setEffectiveNumberTotal(Integer effectiveNumberTotal) {
        this.effectiveNumberTotal = effectiveNumberTotal;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
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

//    public String getOrgStr() {
//        return orgStr;
//    }
//
//    public void setOrgStr(String orgStr) {
//        this.orgStr = orgStr;
//    }
//
//    public String getStoreStr() {
//        return storeStr;
//    }
//
//    public void setStoreStr(String storeStr) {
//        this.storeStr = storeStr;
//    }

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

    public List<Long> getOrderId() {
        return orderId;
    }

    public void setOrderId(List<Long> orderId) {
        this.orderId = orderId;
    }

    public List<Long> getNotBuyIdList() {
        return notBuyIdList;
    }

    public void setNotBuyIdList(List<Long> notBuyIdList) {
        this.notBuyIdList = notBuyIdList;
    }

    public List<Long> getCustomerIdList() {
        return customerIdList;
    }

    public void setCustomerIdList(List<Long> customerIdList) {
        this.customerIdList = customerIdList;
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
}

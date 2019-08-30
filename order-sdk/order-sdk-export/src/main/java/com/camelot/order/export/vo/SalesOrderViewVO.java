package com.camelot.order.export.vo;

import com.camelot.order.export.vo.feignvo.FeignGoodsVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 与移动端交互的VO
 *
 * @author xupengfei
 * @Description
 * @Date 2019年4月8日
 */
public class SalesOrderViewVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer salesOrderId;
    /**
     * 订单编号
     */
    private String salesOrderNumber;
    /**
     * 门店id
     */
    private Integer storeId;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 门店编码
     */
    private String storeNumber;
    /**
     * 订单金额
     */
    private BigDecimal salesOrderAmount;
    /**
     * 支付方式（字典值id）
     */
    private Integer paymentWay;
    /**
     * 订单来源（字典值id）
     */
    private Integer salesOrderSource;
    /**
     * 订单状态（字典值id）
     */
    private Integer salesOrderStatus;
    /**
     * 退货标识（字典值ID）
     */
    private Integer returnFlg;
    /**
     * 顾客id
     */
    private Integer customerId;
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 顾客手机号
     */
    private String customerPhoneNumber;
    /**
     * 获知来源(字典id)
     */
    private Integer customerSource;
    /**
     * 活动id
     */
    private Integer activityId;
    /**
     * 活动优惠码id
     */
    private Integer couponId;
    /**
     * 活动优惠码编号
     */
    private String couponNumber;
    /**
     * 活动图片URL
     */
    private String activityPicture;
    /**
     * 付款二维码URL
     */
    private String paymentQrCode;
    /**
     * 版本号
     */
    private Integer salesOrderVersion;
    /**
     * 删除标识（0代表未删除； 1代表已删除）
     */
    private Integer delFlg;
    /**
     * 创建人id
     */
    private Integer createUserId;
    /**
     * 修改人id
     */
    private Integer updateUserId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 城市ID
     */
    private Integer cityId;

    /**
     * 合伙人ID
     */
    private Integer partnerId;

    /**
     * 商品信息
     */
    private List<FeignGoodsVO> goodsList;

    /**
     * 流水号
     */
    private Long transactionId;

    public Integer getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Integer salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setSalesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }


    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public java.math.BigDecimal getSalesOrderAmount() {
        return salesOrderAmount;
    }

    public void setSalesOrderAmount(java.math.BigDecimal salesOrderAmount) {
        this.salesOrderAmount = salesOrderAmount;
    }

    public Integer getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(Integer paymentWay) {
        this.paymentWay = paymentWay;
    }

    public Integer getSalesOrderSource() {
        return salesOrderSource;
    }

    public void setSalesOrderSource(Integer salesOrderSource) {
        this.salesOrderSource = salesOrderSource;
    }

    public Integer getSalesOrderStatus() {
        return salesOrderStatus;
    }

    public void setSalesOrderStatus(Integer salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    public Integer getReturnFlg() {
        return returnFlg;
    }

    public void setReturnFlg(Integer returnFlg) {
        this.returnFlg = returnFlg;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getActivityPicture() {
        return activityPicture;
    }

    public void setActivityPicture(String activityPicture) {
        this.activityPicture = activityPicture;
    }

    public String getPaymentQrCode() {
        return paymentQrCode;
    }

    public void setPaymentQrCode(String paymentQrCode) {
        this.paymentQrCode = paymentQrCode;
    }

    public Integer getSalesOrderVersion() {
        return salesOrderVersion;
    }

    public void setSalesOrderVersion(Integer salesOrderVersion) {
        this.salesOrderVersion = salesOrderVersion;
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

    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public java.util.Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(java.util.Date modifyDate) {
        this.modifyDate = modifyDate;
    }


    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<FeignGoodsVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<FeignGoodsVO> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(String couponNumber) {
        this.couponNumber = couponNumber;
    }
}

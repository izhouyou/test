package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;
import java.util.Date;

public class FeignCouponBatchVO implements Serializable {


    private static final long serialVersionUID = 8853681389666835577L;
    /**批次优惠码主键id*/
    private Integer tCouponBatchId;
    /**批次优惠码名称*/
    private String couponName;
    /**优惠码渠道id*/
    private Integer couponChannel;
    /**优惠码渠道名称*/
    private String couponChannelName;
    /**优惠码类型*/
    private Integer couponType;
    /**优惠码类型名称*/
    private String couponTypeName;
    /**所属活动*/
    private Integer tActivityId;
    /**活动名称*/
    private String activityName;
    /**批次号前缀*/
    private String batchNumberPrefix;
    /**优惠码总数量*/
    private Integer couponCount;
    /**优惠码已核销数*/
    private Integer cancelNumber;
    /**优惠码未核销数*/
    private Integer uncancelNumber;
    /**开始时间*/
    private Date beginTime;
    /**结束时间*/
    private Date endTime;
    /**状态（是否注销）*/
    private Integer isDestroy;
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date modifyTime;
    /**创建人id*/
    private Integer createUserId;
    /**创建人姓名*/
    private String creatrUserName;
    /**修改人id*/
    private Integer updateUserId;
    /**修改人姓名*/
    private String updateUserName;
    /**优惠金额*/
    private Integer discountAmount;
    /**优惠批次号*/
    private String activityBatchCode;

    public Integer gettCouponBatchId(){
        return tCouponBatchId;
    }

    public void settCouponBatchId(Integer tCouponBatchId){
        this.tCouponBatchId = tCouponBatchId;
    }

    public String getCouponName(){
        return couponName;
    }

    public void setCouponName(String couponName){
        this.couponName = couponName;
    }

    public Integer getCouponChannel(){
        return couponChannel;
    }

    public void setCouponChannel(Integer couponChannel){
        this.couponChannel = couponChannel;
    }

    public Integer getCouponType(){
        return couponType;
    }

    public void setCouponType(Integer couponType){
        this.couponType = couponType;
    }

    public String getBatchNumberPrefix(){
        return batchNumberPrefix;
    }

    public void setBatchNumberPrefix(String batchNumberPrefix){
        this.batchNumberPrefix = batchNumberPrefix;
    }

    public Integer getCouponCount(){
        return couponCount;
    }

    public void setCouponCount(Integer couponCount){
        this.couponCount = couponCount;
    }

    public java.util.Date getBeginTime(){
        return beginTime;
    }

    public void setBeginTime(java.util.Date beginTime){
        this.beginTime = beginTime;
    }

    public java.util.Date getEndTime(){
        return endTime;
    }

    public void setEndTime(java.util.Date endTime){
        this.endTime = endTime;
    }

    public Integer getIsDestroy(){
        return isDestroy;
    }

    public void setIsDestroy(Integer isDestroy){
        this.isDestroy = isDestroy;
    }

    public java.util.Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime){
        this.createTime = createTime;
    }

    public java.util.Date getModifyTime(){
        return modifyTime;
    }

    public void setModifyTime(java.util.Date modifyTime){
        this.modifyTime = modifyTime;
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

    public Integer getDiscountAmount(){
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount){
        this.discountAmount = discountAmount;
    }


    public String getActivityBatchCode() {
        return activityBatchCode;
    }

    public void setActivityBatchCode(String activityBatchCode) {
        this.activityBatchCode = activityBatchCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer gettActivityId() {
        return tActivityId;
    }

    public void settActivityId(Integer tActivityId) {
        this.tActivityId = tActivityId;
    }

    public String getCreatrUserName() { return creatrUserName; }

    public void setCreatrUserName(String creatrUserName) { this.creatrUserName = creatrUserName; }

    public String getUpdateUserName() { return updateUserName; }

    public void setUpdateUserName(String updateUserName) { this.updateUserName = updateUserName; }

    public String getCouponChannelName() { return couponChannelName; }

    public void setCouponChannelName(String couponChannelName) { this.couponChannelName = couponChannelName; }

    public String getCouponTypeName() { return couponTypeName; }

    public void setCouponTypeName(String couponTypeName) { this.couponTypeName = couponTypeName; }

    public Integer getCancelNumber() { return cancelNumber; }

    public void setCancelNumber(Integer cancelNumber) { this.cancelNumber = cancelNumber; }

    public Integer getUncancelNumber() { return uncancelNumber; }

    public void setUncancelNumber(Integer uncancelNumber) {
        this.uncancelNumber = uncancelNumber;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public String toString() {
        return "CouponBatchVO{" +
                "tCouponBatchId=" + tCouponBatchId +
                ", couponName='" + couponName + '\'' +
                ", couponChannel=" + couponChannel +
                ", couponChannelName='" + couponChannelName + '\'' +
                ", couponType=" + couponType +
                ", couponTypeName='" + couponTypeName + '\'' +
                ", tActivityId=" + tActivityId +
                ", batchNumberPrefix='" + batchNumberPrefix + '\'' +
                ", couponCount=" + couponCount +
                ", cancelNumber=" + cancelNumber +
                ", uncancelNumber=" + uncancelNumber +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", isDestroy=" + isDestroy +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", creatrUserName='" + creatrUserName + '\'' +
                ", updateUserId=" + updateUserId +
                ", updateUserName='" + updateUserName + '\'' +
                ", discountAmount=" + discountAmount +
                ", activityBatchCode='" + activityBatchCode + '\'' +
                '}';
    }
}

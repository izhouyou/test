package com.camelot.order.export.snapshot;


import com.camelot.order.export.vo.param.BaseVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author hudya
 */
public class NewSalesOrderVO extends BaseVO implements Serializable {

	/**主键*/

	@JsonSerialize(using = ToStringSerializer.class)
	private Long salesOrderId;
	/**订单编号*/
	private String salesOrderNumber;
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
	/**门店id*/
	private Integer storeId;
	/**门店编码*/
	private String storeNumber;
	/**门店渠道编码*/
	private String storeChannelNumber;
	/**门店名称*/
	private String storeName;
	/**小牛机构id*/
	private Integer niuOrgId;
	/**小牛机构编码*/
	private String niuOrgNumber;
	/**小牛机构名称*/
	private String niuOrgName;
	/**合伙人id*/
	private Integer partnerId;
	/**合伙人编码*/
	private String partnerNumber;
	/**合伙人名称*/
	private String partnerName;
	/**加盟商id*/
	private Integer traderId;
	/**加盟商编码*/
	private String traderNumber;
	/**加盟商名称*/
	private String traderName;
	/**订单实付金额*/
	private java.math.BigDecimal salesOrderAmount;
	/**支付方式（字典值id）*/
	private Integer paymentWayId;
	/**支付方式编码*/
	private String paymentWayNumber;
	/**支付方式名称*/
	private String paymentWayName;
	/**订单来源（字典值id）*/
	private Integer salesOrderSourceId;
	/**订单来源编码*/
	private String salesOrderSourceNumber;
	/**订单来源名称*/
	private String salesOrderSourceName;
	/**订单状态*/
	private Integer salesOrderStatusId;
	/**订单状态编码*/
	private String salesOrderStatusNumber;
	/**订单状态名称(已提交;已取消;已完成)*/
	private String salesOrderStatusName;
	/**付款状态id*/
	private Integer paymentStatusId;
	/**付款状态编码*/
	private String paymentStatusNumber;
	/**付款状态名称(已支付;未支付)*/
	private String paymentStatusName;
	/**退货状态id*/
	private Integer returnStatusId;
	/**退货状态编码*/
	private String returnStatusNumber;
	/**退货状态名称(已退货;未退货)*/
	private String returnStatusName;
	/**消费者id*/
	private Long customerId;
	/**消费者编号*/
	private String customerNumber;
	/**消费者手机号*/
	private String customerPhoneNumber;
	/**消费者姓名*/
	private String customerName;
	/**消费者来源id*/
	private Integer customerSourceId;
	/**消费者来源编码*/
	private String customerSourceNumber;
	/**消费者来源名称*/
	private String customerSourceName;
	/**活动id*/
	private Integer activityId;
	/**活动编码*/
	private String activityNumber;
	/**活动名称*/
	private String activityName;
	/**活动码批次id*/
	private Integer activityBrachId;
	/**活动码批次号*/
	private String activityBatchCode;
	/**活动码批次号名称*/
	private String activityBrachName;
	/**活动优惠码id*/
	private Integer couponId;
	/**活动优惠码*/
	private String couponCode;
	/**活动图片URL*/
	private String activityPicture;
	/**付款二维码URL*/
	private String paymentQrCode;
	/**订单取消原因(字段id)*/
	private Integer cancelReasonId;
	/**订单取消编码*/
	private String cancelReasonNumber;
	/**订单取消名称*/
	private String cancelReasonName;
	/**版本号*/
	private Integer salesOrderVersion;
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
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createDate;
	/**修改时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date modifyDate;
	/**订单流水号(防重复提交)*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long transactionId;
	/**区域集合*/
	private List<Integer> orgList;
	/**门店集合*/
	private List<Integer> storeList;
	/**商品信息*/
	private List<NewSalesOrderGoodsVO> goodsList;
	/**开始搜索时间*/
	private Date beginSearchDate;
	/**截止搜索时间*/
	private Date endSearchDate;
	/**区域搜索条件*/
	private String orgArrStr;
	/**退货订单编号*/
	private String returnOrderNumber;
	/**商品69码*/
	private String goodsBarcode;
	/**商品sn码*/
	private String goodsSn;
	/**整车车架号*/
	private String goodsFrameNumber;
	/**订单id集合*/
	private List<Long> salesOrderIdList;
	/**开始搜索时间(创建时间)*/
	private Date startCreateDate;
	/**截止搜索时间(创建时间)*/
	private Date endCreateDate;
	/**门店搜索*/
	private String storeSearch;
	/** 活动搜索 */
	private String activitySearch;
	/** 操作人编号 */
	private String operatorNumber;
	/** 操作人电话 */
	private String operaorPhone;
	/** 商品金额 */
	private BigDecimal totalMoney;
	/** 备注 */
	private String orderRemark;
	/**强校验标识符*/
	private Integer verify;
	/**日志标识日志记录*/
	private List<NewSalesOrderLogVO> newSalesOrderLogVOS;

	public Long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}

	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}

	public Integer getFirstOrgId() {
		return firstOrgId;
	}

	public void setFirstOrgId(Integer firstOrgId) {
		this.firstOrgId = firstOrgId;
	}

	public String getFirstOrgNumber() {
		return firstOrgNumber;
	}

	public void setFirstOrgNumber(String firstOrgNumber) {
		this.firstOrgNumber = firstOrgNumber;
	}

	public String getFirstOrgName() {
		return firstOrgName;
	}

	public void setFirstOrgName(String firstOrgName) {
		this.firstOrgName = firstOrgName;
	}

	public Integer getSecondOrgId() {
		return secondOrgId;
	}

	public void setSecondOrgId(Integer secondOrgId) {
		this.secondOrgId = secondOrgId;
	}

	public String getSecondOrgNumber() {
		return secondOrgNumber;
	}

	public void setSecondOrgNumber(String secondOrgNumber) {
		this.secondOrgNumber = secondOrgNumber;
	}

	public String getSecondOrgName() {
		return secondOrgName;
	}

	public void setSecondOrgName(String secondOrgName) {
		this.secondOrgName = secondOrgName;
	}

	public Integer getThirdOrgId() {
		return thirdOrgId;
	}

	public void setThirdOrgId(Integer thirdOrgId) {
		this.thirdOrgId = thirdOrgId;
	}

	public String getThirdOrgNumber() {
		return thirdOrgNumber;
	}

	public void setThirdOrgNumber(String thirdOrgNumber) {
		this.thirdOrgNumber = thirdOrgNumber;
	}

	public String getThirdOrgName() {
		return thirdOrgName;
	}

	public void setThirdOrgName(String thirdOrgName) {
		this.thirdOrgName = thirdOrgName;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
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

	public Integer getNiuOrgId() {
		return niuOrgId;
	}

	public void setNiuOrgId(Integer niuOrgId) {
		this.niuOrgId = niuOrgId;
	}

	public String getNiuOrgNumber() {
		return niuOrgNumber;
	}

	public void setNiuOrgNumber(String niuOrgNumber) {
		this.niuOrgNumber = niuOrgNumber;
	}

	public String getNiuOrgName() {
		return niuOrgName;
	}

	public void setNiuOrgName(String niuOrgName) {
		this.niuOrgName = niuOrgName;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerNumber() {
		return partnerNumber;
	}

	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public String getTraderNumber() {
		return traderNumber;
	}

	public void setTraderNumber(String traderNumber) {
		this.traderNumber = traderNumber;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public BigDecimal getSalesOrderAmount() {
		return salesOrderAmount;
	}

	public void setSalesOrderAmount(BigDecimal salesOrderAmount) {
		this.salesOrderAmount = salesOrderAmount;
	}

	public Integer getPaymentWayId() {
		return paymentWayId;
	}

	public void setPaymentWayId(Integer paymentWayId) {
		this.paymentWayId = paymentWayId;
	}

	public String getPaymentWayNumber() {
		return paymentWayNumber;
	}

	public void setPaymentWayNumber(String paymentWayNumber) {
		this.paymentWayNumber = paymentWayNumber;
	}

	public String getPaymentWayName() {
		return paymentWayName;
	}

	public void setPaymentWayName(String paymentWayName) {
		this.paymentWayName = paymentWayName;
	}

	public Integer getSalesOrderSourceId() {
		return salesOrderSourceId;
	}

	public void setSalesOrderSourceId(Integer salesOrderSourceId) {
		this.salesOrderSourceId = salesOrderSourceId;
	}

	public String getSalesOrderSourceNumber() {
		return salesOrderSourceNumber;
	}

	public void setSalesOrderSourceNumber(String salesOrderSourceNumber) {
		this.salesOrderSourceNumber = salesOrderSourceNumber;
	}

	public String getSalesOrderSourceName() {
		return salesOrderSourceName;
	}

	public void setSalesOrderSourceName(String salesOrderSourceName) {
		this.salesOrderSourceName = salesOrderSourceName;
	}

	public Integer getSalesOrderStatusId() {
		return salesOrderStatusId;
	}

	public void setSalesOrderStatusId(Integer salesOrderStatusId) {
		this.salesOrderStatusId = salesOrderStatusId;
	}

	public String getSalesOrderStatusNumber() {
		return salesOrderStatusNumber;
	}

	public void setSalesOrderStatusNumber(String salesOrderStatusNumber) {
		this.salesOrderStatusNumber = salesOrderStatusNumber;
	}

	public String getSalesOrderStatusName() {
		return salesOrderStatusName;
	}

	public void setSalesOrderStatusName(String salesOrderStatusName) {
		this.salesOrderStatusName = salesOrderStatusName;
	}

	public Integer getPaymentStatusId() {
		return paymentStatusId;
	}

	public void setPaymentStatusId(Integer paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}

	public String getPaymentStatusNumber() {
		return paymentStatusNumber;
	}

	public void setPaymentStatusNumber(String paymentStatusNumber) {
		this.paymentStatusNumber = paymentStatusNumber;
	}

	public String getPaymentStatusName() {
		return paymentStatusName;
	}

	public void setPaymentStatusName(String paymentStatusName) {
		this.paymentStatusName = paymentStatusName;
	}

	public Integer getReturnStatusId() {
		return returnStatusId;
	}

	public void setReturnStatusId(Integer returnStatusId) {
		this.returnStatusId = returnStatusId;
	}

	public String getReturnStatusNumber() {
		return returnStatusNumber;
	}

	public void setReturnStatusNumber(String returnStatusNumber) {
		this.returnStatusNumber = returnStatusNumber;
	}

	public String getReturnStatusName() {
		return returnStatusName;
	}

	public void setReturnStatusName(String returnStatusName) {
		this.returnStatusName = returnStatusName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getCustomerSourceId() {
		return customerSourceId;
	}

	public void setCustomerSourceId(Integer customerSourceId) {
		this.customerSourceId = customerSourceId;
	}

	public String getCustomerSourceNumber() {
		return customerSourceNumber;
	}

	public void setCustomerSourceNumber(String customerSourceNumber) {
		this.customerSourceNumber = customerSourceNumber;
	}

	public String getCustomerSourceName() {
		return customerSourceName;
	}

	public void setCustomerSourceName(String customerSourceName) {
		this.customerSourceName = customerSourceName;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityNumber() {
		return activityNumber;
	}

	public void setActivityNumber(String activityNumber) {
		this.activityNumber = activityNumber;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getActivityBrachId() {
		return activityBrachId;
	}

	public void setActivityBrachId(Integer activityBrachId) {
		this.activityBrachId = activityBrachId;
	}

	public String getActivityBrachName() {
		return activityBrachName;
	}

	public void setActivityBrachName(String activityBrachName) {
		this.activityBrachName = activityBrachName;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
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

	public Integer getCancelReasonId() {
		return cancelReasonId;
	}

	public void setCancelReasonId(Integer cancelReasonId) {
		this.cancelReasonId = cancelReasonId;
	}

	public String getCancelReasonNumber() {
		return cancelReasonNumber;
	}

	public void setCancelReasonNumber(String cancelReasonNumber) {
		this.cancelReasonNumber = cancelReasonNumber;
	}

	public String getCancelReasonName() {
		return cancelReasonName;
	}

	public void setCancelReasonName(String cancelReasonName) {
		this.cancelReasonName = cancelReasonName;
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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
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

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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

	public List<NewSalesOrderGoodsVO> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<NewSalesOrderGoodsVO> goodsList) {
		this.goodsList = goodsList;
	}

	public Date getBeginSearchDate() {
		return beginSearchDate;
	}

	public void setBeginSearchDate(Date beginSearchDate) {
		this.beginSearchDate = beginSearchDate;
	}

	public Date getEndSearchDate() {
		return endSearchDate;
	}

	public void setEndSearchDate(Date endSearchDate) {
		this.endSearchDate = endSearchDate;
	}

	public String getOrgArrStr() {
		return orgArrStr;
	}

	public void setOrgArrStr(String orgArrStr) {
		this.orgArrStr = orgArrStr;
	}

	public String getReturnOrderNumber() {
		return returnOrderNumber;
	}

	public void setReturnOrderNumber(String returnOrderNumber) {
		this.returnOrderNumber = returnOrderNumber;
	}

	public String getGoodsBarcode() {
		return goodsBarcode;
	}

	public void setGoodsBarcode(String goodsBarcode) {
		this.goodsBarcode = goodsBarcode;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getGoodsFrameNumber() {
		return goodsFrameNumber;
	}

	public void setGoodsFrameNumber(String goodsFrameNumber) {
		this.goodsFrameNumber = goodsFrameNumber;
	}

	public List<Long> getSalesOrderIdList() {
		return salesOrderIdList;
	}

	public void setSalesOrderIdList(List<Long> salesOrderIdList) {
		this.salesOrderIdList = salesOrderIdList;
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

	public String getStoreSearch() {
		return storeSearch;
	}

	public void setStoreSearch(String storeSearch) {
		this.storeSearch = storeSearch;
	}

	public String getActivitySearch() {
		return activitySearch;
	}

	public void setActivitySearch(String activitySearch) {
		this.activitySearch = activitySearch;
	}

	public String getOperatorNumber() {
		return operatorNumber;
	}

	public void setOperatorNumber(String operatorNumber) {
		this.operatorNumber = operatorNumber;
	}

	public String getActivityBatchCode() {
		return activityBatchCode;
	}

	public void setActivityBatchCode(String activityBatchCode) {
		this.activityBatchCode = activityBatchCode;
	}

	public String getOperaorPhone() {
		return operaorPhone;
	}

	public void setOperaorPhone(String operaorPhone) {
		this.operaorPhone = operaorPhone;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public Integer getVerify() {
		return verify;
	}

	public void setVerify(Integer verify) {
		this.verify = verify;
	}

	public List<NewSalesOrderLogVO> getNewSalesOrderLogVOS() {
		return newSalesOrderLogVOS;
	}

	public void setNewSalesOrderLogVOS(List<NewSalesOrderLogVO> newSalesOrderLogVOS) {
		this.newSalesOrderLogVOS = newSalesOrderLogVOS;
	}
}

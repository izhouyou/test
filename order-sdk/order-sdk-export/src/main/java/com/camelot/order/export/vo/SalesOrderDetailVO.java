package com.camelot.order.export.vo;

import com.camelot.order.export.vo.feignvo.FeignGoodsVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单详情VO
 * @author xupengfei
 * @Description
 * @Date 2019年4月8日
 */
public class SalesOrderDetailVO implements Serializable{

	private static final long serialVersionUID = 1L;

		/**主键*/
		private Integer salesOrderId;
		/**订单编号*/
		private String salesOrderNumber;
		/**门店id*/
		private Integer storeId;
		/**门店编码*/
		private String storeNumber;
		/**门店名称*/
		private String storeName;
		/**订单金额*/
		private String salesOrderAmount;
		/**实付合计*/
		private String actualTotal;
		/**支付方式*/
		private String paymentWay;
		/**订单来源*/
		private String salesOrderSource;
		/**订单状态*/
		private String salesOrderStatus;
		/**顾客id*/
		private Integer customerId;
		/**消费者姓名*/
		private String customerName;
		/**手机号*/
		private String customerPhoneNumber;
		/**消费者来源*/
		private String customerSource;
		/**活动id*/
		private Integer activityId;
		/**活动名称*/
		private String activityName;
		/**活动优惠码*/
		private Integer couponId;
		/**活动优惠码*/
		private String couponNumber;
		/**订单取消原因*/
		private String cancelReason;
		/**创建人id*/
		private Integer createUserId;
		/**操作人姓名*/
		private String createUserName;
		/**创建时间*/
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
		private Date createDate;
		/**商品信息*/
		private List<FeignGoodsVO> goodsList;
		/**退货订单编号*/
		private String returnOrderNumber;
        /**活动图片URL*/
        private String activityPicture;
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
		public String getStoreName() {
			return storeName;
		}
		public void setStoreName(String storeName) {
			this.storeName = storeName;
		}
		public String getSalesOrderAmount() {
			return salesOrderAmount;
		}
		public void setSalesOrderAmount(String salesOrderAmount) {
			this.salesOrderAmount = salesOrderAmount;
		}
		public String getPaymentWay() {
			return paymentWay;
		}
		public void setPaymentWay(String paymentWay) {
			this.paymentWay = paymentWay;
		}
		public String getSalesOrderSource() {
			return salesOrderSource;
		}
		public void setSalesOrderSource(String salesOrderSource) {
			this.salesOrderSource = salesOrderSource;
		}
		public String getSalesOrderStatus() {
			return salesOrderStatus;
		}
		public void setSalesOrderStatus(String salesOrderStatus) {
			this.salesOrderStatus = salesOrderStatus;
		}
		public Integer getCustomerId() {
			return customerId;
		}
		public void setCustomerId(Integer customerId) {
			this.customerId = customerId;
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
		public String getCustomerSource() {
			return customerSource;
		}
		public void setCustomerSource(String customerSource) {
			this.customerSource = customerSource;
		}
		public Integer getActivityId() {
			return activityId;
		}
		public void setActivityId(Integer activityId) {
			this.activityId = activityId;
		}
		public String getActivityName() {
			return activityName;
		}
		public void setActivityName(String activityName) {
			this.activityName = activityName;
		}
		public Integer getCouponId() {
			return couponId;
		}
		public void setCouponId(Integer couponId) {
			this.couponId = couponId;
		}
		public String getCouponNumber() {
			return couponNumber;
		}
		public void setCouponNumber(String couponNumber) {
			this.couponNumber = couponNumber;
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
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		public List<FeignGoodsVO> getGoodsList() {
			return goodsList;
		}
		public void setGoodsList(List<FeignGoodsVO> goodsList) {
			this.goodsList = goodsList;
		}
		public String getStoreNumber() {
			return storeNumber;
		}
		public void setStoreNumber(String storeNumber) {
			this.storeNumber = storeNumber;
		}
		public String getActualTotal() {
			return actualTotal;
		}
		public void setActualTotal(String actualTotal) {
			this.actualTotal = actualTotal;
		}
		public String getCancelReason() {
			return cancelReason;
		}
		public void setCancelReason(String cancelReason) {
			this.cancelReason = cancelReason;
		}
		public String getReturnOrderNumber() {
			return returnOrderNumber;
		}
		public void setReturnOrderNumber(String returnOrderNumber) {
			this.returnOrderNumber = returnOrderNumber;
		}
        public String getActivityPicture() {
            return activityPicture;
        }

        public void setActivityPicture(String activityPicture) {
            this.activityPicture = activityPicture;
        }
}

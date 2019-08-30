package com.camelot.order.dao;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.OrderGoodsDomain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderGoodsDAO extends BaseDAO<OrderGoodsDomain, Long> {
	/**
	 * <p>Description: 查询商品订单信息</p>
	 * @param orderIds 商品ids
	 * @param minAmount 最小金额
	 * @param maxAmount 最大金额
	 * @param goodsSn 商品SN码
	 * @param goodsFrameNumber 车架号
	 * @return
	 */
	List<OrderGoodsDomain> queryListByOrderId(@Param("orderIds") HashSet<Integer> orderIds, @Param("minAmount") BigDecimal minAmount, 
			@Param("maxAmount") BigDecimal maxAmount, @Param("goodsSn") String goodsSn, @Param("goodsFrameNumber") String goodsFrameNumber);
	/**
	 * <p>Description: 统计商品销售次数</p>
	 * @param orderGoodsIds 商品订单ids
	 * @param goodsId 商品id
	 * @return
	 */
	Long queryReturnGoodsCount(@Param("orderIds") HashSet<Integer> orderIds, @Param("goodsSn") String goodsSn);
	/**
	 * <p>Description: 零售差异订单查询</p>
	 * @param orderIds
	 * @param minAmount
	 * @param maxAmount
	 * @param goodsSn
	 * @param goodsFrameNumber
	 * @return
	 */
	List<OrderGoodsDomain> querySalesDifference(@Param("orderIds") HashSet<Integer> orderIds, @Param("minAmount") BigDecimal minAmount, 
			@Param("maxAmount") BigDecimal maxAmount, @Param("goodsSn") String goodsSn, @Param("goodsFrameNumber") String goodsFrameNumber);
	/**
	 * <p>Description: 查询退货有序商品数据</p>
	 * @param orderIdSet
	 * @param goodsSn
	 * @param goodsFrameNumber
	 * @return
	 */
	List<OrderGoodsDomain> queryReturnGoods( @Param("orderIds") HashSet<Integer> orderIdSet,  @Param("goodsSn") String goodsSn, @Param("goodsFrameNumber") String goodsFrameNumber);
	/**
	 * <p>Description: 查询整车商品订单</p>
	 * @param orderId
	 * @return
	 */
	List<OrderGoodsDomain> queryVehicle( @Param("orderId") Integer orderId);
	/**
	 * <p>Description: 查询差异商品订单</p>
	 * @author zhouyou
	 * @date 2019年5月13日
	 */
	List<OrderGoodsDomain> queryDifferenceList(OrderGoodsDomain domain);
	
	List<OrderGoodsDomain> querySales();
	List<OrderGoodsDomain> queryReturn();
	
}

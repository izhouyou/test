package com.camelot.order.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.vo.OrderGoodsVO;
import com.camelot.order.export.vo.QueryParamVO;
import com.camelot.order.export.vo.SalesOrderVO;
import com.camelot.order.export.vo.param.DifferenceParamVO;
import com.camelot.order.export.vo.param.ReturnGoodsParamVO;
import com.github.pagehelper.PageInfo;

/**
 * <p>Description: [合规服务]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName ComplianceService.java
 * Created on 2019年4月2日.
 */
public interface ComplianceService {
	
	/**
	 * <p>Description: 查询订单数据</p>
	 * @param storeIds 门店id集合
	 * @param salesOrderNumber 销售编码
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param salesOrderStatus 订单状态
	 * @param activityIds 活动id集合
	 * @return
	 */
	ExecuteResult<List<SalesOrderVO>> queryByStoreIds(String orgIds, String storeIds, String salesOrderNumber, String startDate, String endDate, String salesOrderStatus, String activityIds, Integer customerId);
	/**
	 * <p>Description: 分页查询订单数据</p>
	 * @param storeIds 门店id集合
	 * @param salesOrderNumber 销售编码
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param salesOrderStatus 订单状态
	 * @param activityIds 活动id集合
	 * @return
	 */
	ExecuteResult<PageInfo<SalesOrderVO>> queryPageByStoreIds(QueryParamVO paramVO);
	/**
	 * <p>Description: 查询商品订单数据</p>
	 * @param orderIds 订单id集合
	 * @param minAmount 最小金额
	 * @param maxAmount 最大金额
	 * @return
	 */
	ExecuteResult<List<OrderGoodsVO>> queryListByOrderId(HashSet<Integer> orderIds, BigDecimal minAmount, BigDecimal maxAmount, String goodsSn, String goodsFrameNumber);
	/**
	 * <p>Description: 分页查询商品订单数据</p>
	 * @param orderIds 订单id集合
	 * @param minAmount 最小金额
	 * @param maxAmount 最大金额
	 * @return
	 */
	ExecuteResult<PageInfo<OrderGoodsVO>> queryPageByOrderId(HashSet<Integer> orderIds, BigDecimal minAmount, BigDecimal maxAmount, String goodsSn, String goodsFrameNumber,Page page);
	/**
	 * <p>Description: 统计商品退货次数</p>
	 * @param orderGoodsIds 订单商品ids
	 * @param goodsId 商品Sn
	 * @return
	 */
	Long queryReturnGoodsCount(HashSet<Integer> orderIdsSet, String goodsSn);
	/**
	 * <p>Description: 退货有序商品查询</p>
	 * @param orderIdsSet
	 * @param goodsSn
	 * @param goodsFrameNumber
	 * @param goodsIdSet
	 * @return
	 */
	ExecuteResult<List<OrderGoodsVO>> queryReturnGoods(ReturnGoodsParamVO paramVO);
	/**
	 * <p>Description: 查询零售差异数据</p>
	 * @param orderIds
	 * @param minAmount
	 * @param maxAmount
	 * @param goodsSn
	 * @param goodsFrameNumber
	 * @return
	 */
	ExecuteResult<List<OrderGoodsVO>> queryDifferenceList(HashSet<Integer> orderIds, BigDecimal minAmount, BigDecimal maxAmount, String goodsSn, String goodsFrameNumber);
	/**
	 * <p>Description: 分页查询零售活动订单数据</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<PageInfo<SalesOrderVO>> queryPageActivityOrder(QueryParamVO paramVO);
	/**
	 * <p>Description: 查询零售活动订单数据</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<List<SalesOrderVO>> queryActivityOrder(QueryParamVO paramVO);
	/**
	 * <p>Description: 零售差异商品订单page查询</p>
	 * @author zhouyou
	 * @date 2019年5月13日
	 */
	ExecuteResult<PageInfo<OrderGoodsVO>> queryPageDifference(DifferenceParamVO paramVO, Page page);
	/**
	 * <p>Description: 零售差异商品订单list查询</p>
	 * @param paramVO
	 * @return
	 * @author zhouyou
	 * @date 2019年5月14日
	 */
	ExecuteResult<List<OrderGoodsVO>> queryListDifference(DifferenceParamVO paramVO);
}

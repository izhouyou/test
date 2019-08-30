package com.camelot.order.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.StatisticsTotalDomain;

@Mapper
public interface StatisticsTotalDAO extends BaseDAO<StatisticsTotalDomain, Long>  {
	/**
	 * <p>Description: 统计数量</p>
	 * @param statisticsTotalDomain 统计数量
	 * @return
	 */
	Long queryCount(StatisticsTotalDomain statisticsTotalDomain);
	/**
	 * <p>Description: 销售量统计</p>
	 * @param storeIds 门店ids
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	Map<String, Object> queryStatisticsResult(@Param("orgIds") HashSet<String> orgIds, @Param("storeIds") HashSet<String> storeIds, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Integer userId, @Param("vehicleCategoryId") Integer vehicleCategoryId, @Param("merchCategoryId") Integer merchCategoryId);
	/**
	 * <p>Description: 销售订单商品销量统计列表</p>
	 * @param storeIdsSet 门店ids
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param goodsFirstCategoryId 商品一级大类
	 * @return
	 */
	List<Map<String, Object>> queryGoodsVolume(@Param("orgIds") HashSet<String> orgIds, @Param("storeIds") HashSet<String> storeIdsSet, @Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("goodsFirstCategoryId") Integer goodsFirstCategoryId);
	/**
	 * <p>Description: queryOrderStatistics</p>
	 * @param storeIdsSet 门店ids
	 * @param startDate 开始时间
	 * @param endDate 结束时间
 	 * @return
	 */
	List<Map<String, Object>> queryOrderStatistics(@Param("orgIds") HashSet<String> orgIds, @Param("storeIds") HashSet<String> storeIdsSet, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Integer userId);
	/**
	 * <p>Description: 查询有效订单整车分类统计列表</p>
	 * @param domain
	 * @param startDate
	 * @param endDate
	 * @param storeIds
	 * @return
	 */
	List<Map<String, Object>> queryCarAssort(@Param("domain") StatisticsTotalDomain domain, @Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("storeIds") HashSet<String> storeIds,@Param("orgIds") HashSet<String> orgIds);
	/**
	 * <p>Description: 查询每时订单/总额统计</p>
	 * @param storeIdsSet
	 * @return
	 */
	List<Map<String, Object>> queryToDayOrderStatistics(@Param("orgIds") HashSet<String> orgIds, @Param("storeIds") HashSet<String> storeIdsSet, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Integer userId);
	/**
	 * <p>Description: 查询List</p>
	 * @param storeIdsSet
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> queryGoodsList(@Param("storeIds") String storeIdsSet, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("sortFlag") Integer sortFlag, @Param("userId") Integer userId);
	/**
	 * <p>Description: 查询当前统计表，订单修改时间的最大值</p>
	 * @return
	 */
	String queryMaxDate();
}

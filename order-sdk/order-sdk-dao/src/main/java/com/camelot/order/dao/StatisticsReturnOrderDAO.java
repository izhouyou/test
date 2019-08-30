package com.camelot.order.dao;

import com.camelot.order.domain.StatisticsReturnOrderDomain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.camelot.common.dao.BaseDAO;
@Mapper
public interface StatisticsReturnOrderDAO extends BaseDAO<StatisticsReturnOrderDomain, Long>  {
	/**
	 * <p>Description: 退货额统计</p>
	 * @param storeIds 门店ids
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	Map<String, BigDecimal> queryStatisticsResult(@Param("orgIds") HashSet<String> orgIds, @Param("storeIds") HashSet<String> storeIds, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Integer userId);

}

package com.camelot.order.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.NotBuyDomain;

@Mapper
public interface NotBuyDAO extends BaseDAO<NotBuyDomain, Long>  {

	/**
	 * <p>Description: 查询未购买上报统计</p>
	 * @param storeIdsSet
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> queryNotBuy(@Param("storeIds") HashSet<String> storeIds, @Param("startDate") String startDate, @Param("endDate") String endDate);

	/**
	 * <p>Description: 查询未购买数据</p>
	 * @param storeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> queryCustomerIdSet(@Param("storeId") String storeId, @Param("startDate") String startDate, @Param("endDate") String endDate);

	/**
	 * <p>Description: 查询未购买数量</p>
	 * @param storeIds
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> queryNotBuyCause(@Param("storeId") String storeId, @Param("startDate") String startDate, @Param("endDate") String endDate);

}

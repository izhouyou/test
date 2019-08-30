package com.camelot.order.dao;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.OrderCustomerDomain;
import com.camelot.order.domain.SalesOrderDomain;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SalesOrderDAO extends BaseDAO<SalesOrderDomain, Long>  {
    /**
     *@Description 根据时间查询当天最大的销售订单编号
     *@author xupengfei
     *@Data 2019年4月4日 
     * @param nowDate
     * @return
     */
    String getMaxSalesOrderNumber(String nowDate);

	/**
	 * <p>Description: </p>
	 * @param orgIds 城市ids
	 * @param storeIds 门店id
	 * @param salesOrderNumber 销售编码
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param orderStatusList 订单状态集合
	 * @param activityIds 活动id集合
	 * @return
	 */
	List<SalesOrderDomain> queryByStoreIds(@Param("orgIds") HashSet<String> orgIds, @Param("storeIds") HashSet<String> storeIds, @Param("salesOrderNumber") String salesOrderNumber,
			@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("orderStatusList") HashSet<String> orderStatusList,
			@Param("activityIds") HashSet<String> activityIds, @Param("customerId") Integer customerId);
	
	/**
	 * <p>Description: 根据时间,查询订单数据</p>
	 * @param createDate时间
	 * @return
	 */
	List<SalesOrderDomain> queryListByDate(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("orderStatusList") List<Integer> orderStatusList);
	
	/**
	 * <p>Description: 查询门店订单数量统计</p>
	 * @param storeIdsSet
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> queryCancelOrder(@Param("orgIds") HashSet<String> orgIdSet,@Param("storeIds") HashSet<String> storeIdsSet, @Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("orderStatusList") HashSet<String> orderStatusList);

	/**
	 * <p>Description: 查询某状态订单</p>
	 * @param storeId
	 * @param startDate
	 * @param endDate
	 * @param salesOrderStatus
	 * @return
	 */
	List<Map<String, Object>> queryCustomerIdSet(@Param("storeId") String storeId, @Param("startDate") String startDate, @Param("endDate") String endDate,@Param("salesOrderStatus") String salesOrderStatus);

	/**
	 * <p>Description: 查询某状态订单数量</p>
	 * @param storeId
	 * @param startDate
	 * @param endDate
	 * @param salesOrderStatus
	 * @return
	 */
	List<Map<String, Object>> queryCancelCause(@Param("storeId") String storeId, @Param("startDate") String startDate, @Param("endDate") String endDate,@Param("salesOrderStatus") String salesOrderStatus);

	
	/**
	 *@Description 统计消费者相关订单数量和订单金额
	 *@author xupengfei
	 *@Date 2019年4月20日 
	 * @param domain
	 * @return
	 */
	OrderCustomerDomain statisticsOrderWithCustomer(OrderCustomerDomain domain);

	/**
	 * <p>Description: 查询活动订单数据</p>
	 * @param storeIdsSet
	 * @param salesOrderNumber
	 * @param startDate
	 * @param endDate
	 * @param salesOrderStatusSet
	 * @param activityIdSet
	 * @param customerId
	 * @return
	 */
	List<SalesOrderDomain> queryActivityOrder(@Param("orgIds") HashSet<String> orgIds,@Param("storeIds") HashSet<String> storeIds, @Param("salesOrderNumber") String salesOrderNumber,
			@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("orderStatusList") HashSet<String> orderStatusList,
			@Param("activityIds") HashSet<String> activityIds, @Param("customerId") Integer customerId);
	
	
	/**
	 *@Description 根据门店ID集合查询订单数据(Group by 消费者ID)
	 *@author xupengfei
	 *@Date 2019年5月1日 
	 * @param domain
	 * @return
	 */
	List<SalesOrderDomain> queryOrderByStoreIds(SalesOrderDomain domain);

	/**
	 * <p>Title: toDayAmount</p>
	 * <p>Description: 今日销售金额和订单数</p>
	 * @param salesOrderStatusSet
	 * @param userId
	 * @param storeIds
	 * @return
	 */
	Map<String, Object> toDayAmount(@Param("salesOrderStatusSet") HashSet<String> salesOrderStatusSet, @Param("userId") Integer userId, @Param("storeId") String storeId);
}

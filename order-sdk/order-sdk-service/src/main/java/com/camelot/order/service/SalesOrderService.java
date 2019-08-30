package com.camelot.order.service;

import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.vo.OrderCustomerVO;
import com.camelot.order.export.vo.SalesOrderVO;

public interface SalesOrderService extends BaseService<SalesOrderVO> {
	/**
	 * <p>Description: 根据起止时间,查询提交的所有数据</p>
	 * @param createDate
	 * @param endDate
	 * @param orderStatusList
	 * @return
	 */
	ExecuteResult<List<SalesOrderVO>> queryListByDate(String createDate, String endDate, List<Integer> orderStatusList);

	/**
	 *@Description 统计消费者相关的订单数目和订单金额
	 *@author xupengfei
	 *@Date 2019年4月20日 
	 * @param vo
	 * @return
	 */
	ExecuteResult<OrderCustomerVO> statisticOrderWithCustomer(OrderCustomerVO vo);
	
	/**
	 *@Description 根据门店ID集合查询订单数据(Group by 消费者ID)
	 *@author xupengfei
	 *@Date 2019年5月1日 
	 * @param vo
	 * @return
	 */
	ExecuteResult<List<SalesOrderVO>> queryOrderByStoreIds(SalesOrderVO vo);
}

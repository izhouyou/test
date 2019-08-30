package com.camelot.order.export.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.vo.OrderCustomerVO;
import com.camelot.order.export.vo.SalesOrderDetailVO;
import com.camelot.order.export.vo.SalesOrderMobileVO;
import com.camelot.order.export.vo.SalesOrderVO;
import com.camelot.order.export.vo.SalesOrderViewVO;

public interface SalesOrderExportService extends BaseService<SalesOrderVO> {
	
	/**
	 *@Description 获取最大的单号
	 *@author xupengfei
	 *@Date 2019年4月4日 
	 * @param nowDate
	 * @return
	 */
	 String getMaxSalesOrderNumber(String nowDate);
	 
	 /**
	 *@Description 对查询结果列表进行处理
	 *@author xupengfei
	 *@Date 2019年4月4日 
	 * @param list
	 * @return
	 */
	List<SalesOrderVO> handleSalesOrderList(List<SalesOrderVO> list);
	
	
	/**
	 *@Description 提交订单信息
	 *@author xupengfei
	 *@Date 2019年4月5日 
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	ExecuteResult<String> submitSalesOrder(SalesOrderViewVO vo) throws Exception;
	
	
	/**
	 *@Description 处理对象,封装为订单详情
	 *@author xupengfei
	 *@Date 2019年4月8日 
	 * @param vo
	 * @return
	 */
	SalesOrderDetailVO handleSalesOrderDetail(SalesOrderVO vo);
	
	/**
	 *@Description 取消订单 
	 *@author xupengfei
	 *@Date 2019年4月9日 
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	ExecuteResult<SalesOrderVO> cancelSalesOrder(SalesOrderVO vo) throws Exception;
	
	/**
	 *@Description 对查询结果进行处理(移动端)
	 *@author xupengfei
	 *@Date 2019年4月9日 
	 * @param list
	 * @return
	 */
	List<SalesOrderMobileVO> handleMobileSalesOrderList(List<SalesOrderVO> list);
	
	/**
	 *@Description 移动端查询前处理查询条件对象
	 *@author xupengfei
	 *@Date 2019年4月15日 
	 * @param vo
	 * @return
	 */
	SalesOrderVO handleMobileVOBeforeQuery(SalesOrderVO vo);
	
	/**
	 *@Description 统计消费者相关订单数量和金额(已完成)
	 *@author xupengfei
	 *@Date 2019年4月20日 
	 * @param String
	 * @return
	 */
	ExecuteResult<List<OrderCustomerVO>> statisticOrderWithCustomer(String ids);
	
	/**
	 *@Description 处理数据权限
	 *@author xupengfei
	 *@Date 2019年4月22日 
	 * @param vo
	 * @return
	 */
	SalesOrderVO handleDataPermission(SalesOrderVO vo);
	
	

}

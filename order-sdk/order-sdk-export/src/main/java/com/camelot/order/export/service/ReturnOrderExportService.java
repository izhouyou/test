package com.camelot.order.export.service;

import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.vo.ReturnOrderVO;
import com.camelot.order.export.vo.ReturnOrderDetailVO;
import com.camelot.order.export.vo.ReturnOrderMobileVO;

public interface ReturnOrderExportService extends BaseService<ReturnOrderVO>{
	
 	/**
	 *@Description 对查询结果列表进行处理
	 *@author xupengfei
	 *@Date 2019年4月4日 
	 * @param list
	 * @return
	 */
	List<ReturnOrderVO> handleReturnOrderList(List<ReturnOrderVO> list);
	/**
	 *@Description 处理对象,封装为订单详情
	 *@author xupengfei
	 *@Date 2019年4月8日 
	 * @param vo
	 * @return
	 */
	ReturnOrderDetailVO handleReturnOrderDetail(ReturnOrderVO vo);
	
	/**
	 *@Description 获取最大的单号
	 *@author xupengfei
	 *@Date 2019年4月4日 
	 * @param nowDate
	 * @return
	 */
	 String getMaxReturnOrderNumber(String nowDate);
	 
	 /**
	 *@Description 退单
	 *@author xupengfei
	 *@Date 2019年4月9日 
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	ExecuteResult<String> submitReturnOrder(ReturnOrderVO vo) throws Exception;
	
	/**
	 *@Description 对查询结果进行处理(移动端)
	 *@author xupengfei
	 *@Date 2019年4月9日 
	 * @param list
	 * @return
	 */
	List<ReturnOrderMobileVO> handleMobileReturnOrderList(List<ReturnOrderVO> list);
	
	/**
	 *@Description 查询之前对查询条件处理
	 *@author xupengfei
	 *@Date 2019年4月10日 
	 * @param vo
	 * @return
	 */
	ReturnOrderVO handleVOBeforeQuery(ReturnOrderVO vo);
	
	/**
	 *@Description 查询之前对查询条件处理(移动端)
	 *@author xupengfei
	 *@Date 2019年4月15日 
	 * @param vo
	 * @return
	 */
	ReturnOrderVO handleMobileVOBeforeQuery(ReturnOrderVO vo);
}

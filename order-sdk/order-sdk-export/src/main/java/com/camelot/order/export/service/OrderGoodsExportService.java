package com.camelot.order.export.service;

import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.vo.OrderGoodsVO;

public interface OrderGoodsExportService extends BaseService<OrderGoodsVO>{
	
	/**
	 *@Description 补充订单信息
	 *@author xupengfei
	 *@Data 2019年4月6日 
	 * @param vo
	 * @return
	 */
	ExecuteResult<List<OrderGoodsVO>> handleOrderGoods(List<OrderGoodsVO> list);

	/**
	 * @Description 查询整车商品订单
	 * @param salesOrderId
	 * @return
	 */
	ExecuteResult<List<OrderGoodsVO>> queryVehicle(Integer salesOrderId);
	
	ExecuteResult<List<OrderGoodsVO>> querySales();
	
	ExecuteResult<List<OrderGoodsVO>> queryReturn();
}

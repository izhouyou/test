package com.camelot.order.service.impl.export;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.OrderGoodsDAO;
import com.camelot.order.domain.OrderGoodsDomain;
import com.camelot.order.export.service.OrderGoodsExportService;
import com.camelot.order.export.vo.OrderGoodsVO;
import com.camelot.order.export.vo.SalesOrderVO;
import com.camelot.order.service.SalesOrderService;
import com.camelot.order.service.base.BaseServiceImpl;

@Service("orderGoodsExportService")
public class OrderGoodsExportServiceImpl extends BaseServiceImpl<OrderGoodsVO,OrderGoodsDomain> implements OrderGoodsExportService {
	
	private static Logger log = Log.get(OrderGoodsExportServiceImpl.class);
	
	@Autowired
	SalesOrderService salesOrderService;
	@Autowired
	OrderGoodsDAO orderGoodsDAO;
	
	@Override
	public ExecuteResult<List<OrderGoodsVO>> handleOrderGoods(List<OrderGoodsVO> list) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "OrderGoodsExportServiceImp-handleOrderGoods", JSONObject.toJSONString(list));
		ExecuteResult<List<OrderGoodsVO>> result = new ExecuteResult<List<OrderGoodsVO>>();
		for(OrderGoodsVO vo : list){	
			if(Utility.isNotEmpty(vo.getOrderId())){
					try{
						SalesOrderVO so = salesOrderService.queryById(vo.getOrderId().longValue()).getResult();
						if(Utility.isNotEmpty(so)){
							vo.setSalesOrderNumber(so.getSalesOrderNumber());
							vo.setSalesOrderStatusValue(getStatusValueByKey(so.getSalesOrderStatus()));
						}else{
							result.setCode(Constants.ERROR_CODE);
							result.setResultMessage("订单ID有误");
							Log.debug(log, "\n 方法[{}]，出参：[{}]", "OrderGoodsExportServiceImp-handleOrderGoods", JSONObject.toJSONString(result));
							return result;
						}
					}catch(Exception e){
						result.setCode(Constants.ERROR_CODE);
						result.setResultMessage(e.toString());
						Log.error(log, "\n 方法[{}]，异常：[{}]", "OrderGoodsExportServiceImp-handleOrderGoods", e.getMessage());
						return result;
					}
				}else{
					result.setCode(Constants.ERROR_CODE);
					result.setResultMessage("订单ID为空");
					Log.debug(log, "\n 方法[{}]，出参：[{}]", "OrderGoodsExportServiceImp-handleOrderGoods", JSONObject.toJSONString(result));
					return result;
				}
		}
		result.setCode(Constants.SUCCESS_CODE);
		result.setResult(list);
		result.setResultMessage("转换成功");
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "OrderGoodsExportServiceImp-handleOrderGoods", JSONObject.toJSONString(result));
		return result;
	}
	
	public String getStatusValueByKey(Integer key){
		if(OrderConstants.ORDER_STATUS_SUBMISSION.equals(key)){
			return "已提交";
		}
		else if(OrderConstants.ORDER_STATUS_FINISH.equals(key)){
			return "已完成";
		}
		else if(OrderConstants.ORDER_STATUS_NOPAY.equals(key)){
			return "未支付";
		}
		else if(OrderConstants.ORDER_STATUS_CANCLE.equals(key)){
			return "已取消";
		}
		else if(OrderConstants.ORDER_STATUS_RETURN.equals(key)){
			return "已退货";
		}
		else{
			return "";
		}
	}

	@Override
	public ExecuteResult<List<OrderGoodsVO>> queryVehicle(Integer orderId) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "OrderGoodsExportServiceImp-queryVehicle", JSONObject.toJSONString(orderId));
		ExecuteResult<List<OrderGoodsVO>> result = new ExecuteResult<>();
		boolean flag = false;
		try {
			List<OrderGoodsDomain> queryResult = orderGoodsDAO.queryVehicle(orderId);
			if(Utility.isNotEmpty(queryResult)) {
				List<OrderGoodsVO> voList = BeanUtil.copyList(OrderGoodsVO.class, queryResult);
				result.setResult(voList);
				flag = true;
			}
			if(flag) {
				result.setCode(Constants.SUCCESS_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			} else {
				result.setCode(Constants.EMPTY_CODE);
				result.setResultMessage(Constants.QUERY_SUCCESS);
			}
		} catch (Exception e) {
			Log.error(log, "\n 方法[{}]，异常：[{}]", "OrderGoodsExportServiceImp-queryVehicle", e.toString());
			result.setCode(Constants.ERROR_CODE);
			result.setResultMessage(e.toString());
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "OrderGoodsExportServiceImp-queryVehicle", JSONObject.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<List<OrderGoodsVO>> querySales() {
        ExecuteResult<List<OrderGoodsVO>> result = new ExecuteResult<List<OrderGoodsVO>>();
        try {
            List<OrderGoodsDomain> queryResult= orderGoodsDAO.querySales();
            if(queryResult.size() > 0){
                List<OrderGoodsVO> rtnList = BeanUtil.copyList(OrderGoodsVO.class,queryResult);
                result.setResult(rtnList);
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
        }
        return result;
    }

	@Override
	public ExecuteResult<List<OrderGoodsVO>> queryReturn() {
        ExecuteResult<List<OrderGoodsVO>> result = new ExecuteResult<List<OrderGoodsVO>>();
        try {
            List<OrderGoodsDomain> queryResult= orderGoodsDAO.queryReturn();
            if(queryResult.size() > 0){
                List<OrderGoodsVO> rtnList = BeanUtil.copyList(OrderGoodsVO.class,queryResult);
                result.setResult(rtnList);
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
        }
        return result;
    }

}

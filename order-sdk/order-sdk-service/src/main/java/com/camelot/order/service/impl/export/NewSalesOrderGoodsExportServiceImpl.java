package com.camelot.order.service.impl.export;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.domain.NewSalesOrderGoodsDomain;
import com.camelot.order.export.service.NewSalesOrderExportService;
import com.camelot.order.export.service.NewSalesOrderGoodsExportService;
import com.camelot.order.export.snapshot.NewSalesOrderGoodsVO;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisitcsGoodsSalesVO;
import com.camelot.order.export.vo.StatisticsGoodsCategoryVO;
import com.camelot.order.service.NewSalesOrderGoodsService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hudya
 */
@Service("newSalesOrderGoodsExportService")
public class NewSalesOrderGoodsExportServiceImpl extends BaseServiceImpl<NewSalesOrderGoodsVO, NewSalesOrderGoodsDomain> implements NewSalesOrderGoodsExportService {

    private static Logger log = Log.get(NewSalesOrderGoodsExportServiceImpl.class);

    /**
     * 商品订单信息
     */
    @Autowired
    private NewSalesOrderGoodsService newSalesOrderGoodsService;
    /**
     * 编号service
     */
    @Autowired
    private NewOrderNumberExportService newOrderNumberExportService;
    /**
     * 订单service
     */
    @Autowired
    private NewSalesOrderExportService newSalesOrderExportService;

    @Override
    public ExecuteResult<NewSalesOrderGoodsVO> verfyOrderGoods(NewSalesOrderGoodsVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsExportServiceImpl-addCustomer", JSONObject.toJSONString(vo));
        ExecuteResult<NewSalesOrderGoodsVO> result = new ExecuteResult<>();
        try {
            // 查询订单商品信息
            ExecuteResult<List<NewSalesOrderGoodsVO>> listExecuteResult = newSalesOrderGoodsService.queryList(vo);
            if(Utility.isNotEmpty(listExecuteResult.getResult())){
                // 查询订单信息
                List<NewSalesOrderGoodsVO> list = listExecuteResult.getResult();
                List<Long> fieldList = list.stream().map(e -> e.getSalesOrderId()).collect(Collectors.toList());
                NewSalesOrderVO newSalesOrderVO = new NewSalesOrderVO();
                newSalesOrderVO.setSalesOrderIdList(fieldList);
                ExecuteResult<List<NewSalesOrderVO>> executeResult = newSalesOrderExportService.queryList(newSalesOrderVO);
                if(Utility.isNotEmpty(executeResult.getResult())){
                    List<NewSalesOrderVO> salesOrderVOS = executeResult.getResult();
                    for (NewSalesOrderVO salesOrderVO : salesOrderVOS) {
                        Integer salesOrderStatusId = salesOrderVO.getSalesOrderStatusId();
                        Integer paymentStatusId = salesOrderVO.getPaymentStatusId();
                        Integer returnStatusId = salesOrderVO.getReturnStatusId();
                        /**
                         * 已提交108           未支付103          未退货226
                         * 已取消110           已支付102          已退货227
                         * 已完成111
                         */
                        // 已提交 未支付 未退货 不能再被下单
                        if(OrderConstants.ORDER_STATUS_SUBMISSION.equals(salesOrderStatusId)&&OrderConstants.ORDER_DICT_RUTURNNO.equals(returnStatusId)){
                            result.setResult(list.get(0));
                            result.setCode(Constants.ERROR_CODE);
                            result.setResultMessage("该商品已提交订单，请勿重复提交");
                            return result;
                        }else if(OrderConstants.ORDER_STATUS_FINISH.equals(salesOrderStatusId)&&OrderConstants.ORDER_DICT_RUTURNNO.equals(returnStatusId)){
                            result.setResult(list.get(0));
                            result.setCode(Constants.ERROR_CODE);
                            result.setResultMessage(list.get(0).getGoodsSn()+"该商品已上报");
                            return result;
                        }
                    }
                }else{
                    result.setCode(Constants.SUCCESS_CODE);
                    result.setResultMessage(Constants.QUERY_SUCCESS);
                }
            }else{
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-verfyOrderGoods", e.getMessage());
        }
        result.setCode(Constants.SUCCESS_CODE);
        result.setResultMessage(Constants.QUERY_SUCCESS);
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsExportServiceImpl-verfyOrderGoods", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<NewSalesOrderGoodsVO>> queryOrderGoods(NewSalesOrderGoodsVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsExportServiceImpl-addCustomer", JSONObject.toJSONString(vo));
        ExecuteResult<List<NewSalesOrderGoodsVO>> result = new ExecuteResult<>();
        try {
            ExecuteResult<List<NewSalesOrderGoodsVO>> executeResult = newSalesOrderGoodsService.queryList(vo);
            if(Utility.isNotEmpty(executeResult.getResult())) {
                result.setResult(executeResult.getResult());
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.QUERY_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryOrderGoods", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryOrderGoods", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<NewSalesOrderGoodsVO> addOrderGoods(NewSalesOrderGoodsVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsExportServiceImpl-addOrderGoods", JSONObject.toJSONString(vo));
        ExecuteResult<NewSalesOrderGoodsVO> result = new ExecuteResult<>();
        try {
            ExecuteResult<String> genid = newOrderNumberExportService.getGenid();
            if(Utility.isEmpty(genid.getResult())){
                result.setCode(Constants.ERROR_CODE);
                result.setResultMessage("网络延迟,请稍后重试!");
                Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-submitSalesOrder", result);
                return result;
            }
            // 添加主键id
            String salesOrderGoodsId = genid.getResult();
            vo.setSalesOrderGoodsId(Long.valueOf(salesOrderGoodsId));
            vo.setDelFlg(Constants.DELFLG_NORMAL);
            ExecuteResult<NewSalesOrderGoodsVO> executeResult = newSalesOrderGoodsService.add(vo);
            if(Utility.isNotEmpty(executeResult.getResult())) {
                result.setResult(executeResult.getResult());
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.SAVE_SUCCESS);
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.SAVE_FAILURE);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(Constants.SAVE_FAILURE);
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-addOrderGoods", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsExportServiceImpl-addOrderGoods", JSONObject.toJSONString(result));
        return result;
    }

	@Override
	public ExecuteResult<PageInfo<NewSalesOrderGoodsVO>> queryPageDifference(NewSalesOrderGoodsVO vo, Page page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryPageDifference",  JSONObject.toJSONString(vo));
        ExecuteResult<PageInfo<NewSalesOrderGoodsVO>> result = new ExecuteResult<>();
        try {
			result = newSalesOrderGoodsService.queryPageDifference(vo, page);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryPageDifference", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryPageDifference",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<NewSalesOrderGoodsVO>> queryListDifference(NewSalesOrderGoodsVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryListDifference",  JSONObject.toJSONString(vo));
        ExecuteResult<List<NewSalesOrderGoodsVO>> result = new ExecuteResult<>();
        try {
			result = newSalesOrderGoodsService.queryListDifference(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryListDifference", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryListDifference",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<SalesVolumeVO> queryCountTotal(NewSalesOrderGoodsVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryCountTotal",  JSONObject.toJSONString(vo));
        ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
        try {
			result = newSalesOrderGoodsService.queryCountTotal(vo);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryCountTotal", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryCountTotal",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> queryPageGoodsVolume(NewSalesOrderGoodsVO vo, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryPageGoodsVolume",  JSONObject.toJSONString(vo));
        ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> result = new ExecuteResult<>();
        try {
			result = newSalesOrderGoodsService.queryPageGoodsVolume(vo, page);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryPageGoodsVolume", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryPageGoodsVolume",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<StatisticsGoodsCategoryVO>> queryListVehicleAmount(NewSalesOrderGoodsVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryListVehicleAmount",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsGoodsCategoryVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	result = newSalesOrderGoodsService.queryListVehicleAmount(vo);
            if(flag) {
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            } else {
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryListVehicleAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsExportServiceImpl-queryListVehicleAmount",  JSONObject.toJSONString(result));
        return result;
	}


}

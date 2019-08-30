package com.camelot.order.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.NewSalesOrderDAO;
import com.camelot.order.domain.NewReturnOrderDomain;
import com.camelot.order.domain.NewSalesOrderDomain;
import com.camelot.order.domain.OrderCustomerDomain;
import com.camelot.order.export.snapshot.NewReturnOrderVO;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
import com.camelot.order.export.vo.*;
import com.camelot.order.service.NewSalesOrderService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hudya
 */
@Service("newSalesOrderService")
public class NewSalesOrderServiceImpl extends BaseServiceImpl<NewSalesOrderVO, NewSalesOrderDomain> implements NewSalesOrderService {

    private static Logger log = Log.get(NewSalesOrderServiceImpl.class);
    @Autowired
    private NewSalesOrderDAO newSalesOrderDAO;

    @Override
    public ExecuteResult<OrderCustomerVO> statisticsOrderWithCustomer(OrderCustomerVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-statisticOrderWithCustomer", JSONObject.toJSONString(vo));
        ExecuteResult<OrderCustomerVO> result = new ExecuteResult<OrderCustomerVO>();
        try{
            OrderCustomerDomain dom = BeanUtil.copyPropertes(OrderCustomerDomain.class, vo);
            OrderCustomerDomain queryResult = newSalesOrderDAO.statisticsOrderWithCustomer(dom);
            if(Utility.isNotEmpty(queryResult.getCustomerId())){
                OrderCustomerVO rtnvo = BeanUtil.copyPropertes(OrderCustomerVO.class,queryResult);
                result.setResult(rtnvo);
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_FAILURE);
            }
        }catch(Exception e){
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-statisticOrderWithCustomer", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-statisticOrderWithCustomer", JSONObject.toJSONString(result));
        return result;
    }

	@Override
	public ExecuteResult<List<NewSalesOrderVO>> queryListActivityData(NewSalesOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryListActivityData",  JSONObject.toJSONString(vo));
        ExecuteResult<List<NewSalesOrderVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
            List<NewSalesOrderDomain> queryResult= newSalesOrderDAO.queryListActivityData(dom);
            if(queryResult.size() > 0){
                List<NewSalesOrderVO> rtnList = (List<NewSalesOrderVO>)BeanUtil.copyList(NewSalesOrderVO.class,queryResult);
                result.setResult(rtnList);
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
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryListActivityData", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryListActivityData",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<Long> queryCount(NewSalesOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryCount",  JSONObject.toJSONString(vo));
        ExecuteResult<Long> result = new ExecuteResult<>();
        try {
        	NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
        	Long amount = newSalesOrderDAO.queryCount(dom);
        	result.setResult(amount);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryCount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryCount",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<SalesVolumeVO> queryCountAmount(NewSalesOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryCountAmount",  JSONObject.toJSONString(vo));
        ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
        	Map<String, Object> amount = newSalesOrderDAO.queryCountAmount(dom);
        	if(Utility.isNotEmpty(amount)) {
        		SalesVolumeVO salesVolumeVO = JSONObject.parseObject(JSONObject.toJSONString(amount), SalesVolumeVO.class);
        		result.setResult(salesVolumeVO);
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
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryCountAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryCountAmount",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<Long>> queryListSalesOrderId(NewSalesOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryListSalesOrderId",  JSONObject.toJSONString(vo));
        ExecuteResult<List<Long>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
        	List<Long> amount = newSalesOrderDAO.queryListSalesOrderId(dom);
        	if(Utility.isNotEmpty(amount)) {
        		result.setResult(amount);
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
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryListSalesOrderId", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryListSalesOrderId",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<Map<String, Object>>> queryListToDayOrderTrend(NewSalesOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryListToDayOrderTrend",  JSONObject.toJSONString(vo));
        ExecuteResult<List<Map<String, Object>>> result = new ExecuteResult<>();
        try {
        	NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
        	List<Map<String, Object>> amount = newSalesOrderDAO.queryListToDayOrderTrend(dom);
        	if(Utility.isEmpty(amount)) {
        		Map<String, Object> empty = new HashMap<>();
    			empty.put("statisticsOrderAmount", 0);
    			empty.put("statisticsAmount", "0.00");
    			empty.put("statisticsDate", 0);
    			empty.put("statisticsDay", 0);
    			amount.add(empty);
        		result.setResult(amount);
    			result.setCode(Constants.EMPTY_CODE);
			}else {
				result.setResult(amount);
				result.setCode(Constants.SUCCESS_CODE);
			}
        	result.setResultMessage(Constants.QUERY_SUCCESS);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryListToDayOrderTrend", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryListToDayOrderTrend",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<Map<String, Object>>> statisticsOrderTrend(NewSalesOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryListToDayOrderTrend",  JSONObject.toJSONString(vo));
        ExecuteResult<List<Map<String, Object>>> result = new ExecuteResult<>();
        try {
        	NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
        	List<Map<String, Object>> amount = newSalesOrderDAO.statisticsOrderTrend(dom);
        	if(Utility.isEmpty(amount)) {
    			Map<String, Object> empty = new HashMap<>();
    			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    			String startDate = simpleDateFormat.format(vo.getStartCreateDate());
    			empty.put("statisticsOrderAmount", 0);
    			empty.put("statisticsAmount", "0.00");
    			empty.put("statisticsDate", startDate);
    			empty.put("statisticsDay", startDate.substring(startDate.length()-2, startDate.length()));
    			amount.add(empty);
    			result.setResult(amount);
    			result.setCode(Constants.EMPTY_CODE);
			}else {
				result.setResult(amount);
				result.setCode(Constants.SUCCESS_CODE);
			}
            result.setResultMessage(Constants.QUERY_SUCCESS);
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryListToDayOrderTrend", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryListToDayOrderTrend",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<StatisticsNotBuyVO>> queryListCancleTotal(NewSalesOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryListNotBuyTotal",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsNotBuyVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
        	List<Map<String, Object>> queryResult = newSalesOrderDAO.queryListCancleTotal(dom);
        	if(Utility.isNotEmpty(queryResult)) {
        		List<StatisticsNotBuyVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult), StatisticsNotBuyVO.class);
        		result.setResult(resultList);
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
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryListNotBuyTotal", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryListNotBuyTotal",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<StatisticsSourceVO>> queryListCustomerSource(NewSalesOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyServiceImpl-queryListCustomerSource",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
        	List<Map<String, Object>> queryResult = newSalesOrderDAO.queryListCustomerSource(dom);
        	if(Utility.isNotEmpty(queryResult)) {
        		List<StatisticsSourceVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult), StatisticsSourceVO.class);
        		result.setResult(resultList);
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
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryListCustomerSource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryListCustomerSource",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<StatisticsSourceVO>> queryListNotBuyCause(NewSalesOrderVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryListNotBuyCause",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
        	List<Map<String, Object>> queryResult = newSalesOrderDAO.queryListNotBuyCause(dom);
        	if(Utility.isNotEmpty(queryResult)) {
        		List<StatisticsSourceVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult), StatisticsSourceVO.class);
        		result.setResult(resultList);
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
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryListNotBuyCause", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryListNotBuyCause",  JSONObject.toJSONString(result));
        return result;
	}

    @Override
    public String getMaxSalesOrderNumber(String nowDate) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-getMaxSalesOrderNumber", nowDate);
        String result = "";
        try {
            result = newSalesOrderDAO.getMaxSalesOrderNumber(nowDate);
        } catch (Exception e) {
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-getMaxSalesOrderNumber", e.getMessage());
            return Constants.ERROR_CODE;
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-getMaxSalesOrderNumber", result);
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsSalesVO>> queryStatsSalesOrderPage(NewSalesOrderVO newSalesOrderParamVO){
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderServiceImpl-queryStatsSalesOrderPage", JSONObject.toJSONString(newSalesOrderParamVO));
        ExecuteResult<List<StatisticsSalesVO>> result = new ExecuteResult<List<StatisticsSalesVO>>();
        try {
            NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, newSalesOrderParamVO);

            //PageHelper.startPage(page.getPageNum(), page.getPageSize(), true);
            List<Map<String, Object>> queryResult= newSalesOrderDAO.queryStatsSalesOrderPage(dom);
            if(queryResult.size() > 0){
               // PageInfo pageInfo = new PageInfo<>(queryResult);
                List<StatisticsSalesVO> rtnList = (List<StatisticsSalesVO>)BeanUtil.copyList(StatisticsSalesVO.class,queryResult);

                /*
                int total = 0;

                for(StatisticsSalesVO vo:rtnList){
                    total = total +vo.getSaleAmount();
                }

                Integer storeID_ALL = Constants.STORE_ID_ALL;
                //当条件查询时，是精准搜索
                if(rtnList.size() == 1){

                }
                //添加 全部 信息
                StatisticsSalesVO statisticsSalesALLVO = new  StatisticsSalesVO();
                statisticsSalesALLVO.setStoreId(storeID_ALL);
                statisticsSalesALLVO.setStoreNumber("全部");
                statisticsSalesALLVO.setStoreName("全部");
                statisticsSalesALLVO.setStoreChannelNumber("全部");
                statisticsSalesALLVO.setSaleAmount(total);
                List<StatisticsSalesVO> statisticsSalesListAll = new ArrayList();
                statisticsSalesListAll.add(0,statisticsSalesALLVO);
                statisticsSalesListAll.addAll(rtnList);



                pageInfo.setList(statisticsSalesListAll);

                result.setResult(pageInfo);
                */
                result.setResult(rtnList);
                result.setCode(Constants.SUCCESS_CODE);
                result.setResultMessage(Constants.QUERY_SUCCESS);
            }else{
                result.setCode(Constants.EMPTY_CODE);
                result.setResultMessage(Constants.QUERY_FAILURE);
                return result;
            }
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryStatsSalesOrderPage", e.getMessage());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryStatsSalesOrderPage", JSONObject.toJSONString(result));
        return result;
    }


    @Override
    public ExecuteResult<List<StatisticsSourceVO>> queryStatsSalesBySource(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyServiceImpl-queryStatsSalesBySource",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
            NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
            List<Map<String, Object>> queryResult = newSalesOrderDAO.queryStatsSalesBySource(dom);
            if(Utility.isNotEmpty(queryResult)) {
                List<StatisticsSourceVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult), StatisticsSourceVO.class);
                result.setResult(resultList);
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
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryStatsSalesBySource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryStatsSalesBySource",  JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsSourceVO>> queryStatsSaleByActive(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyServiceImpl-queryStatsSalesBySource",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
            NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
            List<Map<String, Object>> queryResult = newSalesOrderDAO.queryStatsSaleByActive(dom);
            if(Utility.isNotEmpty(queryResult)) {
                List<StatisticsSourceVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult), StatisticsSourceVO.class);
                result.setResult(resultList);
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
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryStatsSalesBySource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryStatsSalesBySource",  JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public ExecuteResult<List<StatisticsSourceVO>> queryStatsSaleByCategory(NewSalesOrderVO vo) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewNotBuyServiceImpl-queryStatsSalesBySource",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsSourceVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
            NewSalesOrderDomain dom = (NewSalesOrderDomain)BeanUtil.copyPropertes(NewSalesOrderDomain.class, vo);
            List<Map<String, Object>> queryResult = newSalesOrderDAO.queryStatsSaleByCategory(dom);
            if(Utility.isNotEmpty(queryResult)) {
                List<StatisticsSourceVO> resultList = JSONObject.parseArray(JSONObject.toJSONString(queryResult), StatisticsSourceVO.class);
                result.setResult(resultList);
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
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderServiceImpl-queryStatsSalesBySource", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderServiceImpl-queryStatsSalesBySource",  JSONObject.toJSONString(result));
        return result;
    }



}
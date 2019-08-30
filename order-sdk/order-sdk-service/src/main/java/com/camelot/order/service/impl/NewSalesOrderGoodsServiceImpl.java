package com.camelot.order.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.common.utils.BeanUtil;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.dao.NewSalesOrderGoodsDAO;
import com.camelot.order.domain.NewSalesOrderGoodsDomain;
import com.camelot.order.export.snapshot.NewSalesOrderGoodsVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisitcsGoodsSalesVO;
import com.camelot.order.export.vo.StatisticsGoodsCategoryVO;
import com.camelot.order.service.NewSalesOrderGoodsService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.camelot.order.service.impl.export.NewSalesOrderGoodsExportServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author hudya
 */
@Service("newSalesOrderGoodsService")
public class NewSalesOrderGoodsServiceImpl extends BaseServiceImpl<NewSalesOrderGoodsVO, NewSalesOrderGoodsDomain> implements NewSalesOrderGoodsService {

	private static Logger log = Log.get(NewSalesOrderGoodsExportServiceImpl.class);
	
	@Autowired
	private NewSalesOrderGoodsDAO newSalesOrderGoodsDAO;
	
	@Override
	public ExecuteResult<PageInfo<NewSalesOrderGoodsVO>> queryPageDifference(NewSalesOrderGoodsVO vo, Page page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsServiceImpl-queryPageDifference",  JSONObject.toJSONString(vo));
        ExecuteResult<PageInfo<NewSalesOrderGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderGoodsDomain domain = BeanUtil.copyPropertes(NewSalesOrderGoodsDomain.class, vo);
        	PageHelper.startPage(page.getPageNum(), page.getPageSize(), true);
            List<NewSalesOrderGoodsDomain> queryResult= newSalesOrderGoodsDAO.queryListDifference(domain);
            if(queryResult.size() > 0){
                PageInfo pageInfo = new PageInfo<>(queryResult);
                List<NewSalesOrderGoodsVO> rtnList = (List<NewSalesOrderGoodsVO>)BeanUtil.copyList(NewSalesOrderGoodsVO.class,queryResult);
                pageInfo.setList(rtnList);
                result.setResult(pageInfo);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsServiceImpl-queryPageDifference", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsServiceImpl-queryPageDifference",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<NewSalesOrderGoodsVO>> queryListDifference(NewSalesOrderGoodsVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsServiceImpl-queryListDifference",  JSONObject.toJSONString(vo));
        ExecuteResult<List<NewSalesOrderGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderGoodsDomain domain = BeanUtil.copyPropertes(NewSalesOrderGoodsDomain.class, vo);
            List<NewSalesOrderGoodsDomain> queryResult= newSalesOrderGoodsDAO.queryListDifference(domain);
            if(queryResult.size() > 0){
                List<NewSalesOrderGoodsVO> rtnList = (List<NewSalesOrderGoodsVO>)BeanUtil.copyList(NewSalesOrderGoodsVO.class,queryResult);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsServiceImpl-queryListDifference", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsServiceImpl-queryListDifference",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<SalesVolumeVO> queryCountTotal(NewSalesOrderGoodsVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsServiceImpl-queryCountTotal",  JSONObject.toJSONString(vo));
        ExecuteResult<SalesVolumeVO> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderGoodsDomain domain = BeanUtil.copyPropertes(NewSalesOrderGoodsDomain.class, vo);
            Map<String, Object> queryResult= newSalesOrderGoodsDAO.queryCountTotal(domain);
            if(queryResult.size() > 0){
            	SalesVolumeVO salesVolumeVO = JSONObject.parseObject(JSONObject.toJSONString(queryResult), SalesVolumeVO.class);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsServiceImpl-queryCountTotal", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsServiceImpl-queryCountTotal",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> queryPageGoodsVolume(NewSalesOrderGoodsVO vo, Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsServiceImpl-queryCountTotal",  JSONObject.toJSONString(vo));
        ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderGoodsDomain domain = BeanUtil.copyPropertes(NewSalesOrderGoodsDomain.class, vo);
        	PageHelper.startPage(page.getPageNum(), page.getPageSize(), true);
            List<Map<String, Object>> queryResult= newSalesOrderGoodsDAO.queryPageGoodsVolume(domain);
            if(queryResult.size() > 0){
                PageInfo pageInfo = new PageInfo<>(queryResult);
                List<StatisitcsGoodsSalesVO> rtnList = JSONObject.parseArray(JSONObject.toJSONString(queryResult), StatisitcsGoodsSalesVO.class);
                pageInfo.setList(rtnList);
                result.setResult(pageInfo);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsServiceImpl-queryCountTotal", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsServiceImpl-queryCountTotal",  JSONObject.toJSONString(result));
        return result;
	}

	@Override
	public ExecuteResult<List<StatisticsGoodsCategoryVO>> queryListVehicleAmount(NewSalesOrderGoodsVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewSalesOrderGoodsServiceImpl-queryListVehicleAmount",  JSONObject.toJSONString(vo));
        ExecuteResult<List<StatisticsGoodsCategoryVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewSalesOrderGoodsDomain domain = BeanUtil.copyPropertes(NewSalesOrderGoodsDomain.class, vo);
            List<Map<String, Object>> queryResult= newSalesOrderGoodsDAO.queryListVehicleAmount(domain);
            if(queryResult.size() > 0){
                List<StatisticsGoodsCategoryVO> rtnList = JSONObject.parseArray(JSONObject.toJSONString(queryResult), StatisticsGoodsCategoryVO.class);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewSalesOrderGoodsServiceImpl-queryListVehicleAmount", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderGoodsServiceImpl-queryListVehicleAmount",  JSONObject.toJSONString(result));
        return result;
	}

}

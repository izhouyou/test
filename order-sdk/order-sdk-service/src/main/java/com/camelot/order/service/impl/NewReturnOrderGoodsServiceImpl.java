package com.camelot.order.service.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.common.utils.BeanUtil;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.NewReturnOrderGoodsDAO;
import com.camelot.order.domain.NewReturnOrderGoodsDomain;
import com.camelot.order.export.snapshot.NewReturnOrderGoodsVO;
import com.camelot.order.service.NewReturnOrderGoodsService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author hudya
 */
@Service("newReturnOrderGoodsService")
public class NewReturnOrderGoodsServiceImpl extends BaseServiceImpl<NewReturnOrderGoodsVO, NewReturnOrderGoodsDomain> implements NewReturnOrderGoodsService {

	private static Logger log = Log.get(NewReturnOrderGoodsServiceImpl.class);
	
	@Autowired
	private NewReturnOrderGoodsDAO newReturnOrderGoodsDAO;
	
	@Override
	public ExecuteResult<PageInfo<NewReturnOrderGoodsVO>> queryPageReturnGoods(NewReturnOrderGoodsVO vo,
			Page page) {
        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReturnOrderGoodsServiceImpl-queryPageReturnGoods",  JSONObject.toJSONString(vo));
        ExecuteResult<PageInfo<NewReturnOrderGoodsVO>> result = new ExecuteResult<>();
        boolean flag = false;
        try {
        	NewReturnOrderGoodsDomain domain = (NewReturnOrderGoodsDomain)BeanUtil.copyPropertes(NewReturnOrderGoodsDomain.class, vo);
        	PageHelper.startPage(page.getPageNum(), page.getPageSize(), true);
        	List<NewReturnOrderGoodsDomain> queryResult = newReturnOrderGoodsDAO.queryListReturnGoods(domain);
        	if(Utility.isNotEmpty(queryResult)) {
        		PageInfo pageInfo = new PageInfo<>(queryResult);
                List<NewReturnOrderGoodsVO> rtnList = (List<NewReturnOrderGoodsVO>)BeanUtil.copyList(NewReturnOrderGoodsVO.class,queryResult);
                rtnList.sort((x,y) -> Long.compare(x.getReturnNumber(),y.getReturnNumber()));
                Collections.reverse(rtnList);
                pageInfo.setList(rtnList);
                result.setResult(pageInfo);
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
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "NewReturnOrderGoodsServiceImpl-queryPageReturnGoods", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReturnOrderGoodsServiceImpl-queryPageReturnGoods",  JSONObject.toJSONString(result));
        return result;

	}

}

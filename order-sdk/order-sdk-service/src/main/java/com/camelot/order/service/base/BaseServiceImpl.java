package com.camelot.order.service.base;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.common.dao.BaseDAO;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.export.service.base.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * <p>Description: [增删改查接口实现]</p >
 *
 * @author < a href=" ">sunxiaozhe</ a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName BaseServiceImpl
 * Created on 2018/10/17.
 */
public abstract class BaseServiceImpl<VO extends Serializable, DOMAIN extends Serializable > implements BaseService<VO> {


	 private static Logger log = Log.get(BaseServiceImpl.class);

	    //设置serice调用接口
	    @Autowired
	    BaseDAO<DOMAIN, Long> baseDAO;
	    Class<?> voClass;

	    Class<?> domainClass;

	    /**
	     *
	     * @Title: setBaseService
	     * @Description: 设置service调用接口 ,被实现类调用
	     * @param: @param sysBaseService
	     * @return: void
	     * @throws
	     */
	    public BaseServiceImpl(){
	        //this.entityClass = Reflections.getClassGenricType(sysBaseService.getClass(),0);
	        Class<?> clazz = this.getClass();
	        Type genType = clazz.getGenericSuperclass();
	        Type[] params  = ((ParameterizedType) genType).getActualTypeArguments();
	        this.voClass = (Class<?>) params[0];
	        //System.out.println("====dtoClass======="+dtoClass.getName());

	        this.domainClass = (Class<?>) params[1];
	    }


	    @Override
	    @SuppressWarnings(value = { "unchecked" })
	    @Transactional(rollbackFor=Exception.class)
	    public ExecuteResult<VO> add(VO vo) throws Exception {
	    	Log.debug(log, "\n 方法[{}]，入参：[{}]", "BaseServiceImpl-add", JSONObject.toJSONString(vo));
	        ExecuteResult<VO> result=new ExecuteResult<VO>();
	        try {
	            DOMAIN dom = (DOMAIN) BeanUtil.copyPropertes(domainClass, vo);
	            Integer addResult=baseDAO.add(dom);
	            if (addResult >= 1) {
	                VO rtnDto=(VO)BeanUtil.copyPropertes(voClass,dom);
	                result.setResult(rtnDto);
	                result.setCode(Constants.SUCCESS_CODE);
	                result.setResultMessage(Constants.SAVE_SUCCESS);
	            } else {
	                result.setCode(Constants.ERROR_CODE);
	                result.setResultMessage(Constants.SAVE_FAILURE);
	            }
	        } catch (Exception e) {
	            result.setCode(Constants.ERROR_CODE);
	            result.setResultMessage(e.toString());
	            Log.error(log, "\n 方法[{}]，异常：[{}]", "BaseServiceImpl-add", e.getMessage());
	            throw new Exception(e.getMessage());
	        }
	        Log.debug(log, "\n 方法[{}]，出参：[{}]", "BaseServiceImpl-add", JSONObject.toJSONString(result));
	        return result;
	    }
	    @Override
	    @SuppressWarnings(value = { "unchecked" })
	    public ExecuteResult<VO> update(VO vo) throws Exception{
	    	Log.debug(log, "\n 方法[{}]，入参：[{}]", "BaseServiceImpl-update", JSONObject.toJSONString(vo));
	        ExecuteResult<VO> result=new ExecuteResult<VO>();
	        try {
	            DOMAIN dom = (DOMAIN) BeanUtil.copyPropertes(domainClass, vo);
	            Integer updateResult=baseDAO.update(dom);
	            if (updateResult >= 1) {
	                result.setCode(Constants.SUCCESS_CODE);
	                result.setResultMessage(Constants.UPDATE_SUCCESS);
	            } else {
	                result.setCode(Constants.ERROR_CODE);
	                result.setResultMessage(Constants.UPDATE_FAILURE);
	            }
	            result.setResult(vo);

	        } catch (Exception e) {
	            result.setCode(Constants.ERROR_CODE);
	            result.setResultMessage(e.toString());
	            Log.error(log, "\n 方法[{}]，异常：[{}]", "BaseServiceImpl-update", e.getMessage());
	            throw new Exception(e.getMessage());
	        }
	        Log.debug(log, "\n 方法[{}]，出参：[{}]", "BaseServiceImpl-update", JSONObject.toJSONString(result));
	        return result;
	    }
	    @Override
	    @SuppressWarnings(value = { "unchecked" })
	    public ExecuteResult<Integer> delete(Long id){
	    	Log.debug(log, "\n 方法[{}]，入参：[{}]", "BaseServiceImpl-delete", id);
	        ExecuteResult<Integer> result=new ExecuteResult<Integer>();
	        try {
	            Integer deleteResult=baseDAO.delete(id);
	            if (deleteResult >= 1) {
	                result.setResult(deleteResult);
	                result.setCode(Constants.SUCCESS_CODE);
	                result.setResultMessage(Constants.DELETE_SUCCESS);
	            } else {
	                result.setCode(Constants.ERROR_CODE);
	                result.setResultMessage(Constants.DELETE_FAILURE);
	            }
	        } catch (Exception e) {
	            result.setCode(Constants.ERROR_CODE);
	            result.setResultMessage(e.toString());
	            Log.error(log, "\n 方法[{}]，异常：[{}]", "BaseServiceImpl-delete", e.getMessage());
	        }
	        Log.debug(log, "\n 方法[{}]，出参：[{}]", "BaseServiceImpl-delete", JSONObject.toJSONString(result));
	        return result;
	    }

	    @Override
	    @SuppressWarnings(value = { "unchecked" })
	    public ExecuteResult<VO> queryById(Long id){
	    	Log.debug(log, "\n 方法[{}]，入参：[{}]", "BaseServiceImpl-queryById", id);
	        ExecuteResult<VO> result = new ExecuteResult<VO>();
	        try {
	            DOMAIN dom = baseDAO.queryById(id);
	            if (dom != null) {
	                VO rtnDto=(VO)BeanUtil.copyPropertes(voClass,dom);
	                result.setResult(rtnDto);
	                result.setCode(Constants.SUCCESS_CODE);
	                result.setResultMessage(Constants.QUERY_SUCCESS);
	            } else {
	                result.setCode(Constants.EMPTY_CODE);
	                result.setResultMessage(Constants.QUERY_SUCCESS);
	            }

	        } catch (Exception e) {
	            result.setCode(Constants.ERROR_CODE);
	            result.setResultMessage(e.toString());
	            Log.error(log, "\n 方法[{}]，异常：[{}]", "BaseServiceImpl-queryById", e.getMessage());
	        }
	        Log.debug(log, "\n 方法[{}]，出参：[{}]", "BaseServiceImpl-queryById", JSONObject.toJSONString(result));
	        return result;
	    }
	    @Override
	    public ExecuteResult<List<VO>> queryList(VO vo){
	    	Log.debug(log, "\n 方法[{}]，入参：[{}]", "BaseServiceImpl-queryList", JSONObject.toJSONString(vo));
	        ExecuteResult<List<VO>> result = new ExecuteResult<List<VO>>();
	        try {
	            DOMAIN dom = (DOMAIN)BeanUtil.copyPropertes(domainClass, vo);
	            List<DOMAIN> queryResult= baseDAO.queryList(dom);
	            if(queryResult.size() > 0){
	                List<VO> rtnList = (List<VO>)BeanUtil.copyList(voClass,queryResult);
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
	            Log.error(log, "\n 方法[{}]，异常：[{}]", "BaseServiceImpl-queryList", e.getMessage());
	        }
	        Log.debug(log, "\n 方法[{}]，出参：[{}]", "BaseServiceImpl-queryList", JSONObject.toJSONString(result));
	        return result;
	    }
	    @Override
	    public ExecuteResult<PageInfo<VO>> queryListByPage(VO vo, Page<VO> page){
	    	Log.debug(log, "\n 方法[{}]，入参：[{}]", "BaseServiceImpl-queryListByPage", JSONObject.toJSONString(vo));
	        ExecuteResult<PageInfo<VO>> result = new ExecuteResult<PageInfo<VO>>();
	        try {
	            DOMAIN dom = (DOMAIN)BeanUtil.copyPropertes(domainClass, vo);
	            PageHelper.startPage(page.getPageNum(), page.getPageSize(), true);
	            List<DOMAIN> queryResult= baseDAO.queryList(dom);
	            if(queryResult.size() > 0){
	                PageInfo pageInfo = new PageInfo<>(queryResult);
	                List<VO> rtnList = (List<VO>)BeanUtil.copyList(voClass,queryResult);
	                pageInfo.setList(rtnList);
	                result.setResult(pageInfo);
	                result.setCode(Constants.SUCCESS_CODE);
	                result.setResultMessage(Constants.QUERY_SUCCESS);
	            }else{
	                result.setCode(Constants.EMPTY_CODE);
	                result.setResultMessage(Constants.QUERY_SUCCESS);
	                return result;
	            }
	        } catch (Exception e) {
	            result.setCode(Constants.ERROR_CODE);
	            result.setResultMessage(e.toString());
	            Log.error(log, "\n 方法[{}]，异常：[{}]", "BaseServiceImpl-queryListByPage", e.getMessage());
	        }
	        Log.debug(log, "\n 方法[{}]，出参：[{}]", "BaseServiceImpl-queryListByPage", JSONObject.toJSONString(result));
	        return result;
	    }

}

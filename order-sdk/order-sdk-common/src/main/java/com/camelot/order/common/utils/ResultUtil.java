package com.camelot.order.common.utils;

import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.utils.AjaxInfoConstants;
import com.github.pagehelper.PageInfo;
import com.camelot.order.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 根据返回结果返回对应的AjaxInfo
 */

public class ResultUtil {

    private static Logger logger = LoggerFactory.getLogger(ResultUtil.class);


    //查询-查询集合
    public static <VO> AjaxInfo getSelectListAjaxInfo(ExecuteResult<List<VO>> result) {
        AjaxInfo info = new AjaxInfo();

        if(checkResultStatus(result)){
            if(CheckUtil.checkResultListIsNotNull(result)){
            	info = getSuccessAjaxInfo(result.getResultMessage());
                info.setData(result.getResult());
            }else {
                info = getSelectAjaxInfo();
            }
        }else{
            info=getFaileAjaxInfo(result.getResultMessage());
        }
        return info;
    }
    
    //查询-查询分页集合
    public static <VO> AjaxInfo getSelectPageListAjaxInfo(ExecuteResult<PageInfo<VO>> result) {
         AjaxInfo info = new AjaxInfo();

        if(checkResultStatus(result)){
             if(CheckUtil.checkPageResultListIsNotNull(result)){
                info=getSuccessAjaxInfo(result.getResultMessage());
                info.setData(result.getResult());
            }else {
                info = getSelectAjaxInfo();
            }
        }else{
            info=getFaileAjaxInfo(result.getResultMessage());
        }
        return info;
    }

    //查询-查询对象
    public static <VO> AjaxInfo getSelectAjaxInfo(ExecuteResult<VO> result) {
        AjaxInfo info = new AjaxInfo();

        if(checkResultStatus(result)){
            if(CheckUtil.checkResultIsNotNull(result)){
                info=getSuccessAjaxInfo(result.getResultMessage());
                info.setData(result.getResult());
            }else {
                info = getSelectAjaxInfo();
            }
        }else {
            info=getFaileAjaxInfo(result.getResultMessage());
        }
        return info;
    }

    //查询-单个对象
    public static <VO> AjaxInfo getSelectAjaxInfo(VO vo) {
        AjaxInfo info = new AjaxInfo();
        if (vo != null) {
            info.setMsg(AjaxInfoConstants.QUERY_SUCCESS);
            info.setData(vo);
        } else {
            info = getSelectAjaxInfo();
        }
        return info;
    }

    //查询-空
    public static AjaxInfo getSelectAjaxInfo() {
        AjaxInfo info = new AjaxInfo();
        info.setCode(AjaxInfoConstants.SUCCESS_CODE);
        info.setMsg(AjaxInfoConstants.QUERY_EMPTY);
        return info;
    }

    //成功
    public static AjaxInfo getSuccessAjaxInfo(String msg) {
        AjaxInfo info = new AjaxInfo();
        if(msg==null){
            info.setMsg(AjaxInfoConstants.SUCCESS);
        }else {
            info.setMsg(msg);
        }
        return info;
    }

    //失败
    public static AjaxInfo getFaileAjaxInfo(String msg) {
        AjaxInfo info = new AjaxInfo();
        info.setCode(AjaxInfoConstants.ERROR_CODE);
        if(msg==null){
            info.setMsg(AjaxInfoConstants.FAILURE);
        }else {
            info.setMsg(msg);
        }
        return info;
    }

    //查看返回数据的状态
    private static boolean checkResultStatus(ExecuteResult result){
       return Constants.SUCCESS_CODE==result.getCode() || Constants.EMPTY_CODE==result.getCode();
    }
}

package com.camelot.order.common.utils;

import com.camelot.common.bean.ExecuteResult;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * CheckUtil 判断结果是否为空
 */

public class CheckUtil {

    private static Logger logger = LoggerFactory.getLogger(CheckUtil.class);

    //校验是否为空-查询对象
    public static <VO> boolean checkResultIsNotNull(ExecuteResult<VO> result) {

        if (result != null && result.getResult() != null) {
            return true;
        }
        return false;
    }


    //校验是否为空-查询集合
    public static <VO> boolean checkResultListIsNotNull(ExecuteResult<List<VO>> result) {

        if (result != null && result.getResult() != null) {
            List<VO> list = result.getResult();
            if (!CollectionUtils.isEmpty(list)) {
                return true;
            }
        }
        return false;
    }
    
  //校验是否为空-查询分页集合
    
    public static <VO> boolean checkPageResultListIsNotNull(ExecuteResult<PageInfo<VO>> result) {

        if (result != null && result.getResult() != null) {
            PageInfo<VO> pageInfo = result.getResult();
            if (!CollectionUtils.isEmpty(pageInfo.getList())) {
                return true;
            }
        }
        return false;
    }


}

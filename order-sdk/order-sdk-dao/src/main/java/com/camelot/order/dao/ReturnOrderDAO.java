package com.camelot.order.dao;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.ReturnOrderDomain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReturnOrderDAO extends BaseDAO<ReturnOrderDomain, Long> {
	/**
     *@Description 根据时间查询当天最大的退货订单编号
     *@author xupengfei
     *@Data 2019年4月9日 
     * @param nowDate
     * @return
     */
    String getMaxReturnOrderNumber(String nowDate);
    
    List<ReturnOrderDomain> queryListByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}

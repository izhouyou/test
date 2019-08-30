package com.camelot.order.dao;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.NewSalesOrderLogDomain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewSalesOrderLogDAO extends BaseDAO<NewSalesOrderLogDomain,Long> {

}

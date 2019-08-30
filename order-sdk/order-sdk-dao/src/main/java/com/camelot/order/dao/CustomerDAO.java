package com.camelot.order.dao;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.CustomerDomain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerDAO extends BaseDAO<CustomerDomain,Long> {

}

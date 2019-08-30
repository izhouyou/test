package com.camelot.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.TransregionalInfoDomain;

@Mapper
public interface TransregionalInfoDAO extends BaseDAO<TransregionalInfoDomain,Long>  {

}

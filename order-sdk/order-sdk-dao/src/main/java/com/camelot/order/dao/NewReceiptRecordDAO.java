package com.camelot.order.dao;


import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.NewReceiptRecordDomain;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hudya
 */
@Mapper
public interface NewReceiptRecordDAO extends BaseDAO<NewReceiptRecordDomain, Long> {

    /**
     * 最大的退货订单
     * @param nowDate
     * @return
     */
    String getMaxReceiptRecordNumber(String nowDate);


}

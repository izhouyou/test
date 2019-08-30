package com.camelot.order.dao;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.ReceiptRecordDomain;

public interface ReceiptRecordDAO extends BaseDAO<ReceiptRecordDomain, Long> {
	/**
     *@Description 根据时间查询当天最大的收款单编号
     *@author xupengfei
     *@Data 2019年4月9日 
     * @param nowDate
     * @return
     */
    String getMaxReceiptRecordNumber(String nowDate);
}

package com.camelot.order.service;


import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewReceiptRecordVO;

/**
 * @author hudya
 */
public interface NewReceiptRecordService extends BaseService<NewReceiptRecordVO> {

    /**
     * 最大收款记录
     * @param nowDate
     * @return
     */
    String getMaxReceiptRecordNumber(String nowDate);
}

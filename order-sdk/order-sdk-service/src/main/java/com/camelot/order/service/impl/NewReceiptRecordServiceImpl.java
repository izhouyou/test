package com.camelot.order.service.impl;

import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.dao.NewReceiptRecordDAO;
import com.camelot.order.domain.NewReceiptRecordDomain;
import com.camelot.order.export.snapshot.NewReceiptRecordVO;
import com.camelot.order.service.NewReceiptRecordService;
import com.camelot.order.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author hudya
 */
@Service("newReceiptRecordService")
public class NewReceiptRecordServiceImpl extends BaseServiceImpl<NewReceiptRecordVO, NewReceiptRecordDomain> implements NewReceiptRecordService {

    private static Logger log = Log.get(NewReceiptRecordServiceImpl.class);

    @Autowired
    private NewReceiptRecordDAO newReceiptRecordDAO;

    @Override
    public String getMaxReceiptRecordNumber(String nowDate) {

        Log.debug(log, "\n 方法[{}]，入参：[{}]", "NewReceiptRecordServiceImpl-getMaxReceiptRecordNumber", nowDate);
        String result = "";
        try {
            result = newReceiptRecordDAO.getMaxReceiptRecordNumber(nowDate);
        } catch (Exception e) {
            Log.error(log, "\n 方法[{}]，异常：[{}]", "NewReceiptRecordServiceImpl-getMaxReceiptRecordNumber", e.getMessage());
            return Constants.ERROR_CODE;
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewReceiptRecordServiceImpl-getMaxReceiptRecordNumber", result);
        return result;
    }
}
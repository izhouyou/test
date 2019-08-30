package com.camelot.order.service.impl;

import org.springframework.stereotype.Service;

import com.camelot.order.domain.ReceiptRecordDomain;
import com.camelot.order.export.vo.ReceiptRecordVO;
import com.camelot.order.service.ReceiptRecordService;
import com.camelot.order.service.base.BaseServiceImpl;
@Service("receiptRecordService")
public class ReceiptRecordServiceImpl extends BaseServiceImpl<ReceiptRecordVO, ReceiptRecordDomain> implements ReceiptRecordService{

}

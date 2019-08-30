package com.camelot.order.export.service;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewReceiptRecordVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author hudya
 */
public interface NewReceiptRecordExportService extends BaseService<NewReceiptRecordVO> {

    /**
     * 收款单分页查询
     *
     * @param vo
     * @param page
     * @return
     */
    ExecuteResult<PageInfo<NewReceiptRecordVO>> queryReceiptRecordList(NewReceiptRecordVO vo, Page<NewReceiptRecordVO> page);


    /**
     * 新增收款单
     *
     * @param vo
     * @return
     */
    ExecuteResult<NewReceiptRecordVO> addReceiptRecord(NewReceiptRecordVO vo);

    /**
     * 收款单集合查询
     *
     * @param vo
     * @return
     */
    ExecuteResult<List<NewReceiptRecordVO>> queryReceiptList(NewReceiptRecordVO vo);

    /**
     * 最大的收款记录
     *
     * @param nowDate
     * @return
     */
    String getMaxReceiptRecordNumber(String nowDate);
}

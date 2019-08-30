package com.camelot.order.export.service;


import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewSalesOrderLogVO;
import com.github.pagehelper.PageInfo;

/**
 * @author hudya
 */
public interface NewSalesOrderLogExportService extends BaseService<NewSalesOrderLogVO> {

    /**
     * 查询订单日志
     *
     * @param vo
     * @param page
     * @return
     */
    ExecuteResult<PageInfo<NewSalesOrderLogVO>> querySalesOrderLogPage(NewSalesOrderLogVO vo, Page<NewSalesOrderLogVO> page);

    /**
     * 添加订单日志
     *
     * @param vo
     * @return
     */
    ExecuteResult<NewSalesOrderLogVO> addSalesOrderLog(NewSalesOrderLogVO vo);

}

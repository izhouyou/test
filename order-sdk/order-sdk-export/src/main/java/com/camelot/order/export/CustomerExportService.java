package com.camelot.order.export;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.CustomerVO;
import com.camelot.order.export.vo.excelvo.CustomerExcelVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CustomerExportService extends BaseService<CustomerVO> {

    /**
     * 消费者查询
     * @param vo
     * @param page
     * @return
     */
    ExecuteResult<PageInfo<CustomerVO>> queryCustomerPage(CustomerVO vo, Page<CustomerVO> page);

    /**
     * 消费者信息导出
     * @param customerList
     * @return
     */
    ExecuteResult<List<CustomerExcelVO>> exportList(List<CustomerVO> customerList);


    /**
     * 添加消费者信息
     * @param vo
     * @return
     */
    ExecuteResult<CustomerVO> addCustomer(CustomerVO vo);


}

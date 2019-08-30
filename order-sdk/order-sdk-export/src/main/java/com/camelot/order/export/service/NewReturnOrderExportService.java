package com.camelot.order.export.service;


import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewReturnOrderVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisticsSalesVO;
import com.camelot.order.export.vo.StatisticsSourceVO;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hudya
 */
public interface NewReturnOrderExportService extends BaseService<NewReturnOrderVO> {


    /**
     * 退货订单分页查询
     *
     * @param vo
     * @param page
     * @return
     */
    ExecuteResult<PageInfo<NewReturnOrderVO>> queryReturnOrderListPage(NewReturnOrderVO vo, Page page);

    /**
     * 退货订单查看详情
     *
     * @param id
     * @return
     */
    ExecuteResult<NewReturnOrderVO> searchReturnOrderDetail(Long id);

    /**
     * <p>Title: queryReturnAmount</p>
     * <p>Description: 查询退货金额</p>
     *
     * @param newReturnOrderParamVO
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<BigDecimal> queryReturnAmount(NewReturnOrderVO newReturnOrderParamVO);

    /**
     * 订单退货
     *
     * @param vo
     * @return
     */
    ExecuteResult<String> submitReturnOrder(NewReturnOrderVO vo);

    /**
     * 最大的退货订单
     * @param nowDate
     * @return
     */
    String getMaxReturnOrderNumber(String nowDate);

    /**
     * <p>Title: queryStatsSalesOrderPage</p>
     * <p>Description: 查询退货统计</p>
     *
     * @param newSalesOrderParamVO
     * @return
     * @author cuijiudong
     * @date 2019年6月17 日
     */
    ExecuteResult<List<StatisticsSalesVO>>  queryStatsReturnOrderPage(NewReturnOrderVO newSalesOrderParamVO);


    /**
     * <p>Title: queryStatsSalesByType</p>
     * <p>Description: 查询退货统计</p>
     *
     * @param newSalesOrderParamVO
     * @return
     * @author cuijiudong
     * @date 2019年6月17 日
     */
    ExecuteResult<List<StatisticsSourceVO>> queryStatsReturnByReason(NewReturnOrderVO newSalesOrderParamVO);

	/**
	 * <p>Title: queryCountAmount</p>
	 * <p>Description: 查询退货订单统计信息</p>
	 * @param newReturnOrderParamVO
	 * @return
	 * @author zhouyou
	 * @date 2019年6月27日
	 */
	ExecuteResult<SalesVolumeVO> queryCountAmount(NewReturnOrderVO vo);


}
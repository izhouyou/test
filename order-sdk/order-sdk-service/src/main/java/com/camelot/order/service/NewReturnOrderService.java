package com.camelot.order.service;


import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.domain.NewReturnOrderDomain;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewReturnOrderVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisticsSalesVO;
import com.camelot.order.export.vo.StatisticsSourceVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author hudya
 */
public interface NewReturnOrderService extends BaseService<NewReturnOrderVO> {

    /**
     * <p>Title: queryReturnAmount</p>
     * <p>Description: 查询退货金额</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<BigDecimal> queryReturnAmount(NewReturnOrderVO vo);

    /**
     * 最大退货订单
     *
     * @param nowDate
     * @return
     */
    String getMaxReturnOrderNumber(String nowDate);

    /**
     * <p>Title: queryStatsReturnOrderPage</p>
     * <p>Description: 查询退货统计</p>
     *
     * @param newSalesOrderParamVO
     * @return
     * @author cuijiudong
     * @date 2019年6月17日
     */
    ExecuteResult<List<StatisticsSalesVO>> queryStatsReturnOrderPage(NewReturnOrderVO newSalesOrderParamVO);


    /**
     * <p>Title: queryStatsReturnByReason</p>
     * <p>Description: 查询退货统计：退货原因统计</p>
     *
     * @param vo
     * @return
     * @author cuijiudong
     * @date 2019年6月17日queryStatsSaleByReason
     */
        ExecuteResult<List<StatisticsSourceVO>> queryStatsReturnByReason(NewReturnOrderVO vo);

	/**
	 * <p>Title: queryCountAmount</p>
	 * <p>Description: 查询退货订单统计信息</p>
	 * @param vo
	 * @return
	 * @author zhouyou
	 * @date 2019年6月27日
	 */
	ExecuteResult<SalesVolumeVO> queryCountAmount(NewReturnOrderVO vo);


}

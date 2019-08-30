package com.camelot.order.service;

import java.math.BigDecimal;
import java.util.Map;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.vo.QueryParamVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisticsReturnOrderVO;

public interface StatisticsReturnOrderService extends BaseService<StatisticsReturnOrderVO> {
	/**
	 * <p>Description: 退货额</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<Map<String, BigDecimal>> queryStatisticsResult(QueryParamVO paramVO);
}

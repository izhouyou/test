package com.camelot.order.service;

import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.vo.ReturnOrderVO;

public interface ReturnOrderService extends BaseService<ReturnOrderVO> {
	/**
	 * <p>Description: 根据创建时间,查询退单数据</p>
	 * @param createDate 创建时间
	 * @return
	 */
	ExecuteResult<List<ReturnOrderVO>> queryListByDate(String startDate, String endDate);

}

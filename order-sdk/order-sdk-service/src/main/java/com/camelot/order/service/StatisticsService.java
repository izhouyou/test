package com.camelot.order.service;

import java.util.List;
import java.util.Map;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.vo.QueryParamVO;
import com.camelot.order.export.vo.SalesOrderVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisticsSourceVO;

/**
 * <p>Description: [订单-统计]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName StatisticsOrderService.java
 * Created on 2019年3月26日.
 */
public interface StatisticsService {

	/**
	 * <p>Description: 查询未购买信息</p>
	 * @param paramVO 门店id/起止时间
	 * @return
	 */
	List<StatisticsSourceVO> queryNotBuySource(QueryParamVO paramVO);

	/**
	 * <p>Description: 查询订单数据</p>
	 * @param paramVO 门店id/起止时间/订单状态
	 * @return
	 */
	List<StatisticsSourceVO> queryOrderSource(QueryParamVO paramVO);

	/**
	 * <p>Description: 根据门店id/起止时间/订单状态,查询已取消订单数量</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<List<Map<String, Object>>> queryCancelCause(QueryParamVO paramVO);

	/**
	 * <p>Description: 查询已取消订单数量</p>
	 * @param paramVO 门店id/起止时间
	 * @return
	 */
	ExecuteResult<List<Map<String, Object>>> queryNotBuyCause(QueryParamVO paramVO);

	/**
	 * <p>Title: toDayAmount</p>
	 * <p>Description: 今日销售额和订单数</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<SalesVolumeVO> toDayAmount(QueryParamVO paramVO);

}

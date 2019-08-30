package com.camelot.order.service;

import java.util.List;
import java.util.Map;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.vo.QueryParamVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisitcsGoodsSalesVO;
import com.camelot.order.export.vo.StatisticsGoodsVO;
import com.camelot.order.export.vo.StatisticsNotBuyVO;
import com.camelot.order.export.vo.StatisticsTotalVO;
import com.github.pagehelper.PageInfo;

public interface StatisticsTotalService extends BaseService<StatisticsTotalVO> {
	/**
	 * <p>Description: 统计数据总数</p>
	 * @param vo 入参
	 * @return
	 */
	ExecuteResult<Long> queryCount(StatisticsTotalVO vo);
	
	/**
	 * <p>Description: 查询销量统计结果</p>
	 * @param vo
	 * @return
	 */
	ExecuteResult<SalesVolumeVO> queryStatisticsResult(QueryParamVO vo);

	/**
	 * <p>Description: 销售订单商品销量统计列表</p>
	 * @param paramVO 门店ids/开始时间/结束时间
	 * @return
	 */
	ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> queryGoodsVolume(QueryParamVO paramVO);

	/**
	 * <p>Description: 查询订单统计(图)</p>
	 * @param paramVO 门店ids/开始时间/结束时间
	 * @return
	 */
	ExecuteResult<List<Map<String, Object>>> queryOrderStatistics(QueryParamVO paramVO);

	/**
	 * <p>Description: 查询未购买上报统计</p>
	 * @param paramVO  门店ids/开始时间/结束时间
	 * @return
	 */
	List<StatisticsNotBuyVO> queryNotBuy(QueryParamVO paramVO);
	/**
	 * <p>Description: 查询已门店订单数统计</p>
	 * @param paramVO  门店ids/开始时间/结束时间
	 * @return
	 */
	List<StatisticsNotBuyVO> queryOrderAmount(QueryParamVO paramVO);

	/**
	 * <p>Description: 查询有效订单整车分类统计列表</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<PageInfo<Map<String, Object>>> queryCarAssort(QueryParamVO paramVO);

	/**
	 * <p>Description: 查询今日每时订单/总额统计</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<List<Map<String, Object>>> queryToDayOrderStatistics(QueryParamVO paramVO);

	/**
	 * <p>Description: 查询List</p>
	 * @param paramVO 门店id/起止时间
	 * @return
	 */
	ExecuteResult<PageInfo<StatisticsGoodsVO>> queryPageGoodsList(QueryParamVO paramVO);

	/**
	 * <p>Description: 查询当前统计表，订单修改时间的最大值</p>
	 * @return
	 */
	ExecuteResult<String> queryMaxDate();
}

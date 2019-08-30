package com.camelot.order.export.service;

import java.util.List;
import java.util.Map;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.vo.ActivityDataVO;
import com.camelot.order.export.vo.NotBuyVO;
import com.camelot.order.export.vo.QueryParamVO;
import com.camelot.order.export.vo.SalesReportVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisitcsGoodsSalesVO;
import com.camelot.order.export.vo.StatisticsFigureVO;
import com.camelot.order.export.vo.StatisticsGoodsCategoryVO;
import com.camelot.order.export.vo.StatisticsGoodsVO;
import com.camelot.order.export.vo.StatisticsNotBuyVO;
import com.camelot.order.export.vo.StatisticsSourceVO;
import com.github.pagehelper.PageInfo;

/**
 * <p>Description: [订单-统计暴露接口]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName StatisticsExportService.java
 * Created on 2019年4月8日.
 */
public interface StatisticsExportService {
	
	
	
	/**
	 * <p>Description: 门店销售报表查询</p>
	 * @param paramVO 查询条件
	 * @return
	 */
	ExecuteResult<PageInfo<SalesReportVO>> querySalesReportList(QueryParamVO paramVO);
	/**
	 * <p>Description: 查询销量统计结果</p>
	 * @param paramVO 入参
	 * @return
	 */
	ExecuteResult<SalesVolumeVO> queryStatisticsResult(QueryParamVO paramVO);
	/**
	 * <p>Description: 销售订单商品销量统计列表</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> queryGoodsVolume(QueryParamVO paramVO);
	/**
	 * <p>Description: 查询订单统计(图)</p>
	 * @param paramVO 门店ids/开始时间/结束时间
	 * @return
	 */
	ExecuteResult<Map<String, Object>> queryOrderStatistics(QueryParamVO paramVO);
	/**
	 * <p>Description: 查询未购买上报统计</p>
	 * @param paramVO 门店ids/开始时间/结束时间/门店名称/门店编号
	 * @return
	 */
	ExecuteResult<PageInfo<StatisticsNotBuyVO>> queryNotBuy(QueryParamVO paramVO);
	/**
	 * <p>Description: 门店顾客来源分析</p>
	 * @param paramVO 门店id/开始时间/结束时间
	 * @return
	 */
	 ExecuteResult<List<StatisticsSourceVO>> queryCustomerSource(QueryParamVO paramVO);
	/**
	 * <p>Description: 未购买原因分析</p>
	 * @param paramVO 门店id/开始时间/结束时间
	 * @return
	 */
	ExecuteResult<List<StatisticsSourceVO>> queryNotBuyCause(QueryParamVO paramVO);
	/**
	 * <p>Description: 查询有效订单整车分类统计列表</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<PageInfo<StatisticsGoodsCategoryVO>> queryCarAssort(QueryParamVO paramVO);
	/**
	 * <p>Description: 根据门店ids/活动名称/活动编号/门店名称/门店编号,查询数据</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<PageInfo<ActivityDataVO>> queryPageByActivity(QueryParamVO paramVO);
	/**
	 * <p>Description: 查询今日订单统计(图)</p>
	 * @param paramVO 门店ids/开始时间/结束时间
	 * @return
	 */
	ExecuteResult<List<StatisticsFigureVO>> queryToDayOrderStatistics(QueryParamVO paramVO);
	/**
	 * <p>Description: 查询商品信息</p>
	 * @param paramVO 门店id/起止时间/排序
	 * @return
	 */
	ExecuteResult<PageInfo<StatisticsGoodsVO>> queryGoodsList(QueryParamVO paramVO);
	/**
	 * <p>Description: 门店销售报表导出</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<List<SalesReportVO>> exportSalesReport(QueryParamVO paramVO);
	/**
	 * <p>Description: 零售活动报表导出</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<List<ActivityDataVO>> queryByActivity(QueryParamVO paramVO);
	/**
	 * <p>Description: 未购买消费者统计</p>
	 * @param vo 
	 * @param page
	 * @return
	 */
	ExecuteResult<PageInfo<NotBuyVO>> queryNotBuyCustomer(NotBuyVO vo, Page page);
	/** 
	 * 未购买消费者统计导出
	 * @param vo
	 * @return
	 */
	ExecuteResult<List<NotBuyVO>> exportNotBuyCustomer(NotBuyVO vo);
	/**
	 * <p>Title: toDayAmount</p>
	 * <p>Description: 今日销售金额和订单数</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<SalesVolumeVO> toDayAmount(QueryParamVO paramVO);
}

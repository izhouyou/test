package com.camelot.order.export.service;

import java.util.List;
import java.util.Map;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.vo.*;
import com.camelot.order.export.vo.param.*;
import com.github.pagehelper.PageInfo;

/**
 * <p>Title: NewStatisticsExportService</p>
 * <p>Description: [订单-统计暴露接口]</p>
 * <p>Company: Camelot</p>
 * @author zhouyou
 * @date 2019年5月17日
 * @version 1.0
 */
public interface NewStatisticsExportService {
	
	/**
	 * <p>Title: statisticsPageSalesReport</p>
	 * <p>Description: 门店销售报表查询</p>
	 * @param paramVO 城市orgStr/门店storeStr/门店storeIds/起止时间/消费者id/订单状态/活动编号或名称
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<PageInfo<SalesReportVO>> statisticsPageSalesReport(SalesReportParamVO paramVO, Page page);
	
	/**
	 * <p>Title: statisticsListSalesReport</p>
	 * <p>Description: 门店销售报表导出</p>
	 * @param paramVO 城市orgStr/门店storeStr/门店storeIds/起止时间/消费者id/订单状态/活动编号或名称
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<List<NewSalesReportVO>> statisticsListSalesReport(SalesReportParamVO paramVO);
	
	/**
	 * <p>Title: statisticsPageActivityReport</p>
	 * <p>Description: 零售活动报表查询</p>
	 * @param paramVO 城市orgStr/门店storeStr/门店ids/活动名称/活动编号/门店名称/门店编号
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<PageInfo<ActivityDataVO>> statisticsPageActivityReport(ActivityReportPramVO paramVO, Page page);
	
	/**
	 * <p>Title: statisticsListActivityReport</p>
	 * <p>Description: 零售活动报表导出</p>
	 * @param paramVO 城市orgStr/门店storeStr/门店ids/活动名称/活动编号/门店名称/门店编号
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<List<ActivityDataVO>> statisticsListActivityReport(ActivityReportPramVO paramVO);
	
	/**
	 * <p>Title: statisticsOrderStatistics</p>
	 * <p>Description: 订单统计查询</p>
	 * @param paramVO 城市orgStr/门店storeStr/起止时间/用户id
	 * @return
	 * @author zhouyou
	 * @date 2019年5月17日
	 */
	ExecuteResult<SalesVolumeVO> statisticsOrderAmount(StatisticsAnalysisParamVO paramVO);
	
	/**
	 * <p>Title: statisticsToDayAmount</p>
	 * <p>Description: 门店端-今日销售金额和订单数查询</p>
	 * @param paramVO 门店storeStr/用户id
	 * @return
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
	ExecuteResult<SalesVolumeVO> statisticsToDayAmount(StatisticsAnalysisParamVO paramVO);
	
	/**
	 * <p>Title: statisticsPageGoodsVolume</p>
	 * <p>Description: 销售订单商品销量统计查询</p>
	 * @param paramVO 城市orgSTr/门店storeStr/起止时间/商品一级分类id
	 * @return
	 * @author zhouyou
	 * @date 2019年5月17日
	 */
	ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> statisticsPageGoodsVolume(StatisticsAnalysisParamVO paramVO, Page page);
	
	/**
	 * <p>Title: statisticsListGoodsAmount</p>
	 * <p>Description: 门店端-商品销量查询</p>
	 * @param paramVO 门店id/排序标记/用户id
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
	ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> statisticsPageGoodsAmount(StatisticsAnalysisParamVO paramVO, Page page);
	
	/**
	 * <p>Title: statisticsPageVehicleAmount</p>
	 * <p>Description: 有效订单整车分类统计查询</p>
	 * @param paramVO 城市orgStr/门店storeStr/门店id/起止时间/商品分类id/商品分类级别/颜色/消费者来源
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<PageInfo<StatisticsGoodsCategoryVO>> statisticsPageVehicleAmount(StatisticsAnalysisParamVO paramVO, Page page);
	
	/**
	 * <p>Title: statisticsListToDayOrderTrend</p>
	 * <p>Description: 今日订单/销售统计(图)查询</p>
	 * @param paramVO 城市orgStr/门店orgStr/开始时间/用户id
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<Map<String, Object>> statisticsListToDayOrderTrend(StatisticsAnalysisParamVO paramVO);
	
	/**
	 * <p>Title: statisticsOrderTrend</p>
	 * <p>Description: 近7天/近30天订单/销售统计(图)查询</p>
	 * @param paramVO 城市orgStr/门店orgStr/起止时间/用户id
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<Map<String, Object>> statisticsOrderTrend(StatisticsAnalysisParamVO paramVO);

	/**
	 * <p>Title: statisticsPageNotBuy</p>
	 * <p>Description: 未购买上报统计查询</p>
	 * @param paramVO 门店storeStr/开始时间/结束时间/门店名称/门店编号
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<PageInfo<StatisticsNotBuyVO>> statisticsPageNotBuy(NotBuyParamVO paramVO, Page page);
	
	/**
	 * <p>Title: statisticsListCustomerSource</p>
	 * <p>Description: 未购买原因分析(图)查询</p>
	 * @param paramVO 门店id/开始时间/结束时间
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<List<StatisticsSourceVO>> statisticsListCustomerSource(BaseParamVO paramVO);
	
	/**
	 * <p>Title: statisticsListNotBuyCause</p>
	 * <p>Description: 未购买原因分析(图)查询</p>
	 * @param paramVO 门店id/开始时间/结束时间
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<List<StatisticsSourceVO>> statisticsListNotBuyCause(BaseParamVO paramVO);
	
	/**
	 * <p>Title: statisticsPageNotBuyCustomer</p>
	 * <p>Description: 未购买消费者统计</p>
	 * @param paramVO 门店id/起止时间
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<PageInfo<NotBuyCustomerVO>> statisticsPageNotBuyCustomer(BaseParamVO paramVO, Page page);
	
	/**
	 * <p>Title: statisticsListNotBuyCustomer</p>
	 * <p>Description: 未购买消费者统计导出</p>
	 * @param paramVO 门店id/起止时间
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<List<NotBuyCustomerVO>> statisticsListNotBuyCustomer(BaseParamVO paramVO);

	/**
	 * <p>Title: statsSalesOrderPage</p>
	 * <p>Description: 销售统计</p>
	 * @param paramVO 门店storeStr/开始时间/结束时间/门店名称/门店编号
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年7月11日
	 */
	ExecuteResult<PageInfo<StatisticsSalesVO>> statsSalesOrderPage(StatsOrderParamVO paramVO, Page page);

	/**
	 * <p>Title: statsSalesByType</p>
	 * <p>Description: 销售统计:来源分析</p>
	 * @param paramVO 门店id/开始时间/结束时间
	 * @param Type
	 * @return
	 * @author jiudong
	 * @date 2019年7月11日
	 */
	ExecuteResult<List<StatisticsSourceVO>> statsSalesByType(BaseParamVO paramVO,String Type);

	/**
	 * <p>Title: statsReturnOrderPage</p>
	 * <p>Description: 退货统计</p>
	 * @param paramVO 门店storeStr/开始时间/结束时间/门店名称/门店编号
	 * @param page
	 * @return
	 * @author jiudong
	 * @date 2019年7月11日
	 */
	ExecuteResult<PageInfo<StatisticsSalesVO>>  statsReturnOrderPage(StatsOrderParamVO paramVO, Page page);

	/**
	 * <p>Title: statsReturnByReason</p>
	 * <p>Description: 退货统计:退货原因</p>
	 * @param paramVO 门店id/开始时间/结束时间
	 * @param Type
	 * @return
	 * @author jiudong
	 * @date 2019年7月11日
	 */
	ExecuteResult<List<StatisticsSourceVO>> statsReturnByReason(BaseParamVO paramVO,String Type);
}

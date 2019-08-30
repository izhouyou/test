package com.camelot.order.api;

import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.CheckUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.ResultUtil;
import com.camelot.order.common.utils.excel.ExportExcel;
import com.camelot.order.config.checkauthorise.UserAuthoriseController;
import com.camelot.order.export.service.NewStatisticsExportService;
import com.camelot.order.export.vo.*;
import com.camelot.order.export.vo.excelvo.ActivityDataExportVO;
import com.camelot.order.export.vo.excelvo.NotBuyExportVO;
import com.camelot.order.export.vo.excelvo.SalesReportExportVO;
import com.camelot.order.export.vo.param.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("statistics")
@Api(value="订单-统计", tags="订单-统计", description="订单-统计")
public class NewApiStatisticsController {

	@Autowired
	private NewStatisticsExportService newStatisticsExportService;

    private static Logger log = Log.get(NewApiStatisticsController.class);

    /**
     * @Description [ 对前端传值的String类型转换为Date类型 ]
     * @Author [hudyang]
     * @Date 2019/5/16 14:39
     * @Param
     * @return
     **/
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * <p>Title: queryStoreSales</p>
     * <p>Description: 门店销售报表查询接口</p>
     * @param paramVO 城市orgStr/门店storeStr/门店ids/起止时间/活动编号或名称/订单状态/消费者id
     * @param page
     * @return
     * @author zhouyou
     * @date 2019年5月18日
     */
    @UserAuthoriseController
    @RequestMapping(value = "storeSales", method = RequestMethod.GET)
	@ApiOperation(value="门店销售报表查询接口")
	public AjaxInfo queryStoreSales(@ModelAttribute SalesReportParamVO paramVO, @ModelAttribute Page page) {
		ExecuteResult<PageInfo<SalesReportVO>> querySalesReportList = newStatisticsExportService.statisticsPageSalesReport(paramVO, page);
		AjaxInfo result = ResultUtil.getSelectPageListAjaxInfo(querySalesReportList);
		return result;
	}

	/**
	 * <p>Title: exportSalesReport</p>
	 * <p>Description: 门店销售报表导出接口</p>
	 * @param response
	 * @param paramVO 城市orgStr/门店storeStr/门店ids/起止时间/活动编号或名称/订单状态/消费者id
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
    @UserAuthoriseController
	@RequestMapping(value = "salesReportExcel", method = RequestMethod.GET)
	@ApiOperation(value="门店销售报表导出接口")
	public void exportSalesReport(HttpServletResponse response, @ModelAttribute SalesReportParamVO paramVO) {
		ExecuteResult<List<NewSalesReportVO>> queryResult = newStatisticsExportService.statisticsListSalesReport(paramVO);
		if (CheckUtil.checkResultListIsNotNull(queryResult)) {
			String fileName = "门店销售报表导出";
			try {
	    		//writeFile:将导出数据输出到指定的文件
	    		//write：将数据输出到客户端（需要HttpServletResponse对象）
				new ExportExcel(fileName,SalesReportExportVO.class, 1).setDataList(queryResult.getResult()).write(response, fileName +".xlsx").dispose();
	    	} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>Title: queryByActivityId</p>
	 * <p>Description: 零售活动报表查询接口</p>
	 * @param paramVO 城市orgStr/门店storeStr/活动名称/活动编号/门店名称/门店编号
	 * @param page 分页
	 * @return
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
    @UserAuthoriseController
	@RequestMapping(value = "activity",method = RequestMethod.GET)
	@ApiOperation(value="零售活动报表查询接口")
	public AjaxInfo queryByActivityId(@ModelAttribute ActivityReportPramVO paramVO, @ModelAttribute Page page){
	   ExecuteResult<PageInfo<ActivityDataVO>> result = newStatisticsExportService.statisticsPageActivityReport(paramVO, page);
	   AjaxInfo info=ResultUtil.getSelectPageListAjaxInfo(result);
	   return info;
	}

	/**
	 * <p>Title: exportRetailActivity</p>
	 * <p>Description: 零售活动报表导出接口</p>
	 * @param response
	 * @param paramVO 城市orgStr/门店storeStr/活动名称/活动编号/门店名称/门店编号
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
    @UserAuthoriseController
	@RequestMapping(value = "retailActivityExcel",method = RequestMethod.GET)
	@ApiOperation(value="零售活动报表导出接口")
	public void exportRetailActivity(HttpServletResponse response, @ModelAttribute ActivityReportPramVO paramVO){
	   ExecuteResult<List<ActivityDataVO>> queryResult = newStatisticsExportService.statisticsListActivityReport(paramVO);
	   if (Constants.SUCCESS_CODE.equals(queryResult.getCode())) {
			String fileName = "零售活动报表导出";
			try {
	    		//writeFile:将导出数据输出到指定的文件
	    		//write：将数据输出到客户端（需要HttpServletResponse对象）
				new ExportExcel(fileName,ActivityDataExportVO.class, 1).setDataList(queryResult.getResult()).write(response, fileName +".xlsx").dispose();
	    	} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>Title: querySalesOrder</p>
	 * <p>Description: 门店销售统计-订单统计查询接口</p>
	 * @param paramVO 城市orgStr/门店storeStr/起止时间/用户id
	 * @return
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
	@RequestMapping(value = "salesOrder", method = RequestMethod.GET)
	@ApiOperation(value="门店销售统计-订单统计查询接口")
	public AjaxInfo querySalesOrder(@ModelAttribute StatisticsAnalysisParamVO paramVO) {
		ExecuteResult<SalesVolumeVO> executeResult = newStatisticsExportService.statisticsOrderAmount(paramVO);
		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
		return result;
	}

	/**
	 * <p>Title: toDayAmount</p>
	 * <p>Description: 门店端-今日销售金额和订单数查询接口</p>
	 * @param paramVO 门店storeStr/用户id
	 * @return
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
	@RequestMapping(value="toDayAmount", method=RequestMethod.GET)
	@ApiOperation(value="门店端-今日销售金额和订单数查询接口")
	public AjaxInfo toDayAmount(@ModelAttribute StatisticsAnalysisParamVO paramVO) {
		ExecuteResult<SalesVolumeVO> executeResult = newStatisticsExportService.statisticsToDayAmount(paramVO);
		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
		return result;
	}

	/**
	 * <p>Title: queryGoodsVolume</p>
	 * <p>Description: 门店销售统计-销售订单商品销量统计查询接口</p>
	 * @param paramVO 城市orgSTr/门店storeStr/起止时间/商品一级分类id
	 * @param page 分页
	 * @return
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
    @UserAuthoriseController
	@RequestMapping(value = "goodsVolume", method = RequestMethod.GET)
	@ApiOperation(value="门店销售统计-销售订单商品销量统计查询接口")
	public AjaxInfo queryGoodsVolume(@ModelAttribute StatisticsAnalysisParamVO paramVO, @ModelAttribute Page page) {
		ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> executeResult = newStatisticsExportService.statisticsPageGoodsVolume(paramVO, page);
		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
		return result;
	}

	/**
	 * <p>Title: queryGoodsList</p>
	 * <p>Description: 门店端-商品销量查询接口</p>
	 * @param paramVO 门店storeStr/起止时间/排序标记/用户id
	 * @param page 分页
	 * @return
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
	@RequestMapping(value = "goods", method = RequestMethod.GET)
	@ApiOperation(value="门店端-商品销量查询接口")
	public AjaxInfo queryGoodsList(@ModelAttribute StatisticsAnalysisParamVO paramVO, @ModelAttribute Page page) {
		ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> executeResult = newStatisticsExportService.statisticsPageGoodsAmount(paramVO, page);
		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
		return result;
	}
	/**
	 * <p>Title: queryCarAssort</p>
	 * <p>Description: </p>
	 * @param paramVO 城市orgStr/门店storeStr/门店id/起止时间/商品分类id/商品分类级别/颜色/消费者来源
	 * @param page 分页
	 * @return
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
    @UserAuthoriseController
	@RequestMapping(value = "carAssort", method = RequestMethod.GET)
	@ApiOperation(value="门店销售统计-有效订单整车分类统计查询接口")
	public AjaxInfo queryCarAssort(@ModelAttribute StatisticsAnalysisParamVO paramVO, @ModelAttribute Page page) {
		ExecuteResult<PageInfo<StatisticsGoodsCategoryVO>> executeResult = newStatisticsExportService.statisticsPageVehicleAmount(paramVO, page);
		AjaxInfo result = ResultUtil.getSelectPageListAjaxInfo(executeResult);
		return result;
	}

	/**
	 * <p>Title: queryToDayOrderStatistics</p>
	 * <p>Description: 门店销售统计-今日订单/销售统计(图)查询接口</p>
	 * @param paramVO 城市orgStr/门店storeStr/门店storeStr/开始时间/用户id
	 * @return
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
	@RequestMapping(value = "toDayOrder", method = RequestMethod.GET)
	@ApiOperation(value="门店销售统计-今日订单/销售统计(图)查询接口")
	public AjaxInfo queryToDayOrderStatistics(@ModelAttribute StatisticsAnalysisParamVO paramVO) {
		ExecuteResult<Map<String, Object>> executeResult = newStatisticsExportService.statisticsListToDayOrderTrend(paramVO);
		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
		return result;
	}

	/**
	 * <p>Title: queryOrderStatistics</p>
	 * <p>Description: 门店销售统计-近7天/近30天订单/销售统计(图)查询接口</p>
	 * @param paramVO 城市orgStr/门店storeStr/门店storeStr/起止时间/用户id
	 * @return
	 * @author zhouyou
	 * @date 2019年5月18日
	 */
	@RequestMapping(value = "order", method = RequestMethod.GET)
	@ApiOperation(value="门店销售统计-近7天/近30天订单/销售统计(图)查询接口")
	public AjaxInfo queryOrderStatistics(@ModelAttribute StatisticsAnalysisParamVO paramVO) {
		ExecuteResult<Map<String, Object>> executeResult = newStatisticsExportService.statisticsOrderTrend(paramVO);
		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
		return result;
	}

	/**
	 * <p>Title: queryNotBuy</p>
	 * <p>Description: 未购买上报统计查询接口</p>
	 * @param paramVO 门店storeStr/起止时间/门店名称/门店编号
	 * @param page 分页
	 * @return
	 * @author zhouyou
	 * @date 2019年5月19日
	 */
	@RequestMapping(value = "notBuy", method = RequestMethod.GET)
	@ApiOperation(value="未购买上报统计查询接口")
	public AjaxInfo queryNotBuy(@ModelAttribute NotBuyParamVO paramVO, @ModelAttribute Page page) {
		ExecuteResult<PageInfo<StatisticsNotBuyVO>> executeResult = newStatisticsExportService.statisticsPageNotBuy(paramVO, page);
		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
		return result;
	}

	/**
	 * <p>Title: queryCustomerSource</p>
	 * <p>Description: 门店消费者来源分析(图)查询接口</p>
	 * @param paramVO 门店storeIds/起止时间
	 * @return
	 * @author zhouyou
	 * @date 2019年5月19日
	 */
	@RequestMapping(value = "source", method = RequestMethod.GET)
	@ApiOperation(value="门店消费者来源分析(图)查询接口")
	public AjaxInfo queryCustomerSource(@ModelAttribute BaseParamVO paramVO) {
		ExecuteResult<List<StatisticsSourceVO>> executeResult = newStatisticsExportService.statisticsListCustomerSource(paramVO);
		return ResultUtil.getSelectListAjaxInfo(executeResult);
	}

	/**
	 * <p>Title: queryNotBuyCause</p>
	 * <p>Description: 未购买原因分析(图)查询接口</p>
	 * @param paramVO 门店storeIds/起止时间
	 * @return
	 * @author zhouyou
	 * @date 2019年5月19日
	 */
	@RequestMapping(value = "cause", method = RequestMethod.GET)
	@ApiOperation(value="未购买原因分析(图)查询接口")
	public AjaxInfo queryNotBuyCause(@ModelAttribute BaseParamVO paramVO) {
		ExecuteResult<List<StatisticsSourceVO>> executeResult = newStatisticsExportService.statisticsListNotBuyCause(paramVO);
		AjaxInfo result = ResultUtil.getSelectListAjaxInfo(executeResult);
		return result;
	}
	/**
	 * <p>Title: queryNotBuyCustomer</p>
	 * <p>Description: 未购买消费者统计查询接口</p>
	 * @param vo 门店id/起止时间
	 * @param page 分页
	 * @return
	 * @author zhouyou
	 * @date 2019年5月19日
	 */
    @UserAuthoriseController
	@RequestMapping(value="notBuyCustomer", method=RequestMethod.GET)
	@ApiOperation(value="未购买消费者统计查询接口")
	public AjaxInfo queryNotBuyCustomer(@ModelAttribute BaseParamVO vo,@ModelAttribute Page page){
		AjaxInfo info = new AjaxInfo();
		ExecuteResult<PageInfo<NotBuyCustomerVO>> result = newStatisticsExportService.statisticsPageNotBuyCustomer(vo, page);
		info=ResultUtil.getSelectAjaxInfo(result);
		return info;
	}

	/**
	 * <p>Title: exportNotBuyCustomer</p>
	 * <p>Description: 未购买消费者统计导出接口</p>
	 * @param response
	 * @param vo 门店id/起止时间
	 * @author zhouyou
	 * @date 2019年5月19日
	 */
    @UserAuthoriseController
	@RequestMapping(value="notBuyCustomerExcel", method=RequestMethod.GET)
	@ApiOperation(value="未购买消费者统计导出接口")
	public void exportNotBuyCustomer(HttpServletResponse response, @ModelAttribute BaseParamVO vo){
		ExecuteResult<List<NotBuyCustomerVO>> queryResult = newStatisticsExportService.statisticsListNotBuyCustomer(vo);
		if (Constants.SUCCESS_CODE.equals(queryResult.getCode())) {
			String fileName = "未购买消费者统计导出";
			try {
	    		//writeFile:将导出数据输出到指定的文件
	    		//write：将数据输出到客户端（需要HttpServletResponse对象）
				new ExportExcel(fileName,NotBuyExportVO.class, 1).setDataList(queryResult.getResult()).write(response, fileName +".xlsx").dispose();
	    	} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * <p>Title: salesStats</p>
	 * <p>Description: 销售统计 统计销售情况</p>
	 * @param paramVO 门店storeStr/起止时间/门店名称/门店编号
	 * @param page 分页
	 * @return
	 * @author cuijiudong
	 * @date 2019年6月17日
	 */
	@RequestMapping(value = "salesStats", method = RequestMethod.GET)
	@ApiOperation(value="销售统计")
	public AjaxInfo queryStatsSales(@ModelAttribute StatsOrderParamVO paramVO, @ModelAttribute Page page) {
		ExecuteResult<PageInfo<StatisticsSalesVO>> executeResult = newStatisticsExportService.statsSalesOrderPage(paramVO, page);
		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
		return result;
	}

	/**
	 * <p>Title: queryCustomerSource</p>
	 * <p>Description: 门店消费者来源分析(图)查询接口</p>
	 * @param paramVO 门店storeIds/起止时间
	 * @return
	 * @author cuijiudong
	 * @date 2019年6月17日
	 */
	@RequestMapping(value = "salesStatsBySource", method = RequestMethod.GET)
	@ApiOperation(value="销售统计：消费者来源统计 ")
	public AjaxInfo queryStatsSalesBySource(@ModelAttribute BaseParamVO paramVO) {
		ExecuteResult<List<StatisticsSourceVO>> executeResult = newStatisticsExportService.statsSalesByType(paramVO, OrderConstants.STATS_BY_SOURCE);
		return ResultUtil.getSelectListAjaxInfo(executeResult);
	}

	/**
	 * <p>Title: queryNotBuyCause</p>
	 * <p>Description: 销售统计-根据车型</p>
	 * @param paramVO 门店storeIds/起止时间
	 * @return
	 * @author cuijiudong
	 * @date 2019年6月17日
	 */
	@RequestMapping(value = "salesStatsByCategory", method = RequestMethod.GET)
	@ApiOperation(value="销售统计-根据车型统计")
	public AjaxInfo queryStatsSaleByCategory(@ModelAttribute BaseParamVO paramVO) {
		ExecuteResult<List<StatisticsSourceVO>> executeResult = newStatisticsExportService.statsSalesByType(paramVO, OrderConstants.STATS_BY_CATEGORY);
		AjaxInfo result = ResultUtil.getSelectListAjaxInfo(executeResult);
		return result;
	}
	/**
	 * <p>Title: queryNotBuyCause</p>
	 * <p>Description: 销售统计-根据车型</p>
	 * @param paramVO 门店storeIds/起止时间
	 * @return
	 * @author cuijiudong
	 * @date 2019年6月17日
	 */
	@RequestMapping(value = "salesStatsByActive", method = RequestMethod.GET)
	@ApiOperation(value="销售统计-根据活动")
	public AjaxInfo queryStatsSaleByActive(@ModelAttribute BaseParamVO paramVO) {
		ExecuteResult<List<StatisticsSourceVO>> executeResult = newStatisticsExportService.statsSalesByType(paramVO, OrderConstants.STATS_BY_ACTIVE);
		AjaxInfo result = ResultUtil.getSelectListAjaxInfo(executeResult);
		return result;
	}


	/**
	 * <p>Title: salesStats</p>
	 * <p>Description: 退货统计</p>
	 * @param paramVO 门店storeStr/起止时间/门店名称/门店编号
	 * @param page 分页
	 * @return
	 * @author cuijiudong
	 * @date 2019年6月20日
	 */
	@RequestMapping(value = "returnStats", method = RequestMethod.GET)
	@ApiOperation(value="退货统计")
	public AjaxInfo queryStatsReturn(@ModelAttribute StatsOrderParamVO paramVO, @ModelAttribute Page page) {
		ExecuteResult<PageInfo<StatisticsSalesVO>> executeResult = newStatisticsExportService.statsReturnOrderPage(paramVO, page);
		AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
		return result;
	}

	/**
	 * <p>Title: queryCustomerSource</p>
	 * <p>Description: 门店消费者来源分析(图)查询接口</p>
	 * @param paramVO 门店storeIds/起止时间
	 * @return
	 * @author cuijiudong
	 * @date 2019年6月17日
	 */
	@RequestMapping(value = "returnStatsByReason", method = RequestMethod.GET)
	@ApiOperation(value="销售统计：退货原因 ")
	public AjaxInfo queryStatsReturnByReason(@ModelAttribute BaseParamVO paramVO) {

        ExecuteResult<List<StatisticsSourceVO>> executeResult = newStatisticsExportService.statsReturnByReason(paramVO, OrderConstants.STATS_BY_REASON);
		return ResultUtil.getSelectListAjaxInfo(executeResult);
	}



}

package com.camelot.order.export.service;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewSalesOrderGoodsVO;
import com.camelot.order.export.vo.SalesVolumeVO;
import com.camelot.order.export.vo.StatisitcsGoodsSalesVO;
import com.camelot.order.export.vo.StatisticsGoodsCategoryVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author hudya
 */
public interface NewSalesOrderGoodsExportService extends BaseService<NewSalesOrderGoodsVO> {


    /**
     * 条件查询订单商品信息
     *
     * @param vo
     * @return
     */
    ExecuteResult<List<NewSalesOrderGoodsVO>> queryOrderGoods(NewSalesOrderGoodsVO vo);

    /**
     * 添加订单商品信息
     *
     * @param vo
     * @return
     */
    ExecuteResult<NewSalesOrderGoodsVO> addOrderGoods(NewSalesOrderGoodsVO vo);
    
    /**
     * <p>Title: queryPageSalesOrderGoods</p>
     * <p>Description: 分页查询差异订单商品信息</p>
     * @param vo
     * @param page
     * @return
     * @author zhouyou
     * @date 2019年5月20日
     */
    ExecuteResult<PageInfo<NewSalesOrderGoodsVO>> queryPageDifference(NewSalesOrderGoodsVO vo, Page page);

	/**
	 * <p>Title: queryListDifference</p>
	 * <p>Description: 查询差异订单商品信息</p>
	 * @param newSalesOrderGoods
	 * @return
	 * @author zhouyou
	 * @date 2019年5月20日
	 */
	ExecuteResult<List<NewSalesOrderGoodsVO>> queryListDifference(NewSalesOrderGoodsVO newSalesOrderGoods);

	/**
	 * <p>Title: queryCountTotal</p>
	 * <p>Description: 查询整车/周边销量信息</p>
	 * @param newSalesOrderGoodsParamVO
	 * @return
	 * @author zhouyou
	 * @date 2019年5月21日
	 */
	ExecuteResult<SalesVolumeVO> queryCountTotal(NewSalesOrderGoodsVO newSalesOrderGoodsParamVO);

	/**
	 * <p>Title: queryPageGoodsVolume</p>
	 * <p>Description: 查询销售订单商品销量统计信息</p>
	 * @param newSalesOrderGoodsParamVO
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	ExecuteResult<PageInfo<StatisitcsGoodsSalesVO>> queryPageGoodsVolume(NewSalesOrderGoodsVO vo, Page page);

	/**
	 * <p>Title: queryListVehicleAmount</p>
	 * <p>Description: 查询有效订单整车分类统计</p>
	 * @param newSalesOrderGoodsParamVO
	 * @return
	 * @author zhouyou
	 * @date 2019年5月23日
	 */
	ExecuteResult<List<StatisticsGoodsCategoryVO>> queryListVehicleAmount(NewSalesOrderGoodsVO newSalesOrderGoodsParamVO);

	/**
	 * 校验该商品是否可以再被下单
	 * @param vo
	 * @return
	 */
	ExecuteResult<NewSalesOrderGoodsVO> verfyOrderGoods(NewSalesOrderGoodsVO vo);
}

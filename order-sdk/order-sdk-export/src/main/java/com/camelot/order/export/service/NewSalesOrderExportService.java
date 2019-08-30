package com.camelot.order.export.service;


import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
import com.camelot.order.export.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface NewSalesOrderExportService extends BaseService<NewSalesOrderVO> {


    /**
     * 销售订单分页查询
     *
     * @param vo
     * @param page
     * @return
     */
    ExecuteResult<PageInfo<NewSalesOrderVO>> querySalesOrderList(NewSalesOrderVO vo, Page<NewSalesOrderVO> page);

    /**
     * 销售订单详情查询
     *
     * @param id
     * @return
     */
    ExecuteResult<NewSalesOrderVO> searchSalesOrderDetail(Long id);

    /**
     * 销售订单提交
     *
     * @param vo
     * @return
     */
    ExecuteResult<NewSalesOrderVO> addSalesOrder(NewSalesOrderVO vo);

    /**
     * 订单取消/修改订单状态
     *
     * @param vo
     * @return
     */
    ExecuteResult<NewSalesOrderVO> cancelSalesOrder(NewSalesOrderVO vo);

    /**
     * 统计消费者相关的订单数目和订单金额(origin-pengfei)
     *
     * @param vo
     * @return
     */
    ExecuteResult<OrderCustomerVO> statisticOrderWithCustomer(OrderCustomerVO vo);

    /**
     * <p>Title: queryListActivityData</p>
     * <p>Description: 查询参加活动销售订单信息</p>
     *
     * @param newSalesOrderVO
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    ExecuteResult<List<NewSalesOrderVO>> queryListActivityData(NewSalesOrderVO newSalesOrderVO);

    /**
     * <p>Title: queryCount</p>
     * <p>Description: 查询数量</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    ExecuteResult<Long> queryCount(NewSalesOrderVO vo);

    /**
     * <p>Title: queryCountAmount</p>
     * <p>Description: 查询订单总数/销售总额</p>
     *
     * @param newSalesOrderVO
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    ExecuteResult<SalesVolumeVO> queryCountAmount(NewSalesOrderVO newSalesOrderVO);

    /**
     * <p>Title: queryListSalesOrderId</p>
     * <p>Description: 查询订单idList</p>
     *
     * @param newSalesOrderVO
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    ExecuteResult<List<Long>> queryListSalesOrderId(NewSalesOrderVO newSalesOrderVO);

    /**
     * <p>Title: queryListToDayOrderTrend</p>
     * <p>Description: 查询今日订单/销售统计(图)信息</p>
     *
     * @param paramVO
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<List<Map<String, Object>>> queryListToDayOrderTrend(NewSalesOrderVO paramVO);

    /**
     * <p>Title: statisticsOrderTrend</p>
     * <p>Description: 查询近7天/近30天订单/销售统计(图)信息</p>
     *
     * @param paramVO
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<List<Map<String, Object>>> statisticsOrderTrend(NewSalesOrderVO paramVO);

    /**
     * <p>Title: queryListCancleTotal</p>
     * <p>Description: 查询取消订单信息</p>
     *
     * @param newSalesOrderParamVO
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<List<StatisticsNotBuyVO>> queryListCancleTotal(NewSalesOrderVO newSalesOrderParamVO);

    /**
     * <p>Title: queryListCustomerSource</p>
     * <p>Description: 查询取消订单原因分析(图)信息</p>
     *
     * @param newSalesOrderParamVO
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<List<StatisticsSourceVO>> queryListCustomerSource(NewSalesOrderVO newSalesOrderParamVO);

    /**
     * <p>Title: queryListNotBuyCause</p>
     * <p>Description: 查询取消原因分析(图)信息</p>
     *
     * @param newSalesOrderParamVO
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<List<StatisticsSourceVO>> queryListNotBuyCause(NewSalesOrderVO newSalesOrderParamVO);

    /**
     * 最大的订单
     * @param nowDate
     * @return
     */
    String getMaxSalesOrderNumber(String nowDate);

    /**
     * <p>Title: queryStatsSalesOrderPage</p>
     * <p>Description: 查询销售统计</p>
     *
     * @param newSalesOrderParamVO
     * @return
     * @author cuijiudong
     * @date 2019年6月17 日
     */
    ExecuteResult<List<StatisticsSalesVO>> queryStatsSalesOrderPage(NewSalesOrderVO newSalesOrderParamVO);

    /**
     * <p>Title: queryStatsSalesByType</p>
     * <p>Description: 查询销售统计:消费者来源统计</p>
     *
     * @param newSalesOrderParamVO
     * @return
     * @author cuijiudong
     * @date 2019年6月17 日
     */
    ExecuteResult<List<StatisticsSourceVO>> queryStatsSalesByType(NewSalesOrderVO newSalesOrderParamVO,String type);


    /**
     * @Description [ 查询数据金额不符合 ]
     * @Author [hudyang]
     * @Date 2019/6/26 15:24
     * @Param
     * @return
     **/
    ExecuteResult<List<NewSalesOrderVO>> queryamountList(NewSalesOrderVO vo);

    /**
     * @Description [ 修改不对的金额 ]
     * @Author [hudyang]
     * @Date 2019/6/26 15:52
     * @Param
     * @return
     **/
    ExecuteResult<List<NewSalesOrderVO>> updateamountList(NewSalesOrderVO vo);

    /**
     * @Description [ 扣除库存数量服务-stock ]
     * @Author [hudyang]
     * @Date 2019/6/27 14:56
     * @Param
     * @return
    **/
    ExecuteResult<String> reduceWarhouseGoodsAmount(NewSalesOrderVO vo);
}

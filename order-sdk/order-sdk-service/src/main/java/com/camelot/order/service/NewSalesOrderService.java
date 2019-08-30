package com.camelot.order.service;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewReturnOrderVO;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
import com.camelot.order.export.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface NewSalesOrderService extends BaseService<NewSalesOrderVO> {

    /**
     * 统计消费者相关的订单数目和订单金额(origin-pengfei)
     *
     * @param vo
     * @return
     */
    ExecuteResult<OrderCustomerVO> statisticsOrderWithCustomer(OrderCustomerVO vo);

    /**
     * <p>Title: queryListActivityData</p>
     * <p>Description: 查询参加活动的销售订单信息</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    ExecuteResult<List<NewSalesOrderVO>> queryListActivityData(NewSalesOrderVO vo);

    /**
     * <p>Title: queryCount</p>
     * <p>Description: 查询数量信息</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    ExecuteResult<Long> queryCount(NewSalesOrderVO vo);

    /**
     * <p>Title: queryCountAmount</p>
     * <p>Description: 查询订单总数/销售总额信息</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    ExecuteResult<SalesVolumeVO> queryCountAmount(NewSalesOrderVO vo);

    /**
     * <p>Title: queryListSalesOrderId</p>
     * <p>Description: 查询销售订单id集合信息</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    ExecuteResult<List<Long>> queryListSalesOrderId(NewSalesOrderVO vo);

    /**
     * <p>Title: queryListToDayOrderTrend</p>
     * <p>Description: 查询今日订单/销售统计(图)信息</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<List<Map<String, Object>>> queryListToDayOrderTrend(NewSalesOrderVO vo);

    /**
     * <p>Title: statisticsOrderTrend</p>
     * <p>Description: 查询近7天/近30天订单/销售统计(图)信息</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<List<Map<String, Object>>> statisticsOrderTrend(NewSalesOrderVO vo);

    /**
     * <p>Title: queryListCancleTotal</p>
     * <p>Description: 查询取消订单信息</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<List<StatisticsNotBuyVO>> queryListCancleTotal(NewSalesOrderVO vo);

    /**
     * <p>Title: queryListCustomerSource</p>
     * <p>Description: 查询取消订单原因分析(图)信息</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<List<StatisticsSourceVO>> queryListCustomerSource(NewSalesOrderVO vo);

    /**
     * <p>Title: queryListNotBuyCause</p>
     * <p>Description: 查询取消原因分析(图)信息</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    ExecuteResult<List<StatisticsSourceVO>> queryListNotBuyCause(NewSalesOrderVO vo);

    /**
     * 最大订单
     * @param nowDate
     * @return
     */
    String getMaxSalesOrderNumber(String nowDate);


    /**
     * <p>Title: queryListNotBuyCause</p>
     * <p>Description: 查询销售统计 </p>
     *
     * @param newSalesOrderParamVO ,page
     * @return
     * @author cujiudong
     * @date 2019年6月17日
     */
    ExecuteResult<List<StatisticsSalesVO>> queryStatsSalesOrderPage(NewSalesOrderVO newSalesOrderParamVO);
    /**
     * <p>Title: queryListCustomerSource</p>
     * <p>Description: 查询销售统计:根据消费者来源 </p>
     *
     * @param vo
     * @return
     * @author cujiudong
     * @date 2019年6月17日
     */
    ExecuteResult<List<StatisticsSourceVO>> queryStatsSalesBySource(NewSalesOrderVO vo);

    /**
     * <p>Title: queryListCustomerSource</p>
     * <p>Description: 查询销售统计:根据消费者来源 </p>
     *
     * @param vo
     * @return
     * @author cujiudong
     * @date 2019年6月17日
     */
    ExecuteResult<List<StatisticsSourceVO>> queryStatsSaleByCategory(NewSalesOrderVO vo);

    /**
     * <p>Title: queryListCustomerSource</p>
     * <p>Description: 查询销售统计:根据消费者来源 </p>
     *
     * @param vo
     * @return
     * @author cujiudong
     * @date 2019年6月17日
     */
    ExecuteResult<List<StatisticsSourceVO>> queryStatsSaleByActive(NewSalesOrderVO vo);

    /**
     * <p>Title: queryListNotBuyCause</p>
     * <p>Description: 查询退货统计 </p>
     *
     * @param newSalesOrderParamVO ,page
     * @return
     * @author cujiudong
     * @date 2019年6月17日
     */
   // ExecuteResult<List<StatisticsSalesVO>> queryStatsReturnOrderPage(NewReturnOrderVO newSalesOrderParamVO);

    /**
     * <p>Title: queryListCustomerSource</p>
     * <p>Description: 查询退货统计:退货原因 </p>
     *
     * @param vo
     * @return
     * @author cujiudong
     * @date 2019年6月17日
     */
    //ExecuteResult<List<StatisticsSourceVO>> queryStatsReturnByReason(NewSalesOrderVO vo);

}

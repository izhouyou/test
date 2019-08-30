package com.camelot.order.dao;


import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.NewReturnOrderDomain;
import com.camelot.order.domain.NewSalesOrderDomain;
import com.camelot.order.domain.OrderCustomerDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author hudya
 */
@Mapper
public interface NewSalesOrderDAO extends BaseDAO<NewSalesOrderDomain, Long> {

    /**
     * 统计消费者相关的订单数目和订单金额(origin-pengfei)
     *
     * @param dom
     * @return
     */
    OrderCustomerDomain statisticsOrderWithCustomer(OrderCustomerDomain dom);

    /**
     * <p>Title: queryListActivityData</p>
     * <p>Description: 查询参加活动的销售订单信息</p>
     *
     * @param dom
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    List<NewSalesOrderDomain> queryListActivityData(NewSalesOrderDomain dom);

    /**
     * <p>Title: queryCount</p>
     * <p>Description: 查询数量</p>
     *
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    Long queryCount(NewSalesOrderDomain vo);

    /**
     * <p>Title: queryCountAmount</p>
     * <p>Description: 查询订单总数/销售总额</p>
     *
     * @param dom
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    Map<String, Object> queryCountAmount(NewSalesOrderDomain dom);

    /**
     * <p>Title: queryListSalesOrderId</p>
     * <p>Description: 查询销售订单id集合</p>
     *
     * @param dom
     * @return
     * @author zhouyou
     * @date 2019年5月21日
     */
    List<Long> queryListSalesOrderId(NewSalesOrderDomain dom);

    /**
     * <p>Title: queryListToDayOrderTrend</p>
     * <p>Description: 查询今日订单/销售统计(图)信息</p>
     *
     * @param dom
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    List<Map<String, Object>> queryListToDayOrderTrend(NewSalesOrderDomain dom);

    /**
     * <p>Title: statisticsOrderTrend</p>
     * <p>Description: 查询近7天/近30天订单/销售统计(图)信息</p>
     *
     * @param dom
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    List<Map<String, Object>> statisticsOrderTrend(NewSalesOrderDomain dom);

    /**
     * <p>Title: queryListCancleTotal</p>
     * <p>Description: 查询取消订单信息</p>
     *
     * @param dom
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    List<Map<String, Object>> queryListCancleTotal(NewSalesOrderDomain dom);

    /**
     * <p>Title: queryListCustomerSource</p>
     * <p>Description: 查询取消订单原因分析(图)信息</p>
     *
     * @param dom
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    List<Map<String, Object>> queryListCustomerSource(NewSalesOrderDomain dom);

    /**
     * <p>Title: queryListNotBuyCause</p>
     * <p>Description: 查询取消原因分析(图)信息</p>
     *
     * @param dom
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    List<Map<String, Object>> queryListNotBuyCause(NewSalesOrderDomain dom);

    /**
     * 最大订单
     * @param nowDate
     * @return
     */
    String getMaxSalesOrderNumber(String nowDate);


    /**
     * <p>Title: queryStatsSalesOrderPage</p>
     * <p>Description: 查询销售统计</p>
     *
     * @param dom
     * @return
     * @author cuijiudong
     * @date 2019年6月17日
     */
    List<Map<String, Object>> queryStatsSalesOrderPage(NewSalesOrderDomain dom);

    /**
     * <p>Title: queryStatsSalesOrderPage</p>
     * <p>Description: 查询销售统计：来源</p>
     *
     * @param dom
     * @return
     * @author cuijiudong
     * @date 2019年6月17日
     */
    List<Map<String, Object>> queryStatsSalesBySource(NewSalesOrderDomain dom);
    /**
     * <p>Title: queryStatsSalesOrderPage</p>
     * <p>Description: 查询销售统计:车型</p>
     *
     * @param dom
     * @return
     * @author cuijiudong
     * @date 2019年6月17日
     */
    List<Map<String, Object>> queryStatsSaleByCategory(NewSalesOrderDomain dom);
    /**
     * <p>Title: queryStatsSalesOrderPage</p>
     * <p>Description: 查询销售统计：活动分类</p>
     *
     * @param dom
     * @return
     * @author cuijiudong
     * @date 2019年6月17日
     */
    List<Map<String, Object>> queryStatsSaleByActive(NewSalesOrderDomain dom);




}

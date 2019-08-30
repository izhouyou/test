package com.camelot.order.dao;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.NewReturnOrderDomain;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface NewReturnOrderDAO extends BaseDAO<NewReturnOrderDomain, Long> {

    /**
     * <p>Title: queryReturnAmount</p>
     * <p>Description: 查询退货金额</p>
     *
     * @param domain
     * @return
     * @author zhouyou
     * @date 2019年5月22日
     */
    BigDecimal queryReturnAmount(NewReturnOrderDomain domain);

    /**
     * 最大退货订单
     * @param nowDate
     * @return
     */
    String getMaxReturnOrderNumber(String nowDate);

    /**
     * <p>Title: queryStatsReturnOrderPage</p>
     * <p>Description: 查询退货统计</p>
     *
     * @param dom
     * @return
     * @author cuijiudong
     * @date 2019年6月17日
     */
    List<Map<String, Object>> queryStatsReturnOrderPage(NewReturnOrderDomain dom);


    /**
     * <p>Title: queryStatsReturnByReason</p>
     * <p>Description: 查询退货统计：退货原因统计</p>
     *
     * @param dom
     * @return
     * @author cuijiudong
     * @date 2019年6月17日queryStatsSaleByReason
     */
    List<Map<String, Object>> queryStatsReturnByReason(NewReturnOrderDomain dom);

	/**
	 * <p>Title: queryCountAmount</p>
	 * <p>Description: 查询退货订单统计信息</p>
	 * @param domain
	 * @return
	 * @author zhouyou
	 * @date 2019年6月27日
	 */
	Map<String, Object> queryCountAmount(NewReturnOrderDomain domain);


}

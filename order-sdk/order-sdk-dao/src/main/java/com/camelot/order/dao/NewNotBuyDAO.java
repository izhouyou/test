package com.camelot.order.dao;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.NewNotBuyDomain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewNotBuyDAO extends BaseDAO<NewNotBuyDomain, Long> {

	/**
	 * <p>Title: queryListNotBuyTotal</p>
	 * <p>Description: 查询未购买上报统计信息</p>
	 * @param dom
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	List<Map<String, Object>> queryListNotBuyTotal(NewNotBuyDomain dom);

	/**
	 * <p>Title: queryListCustomerSource</p>
	 * <p>Description: 查询未购买原因分析(图)信息</p>
	 * @param dom
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	List<Map<String, Object>> queryListCustomerSource(NewNotBuyDomain dom);

	/**
	 * <p>Title: queryListNotBuyCause</p>
	 * <p>Description: 查询未购买原因分析(图)信息</p>
	 * @param dom
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	List<Map<String, Object>> queryListNotBuyCause(NewNotBuyDomain dom);

}

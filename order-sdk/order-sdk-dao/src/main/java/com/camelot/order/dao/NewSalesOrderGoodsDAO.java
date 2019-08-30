package com.camelot.order.dao;

import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.NewSalesOrderGoodsDomain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewSalesOrderGoodsDAO extends BaseDAO<NewSalesOrderGoodsDomain,Long> {

	/**
	 * <p>Title: queryListDifference</p>
	 * <p>Description: 查询差异订单商品信息</p>
	 * @param domain
	 * @return
	 * @author zhouyou
	 * @date 2019年5月20日
	 */
	List<NewSalesOrderGoodsDomain> queryListDifference(NewSalesOrderGoodsDomain domain);

	/**
	 * <p>Title: queryCountTotal</p>
	 * <p>Description: 查询整车/周边销量</p>
	 * @param domain
	 * @return
	 * @author zhouyou
	 * @date 2019年5月21日
	 */
	Map<String, Object> queryCountTotal(NewSalesOrderGoodsDomain domain);

	/**
	 * <p>Title: queryPageGoodsVolume</p>
	 * <p>Description: 查询销售订单商品销量统计信息</p>
	 * @param domain
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	List<Map<String, Object>> queryPageGoodsVolume(NewSalesOrderGoodsDomain domain);

	/**
	 * <p>Title: queryListVehicleAmount</p>
	 * <p>Description: 查询有效订单整车分类统计信息</p>
	 * @param domain
	 * @return
	 * @author zhouyou
	 * @date 2019年5月23日
	 */
	List<Map<String, Object>> queryListVehicleAmount(NewSalesOrderGoodsDomain domain);

}

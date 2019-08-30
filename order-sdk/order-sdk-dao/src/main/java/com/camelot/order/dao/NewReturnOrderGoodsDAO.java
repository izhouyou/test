package com.camelot.order.dao;

 import com.camelot.common.dao.BaseDAO;
import com.camelot.order.domain.NewReturnOrderGoodsDomain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewReturnOrderGoodsDAO extends BaseDAO<NewReturnOrderGoodsDomain,Long> {

	/**
	 * <p>Title: queryListReturnGoods</p>
	 * <p>Description: 查询退货有序商品信息</p>
	 * @param domain
	 * @return
	 * @author zhouyou
	 * @date 2019年5月20日
	 */
	List<NewReturnOrderGoodsDomain> queryListReturnGoods(NewReturnOrderGoodsDomain domain);

}

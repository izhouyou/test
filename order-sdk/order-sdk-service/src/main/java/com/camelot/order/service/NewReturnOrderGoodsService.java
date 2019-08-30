package com.camelot.order.service;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewReturnOrderGoodsVO;
import com.github.pagehelper.PageInfo;

/**
 * @author hudya
 */
public interface NewReturnOrderGoodsService extends BaseService<NewReturnOrderGoodsVO> {

	/**
	 * <p>Title: queryPageReturnGoods</p>
	 * <p>Description: 查询退货有序商品信息</p>
	 * @param paramVO
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年5月20日
	 */
	ExecuteResult<PageInfo<NewReturnOrderGoodsVO>> queryPageReturnGoods(NewReturnOrderGoodsVO paramVO, Page page);
}

package com.camelot.order.export.service;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewReturnOrderGoodsVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author hudya
 */
public interface NewReturnOrderGoodsExportService extends BaseService<NewReturnOrderGoodsVO> {

    /**
     * <p>Title: queryPageReturnGoods</p>
     * <p>Description: 查询退货有序商品信息</p>
     *
     * @param newReturnOrderGoodsVO
     * @param page
     * @return
     * @author zhouyou
     * @date 2019年5月20日
     */
    ExecuteResult<PageInfo<NewReturnOrderGoodsVO>> queryPageReturnGoods(NewReturnOrderGoodsVO newReturnOrderGoodsVO, Page page);

    /**
     * @Description [查询退单商品数据 ]
     * @Author [hudyang]
     * @Date 2019/5/26 16:43
     * @Param
     * @return
    **/
    ExecuteResult<List<NewReturnOrderGoodsVO>> queryReturnOrderGoods(NewReturnOrderGoodsVO vo);

    /**
     * @Description [ 添加订单商品数据 ]
     * @Author [hudyang]
     * @Date 2019/5/26 16:43
     * @Param
     * @return
    **/
    ExecuteResult<NewReturnOrderGoodsVO> addNewReturnGoods(NewReturnOrderGoodsVO newReturnOrderGoodsVO);

}

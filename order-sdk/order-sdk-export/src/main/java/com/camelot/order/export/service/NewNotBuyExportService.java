package com.camelot.order.export.service;


import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewNotBuyVO;
import com.camelot.order.export.vo.StatisticsNotBuyVO;
import com.camelot.order.export.vo.StatisticsSourceVO;
import com.github.pagehelper.PageInfo;

/**
 * @author hudya
 */
public interface NewNotBuyExportService extends BaseService<NewNotBuyVO> {

    /**
     * 未购买上报分页查询
     * @param vo
     * @param page
     * @return
     */
    ExecuteResult<PageInfo<NewNotBuyVO>> queryNotBuyByPage(NewNotBuyVO vo, Page page);

    /**
     * 新增未购买上报
     * @param vo
     * @return
     */
    ExecuteResult<NewNotBuyVO> addNotBuy(NewNotBuyVO vo);

	/**
	 * <p>Title: queryListNotBuyTotal</p>
	 * <p>Description: 查询未购买上报统计信息</p>
	 * @param newNotBuyVO
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	ExecuteResult<List<StatisticsNotBuyVO>> queryListNotBuyTotal(NewNotBuyVO newNotBuyVO);

	/**
	 * <p>Title: queryListCustomerSource</p>
	 * <p>Description: 查询未购买原因分析(图)信息</p>
	 * @param newNotBuyParamVO
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	ExecuteResult<List<StatisticsSourceVO>> queryListCustomerSource(NewNotBuyVO newNotBuyParamVO);

	/**
	 * <p>Title: queryListNotBuyCause</p>
	 * <p>Description: 查询未购买原因分析(图)信息</p>
	 * @param newNotBuyParamVO
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	ExecuteResult<List<StatisticsSourceVO>> queryListNotBuyCause(NewNotBuyVO newNotBuyParamVO);
}

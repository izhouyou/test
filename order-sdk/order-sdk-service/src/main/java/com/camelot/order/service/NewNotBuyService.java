package com.camelot.order.service;


import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.NewNotBuyVO;
import com.camelot.order.export.vo.StatisticsNotBuyVO;
import com.camelot.order.export.vo.StatisticsSourceVO;

/**
 * @author hudya
 */
public interface NewNotBuyService extends BaseService<NewNotBuyVO> {

	/**
	 * <p>Title: queryListNotBuyTotal</p>
	 * <p>Description: 查询未购买上报统计信息</p>
	 * @param vo
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	ExecuteResult<List<StatisticsNotBuyVO>> queryListNotBuyTotal(NewNotBuyVO vo);

	/**
	 * <p>Title: queryListCustomerSource</p>
	 * <p>Description: 查询未购买原因分析(图)信息</p>
	 * @param vo
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	ExecuteResult<List<StatisticsSourceVO>> queryListCustomerSource(NewNotBuyVO vo);

	/**
	 * <p>Title: queryListNotBuyCause</p>
	 * <p>Description: 查询未购买原因分析(图)信息</p>
	 * @param vo
	 * @return
	 * @author zhouyou
	 * @date 2019年5月22日
	 */
	ExecuteResult<List<StatisticsSourceVO>> queryListNotBuyCause(NewNotBuyVO vo);

    

}

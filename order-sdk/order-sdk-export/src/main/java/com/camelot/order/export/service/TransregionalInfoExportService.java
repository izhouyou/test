package com.camelot.order.export.service;

import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.snapshot.TransregionalInfoVO; 
 
public interface TransregionalInfoExportService extends BaseService<TransregionalInfoVO> {

	/**
	 * <p>Title: addTransregionalInfo</p>
	 * <p>Description: 窜货订单追踪添加接口</p>
	 * @param vo
	 * @return
	 * @author zhouyou
	 * @date 2019年6月21日
	 */
	ExecuteResult<TransregionalInfoVO> addTransregionalInfo(TransregionalInfoVO vo);

	/**
	 * <p>Title: handleTransregionalInfoList</p>
	 * <p>Description: 对查询结果列表进行处理</p>
	 * @param list
	 * @return
	 * @author zhouyou
	 * @date 2019年6月24日
	 */
	List<TransregionalInfoVO> handleTransregionalInfoList(List<TransregionalInfoVO> list);

}

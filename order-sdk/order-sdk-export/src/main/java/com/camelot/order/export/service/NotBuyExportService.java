package com.camelot.order.export.service;

import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.vo.NotBuyVO;

public interface NotBuyExportService extends BaseService<NotBuyVO>{
	
	/**
	 *@Description 新增未购买上报
	 *@author xupengfei
	 *@Data 2019年4月9日 
	 * @param vo
	 * @return
	 */
	ExecuteResult<NotBuyVO> submitNotBuy(NotBuyVO vo);
	
	/**
	 *@Description 对查询结果处理
	 *@author xupengfei
	 *@Date 2019年4月9日 
	 * @param list
	 * @return
	 */
	List<NotBuyVO> handleNotBuyList(List<NotBuyVO> list);
}

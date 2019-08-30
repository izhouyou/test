package com.camelot.order.export.service;


import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.base.BaseService;
import com.camelot.order.export.vo.ReceiptRecordVO;

public interface ReceiptRecordExportService extends BaseService<ReceiptRecordVO>{
	
	/**
	 *@Description 新增收款单信息
	 *@author xupengfei
	 *@Date 2019年4月10日 
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	ExecuteResult<ReceiptRecordVO> addReceiptRecord(ReceiptRecordVO vo) throws Exception;
	
	/**
	 *@Description 获取当天最大的单号
	 *@author xupengfei
	 *@Date 2019年4月4日 
	 * @param nowDate
	 * @return
	 */
	 String getMaxReceiptRecordNumber(String nowDate);
	 
	 /**
	 *@Description 处理查询列表
	 *@author xupengfei
	 *@Date 2019年4月10日 
	 * @param list
	 * @return
	 */
	List<ReceiptRecordVO> handleReceiptRecordList(List<ReceiptRecordVO> list);
	
	
	/**
	 *@Description 查询之前对查询条件处理
	 *@author xupengfei
	 *@Date 2019年4月10日 
	 * @param vo
	 * @return
	 */
	ReceiptRecordVO handleVOBeforeQuery(ReceiptRecordVO vo);
	
}

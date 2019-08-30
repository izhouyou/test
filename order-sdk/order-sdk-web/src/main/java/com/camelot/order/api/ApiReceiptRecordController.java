//package com.camelot.order.api;
//
//import com.camelot.common.bean.AjaxInfo;
//import com.camelot.common.bean.ExecuteResult;
//import com.camelot.common.bean.Page;
//import com.camelot.order.common.utils.AjaxInfoConstants;
//import com.camelot.order.common.utils.CheckUtil;
//import com.camelot.order.common.utils.Log;
//import com.camelot.order.common.utils.ResultUtil;
//import com.camelot.order.common.utils.excel.ExportExcel;
//import com.camelot.order.export.service.ReceiptRecordExportService;
//import com.camelot.order.export.vo.ReceiptRecordVO;
//import com.camelot.order.export.vo.excelvo.ReceiptRecordExcelVO;
//import com.github.pagehelper.PageInfo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.web.bind.ServletRequestDataBinder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping("receiptRecord")
//@Api(value="收款记录", tags="收款记录", description="")
//public class ApiReceiptRecordController {
//
//	private static Logger log = Log.get(ApiReceiptRecordController.class);
//
//	@Autowired
//	ReceiptRecordExportService receiptRecordExportService;
//
//	/**
//	 *@Description 对前端传值的String类型转换为Date类型
//	 *@author xupengfei
//	 *@Data 2019年4月10日
//	 * @param request
//	 * @param binder
//	 */
//	@InitBinder
//    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//    }
//
//	/**
//	 *@Description 新增收款单
//	 *@author xupengfei
//	 *@Date 2019年4月10日
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value="", method=RequestMethod.POST)
//	@ApiOperation(value="新增收款单")
//	public AjaxInfo addReceiptRecord(@RequestBody ReceiptRecordVO vo){
//		AjaxInfo info = new AjaxInfo();
//		try{
//			ExecuteResult<ReceiptRecordVO> result = receiptRecordExportService.addReceiptRecord(vo);
//			info = ResultUtil.getSelectAjaxInfo(result);
//		}catch(Exception e){
//			info.setCode(AjaxInfoConstants.ERROR_CODE);
//			info.setMsg(e.toString());
//			Log.error(log, "\n 方法[{}]，异常：[{}]", "ApiReceiptRecordController-addReceiptRecord", e.getMessage());
//
//		}
//		return info;
//	}
//
//
//	/**
//	 * 中台:收款记录 分页查询
//	 *@Description 收款单分页查询
//	 *@author xupengfei
//	 *@Date 2019年4月10日
//	 * @param vo
//	 * @param page
//	 * @return
//	 */
//	@RequestMapping(value="page", method=RequestMethod.GET)
//	@ApiOperation(value="收款单分页查询")
//	public AjaxInfo queryReceiptRecordList(@ModelAttribute ReceiptRecordVO vo, @ModelAttribute Page page){
//		AjaxInfo info = new AjaxInfo();
//		//判断查询条件是否和销售订单关联
//		vo=receiptRecordExportService.handleVOBeforeQuery(vo);
//		ExecuteResult<PageInfo<ReceiptRecordVO>> result = receiptRecordExportService.queryListByPage(vo, page);
//		if(CheckUtil.checkPageResultListIsNotNull(result)){
//			PageInfo<ReceiptRecordVO> pageInfo = result.getResult();
//			pageInfo.setList(receiptRecordExportService.handleReceiptRecordList(pageInfo.getList()));
//			result.setResult(pageInfo);
//		}
//		info = ResultUtil.getSelectPageListAjaxInfo(result);
//		return info;
//	}
//
//	/**
//	 *@Description 收款单数据导出
//	 *@author xupengfei
//	 *@Date 2019年4月10日
//	 * @param vo
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value="receiptExcel", method=RequestMethod.GET)
//	@ApiOperation(value="收款单数据导出")
//	public AjaxInfo queryReceiptRecordList(@ModelAttribute ReceiptRecordVO vo,HttpServletResponse response){
//		AjaxInfo info = new AjaxInfo();
//		//判断查询条件是否和销售订单关联
//		vo=receiptRecordExportService.handleVOBeforeQuery(vo);
//		ExecuteResult<List<ReceiptRecordVO>> result = receiptRecordExportService.queryList(vo);
//		if(CheckUtil.checkResultListIsNotNull(result)){
//			result.setResult(receiptRecordExportService.handleReceiptRecordList(result.getResult()));
//			String fileName = "收款单数据导出";
//			try {
//	    		//write：将数据输出到客户端（需要HttpServletResponse对象）
//				new ExportExcel(fileName,ReceiptRecordExcelVO.class, 1).setDataList(result.getResult()).write(response, fileName +".xlsx").dispose();
//	    	} catch (IOException e) {
//				info.setCode(AjaxInfoConstants.ERROR_CODE);
//				info.setMsg(e.toString());
//			}
//		}
//		return info;
//	}
//}

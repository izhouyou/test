//package com.camelot.order.api;
//
//import com.camelot.common.bean.AjaxInfo;
//import com.camelot.common.bean.ExecuteResult;
//import com.camelot.common.bean.Page;
//import com.camelot.order.common.utils.CheckUtil;
//import com.camelot.order.common.utils.ResultUtil;
//import com.camelot.order.common.utils.excel.ExportExcel;
//import com.camelot.order.export.service.ComplianceExportService;
//import com.camelot.order.export.vo.DifferenceOrderVO;
//import com.camelot.order.export.vo.ReturnGoodsVO;
//import com.camelot.order.export.vo.SameAgencyVO;
//import com.camelot.order.export.vo.excelvo.DifferenceOrderExcelVO;
//import com.camelot.order.export.vo.excelvo.SameAgencyExportVO;
//import com.camelot.order.export.vo.param.DifferenceParamVO;
//import com.camelot.order.export.vo.param.ReturnGoodsParamVO;
//import com.camelot.order.export.vo.param.SameNameParamVO;
//import com.github.pagehelper.PageInfo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
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
//@Api(value="合规", tags="合规", description="合规")
//@RequestMapping("compliance")
//public class ApiCompliance {
//	@Autowired
//	private ComplianceExportService service;
//
//	/**
//	 *@Description 对前端传值的String类型转换为Date类型
//	 */
//	@InitBinder
//    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//    }
//
//	/**
//	 * <p>Description: 零售差异订单查询接口</p>
//	 * @param paramVO 门店idStr/城市idStr/门店ids/起止时间/订单编号/差异金额
//	 * @param page 分页
//	 * @return 零售差异订单数据
//	 * @author zhouyou
//	 * @date 2019年5月14日
//	 */
//	@RequestMapping(value="order", method=RequestMethod.GET)
//	@ApiOperation(value="零售差异统计查询")
//	public AjaxInfo queryOrder(@ModelAttribute DifferenceParamVO paramVO, @ModelAttribute Page page) {
//		ExecuteResult<PageInfo<DifferenceOrderVO>> queryResult = service.compliancePageDifference(paramVO, page);
//		AjaxInfo result = ResultUtil.getSelectPageListAjaxInfo(queryResult);
//		return result;
//	}
//
//	/**
//	 * <p>Description: 零售差异订单导出接口</p>
//	 * @param response
//	 * @param paramVO 门店idStr/城市idStr/门店ids/起止时间/订单编号/差异金额
//	 * @author zhouyou
//	 * @date 2019年5月14日
//	 */
//	@RequestMapping(value="orderExcel", method=RequestMethod.GET)
//	@ApiOperation(value="零售差异统计导出")
//	public void exportOrder(HttpServletResponse response, @ModelAttribute DifferenceParamVO paramVO) {
//		ExecuteResult<List<DifferenceOrderVO>> queryResult = service.complianceListDifference(paramVO);
//		if (CheckUtil.checkResultListIsNotNull(queryResult)) {
//			String fileName = "零售差异统计导出";
//			try {
//	    		//writeFile:将导出数据输出到指定的文件
//	    		//write：将数据输出到客户端（需要HttpServletResponse对象）
//				new ExportExcel(fileName,DifferenceOrderExcelVO.class, 1).setDataList(queryResult.getResult()).write(response, fileName +".xlsx").dispose();
//	    	} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * <p>Description: 退货有序商品查询接口</p>
//	 * @param paramVO 门店idStr/城市idStr/商品名称/商品SN码/商品车架号
//	 * @param page 分页
//	 * @return 退货有序商品数据
//	 * @author zhouyou
//	 * @date 2019年5月14日
//	 */
//	@RequestMapping(value="returnGoods", method=RequestMethod.GET)
//	@ApiOperation(value="退货有序商品查询")
//	public AjaxInfo queryReturnGoods(@ModelAttribute ReturnGoodsParamVO paramVO, @ModelAttribute Page page) {
//		ExecuteResult<PageInfo<ReturnGoodsVO>> queryResult = service.complianceReturnGoods(paramVO, page);
//		AjaxInfo result = ResultUtil.getSelectPageListAjaxInfo(queryResult);
//		return result;
//	}
//
//	/**
//	 * <p>Description: 归属机构相同查询接口</p>
//	 * @param paramVO 城市idStr/销售编号/加盟商编号/公司名称/联系手机号
//	 * @param page 分页
//	 * @return 归属机构相同数据
//	 * @author zhouyou
//	 * @date 2019年5月14日
//	 */
//	@RequestMapping(value="sameName", method=RequestMethod.GET)
//	@ApiOperation(value="机构归属相同查询")
//	public AjaxInfo querySameName(@ModelAttribute SameNameParamVO paramVO, @ModelAttribute Page page) {
//		ExecuteResult<PageInfo<SameAgencyVO>> queryResult = service.complianceSameAgency(paramVO, page);
//		AjaxInfo result = ResultUtil.getSelectPageListAjaxInfo(queryResult);
//		return result;
//	}
//
//	@RequestMapping(value="sameNameExcel", method=RequestMethod.GET)
//	@ApiOperation(value="机构归属相同导出")
//	public AjaxInfo exportSameName(HttpServletResponse response, @ModelAttribute SameNameParamVO paramVO) {
//		AjaxInfo result = new AjaxInfo();
//		ExecuteResult<List<SameAgencyVO>> queryResult = service.exportSameName(paramVO);
//		if (CheckUtil.checkResultListIsNotNull(queryResult)) {
//			result = ResultUtil.getSelectListAjaxInfo(queryResult);
//			String fileName = "机构归属相同导出";
//			try {
//	    		//writeFile:将导出数据输出到指定的文件
//	    		//write：将数据输出到客户端（需要HttpServletResponse对象）
//				new ExportExcel(fileName,SameAgencyExportVO.class, 1).setDataList(queryResult.getResult()).write(response, fileName +".xlsx").dispose();
//	    	} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
//}

//package com.camelot.order.api;
//
//import com.camelot.common.bean.AjaxInfo;
//import com.camelot.common.bean.ExecuteResult;
//import com.camelot.common.bean.Page;
//import com.camelot.common.utils.BeanUtil;
//import com.camelot.order.common.Constants;
//import com.camelot.order.common.utils.*;
//import com.camelot.order.export.service.ReturnOrderExportService;
//import com.camelot.order.export.vo.ReturnOrderMobileVO;
//import com.camelot.order.export.vo.ReturnOrderVO;
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
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping("returnOrder")
//@Api(value="退货订单", tags="退货订单", description="退货订单")
//public class ApiReturnOrderController {
//
//	private static Logger log = Log.get(ApiReturnOrderController.class);
//
//	@Autowired
//	ReturnOrderExportService returnOrderExportService;
//
//	/**
//	 *@Description 对前端传值的String类型转换为Date类型
//	 *@author xupengfei
//	 *@Data 2019年4月8日
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
//	 * 移动端订单退货
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value="", method=RequestMethod.POST)
//	@ApiOperation(value="退单")
//	public AjaxInfo addReturnOrder(@RequestBody ReturnOrderVO vo){
//		AjaxInfo info = new AjaxInfo();
//		try{
//			ExecuteResult<String> result = returnOrderExportService.submitReturnOrder(vo);
//			info.setCode(Constants.SUCCESS_CODE.equals(result.getCode()) ? AjaxInfoConstants.SUCCESS_CODE : AjaxInfoConstants.ERROR_CODE);
//			info.setMsg(result.getResultMessage());
//			info.setData(result.getResult());
//		}catch(Exception e){
//			info.setCode(AjaxInfoConstants.ERROR_CODE);
//			info.setMsg(e.toString());
//			Log.error(log, "\n 方法[{}]，异常：[{}]", "ApiReturnOrderController-addReturnOrder", e.getMessage());
//		}
//		return info;
//	}
//
//	/**
//	 * 中台 退货订单列表详情查询--->>>>>改成大表处理
//	 * Tip:状态: 已退货
//	 *@Description 退货订单分页查询
//	 *@author xupengfei
//	 *@Data 2019年4月8日
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value="/page", method=RequestMethod.GET)
//	@ApiOperation(value="退货订单分页查询")
//	@SuppressWarnings("unchecked")
//	public AjaxInfo queryReturnOrderList(@ModelAttribute ReturnOrderVO vo,@ModelAttribute Page page){
//		AjaxInfo info = new AjaxInfo();
//		//判断是否需要从销售订单获取ID集合
//		vo=returnOrderExportService.handleVOBeforeQuery(vo);
//		ExecuteResult<PageInfo<ReturnOrderVO>> result = returnOrderExportService.queryListByPage(vo, page);
//		if(CheckUtil.checkPageResultListIsNotNull(result)){
//			PageInfo<ReturnOrderVO> pageInfo =result.getResult();
//			pageInfo.setList(returnOrderExportService.handleReturnOrderList(pageInfo.getList()));
//			result.setResult(pageInfo);
//		}
//		info=ResultUtil.getSelectPageListAjaxInfo(result);
//		return info;
//	}
//
//	/**
//	 *@Description 中台退单详情查询
//	 *@author xupengfei
//	 *@Data 2019年4月8日
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value="/{id}", method=RequestMethod.GET)
//	@ApiOperation(value="退单详情查询")
//	public AjaxInfo searchReturnOrderDetail(@PathVariable Long id){
//		AjaxInfo info = new AjaxInfo();
//		ExecuteResult<ReturnOrderVO> result = returnOrderExportService.queryById(id);
//		info = ResultUtil.getSelectAjaxInfo(result);
//		//处理结果,封装订单详情
//		if(CheckUtil.checkResultIsNotNull(result)){
//			info.setData(returnOrderExportService.handleReturnOrderDetail(result.getResult()));
//		}
//		return info;
//	}
//
//	/**
//	 *@Description 移动端退单分页查询
//	 *@author xupengfei
//	 *@Date 2019年4月9日
//	 * @param vo
//	 * @param page
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="mobile/page", method=RequestMethod.GET)
//	@ApiOperation(value="移动端退单分页查询")
//	public AjaxInfo queryMobileReturnOrderList(@ModelAttribute ReturnOrderVO vo,@ModelAttribute Page page){
//		AjaxInfo info = new AjaxInfo();
//		//门店ID不能为空(权限)
//		if(Utility.isEmpty(vo.getStoreId())){
//			info.setCode(AjaxInfoConstants.ERROR_CODE);
//			info.setMsg("门店ID不能为空");
//			return info;
//		}
//		//处理查询条件
//		vo=returnOrderExportService.handleMobileVOBeforeQuery(vo);
//		ExecuteResult<PageInfo<ReturnOrderVO>> result = returnOrderExportService.queryListByPage(vo, page);
//		ExecuteResult<PageInfo<ReturnOrderMobileVO>> mobileResult = new ExecuteResult<PageInfo<ReturnOrderMobileVO>>();
//		mobileResult.setCode(result.getCode());
//		mobileResult.setResultMessage(result.getResultMessage());
//		if(CheckUtil.checkPageResultListIsNotNull(result)){
//			PageInfo<ReturnOrderVO> pageInfo =result.getResult();
//			List<ReturnOrderVO> list = pageInfo.getList();
//			pageInfo.setList(null);
//			PageInfo<ReturnOrderMobileVO> mobilePageInfo = new PageInfo<ReturnOrderMobileVO>();
//			mobilePageInfo=BeanUtil.copyPropertes(mobilePageInfo.getClass(), pageInfo);
//			mobilePageInfo.setList(returnOrderExportService.handleMobileReturnOrderList(list));
//			mobileResult.setResult(mobilePageInfo);
//		}
//		info=ResultUtil.getSelectPageListAjaxInfo(mobileResult);
//		return info;
//	}
//
//}

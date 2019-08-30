//package com.camelot.order.api;
//
//import com.camelot.common.bean.AjaxInfo;
//import com.camelot.common.bean.ExecuteResult;
//import com.camelot.common.bean.Page;
//import com.camelot.common.utils.BeanUtil;
//import com.camelot.order.common.Constants;
//import com.camelot.order.common.OrderConstants;
//import com.camelot.order.common.utils.*;
//import com.camelot.order.export.service.SalesOrderExportService;
//import com.camelot.order.export.vo.OrderCustomerVO;
//import com.camelot.order.export.vo.SalesOrderMobileVO;
//import com.camelot.order.export.vo.SalesOrderVO;
//import com.camelot.order.export.vo.SalesOrderViewVO;
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
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping("salesOrder")
//@Api(value="销售订单", tags="销售订单", description="销售订单")
//public class ApiSalesOrderController {
//
//	private static Logger log = Log.get(ApiSalesOrderController.class);
//
//	@Autowired
//	SalesOrderExportService salesOrderExportService;
//
//
//	/**
//	 *@Description 对前端传值的String类型转换为Date类型
//	 *@author xupengfei
//	 *@Data 2019年4月5日
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
//	 * 中台订单列表查询--->>>>>改成大表处理
//	 * Tip:状态:订单状态:已提交/已取消/已完成/---退货状态:已退货/未退货----支付状态:已支付/未支付
//	 *@Description 销售订单分页查询
//	 *@author xupengfei
//	 *@Data 2019年4月5日
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value="/page", method=RequestMethod.GET)
//	@ApiOperation(value="销售订单分页查询")
//	public AjaxInfo querySalesOrderList(@ModelAttribute SalesOrderVO vo,@ModelAttribute Page page){
//		AjaxInfo info = new AjaxInfo();
//		//添加权限
//		vo=salesOrderExportService.handleDataPermission(vo);
//		//已完成的订单查询:已完成和已退货两种状态
//		if(Utility.isNotEmpty(vo.getSalesOrderStatus()) && OrderConstants.ORDER_STATUS_FINISH.equals(vo.getSalesOrderStatus())){
//			vo.setSalesOrderStatus(null);
//			List<Integer> statusList = new ArrayList<Integer>();
//			statusList.add(OrderConstants.ORDER_STATUS_FINISH);
//			statusList.add(OrderConstants.ORDER_STATUS_RETURN);
//			vo.setStatusList(statusList);
//		}
//		@SuppressWarnings("unchecked")
//		ExecuteResult<PageInfo<SalesOrderVO>> result = salesOrderExportService.queryListByPage(vo, page);
//		if(CheckUtil.checkPageResultListIsNotNull(result)){
//			PageInfo<SalesOrderVO> pageInfo =result.getResult();
//			pageInfo.setList(salesOrderExportService.handleSalesOrderList(pageInfo.getList()));
//			result.setResult(pageInfo);
//		}
//		info=ResultUtil.getSelectPageListAjaxInfo(result);
//		return info;
//	}
//
//
//	/**
//	 * 订单列表详情查询--->>>>>改成大表处理
//	 * Tip:状态:订单状态:已提交/已取消/已完成/---退货状态:已退货/未退货----支付状态:已支付/未支付
//	 *@Description 订单详情查询
//	 *@author xupengfei
//	 *@Data 2019年4月8日
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value="/{id}", method=RequestMethod.GET)
//	@ApiOperation(value="销售订单详情查询")
//	public AjaxInfo searchSalesOrderDetail(@PathVariable Long id){
//		AjaxInfo info = new AjaxInfo();
//		ExecuteResult<SalesOrderVO> result = salesOrderExportService.queryById(id);
//		info = ResultUtil.getSelectAjaxInfo(result);
//		//处理结果,封装订单详情
//		if(CheckUtil.checkResultIsNotNull(result)){
//			info.setData(salesOrderExportService.handleSalesOrderDetail(result.getResult()));
//		}
//		return info;
//	}
//
//	/**
//	 * 新增订单--现在改成大表数据
//	 *@Description 新增销售订单/消费者
//	 *@author xupengfei
//	 *@Data 2019年4月8日
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value="", method=RequestMethod.POST)
//	@ApiOperation(value="销售订单提交")
//	public AjaxInfo addSalesOrder(@RequestBody SalesOrderViewVO vo){
//		AjaxInfo info = new AjaxInfo();
//		ExecuteResult<String> result;
//		try {
//			result = salesOrderExportService.submitSalesOrder(vo);
//			info.setCode(Constants.SUCCESS_CODE.equals(result.getCode()) ? AjaxInfoConstants.SUCCESS_CODE : AjaxInfoConstants.ERROR_CODE);
//			info.setMsg(result.getResultMessage());
//			info.setData(result.getResult());
//		} catch (Exception e) {
//			info.setCode(AjaxInfoConstants.ERROR_CODE);
//			info.setMsg(e.toString());
//			Log.error(log, "\n 方法[{}]，异常：[{}]", "ApiSalesOrderController-addSalesOrder", e.getMessage());
//
//		}
//
//		return info;
//	}
//
//
//	/**
//	 * 取消订单--现在改成大表数据
//	 *@Description 取消订单
//	 *@author xupengfei
//	 *@Date 2019年4月9日
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value="", method=RequestMethod.PUT)
//	@ApiOperation(value="订单取消")
//	public AjaxInfo cancelSalesOrder(@RequestBody SalesOrderVO vo){
//		AjaxInfo info = new AjaxInfo();
//		try{
//			ExecuteResult<SalesOrderVO> result=salesOrderExportService.cancelSalesOrder(vo);
//			info = ResultUtil.getSelectAjaxInfo(result);
//		}catch(Exception e){
//			info.setCode(AjaxInfoConstants.ERROR_CODE);
//			info.setMsg(e.toString());
//			Log.error(log, "\n 方法[{}]，异常：[{}]", "ApiSalesOrderController-cancelSalesOrder", e.getMessage());
//
//		}
//		return info;
//	}
//
//	/**
//	 * 现在改成大表处理
//	 *@Description 移动端订单分页查询
//	 *@author xupengfei
//	 *@Date 2019年4月9日
//	 * @param vo
//	 * @param page
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="mobile/page", method=RequestMethod.GET)
//	@ApiOperation(value="移动端销售订单分页查询")
//	public AjaxInfo queryMobileSalesOrderList(@ModelAttribute SalesOrderVO vo,@ModelAttribute Page page){
//		AjaxInfo info = new AjaxInfo();
//		//对查询条件对象处理
//		vo=salesOrderExportService.handleMobileVOBeforeQuery(vo);
//		ExecuteResult<PageInfo<SalesOrderVO>> result = salesOrderExportService.queryListByPage(vo, page);
//		ExecuteResult<PageInfo<SalesOrderMobileVO>> mobileResult = new ExecuteResult<PageInfo<SalesOrderMobileVO>>();
//		mobileResult.setCode(result.getCode());
//		mobileResult.setResultMessage(result.getResultMessage());
//		if(CheckUtil.checkPageResultListIsNotNull(result)){
//			PageInfo<SalesOrderVO> pageInfo =result.getResult();
//			List<SalesOrderVO> list = pageInfo.getList();
//			pageInfo.setList(null);
//			PageInfo<SalesOrderMobileVO> mobilePageInfo = new PageInfo<SalesOrderMobileVO>();
//			mobilePageInfo=BeanUtil.copyPropertes(mobilePageInfo.getClass(), pageInfo);
//			mobilePageInfo.setList(salesOrderExportService.handleMobileSalesOrderList(list));
//			mobileResult.setResult(mobilePageInfo);
//		}
//		info=ResultUtil.getSelectPageListAjaxInfo(mobileResult);
//		return info;
//	}
//
////	/**
////	 * 根据活动id查询数据
////	 * @param id
////	 * @return
////	 */
////	@RequestMapping(value = "activityId",method = RequestMethod.GET)
////	public AjaxInfo queryByActivityId(@RequestParam Integer id){
////	   SalesOrderVO vo = new SalesOrderVO();
////	   vo.setActivityId(id);
////	   ExecuteResult<List<SalesOrderVO>> result = salesOrderExportService.queryList(vo);
////	   if (CheckUtil.checkResultListIsNotNull(result)){
////	      // PageInfo<SalesOrderVO> pageInfo =result.getResult();
////	      // pageInfo.setList(salesOrderExportService.handleSalesOrderList(pageInfo.getList()));
////	      List<SalesOrderVO> salesOrderVOList = salesOrderExportService.handleSalesOrderList(result.getResult());
////	      result.setResult(salesOrderVOList);
////	   }
////	   AjaxInfo info=ResultUtil.getSelectListAjaxInfo(result);
////	   return info;
////	}
////
////	/**
////	 * 根据优惠码id查询订单数据
////	 * @param id
////	 * @return
////	 */
////	@RequestMapping(value = "codeId",method = RequestMethod.GET)
////	public AjaxInfo queryByCodeId(@RequestParam Integer id){
////	   SalesOrderVO vo = new SalesOrderVO();
////	   vo.setCouponId(id);
////	   AjaxInfo info = new AjaxInfo();
////	   ExecuteResult<List<SalesOrderVO>> result = salesOrderExportService.queryList(vo);
////	   if (CheckUtil.checkResultListIsNotNull(result)){
////	      info.setData(result.getResult().get(0));
////	   }
////	   return info;
////	}
//
//	/**
//	 *@Description 查询消费者相关订单数量和金额
//	 *@author xupengfei
//	 *@Date 2019年4月20日
//	 * @param ids
//	 * @return
//	 */
//	@RequestMapping(value = "statisticsWithCustomer",method = RequestMethod.GET)
//	public AjaxInfo statisticSalesOrder(@RequestParam String ids){
//		AjaxInfo info = new AjaxInfo();
//		ExecuteResult<List<OrderCustomerVO>> result = salesOrderExportService.statisticOrderWithCustomer(ids);
//		info=ResultUtil.getSelectListAjaxInfo(result);
//		return info;
//	}
//
//
////	/**
////	 *@Description 活动模块接口:由核销时间查询订单信息
////	 *@author gaosongchao
////	 *@Date 2019年4月21日
////	 * @param modifyDate
////	 * @return
////	 */
////	@RequestMapping(value = "modifyDate",method = RequestMethod.GET)
////	public AjaxInfo queryByModifyDate(@RequestParam Date modifyDate){
////	   SalesOrderVO vo = new SalesOrderVO();
////	   vo.setModifyDate(modifyDate);
////	   AjaxInfo info = new AjaxInfo();
////	   ExecuteResult<List<SalesOrderVO>> result = salesOrderExportService.queryList(vo);
////	   if (CheckUtil.checkResultListIsNotNull(result)){
////	      info = ResultUtil.getSelectListAjaxInfo(result);
////	   }
////	   return info;
////	}
//
//	/**
//	 * 活动调用订单接口
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value = "activityorder",method = RequestMethod.GET)
//	public AjaxInfo queryByActivityId(@ModelAttribute SalesOrderVO vo){
//	   //权限处理
//	   vo=salesOrderExportService.handleDataPermission(vo);
//	   ExecuteResult<List<SalesOrderVO>> result = salesOrderExportService.queryList(vo);
//	   if (CheckUtil.checkResultListIsNotNull(result)){
//	      // PageInfo<SalesOrderVO> pageInfo =result.getResult();
//	      // pageInfo.setList(salesOrderExportService.handleSalesOrderList(pageInfo.getList()));
//	      List<SalesOrderVO> salesOrderVOList = salesOrderExportService.handleSalesOrderList(result.getResult());
//	      result.setResult(salesOrderVOList);
//	   }
//	   AjaxInfo info=ResultUtil.getSelectListAjaxInfo(result);
//	   return info;
//	}
//
//
//}

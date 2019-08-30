//package com.camelot.order.api;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.camelot.common.bean.AjaxInfo;
//import com.camelot.common.bean.ExecuteResult;
//import com.camelot.order.common.Constants;
//import com.camelot.order.common.utils.AjaxInfoConstants;
//import com.camelot.order.common.utils.CheckUtil;
//import com.camelot.order.common.utils.ResultUtil;
//import com.camelot.order.export.service.OrderGoodsExportService;
//import com.camelot.order.export.vo.OrderGoodsVO;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("orderWithGoods")
//@Api(value="订单商品关联", tags="订单商品关联", description="订单商品关联")
//public class ApiOrderGoodsController {
//
//	@Autowired
//	OrderGoodsExportService orderGoodsExportService;
//
//	@RequestMapping(value="", method=RequestMethod.GET)
//	@ApiOperation(value="条件查询商品订单信息")
//	public AjaxInfo queryOrderGoods(@ModelAttribute OrderGoodsVO vo){
//		AjaxInfo info = new AjaxInfo();
//		ExecuteResult<List<OrderGoodsVO>> result = orderGoodsExportService.queryList(vo);
//		if(CheckUtil.checkResultListIsNotNull(result)){
//			ExecuteResult<List<OrderGoodsVO>> handleResult = orderGoodsExportService.handleOrderGoods(result.getResult());
//			info = ResultUtil.getSelectAjaxInfo(handleResult);
//		}else{
//			info = ResultUtil.getSelectListAjaxInfo(result);
//		}
//		return info;
//
//	}
//}

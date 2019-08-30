//package com.camelot.order.api;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.web.bind.ServletRequestDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.camelot.common.bean.AjaxInfo;
//import com.camelot.common.bean.ExecuteResult;
//import com.camelot.common.bean.Page;
//import com.camelot.order.common.utils.CheckUtil;
//import com.camelot.order.common.utils.ResultUtil;
//import com.camelot.order.export.service.NotBuyExportService;
//import com.camelot.order.export.vo.NotBuyVO;
//import com.github.pagehelper.PageInfo;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("notBuy")
//@Api(value="未购买上报", tags="未购买上报", description="")
//public class ApiNotBuyController {
//
//	@Autowired
//	NotBuyExportService notBuyExportService;
//
//
//	/**
//	 *@Description 对前端传值的String类型转换为Date类型
//	 *@author xupengfei
//	 *@Data 2019年4月19日
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
//	 *@Description 新增未购买上报
//	 *@author xupengfei
//	 *@Date 2019年4月9日
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value="", method=RequestMethod.POST)
//	@ApiOperation(value="新增未购买上报")
//	public AjaxInfo addNotBuy(@RequestBody NotBuyVO vo){
//		AjaxInfo info = new AjaxInfo();
//		ExecuteResult<NotBuyVO> result = notBuyExportService.submitNotBuy(vo);
//		info = ResultUtil.getSelectAjaxInfo(result);
//		return info;
//
//	}
//
//	/**
//	 *@Description 未购买上报分页查询
//	 *@author xupengfei
//	 *@Date 2019年4月9日
//	 * @param vo
//	 * @param page
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="page", method=RequestMethod.GET)
//	@ApiOperation(value="未购买上报分页查询")
//	public AjaxInfo queryNotBuyByPage(@ModelAttribute NotBuyVO vo,@ModelAttribute Page page){
//		AjaxInfo info = new AjaxInfo();
//		ExecuteResult<PageInfo<NotBuyVO>> result = notBuyExportService.queryListByPage(vo, page);
//		if(CheckUtil.checkResultIsNotNull(result)){
//			PageInfo<NotBuyVO> pageInfo = result.getResult();
//			pageInfo.setList((notBuyExportService.handleNotBuyList(result.getResult().getList())));
//			result.setResult(pageInfo);
//		}
//		info=ResultUtil.getSelectAjaxInfo(result);
//		return info;
//	}
//
//
//}

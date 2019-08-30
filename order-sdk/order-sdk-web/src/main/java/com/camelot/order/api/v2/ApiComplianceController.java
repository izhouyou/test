package com.camelot.order.api.v2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.api.NewApiComplianceController;
import com.camelot.order.common.utils.CheckUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.ResultUtil;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.common.utils.excel.ExportExcel;
import com.camelot.order.config.checkauthorise.UserAuthoriseController;
import com.camelot.order.export.service.TransregionalInfoExportService;
import com.camelot.order.export.snapshot.TransregionalInfoVO;
import com.camelot.order.export.vo.excelvo.TransregionalInfoExportVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Api(value="订单-窜货订单", tags="订单-窜货订单", description="订单-窜货订单")
@RequestMapping("v2/compliance")
public class ApiComplianceController {
	
	private static Logger log = Log.get(NewApiComplianceController.class);
	
	@Autowired
	private TransregionalInfoExportService transregionalInfoExportService;
	
	/**
	 * <p>Title: init</p>
	 * <p>Description: 对前端传值的String类型转换为Date类型</p>
	 * @param request
	 * @param binder
	 * @author zhouyou
	 * @date 2019年6月20日
	 */
	@InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
	
	/**
	 * <p>Title: queryPagePickup</p>
	 * <p>Description: 窜货订单追踪查询接口</p>
	 * @param vo
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年6月21日
	 */
	@RequestMapping(value="page-pickup", method=RequestMethod.GET)
	@ApiOperation(value="窜货订单追踪查询接口(分页)")
	@UserAuthoriseController
	public AjaxInfo queryPagePickup(@ModelAttribute TransregionalInfoVO vo, @ModelAttribute Page page) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ApiComplianceController-queryPagePickup",  JSONObject.toJSONString(vo));
        ExecuteResult<PageInfo<TransregionalInfoVO>> executeResult = transregionalInfoExportService.queryListByPage(vo, page);
        PageInfo<TransregionalInfoVO> pageInfo = executeResult.getResult();
		if(Utility.isNotEmpty(pageInfo)) {
			List<TransregionalInfoVO> list = pageInfo.getList();
			pageInfo.setList(transregionalInfoExportService.handleTransregionalInfoList(list));
			executeResult.setResult(pageInfo);
		}
        AjaxInfo result = ResultUtil.getSelectPageListAjaxInfo(executeResult);
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ApiComplianceController-queryPagePickup",  JSONObject.toJSONString(result));
        return result;
	}
	
	/**
	 * <p>Title: queryListPickup</p>
	 * <p>Description: 窜货订单追踪查询接口(未分页)</p>
	 * @param vo
	 * @return
	 * @author zhouyou
	 * @date 2019年6月21日
	 */
	@RequestMapping(value="list-pickup", method=RequestMethod.GET)
	@ApiOperation(value="窜货订单追踪查询接口(未分页)")
	@UserAuthoriseController
	public AjaxInfo queryListPickup(@ModelAttribute TransregionalInfoVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ApiComplianceController-queryListPickup",  JSONObject.toJSONString(vo));
        ExecuteResult<List<TransregionalInfoVO>> executeResult = transregionalInfoExportService.queryList(vo);
        List<TransregionalInfoVO> list = executeResult.getResult();
        if(Utility.isNotEmpty(list)) {
        	executeResult.setResult(transregionalInfoExportService.handleTransregionalInfoList(list));
        }
        AjaxInfo result = ResultUtil.getSelectListAjaxInfo(executeResult);
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ApiComplianceController-queryListPickup",  JSONObject.toJSONString(result));
        return result;
	}
	
	/**
	 * <p>Title: addPickup</p>
	 * <p>Description: 窜货订单追踪添加接口</p>
	 * @param vo
	 * @return
	 * @author zhouyou
	 * @date 2019年6月21日
	 */
	@RequestMapping(value="add-pickup", method=RequestMethod.POST)
	@ApiOperation(value="窜货订单追踪添加接口")
	public AjaxInfo addPickup(@ModelAttribute TransregionalInfoVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ApiComplianceController-addPickup",  JSONObject.toJSONString(vo));
        ExecuteResult<TransregionalInfoVO> executeResult = transregionalInfoExportService.addTransregionalInfo(vo);
        AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ApiComplianceController-addPickup",  JSONObject.toJSONString(result));
        return result;
	}
	
	/**
	 * <p>Title: updatePickup</p>
	 * <p>Description: 窜货订单追踪修改接口</p>
	 * @param vo
	 * @return
	 * @author zhouyou
	 * @date 2019年6月21日
	 */
	@RequestMapping(value="update-pickup", method=RequestMethod.POST)
	@ApiOperation(value="窜货订单追踪修改接口")
	public AjaxInfo updatePickup(@ModelAttribute TransregionalInfoVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "ApiComplianceController-updatePickup",  JSONObject.toJSONString(vo));
        ExecuteResult<TransregionalInfoVO> executeResult = new ExecuteResult<>();
		try {
			executeResult = transregionalInfoExportService.update(vo);
		} catch (Exception e) {
			executeResult.setCode("-1");
			executeResult.setResultMessage(e.toString());
		}
        AjaxInfo result = ResultUtil.getSelectAjaxInfo(executeResult);
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "ApiComplianceController-updatePickup",  JSONObject.toJSONString(result));
        return result;
	}
	
	
	/**
	 * <p>Title: exportGrade</p>
	 * <p>Description: 窜货订单追踪导出接口</p>
	 * @param vo
	 * @param response
	 * @author zhouyou
	 * @date 2019年6月24日
	 */
	@RequestMapping(value="excel", method = RequestMethod.GET)
	@ApiOperation(value="窜货订单追踪导出接口")
	@UserAuthoriseController
    public void exportGrade(@ModelAttribute TransregionalInfoVO vo, HttpServletResponse response) {
    	try {
			ExecuteResult<List<TransregionalInfoVO>> executeResult = transregionalInfoExportService.queryList(vo);
			List<TransregionalInfoVO> list = executeResult.getResult();
		    if(Utility.isNotEmpty(list)) {
	        	executeResult.setResult(transregionalInfoExportService.handleTransregionalInfoList(list));
				String fileName = "窜货订单追踪导出";
				try {
		    		//writeFile:将导出数据输出到指定的文件
		    		//write：将数据输出到客户端（需要HttpServletResponse对象）
					new ExportExcel(fileName,TransregionalInfoExportVO.class, 1).setDataList(executeResult.getResult()).write(response, fileName +".xlsx").dispose();
		    	} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}

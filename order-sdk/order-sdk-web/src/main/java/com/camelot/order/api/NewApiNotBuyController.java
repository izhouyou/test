package com.camelot.order.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.ResultUtil;
import com.camelot.order.config.checkauthorise.UserAuthoriseController;
import com.camelot.order.config.elasticConfig.SystemControllerLog;
import com.camelot.order.export.service.NewNotBuyExportService;
import com.camelot.order.export.snapshot.NewNotBuyVO;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/notBuy")
@Api(value="未购买上报", tags="未购买上报", description="")
public class NewApiNotBuyController {

    private static Logger log = Log.get(NewApiNotBuyController.class);

    @Autowired
    private NewNotBuyExportService newNotBuyExportService;

    /**
     * @Description [ 对前端传值的String类型转换为Date类型 ]
     * @Author [hudyang]
     * @Date 2019/5/16 14:39
     * @Param
     * @return
     **/
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    /**
     * @Description [ 新增未购买上报 ]
     * @Author [hudyang]
     * @Date 2019/5/16 16:33
     * @Param
     * @return
    **/
    @SystemControllerLog(description = "新增未购买上报数据")
    @RequestMapping(value="", method= RequestMethod.POST)
    @ApiOperation(value="新增未购买上报")
    public AjaxInfo addNotBuy(@RequestBody NewNotBuyVO vo){
        ExecuteResult<NewNotBuyVO> result = newNotBuyExportService.addNotBuy(vo);
        AjaxInfo info = ResultUtil.getSelectAjaxInfo(result);
        return info;

    }

    /**
     * @Description [ 未购买上报分页查询 ]
     * @Author [hudyang]
     * @Date 2019/5/16 16:34
     * @Param
     * @return
    **/
    @UserAuthoriseController
    @RequestMapping(value="page", method=RequestMethod.GET)
    @ApiOperation(value="未购买上报分页查询")
    public AjaxInfo queryNotBuyByPage(@ModelAttribute NewNotBuyVO vo,@ModelAttribute Page page){
        ExecuteResult<PageInfo<NewNotBuyVO>> result = newNotBuyExportService.queryNotBuyByPage(vo, page);
        AjaxInfo info = ResultUtil.getSelectPageListAjaxInfo(result);
        return info;
    }




}

package com.camelot.order.api;


import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.ResultUtil;
import com.camelot.order.config.checkauthorise.UserAuthoriseController;
import com.camelot.order.config.elasticConfig.SystemControllerLog;
import com.camelot.order.export.service.NewReceiptRecordExportService;
import com.camelot.order.export.snapshot.NewReceiptRecordVO;
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

@RestController
@RequestMapping("api/receiptRecord")
@Api(value="收款记录", tags="收款记录", description="")
public class NewApiReceiptRecordController {

    private static Logger log = Log.get(NewApiReceiptRecordController.class);

    @Autowired
    private NewReceiptRecordExportService newReceiptRecordExportService;

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
     * @Description [ 新增收款单 ]
     * @Author [hudyang]
     * @Date 2019/5/16 16:06 
     * @Param
     * @return 
    **/
    @RequestMapping(value="", method= RequestMethod.POST)
    @ApiOperation(value="新增收款单")
    @SystemControllerLog(description = "新增收款单数据")
    public AjaxInfo addReceiptRecord(@RequestBody NewReceiptRecordVO vo){
        ExecuteResult<NewReceiptRecordVO> result = newReceiptRecordExportService.addReceiptRecord(vo);
        AjaxInfo info = ResultUtil.getSelectAjaxInfo(result);
        return info;
    }

    /**
     * @Description [ 收款单分页查询 ]
     * @Author [hudyang]
     * @Date 2019/5/16 16:06
     * @Param
     * @return
    **/
    @UserAuthoriseController
    @RequestMapping(value="page", method=RequestMethod.GET)
    @ApiOperation(value="收款单分页查询")
    public AjaxInfo queryReceiptRecordList(@ModelAttribute NewReceiptRecordVO vo, @ModelAttribute Page<NewReceiptRecordVO> page){
        ExecuteResult<PageInfo<NewReceiptRecordVO>> result = newReceiptRecordExportService.queryReceiptRecordList(vo,page);
        AjaxInfo info = ResultUtil.getSelectPageListAjaxInfo(result);
        return info;
    }

}

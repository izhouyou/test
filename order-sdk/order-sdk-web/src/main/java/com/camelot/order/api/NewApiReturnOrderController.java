package com.camelot.order.api;

import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.ResultUtil;
import com.camelot.order.config.checkauthorise.UserAuthoriseController;
import com.camelot.order.config.elasticConfig.SystemControllerLog;
import com.camelot.order.export.service.NewReturnOrderExportService;
import com.camelot.order.export.snapshot.NewReturnOrderVO;
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
@RequestMapping("api/returnOrder")
@Api(value="退货订单", tags="退货订单", description="退货订单")
public class NewApiReturnOrderController {


    private static Logger log = Log.get(NewApiReturnOrderController.class);

    @Autowired
    private NewReturnOrderExportService newReturnOrderExportService;

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
     * @Description [ 移动端订单退货 ]
     * @Author [hudyang]
     * @Date 2019/5/16 15:59
     * @Param
     * @return
    **/
    @RequestMapping(value="", method= RequestMethod.POST)
    @ApiOperation(value = "退货订单")
    @SystemControllerLog(description = "新增退货订单数据")
    public AjaxInfo addReturnOrder(@RequestBody NewReturnOrderVO vo){
        ExecuteResult<String> result = newReturnOrderExportService.submitReturnOrder(vo);
        AjaxInfo info = ResultUtil.getSelectAjaxInfo(result);
        return info;
    }

    /**  
     * @Description [ 退货订单分页查询 ]
     * @Author [hudyang]
     * @Date 2019/5/16 16:00 
     * @Param
     * @return 
    **/
    @UserAuthoriseController
    @RequestMapping(value="/page", method=RequestMethod.GET)
    @ApiOperation(value="退货订单分页查询")
    public AjaxInfo queryReturnOrderListPage(@ModelAttribute NewReturnOrderVO vo,@ModelAttribute Page page){
        ExecuteResult<PageInfo<NewReturnOrderVO>> result = newReturnOrderExportService.queryReturnOrderListPage(vo,page);
        AjaxInfo info = ResultUtil.getSelectPageListAjaxInfo(result);
        return info;
    }

    /**
     * @Description [ 退单详情查询 ]
     * @Author [hudyang]
     * @Date 2019/5/16 16:02
     * @Param
     * @return
    **/
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ApiOperation(value="退单详情查询")
    public AjaxInfo searchReturnOrderDetail(@PathVariable Long id){
        ExecuteResult<NewReturnOrderVO> result = newReturnOrderExportService.searchReturnOrderDetail(id);
        AjaxInfo info = ResultUtil.getSelectAjaxInfo(result);
        return info;
    }






}

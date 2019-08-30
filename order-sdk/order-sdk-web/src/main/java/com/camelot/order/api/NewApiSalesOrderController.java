package com.camelot.order.api;

import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.ResultUtil;
import com.camelot.order.config.checkauthorise.UserAuthoriseController;
import com.camelot.order.config.elasticConfig.SystemControllerLog;
import com.camelot.order.export.service.NewSalesOrderExportService;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
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
@RequestMapping("api/salesOrder")
@Api(value="销售订单", tags="销售订单", description="销售订单")
public class NewApiSalesOrderController {

    private static Logger log = Log.get(NewApiSalesOrderController.class);


    @Autowired
    private NewSalesOrderExportService newSalesOrderExportService;

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
     * 订单  销售订单状态 字典表30     已提交108      已取消110   已完成111
     * 订单  支付收款单状态 字典表28   未支付103      支付中      支付失败     已支付102
     * 订单  退货订单状态 字典表35     未退货226      已退货227
     * @Description [ 销售订单分页查询  ]
     * @Author [hudyang]
     * @Date 2019/5/16 15:30
     * @Param
     * @return
     **/
    @RequestMapping(value="/page", method= RequestMethod.GET)
    @ApiOperation(value="销售订单分页查询")
    @UserAuthoriseController
    public AjaxInfo querySalesOrderList(@ModelAttribute NewSalesOrderVO vo, @ModelAttribute Page<NewSalesOrderVO> page){
        ExecuteResult<PageInfo<NewSalesOrderVO>> result = newSalesOrderExportService.querySalesOrderList(vo,page);
        AjaxInfo info = ResultUtil.getSelectPageListAjaxInfo(result);
        return info;
    }

    /**
     * @Description [ 销售订单详情查询 ]
     * @Author [hudyang]
     * @Date 2019/5/16 15:31
     * @Param
     * @return
     **/
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ApiOperation(value="销售订单详情查询")
    public AjaxInfo searchSalesOrderDetail(@PathVariable Long id){
        ExecuteResult<NewSalesOrderVO> result = newSalesOrderExportService.searchSalesOrderDetail(id);
        AjaxInfo info = ResultUtil.getSelectAjaxInfo(result);
        return info;
    }

    /**
     * @Description [ 销售订单提交 ]
     * @Author [hudyang]
     * @Date 2019/5/16 15:32
     * @Param
     * @return
     **/
    @RequestMapping(value="", method=RequestMethod.POST)
    @ApiOperation(value="销售订单提交")
    @SystemControllerLog(description = "销售订单提交操作")
    public AjaxInfo addSalesOrder(@RequestBody NewSalesOrderVO vo){
        ExecuteResult<NewSalesOrderVO> result = newSalesOrderExportService.addSalesOrder(vo);
        AjaxInfo info = ResultUtil.getSelectAjaxInfo(result);
        return info;
    }

    /**
     * @Description [ 订单取消/修改订单状态 ]
     * @Author [hudyang]
     * @Date 2019/5/16 15:33
     * @Param
     * @return
     **/
    @RequestMapping(value="", method=RequestMethod.PUT)
    @ApiOperation(value="订单取消")
    @SystemControllerLog(description = "订单取消操作")
    public AjaxInfo cancelSalesOrder(@RequestBody NewSalesOrderVO vo){
        ExecuteResult<NewSalesOrderVO> result = newSalesOrderExportService.cancelSalesOrder(vo);
        AjaxInfo info = ResultUtil.getSelectAjaxInfo(result);
        return info;
    }
    
    /**
     * <p>Title: queryList</p>
     * <p>Description: 查询销售订单list集合</p>
     * @param vo
     * @return
     * @author zhouyou
     * @date 2019年5月29日
     */
    @UserAuthoriseController
    @RequestMapping(value="/activityorder", method= RequestMethod.GET)
    @ApiOperation(value="销售订单查询")
    public AjaxInfo queryList(@ModelAttribute NewSalesOrderVO vo){
        ExecuteResult<List<NewSalesOrderVO>> result = newSalesOrderExportService.queryList(vo);
        AjaxInfo info = ResultUtil.getSelectListAjaxInfo(result);
        return info;
    }

    /**
     * @Description [ 查询数据金额不符合 ]
     * @Author [hudyang]
     * @Date 2019/6/26 15:24
     * @Param
     * @return
    **/
    @UserAuthoriseController
    @RequestMapping(value="/amount", method= RequestMethod.GET)
    @ApiOperation(value="销售订单查询")
    public AjaxInfo queryamountList(@ModelAttribute NewSalesOrderVO vo){
        ExecuteResult<List<NewSalesOrderVO>> result = newSalesOrderExportService.queryamountList(vo);
        AjaxInfo info = ResultUtil.getSelectListAjaxInfo(result);
        return info;
    }
    /**
     * @Description [ 修改不对的金额 ]
     * @Author [hudyang]
     * @Date 2019/6/26 15:52
     * @Param
     * @return
    **/
    @RequestMapping(value="/amount/update", method= RequestMethod.GET)
    @ApiOperation(value="销售订单查询")
    @SystemControllerLog(description = "修改不对的金额操作")
    public AjaxInfo updateamountList(@ModelAttribute NewSalesOrderVO vo){
        ExecuteResult<List<NewSalesOrderVO>> result = newSalesOrderExportService.updateamountList(vo);
        AjaxInfo info = ResultUtil.getSelectListAjaxInfo(result);
        return info;
    }


}

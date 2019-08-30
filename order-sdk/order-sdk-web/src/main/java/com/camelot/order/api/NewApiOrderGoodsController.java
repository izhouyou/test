package com.camelot.order.api;

import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.utils.Log;
import com.camelot.order.export.service.NewSalesOrderGoodsExportService;
import com.camelot.order.export.snapshot.NewSalesOrderGoodsVO;
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
@RequestMapping("api/orderWithGoods")
@Api(value="订单商品关联", tags="订单商品关联", description="订单商品关联")
public class NewApiOrderGoodsController {


    private static Logger log = Log.get(NewApiOrderGoodsController.class);

    @Autowired
    private NewSalesOrderGoodsExportService newSalesOrderGoodsExportService;

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
     * @Description [ 条件查询订单商品信息--toGoodsServer ]
     * @Author [hudyang]
     * @Date 2019/5/16 16:12
     * @Param
     * @return
    **/
    @RequestMapping(value="", method= RequestMethod.GET)
    @ApiOperation(value="条件查询订单商品信息")
    public AjaxInfo queryOrderGoods(@ModelAttribute NewSalesOrderGoodsVO vo){
        AjaxInfo info = new AjaxInfo();
        ExecuteResult<NewSalesOrderGoodsVO> result = newSalesOrderGoodsExportService.verfyOrderGoods(vo);
        info.setCode(Integer.valueOf(result.getCode()));
        info.setMsg(result.getResultMessage());
        info.setData(result.getResult());
        return info;
    }


}

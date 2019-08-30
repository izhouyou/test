package com.camelot.order.api;

import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.ResultUtil;
import com.camelot.order.export.service.NewSalesOrderLogExportService;
import com.camelot.order.export.snapshot.NewSalesOrderLogVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("api/salesOrderLog")
@Api(value = "订单日志", tags = "销售日志", description = "销售日志")
public class NewApiSalesOrderLogController {

    private static Logger log = Log.get(NewApiSalesOrderController.class);


    @Autowired
    private NewSalesOrderLogExportService newSalesOrderLogExportService;

    /**
     * @return
     * @Description [ 对前端传值的String类型转换为Date类型 ]
     * @Author [hudyang]
     * @Date 2019/5/16 14:39
     * @Param
     **/
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    /**
     * @return
     * @Description [ 查询 ]
     * @Author [hudyang]
     * @Date 2019/6/1 11:22
     * @Param
     **/
    public AjaxInfo querySalesOrderLogPage(NewSalesOrderLogVO vo, Page<NewSalesOrderLogVO> page) {
        ExecuteResult<PageInfo<NewSalesOrderLogVO>> result = newSalesOrderLogExportService.querySalesOrderLogPage(vo, page);
        AjaxInfo info = ResultUtil.getSelectPageListAjaxInfo(result);
        return info;
    }

}

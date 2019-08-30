package com.camelot.order.api;


import com.camelot.common.bean.AjaxInfo;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.ResultUtil;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.common.utils.excel.ExportExcel;
import com.camelot.order.config.checkauthorise.UserAuthoriseController;
import com.camelot.order.config.elasticConfig.SystemControllerLog;
import com.camelot.order.export.CustomerExportService;
import com.camelot.order.export.snapshot.CustomerVO;
import com.camelot.order.export.vo.excelvo.CustomerExcelVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/customer")
@Api(value = "消费者信息", tags = "消费者信息", description = "消费者信息")
public class NewCustomerController {


    private static Logger log = Log.get(NewCustomerController.class);

    @Autowired
    private CustomerExportService customerExportService;

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
     * @Description [ 消费者信息分页列表 ]
     * @Author [hudyang]
     * @Date 2019/5/16 16:19
     * @Param
     **/
    @UserAuthoriseController
    @RequestMapping(value = "page", method = RequestMethod.GET)
    @ApiOperation(value = "消费者信息分页列表", tags = {"json请求数据"}, notes = "")
    public AjaxInfo queryCustomerPage(@ModelAttribute CustomerVO vo, @ModelAttribute Page<CustomerVO> page) {
        ExecuteResult<PageInfo<CustomerVO>> result = customerExportService.queryCustomerPage(vo, page);
        AjaxInfo info = ResultUtil.getSelectAjaxInfo(result);
        return info;
    }
    /**
     * @return
     * @Description [ 添加消费者 ]
     * @Author [hudyang]
     * @Date 2019/5/16 16:19
     * @Param
     **/
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "消费者信息添加", tags = {"json请求数据"}, notes = "")
    @SystemControllerLog(description = "消费者信息添加操作")
    public AjaxInfo addCustomerPage(@RequestBody CustomerVO vo) {
        ExecuteResult<CustomerVO> result = customerExportService.addCustomer(vo);
        AjaxInfo info = ResultUtil.getSelectAjaxInfo(result);
        return info;
    }

    /**
     * @return
     * @Description [ 消费者列表 ]
     * @Author [hudyang]
     * @Date 2019/5/19 19:17
     * @Param
     **/
    @UserAuthoriseController
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ApiOperation(value = "消费者列表", tags = {"json请求数据"}, notes = "")
    public AjaxInfo queryCustomerList(@ModelAttribute CustomerVO vo) {
        ExecuteResult<List<CustomerVO>> result = customerExportService.queryList(vo);
        AjaxInfo info = ResultUtil.getSelectListAjaxInfo(result);
        return info;
    }

    /**
     * @return
     * @Description [ 消费者信息导出 ]
     * @Author [hudyang]
     * @Date 2019/5/16 16:18
     * @Param
     **/
    @UserAuthoriseController
    @RequestMapping(value = "excel", method = RequestMethod.GET)
    public void exportOrderData(@ModelAttribute CustomerVO vo, HttpServletResponse response) {
        try {
            ExecuteResult<List<CustomerVO>> queryListResult = customerExportService.queryList(vo);
            if (Utility.isNotEmpty(queryListResult.getResult())) {
                List<CustomerVO> customerList = queryListResult.getResult();
                ExecuteResult<List<CustomerExcelVO>> excelResult = customerExportService.exportList(customerList);
                if (Utility.isNotEmpty(excelResult.getResult())) {
                    String fileName = "消费者信息导出";
                    //writeFile:将导出数据输出到指定的文件
                    //write：将数据输出到客户端（需要HttpServletResponse对象）
                    new ExportExcel(fileName, CustomerExcelVO.class, 1).setDataList(excelResult.getResult()).write(response, fileName + ".xlsx").dispose();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

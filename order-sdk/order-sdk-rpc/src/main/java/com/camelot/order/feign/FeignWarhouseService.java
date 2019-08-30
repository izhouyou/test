package com.camelot.order.feign;

import com.camelot.common.bean.AjaxInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "sysbase-sdk")
//@FeignClient(name = "sysbase-sdk", url = "172.16.1.22:9991")
public interface FeignWarhouseService {

    /**
     * @Description [ 查询仓库信息 ]
     * @Author [hudyang]
     * @Date 2019/7/5 15:28
     * @Param
     * @return
    **/
    @GetMapping("warehouse/list")
    public AjaxInfo queryWarhouse(@RequestParam Map<String, Object> map);


}

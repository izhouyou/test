package com.camelot.order.feign;

import com.camelot.common.bean.AjaxInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "stock-sdk")
//@FeignClient(name = "stock-sdk", url = "172.16.1.22:8881")
public interface FeignBoundService {

    /**
     * @return
     * @Description [ 出库 ]
     * @Author [hudyang]
     * @Date 2019/6/26 18:17
     * @Param
     **/
    @PostMapping("outbound/add-outbound")
    public AjaxInfo addOutBound(@RequestBody Map<String, Object> map) throws Exception;

    /**
     * @return
     * @Description [ 入库 ]
     * @Author [hudyang]
     * @Date 2019/6/26 18:17
     * @Param
     **/
    @PostMapping("inbound/addReturnOrderInbound")
    public AjaxInfo addInBound(@RequestBody Map<String, Object> map)throws Exception;
}

package com.camelot.order.feign;

import com.camelot.common.bean.AjaxInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "stock-sdk")
//@FeignClient(name = "stock-sdk", url = "172.16.1.22:8881")
@RequestMapping("warehouse-goods")
public interface FeignWarhouseGoodsService {

    /**
     * @return
     * @Description [ 查询仓库数量 ]
     * @Author [hudyang]
     * @Date 2019/6/26 18:17
     * @Param
     **/
    @GetMapping("list-category")
    public AjaxInfo queryWarhouseGoods(@RequestParam Map<String, Object> map);


}

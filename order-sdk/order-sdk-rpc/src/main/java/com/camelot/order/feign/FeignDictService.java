package com.camelot.order.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camelot.common.bean.AjaxInfo;

@FeignClient(name = "sysbase-sdk" )
//@FeignClient(name = "sysbase-sdk", url = "http://172.16.1.22:9991")
@RequestMapping("dictValue")
public interface FeignDictService {
	
	@RequestMapping(value = "list",method = RequestMethod.GET)
	public AjaxInfo getDictValue(@RequestParam Map<String, Object> vo);

	@RequestMapping(value = "{dictValueId}", method = RequestMethod.GET)
	public AjaxInfo queryValueById(@PathVariable(value = "dictValueId") Integer dictValueId);
}

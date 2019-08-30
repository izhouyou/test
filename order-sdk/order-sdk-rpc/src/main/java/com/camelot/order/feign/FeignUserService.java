package com.camelot.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.camelot.common.bean.AjaxInfo;

@FeignClient(name = "sysbase-sdk" )
@RequestMapping("inUser")
public interface FeignUserService {
	
	@RequestMapping(value = "{user}",method = RequestMethod.GET)
	public AjaxInfo queryByUser(@PathVariable(value="user") String user) ;
}

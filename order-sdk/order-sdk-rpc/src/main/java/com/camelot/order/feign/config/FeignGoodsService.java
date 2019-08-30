package com.camelot.order.feign.config;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camelot.common.bean.AjaxInfo;

@FeignClient("goods-sdk")
public interface FeignGoodsService {
	@RequestMapping(value="goods/One/{id}",method=RequestMethod.GET)
	AjaxInfo queryGoodsList(@PathVariable("id") String id);
	
	@RequestMapping(value="goods/page", method=RequestMethod.GET)
	AjaxInfo queryGoods(@RequestParam Map<String,Object> vo);
	
}

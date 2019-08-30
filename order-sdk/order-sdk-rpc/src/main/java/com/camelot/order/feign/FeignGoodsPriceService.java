package com.camelot.order.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camelot.common.bean.AjaxInfo;


@FeignClient(name = "goods-sdk")
@RequestMapping("price")
public interface FeignGoodsPriceService {
	
	/**
	 *@Description 查询单个商品时销价格
	 *@author xupengfei
	 *@Date 2019年4月13日 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/now", method = RequestMethod.GET)
	public AjaxInfo queryGoodsNowPrice(@RequestParam Map<String,Object> vo);
	
	
}

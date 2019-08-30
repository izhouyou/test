package com.camelot.order.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camelot.common.bean.AjaxInfo;

@FeignClient(name = "goods-sdk")
//@FeignClient(name = "goods-sdk", url = "http://172.16.1.22:4441")
@RequestMapping("goodsCategory")
public interface FeignGoodsCategoryService {
	
	/**
	 *@Description 获取意向车型
	 *@author xupengfei
	 *@Date 2019年4月12日 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/vehicle", method = RequestMethod.GET)
	public AjaxInfo queryVehicleByCategoryId(@RequestParam(name="id")Long id);
	
	@RequestMapping(value = "/categoryList", method = RequestMethod.GET)
	public AjaxInfo queryList(@RequestParam Map<String, Object> vo);
}

package com.camelot.order.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camelot.common.bean.AjaxInfo;


@FeignClient(name = "goods-sdk" )
@RequestMapping("goods")
public interface FeignGoodsService {
	
	/**
	 *@Description 根据商品id查询商品信息
	 *@author xupengfei
	 *@Date 2019年4月15日 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/One/{id}", method = RequestMethod.GET)
	public AjaxInfo queryGoodsById(@PathVariable(value="id")Long id);
	
	/**
	 *@Description 条件查询商品列表信息
	 *@author xupengfei
	 *@Date 2019年4月15日 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public AjaxInfo queryGoodsList(@RequestParam Map<String,Object> vo);
	
}

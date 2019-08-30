package com.camelot.order.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camelot.common.bean.AjaxInfo;

@FeignClient(name = "sysbase-sdk")
@RequestMapping("org")
public interface FeignOrgService {
	
	 
	/**
	 *@Description 获取所有区域信息列表
	 *@Data 2019年4月3日 
	 * @param vo
	 * @return
	 */
	 @RequestMapping(value = "list",method = RequestMethod.GET)
	 public AjaxInfo queryList(@RequestParam Map<String, Object> vo);
	
	 /**
	 *@Description 根据区域id查询数据
	 *@Data 2019年4月3日 
	 * @param id
	 * @return
	 */
	@RequestMapping(value= "{id}",method = RequestMethod.GET)
	 public AjaxInfo queryById(@PathVariable(value="id") Long id);
}

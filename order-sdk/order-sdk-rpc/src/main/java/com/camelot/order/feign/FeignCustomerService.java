package com.camelot.order.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camelot.common.bean.AjaxInfo;

@FeignClient(name = "sysbase-sdk" )
@RequestMapping("customer")
public interface FeignCustomerService {
	
 	/**
 	 *@Description 保存消费者信息
 	 *@Data 2019年4月8日 
 	 * @param vo
 	 * @return
 	 */
 	@RequestMapping(value = "saveCustomer", method = RequestMethod.POST)
    public AjaxInfo saveCustomer(@RequestBody Map<String,Object> vo) ;
 	
 	/**
 	 *@Description 条件查询消费者列表
 	 *@author xupengfei
 	 *@Data 2019年4月8日 
 	 * @param vo
 	 * @return
 	 */
 	@RequestMapping(value ="list", method = RequestMethod.GET)
 	public AjaxInfo queryCustomerList(@RequestParam Map<String,Object> vo); 
 	/**
 	 * <p>Description: 查询消费者来源统计</p>
 	 * @param ids 消费者ids
 	 * @return
 	 */
 	@RequestMapping(value ="source", method = RequestMethod.GET)
 	public AjaxInfo queryCustomerSource(@RequestParam("ids") String ids); 
 	
}

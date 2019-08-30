package com.camelot.order.feign;

import com.camelot.common.bean.AjaxInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "sysbase-sdk" )
//@FeignClient(name = "sysbase-sdk",url = "http://172.16.1.22:9991")
@RequestMapping("store")
public interface FeignStoreService {
	 
	/**
	 *@Description 根据主键id查询门店信息
	 *@author xupengfei
	 *@Data 2019年4月5日 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public AjaxInfo getStoreById(@PathVariable(value = "id") Long id);
	/**
	 * <p>Description: 根据区域ids,调用基础服务,获取合伙人/加盟商信息</p>
	 * @author zhouyou
	 * @param vo 门店vo
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	AjaxInfo queryList(@RequestParam Map<String, Object> vo);
	/**
	 * <p>Description: 根据门店ids,调用将基础服务,获取门店信息</p>
	 * @param storeIds 门店ids
	 * @return
	 */
	@RequestMapping(value = "ids", method = RequestMethod.GET)
    AjaxInfo queryStoreList(@RequestParam("ids") String storeIds);
	
}

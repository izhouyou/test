package com.camelot.order.feign.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camelot.common.bean.AjaxInfo;

@FeignClient(value = "sysbase-sdk")
public interface FeignStoreService {
	
	/**
	 * <p>Description: 根据门店ids,调用将基础服务,获取门店信息</p>
	 * @param storeIds 门店ids
	 * @return
	 */
	@RequestMapping(value = "store/ids", method = RequestMethod.GET)
    AjaxInfo queryStoreList(@RequestParam("ids") String storeIds);
	
	/**
	 * <p>Description: 根据区域ids,调用基础服务,获取合伙人/加盟商信息</p>
	 * @param regionIds 区域ids
	 * @return
	 */
	@RequestMapping(value = "partner/ids", method = RequestMethod.GET)
	AjaxInfo queryPartnerList(@RequestParam("ids") String regionIds);
	
	/**
	 * <p>Title: getDataPermission</p>
	 * <p>Description: 根据userId查询用户权限信息</p>
	 * @return
	 */
	@GetMapping("inUser/permission/{userId}")
	AjaxInfo getDataPermission(@PathVariable("userId") Long userId);
	
}

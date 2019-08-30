package com.camelot.order.feign;

import com.camelot.common.bean.AjaxInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "marketing-sdk")
//@FeignClient(name = "marketing-sdk",url = "172.16.1.22:7771")
@RequestMapping("couponext")
public interface FeignActivityCouponService {

	/**
	 * 判断优惠吗是否存在
	 *
	 * @param couponCode
	 * @return
	 */
	@RequestMapping(value = "verifyCode", method = RequestMethod.GET)
	AjaxInfo verifyCode(@RequestParam("couponCode") String couponCode);

	/**
	 * 核销高伟的优惠吗
	 *
	 * @param couponCode
	 * @return
	 */
	@RequestMapping(value = "applyCode", method = RequestMethod.GET)
	AjaxInfo applyCode(@RequestParam("couponCode") String couponCode);

}

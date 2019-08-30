package com.camelot.order.feign;

import com.camelot.common.bean.AjaxInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "marketing-sdk")
//@FeignClient(name = "marketing-sdk", url = "http://172.16.1.22:7771")
@RequestMapping("promotion")
public interface FeignCouponService {
	
	/**
	 *@Description 根据ID查询活动码数据
	 *@Data 2019年4月8日 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "id",method = RequestMethod.GET)
	public AjaxInfo queryById(@RequestParam("tCouponId") Integer id);
	/**
	 * <p>Description:  根据ID查询活动批次数据</p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "page",method = RequestMethod.GET)
	public AjaxInfo queryPage(@RequestParam("tCouponBatchId") Integer tCouponBatchId, @RequestParam("tCouponId") String id);
	
	/**
	 * 注销 还原优惠码
	 * @param couponVO
	 * @return
	 */
	@RequestMapping(value = "destroy" ,method = RequestMethod.GET)
	public AjaxInfo destroy(@RequestParam Map<String,Object> vo);
	
	/**
	 * <p>Title: queryById</p>
	 * <p>Description: 根据优惠码批次id查询优惠码批次信息</p>
	 * @param id
	 * @return
	 * @author zhouyou
	 * @date 2019年5月26日
	 */
	@RequestMapping(value = "tCouponBatchId",method = RequestMethod.GET)
	public AjaxInfo queryCouponBatchById(@RequestParam("tCouponBatchId") Integer tCouponBatchId);

}

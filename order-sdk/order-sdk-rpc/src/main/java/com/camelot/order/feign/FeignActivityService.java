package com.camelot.order.feign;

import com.camelot.common.bean.AjaxInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="marketing-sdk")
@RequestMapping("activity")
public interface FeignActivityService {
	// 查询活动信息
//	@RequestMapping(name="allList", method=RequestMethod.GET)
//	AjaxInfo queryActivityList(@RequestParam("vo") String vo);
	
	/**
	 *@Description 根据id查询活动数据
	 *@author xupengfei
	 *@Data 2019年4月8日 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "id",method = RequestMethod.GET)
	public AjaxInfo queryById(@RequestParam("tActivityId") Integer id);
	
	/**
	 * 查询活动信息
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "ActivityVoList", method = RequestMethod.GET)
	AjaxInfo queryActivityAllList(@RequestParam Map<String, Object> vo);
	
	/**
	 * <p>Title: updateCardState</p>
	 * <p>Description: FMS客户卡券状态更新</p>
	 * @param vo
	 * @author zhouyou
	 * @date 2019年8月1日
	 */
	@GetMapping(value = "requestLog/fms/updateCardState")
	void updateCardState(@RequestParam Map<String, Object> vo);
}

package com.camelot.order.service.impl.export;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.service.FeignCouponExportService;
import com.camelot.order.export.vo.feignvo.FeignCouponBatchVO;
import com.camelot.order.feign.FeignCouponService;
import com.github.pagehelper.PageInfo;

/**
 * <p>Title: FeignCouponExportServiceImpl</p>
 * <p>Description: [营促销服务信息]</p>
 * <p>Company: Camelot</p>
 * @author zhouyou
 * @date 2019年5月21日
 * @version 1.0
 */
@Service("feignCouponExportServiceImpl")
public class FeignCouponExportServiceImpl implements FeignCouponExportService {
	
	@Autowired
	private FeignCouponService feignCouponService;

	@Override
	public FeignCouponBatchVO queryCouponBatch(Integer tCouponBatchId, String activityBatchCode) {
		AjaxInfo ajaxInfo = feignCouponService.queryPage(tCouponBatchId, activityBatchCode);
		if(Utility.isNotEmpty(ajaxInfo.getData())) {
			PageInfo pageInfo =  JSONObject.parseObject(JSONObject.toJSONString(ajaxInfo.getData()), PageInfo.class);
			if(Utility.isNotEmpty(pageInfo)) {
				return JSONObject.parseObject(Utility.objectToString(pageInfo.getList().get(0)), FeignCouponBatchVO.class);
			}
		}
		return null;
	}

	@Override
	public FeignCouponBatchVO queryCouponBatch(Integer tCouponBatchId) {
		if(Utility.isNotEmpty(tCouponBatchId)) {
			AjaxInfo ajaxInfo = feignCouponService.queryCouponBatchById(tCouponBatchId);
			if(Utility.isNotEmpty(ajaxInfo.getData())) {
				FeignCouponBatchVO feignCouponBatchVO =  JSONObject.parseObject(JSONObject.toJSONString(ajaxInfo.getData()), FeignCouponBatchVO.class);
				if(Utility.isNotEmpty(feignCouponBatchVO)) {
					return feignCouponBatchVO;
				}
			}
		}
		return null;
	}

}

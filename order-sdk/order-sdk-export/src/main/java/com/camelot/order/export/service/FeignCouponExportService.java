package com.camelot.order.export.service;

import java.util.Map;

import com.camelot.order.export.vo.feignvo.FeignCouponBatchVO;

/**
 * <p>Title: FeignCouponExportService</p>
 * <p>Description: [调用营促销服务]</p>
 * <p>Company: Camelot</p>
 * @author zhouyou
 * @date 2019年5月21日
 * @version 1.0
 */
public interface FeignCouponExportService {

	/**
	 * <p>Title: queryCouponBatch</p>
	 * <p>Description: 查询单个活动码信息</p>
	 * @param vo
	 * @return
	 * @author zhouyou
	 * @date 2019年5月21日
	 */
	FeignCouponBatchVO queryCouponBatch(Integer tCouponBatchId, String activityBatchCode);

	/**
	 * <p>Title: queryCouponBatch</p>
	 * <p>Description: 查询单个活动批次信息</p>
	 * @param couponId
	 * @return
	 * @author zhouyou
	 * @date 2019年5月26日
	 */
	FeignCouponBatchVO queryCouponBatch(Integer tCouponBatchId);
	
}

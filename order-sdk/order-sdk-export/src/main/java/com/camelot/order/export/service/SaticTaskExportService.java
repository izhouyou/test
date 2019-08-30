package com.camelot.order.export.service;

import com.camelot.common.bean.ExecuteResult;

/**
 * <p>Description: [订单销量统计定时任务]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName SaticScheduleTaskService.java
 * Created on 2019年4月9日.
 */
public interface SaticTaskExportService {
	ExecuteResult<String> statisticsStaticTask();
}

package com.camelot.order.export.service;

import java.util.List;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.camelot.order.export.vo.DifferenceOrderVO;
import com.camelot.order.export.vo.ReturnGoodsVO;
import com.camelot.order.export.vo.SameAgencyVO;
import com.camelot.order.export.vo.param.DifferenceParamVO;
import com.camelot.order.export.vo.param.ReturnGoodsParamVO;
import com.camelot.order.export.vo.param.SameNameParamVO;
import com.github.pagehelper.PageInfo;

/**
 * <p>Description: [合规暴露接口]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName ComplianceExportService.java
 * Created on 2019年4月2日.
 */
public interface ComplianceExportService {
	/**
	 * <p>Description: 查询零售差异订单信息</p>
	 * @param storeIds 城市idSet集合
	 * @param storeIds 门店idSet集合
	 * @param storeIds 门店idStr集合
	 * @param page 分页
	 * @param minAmount 最小差异金额
	 * @param maxAmount 最大差异金额
	 * @return
	 */
	ExecuteResult<PageInfo<DifferenceOrderVO>> compliancePageDifference(DifferenceParamVO paramVO, Page page);
	/**
	 * <p>Description: </p>
	 * @param storeIds 城市idSet集合
	 * @param storeIds 门店idSet集合
	 * @param storeIds 门店idStr集合
	 * @param minAmount 最小差异金额
	 * @param maxAmount 最大差异金额
	 * @return 零售差异订单数据
	 * @author zhouyou
	 * @date 2019年5月14日
	 */
	ExecuteResult<List<DifferenceOrderVO>> complianceListDifference(DifferenceParamVO paramVO);
	/**
	 * <p>Description: 统计退货有序商品</p>
	 * @param page 分页
	 * @param storeIds 门店ids
	 * @param goodsName 商品名称
	 * @param goodsSn 商品SN码
	 * @param goodsFrameNumber 商品编码
	 * @param salesOrderStatus 订单状态
	 * @return
	 */
	ExecuteResult<PageInfo<ReturnGoodsVO>> complianceReturnGoods(ReturnGoodsParamVO paramVO, Page page);
	/**
	 * <p>Description: 查询相同机构合伙人/加盟商信息</p>
	 * @param pageNum 页号
	 * @param pageSize 条数
	 * @param orgIds 区域ids
	 * @param partnerNumber 合伙人编号
	 * @param franchiseeNumber 加盟商编号
	 * @param companyName 公司名称
	 * @param mobilePhone 联系电话
	 * @return
	 */
	ExecuteResult<PageInfo<SameAgencyVO>> complianceSameAgency(SameNameParamVO paramVO, Page page);
	/**
	 * <p>Description: 机构归属相同导出</p>
	 * @param paramVO
	 * @return
	 */
	ExecuteResult<List<SameAgencyVO>> exportSameName(SameNameParamVO paramVO);
}

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
 * <p>Title: NewComplianceExportService</p>
 * <p>Description: [合规暴露接口]</p>
 * <p>Company: Camelot</p>
 * @author zhouyou
 * @date 2019年5月16日
 */
public interface NewComplianceExportService {
	/**
	 * <p>Title: compliancePageDifference</p>
	 * <p>Description: 零售差异订单查询</p>
	 * @param paramVO 门店idStr/城市idStr/门店ids/起止时间/订单编号/差异金额
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年5月16日
	 */
	ExecuteResult<PageInfo<DifferenceOrderVO>> compliancePageDifference(DifferenceParamVO paramVO, Page page);
	/**
	 * <p>Title: complianceListDifference</p>
	 * <p>Description: 零售差异订单导出</p>
	 * @param paramVO 门店idStr/城市idStr/门店ids/起止时间/订单编号/差异金额
	 * @return
	 * @author zhouyou
	 * @date 2019年5月16日
	 */
	ExecuteResult<List<DifferenceOrderVO>> complianceListDifference(DifferenceParamVO paramVO);
	/**
	 * <p>Title: compliancePageReturnGoods</p>
	 * <p>Description: 退货有序商品查询</p>
	 * @param paramVO 门店idStr/城市idStr/商品名称/商品SN码/商品车架号
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年5月16日
	 */
	ExecuteResult<PageInfo<ReturnGoodsVO>> compliancePageReturnGoods(ReturnGoodsParamVO paramVO, Page page);
	/**
	 * <p>Title: compliancePageSameAgency</p>
	 * <p>Description: 机构归属相同查询</p>
	 * @param paramVO 城市idStr/销售编号/加盟商编号/公司名称/联系手机号
	 * @param page
	 * @return
	 * @author zhouyou
	 * @date 2019年5月16日
	 */
	ExecuteResult<PageInfo<SameAgencyVO>> compliancePageSameAgency(SameNameParamVO paramVO, Page page);
	/**
	 * <p>Title: complianceListSameAgency</p>
	 * <p>Description: 机构归属相同导出</p>
	 * @param paramVO 城市idStr/销售编号/加盟商编号/公司名称/联系手机号
	 * @return
	 * @author zhouyou
	 * @date 2019年5月16日
	 */
	ExecuteResult<List<SameAgencyVO>> complianceListSameAgency(SameNameParamVO paramVO);
}

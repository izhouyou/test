package com.camelot.order.service.impl.export;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.domain.TransregionalInfoDomain;
import com.camelot.order.export.service.DictValueExportService;
import com.camelot.order.export.service.TransregionalInfoExportService;
import com.camelot.order.export.snapshot.TransregionalInfoVO;
import com.camelot.order.service.TransregionalInfoService;
import com.camelot.order.service.base.BaseServiceImpl;

@Service("transregionalInfoExportService")
public class TransregionalInfoExportServiceImpl extends BaseServiceImpl<TransregionalInfoVO, TransregionalInfoDomain> implements TransregionalInfoExportService {

	private static Logger log = Log.get(TransregionalInfoExportServiceImpl.class);
	
	@Autowired
	private TransregionalInfoService transregionalInfoService;
	@Autowired
	private NewOrderNumberExportService newOrderNumberExportService;
	/** 基础服务-字典数据 */
	@Autowired
	private DictValueExportService dictValueExportService;
	
	/**
	 * <p>Title: checkRequiredData</p>
	 * <p>Description: 校验数据必填项</p>
	 * @param vo
	 * @return
	 * @author zhouyou
	 * @date 2019年6月21日
	 */
	private String checkRequiredData(TransregionalInfoVO vo) {
		if(Utility.isEmpty(vo.getGoodsNumber())) {
			// 商品编号
			return "商品编号为空";
		} else if(Utility.isEmpty(vo.getGoodsName())) {
			// 商品名称
			return "商品名称为空";
		} else if(Utility.isEmpty(vo.getGoodsSn())) {
			// 商品SN
			return "商品SN为空";
		} else if(Utility.isEmpty(vo.getGoodsFrameNumber())) {
			// 车架号
			return "车架号为空";
		} else if(Utility.isEmpty(vo.getActivationFlag())) {
			// 是否激活 0:未激活;1激活
			return "是否激活为空";
		} else if(Utility.isEmpty(vo.getCityThird())) {
			// 销售后3天城市
			return "销售后3天城市为空";
		} else if(Utility.isEmpty(vo.getCityFifteen())) {
			// 销售后15天城市
			return "销售后15天城市为空";
		} else if(Utility.isEmpty(vo.getSalesOrderNumber())) {
			// 销售订单编号
			return "销售订单编号为空";
		} else if(Utility.isEmpty(vo.getOperationUserId())) {
			// 操作人
			return "操作人为空";
		} else if(Utility.isEmpty(vo.getOperationUserName())) {
			// 操作人姓名
			return "操作人姓名为空";
		} else if(Utility.isEmpty(vo.getSubmitTime())) {
			// 提交时间
			return "提交时间为空";
		} else if(Utility.isEmpty(vo.getStoreId())) {
			// 销售门店ID
			return "销售门店ID为空";
		} else if(Utility.isEmpty(vo.getStoreNumber())) {
			// 销售门店编码
			return "销售门店编码为空";
		} else if(Utility.isEmpty(vo.getStoreName())) {
			// 销售门店名称
			return "销售门店名称为空";
		} else if(Utility.isEmpty(vo.getSpartnerId())) {
			// 销售合伙人ID
			return "销售合伙人ID为空";
		} else if(Utility.isEmpty(vo.getSpartnerNumber())) {
			// 销售合伙人编码
			return "销售合伙人编码为空";
		} else if(Utility.isEmpty(vo.getSpartnerName())) {
			// 销售合伙人名称
			return "销售合伙人名称为空";
		} else if(Utility.isEmpty(vo.getCpartnerId())) {
			// 采购合伙人ID
			return "采购合伙人ID为空";
		} else if(Utility.isEmpty(vo.getCpartnerNumber())) {
			// 采购合伙人编码
			return "采购合伙人编码为空";
		} else if(Utility.isEmpty(vo.getCpartnerName())) {
			// 采购合伙人名称
			return "采购合伙人名称为空";
		} else if(Utility.isEmpty(vo.getProblemType())) {
			// 1:合伙人外部窜货
			return "窜货类型为空";
		} else if(Utility.isEmpty(vo.getTransactionId())) {
			// 流水号(防重复提交)
			return "流水号(防重复提交)为空";
		} else if(Utility.isEmpty(vo.getGoodsId())) {
			// 商品id
			return "商品id为空";
		} else if(Utility.isEmpty(vo.getFirstOrgId())) {
			// 大区id
			return "大区id为空";
		} else if(Utility.isEmpty(vo.getFirstOrgNumber())) {
			// 大区编码
			return "大区编码为空";
		} else if(Utility.isEmpty(vo.getFirstOrgName())) {
			// 大区名称
			return "大区名称为空";
		} else if(Utility.isEmpty(vo.getSecondOrgId())) {
			// 区域id
			return "区域id为空";
		} else if(Utility.isEmpty(vo.getSecondOrgNumber())) {
			// 区域编号
			return "区域编号为空";
		} else if(Utility.isEmpty(vo.getSecondOrgName())) {
			// 城市名称
			return "城市名称为空";
		} else if(Utility.isEmpty(vo.getThirdOrgId())) {
			// 城市id
			return "城市id为空";
		} else if(Utility.isEmpty(vo.getThirdOrgNumber())) {
			// 城市编号
			return "城市编号为空";
		} else if(Utility.isEmpty(vo.getThirdOrgName())) {
			// 城市名称
			return "城市名称为空";
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public ExecuteResult<TransregionalInfoVO> addTransregionalInfo(TransregionalInfoVO vo) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "TransregionalInfoExportServiceImpl-addTransregionalInfo",  JSONObject.toJSONString(vo));
        ExecuteResult<TransregionalInfoVO> result = new ExecuteResult<>();
        try {
            // 数据校验
        	String checkResult = checkRequiredData(vo);
        	if(Constants.SUCCESS_CODE.equals(checkResult)) {
        		// 获取主键id
        		ExecuteResult<String> genid = newOrderNumberExportService.getGenid();
        		if (Utility.isEmpty(genid.getResult())) {
        			result.setCode(Constants.ERROR_CODE);
        			result.setResultMessage("网络延迟,请稍后重试!");
        			Log.debug(log, "\n 方法[{}]，出参：[{}]", "NewSalesOrderExportServiceImpl-submitSalesOrder", result);
        			return result;
        		}
        		// 主键id获取
        		String id = genid.getResult();
        		vo.setId(Long.valueOf(id));
        		// 数据初始化
        		TransregionalInfoVO transregionalInfoVO = voCoverData(vo);
        		// 添加操作
        		result = transregionalInfoService.add(transregionalInfoVO);
        	} else {
        		result.setCode(Constants.ERROR_CODE);
        		result.setResultMessage(checkResult);
        	}
        } catch (Exception e) {
            result.setCode(Constants.ERROR_CODE);
            result.setResultMessage(e.toString());
            Log.debug(log, "\n 方法[{}]，异常：[{}]", "TransregionalInfoExportServiceImpl-addTransregionalInfo", e.toString());
        }
        Log.debug(log, "\n 方法[{}]，出参：[{}]", "TransregionalInfoExportServiceImpl-addTransregionalInfo",  JSONObject.toJSONString(result));
        return result;
	}
	
	public TransregionalInfoVO voCoverData(TransregionalInfoVO vo) {
		Date date = new Date();
		// 创建时间
		vo.setCreateDate(date);
		// 修改时间
		vo.setModifyDate(date);
		// 版本号
		vo.setVersion(OrderConstants.ORDER_DEFAULT_VERSION);
		// 删除标识
		vo.setDelFlg(Constants.DELFLG_NORMAL);
		return vo;
	}

	@Override
	public List<TransregionalInfoVO> handleTransregionalInfoList(List<TransregionalInfoVO> list) {
		if (Utility.isNotEmpty(list)) {
			for (TransregionalInfoVO vo : list) {
				// 字典值处理
				// 是否激活
				if (Utility.isNotEmpty(vo.getActivationFlag())) {
					vo.setActivationFlagValue(dictValueExportService.getDictValueNameById(OrderConstants.ACTIVATION_FLAG_TYPE,
							vo.getActivationFlag()));
				}
				// 问题类型
				if (Utility.isNotEmpty(vo.getProblemType())) {
					vo.setProblemTypeValue(dictValueExportService
							.getDictValueNameById(OrderConstants.PROBLEM_TYPE, vo.getProblemType()));
				}
			}

		}
		return list;
	}


}
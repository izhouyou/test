package com.camelot.order.export.vo.param;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 归属机构相同查询参数</p>
 * @author zhouyou
 * @date 2019年5月14日
 */
public class SameNameParamVO extends BaseParamVO implements Serializable {
	
	/** 销售编号 */
	@ApiModelProperty(value = "销售编号", required = false)
	private String partnerNumber;
	/** 加盟商编号 */
	@ApiModelProperty(value = "加盟商编号", required = false)
	private String franchiseeNumber;
	/** 公司名称 */
	@ApiModelProperty(value = "公司名称", required = false)
	private String companyName;
	/** 联系手机号 */
	@ApiModelProperty(value = "联系手机号", required = false)
	private String mobilePhone;
	
	public String getPartnerNumber() {
		return partnerNumber;
	}
	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}
	public String getFranchiseeNumber() {
		return franchiseeNumber;
	}
	public void setFranchiseeNumber(String franchiseeNumber) {
		this.franchiseeNumber = franchiseeNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
}

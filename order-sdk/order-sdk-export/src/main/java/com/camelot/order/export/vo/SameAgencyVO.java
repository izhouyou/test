package com.camelot.order.export.vo;

import java.io.Serializable;

/**
 * <p>Description: [机构归属相同]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName SameAgencyVO.java
 * Created on 2019年4月8日.
 */
public class SameAgencyVO implements Serializable {
	/** partnerNumber合伙人编号*/
	private String partnerNumber;
	/** partnerName合伙人名称*/
	private String partnerName;
	/** franchiseeNumber加盟商编号*/
	private String franchiseeNumber;
	/** franchiseeName加盟商名称*/
	private String franchiseeName;
	/**公司名称*/
	private String companyName;
	/**联系电话*/
	private String mobilePhone;
	
	public String getPartnerNumber() {
		return partnerNumber;
	}
	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getFranchiseeNumber() {
		return franchiseeNumber;
	}
	public void setFranchiseeNumber(String franchiseeNumber) {
		this.franchiseeNumber = franchiseeNumber;
	}
	public String getFranchiseeName() {
		return franchiseeName;
	}
	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
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
	@Override
	public String toString() {
		return "SameAgencyVO [partnerNumber=" + partnerNumber + ", partnerName=" + partnerName + ", franchiseeNumber="
				+ franchiseeNumber + ", franchiseeName=" + franchiseeName + ", companyName=" + companyName
				+ ", mobilePhone=" + mobilePhone + "]";
	}
}

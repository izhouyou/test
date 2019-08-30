package com.camelot.order.export.vo.excelvo;

import java.io.Serializable;

import com.camelot.order.common.utils.excel.ExcelField;

/**
 * <p>Description: [机构归属相同导出]</p>
 *
 * @author <a href="mailto: zhouyou@camelotchina.com">zhouyou</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName SameAgencyVO.java
 * Created on 2019年4月8日.
 */
public class SameAgencyExportVO implements Serializable {
	
	/** partnerNumber合伙人编号*/
	@ExcelField(title = "销售编号", sort = 5 )
	private String partnerNumber;
	/** partnerName合伙人名称*/
	@ExcelField(title = "合伙人名称", sort = 10 )
	private String partnerName;
	/**联系电话*/
	@ExcelField(title = "联系电话", sort = 15 )
	private String mobilePhone;
	/**公司名称*/
	@ExcelField(title = "公司名称", sort = 20 )
	private String companyName;
	/** franchiseeNumber加盟商编号*/
	@ExcelField(title = "加盟商编码", sort = 25 )
	private String franchiseeNumber;
	/** franchiseeName加盟商名称*/
	@ExcelField(title = "加盟商名称", sort = 30 )
	private String franchiseeName;
	
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

}

package com.camelot.order.export.vo.feignvo;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  机构表
 */
public class OrgVO implements Serializable {
    private static final long serialVersionUID = 7382084521532941729L;

    /**主键*/
    private Long id ;
    /**机构名称*/
    private String orgName ;
    /**父节点id*/
    private Long parentorgId;
    /**描述*/
    private String desciption ;
    /**机构编码*/
    private String orgCode ;
    /**机构等级*/
    private Integer orgLevel ;
    /**创建日期*/
    private Date createDate;
    /**修改日期*/
    private Date modifyDate ;
    /**操作人*/
    private Long operatorId ;
    /**删除状态(0 有效 1无效)*/
    private Integer delFlg ;

    /**业务编码*/
    private String businessCode ;
    /**业务类别*/
    private String businessType ;


    private Long parentId ;
    private Integer state ;
    private String checked ;
    private String orgType ;
    private Long appId ;
    private List<OrgVO> orgs ;
    /**上级区域信息*/
    private OrgVO orgVO;

    private String sortNumber;//排序
    /**合伙人加盟商信息*/
    private List<PartnerTraderVO> partnerTraderVOList;
    /**区域树级别*/
    private Integer treeLevel;
    /*区域id*/
    private List<Long> orgList;

    public List<Long> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<Long> orgList) {
        this.orgList = orgList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getParentorgId() {
        return parentorgId;
    }

    public void setParentorgId(Long parentorgId) {
        this.parentorgId = parentorgId;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public List<OrgVO> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<OrgVO> orgs) {
        this.orgs = orgs;
    }

    public String getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

	public List<PartnerTraderVO> getPartnerTraderVOList() {
		return partnerTraderVOList;
	}

	public void setPartnerTraderVOList(List<PartnerTraderVO> partnerTraderVOList) {
		this.partnerTraderVOList = partnerTraderVOList;
	}

	public OrgVO getOrgVO() {
		return orgVO;
	}

	public void setOrgVO(OrgVO orgVO) {
		this.orgVO = orgVO;
	}

	public Integer getTreeLevel() {
		return treeLevel;
	}

	public void setTreeLevel(Integer treeLevel) {
		this.treeLevel = treeLevel;
	}

	@Override
	public String toString() {
		return "OrgVO [id=" + id + ", orgName=" + orgName + ", parentorgId=" + parentorgId + ", desciption="
				+ desciption + ", orgCode=" + orgCode + ", orgLevel=" + orgLevel + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + ", operatorId=" + operatorId + ", delFlg=" + delFlg + ", businessCode="
				+ businessCode + ", businessType=" + businessType + ", parentId=" + parentId + ", state=" + state
				+ ", checked=" + checked + ", orgType=" + orgType + ", appId=" + appId + ", orgs=" + orgs + ", orgVO="
				+ orgVO + ", sortNumber=" + sortNumber + ", partnerTraderVOList=" + partnerTraderVOList + ", treeLevel="
				+ treeLevel + "]";
	}

}

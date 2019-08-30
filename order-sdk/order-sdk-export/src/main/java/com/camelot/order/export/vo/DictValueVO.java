package com.camelot.order.export.vo;

import java.io.Serializable;
import java.util.Date;

public class DictValueVO implements Serializable {

    private static final long serialVersionUID = -788086881507681127L;
    /**主键*/
    private Integer dictValueId;
    /**字典类型主键*/
    private Integer dictTypeId;
    /**字典值编号*/
    private String dictValueNumber;
    /**数据字典值名称*/
    private String dictValueName;
    /**描述*/
    private String description;
    /**创建时间*/
    private Date createDate;
    /**修改时间*/
    private Date modifyDate;
    /**删除状态(0 有效 1无效)*/
    private Integer delFlg;
    /**创建人*/
    private Long createUserId;
    /**修改人*/
    private Long updateUserId;

    public Integer getDictValueId() {
        return dictValueId;
    }

    public void setDictValueId(Integer dictValueId) {
        this.dictValueId = dictValueId;
    }

    public Integer getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(Integer dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public String getDictValueNumber() {
        return dictValueNumber;
    }

    public void setDictValueNumber(String dictValueNumber) {
        this.dictValueNumber = dictValueNumber;
    }

    public String getDictValueName() {
        return dictValueName;
    }

    public void setDictValueName(String dictValueName) {
        this.dictValueName = dictValueName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

}

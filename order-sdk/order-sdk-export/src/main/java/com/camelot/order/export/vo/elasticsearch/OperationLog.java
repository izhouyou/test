package com.camelot.order.export.vo.elasticsearch;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [操作行为日志]</p>
 * Created on 2018/4/17 11:26
 *
 * @author <a href="mailto: wuxinkuan@camelotchina.com">吴心宽</a>
 * @version 1.0
 * Copyright (c) 2018 北京柯莱特科技有限公司
 */
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1010708872782259235L;
    /**
     * opertionId  系统生成唯一ID
     */
    private Long opertionId;

    /**
     * 操作人 当前登录用户id
     */
    private String rmsOperatorId;
    /**
     * 操作人 当前登录用户
     */
    private String operatorName;
    /**
     * 业务描述 整个功能模块描述
     */
    private String businessDescription;
    /**
     * 操作描述 具体功能方法的描述
     */
    private String operationDescription;
    /**
     * URL 请求地址
     */
    private String url;
    /**
     * 请求参数
     */
    private String requestParameters;
    /**
     * 返回值
     */
    private String returnValue;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 操作平台
     */
    private String operType;
    /**
     * ip地址
     */
    private String ip;

    /**
     * 操作人 当前登录用户编号
     */
    private String operatorNumber;

    /**
     * 操作平台name
     */
    private String operTypeName;


    /**
     * 区域城市id
     */
    private String gid;
    /**
     * 门店id
     */
    private String sid;

    public Long getOpertionId() {
        return opertionId;
    }

    public void setOpertionId(Long opertionId) {
        this.opertionId = opertionId;
    }

    public String getRmsOperatorId() {
		return rmsOperatorId;
	}

	public void setRmsOperatorId(String rmsOperatorId) {
		this.rmsOperatorId = rmsOperatorId;
	}

	public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(String requestParameters) {
        this.requestParameters = requestParameters;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOperatorNumber() {
        return operatorNumber;
    }

    public void setOperatorNumber(String operatorNumber) {
        this.operatorNumber = operatorNumber;
    }

    public String getOperTypeName() {
        return operTypeName;
    }

    public void setOperTypeName(String operTypeName) {
        this.operTypeName = operTypeName;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
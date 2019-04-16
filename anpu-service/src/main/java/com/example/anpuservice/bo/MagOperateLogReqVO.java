package com.example.anpuservice.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * FileName: com.iyingke.riskbackstage.vo.MagOperateLogReqVO.java
 * Author: Administrator
 * Description:
 */

public class MagOperateLogReqVO {
    @ApiModelProperty(value = "页码")
    private Integer currentPage;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @ApiModelProperty(value = "每页显示的数量")
    private Integer pageSize;
    @ApiModelProperty(value = "操作名称")
    private String operation;
    @ApiModelProperty(value = "操作类型")
    private String operateType;
    @ApiModelProperty(value = "操作人")
    private String userName;
    @ApiModelProperty(value = "结果状态：0 失败，1 成功 ，空为全部")
    private String resultStatus;
    @ApiModelProperty(value = "开始时间")
    private String beginTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
}

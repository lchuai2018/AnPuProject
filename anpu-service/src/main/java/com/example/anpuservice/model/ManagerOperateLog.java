package com.example.anpuservice.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "manager_operate_log")
public class ManagerOperateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 操作用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 操作类型
     */
    @Column(name = "operate_type")
    private String operateType;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 日志类型
     */
    @Column(name = "log_type")
    private String logType;

    /**
     * 日志编号
     */
    @Column(name = "log_number")
    private String logNumber;

    /**
     * ip
     */
    private String ip;

    /**
     * 结果状态码:0 失败,1 成功
     */
    @Column(name = "result_status")
    private String resultStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 操作前参数
     */
    @Column(name = "before_params")
    private String beforeParams;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 返回结果
     */
    private String result;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取操作用户名
     *
     * @return user_name - 操作用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置操作用户名
     *
     * @param userName 操作用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户操作
     *
     * @return operation - 用户操作
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 设置用户操作
     *
     * @param operation 用户操作
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * 获取操作类型
     *
     * @return operate_type - 操作类型
     */
    public String getOperateType() {
        return operateType;
    }

    /**
     * 设置操作类型
     *
     * @param operateType 操作类型
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    /**
     * 获取请求方法
     *
     * @return method - 请求方法
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置请求方法
     *
     * @param method 请求方法
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取日志类型
     *
     * @return log_type - 日志类型
     */
    public String getLogType() {
        return logType;
    }

    /**
     * 设置日志类型
     *
     * @param logType 日志类型
     */
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
     * 获取日志编号
     *
     * @return log_number - 日志编号
     */
    public String getLogNumber() {
        return logNumber;
    }

    /**
     * 设置日志编号
     *
     * @param logNumber 日志编号
     */
    public void setLogNumber(String logNumber) {
        this.logNumber = logNumber;
    }

    /**
     * 获取ip
     *
     * @return ip - ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip
     *
     * @param ip ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取结果状态码:0 失败,1 成功
     *
     * @return result_status - 结果状态码:0 失败,1 成功
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * 设置结果状态码:0 失败,1 成功
     *
     * @param resultStatus 结果状态码:0 失败,1 成功
     */
    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取操作前参数
     *
     * @return before_params - 操作前参数
     */
    public String getBeforeParams() {
        return beforeParams;
    }

    /**
     * 设置操作前参数
     *
     * @param beforeParams 操作前参数
     */
    public void setBeforeParams(String beforeParams) {
        this.beforeParams = beforeParams;
    }

    /**
     * 获取请求参数
     *
     * @return params - 请求参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置请求参数
     *
     * @param params 请求参数
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取返回结果
     *
     * @return result - 返回结果
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置返回结果
     *
     * @param result 返回结果
     */
    public void setResult(String result) {
        this.result = result;
    }
}
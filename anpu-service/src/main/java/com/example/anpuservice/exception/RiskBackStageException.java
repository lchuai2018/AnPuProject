package com.example.anpuservice.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * FileName: com.iyingke.riskbackstage.exception.RiskBackStageException.java
 * Author: Administrator
 * Description:
 */
public class RiskBackStageException extends RuntimeException {

    private String errorMsg;
    private int errorCode = HttpServletResponse.SC_BAD_REQUEST;

    public RiskBackStageException() {
        super();
    }

    public RiskBackStageException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public RiskBackStageException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public RiskBackStageException(String errorMsg, int errorCode) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "RiskBackStageException{" +
                "errorMsg='" + errorMsg + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}

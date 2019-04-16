package com.example.anpuservice.enums;

/**
 * FileName: com.iyingke.riskbackstage.constants.enums.StatusEnum.java
 * Author: Administrator
 * Description:
 */
public enum StatusEnum {
    UNUSED(0, "禁用"),
    USED(1, "可用"),;

    private Integer status;

    private String statusName;

    StatusEnum(Integer status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }

    public static String getStatusNameByStatus(Integer status) {
        if (status != null) {
            switch (status) {
                case 0:
                    return StatusEnum.UNUSED.getStatusName();
                case 1:
                    return StatusEnum.USED.getStatusName();
            }
        }
        return "未知状态";
    }

}

package com.example.anpuservice.enums;

/**
 * FileName: com.iyingke.riskbackstage.constants.enums.MenuTypeEnum.java
 * Author: Administrator
 * Description:
 */
public enum MenuTypeEnum {
    //目录
    CATALOG(0),
    MENU(1),//菜单
    ;

    private int value;

    MenuTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

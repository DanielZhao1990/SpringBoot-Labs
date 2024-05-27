package com.example.dlab.log;

/**
 * @Author: DI.YIN
 * @Date: 2024/3/13 16:31
 * @Version: 1.0.0
 * @Description: 接口日志状态
 **/
public enum InterfaceLogStatus {
    SUCCESS("S"),
    ERROR("E"),
    DOING("D"),
    ;
    private String code;

    InterfaceLogStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

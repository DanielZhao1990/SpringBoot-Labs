package com.example.dlab.log;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * @Author: DI.YIN
 * @Date: 2024/3/13 16:05
 * @Version: 1.0.0
 * @Description: 日志实体类
 **/
@Data
@RequiredArgsConstructor
public class InterfaceLogDomain {


    private static final long serialVersionUID = 2588400601329175428L;


    private Long logId;


    private String remoteSysCode;

    private String remoteSysName;

    private Date requestTime;

    private Date responseTime;

    private String requestMethod;

    private String requestUrl;

    private String requestHeaders;

    private String requestMessage;

    private String responseMessage;


    private String status;

}

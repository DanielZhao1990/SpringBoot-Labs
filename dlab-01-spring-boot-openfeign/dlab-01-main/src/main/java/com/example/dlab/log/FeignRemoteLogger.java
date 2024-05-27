package com.example.dlab.log;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static feign.Util.UTF_8;
import static feign.Util.decodeOrDefault;

/**
 * @Author: DI.YIN
 * @Date: 2024/3/13 15:36
 * @Version: 1.0.0
 * @Description: Feign接口远程调用接口日志记录
 **/
@RequiredArgsConstructor
public class FeignRemoteLogger extends Logger {

    private final InterfaceLogMapper logMapper;

    /**
     * 具体日志显示格式再次方法打印，因为要将日志写入数据库，因此该地方就不打印日志了
     *
     * @param configKey
     * @param format
     * @param args
     */
    @Override
    protected void log(String configKey, String format, Object... args) {

    }

    /**
     * 在调用之前调用该方法，将请求数据写入数据库
     *
     * @param configKey
     * @param logLevel
     * @param request
     */
    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {

        InterfaceLogDomain interfaceLogDomain = new InterfaceLogDomain();
        //从header中获取系统编码
        interfaceLogDomain.setRemoteSysCode(request.headers().get("SYS_CODE") == null || request.headers().get("SYS_CODE").isEmpty() ? "UNKNOWN" : request.headers().get("SYS_CODE").toArray()[0].toString());
        //从header中获取系统名称
        interfaceLogDomain.setRemoteSysName(request.headers().get("SYS_NAME") == null || request.headers().get("SYS_NAME").isEmpty() ? "UNKNOWN" : request.headers().get("SYS_NAME").toArray()[0].toString());
        interfaceLogDomain.setRequestTime(new Date());
        interfaceLogDomain.setStatus(InterfaceLogStatus.DOING.getCode());
        interfaceLogDomain.setRequestMethod(request.httpMethod().name());//请求方式
        interfaceLogDomain.setRequestUrl(request.url()); //请求地址
        Map<String, Collection<String>> headers = request.headers();//请求头
        if (request.body() != null) {
            interfaceLogDomain.setRequestMessage(request.body().toString());//请求内容
        }
        //存入数据库
        logMapper.insert(interfaceLogDomain);
        //放入线程缓存中,等待请求完成或者异常时，从线程缓存中拿出日志对象，回填数据
        FeignRequestContextHolder.setThreadLocalInterfaceLog(interfaceLogDomain);
    }

    /**
     * 当配置了接口重试机制时，再接口重试之前调用该方法
     *
     * @param configKey
     * @param logLevel
     */
    @Override
    protected void logRetry(String configKey, Level logLevel) {

    }

    /**
     * 当接口调用异常时，会调用该接口，如果配置了重试机制，该方法在logRetry之前调用
     *
     * @param configKey
     * @param logLevel
     * @param ioe
     * @param elapsedTime
     * @return
     */
    @Override
    protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {

        //从线程缓存中获取日志信息，用于信息回填存入数据库
        InterfaceLogDomain threadLocalInterfaceLog = FeignRequestContextHolder.getThreadLocalInterfaceLog();
        if (threadLocalInterfaceLog != null) {
            threadLocalInterfaceLog.setStatus(InterfaceLogStatus.ERROR.getCode());//回填响应状态
            threadLocalInterfaceLog.setResponseTime(new Date()); //回填响应时间
            threadLocalInterfaceLog.setResponseMessage(ioe.getMessage());
            try {
                logMapper.updateById(threadLocalInterfaceLog);
            } catch (Throwable throwable) {
                //清除线程缓存
                FeignRequestContextHolder.clear();
            }
        }
        return ioe;
    }

    /**
     * 接口调用成功后，调用该方法记录日志信息
     *
     * @param configKey
     * @param logLevel
     * @param response
     * @param elapsedTime
     * @return
     * @throws IOException
     */
    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        //从线程缓存中获取日志信息，用于信息回填存入数据库
        InterfaceLogDomain threadLocalInterfaceLog = FeignRequestContextHolder.getThreadLocalInterfaceLog();
        if (threadLocalInterfaceLog != null) {
            try {
                threadLocalInterfaceLog.setResponseTime(new Date());//回写接口响应时间
                threadLocalInterfaceLog.setStatus(InterfaceLogStatus.SUCCESS.getCode());//回写接口日志状态
                int status = response.status();
                int bodyLength;
                if (response.body() != null && !(status == 204 || status == 205)) {
                    // HTTP 204 No Content "...response MUST NOT include a message-body"
                    // HTTP 205 Reset Content "...response MUST NOT include an entity"
                    //获取响应内容
                    byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                    //内容长度
                    bodyLength = bodyData.length;
                    if (bodyLength > 0) {
                        String responseMessage = decodeOrDefault(bodyData, UTF_8, "Binary data");
                        threadLocalInterfaceLog.setResponseMessage(responseMessage);
                    }
                    return response.toBuilder().body(bodyData).build();
                }
            } finally {
                logMapper.updateById(threadLocalInterfaceLog);
                FeignRequestContextHolder.clear();
            }
        }
        return response;
    }
}

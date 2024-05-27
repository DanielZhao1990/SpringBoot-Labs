package com.example.dlab.config;

import com.example.dlab.log.FeignRemoteLogger;
import com.example.dlab.log.InterfaceLogMapper;
import feign.Logger;
import feign.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig {
    private String tokenId="123124";
    private Long connectTimeoutMillis;
    private Long readTimeoutMillis;
    private boolean followRedirects;
    private Long maxAttempts;
    private Long period;
    private Long maxPeriod;
    @Autowired
    private InterfaceLogMapper interfaceLogMapper;

    /**
     * 自定义拦截器
     * @return
     */

    @Bean
    public FeignAuthRequestInterceptor feignAuthRequestInterceptor() {
        return new FeignAuthRequestInterceptor(tokenId);
    }

    /**
     * 配置openfeign调用接口时的超时时间和等待超时时间
     *
     * @return Request.Options
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(1000*10, 1000*120, followRedirects);
//        return new Request.Options(connectTimeoutMillis, TimeUnit.SECONDS, readTimeoutMillis, TimeUnit.SECONDS, followRedirects);
    }


    /**
     * 参考SynchronousMethodHandler源码，executeAndDecode方法中，当日志等级不等于Logger.Level.NONE才触发日志显示
     *
     * @return 返回日志等级，openfeign根据日志等级来显示请求响应日志
     */
    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }

    /**
     * 配置openfeign自定义请求日志记录，将请求日志存入MQ或者数据库
     *
     * @return
     */
    @Bean
    public Logger feignRemoteLogger() {
        return new FeignRemoteLogger(interfaceLogMapper);
    }
}

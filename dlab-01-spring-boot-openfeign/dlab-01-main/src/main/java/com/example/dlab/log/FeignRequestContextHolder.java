package com.example.dlab.log;

public class FeignRequestContextHolder {

    private static final ThreadLocal<InterfaceLogDomain> THREAD_LOCAL_INTERFACE_LOG = new ThreadLocal<>();

    /**
     * 获取当前线程缓存的日志信息
     *
     * @return
     */
    public static InterfaceLogDomain getThreadLocalInterfaceLog() {
        return THREAD_LOCAL_INTERFACE_LOG.get();
    }

    /**
     * 设置当前线程接口请求的日志信息
     *
     * @param interfaceLogDomain
     */
    public static void setThreadLocalInterfaceLog(InterfaceLogDomain interfaceLogDomain) {
        THREAD_LOCAL_INTERFACE_LOG.set(interfaceLogDomain);
    }

    /**
     * 清除数据
     */
    public static void clear() {
        THREAD_LOCAL_INTERFACE_LOG.remove();
    }
}

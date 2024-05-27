package com.example.dlab.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: DI.YIN
 * @Date: 2024/3/13 16:34
 * @Version: 1.0.0
 * @Description: 接口日志
 **/
@Service
@Slf4j
public class InterfaceLogMapper  {
    public void insert(InterfaceLogDomain interfaceLogDomain) {
        log.info("InterfaceLogMapper insert:{}", interfaceLogDomain);
    }

    public void updateById(InterfaceLogDomain threadLocalInterfaceLog) {
        log.info("InterfaceLogMapper updateById:{}", threadLocalInterfaceLog);
    }
}

package com.example.dlab.master;

import com.example.dlab.api.CorrectResponse;
import com.example.dlab.api.CorrectService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "server.mode", havingValue = "master")
public class MasterCorrectService implements CorrectService {
    public CorrectResponse correct(String engine, String originalText) {
        // 调用本地服务并返回结果
        CorrectResponse response = new CorrectResponse();
        return response;
    }
}

package com.example.dlab.slave;

import com.example.dlab.api.CorrectRequest;
import com.example.dlab.api.CorrectResponse;
import com.example.dlab.api.CorrectService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
@ConditionalOnProperty(name = "server.mode", havingValue = "slave")
public class SlaveCorrectService implements CorrectService {
    private final FeignServerClient feignServerClient;

    @Autowired
    public SlaveCorrectService(FeignServerClient feignServerClient) {
        this.feignServerClient = feignServerClient;
    }

    public CorrectResponse correct(String engine, String originalText) {
        CorrectRequest request = new CorrectRequest();
        request.setEngine(engine);
        request.setOriginalText(originalText);
        // 远程调用主服务器的服务
        return feignServerClient.correct(request);
    }
}

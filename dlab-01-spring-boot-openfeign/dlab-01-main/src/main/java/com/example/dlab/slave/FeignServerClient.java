package com.example.dlab.slave;

import com.example.dlab.api.CorrectRequest;
import com.example.dlab.api.CorrectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "master-server", url = "${server.master.url}")
public interface FeignServerClient {
    @PostMapping("/api/correct")
    CorrectResponse correct(@RequestBody CorrectRequest request);
}

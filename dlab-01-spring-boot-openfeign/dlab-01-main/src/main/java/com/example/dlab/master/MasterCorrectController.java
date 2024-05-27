package com.example.dlab.master;

import com.example.dlab.api.CorrectRequest;
import com.example.dlab.api.CorrectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(name = "server.mode", havingValue = "master")
public class MasterCorrectController {

    @Autowired
    private MasterCorrectService masterCorrectService;

    @PostMapping("/api/correct")
    public CorrectResponse correct(@RequestBody CorrectRequest request) {
        int a = 1 / 0;
        return masterCorrectService.correct(request.getEngine(), request.getOriginalText());
    }
}

package com.example.dlab.business;

import com.example.dlab.api.CorrectResponse;
import com.example.dlab.api.CorrectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private CorrectService correctService;

    @GetMapping("/correct")
    public CorrectResponse someMethod() {
        CorrectResponse correctResponse = correctService.correct("engine", "originalText");
        return correctResponse;
    }
}

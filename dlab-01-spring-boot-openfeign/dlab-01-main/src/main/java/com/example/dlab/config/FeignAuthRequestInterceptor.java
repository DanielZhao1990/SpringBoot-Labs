package com.example.dlab.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignAuthRequestInterceptor implements RequestInterceptor {

    private String tokenId="abc";

    public FeignAuthRequestInterceptor(String tokenId) {
        this.tokenId = tokenId;
    }

    public void apply(RequestTemplate template) {
        template.header("Authorization", tokenId);

        log.info("FeignAuthRequestInterceptor apply");
    }
}

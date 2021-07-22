package com.sai.client.controller;

import com.sai.client.feign.GatewayProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class GatewayController {

    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

    @Autowired
    private GatewayProxy proxy;

    @RequestMapping("/courseFallback")
    public ResponseEntity<?> courseFallback() {
        String object = proxy.courseFallback();
        return ResponseEntity.ok(object);
    }

    @RequestMapping("/userFallback")
    public ResponseEntity<?> userFallback() {
        String object = proxy.userFallback();
        System.out.println(object);
        return ResponseEntity.ok(object);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody Object loginRequest) {
        Object object = proxy.login(loginRequest);
        return ResponseEntity.ok(object);
    }

    @PostMapping(value = "/token/refresh", consumes = "application/json")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String bearerToken, @RequestBody Object loginRequest) {
        Object object = proxy.refresh(bearerToken, loginRequest);
        System.out.println(object);
        return ResponseEntity.ok(object);
    }

}

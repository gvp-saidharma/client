package com.sai.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="gateway")
public interface GatewayProxy {

    @GetMapping("/userFallback")
    public String userFallback();

    @GetMapping("/courseFallback")
    public String courseFallback();

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public Object retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to);

    @PostMapping("/api/auth/login")
    public Object login(@RequestBody Object object);

    @PostMapping("/token/refresh")
    public Object refresh(@RequestHeader("Authorization") String bearerToken, @RequestBody Object loginRequest);
}

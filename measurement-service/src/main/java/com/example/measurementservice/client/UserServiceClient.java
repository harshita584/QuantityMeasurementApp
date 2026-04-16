package com.example.measurementservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service", fallbackFactory = UserServiceFallbackFactory.class)
public interface UserServiceClient {

    @PostMapping("/api/users/{userId}/history")
    Map<String, Object> saveHistory(@PathVariable("userId") Long userId, @RequestBody Map<String, Object> record);

    @GetMapping("/api/users/{userId}/history")
    List<Map<String, Object>> getHistory(@PathVariable("userId") Long userId);
}

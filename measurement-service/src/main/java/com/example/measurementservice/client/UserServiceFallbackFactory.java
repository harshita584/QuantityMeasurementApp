package com.example.measurementservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserServiceClient> {

    private static final Logger log = LoggerFactory.getLogger(UserServiceFallbackFactory.class);

    @Override
    public UserServiceClient create(Throwable cause) {
        return new UserServiceClient() {
            @Override
            public Map<String, Object> saveHistory(Long userId, Map<String, Object> record) {
                log.error("Fallback triggered when saving history for userId: {}. Reason: {}", userId, cause.getMessage());
                // Fail silently as required, returning empty map
                return Collections.emptyMap();
            }

            @Override
            public List<Map<String, Object>> getHistory(Long userId) {
                log.error("Fallback triggered when getting history for userId: {}. Reason: {}", userId, cause.getMessage());
                return Collections.emptyList();
            }
        };
    }
}

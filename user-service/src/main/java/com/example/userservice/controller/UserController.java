package com.example.userservice.controller;

import com.example.userservice.model.QuantityMeasurementEntity;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Modified to return the saved entity for better contract
    @PostMapping("/{userId}/history")
    public QuantityMeasurementEntity saveHistory(@PathVariable("userId") Long userId, @RequestBody QuantityMeasurementEntity history) {
        return userService.saveHistory(userId, history);
    }

    @GetMapping("/{userId}/history")
    public List<QuantityMeasurementEntity> getHistory(@PathVariable("userId") Long userId) {
        return userService.getHistory(userId);
    }

    @GetMapping("/history")
    public List<QuantityMeasurementEntity> getAllHistory() {
        return userService.getAllHistory();
    }

    @GetMapping("/history/{operation}")
    public List<QuantityMeasurementEntity> getHistoryByOperation(@PathVariable String operation) {
        return userService.getHistoryByOperation(operation);
    }
}

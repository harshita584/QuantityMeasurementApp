package com.example.measurementservice.controller;

import com.example.measurementservice.dto.QuantityDTO;
import com.example.measurementservice.dto.QuantityInputDTO;
import com.example.measurementservice.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quantities")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    // Optional user ID appended for history tracking, defaulting to 1 since we don't have full auth wired yet.
    private static final Long DEFAULT_USER_ID = 1L;

    @PostMapping("/compare")
    public boolean compare(@RequestBody QuantityInputDTO input, @RequestParam(required = false) Double userId) {
        return measurementService.compare(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO(),
                userId != null ? userId.longValue() : DEFAULT_USER_ID
        );
    }

    @PostMapping("/convert/{targetUnit}")
    public QuantityDTO convert(@RequestBody QuantityDTO input, @PathVariable String targetUnit, @RequestParam(required = false) Double userId) {
        return measurementService.convert(input, targetUnit, userId != null ? userId.longValue() : DEFAULT_USER_ID);
    }

    @PostMapping("/add")
    public QuantityDTO add(@RequestBody QuantityInputDTO input, @RequestParam(required = false) Double userId) {
        return measurementService.add(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO(),
                userId != null ? userId.longValue() : DEFAULT_USER_ID
        );
    }

    @PostMapping("/subtract")
    public QuantityDTO subtract(@RequestBody QuantityInputDTO input, @RequestParam(required = false) Double userId) {
        return measurementService.subtract(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO(),
                userId != null ? userId.longValue() : DEFAULT_USER_ID
        );
    }

    @PostMapping("/divide")
    public double divide(@RequestBody QuantityInputDTO input, @RequestParam(required = false) Double userId) {
        return measurementService.divide(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO(),
                userId != null ? userId.longValue() : DEFAULT_USER_ID
        );
    }
}

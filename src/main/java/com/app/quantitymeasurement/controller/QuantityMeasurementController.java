package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {
    private final IQuantityMeasurementService service;

    private static final Logger log = LoggerFactory.getLogger(ClassName.class);

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> performComparison(@Valid @RequestBody QuantityInputDTO input) {
    	log.info(input.toString());
    	return ResponseEntity.ok(service.compare(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert quantity to target unit")
    public ResponseEntity<QuantityMeasurementDTO> performConversion(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.convert(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> performAddition(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    // ADD WITH TARGET UNIT
    @PostMapping("/add-with-target-unit")
    @Operation(summary = "Add two quantities with target unit")
    public ResponseEntity<QuantityMeasurementDTO> performAdditionWithTargetUnit(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO(), input.getTargetQuantityDTO()));
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> performSubtraction(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.subtract( input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    // SUBTRACT WITH TARGET UNIT
    @PostMapping("/subtract-with-target-unit")
    @Operation(summary = "Subtract two quantities with target unit")
    public ResponseEntity<QuantityMeasurementDTO> performSubtractionWithTargetUnit(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO(), input.getTargetQuantityDTO()));
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> performDivision(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.divide(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get operation history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable String operation) {
        return ResponseEntity.ok(service.getOperationHistory(operation));
    }

    @GetMapping("/history/type/{type}")
    @Operation(summary = "Get history by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistoryByType(@PathVariable String type) {
        return ResponseEntity.ok(service.getMeasurementsByType(type));
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get operation count")
    public ResponseEntity<Long> getOperationCount(@PathVariable String operation) {
        return ResponseEntity.ok(service.getOperationCount(operation));
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get errored operations history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErroredOperations() {
        return ResponseEntity.ok(service.getErrorHistory());
    }
}
package com.app.quantitymeasurement.service;

import java.util.List;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;

public interface IQuantityMeasurementService {
    QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

    QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

    QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    // Get operation history by operation type - ADD, COMPARE
    List<QuantityMeasurementDTO> getOperationHistory(String operation);

    // Get history by measurement type (LengthUnit, WeightUnit
    List<QuantityMeasurementDTO> getMeasurementsByType(String type);

    // Get count of successful operations
    long getOperationCount(String operation);

    // Get all failed/error operations
    List<QuantityMeasurementDTO> getErrorHistory();
}
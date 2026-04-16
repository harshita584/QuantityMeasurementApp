package com.example.measurementservice.service;

import com.example.measurementservice.client.UserServiceClient;
import com.example.measurementservice.core.LengthUnit;
import com.example.measurementservice.core.Quantity;
import com.example.measurementservice.core.TemperatureUnit;
import com.example.measurementservice.core.VolumeUnit;
import com.example.measurementservice.core.WeightUnit;
import com.example.measurementservice.dto.QuantityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MeasurementService {

    @Autowired
    private UserServiceClient userServiceClient;

    private String normalizeUnit(String unit) {
        if (unit == null) return "UNKNOWN";
        return switch (unit.toLowerCase()) {
            case "ft", "feet" -> "FEET";
            case "in", "inch", "inches" -> "INCHES";
            case "yd", "yard", "yards" -> "YARDS";
            case "cm", "centimeter", "centimeters" -> "CENTIMETERS";
            case "m", "meter", "meters" -> "METERS";
            case "km", "kilometer", "kilometers" -> "KILOMETERS";
            case "mm", "millimeter", "millimeters" -> "MILLIMETERS";
            case "mi", "mile", "miles" -> "MILES";
            case "kg", "kilogram", "kilograms" -> "KILOGRAM";
            case "g", "gram", "grams" -> "GRAM";
            case "mg", "milligram", "milligrams" -> "MILLIGRAM";
            case "lb", "lbs", "pound", "pounds" -> "POUND";
            case "oz", "ounce", "ounces" -> "OUNCE";
            case "ton", "tonne", "tonnes" -> "TONNE";
            case "l", "litre", "litres", "liter", "liters" -> "LITRE";
            case "ml", "millilitre", "milliliter" -> "MILLILITRE";
            case "gal", "gallon", "gallons" -> "GALLON";
            case "pint", "pints" -> "PINT";
            case "cup", "cups" -> "CUP";
            case "c", "celsius" -> "CELSIUS";
            case "f", "fahrenheit" -> "FAHRENHEIT";
            case "k", "kelvin" -> "KELVIN";
            default -> unit.toUpperCase();
        };
    }

    private Quantity<?> createQuantity(QuantityDTO dto) {
        String normalizedUnit = normalizeUnit(dto.getUnit());
        
        try { return new Quantity<>(dto.getValue(), LengthUnit.valueOf(normalizedUnit)); } catch (Exception ignored) {}
        try { return new Quantity<>(dto.getValue(), WeightUnit.valueOf(normalizedUnit)); } catch (Exception ignored) {}
        try { return new Quantity<>(dto.getValue(), VolumeUnit.valueOf(normalizedUnit)); } catch (Exception ignored) {}
        try { return new Quantity<>(dto.getValue(), TemperatureUnit.valueOf(normalizedUnit)); } catch (Exception ignored) {}

        throw new IllegalArgumentException("Unsupported Unit: " + dto.getUnit() + " (normalized: " + normalizedUnit + ")");
    }

    public boolean compare(QuantityDTO q1, QuantityDTO q2, Long userId) {
        boolean result = createQuantity(q1).equals(createQuantity(q2));
        saveHistoryIfRequested(userId, "COMPARE", q1, q2, String.valueOf(result), null);
        return result;
    }

    @SuppressWarnings("unchecked")
    public QuantityDTO convert(QuantityDTO input, String targetUnit, Long userId) {
        try {
            Quantity quantity = createQuantity(input);
            Quantity targetDummy = createQuantity(new QuantityDTO(0.0, targetUnit));
            
            Quantity converted = quantity.convertTo(targetDummy.getUnit());
            
            QuantityDTO result = new QuantityDTO(converted.getValue(), converted.getUnit().toString());
            saveHistoryIfRequested(userId, "CONVERT", input, new QuantityDTO(0.0, targetUnit), result.getValue() + " " + result.getUnit(), null);
            return result;
        } catch (Exception e) {
            saveHistoryIfRequested(userId, "CONVERT", input, new QuantityDTO(0.0, targetUnit), null, e.getMessage());
            throw e;
        }
    }

    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, Long userId) {
        try {
            Quantity<?> result = ((Quantity) createQuantity(q1)).add((Quantity) createQuantity(q2));
            QuantityDTO resDto = new QuantityDTO(result.getValue(), result.getUnit().toString());
            saveHistoryIfRequested(userId, "ADD", q1, q2, resDto.getValue() + " " + resDto.getUnit(), null);
            return resDto;
        } catch (Exception e) {
            saveHistoryIfRequested(userId, "ADD", q1, q2, null, e.getMessage());
            throw e;
        }
    }

    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, Long userId) {
        try {
            Quantity<?> result = ((Quantity) createQuantity(q1)).subtract((Quantity) createQuantity(q2));
            QuantityDTO resDto = new QuantityDTO(result.getValue(), result.getUnit().toString());
            saveHistoryIfRequested(userId, "SUBTRACT", q1, q2, resDto.getValue() + " " + resDto.getUnit(), null);
            return resDto;
        } catch (Exception e) {
            saveHistoryIfRequested(userId, "SUBTRACT", q1, q2, null, e.getMessage());
            throw e;
        }
    }

    public double divide(QuantityDTO q1, QuantityDTO q2, Long userId) {
        try {
            double result = ((Quantity) createQuantity(q1)).divide((Quantity) createQuantity(q2));
            saveHistoryIfRequested(userId, "DIVIDE", q1, q2, String.valueOf(result), null);
            return result;
        } catch (Exception e) {
            saveHistoryIfRequested(userId, "DIVIDE", q1, q2, null, e.getMessage());
            throw e;
        }
    }

    private void saveHistoryIfRequested(Long userId, String operation, QuantityDTO op1, QuantityDTO op2, String resultStr, String errorMsg) {
        // According to user requirements: save memory to user-service using Feign
        try {
            String strOp1 = op1 != null ? op1.getValue() + " " + op1.getUnit() : null;
            String strOp2 = op2 != null ? op2.getValue() + " " + op2.getUnit() : null;
            
            // Map directly. I will use a Map to send the structure to avoid duplicate DTO classes
            Map<String, Object> entityMap = Map.of(
                    "operation", operation != null ? operation : "",
                    "operand1", strOp1 != null ? strOp1 : "",
                    "operand2", strOp2 != null ? strOp2 : "",
                    "result", resultStr != null ? resultStr : "",
                    "errorMessage", errorMsg != null ? errorMsg : ""
            );

            // Since user-service expects QuantityMeasurementEntity, I will change the Feign client signature to map this.
            userServiceClient.saveHistory(userId != null ? userId : 0L, entityMap);
        } catch (Exception e) {
            // Fail silently due to microservices resilience requirements
        }
    }
}

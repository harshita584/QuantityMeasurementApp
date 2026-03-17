package com.apps.quantitymeasurement.services;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.entity.QuantityModel;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.unit.*;

import java.util.Objects;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private enum Operation {
        COMPARE, CONVERT, ADD, SUBTRACT, DIVIDE 
    }

    @Override
    public boolean compare(QuantityDTO q1DTO, QuantityDTO q2DTO) {
        try {
            validateOperands(q1DTO, q2DTO); 
            QuantityModel<IMeasurable> q1 = getQuantityModel(q1DTO);
            QuantityModel<IMeasurable> q2 = getQuantityModel(q2DTO);

            double val1 = q1.getUnit().convertToBaseUnit(q1.getValue());
            double val2 = q2.getUnit().convertToBaseUnit(q2.getValue());
            
            boolean result = Double.compare(val1, val2) == 0;
            String resultStr = result ? "Equal" : "Not Equal"; 

            repository.save(new QuantityMeasurementEntity(q1DTO, q2DTO, Operation.COMPARE.name(), resultStr));
            return result;
        } catch (Exception e) {
            handleException(q1DTO, q2DTO, Operation.COMPARE.name(), e);
            throw e;
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO inputDTO, QuantityDTO targetDTO) {
        try {
            validateOperands(inputDTO, targetDTO);
            QuantityModel<IMeasurable> input = getQuantityModel(inputDTO);
            IMeasurable targetUnit = getQuantityModel(targetDTO).getUnit();

            double baseVal = input.getUnit().convertToBaseUnit(input.getValue());
            double convertedVal = targetUnit.convertFromBaseUnit(baseVal);

            QuantityDTO resultDTO = new QuantityDTO(convertedVal, targetUnit.getUnitName(), inputDTO.getMeasurementType());
            repository.save(new QuantityMeasurementEntity(inputDTO, targetDTO, Operation.CONVERT.name(), resultDTO));
            return resultDTO;
        } catch (Exception e) {
            handleException(inputDTO, targetDTO, Operation.CONVERT.name(), e);
            throw e;
        }
    }

    @Override
    public QuantityDTO add(QuantityDTO q1DTO, QuantityDTO q2DTO) {
        return add(q1DTO, q2DTO, q1DTO); 
    }

    @Override
    public QuantityDTO add(QuantityDTO q1DTO, QuantityDTO q2DTO, QuantityDTO targetDTO) {
        return performArithmetic(q1DTO, q2DTO, targetDTO, Operation.ADD);
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1DTO, QuantityDTO q2DTO) {
        return subtract(q1DTO, q2DTO, q1DTO);
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1DTO, QuantityDTO q2DTO, QuantityDTO targetDTO) {
        return performArithmetic(q1DTO, q2DTO, targetDTO, Operation.SUBTRACT);
    }

    @Override
    public double divide(QuantityDTO q1DTO, QuantityDTO q2DTO) {
        try {
            validateOperands(q1DTO, q2DTO);
            QuantityModel<IMeasurable> q1 = getQuantityModel(q1DTO);
            QuantityModel<IMeasurable> q2 = getQuantityModel(q2DTO);

            q1.getUnit().validateOperationSupport(Operation.DIVIDE.name()); 

            double val1 = q1.getUnit().convertToBaseUnit(q1.getValue());
            double val2 = q2.getUnit().convertToBaseUnit(q2.getValue());

            if (val2 == 0) throw new ArithmeticException("Division by zero"); 

            double result = val1 / val2;
            repository.save(new QuantityMeasurementEntity(q1DTO, q2DTO, Operation.DIVIDE.name(), String.valueOf(result)));
            return result;
        } catch (Exception e) {
            handleException(q1DTO, q2DTO, Operation.DIVIDE.name(), e);
            throw e;
        }
    }

    
    private QuantityDTO performArithmetic(QuantityDTO q1DTO, QuantityDTO q2DTO, QuantityDTO targetDTO, Operation op) {
        try {
            validateOperands(q1DTO, q2DTO);
            QuantityModel<IMeasurable> q1 = getQuantityModel(q1DTO);
            QuantityModel<IMeasurable> q2 = getQuantityModel(q2DTO);
            IMeasurable targetUnit = getQuantityModel(targetDTO).getUnit();
 
            q1.getUnit().validateOperationSupport(op.name());

            double v1 = q1.getUnit().convertToBaseUnit(q1.getValue());
            double v2 = q2.getUnit().convertToBaseUnit(q2.getValue());
            
            double baseResult = (op == Operation.ADD) ? (v1 + v2) : (v1 - v2);
            double finalVal = targetUnit.convertFromBaseUnit(baseResult);

            QuantityDTO resultDTO = new QuantityDTO(finalVal, targetUnit.getUnitName(), q1DTO.getMeasurementType());
            repository.save(new QuantityMeasurementEntity(q1DTO, q2DTO, op.name(), resultDTO));
            return resultDTO;
        } catch (Exception e) {
            handleException(q1DTO, q2DTO, op.name(), e);
            throw e;
        }
    }

    private void validateOperands(QuantityDTO q1, QuantityDTO q2) {
        if (q1 == null || q2 == null) throw new IllegalArgumentException("Operands cannot be null"); 
        if (!Objects.equals(q1.getMeasurementType(), q2.getMeasurementType())) { 
            throw new IllegalArgumentException("Category mismatch: " + q1.getMeasurementType() + " vs " + q2.getMeasurementType());
        }
    }

    private QuantityModel<IMeasurable> getQuantityModel(QuantityDTO dto) { 
        IMeasurable unit;
        switch (dto.getMeasurementType().toUpperCase()) { 
            case "LENGTHUNIT": unit = LengthUnit.valueOf(dto.getUnit()); break;
            case "WEIGHTUNIT": unit = WeightUnit.valueOf(dto.getUnit()); break;
            case "VOLUMEUNIT": unit = VolumeUnit.valueOf(dto.getUnit()); break;
            case "TEMPERATUREUNIT": unit = TemperatureUnit.valueOf(dto.getUnit()); break;
            default: throw new IllegalArgumentException("Unsupported category: " + dto.getMeasurementType());
        }
        return new QuantityModel<>(dto.getValue(), unit); 
    }

    private void handleException(QuantityDTO q1, QuantityDTO q2, String op, Exception e) { 
        repository.save(new QuantityMeasurementEntity(q1, q2, op, e.getMessage(), true)); 
    }
    
    public static void main(String[] args) {
        
        IQuantityMeasurementRepository repository = QuantityMeasurementCacheRepository.getInstance();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);

        System.out.println("--- UC15 Functional Test: Service Layer ---");

        try {
           
            QuantityDTO inchesDTO = new QuantityDTO(1, "FEET", "LengthUnit");
            QuantityDTO feetTargetDTO = new QuantityDTO(0.0, "INCHES", "LengthUnit");
            
            QuantityDTO converted = service.convert(inchesDTO, feetTargetDTO);
            System.out.println("Conversion (24 IN to FT): " + converted.getValue() + " " + converted.getUnit());

           
            QuantityDTO feetDTO = new QuantityDTO(1.0, "FEET", "LengthUnit");
            QuantityDTO inchesToAddDTO = new QuantityDTO(12.0, "INCHES", "LengthUnit");
            
            QuantityDTO sumResult = service.add(feetDTO, inchesToAddDTO);
            System.out.println("Addition (1 FT + 12 IN): " + sumResult.getValue() + " " + sumResult.getUnit());

           
            System.out.println("\nTesting Category Mismatch (Length + Weight)...");
            QuantityDTO weightDTO = new QuantityDTO(1.0, "KILOGRAM", "WeightUnit");
            service.add(feetDTO, weightDTO); 

        } catch (Exception e) {
            System.err.println("Caught Expected Error: " + e.getMessage());
        }

        System.out.println("\n--- Repository Audit Log (Persisted Entities) ---");
        repository.getAllMeasurements().forEach(System.out::println);
    }
}
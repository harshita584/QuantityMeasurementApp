package com.app.quantitymeasurement.service.impl;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.*;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.unit.IMeasurable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
    private final QuantityMeasurementRepository repository;


    private IMeasurable getUnit(QuantityDTO dto) {
        try {
            return IMeasurable.getUnitInstance(
                    dto.unit,
                    Class.forName("com.app.quantitymeasurement.unit." + dto.measurementType)
            );
        } catch (Exception e) {
        	log.info("Invalid unit " + dto.measurementType);
            throw new QuantityMeasurementException("Invalid unit");
        }
    }

    private double toBase(QuantityDTO dto) {
        return getUnit(dto).convertToBaseUnit(dto.value);
    }

    private double fromBase(double value, IMeasurable unit) {
        return unit.convertFromBaseUnit(value);
    }

    private void validateSameType(QuantityDTO a, QuantityDTO b) {
        if (!a.measurementType.equals(b.measurementType)) {
            throw new QuantityMeasurementException("Different measurement types not allowed");
        }
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO thisDTO, QuantityDTO thatDTO) {
        try {
        	log.info("validating compare request");
            validateSameType(thisDTO, thatDTO);

            double base1 = toBase(thisDTO);
            double base2 = toBase(thatDTO);
            
            log.info(base1 + " " + base2);

            boolean result = Double.compare(base1, base2) == 0;

            return saveAndReturn(thisDTO, thatDTO, OperationType.COMPARE,
                    String.valueOf(result), 0, null, null, false, null);

        } catch (Exception e) {
            return saveError(thisDTO, thatDTO, OperationType.COMPARE, e);
        }
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO thisDTO, QuantityDTO targetDTO) {
        try {
            validateSameType(thisDTO, targetDTO);

            IMeasurable source = getUnit(thisDTO);
            IMeasurable target = getUnit(targetDTO);

            double base = source.convertToBaseUnit(thisDTO.value);
            double result = target.convertFromBaseUnit(base);

            return saveAndReturn(thisDTO, targetDTO, OperationType.CONVERT,
                    null, result, targetDTO.unit, targetDTO.measurementType, false, null);

        } catch (Exception e) {
            return saveError(thisDTO, targetDTO, OperationType.CONVERT, e);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO a, QuantityDTO b) {
        try {
            validateSameType(a, b);

            IMeasurable unit = getUnit(a);
            unit.validateOperationSupport("ADD");

            double base = toBase(a) + toBase(b);
            double result = fromBase(base, unit);

            return saveAndReturn(a, b, OperationType.ADD,
                    null, result, a.unit, a.measurementType, false, null);

        } catch (Exception e) {
            return saveError(a, b, OperationType.ADD, e);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO a, QuantityDTO b, QuantityDTO target) {
        try {
            validateSameType(a, b);

            IMeasurable targetUnit = getUnit(target);

            double base = toBase(a) + toBase(b);
            double result = fromBase(base, targetUnit);

            return saveAndReturn(a, b, OperationType.ADD,
                    null, result, target.unit, target.measurementType, false, null);

        } catch (Exception e) {
            return saveError(a, b, OperationType.ADD, e);
        }
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO a, QuantityDTO b) {
        try {
            validateSameType(a, b);

            IMeasurable unit = getUnit(a);
            unit.validateOperationSupport("SUBTRACT");

            double base = toBase(a) - toBase(b);
            double result = fromBase(base, unit);

            return saveAndReturn(a, b, OperationType.SUBTRACT,
                    null, result, a.unit, a.measurementType, false, null);

        } catch (Exception e) {
            return saveError(a, b, OperationType.SUBTRACT, e);
        }
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO a, QuantityDTO b, QuantityDTO target) {
        try {
            validateSameType(a, b);

            IMeasurable targetUnit = getUnit(target);

            double base = toBase(a) - toBase(b);
            double result = fromBase(base, targetUnit);

            return saveAndReturn(a, b, OperationType.SUBTRACT,
                    null, result, target.unit, target.measurementType, false, null);

        } catch (Exception e) {
            return saveError(a, b, OperationType.SUBTRACT, e);
        }
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO a, QuantityDTO b) {
        try {
            validateSameType(a, b);

            double base1 = toBase(a);
            double base2 = toBase(b);

            if (base2 == 0) throw new ArithmeticException("Divide by zero");

            double result = base1 / base2;

            return saveAndReturn(a, b, OperationType.DIVIDE,
                    null, result, null, null, false, null);

        } catch (Exception e) {
            return saveError(a, b, OperationType.DIVIDE, e);
        }
    }

    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
        return QuantityMeasurementDTO.fromList(repository.findByOperation(operation));
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {
        return QuantityMeasurementDTO.fromList(repository.findByThisMeasurementType(type));
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndIsErrorFalse(operation);
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromList(repository.findByIsErrorTrue());
    }


    private QuantityMeasurementDTO saveAndReturn(
            QuantityDTO a,
            QuantityDTO b,
            OperationType op,
            String resultString,
            double resultValue,
            String resultUnit,
            String resultType,
            boolean error,
            String errorMsg) {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        a.value, a.unit, a.measurementType,
                        b.value, b.unit, b.measurementType,
                        op.name(),
                        resultValue,
                        resultUnit,
                        resultType
                );

        entity.resultString = resultString;
        entity.isError = error;
        entity.errorMessage = errorMsg;

        return QuantityMeasurementDTO.from(repository.save(entity));
    }

    private QuantityMeasurementDTO saveError(
            QuantityDTO a,
            QuantityDTO b,
            OperationType op,
            Exception e) {

        return saveAndReturn(a, b, op, null, 0, null, null, true, e.getMessage());
    }
}
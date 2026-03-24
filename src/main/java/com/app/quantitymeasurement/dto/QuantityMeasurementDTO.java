package com.app.quantitymeasurement.dto;

import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class QuantityMeasurementDTO {
    public double thisValue;
    public String thisUnit;
    public String thisMeasurementType;

    public double thatValue;
    public String thatUnit;
    public String thatMeasurementType;

    public String operation;

    public String resultString;
    public double resultValue;
    public String resultUnit;
    public String resultMeasurementType;

    public String errorMessage;

    /**
     * Rename field from isError -> error
     * Jackson strips "is" prefix from boolean getters.
     */
    @JsonProperty("error")
    public boolean error;

    // Convert Entity to DTO
    public static QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {
        if (entity == null) return null;

        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();

        dto.thisValue = entity.thisValue;
        dto.thisUnit = entity.thisUnit;
        dto.thisMeasurementType = entity.thisMeasurementType;

        dto.thatValue = entity.thatValue;
        dto.thatUnit = entity.thatUnit;
        dto.thatMeasurementType = entity.thatMeasurementType;

        dto.operation = entity.operation;

        dto.resultString = entity.resultString;
        dto.resultValue = entity.resultValue;
        dto.resultUnit = entity.resultUnit;
        dto.resultMeasurementType = entity.resultMeasurementType;

        dto.errorMessage = entity.errorMessage;
        dto.error = entity.isError;

        return dto;
    }

    // Convert DTO to Entity
    public QuantityMeasurementEntity toEntity() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.thisValue = this.thisValue;
        entity.thisUnit = this.thisUnit;
        entity.thisMeasurementType = this.thisMeasurementType;

        entity.thatValue = this.thatValue;
        entity.thatUnit = this.thatUnit;
        entity.thatMeasurementType = this.thatMeasurementType;

        entity.operation = this.operation;

        entity.resultString = this.resultString;
        entity.resultValue = this.resultValue;
        entity.resultUnit = this.resultUnit;
        entity.resultMeasurementType = this.resultMeasurementType;

        entity.errorMessage = this.errorMessage;
        entity.isError = this.error;

        return entity;
    }

    // Convert list of entities to list of DTOs
    public static List<QuantityMeasurementDTO> fromList(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::from)
                .collect(Collectors.toList());
    }

    // Convert list of DTOs to list of entities
    public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
        return dtos.stream()
                .map(QuantityMeasurementDTO::toEntity)
                .collect(Collectors.toList());
    }
}
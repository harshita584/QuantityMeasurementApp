package com.app.quantitymeasurement.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quantity_measurement_entity", indexes = {
        @Index(name = "idx_operation", columnList = "operation"),
        @Index(name = "idx_measurement_type", columnList = "this_measurement_type"),
        @Index(name = "idx_created_at", columnList = "created_at")
})
public class QuantityMeasurementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "this_value", nullable = false)
    public double thisValue;

    @Column(name = "this_unit", nullable = false)
    public String thisUnit;

    @Column(name = "this_measurement_type", nullable = false)
    public String thisMeasurementType;

    @Column(name = "that_value", nullable = false)
    public double thatValue;

    @Column(name = "that_unit", nullable = false)
    public String thatUnit;

    @Column(name = "that_measurement_type", nullable = false)
    public String thatMeasurementType;

    // e.g., "COMPARE", "CONVERT", "ADD", "SUBTRACT", "DIVIDE"
    @Column(name = "operation", nullable = false)
    public String operation;

    @Column(name = "result_value")
    public double resultValue;

    @Column(name = "result_unit")
    public String resultUnit;

    @Column(name = "result_measurement_type")
    public String resultMeasurementType;

    // For comparison results like "Equal" or "Not Equal"
    @Column(name = "result_string")
    public String resultString;

    // Flag to indicate if an error occurred
    @Column(name = "is_error")
    public boolean isError;

    // Error message if operation fails
    @Column(name = "error_message")
    public String errorMessage;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;
    
//    Constructor for comparison operations
    public QuantityMeasurementEntity(
            double thisValue,
            String thisUnit,
            String thisMeasurementType,
            double thatValue,
            String thatUnit,
            String thatMeasurementType,
            String operation,
            String resultString) {

        this.thisValue = thisValue;
        this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;
        this.thatValue = thatValue;
        this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;
        this.operation = operation;
        this.resultString = resultString;
        this.isError = false;
    }

//    Constructor for arithmetic operations
    public QuantityMeasurementEntity(
            double thisValue,
            String thisUnit,
            String thisMeasurementType,
            double thatValue,
            String thatUnit,
            String thatMeasurementType,
            String operation,
            double resultValue,
            String resultUnit,
            String resultMeasurementType) {

        this.thisValue = thisValue;
        this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;
        this.thatValue = thatValue;
        this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;
        this.operation = operation;
        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.resultMeasurementType = resultMeasurementType;
        this.isError = false;
    }

//    Constructor for error cases
    public QuantityMeasurementEntity(
            double thisValue,
            String thisUnit,
            String thisMeasurementType,
            double thatValue,
            String thatUnit,
            String thatMeasurementType,
            String operation,
            String errorMessage,
            boolean isError) {

        this.thisValue = thisValue;
        this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;
        this.thatValue = thatValue;
        this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;
        this.operation = operation;
        this.errorMessage = errorMessage;
        this.isError = isError;
    }
}
package com.example.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="quantity_measurements")
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Keeping userId for the microservice context natively
    private String operation;
    private String operand1;
    private String operand2;
    private String result;
    private String errorMessage;

    public QuantityMeasurementEntity() {}

    public QuantityMeasurementEntity(Long userId, String operation, String operand1, String operand2, String result, String errorMessage) {
        this.userId = userId;
        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
    public String getOperand1() { return operand1; }
    public void setOperand1(String operand1) { this.operand1 = operand1; }
    public String getOperand2() { return operand2; }
    public void setOperand2(String operand2) { this.operand2 = operand2; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}

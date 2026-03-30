package com.app.quantitymeasurement.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

@Repository 
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
    // Find all measurements by operation type
    List<QuantityMeasurementEntity> findByOperation(String operation);

    // Find all measurements by measurement type
    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);

    // Find measurements created after specific date
    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);

    // Custom JPQL query for complex operations
    @Query("SELECT e FROM QuantityMeasurementEntity e WHERE e.operation = :operation " + "AND e.isError = false")
    List<QuantityMeasurementEntity> findSuccessfulOperations(
            @Param("operation") String operation
    );

    // Count successful operations
    long countByOperationAndIsErrorFalse(String operation);

    // Find measurements with errors
    List<QuantityMeasurementEntity> findByIsErrorTrue();
}
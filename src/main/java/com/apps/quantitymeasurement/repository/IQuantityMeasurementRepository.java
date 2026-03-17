package com.apps.quantitymeasurement.repository;

import java.util.List;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {
	void save(QuantityMeasurementEntity entity);
	
	List<QuantityMeasurementEntity> getAllMeasurements();
	
}

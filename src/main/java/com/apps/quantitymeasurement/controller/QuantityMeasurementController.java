package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.services.*;

public class QuantityMeasurementController {
	private IQuantityMeasurementService quantityMeasurementService;
	
	public QuantityMeasurementController(
			IQuantityMeasurementService quantityMeasurementService) {
		this.quantityMeasurementService = quantityMeasurementService;
	}
	
	public boolean performComparison(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.compare(thisQuantityDTO, thatQuantityDTO);
	}
	
	public QuantityDTO performConversion(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.convert(thisQuantityDTO, thatQuantityDTO);
	}
	
	public QuantityDTO performAddition(QuantityDTO thisQuantity, QuantityDTO thatQuantity) {
		return quantityMeasurementService.add(thisQuantity, thatQuantity);
	}
	
	public QuantityDTO performAddition(QuantityDTO thisQuantity, QuantityDTO thatQuantity, 
			QuantityDTO targetQuantity) {
		return quantityMeasurementService.add(thisQuantity, thatQuantity, targetQuantity);
	}
	public QuantityDTO performSubtraction(QuantityDTO thisQuantity, QuantityDTO thatQuantity) {
		return quantityMeasurementService.subtract(thisQuantity, thatQuantity);
	}
	public QuantityDTO performSubtraction(QuantityDTO thisQuantity, QuantityDTO thatQuantity, QuantityDTO targetQuantity) {
		return quantityMeasurementService.subtract(thisQuantity, thatQuantity, targetQuantity);
	}
	
	public double performDivision(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.divide(thisQuantityDTO, thatQuantityDTO);
	}
}

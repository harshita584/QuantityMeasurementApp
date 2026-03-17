package com.apps.quantitymeasurement.services;

import com.apps.quantitymeasurement.entity.QuantityDTO;

public interface IQuantityMeasurementService {
	
	public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);
	
	public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);
	
	public double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public static void main(String[] args) {
		System.out.println("IQuantityMeasurementService Interface");
	}
}

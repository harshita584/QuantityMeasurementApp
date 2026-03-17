package com.apps.quantitymeasurement.unit;

public enum WeightUnit implements IMeasurable {
	KILOGRAM(1.0),
	GRAM(0.001),
	POUND(0.453592);
	
	public final double conversionFactor;
	
	WeightUnit(double conversionFactor) {
		this.conversionFactor= conversionFactor;
	}
	
	public double getConversionFactor() {
		return this.conversionFactor;
	}
	public double convertToBaseUnit(double value) {
		return this.conversionFactor*value;
	}
	public double convertFromBaseUnit(double value) {
		return value*WeightUnit.KILOGRAM.getConversionFactor()/this.getConversionFactor();
	}
	public String getUnitName() {
		return WeightUnit.this.name();
	}
	
	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public IMeasurable getUnitInstance(String unitName) {
		for(WeightUnit unit: WeightUnit.values()) {
			if(unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid length unit: "+unitName);
	}
}

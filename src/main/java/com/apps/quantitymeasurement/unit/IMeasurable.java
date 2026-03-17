package com.apps.quantitymeasurement.unit;


@FunctionalInterface
interface SupportsArithmetic{
	boolean isSupported();
}

public interface IMeasurable {
	double getConversionFactor();
	double convertToBaseUnit(double value);
	double convertFromBaseUnit(double baseValue);
	String getUnitName();
	public String getMeasurementType();
	public IMeasurable getUnitInstance(String untiName);
	
	default boolean supportsArithmetic() {
		return true;
	}
	
	default void validateOperationSupport(String operation) {
		if(!supportsArithmetic()) {
			throw new UnsupportedOperationException(getUnitName()+ "category does not support "+ operation );
		}
	}
}

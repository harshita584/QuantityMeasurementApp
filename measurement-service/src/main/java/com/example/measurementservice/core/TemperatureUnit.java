package com.example.measurementservice.core;

import java.util.function.Function;

/*
 * UC14 — Temperature Measurement Category
 *
 * Temperature supports:
 *  Equality
 *  Conversion
 *  Addition / Subtraction / Division (meaningless operations)
 */

public enum TemperatureUnit implements IMeasurable {

	CELSIUS, FAHRENHEIT, KELVIN;

	// Temperature does NOT support arithmetic
	private static final SupportsArithmetic supportsArithmetic = () -> false;

	@Override
	public boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}

	@Override
	public void validateOperationSupport(String operation) {
		throw new UnsupportedOperationException(this.name() + " does not support " + operation + " operation.");
	}

	// ===== Conversion formulas =====

	private static final Function<Double, Double> F_TO_C = f -> (f - 32) * 5 / 9;

	private static final Function<Double, Double> C_TO_F = c -> (c * 9 / 5) + 32;

	private static final Function<Double, Double> K_TO_C = k -> k - 273.15;

	private static final Function<Double, Double> C_TO_K = c -> c + 273.15;

	// ===== IMeasurable methods =====

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public double getConversionFactor() {
		return 1.0; // Not used for temperature
	}

	// Base unit = CELSIUS
	@Override
	public double convertToBaseUnit(double value) {
		switch (this) {
		case CELSIUS:
			return value;
		case FAHRENHEIT:
			return F_TO_C.apply(value);
		case KELVIN:
			return K_TO_C.apply(value);
		default:
			throw new IllegalStateException("Unexpected unit");
		}
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		switch (this) {
		case CELSIUS:
			return baseValue;
		case FAHRENHEIT:
			return C_TO_F.apply(baseValue);
		case KELVIN:
			return C_TO_K.apply(baseValue);
		default:
			throw new IllegalStateException("Unexpected unit");
		}
	}

	// UC14 SPECIAL DIRECT TEMPERATURE CONVERSION 
	public double convertTo(double value, TemperatureUnit target) {

	    if (target == null)
	        throw new IllegalArgumentException("Target temperature unit cannot be null");

	    if (this == target)
	        return value;

	    // Step 1: Convert source → Celsius (base)
	    double celsiusValue;
	    switch (this) {
	        case CELSIUS:
	            celsiusValue = value;
	            break;
	        case FAHRENHEIT:
	            celsiusValue = (value - 32) * 5 / 9;
	            break;
	        case KELVIN:
	            celsiusValue = value - 273.15;
	            break;
	        default:
	            throw new IllegalStateException("Unexpected unit");
	    }

	    // Step 2: Convert Celsius → target
	    switch (target) {
	        case CELSIUS:
	            return celsiusValue;
	        case FAHRENHEIT:
	            return (celsiusValue * 9 / 5) + 32;
	        case KELVIN:
	            return celsiusValue + 273.15;
	        default:
	            throw new IllegalStateException("Unexpected unit");
	    }
	}
}

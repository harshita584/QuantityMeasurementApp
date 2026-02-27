package com.apps.quantitymeasurement;

public enum LengthUnit {
    FEET(12.0),
    INCHES(1.0),
    YARDS(36.0),
    CENTIMETERS(0.393701);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    // Convert value from this unit to base unit
    public double convertToBaseUnit(double value) {
        double result = value * conversionFactor;
        return roundOffTillTwoDecimal(result);
    }

    // Convert value from base unit (inches) to this unit
    public double convertFromBaseUnit(double baseValue) {
        double result = baseValue / conversionFactor;
        return roundOffTillTwoDecimal(result);
    }
    
    // Round off till 2 decimal precision
    private double roundOffTillTwoDecimal(double value) {
    	return Math.round(value * 100.0) / 100.0;
    }
}
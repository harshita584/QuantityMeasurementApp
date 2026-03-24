package com.app.quantitymeasurement.unit;

public enum LengthUnit implements IMeasurable {
    FEET(12.0),
    INCHES(1.0),
    YARDS(36.0),
    CENTIMETERS(0.393701);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        double result = value * conversionFactor;
        return roundOffTillTwoDecimal(result);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        double result = baseValue / conversionFactor;
        return roundOffTillTwoDecimal(result);
    }

    private double roundOffTillTwoDecimal(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    public String getMeasurementType() {
        return this.getClass().getSimpleName();
    }

    public static IMeasurable getUnitInstance(String unitName) {
        for (LengthUnit unit : LengthUnit.values()) {
            if (unit.name().equalsIgnoreCase(unitName)) return unit;
        }
        throw new IllegalArgumentException("Invalid length unit: " + unitName);
    }
}
package com.app.quantitymeasurement.unit;

public enum WeightUnit implements IMeasurable {
    MILLIGRAM(0.001),
    GRAM(1.0),
    KILOGRAM(1000.0),
    POUND(453.592),
    TONNE(1000000.0);

    private final double conversionFactor;

    private WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        double result = value * this.conversionFactor;
        return roundOffTillTwoDecimal(result);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        double result = baseValue / this.conversionFactor;
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
        for (WeightUnit unit : WeightUnit.values()) {
            if (unit.name().equalsIgnoreCase(unitName)) return unit;
        }
        throw new IllegalArgumentException("Invalid weight unit: " + unitName);
    }
}
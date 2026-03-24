package com.app.quantitymeasurement.unit;

public enum VolumeUnit implements IMeasurable {
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double conversionFactor;

    private VolumeUnit(double conversionFactor) {
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
        for (VolumeUnit unit : VolumeUnit.values()) {
            if (unit.name().equalsIgnoreCase(unitName)) return unit;
        }
        throw new IllegalArgumentException("Invalid volume unit: " + unitName);
    }
}
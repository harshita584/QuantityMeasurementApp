package com.example.measurementservice.core;

// volume units
public enum VolumeUnit implements IMeasurable {

    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541),
    OUNCE(0.0295735),
    PINT(0.473176),
    CUP(0.236588);

    private final double conversionFactor;

    VolumeUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * conversionFactor; // convert to litre
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor; // from litre
    }

    @Override
    public String getUnitName() {
        return this.name();
    }
}

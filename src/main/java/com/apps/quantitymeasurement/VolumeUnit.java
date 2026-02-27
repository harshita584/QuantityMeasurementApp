package com.apps.quantitymeasurement;

public enum VolumeUnit implements IMeasurable {
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double conversionFactor;

    private VolumeUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public double convertToBaseUnit(double value) {
        double result = value * conversionFactor;
        return roundOffTillTwoDecimal(result);
    }

    public double convertFromBaseUnit(double baseValue) {
        double result = baseValue / conversionFactor;
        return roundOffTillTwoDecimal(result);
    }

    private double roundOffTillTwoDecimal(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
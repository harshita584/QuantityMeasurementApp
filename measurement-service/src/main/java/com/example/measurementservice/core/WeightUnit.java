package com.example.measurementservice.core;

public enum WeightUnit implements IMeasurable {

    MILLIGRAM(0.001),
    GRAM(1.0),
    KILOGRAM(1000.0),
    POUND(453.592),
    TONNE(1_000_000.0),
    OUNCE(28.3495);

    private final double conversionFactor; // conversion to base (grams)

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        double result = value * conversionFactor; // to grams
        return Math.round(result * 100.0) / 100.0; // round
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        double result = baseValue / conversionFactor; // from grams
        return Math.round(result * 100.0) / 100.0; // round
    }

    @Override
    public String getUnitName() {
        return this.name(); // unit name
    }
}

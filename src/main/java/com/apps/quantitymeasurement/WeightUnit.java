package com.apps.quantitymeasurement;

public enum WeightUnit implements IMeasurable{
    MILLIGRAM(0.001),
    GRAM(1.0),
    KILOGRAM(1000.0),
    POUND(453.592),
    TONNE(1000000.0);

    // Conversion factor to the base unit
    private final double conversionFactor;

    // Constructor
    private WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    // Get conversion factor
    public double getConversionFactor() {
        return conversionFactor;
    }

    // Convert value from this unit to base unit
    public double convertToBaseUnit(double value) {
        double result = value * this.conversionFactor;
        return roundOffTillTwoDecimal(result);
    }

    // Convert value from base unit to this unit
    public double convertFromBaseUnit(double baseValue) {
        double result = baseValue / this.conversionFactor;
        return roundOffTillTwoDecimal(result);
    }
    
    // Utility method to round off till two decimal places
    private double roundOffTillTwoDecimal(double value) {
    	return Math.round(value * 100.0) / 100.0;
    }
    
    public static void main(String[] args) {
        double kilograms = 10.0;
        double grams = WeightUnit.KILOGRAM.convertToBaseUnit(kilograms);
        System.out.println(kilograms + " kilograms is " + grams + " grams");
        System.out.println();

        double milligrams = WeightUnit.MILLIGRAM.convertFromBaseUnit(grams);
        System.out.println(grams + " grams is " + milligrams + " milligrams");
        System.out.println();

        double pounds = WeightUnit.POUND.convertFromBaseUnit(grams);
        System.out.println(grams + " grams is " + pounds + " pounds");
        System.out.println();

        double tonnes = WeightUnit.TONNE.convertFromBaseUnit(grams);
        System.out.println(grams + " grams is " + tonnes + " tonnes");
        System.out.println();
    }
}
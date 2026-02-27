package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
    // Demonstrate Equality
    public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1, Quantity<U> quantity2) {
        return quantity1.equals(quantity2);
    }

    // Demonstrate Conversion of a quantity
    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {
        double convertedValue = quantity.convertTo(targetUnit);
        return new Quantity<>(convertedValue, targetUnit);
    }

    // Demonstrate Addition 
    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2) {
        return quantity1.add(quantity2);
    }

    // Demonstrate Addition with provided return type unit
    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit) {
        return quantity1.add(quantity2, targetUnit);
    }

    public static void main(String[] args) {
        // Demonstration equality 
        Quantity<WeightUnit> weightInGrams = new Quantity<>(1000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> weightInKilograms = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        boolean areEqual = demonstrateEquality(weightInGrams, weightInKilograms);
        System.out.println("Are weights equal? " + areEqual);
        System.out.println();

        // Demonstration conversion 
        Quantity<WeightUnit> convertedWeight = demonstrateConversion(weightInGrams, WeightUnit.KILOGRAM);
        System.out.println("Converted Weight: " + convertedWeight.getValue() + "" + convertedWeight.getUnit());
        System.out.println();

        // Demonstration addition 
        Quantity<WeightUnit> weightInPounds = new Quantity<>(2.20462, WeightUnit.POUND);
        Quantity<WeightUnit> sumWeight = demonstrateAddition(weightInKilograms, weightInPounds);
        System.out.println("Sum Weight: " + sumWeight.getValue() + "" + sumWeight.getUnit());
        System.out.println();

        // demonstration addition with target unit
        Quantity<WeightUnit> sumWeightInGrams = demonstrateAddition(weightInKilograms, weightInPounds, WeightUnit.GRAM);
        System.out.println("Sum Weight in Grams: " + sumWeightInGrams.getValue() + " " + sumWeightInGrams.getUnit());
        System.out.println();
        
        
        // demonstration for volume
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> millilitre = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        // Equality
        System.out.println(demonstrateEquality(litre, millilitre));
        System.out.println();

        // Conversion
        System.out.println(demonstrateConversion(litre, VolumeUnit.GALLON));
        System.out.println();

        // Addition
        System.out.println(demonstrateAddition(litre, millilitre));
        System.out.println();
    }
}
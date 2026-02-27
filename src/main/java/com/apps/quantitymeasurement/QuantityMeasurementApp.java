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
    
    // Demonstrate Subtraction
    public static <U extends IMeasurable> Quantity<U>
    demonstrateSubtraction(Quantity<U> quantity1, Quantity<U> quantity2) {
        return quantity1.subtract(quantity2);
    }

    // Demonstrate Subtraction with target unit
    public static <U extends IMeasurable> Quantity<U>
    demonstrateSubtraction(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit) {
        return quantity1.subtract(quantity2, targetUnit);
    }

    // Demonstrate Division
    public static <U extends IMeasurable> Quantity<U>
    demonstrateDivision(Quantity<U> quantity1, Quantity<U> quantity2) {
        return quantity1.divide(quantity2);
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
        
        // Subtraction demo
        Quantity<LengthUnit> tenFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> sixInches = new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println(demonstrateSubtraction(tenFeet, sixInches));
        System.out.println();

        // Division demo
        Quantity<LengthUnit> twentyFeet = new Quantity<>(20.0, LengthUnit.FEET);
        Quantity<LengthUnit> fiveFeet = new Quantity<>(5.0, LengthUnit.FEET);

        System.out.println(demonstrateDivision(twentyFeet, fiveFeet));
        System.out.println();
    }
}
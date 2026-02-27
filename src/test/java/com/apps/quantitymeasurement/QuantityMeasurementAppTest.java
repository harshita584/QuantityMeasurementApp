package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {
    @Test
    public void lengthFeetEqualsInches() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCHES);

        assertTrue(feet.equals(inches));
    }

    @Test
    public void lengthYardsEqualsFeet() {
        Quantity<LengthUnit> yards = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);

        assertTrue(yards.equals(feet));
    }

    @Test
    public void weightKilogramEqualsGrams() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> grams = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(kg.equals(grams));
    }

    @Test
    public void weightPoundEqualsGrams() {
        Quantity<WeightUnit> pound = new Quantity<>(1.0, WeightUnit.POUND);
        Quantity<WeightUnit> grams = new Quantity<>(453.592, WeightUnit.GRAM);

        assertTrue(pound.equals(grams));
    }

    @Test
    public void convertLengthFeetToInches() {
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateConversion(feet, LengthUnit.INCHES);

        assertEquals(36.0, result.getValue());
    }

    @Test
    public void addLengthFeetAndInches() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCHES);
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateAddition(feet, inches);

        assertEquals(2.0, result.getValue());
    }

    @Test
    public void addWeightKilogramsAndGrams() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> grams = new Quantity<>(1000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> result = QuantityMeasurementApp.demonstrateAddition(kg, grams);

        assertEquals(2.0, result.getValue());
    }

    @Test
    public void testGenericTypeSafetyWithWeight() {
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        
        assertEquals(1.0, weight.getValue());
        assertEquals(WeightUnit.KILOGRAM, weight.getUnit());
    }

    @Test
    public void convertWeightKilogramsToGrams() {
        Quantity<WeightUnit> kg = new Quantity<>(2.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> result = QuantityMeasurementApp.demonstrateConversion(kg, WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue());
    }

    @Test
    public void addWeightKilogramsAndPounds() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> pound = new Quantity<>(2.20462, WeightUnit.POUND);
        Quantity<WeightUnit> result = QuantityMeasurementApp.demonstrateAddition(kg, pound);

        assertEquals(2.0, result.getValue());
    }

    @Test
    public void convertLengthYardsToInches() {
        Quantity<LengthUnit> yards = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateConversion(yards, LengthUnit.INCHES);

        assertEquals(36.0, result.getValue());
    }

    @Test
    public void preventCrossTypeComparisonLengthVsWeight() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }

    @Test
    public void preventCrossTypeAdditionLengthVsWeight() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> {
            length.add((Quantity)new Quantity<WeightUnit>(1.0, WeightUnit.KILOGRAM));
        });
    }

    @Test
    public void preventCrossTypeConversionLengthToWeight() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        
        assertThrows(Exception.class, () -> {
            length.convertTo((LengthUnit) (Object) WeightUnit.GRAM);
        });
    }

    @Test
    public void addLengthYardsAndFeet() {
        Quantity<LengthUnit> yards = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateAddition(yards, feet);

        assertEquals(2.0, result.getValue());
    }

    @Test
    public void addWeightTonnesAndKilograms() {
        Quantity<WeightUnit> tonne = new Quantity<>(1.0, WeightUnit.TONNE);
        Quantity<WeightUnit> kg = new Quantity<>(1000.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> result = QuantityMeasurementApp.demonstrateAddition(tonne, kg);

        assertEquals(2.0, result.getValue());
    }
    
    @Test
    public void backwardCompatibilityLengthFeetEqualsInches() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCHES);

        assertTrue(feet.equals(inches));
    }

    @Test
    public void backwardCompatibilityWeightKilogramEqualsGrams() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> grams = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(kg.equals(grams));
    }

    @Test
    public void backwardCompatibilityConvertLengthFeetToInches() {
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateConversion(feet, LengthUnit.INCHES);

        assertEquals(36.0, result.getValue());
    }

    @Test
    public void backwardCompatibilityConvertWeightKilogramsToGrams() {
        Quantity<WeightUnit> kg = new Quantity<>(2.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> result = QuantityMeasurementApp.demonstrateConversion(kg, WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue());
    }

    @Test
    public void backwardCompatibilityAddLengthInSameUnit() {
        Quantity<LengthUnit> feet1 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> feet2 = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = feet1.add(feet2);

        assertEquals(5.0, result.getValue());
    }

    @Test
    public void backwardCompatibilityAddWeightInSameUnit() {
        Quantity<WeightUnit> grams1 = new Quantity<>(500.0, WeightUnit.GRAM);
        Quantity<WeightUnit> grams2 = new Quantity<>(500.0, WeightUnit.GRAM);
        Quantity<WeightUnit> result = grams1.add(grams2);

        assertEquals(1000.0, result.getValue());
    }

    @Test
    public void backwardCompatibilityLengthYardsEqualsFeet() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);

        assertTrue(yard.equals(feet));
    }

    @Test
    public void backwardCompatibilityWeightPoundEqualsGrams() {
        Quantity<WeightUnit> pound = new Quantity<>(1.0, WeightUnit.POUND);
        Quantity<WeightUnit> grams = new Quantity<>(453.592, WeightUnit.GRAM);

        assertTrue(pound.equals(grams));
    }

    @Test
    public void backwardCompatibilityChainedAdditionsLength() {
        Quantity<LengthUnit> oneFoot = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> twelveInches = new Quantity<>(12.0, LengthUnit.INCHES);
        Quantity<LengthUnit> oneYard = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> result = oneFoot.add(twelveInches).add(oneYard);

        assertEquals(5.0, result.getValue());
    }
    
    @Test
    public void volumeLitreEqualsMillilitre() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertTrue(litre.equals(ml));
    }

    @Test
    public void volumeNotEqual() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> twoLitres = new Quantity<>(2.0, VolumeUnit.LITRE);

        assertFalse(litre.equals(twoLitres));
    }
    
    @Test
    public void convertLitreToMillilitre() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = QuantityMeasurementApp.demonstrateConversion(litre, VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue());
    }

    @Test
    public void convertGallonToLitre() {
        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> result = QuantityMeasurementApp.demonstrateConversion(gallon, VolumeUnit.LITRE);

        assertEquals(3.79, result.getValue());
    }
    
    @Test
    public void addVolumeSameUnit() {
        Quantity<VolumeUnit> one = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> two = new Quantity<>(2.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result = one.add(two);

        assertEquals(3.0, result.getValue());
    }

    @Test
    public void addVolumeDifferentUnits() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result = litre.add(ml);

        assertEquals(2.0, result.getValue());
    }

    @Test
    public void addVolumeWithTargetUnit() {
    	Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result = litre.add(ml, VolumeUnit.MILLILITRE);

        assertEquals(2000.0, result.getValue());
    }
    
    @Test
    public void preventVolumeVsLengthComparison() {
        Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);

        assertFalse(volume.equals(length));
    }
}
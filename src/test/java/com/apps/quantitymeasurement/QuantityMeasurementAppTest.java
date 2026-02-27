package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {
    @Test
    public void testFeetEquality() {
    	Length feet1 = new Length(10.0, LengthUnit.FEET);
    	Length feet2 = new Length(10.0, LengthUnit.FEET);
    	
    	assertTrue(feet1.equals(feet2));
    }
    
    @Test
    public void testInchesEquality() {
    	Length inch1 = new Length(10.0, LengthUnit.INCHES);
    	Length inch2 = new Length(10.0, LengthUnit.INCHES);
    	
    	assertTrue(inch1.equals(inch2));
    }
    
    @Test
    public void testFeetInchesComparison() {
    	Length feet = new Length(1.0, LengthUnit.FEET);
    	Length inch = new Length(12.0, LengthUnit.INCHES);
    	
    	assertTrue(feet.equals(inch));
	}
    
    @Test
    public void testFeetInequality() {
    	Length feet1 = new Length(10.0, LengthUnit.FEET);
    	Length feet2 = new Length(20.0, LengthUnit.FEET);
    	
    	assertFalse(feet1.equals(feet2));
    }
    
    @Test
    public void testInchesInequality() {
    	Length inch1 = new Length(10.0, LengthUnit.INCHES);
    	Length inch2 = new Length(20.0, LengthUnit.INCHES);
    	
    	assertFalse(inch1.equals(inch2));
    }
    
    @Test
    public void testCrossUnitInequality() {
    	Length feet = new Length(24.0, LengthUnit.FEET);
    	Length inch = new Length(1.0, LengthUnit.INCHES);
    	
    	assertFalse(feet.equals(inch));
	}
    
    @Test
    public void testMultipleFeetComparison() {
        Length feet = new Length(3.0, LengthUnit.FEET);
        Length inch = new Length(36.0, LengthUnit.INCHES);

        assertTrue(feet.equals(inch));
    }
    
    @Test 
    public void yardEquals36Inches() {
    	Length yard = new Length(1.0, LengthUnit.YARDS);
    	Length inches = new Length(36.0, LengthUnit.INCHES);
    	
    	assertTrue(yard.equals(inches));
    }
    
    @Test
    public void centimeterEquals39Point3701Inches() {
    	Length centimeter = new Length(100.0, LengthUnit.CENTIMETERS);
    	Length inches = new Length(39.37, LengthUnit.INCHES);
    	
    	assertTrue(centimeter.equals(inches));
    }
    
    @Test
    public void threeFeetEqualsOneYard() {
        Length feet = new Length(3.0, LengthUnit.FEET);
        Length yard = new Length(1.0, LengthUnit.YARDS);

        assertTrue(feet.equals(yard));
    }

    @Test
    public void thirtyPoint48CmEqualsOneFoot() {
        Length centimeter = new Length(30.48, LengthUnit.CENTIMETERS);
        Length foot = new Length(1.0, LengthUnit.FEET);

        assertTrue(centimeter.equals(foot));
    }

    @Test
    public void yardNotEqualToInches() {
        Length yard = new Length(1.0, LengthUnit.YARDS);
        Length inches = new Length(10.0, LengthUnit.INCHES);

        assertFalse(yard.equals(inches));
    }

    @Test
    public void referenceEqualitySameObject() {
        Length length = new Length(10.0, LengthUnit.FEET);

        assertTrue(length.equals(length));
    }

    @Test
    public void equalsReturnsFalseForNull() {
        Length length = new Length(10.0, LengthUnit.FEET);

        assertFalse(length.equals(null));
    }

    @Test
    public void reflexiveSymmetricAndTransitiveProperty() {
        Length a = new Length(36.0, LengthUnit.INCHES);
        Length b = new Length(3.0, LengthUnit.FEET);
        Length c = new Length(1, LengthUnit.YARDS);

        // Reflexive
        assertTrue(a.equals(a));

        // Symmetric
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        // Transitive
        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
    }

    @Test
    public void differentValuesSameUnitNotEqual() {
        Length feet1 = new Length(10.0, LengthUnit.FEET);
        Length feet2 = new Length(20.0, LengthUnit.FEET);

        assertFalse(feet1.equals(feet2));
    }

    @Test
    public void crossUnitEqualityDemonstrateMethod() {
    	Length yards = new Length(1.0, LengthUnit.YARDS);
    	Length feets = new Length(3.0, LengthUnit.FEET); 
        assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(yards, feets));
    }
    
    @Test 
    public void convertFeetToInches() {
    	Length lengthInInches = QuantityMeasurementApp.demonstrateLengthConversion(3.0, LengthUnit.FEET, LengthUnit.INCHES);
    	Length expectedLength = new Length(36.0, LengthUnit.INCHES);
    	assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(lengthInInches, expectedLength));
    }
    
    @Test
    public void convertYardsToInchesUsingOverloadedMethod() {
    	Length lengthInYards = new Length(2.0, LengthUnit.YARDS);
    	Length lengthInInches = QuantityMeasurementApp.demonstrateLengthConversion(lengthInYards, LengthUnit.INCHES);
    	Length expectedLength = new Length(72.0, LengthUnit.INCHES);
    	
    	assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(lengthInInches, expectedLength));
    }
    
    @Test
    public void addFeetAndInches() {
    	Length length1 = new Length(1.0, LengthUnit.FEET);
    	Length length2 = new Length(12.0, LengthUnit.INCHES);
    	
    	Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2);
    	Length expectedLength = new Length(2.0, LengthUnit.FEET);
    	assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, expectedLength));
    }
    
    @Test
    public void testAddition_NullSecondOperand() {
        Length length = new Length(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> {
        	length.add(null);
        });
    }
    
    @Test 
    public void testAddition_NegativeValues() {
    	Length length1 = new Length(10.0, LengthUnit.FEET);
    	Length length2 = new Length(-5.0, LengthUnit.FEET);
    	
    	Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2);
    	Length expectedLength = new Length(5.0, LengthUnit.FEET);
    	assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, expectedLength));
    }
    
    @Test
    public void addFeetAndInchesWithTargetUnitInches() {
    	Length feet = new Length(1.0, LengthUnit.FEET);
    	Length inches = new Length(12.0, LengthUnit.INCHES);
    	
    	Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(feet, inches, LengthUnit.INCHES);
    	Length expectedLength = new Length(24.0, LengthUnit.INCHES);
    	assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, expectedLength));
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_NullTargetUnit() {
    	Length feet = new Length(1.0, LengthUnit.FEET);
    	Length inches = new Length(12.0, LengthUnit.INCHES);
    	
    	assertThrows(IllegalArgumentException.class, () -> {
    		QuantityMeasurementApp.demonstrateLengthAddition(feet, inches, null);
    	});
    }
    
    @Test
    public void kilogramEquals1000Grams() {
    	Weight kg = new Weight(1, WeightUnit.KILOGRAM);
    	Weight grams = new Weight(1000, WeightUnit.GRAM);
    	
    	assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(kg, grams));
    }

    @Test
    public void poundEquals453Point592Grams() {
        Weight pound = new Weight(1.0, WeightUnit.POUND);
        Weight grams = new Weight(453.592, WeightUnit.GRAM);

        assertTrue(pound.equals(grams));
    }

    @Test
    public void tonneEquals1000000Grams() {
        Weight tonne = new Weight(1.0, WeightUnit.TONNE);
        Weight grams = new Weight(1000000.0, WeightUnit.GRAM);

        assertTrue(tonne.equals(grams));
    }

    @Test
    public void kilogramNotEqualToPound() {
        Weight kilogram = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight pound = new Weight(1.0, WeightUnit.POUND);

        assertFalse(kilogram.equals(pound));
    }
    
    @Test
    public void addWeightSameUnit() {
        Weight weight1 = new Weight(500.0, WeightUnit.GRAM);
        Weight weight2 = new Weight(500.0, WeightUnit.GRAM);
        Weight sum = weight1.add(weight2);
        Weight expected = new Weight(1000.0, WeightUnit.GRAM);

        assertTrue(sum.equals(expected));
    }

    @Test
    public void addWeightDiffrentUnit() {
        Weight kilogram = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight grams = new Weight(500.0, WeightUnit.GRAM);
        Weight sum = kilogram.add(grams);
        Weight expected = new Weight(1.5, WeightUnit.KILOGRAM);

        assertTrue(sum.equals(expected));
    }

    @Test
    public void addWeightDiffrentUnitWithTarget() {
        Weight kilogram = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight grams = new Weight(500.0, WeightUnit.GRAM);

        Weight sum = kilogram.add(grams, WeightUnit.GRAM);
        Weight expected = new Weight(1500.0, WeightUnit.GRAM);

        assertTrue(sum.equals(expected));
    }

    @Test
    public void weightConversion() {
        Weight result = QuantityMeasurementApp.demonstrateWeightConversion(2.0, WeightUnit.KILOGRAM, WeightUnit.GRAM);
        Weight expected = new Weight(2000.0, WeightUnit.GRAM);
        
        assertTrue(result.equals(expected));
    }

    @Test
    public void weightConversionOverloadedMethod() {
        Weight grams = new Weight(2000.0, WeightUnit.GRAM);
        
        Weight result = QuantityMeasurementApp.demonstrateWeightConversion(grams, WeightUnit.KILOGRAM);
        Weight expected = new Weight(2.0, WeightUnit.KILOGRAM);

        assertTrue(result.equals(expected));
    }
}
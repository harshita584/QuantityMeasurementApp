package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.Length.LengthUnit;

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
        Length feet = new Length(3.0, Length.LengthUnit.FEET);
        Length inch = new Length(36.0, Length.LengthUnit.INCHES);

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
    	Length centimeter = new Length(1.0, LengthUnit.CENTIMETERS);
    	Length inches = new Length(0.393701, LengthUnit.INCHES);
    	
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
}
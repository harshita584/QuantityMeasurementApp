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
    public void centimeterEqualsPoint393701Inches() {
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
    void thirtyPointFourEightCmEqualsOneFoot() {
        Length centimeter = new Length(30.48, LengthUnit.CENTIMETERS);
        Length foot = new Length(1.0, LengthUnit.FEET);

        assertEquals(centimeter, foot);
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
}
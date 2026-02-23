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
    public void testFeetInchesComparisoin() {
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
}
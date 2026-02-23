package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.Length.LengthUnit;

public class QuantityMeasurementApp {
	// Generic method to demonstrate length equality check
    public static boolean demonstrateLengthEquality(Length length1, Length length2) {
    	return length1.equals(length2);
    }
    
    public static void demonstrateFeetEquality() {
    	Length feet1 = new Length(10, LengthUnit.FEET);
    	Length feet2 = new Length(10, LengthUnit.FEET);
    	
    	System.out.println("Feet1 equals Feet2 ? " + feet1.equals(feet2));
    }
    
    public static void demonstrateInchEquality() {
    	Length inch1 = new Length(10, LengthUnit.INCHES);
    	Length inch2 = new Length(10, LengthUnit.INCHES);
    	
    	System.out.println("Inch1 equals Inch2 ? " + inch1.equals(inch2));
    }
    
    public static void demonstrateFeetInchesComparasion() {
    	Length feet = new Length(1, LengthUnit.FEET);
    	Length inch = new Length(12, LengthUnit.INCHES);
    	
    	System.out.println("Feet equals Inch ? " + demonstrateLengthEquality(feet, inch));
    }
    
    public static void main(String[] args) {
		demonstrateFeetEquality();
		demonstrateInchEquality();
		demonstrateFeetInchesComparasion();
	}
}

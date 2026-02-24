package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.Length.LengthUnit;

public class QuantityMeasurementApp {
	// Generic method to demonstrate length equality check
    public static boolean demonstrateLengthEquality(Length length1, Length length2) {
    	return length1.equals(length2);
    }
    
   public static boolean demonstrateLengthComparison(double value1, LengthUnit unit1, double value2, LengthUnit unit2) {
	   Length length1 = new Length(value1, unit1);
	   Length length2 = new Length(value2, unit2);
	   
	   System.out.println("Comparing " + value1 + unit1 + " and " + value2 + unit2);
	   return demonstrateLengthEquality(length1, length2);
   }
   
   // convert the length form one unit to other
   public static Length demonstrateLengthConversion(double value, LengthUnit fromUnit, LengthUnit toUnit) {
	   Length length = new Length(value, fromUnit);
	   return length.convertTo(toUnit);
   }
   
   // convert the length from one uint to other, overloaded method takes Length object directly
   public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
	   return length.convertTo(toUnit);
   }
    
    public static void main(String[] args) {
    	// Demonstrate feet and inch equality
    	System.out.println(demonstrateLengthComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES));
    	System.out.println();
    	
    	// Demonstrate yards and inch equality
    	System.out.println(demonstrateLengthComparison(1.0, LengthUnit.YARDS, 36.0, LengthUnit.INCHES));
    	System.out.println();
    	
    	// Demonstrate centimeter and inch equality
    	System.out.println(demonstrateLengthComparison(100.0, LengthUnit.CENTIMETERS, 39.3701, LengthUnit.INCHES));
    	System.out.println();
    	
    	// Demonstrate feet and inch yards
    	System.out.println(demonstrateLengthComparison(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARDS));
    	System.out.println();
    	
    	// Demonstrate centimeter and feets equality
    	System.out.println(demonstrateLengthComparison(30.48, LengthUnit.CENTIMETERS, 1.0, LengthUnit.FEET));
    	System.out.println();
    	
    	// Demonstrate conversion
    	System.out.println(demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES));
    	System.out.println();
    	
    	// Demonstrate conversion overloaded method
    	System.out.println(demonstrateLengthConversion(new Length(36.0, LengthUnit.INCHES), LengthUnit.YARDS));
    	System.out.println();
	}
}
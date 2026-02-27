package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
	// Method to demonstrate length equality check
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
   
   // convert the length from one unit to other, overloaded method takes Length object directly
   public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
	   return length.convertTo(toUnit);
   }
   
   // Demonstrate length addition
   public static Length demonstrateLengthAddition(Length length1, Length length2) {
	   return length1.add(length2);
   }
   
   // Demonstrate length addition
   public static Length demonstrateLengthAddition(Length length1, Length length2, LengthUnit targetUnit) {
	   return length1.add(length2, targetUnit);
   }
   
   // Demonstrate weight equality
   public static boolean demonstrateWeightEquality(Weight weight1, Weight weight2) {
       return weight1.equals(weight2);
   }
   
   // Demonstrate weight equality 
   public static boolean demonstrateWeightComparison(double value1, WeightUnit unit1, double value2, WeightUnit unit2) {
	   Weight weight1 = new Weight(value1, unit1);
	   Weight weight2 = new Weight(value2, unit2);

	   return demonstrateWeightEquality(weight1, weight2);
   }
   
   // convert the weight
   public static Weight demonstrateWeightConversion(double value, WeightUnit fromUnit, WeightUnit toUnit) {
       Weight weight = new Weight(value, fromUnit);
       return weight.convertTo(toUnit);
   }

   // convert the weight
   public static Weight demonstrateWeightConversion(Weight weight, WeightUnit toUnit) {
       return weight.convertTo(toUnit);
   }

   // Demonstrate weight addition
   public static Weight demonstrateWeightAddition(Weight weight1, Weight weight2) {
       return weight1.add(weight2);
   }

   // Demonstrate weight addition with target unit
   public static Weight demonstrateWeightAddition(Weight weight1, Weight weight2, WeightUnit targetUnit) {
       return weight1.add(weight2, targetUnit);
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
    	
    	// Demonstrate addition of two length
    	System.out.println(demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(2.0, LengthUnit.FEET)));
    	System.out.println();
    	
    	System.out.println(demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES)));
    	System.out.println();
    	
    	System.out.println(demonstrateLengthAddition(new Length(12.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.FEET)));
    	System.out.println();
    	
    	// Demonstrate addition of two length, using the overloaded method
    	System.out.println(demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(2.0, LengthUnit.FEET), LengthUnit.FEET));
    	System.out.println();
    	
    	System.out.println(demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(11.0, LengthUnit.FEET), LengthUnit.INCHES));
    	System.out.println();
    	
    	System.out.println(demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES), LengthUnit.YARDS));
    	System.out.println();
    	
    	
    	System.out.println(demonstrateWeightComparison(1000.0, WeightUnit.GRAM, 1.0, WeightUnit.KILOGRAM));
        System.out.println();

        System.out.println(demonstrateWeightConversion(2.0, WeightUnit.KILOGRAM, WeightUnit.GRAM));
        System.out.println();

        System.out.println(demonstrateWeightAddition(new Weight(500.0, WeightUnit.GRAM), new Weight(500.0, WeightUnit.GRAM)));
        System.out.println();
        
        System.out.println(demonstrateWeightAddition(new Weight(500.0, WeightUnit.GRAM), new Weight(500.0, WeightUnit.GRAM), WeightUnit.KILOGRAM));
        System.out.println();
	}
}	
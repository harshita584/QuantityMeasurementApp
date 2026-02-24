package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {
	
	private double value;
	private LengthUnit unit;
	
	// Enum with conversion factor to base unit (inches)
	public enum LengthUnit{
		FEET(12.0),
		INCHES(1.0),
		YARDS(36.0),
		CENTIMETERS(0.393701);
		
		private final double conversionFactor;
		
		LengthUnit(double conversionFactor){
			this.conversionFactor = conversionFactor;
		}
		
		public double getConversionFactor() {
			return conversionFactor;
		}

	}

	// Constructor to initialize length value and unit
	public Length(double value, LengthUnit unit) {

		if(!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		if(unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		this.value = value;
		this.unit = unit;
	}
	public double getValue(){return value;}

	// Convert the length value to the base unit (inches) and round off to two decimal places
	private double convertToBaseUnit() {
		double convertedValue = this.value * this.unit.getConversionFactor();
		return round(convertedValue);
	}
	
	// Compare two length objects for equality based on their values in the base unit
	public boolean compare(Length thatLength) {
		if(thatLength == null) {return false;}
		return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit()) == 0;
	}
	
	@Override
	public boolean equals(Object o) {
		
		// Reference check
		if(o == this) {return true;}

		// Null check and Same class check
		if(o == null || o.getClass() != this.getClass()) {return false;}
		
		
		// Cast
		Length length = (Length) o;
		return Double.compare(this.convertToBaseUnit(), length.convertToBaseUnit()) == 0;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(convertToBaseUnit());
	}
	
	// Convert the length to a specific target unit
	public Length convertTo(LengthUnit targetUnit) {
		if(targetUnit == null) {
			throw new IllegalArgumentException("Target unit connot be null");
		}
		
		double baseValue = this.convertToBaseUnit();
		double convertValue = baseValue / targetUnit.getConversionFactor();
		
		return new Length(round(convertValue), targetUnit);
	}
	
	// Round the values to the two decimal places
	private double round(double value) {
		return Math.round(value*100)/100;
	}
	
	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}
	
	// Main method
	public static void main(String[] args) {
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		System.out.println("Are lengths equals? " + length1.equals(length2)); // Should print true;
		
		Length length3 = new Length(1, LengthUnit.YARDS);
		Length length4 = new Length(36, LengthUnit.INCHES);
		System.out.println("Are lengths equals? " + length3.equals(length4)); // Should print true;
		
		Length length5 = new Length(100, LengthUnit.CENTIMETERS);
		Length length6 = new Length(39.3701, LengthUnit.INCHES);
		System.out.println("Are lengths equals? " + length5.equals(length6)); // Should print true;
	}
}
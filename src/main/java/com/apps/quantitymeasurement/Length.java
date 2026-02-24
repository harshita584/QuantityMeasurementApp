package com.apps.quantitymeasurement;

public class Length {
	private double value;
	private LengthUnit unit;
	
	// Enum to convert unit and storing the conversion factor, base unit Inch
	public enum LengthUnit{
		FEET(12.0),
		INCHES(1.0),
		YARDS(36.0),
		CENTIMETERS(0.393701);
		
		private final double conversionFactor;
		
		LengthUnit(double conversionFactor) {
			this.conversionFactor = conversionFactor;
		}
		
		public double getConversionFactor() {
			return this.conversionFactor;
		}
	}
	
	// constructor to initialize length and unit
	public Length(double value, LengthUnit unit) {	
		if(unit == null) throw new IllegalArgumentException("Enter a valid length unit");
		if(!Double.isFinite(value)) throw new IllegalArgumentException("Enter a valid double value for conversion");

		this.value = value;
		this.unit = unit;
	}
	
	// convert the length value to base unit (inches)
	private double convertToBaseUnit() {
		return unit.getConversionFactor() * value;
	}
	
	// compare two length object
	public boolean compare(Length thatLength) {
		if(thatLength == null) return false;
		
		double currentBaseLength = this.convertToBaseUnit();
		double thatBaseLength = thatLength.convertToBaseUnit();
		
		return Double.compare(currentBaseLength, thatBaseLength) == 0;
	}
	
	// convert to other unit
	public Length convertTo(LengthUnit targetUnit) {
		if(targetUnit == null) throw new IllegalArgumentException("Enter a valid unit for conversion");
		
		double toBaseUnit = this.convertToBaseUnit();
		double resultantValue = toBaseUnit / targetUnit.getConversionFactor();
		
		return new Length(resultantValue, targetUnit);
	}
	
	public Length add(Length thatLength) {
		if(thatLength == null) throw new IllegalArgumentException("Enter a valid length for addition");
		
		double value1 = this.convertToBaseUnit();
		double value2 = thatLength.convertToBaseUnit();
		double totalValue = value1 + value2;
		double resultantValue = convertFromBaseToTargetUnit(totalValue, unit);
		
		return new Length(resultantValue, this.unit);
	}
	
	// Rounded up to 2 decimal places
	private double convertFromBaseToTargetUnit(double lengthInInches, LengthUnit targetUnit) {
		double result = lengthInInches / targetUnit.getConversionFactor();
		return Math.round(result * 100.0) / 100.0;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Length thatLength = (Length) o;
        return compare(thatLength);
    }
    
    @Override
    public String toString() {	
    	return this.value + "" + unit;
    }
    
    public static void main(String[] args) {
        Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.INCHES);
        System.out.println("Are lengths equal : " + length1.equals(length2));
        
        Length length3 = new Length(1.0, LengthUnit.YARDS);
        Length length4 = new Length(36.0, LengthUnit.INCHES);
        System.out.println("Are lengths equal : " + length3.equals(length4));
        
        Length length5 = new Length(100.0, LengthUnit.CENTIMETERS);
        Length length6 = new Length(39.3701, LengthUnit.INCHES);
        System.out.println("Are lengths equal : " + length5.equals(length6));
        
        System.out.println("36 Inches to equals to : " + length4.convertTo(LengthUnit.YARDS));
    }
}
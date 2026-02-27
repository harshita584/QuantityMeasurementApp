package com.apps.quantitymeasurement;

public class Length {
    private double value;
    private LengthUnit unit;

    public Length(double value, LengthUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Enter a valid length unit");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Enter a valid double value for conversion");

        this.value = value;
        this.unit = unit;
    }

    // Converts this length value to base unit
    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    // Converts from base unit to target unit
    private double convertFromBaseToTargetUnit(double lengthInInches, LengthUnit targetUnit) {
        return targetUnit.convertFromBaseUnit(lengthInInches);
    }

    // Compare two different length
    public boolean compare(Length thatLength) {
        if (thatLength == null) return false;

        double value1 = this.convertToBaseUnit();
        double value2 = thatLength.convertToBaseUnit();

        return Double.compare(value1, value2) == 0;
    }

    // Convert the value in target unit
    public Length convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Enter a valid unit for conversion");

        double baseValue = this.convertToBaseUnit();
        double result = convertFromBaseToTargetUnit(baseValue, targetUnit);

        return new Length(result, targetUnit);
    }

    // Add two length and return
    public Length add(Length thatLength) {
        if(thatLength == null) throw new IllegalArgumentException("Enter a valid length for addition");

        double value1 = this.convertToBaseUnit();
        double value2 = thatLength.convertToBaseUnit();
        double total = value1 + value2; 
        double result = convertFromBaseToTargetUnit(total, unit);

        return new Length(result, this.unit);
    }

    // Add and return length in target unit
    public Length add(Length thatLength, LengthUnit targetUnit) {
        if(thatLength == null) throw new IllegalArgumentException("The length to add cannot be null");
        if(targetUnit == null) throw new IllegalArgumentException("The target unit cannot be null");

        return addAndConvert(thatLength, targetUnit);
    }

    // Private method to perform addition
    private Length addAndConvert(Length thatLength, LengthUnit targetUnit) {
        double totalBase = this.convertToBaseUnit() + thatLength.convertToBaseUnit();
        double result = convertFromBaseToTargetUnit(totalBase, targetUnit);

        return new Length(result, targetUnit);
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
        System.out.println();

        Length length3 = new Length(1.0, LengthUnit.YARDS);
        Length length4 = new Length(36.0, LengthUnit.INCHES);
        System.out.println("Are lengths equal : " + length3.equals(length4));
        System.out.println();

        Length length5 = new Length(100.0, LengthUnit.CENTIMETERS);
        Length length6 = new Length(39.3701, LengthUnit.INCHES);
        System.out.println("Are lengths equal : " + length5.equals(length6));
        System.out.println();

        System.out.println("36 Inches to equals to : " + length4.convertTo(LengthUnit.YARDS));
        System.out.println();

        Length length7 = new Length(1.0, LengthUnit.FEET);
        Length length8 = new Length(2.0, LengthUnit.FEET);
        System.out.println("Addition result : " + length7.add(length8));
        System.out.println();

        System.out.println("Addition result : " + length1.add(length2));
        System.out.println();

        System.out.println("Addition result : " + length2.add(length1));
        System.out.println();

        System.out.println("Addition result : " + length1.add(length2, LengthUnit.YARDS));
        System.out.println();
    }
}
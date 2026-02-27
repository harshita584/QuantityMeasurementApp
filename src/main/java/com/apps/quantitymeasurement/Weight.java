package com.apps.quantitymeasurement;

public class Weight {
    private double value;
    private WeightUnit unit;

    // constructor
    public Weight(double value, WeightUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Enter a valid weight unit");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Enter a valid double value for conversion");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    // Convert this weight to base unit
    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    // Convert from base unit to target unit
    private double convertFromBaseToTargetUnit(double weightInGrams, WeightUnit targetUnit) {
        return targetUnit.convertFromBaseUnit(weightInGrams);
    }

    // Compare two weights
    private boolean compare(Weight thatWeight) {
        if (thatWeight == null) return false;

        double value1 = this.convertToBaseUnit();
        double value2 = thatWeight.convertToBaseUnit();

        return Double.compare(value1, value2) == 0;
    }

    // Convert to target unit
    public Weight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Enter a valid unit for conversion");

        double baseValue = this.convertToBaseUnit();
        double result = convertFromBaseToTargetUnit(baseValue, targetUnit);

        return new Weight(result, targetUnit);
    }

    // Add and return in this unit
    public Weight add(Weight thatWeight) {
        if (thatWeight == null) throw new IllegalArgumentException("Enter a valid weight for addition");

        double value1 = this.convertToBaseUnit();
        double value2 = thatWeight.convertToBaseUnit();
        double totalBase = value1 + value2;
        double result = convertFromBaseToTargetUnit(totalBase, unit);

        return new Weight(result, unit);
    }

    // Add and return in target unit
    public Weight add(Weight weight, WeightUnit targetUnit) {
        if (weight == null) throw new IllegalArgumentException("The weight to add cannot be null");
        if (targetUnit == null) throw new IllegalArgumentException("The target unit cannot be null");

        return addAndConvert(weight, targetUnit);
    }

    // Private helper for addition
    private Weight addAndConvert(Weight weight, WeightUnit targetUnit) {
    	double value1 = this.convertToBaseUnit();
    	double value2 = weight.convertToBaseUnit();
        double totalBase = value1 + value2;
        
        double result = convertFromBaseToTargetUnit(totalBase, targetUnit);
        return new Weight(result, targetUnit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Weight thatWeight = (Weight) o;
        return compare(thatWeight);
    }

    @Override
    public String toString() {
        return this.value + "" + unit;
    }

    public static void main(String[] args) {
        Weight weight1 = new Weight(1000.0, WeightUnit.GRAM);
        Weight weight2 = new Weight(1.0, WeightUnit.KILOGRAM);
        System.out.println("Are weights equal : " + weight1.equals(weight2));
        System.out.println();

        Weight weight3 = new Weight(2.0, WeightUnit.KILOGRAM);
        Weight weight4 = new Weight(2000.0, WeightUnit.GRAM);
        System.out.println("Are weights equal : " + weight3.equals(weight4));
        System.out.println();

        System.out.println("1 Kilogram in grams equals " +  weight2.convertTo(WeightUnit.GRAM));
        System.out.println();

        Weight weight5 = new Weight(500.0, WeightUnit.GRAM);
        Weight weight6 = new Weight(500.0, WeightUnit.GRAM);
        System.out.println("Result : " + weight5.add(weight6));
        System.out.println();

        System.out.println("Result : " + weight1.add(weight2, WeightUnit.KILOGRAM));
        System.out.println();
    }
}
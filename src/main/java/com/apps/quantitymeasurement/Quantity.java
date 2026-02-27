package com.apps.quantitymeasurement;

public class Quantity<U extends IMeasurable> {
    private double value;
    private U unit;

    // Constructor
    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid numeric value");

        this.value = value;
        this.unit = unit;
    }

    // Getters
    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // Convert to target unit
    public double convertTo(U targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target cannot be null");
        if (!unit.getClass().equals(targetUnit.getClass())) throw new IllegalArgumentException("Provide similar unit type for conversion");

        double baseValue = unit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(baseValue);
    }

    // Add and return in this unit
    public Quantity<U> add(Quantity<U> other) {
        if (other == null) throw new IllegalArgumentException("Quantity to add cannot be null");
        if (!unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException("Provide similar unit type for conversion");

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);
        double totalBase = base1 + base2;

        double result = unit.convertFromBaseUnit(totalBase);
        return new Quantity<>(result, unit);
    }

    // Add and return in target unit
    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null) throw new IllegalArgumentException("Quantity cannot be null");
        if (targetUnit == null) throw new IllegalArgumentException("Target cannot be null");
        if (!unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException("Provide similar unit type for conversion");
        if (!unit.getClass().equals(targetUnit.getClass())) throw new IllegalArgumentException("Provide similar unit type for conversion");

        double value1 = unit.convertToBaseUnit(value);
        double value2 = other.unit.convertToBaseUnit(other.value);
        double total = value1 + value2;
        double result = targetUnit.convertFromBaseUnit(total);
        
        return new Quantity<>(result, targetUnit);
    }
    
    // Subtract and return in this unit
    public Quantity<U> subtract(Quantity<U> other) {
        if (other == null) throw new IllegalArgumentException("Quantity cannot be null");
        if (!unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException("Provide similar unit for deletion");

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double resultBase = base1 - base2;
        double result = unit.convertFromBaseUnit(resultBase);

        return new Quantity<>(result, unit);
    }

    // Subtract and return in target unit
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        if (other == null) throw new IllegalArgumentException("Quantity cannot be null");
        if (targetUnit == null) throw new IllegalArgumentException("Target cannot be null");
        if (!unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException("Provide similar unit for deletion");
        if (!unit.getClass().equals(targetUnit.getClass())) throw new IllegalArgumentException("Provide similar target unit for deletion");

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double resultBase = base1 - base2;
        double result = targetUnit.convertFromBaseUnit(resultBase);

        return new Quantity<>(result, targetUnit);
    }

    public Quantity<U> divide(Quantity<U> other) {
        if (other == null) throw new IllegalArgumentException("Quantity cannot be null");
        if (!unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException("Provide similar unit type for division");

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);
        
        if (base2 == 0) throw new IllegalArgumentException("Cannot divide by zero");
        double result = base1 / base2;

        return new Quantity<>(result, unit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Quantity<?> that = (Quantity<?>) obj;
        if (!this.unit.getClass().equals(that.unit.getClass())) return false;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = that.unit.convertToBaseUnit(that.value);

        return Double.compare(base1, base2) == 0;
    }

    @Override
    public String toString() {
        return value + "" + unit;
    }

    public static void main(String[] args) {
        Quantity<LengthUnit> lengthInFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> lengthInInches = new Quantity<>(120.0, LengthUnit.INCHES);
        boolean isEqual = lengthInFeet.equals(lengthInInches);
        System.out.println("Are lengths equal? " + isEqual);
        System.out.println();

        Quantity<WeightUnit> weightInKilograms = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> weightInGrams = new Quantity<>(1000.0, WeightUnit.GRAM);
        isEqual = weightInKilograms.equals(weightInGrams);
        System.out.println("Are weights equal?" + isEqual);
        System.out.println();

        double convertedLength = lengthInFeet.convertTo(LengthUnit.INCHES);
        System.out.println("10 feet in inches " + convertedLength);
        System.out.println();

        Quantity<LengthUnit> totalLength = lengthInFeet.add(lengthInInches, LengthUnit.FEET);
        System.out.println("Total Length in feet " + totalLength.getValue() + " " + totalLength.getUnit());
        System.out.println();

        Quantity<WeightUnit> weightInPounds = new Quantity<>(2.0, WeightUnit.POUND);
        Quantity<WeightUnit> totalWeight = weightInKilograms.add(weightInPounds, WeightUnit.KILOGRAM);
        System.out.println("Total Weight in kilograms " + totalWeight.getValue() + " " + totalWeight.getUnit());
        System.out.println();
        
        Quantity<VolumeUnit> oneLitre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> thousandMl = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        System.out.println("Are volumes equal? " + oneLitre.equals(thousandMl));
        System.out.println();

        double converted = oneLitre.convertTo(VolumeUnit.MILLILITRE);
        System.out.println("1 litre in millilitres: " + converted);
        System.out.println();

        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> sumVolume = oneLitre.add(gallon, VolumeUnit.LITRE);
        System.out.println("Sum in litres: " + sumVolume.getValue() + " " + sumVolume.getUnit());
        System.out.println();
    }
}
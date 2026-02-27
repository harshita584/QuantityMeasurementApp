package com.apps.quantitymeasurement;

public class Quantity<U extends IMeasurable> {
    private double value;
    private U unit;

    // Constructor
    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    // Getter
    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // Convert to target unit
    public double convertTo(U targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target cannot be null");
        if (!unit.getClass().equals(targetUnit.getClass())) throw new IllegalArgumentException("Provide similar unit type");

        double base = unit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(base);
    }

    // Add
    public Quantity<U> add(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double result = unit.convertFromBaseUnit(baseResult);
        
        return new Quantity<>(round(result), unit);
    }

    // Add with target unit
    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double result = targetUnit.convertFromBaseUnit(baseResult);
        
        return new Quantity<>(round(result), targetUnit);
    }

    // Subtract 
    public Quantity<U> subtract(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double result = unit.convertFromBaseUnit(baseResult);
        
        return new Quantity<>(round(result), unit);
    }

    // Subtract with target unit
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double result = targetUnit.convertFromBaseUnit(baseResult);
        
        return new Quantity<>(round(result), targetUnit);
    }

    // Divide 
    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    // Common validation
    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetRequired) {
        if(other == null) throw new IllegalArgumentException("Quantity cannot be null");

        if(!unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException("Provide similar unit type");

        if(!Double.isFinite(value) || !Double.isFinite(other.value)) throw new IllegalArgumentException("Invalid numeric value");

        if(targetRequired) {
            if(targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
            if(!unit.getClass().equals(targetUnit.getClass())) throw new IllegalArgumentException("Provide similar target unit type");
        }
    }

    // Core arithmetic logic in base unit
    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {
        double value1 = unit.convertToBaseUnit(value);
        double value2 = other.unit.convertToBaseUnit(other.value);

        return operation.compute(value1, value2);
    }

    // Rounding helper
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // Enum 
    private enum ArithmeticOperation {
        ADD {
            @Override
            public double compute(double thisBase, double otherBase) {
                return thisBase + otherBase;
            }
        },

        SUBTRACT {
            @Override
            public double compute(double thisBase, double otherBase) {
                return thisBase - otherBase;
            }
        },

        DIVIDE {
            @Override
            public double compute(double thisBase, double otherBase) {
            	System.out.println(otherBase);
                if(otherBase == 0.0) throw new IllegalArgumentException("Cannot divide by zero");
                return thisBase / otherBase;
            }
        };

        public abstract double compute(double thisBase, double otherBase);
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quantity<?> that = (Quantity<?>) obj;
        if (!this.unit.getClass().equals(that.unit.getClass())) return false;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = that.unit.convertToBaseUnit(that.value);

        return Double.compare(base1, base2) == 0;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}
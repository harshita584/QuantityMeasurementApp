package com.apps.quantitymeasurement.quantity;
import java.util.function.*;

import com.apps.quantitymeasurement.unit.IMeasurable;
import com.apps.quantitymeasurement.unit.LengthUnit;
import com.apps.quantitymeasurement.unit.TemperatureUnit;
import com.apps.quantitymeasurement.unit.WeightUnit;

public class Quantity<U extends IMeasurable> {
	private static final double EPSILON = 0.0001;
	private final Double value;
	private final U unit;
	
	public Quantity(double value, U unit) {
		
		if(Double.isNaN(value) || Double.isInfinite(value))
			throw new IllegalArgumentException("Value is invalid");
		if(unit==null)
			throw new IllegalArgumentException("Unit cannot be null");
		this.value= value;
		this.unit = unit;
	}
	public double getValue() {
		return value;
	}
	public U getUnit() {
		return unit;
	}
		
	@Override 
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(obj==null || this.getClass()!=obj.getClass()) return false;
		
		Quantity quan = (Quantity) obj;
		if(this.getUnit().getClass()!=quan.getUnit().getClass()) return false;
		
		return Math.abs(this.value-quan.convertTo(this.unit).getValue())<EPSILON;
	}
	
	public Quantity<U> convertTo(U targetUnit) {
        if (this.unit instanceof TemperatureUnit) {
            double base = this.unit.convertToBaseUnit(this.value);
            double result = ((TemperatureUnit) targetUnit).convertFromBaseUnit(base);
            return new Quantity<>(result, targetUnit);
        }
        double value = this.value * this.unit.getConversionFactor() / targetUnit.getConversionFactor();
        return new Quantity<>(value, targetUnit);
    }
	public Quantity<U>  add(Quantity<U> quan){
		quan = quan.convertTo(this.unit);
		Quantity add = new Quantity(this.performArithmeticOperation(quan, ArithmeticOperation.ADD), this.getUnit());
		return add;
	}
	
	public Quantity<U> add(Quantity<U> quan, U unit){
		quan = quan.convertTo(unit);
		Quantity<U> quan2 = this.convertTo(unit);
		Quantity add = new Quantity<>(quan2.performArithmeticOperation(quan, ArithmeticOperation.ADD), unit);
		
		return add;
	}
	public Quantity<U> subtract(Quantity<U> quantity)throws IllegalArgumentException{
		if(!this.getUnit().getClass().equals(quantity.getUnit().getClass())) {
			throw new IllegalArgumentException("different measurement unit");
		}
		Quantity<U> q = quantity.convertTo(this.unit);
		Quantity<U> ans = new Quantity(this.performArithmeticOperation(q, ArithmeticOperation.SUBTRACT), this.getUnit());
		return ans;
	}
	public Quantity<U> subtract(Quantity<U> quantity, U targetUnit) throws IllegalArgumentException{
		if(!this.getUnit().getClass().equals(quantity.getUnit().getClass())) {
			throw new IllegalArgumentException("different measurement unit");
		}
		Quantity<U> quan =quantity.convertTo(targetUnit);
		Quantity<U> quan2 = this.convertTo(targetUnit);
		Quantity<U> ans = new Quantity(quan2.performArithmeticOperation(quan, ArithmeticOperation.SUBTRACT), targetUnit);
		return ans;
	}
	public double divide(Quantity<U> quantity) throws ArithmeticException, IllegalArgumentException{
		if(!this.getUnit().getClass().equals(quantity.getUnit().getClass())) {
			throw new IllegalArgumentException("different measurement unit");
		}
		if(quantity.getValue()==0) {
			throw new ArithmeticException("cannot divide by zero");
		}
		double div = this.performArithmeticOperation(quantity, ArithmeticOperation.DIVIDE);
		return div;
	}
	public double divide(Quantity<U> quantity, U targetUnit) throws ArithmeticException, IllegalArgumentException{
		if(!this.getUnit().getClass().equals(quantity.getUnit().getClass())) {
			throw new IllegalArgumentException("different measurement unit");
		}
		if(quantity.getValue()==0) {
			throw new ArithmeticException("cannot divide by zero");
		}
		double div = this.performArithmeticOperation(quantity, ArithmeticOperation.DIVIDE);		
		return div;
	}
	public double convertToBaseUnit() {
		return value*unit.getConversionFactor();
	}
	
	@Override 
	public int hashCode(){
		double base =convertToBaseUnit();
	    long rounded = Math.round(base / EPSILON);
	    return Long.hashCode(rounded);
	}
	
	@Override 
	public String toString() {
		return "Qunatity [ value="+value+", unit="+unit+"]";
	}
	
	private enum ArithmeticOperation{
		ADD((a,b) -> a+b),
		SUBTRACT((a,b)-> a-b),
		DIVIDE((a,b) ->{
			if(b==0.0) throw new ArithmeticException("Divide by zero");
			return a/b;
		});
		
		private final DoubleBinaryOperator operation;
		
		private ArithmeticOperation(DoubleBinaryOperator operation) {
			// TODO Auto-generated constructor stub
			this.operation = operation;
		}
		public double compute(double a, double b) {
			return this.operation.applyAsDouble(a, b);
		}
		
	}
	
	private double performArithmeticOperation(Quantity<U> other, ArithmeticOperation operation) {
        // UC14: Validate support before doing math
        this.unit.validateOperationSupport(operation.name());
        
        double temp = other.convertTo(this.unit).getValue();
        return operation.compute(this.value, temp);
    }
	public static void main(String[] args) {
		Quantity<LengthUnit> lengthInFeet = new Quantity(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthInInches = new Quantity(120.0, LengthUnit.INCHES);
		boolean isEqual = lengthInFeet.equals(lengthInInches);
		System.out.println("Are lengths equal?: "+isEqual);
		
		Quantity<WeightUnit> weightInKilogram = new Quantity(1.0,  WeightUnit.KILOGRAM);
		Quantity<WeightUnit> weightInGram = new Quantity(1000.0, WeightUnit.GRAM);
		isEqual = weightInKilogram.equals(weightInGram);
		System.out.println("Are weights equal?: "+isEqual);
		
		double convertedLength = lengthInFeet.convertTo(LengthUnit.INCHES).getValue();
		System.out.println("!0 feet in inches: "+convertedLength);
		
		Quantity<LengthUnit> totalLength = lengthInFeet.add(lengthInInches);
		System.out.println(totalLength);
		
		Quantity<WeightUnit> weightInPounds = new Quantity(2.0, WeightUnit.POUND);
		Quantity<WeightUnit> totalWeight = weightInKilogram.add(weightInGram);
		System.out.println(totalWeight);
		
		Quantity<LengthUnit> len1 = new Quantity(1, LengthUnit.FEET);
		Quantity<LengthUnit> len2 = new Quantity(12, LengthUnit.INCHES);
		
		System.out.println(len1.performArithmeticOperation(len2, ArithmeticOperation.ADD));
		
  	}
}

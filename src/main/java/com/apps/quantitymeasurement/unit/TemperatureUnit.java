package com.apps.quantitymeasurement.unit;
import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {
	CELSIUS(val -> val, val->val),
	FAHRENHEIT(c->(c*9/5)+32, f->(f-32)*5/9);
	
	private final Function<Double, Double> fromBase;
	private final Function<Double, Double> toBase;
	
	TemperatureUnit(Function<Double, Double> fromBase, Function<Double, Double> toBase){
		this.fromBase = fromBase;
		this.toBase = toBase;
	}
	@Override
    public double convertToBaseUnit(double value) {
        return toBase.apply(value);
    }

    public double convertFromBaseUnit(double value) {
        return fromBase.apply(value);
    }

    @Override
    public boolean supportsArithmetic() {
        return false; // Temperature units do not support add/sub/div
    }

    @Override
    public double getConversionFactor() { return 1.0; } // Not used for non-linear
    
    @Override
    public String getUnitName() { return this.name(); }
	@Override
	public String getMeasurementType() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName();
	}
	@Override
	public IMeasurable getUnitInstance(String unitName) {
		for(TemperatureUnit unit: TemperatureUnit.values()) {
			if(unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid temperature unit: "+ unitName);
	}
	
}

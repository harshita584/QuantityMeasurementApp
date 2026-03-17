package com.apps.quantitymeasurement.entity;


interface IMeasurableUnit{
	public String getUnitName();
	public String getMeasurementType();
}
public class QuantityDTO {
	
	enum LengthUnit implements IMeasurableUnit{
		FEET, INCHES, YARDS, CENTIMETERS;
		
		@Override
		public String getUnitName() {
			return this.name();
		}
		
		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}
	
	enum VolumeUnit implements IMeasurableUnit{
		LITRE, MILLILETRE,GALLON;

		@Override
		public String getUnitName() {
			// TODO Auto-generated method stub
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			// TODO Auto-generated method stub
			return this.getClass().getSimpleName();
		}
	}
	
	enum WeightUnit implements IMeasurableUnit{
		KILOGRAM, GRAM, POUND;

		@Override
		public String getUnitName() {
			// TODO Auto-generated method stub
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			// TODO Auto-generated method stub
			return this.getClass().getSimpleName();
		}
	}
	
	enum TemperatureUnit implements IMeasurableUnit{
		CELSIUS, FAHRENHEIT;

		@Override
		public String getUnitName() {
			// TODO Auto-generated method stub
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			// TODO Auto-generated method stub
			return this.getClass().getName();
		}
	}
	
	private double value;
	private String unit;
	private String measurementType;
	
	public QuantityDTO(double value, String unit, String measurementType){
		this.value = value;
		this.unit = unit;
		this.measurementType = measurementType;
	}

	public double getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	public String getMeasurementType() {
		return measurementType;
	}

	@Override
	public String toString() {
		return "QuantityDTO [value=" + value + ", unit=" + unit + ", measurementType=" + measurementType + "]";
	}
	
	public static void main(String[] args) {
		
	}
}

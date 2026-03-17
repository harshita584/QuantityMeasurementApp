package com.apps.quantitymeasurement.entity;

import java.util.Objects;

public class QuantityMeasurementEntity implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		public double thisValue;
		public String thisUnit;
		public String thisMeasurementType;
		public double thatValue;
		public String thatUnit;
		public String thatMeasurementType;
		
		public String operation;
		public double resultValue;
		public String resultUnit;
		public String resultMeasurementType;
		
		public String resultString;
		
		public boolean isError;
		
		public String errorMessage;
		
		public QuantityMeasurementEntity(QuantityDTO thisQuantity,QuantityDTO  thatQuantity,String operation, String result) {
                this(thisQuantity, thatQuantity, operation);
				this.resultString = result;
		}
		
		public QuantityMeasurementEntity(QuantityDTO  thisQuantity,QuantityDTO  thatQuantity,String operation,QuantityDTO  result) {
				this(thisQuantity, thatQuantity, operation);
				this. resultValue = result.getValue();
				this.resultUnit = result.getUnit();
				this. resultMeasurementType = result.getMeasurementType();}
		
		public QuantityMeasurementEntity(QuantityDTO  thisQuantity,QuantityDTO  thatQuantity,String operation, String errorMessage, boolean isError) {
				this(thisQuantity, thatQuantity, operation);
				this.errorMessage = errorMessage;
				this.isError = isError;
		}
		public QuantityMeasurementEntity(QuantityDTO  thisQuantity,QuantityDTO  thatQuantity, String operation) {
			this.thisValue = thisQuantity.getValue();
			this.thisUnit =  thisQuantity.getUnit().toString();
			this.thatValue = thatQuantity.getValue();
			this.thatUnit = thatQuantity.getUnit().toString();
			this.operation = operation;
		}
		
		
		
		public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType,
				double thatValue, String thatUnit, String thatMeasurementType, String operation, double resultValue,
				String resultUnit, String resultMeasurementType, String resultString, boolean isError,
				String errorMessage) {
			super();
			this.thisValue = thisValue;
			this.thisUnit = thisUnit;
			this.thisMeasurementType = thisMeasurementType;
			this.thatValue = thatValue;
			this.thatUnit = thatUnit;
			this.thatMeasurementType = thatMeasurementType;
			this.operation = operation;
			this.resultValue = resultValue;
			this.resultUnit = resultUnit;
			this.resultMeasurementType = resultMeasurementType;
			this.resultString = resultString;
			this.isError = isError;
			this.errorMessage = errorMessage;
		}

		public QuantityMeasurementEntity() {
			// TODO Auto-generated constructor stub
		}

		@Override
	    public boolean equals(Object obj) {

	        if (this == obj)
	            return true;

	        if (obj == null || getClass() != obj.getClass())
	            return false;

	        QuantityMeasurementEntity that = (QuantityMeasurementEntity) obj;
	        return Double.compare(that.thisValue, thisValue) == 0 &&
	                Double.compare(that.thatValue, thatValue) == 0 &&
	                Double.compare(that.resultValue, resultValue) == 0 &&
	                isError == that.isError &&
	                Objects.equals(thisUnit, that.thisUnit) &&
	                Objects.equals(thisMeasurementType, that.thisMeasurementType) &&
	                Objects.equals(thatUnit, that.thatUnit) &&
	                Objects.equals(thatMeasurementType, that.thatMeasurementType) &&
	                Objects.equals(operation, that.operation) &&
	                Objects.equals(resultUnit, that.resultUnit) &&
	                Objects.equals(resultMeasurementType, that.resultMeasurementType) &&
	                Objects.equals(resultString, that.resultString) &&
	                Objects.equals(errorMessage, that.errorMessage);
	    }

		@Override
		public String toString() {
			return "QuantityMeasurementEntity [thisValue=" + thisValue + ", thisUnit=" + thisUnit
					+ ", thisMeasurementType=" + thisMeasurementType + ", thatValue=" + thatValue + ", thatUnit="
					+ thatUnit + ", thatMeasurementType=" + thatMeasurementType + ", operation=" + operation
					+ ", resultValue=" + resultValue + ", resultUnit=" + resultUnit + ", resultMeasurementType="
					+ resultMeasurementType + ", resultString=" + resultString + ", isError=" + isError
					+ ", errorMessage=" + errorMessage + "]";
		}
		
		
}
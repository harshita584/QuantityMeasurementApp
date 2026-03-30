package com.app.quantitymeasurement.unit;

@FunctionalInterface
interface SupportsArithmetic {
    boolean isSupported();
}

public interface IMeasurable {

    SupportsArithmetic supportsArithmetic = () -> true;

    String getUnitName();

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
    }

    default String getMeasurementType() {
        return this.getClass().getSimpleName();
    }

    static IMeasurable getUnitInstance(String unitName, Class<?> enumClass) {
        for (Object constant : enumClass.getEnumConstants()) {
            IMeasurable unit = (IMeasurable) constant;

            if (unit.getUnitName().equalsIgnoreCase(unitName)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid unit: " + unitName);
    }
}
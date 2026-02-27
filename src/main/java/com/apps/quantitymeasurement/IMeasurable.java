package com.apps.quantitymeasurement;


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

    // Override and throw error if the unit does not support, default allow
    default void validateOperationSupport(String operation) {
        
    }
}
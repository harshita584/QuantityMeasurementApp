package com.example.measurementservice.core;

/*
 * UC14 Enhancements:
 * Adds optional arithmetic capability support.
 * Existing units remain fully compatible.
 */

public interface IMeasurable {

    // ===== EXISTING REQUIRED METHODS (UNCHANGED) =====
    String getUnitName();

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);


    // ===== NEW UC14 ADDITIONS =====

    // default lambda → all units support arithmetic by default
    SupportsArithmetic supportsArithmetic = () -> true;

    // default method → existing units inherit TRUE
    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    /*
     * Default validation method.
     * Units that do NOT support arithmetic (Temperature) will override this.
     */
    default void validateOperationSupport(String operation) {
        // do nothing by default
    }
}

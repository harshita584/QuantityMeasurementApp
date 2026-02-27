package com.apps.quantitymeasurement;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {
    CELSIUS(false),
    FAHRENHEIT(true);

    private final boolean isFahrenheit;

    final Function<Double, Double> FAHRENHEIT_TO_CELSIUS = (fahrenheit) -> (fahrenheit - 32) * 5 / 9;

    final Function<Double, Double> CELSIUS_TO_CELSIUS = (celsius) -> celsius;

    // conversion function
    Function<Double, Double> conversionValue;

    SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(boolean isFahrenheit) {
        this.isFahrenheit = isFahrenheit;

        if (isFahrenheit) conversionValue = FAHRENHEIT_TO_CELSIUS;
        else conversionValue = CELSIUS_TO_CELSIUS;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return conversionValue.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (isFahrenheit) return (baseValue * 9 / 5) + 32;
        return baseValue;
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        if (!supportsArithmetic.isSupported()) {
        	String message = this.name() + " does not support " + operation + " operations.";
            throw new UnsupportedOperationException(message);
        }
    }
}
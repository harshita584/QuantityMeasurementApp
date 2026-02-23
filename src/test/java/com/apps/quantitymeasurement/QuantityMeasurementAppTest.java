package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.apps.quantitymeasurement.QuantityMeasurementApp.Feet;

public class QuantityMeasurementAppTest {
    @Test
    public void testFeetEquality_SameValue() {
        Feet firstValue = new Feet(1.0);
        Feet secondValue = new Feet(1.0);

        assertTrue(firstValue.equals(secondValue));
    }

    @Test
    public void testFeetEquality_DifferentValue() {
        Feet firstValue = new Feet(1.0);
        Feet secondValue = new Feet(2.0);

        assertFalse(firstValue.equals(secondValue));
    }

    @Test
    public void testFeetEquality_NullComparison() {
        Feet feet = new Feet(1.0);

        assertFalse(feet.equals(null));
    }

    @Test
    public void testFeetEquality_DifferentClass() {
        Feet feet = new Feet(1.0);

        assertFalse(feet.equals(new String("1")));
    }

    @Test
    public void testFeetEquality_SameReference() {
        Feet feet = new Feet(1.0);

        assertTrue(feet.equals(feet));
    }
}
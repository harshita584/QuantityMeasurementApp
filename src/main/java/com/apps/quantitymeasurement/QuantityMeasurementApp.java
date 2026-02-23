package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }
    
    public static void main(String[] args) {
    	 Feet f1 = new Feet(5.0);
         Feet f2 = new Feet(5.0);
         Feet f3 = new Feet(6.0);

         System.out.println("f1 equals f2: " + f1.equals(f2)); 
         System.out.println("f1 equals f3: " + f1.equals(f3)); 
    }
}
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
    
    public static class Inches{
    	private final double value;
    	
    	public Inches(double value) {
    		this.value = value;
    	}
    	
    	@Override
    	public boolean equals(Object obj) {
    		if(this == obj) return true;
    		if(obj == null) return false;
    		if(this.getClass() != obj.getClass()) return false;
    		Inches other = (Inches) obj;
    		return Double.compare(this.value, other.value) == 0;
    	}
    }
    
    public static void demonstrateFeetEquality() {
    	Feet f1 = new Feet(5.0);
    	Feet f2 = new Feet(5.0);
    	
    	System.out.println("f1 equals f2: " + f1.equals(f2));
    }
    
    public static void demonstrateInchEquality() {
    	Inches i1 = new Inches(7.0);
    	Inches i2 = new Inches(7.0);
    	
    	System.out.println("i1 equals i2: " + i1.equals(i2));
    }
    
    public static void main(String[] args) {
    	demonstrateFeetEquality();
    	demonstrateInchEquality();
    }
}

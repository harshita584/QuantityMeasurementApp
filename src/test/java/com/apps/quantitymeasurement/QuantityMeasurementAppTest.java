package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.quantity.Quantity;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.services.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.unit.LengthUnit;
import com.apps.quantitymeasurement.unit.TemperatureUnit;
import com.apps.quantitymeasurement.unit.VolumeUnit;
import com.apps.quantitymeasurement.unit.WeightUnit;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;


public class QuantityMeasurementAppTest {
	
	private QuantityMeasurementServiceImpl service;
	private QuantityMeasurementController controller;
	private QuantityMeasurementCacheRepository repository;
	
	@BeforeEach
	void setup() {
		repository = new QuantityMeasurementCacheRepository().getInstance();
		service = new QuantityMeasurementServiceImpl(repository);
		controller = new QuantityMeasurementController(service);
	}


    @Test
    void testService_CompareEquality_SameUnit_Success() {

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTHUNIT");
        QuantityDTO q2 = new QuantityDTO(10, "FEET", "LENGTHUNIT");

        Assertions.assertTrue(service.compare(q1, q2));
    }

    @Test
    void testService_CompareEquality_DifferentUnit_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTHUNIT");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTHUNIT");

        Assertions.assertTrue(service.compare(q1, q2));
    }

    @Test
    void testService_Convert_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTHUNIT");
        QuantityDTO target = new QuantityDTO(0, "INCHES", "LENGTHUNIT");

        QuantityDTO result = service.convert(q1, target);

        Assertions.assertEquals(12, result.getValue());
    }

    @Test
    void testService_Add_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTHUNIT");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTHUNIT");

        QuantityDTO result = service.add(q1, q2);

        Assertions.assertEquals(2, result.getValue());
    }

    @Test
    void testService_Subtract_Success() {

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTHUNIT");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "LENGTHUNIT");

        QuantityDTO result = service.subtract(q1, q2);

        Assertions.assertEquals(5, result.getValue());
    }

    @Test
    void testService_Divide_Success() {

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTHUNIT");
        QuantityDTO q2 = new QuantityDTO(2, "FEET", "LENGTHUNIT");

        Assertions.assertEquals(5, service.divide(q1, q2));
    }

    @Test
    void testService_Divide_ByZero_Error() {

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTHUNIT");
        QuantityDTO q2 = new QuantityDTO(0, "FEET", "LENGTHUNIT");

        Assertions.assertThrows(ArithmeticException.class,
                () -> service.divide(q1, q2));
    }

    // ==============================
    // CONTROLLER TESTS
    // ==============================

    @Test
    void testController_DemonstrateEquality_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTHUNIT");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTHUNIT");

        Assertions.assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    void testController_DemonstrateAddition_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTHUNIT");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTHUNIT");

        QuantityDTO result = controller.performAddition(q1, q2);

        Assertions.assertEquals(2, result.getValue());
    }

    // ==============================
    // LAYER COMMUNICATION TESTS
    // ==============================

    @Test
    void testDataFlow_ControllerToService() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTHUNIT");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTHUNIT");

        boolean result = controller.performComparison(q1, q2);

        Assertions.assertTrue(result);
    }

    @Test
    void testDataFlow_ServiceToRepository() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTHUNIT");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTHUNIT");

        service.compare(q1, q2);

        Assertions.assertFalse(repository.getAllMeasurements().isEmpty());
    }

    // ==============================
    // ERROR PROPAGATION
    // ==============================

    @Test
    void testService_NullEntity_Rejection() {

        Assertions.assertThrows(NullPointerException.class,
                () -> service.compare(null, null));
    }
    @Test
    void testService_ArithmeticErrorPersistence() {
        QuantityDTO q1 = new QuantityDTO(10, "CELSIUS", "TEMPERATUREUNIT");
        QuantityDTO q2 = new QuantityDTO(20, "CELSIUS", "TEMPERATUREUNIT");

        Assertions.assertThrows(UnsupportedOperationException.class, () -> service.add(q1, q2));

        QuantityMeasurementEntity errorLog = repository.getAllMeasurements().get(repository.getAllMeasurements().size() - 1);
        Assertions.assertTrue(errorLog.isError);
        Assertions.assertNotNull(errorLog.errorMessage);
    }
    @Test
    public void testFeetEquality() {
    	Quantity len1 = new Quantity(2,LengthUnit.FEET);
    	Quantity len2 = new Quantity(2,LengthUnit.FEET);
    	Assertions.assertTrue(len1.equals(len2));
    }
    
    @Test
    public void testIncheEquality() {
    	Quantity len1 = new Quantity(2,LengthUnit.INCHES);
    	Quantity len2 = new Quantity(2,LengthUnit.INCHES);
    	Assertions.assertTrue(len1.equals(len2));
    }
    
    @Test
    public void testFeetIncheComparision() {
    	Quantity len1 = new Quantity(2,LengthUnit.FEET);
    	Quantity len2 = new Quantity(24,LengthUnit.INCHES);
    	Assertions.assertTrue(len1.equals(len2));
    }
    
    @Test
    public void testFeetInEquality() {
    	Quantity len1 = new Quantity(2,LengthUnit.FEET);
    	Quantity len2 = new Quantity(24,LengthUnit.FEET);
    	Assertions.assertTrue(!len1.equals(len2));
    }
    
    @Test
    public void testIncheInEquality() {
    	Quantity len1 = new Quantity(2,LengthUnit.INCHES);
    	Quantity len2 = new Quantity(24,LengthUnit.INCHES);
    	Assertions.assertTrue(!len1.equals(len2));
    }
  
    
    @Test 
    public void testEquality_YardToYard_SameValue() {
    	Quantity l1 = new Quantity(1, LengthUnit.YARDS);
    	Quantity l2 = new Quantity(2, LengthUnit.YARDS);
    	Assertions.assertFalse(l1.equals(l2));
    }
    
    @Test 
    public void testEquality_YardToYard_DifferentValue() {
    	Quantity l1 = new Quantity(1, LengthUnit.YARDS);
    	Quantity l2 = new Quantity(2, LengthUnit.YARDS);
    	Assertions.assertFalse(l1.equals(l2));
    }
    
    @Test 
    public void testEquality_YardToFeet_EquivalentValue() {
    	Quantity l1 = new Quantity(1,LengthUnit.YARDS);
    	Quantity l2 = new Quantity(3, LengthUnit.FEET);
    	Assertions.assertTrue(l1.equals(l2));
    }
    
    @Test
    public void testEquality_FeetToYard_EquivalentValue() {
    	Quantity l1 = new Quantity(1,LengthUnit.YARDS);
    	Quantity l2 = new Quantity(3, LengthUnit.FEET);
    	Assertions.assertTrue(l2.equals(l1));
    }
    
    @Test 
    public void testEquality_YardToInches_EquivalentValue() {
    	Quantity l1 = new Quantity(1, LengthUnit.YARDS);
    	Quantity l2 = new Quantity(36, LengthUnit.INCHES);
    	Assertions.assertTrue(l1.equals(l2));
    }
    
    @Test
    public void testEquality_InchesToYard_EquivalentValue() {
    	Quantity l1 = new Quantity(36, LengthUnit.INCHES);
    	Quantity l2 = new Quantity(1, LengthUnit.YARDS);
    	Assertions.assertTrue(l1.equals(l2));
    }
    
    @Test
    public void testEquality_YardToFeet_NonEquivalentValue() {
    	Quantity l1 = new Quantity(1, LengthUnit.YARDS);
    	Quantity l2 = new Quantity(2, LengthUnit.FEET);
    	Assertions.assertFalse(l1.equals(l2));
    }
    
    @Test 
    public void testEquality_centimetersToInches_EquivalentValue() {
    	Quantity l1 = new Quantity(1, LengthUnit.CENTIMETERS);
    	Quantity l2 = new Quantity(0.393701, LengthUnit.INCHES);
    	Assertions.assertTrue(l1.equals(l2));
    }
    
    @Test
    public void testEquality_centimetersToFeet_NonEquivalentValue() {
    	Quantity l1 = new Quantity(1, LengthUnit.CENTIMETERS);
    	Quantity l2 = new Quantity(1, LengthUnit.FEET);
    	Assertions.assertFalse(l1.equals(l2));
    }
    
    @Test
    public void testEquality_MultiUnit_TransitiveProperty() {
    	Quantity l1 = new Quantity(1, LengthUnit.YARDS);
    	Quantity l2 = new Quantity(3, LengthUnit.FEET);
    	Quantity l3 = new Quantity(36, LengthUnit.INCHES);
    	Assertions.assertTrue(l1.equals(l2));
    	Assertions.assertTrue(l2.equals(l3));
    	Assertions.assertTrue(l1.equals(l3));
    }
    
    @Test
    public void testEquality_YardWithNullUnit() {
    	Quantity l1 = new Quantity(1, LengthUnit.YARDS);
    	Assertions.assertFalse(l1.equals(null));
    }
    
    @Test
    public void testEquality_YardSameReference() {
    	Quantity l1 = new Quantity(1, LengthUnit.YARDS);
    	Assertions.assertTrue(l1.equals(l1));
    }
    
    @Test
    public void testEquality_YardNullComparison() {
    	Quantity l1 = new Quantity(1, LengthUnit.YARDS);
    	Assertions.assertFalse(l1.equals(null));
    }
    
    @Test
    public void testEquality_CentimetersWithNullUnit() {
    	Quantity l1 = new Quantity(1, LengthUnit.CENTIMETERS);
    	Assertions.assertFalse(l1.equals(null));
    }
    
    @Test
    public void testEquality_CentimetersSameReference() {
    	Quantity l1 = new Quantity(1, LengthUnit.CENTIMETERS);
    	Assertions.assertTrue(l1.equals(l1));
    }
    
    @Test
    public void testEquality_CentimetersNullComparison() {
    	Quantity l1 = new Quantity(1, LengthUnit.CENTIMETERS);
    	Assertions.assertFalse(l1.equals(null));
    }
    
    @Test
    public void testEquality_AllUnit_ComplexScenario() {
    	Quantity len1 = new Quantity(2,LengthUnit.YARDS);
    	Quantity len2 = new Quantity(6,LengthUnit.FEET);
    	Quantity len3 = new Quantity(72,LengthUnit.INCHES);
    	Assertions.assertTrue(len1.equals(len2) && len1.equals(len3));
    }
    
    @Test 
    public void testConversion_FeetToInches() {
    	Quantity l1 = new Quantity(1, LengthUnit.FEET);
    	Assertions.assertEquals(12.0, l1.convertTo(LengthUnit.INCHES).getValue());
    }
    
    @Test 
    public void testConversion_InchesToFeet() {
    	Quantity l = new Quantity(24, LengthUnit.INCHES);
    	Assertions.assertEquals(2.0, l.convertTo(LengthUnit.FEET).getValue());
    }
    
    @Test
    public void testConversion_YardsToInches() {
    	Quantity l = new Quantity(1, LengthUnit.YARDS);
    	Assertions.assertEquals(36.0, l.convertTo(LengthUnit.INCHES).getValue());
    }
    
    @Test
    public void testConversion_InchesToYards() {
    	Quantity l = new Quantity(72, LengthUnit.INCHES);
    	Assertions.assertEquals(2.0, l.convertTo(LengthUnit.YARDS).getValue());
    }
    
    @Test 
    public void testConversion_CentimetersToInches() {
    	Quantity l = new Quantity(2.54, LengthUnit.CENTIMETERS);
    	Assertions.assertEquals(1.0, l.convertTo(LengthUnit.INCHES).getValue(), 0.0001);
    }
    
    @Test
    public void testConversion_FeetToYard() {
    	Quantity l = new Quantity(6,LengthUnit.FEET);
    	Assertions.assertEquals(2.0, l.convertTo(LengthUnit.YARDS).getValue());
    }
    
    @Test
    public void testConversion_RoundTrip_PreservesValue() {
    	 Assertions.assertEquals(12,QuantityMeasurementApp
    			 .demonstrateConversion(QuantityMeasurementApp.demonstrateConversion(new Quantity(12.0,LengthUnit.INCHES), LengthUnit.FEET), LengthUnit.INCHES).getValue());            
    }
    
    @Test 
    public void testConversion_ZeroValue() {
    	Quantity l1 = new Quantity(0, LengthUnit.FEET);
    	Assertions.assertEquals(0, l1.convertTo(LengthUnit.YARDS).getValue());
    }
    
    @Test
    public void testConversion_NegativeValue() {
    	Assertions.assertEquals(-12, QuantityMeasurementApp
    			.demonstrateConversion(new Quantity(-1.0, LengthUnit.FEET), LengthUnit.INCHES).getValue());
    }
    
    
    @Test
    public void testConversion_NaNOrInfinite_Throws() {
    	Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->{
    		QuantityMeasurementApp.demonstrateConversion(new Quantity(Double.NaN,LengthUnit.CENTIMETERS),LengthUnit.INCHES);
    	});
    }
    
    @Test 
    public void testConversion_PrecisionTolerance() {
    	Assertions.assertEquals(1.0,QuantityMeasurementApp
    			.demonstrateConversion(new Quantity(2.54, LengthUnit.CENTIMETERS),LengthUnit.INCHES)
    			.getValue(), 1e-6);
    }
    
    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {
    	Quantity l1 = new Quantity(1.0, LengthUnit.FEET);
    	Quantity l2 = new Quantity(2.0, LengthUnit.FEET);
    	
    	Assertions.assertEquals(3.0, QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue());
    }
    
    @Test
    public void testAddition_SameUnit_InchPlusInch() {
    	Quantity l1 = new Quantity(6.0, LengthUnit.INCHES);
    	Quantity l2 = new Quantity(6.0, LengthUnit.INCHES);
    	
    	Assertions.assertEquals(12.0, QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue());
    }
    
    @Test 
    public void testAddition_CrossUnit_FeetPlusInches() {
    	Quantity l1 = new Quantity(1.0, LengthUnit.FEET);
    	Quantity l2 = new Quantity(12.0, LengthUnit.INCHES);
    	
    	Assertions.assertEquals(2.0, QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue());
    }
    
    @Test
    public void testAddition_CrossUnit_InchPlusFeet() {
    	Quantity l1 = new Quantity(12.0, LengthUnit.INCHES);
    	Quantity l2 = new Quantity(1.0, LengthUnit.FEET);
    	
    	Assertions.assertEquals(24.0, QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue());
    }
    
    @Test 
    public void testAddition_CrossUnit_YardPlusFeet() {
    	Quantity l1 = new Quantity(1.0, LengthUnit.YARDS);
    	Quantity l2 = new Quantity(3.0, LengthUnit.FEET);
    	
    	Assertions.assertEquals(2, QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue());
    }
    
    @Test
    public void testAddition_CrossUnit_CentimeterPlusInch() {
    	Quantity l1 = new Quantity(2.54, LengthUnit.CENTIMETERS);
    	Quantity l2 = new Quantity(1.0, LengthUnit.INCHES);
    	
    	Assertions.assertEquals(5.08, QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue(),0.001);
    }
    
    @Test
    public void testAddition_Commutativity() {
    	Quantity l1 = new Quantity(1.0, LengthUnit.FEET);
    	Quantity l2 = new Quantity(12.0, LengthUnit.INCHES);
    	
    	Assertions.assertEquals(QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue(), QuantityMeasurementApp.demonstrateAddition(l2, l1).convertTo(LengthUnit.FEET).getValue());
    }
    
    @Test
    public void testAddition_WithZero() {
    	Quantity l1 = new Quantity(5, LengthUnit.FEET);
    	Quantity l2 = new Quantity(0, LengthUnit.FEET);
    	
    	Assertions.assertEquals(5.0, QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue());
    }
    
    @Test
    public void testAddition_NegativeValues() {
    	Quantity l1 = new Quantity(5, LengthUnit.FEET);
    	Quantity l2 = new Quantity(-2, LengthUnit.FEET);
    	
    	Assertions.assertEquals(3.0, QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue());
    	
    }
    
    @Test
    public void testAddition_NullSecondOperand() {
    	Quantity l1 = new Quantity(1, LengthUnit.FEET);
    	Assertions.assertThrows(IllegalArgumentException.class,()-> { QuantityMeasurementApp.demonstrateAddition(l1, null);});
    }
    
    @Test
    public void testAddition_LargeValues() {
    	Quantity l1 = new Quantity(1e6, LengthUnit.FEET);
    	Quantity l2 = new Quantity(1e6, LengthUnit.FEET);
    	
    	Assertions.assertEquals(2e6, QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue());
    }
    
    @Test
    public void testAddition_SmallValues() {
    	Quantity l1 = new Quantity(0.001, LengthUnit.FEET);
    	Quantity l2 = new Quantity(0.002, LengthUnit.FEET);
    	
    	Assertions.assertEquals(0.003, QuantityMeasurementApp.demonstrateAddition(l1, l2).getValue(), 0.001);
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_Feet() {
    	Quantity l1 = new Quantity(1, LengthUnit.FEET);
    	Quantity l2 = new Quantity(12, LengthUnit.INCHES);
    	
    	Assertions.assertEquals(2.0, QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.FEET).getValue());
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_Inches() {
    	Quantity l1 = new Quantity(1, LengthUnit.FEET);
    	Quantity l2 = new Quantity(12, LengthUnit.INCHES);
    	Assertions.assertEquals(24.0, QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.INCHES).getValue());
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_Yards() {
    	Quantity l1 = new Quantity(1, LengthUnit.FEET);
    	Quantity l2 = new Quantity(12, LengthUnit.INCHES);
    	Assertions.assertEquals(0.667, QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.YARDS).getValue(),0.001);
    }
    
    @Test 
    public void testAddition_ExplicitTargetUnit_Centimeters() {
    	Quantity l1 = new Quantity(1, LengthUnit.INCHES);
    	Quantity l2 = new Quantity(1, LengthUnit.INCHES);
    	Assertions.assertEquals(5.08, QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.CENTIMETERS).getValue(),0.001);
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
    	Quantity l1 = new Quantity(1, LengthUnit.YARDS);
    	Quantity l2 = new Quantity(3, LengthUnit.FEET);
    	Assertions.assertEquals(2.0, QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.YARDS).getValue());
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
    	Quantity l1 = new Quantity(2, LengthUnit.YARDS);
    	Quantity l2 = new Quantity(3, LengthUnit.FEET);
    	Assertions.assertEquals(9.0, QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.FEET).getValue()); 
    }
    
    @Test 
    public void testAddition_ExplicitTargetUnit_Commutativity() {
    	Quantity l1 = new Quantity(1, LengthUnit.FEET);
    	Quantity l2 = new Quantity(12, LengthUnit.INCHES);
    	Assertions.assertEquals(QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.YARDS).getValue(), QuantityMeasurementApp.demonstrateAddition(l2, l1, LengthUnit.YARDS).getValue()); 
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_WithZero() {
    	Quantity l1 = new Quantity(5, LengthUnit.FEET);
    	Quantity l2 = new Quantity(0, LengthUnit.INCHES);
    	Assertions.assertEquals(1.667, QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.YARDS).getValue(),0.001); 
    }
    
    @Test 
    public void testAddition_ExplicitTargetUnit_NegativeValues() {
    	Quantity l1 = new Quantity(5, LengthUnit.FEET);
    	Quantity l2 = new Quantity(-2, LengthUnit.FEET);
    	
    	Assertions.assertEquals(36.0, QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.INCHES).getValue());
    }
    
    @Test 
    public void testAddition_ExplicitTargetUnit_NullTargetUnit() {
    	Quantity l1 = new Quantity(5, LengthUnit.FEET);
    	Quantity l2 = new Quantity(-2, LengthUnit.FEET);
    	
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {QuantityMeasurementApp.demonstrateAddition(l1, l2, null); });
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
    	Quantity l1 = new Quantity(1000.0, LengthUnit.FEET);
    	Quantity l2 = new Quantity(500.0, LengthUnit.FEET);
    	Assertions.assertEquals(18000.0, QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.INCHES).getValue());
    }
    
    @Test 
    public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
    	Quantity l1 = new Quantity(12, LengthUnit.INCHES);
    	Quantity l2 = new Quantity(12, LengthUnit.INCHES);
    	
    	Assertions.assertEquals(0.667, QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.YARDS).getValue(), 0.001);
    }
    
    @Test
    public void testLengthUnitEnum_FeetConstant() {
    	Assertions.assertEquals(1.0, LengthUnit.FEET.getConversionFactor());
    }
    
    @Test
    public void testLengthUnitEnum_InchesConstant() {
    	Assertions.assertEquals(0.0833, LengthUnit.INCHES.getConversionFactor(),0.0001);
    }
    
    @Test
    public void testLengthUnitEnum_YardsConstant() {
    	Assertions.assertEquals(3.0, LengthUnit.YARDS.getConversionFactor());
    }
    
    @Test
    public void testLengthUnitEnum_CentimetersConstant() {
    	Assertions.assertEquals(0.0328, LengthUnit.CENTIMETERS.getConversionFactor(), 0.0001);
    }
    
    @Test
    public void testConvertToBaseUnit_FeetToFeet() {
    	Assertions.assertEquals(5.0, LengthUnit.FEET.convertToBaseUnit(5));
    }
    
    @Test
    public void testConvertToBaseUnit_InchesToFeet() {
    	Assertions.assertEquals(1.0, LengthUnit.INCHES.convertToBaseUnit(12));
    }
    
    @Test
    public void testConvertToBaseUnit_YardsToFeet() {
    	Assertions.assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1));
    }
    
    @Test
    public void testConvertToBaseUnit_CentimetersToFeet() {
    	Assertions.assertEquals(1.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), 0.1);
    }
    
    @Test
    public void testConvertFromBaseUnit_FeetToFeet() {
    	Assertions.assertEquals(1.0, LengthUnit.FEET.convertFromBaseUnit(1.0));
    }
    
    @Test
    public void testConvertFromBaseUnit_FeetToInches() {
    	Assertions.assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(1));
    }
    
    @Test
    public void testConvertFromBaseUnit_FeetToYards() {
    	Assertions.assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(3));
    }
    
    @Test
    public void testConvertFromBaseUnit_FeetToCentimeters() {
    	Assertions.assertEquals(30.48, LengthUnit.CENTIMETERS.convertFromBaseUnit(1), 0.01);
    }
    
    @Test
    public void testQuantityLengthRefactored_Equality() {
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity(1, LengthUnit.FEET),new Quantity(12, LengthUnit.INCHES)));
    }
    
    @Test
    public void testQuantityLengthRefactored_ConvertTo() {
    	Assertions.assertEquals(12.0,QuantityMeasurementApp.demonstrateConversion(new Quantity(1.0, LengthUnit.FEET),LengthUnit.INCHES).getValue(),0.1);
    }
    
    @Test
    public void testQuantityLengthRefactored_Add() {
    	Assertions.assertEquals(2.0,QuantityMeasurementApp.demonstrateAddition(new Quantity(1, LengthUnit.FEET),new Quantity(12, LengthUnit.INCHES), LengthUnit.FEET).getValue());
    }
    
    @Test
    public void testQuantityLengthRefactored_AddWithTargetUnit() {
    	Assertions.assertEquals(0.667,QuantityMeasurementApp.demonstrateAddition(new Quantity(1, LengthUnit.FEET), new Quantity(12, LengthUnit.INCHES), LengthUnit.YARDS).getValue(),0.001);
    }
    
    @Test
    public void testQuantityLengthRefactored_NullUnit() {
    	Assertions.assertThrows(IllegalArgumentException.class, ()-> { new Quantity(1, null); });
    }
    
    @Test 
    public void testQuantityLengthRefactored_InvalidValue() {
    	Assertions.assertThrows(IllegalArgumentException.class, ()->{new Quantity(Double.NaN, LengthUnit.FEET); });
    }
    
    @Test
    public void testEquality_KilogramToKilogram_SameValue() {
    	Quantity w1 = new Quantity(1, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(1, WeightUnit.KILOGRAM);
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(w1, w2));
    }
   
    @Test
    public void testEquality_KilogramToKilogram_DifferentValue() {
    	Quantity w1 = new Quantity(1, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(2, WeightUnit.KILOGRAM);
    	Assertions.assertFalse(QuantityMeasurementApp.demonstrateEquality(w1, w2));
    }
    
    @Test
    public void testEquality_KilogramToGram_EquivalentValue() {
    	Quantity w1 = new Quantity(1, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(1000, WeightUnit.GRAM);
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(w1, w2));
    }
    
    @Test
    public void testEquality_GramToKilogram_EquivalentValue() {
    	Quantity w1 = new Quantity(1000, WeightUnit.GRAM);
    	Quantity w2 = new Quantity(1, WeightUnit.KILOGRAM);
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(w1, w2));
    }
    
    @Test
    public void testEquality_NullComparison() {
    	Quantity w1 = new Quantity(1, WeightUnit.KILOGRAM);
    	Assertions.assertFalse(QuantityMeasurementApp.demonstrateEquality(w1, null));
    }
    @Test
    public void testEquality_SameReference() {
    	Quantity w = new Quantity(1, WeightUnit.KILOGRAM);
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(w, w));
    }
    @Test
    public void testEquality_NullUnit() {
    	Assertions.assertThrows(IllegalArgumentException.class, ()->{
    		Quantity w= new Quantity(1, null);
    	});
    }
    @Test
    public void testEquality_TransitiveProperty() {
    	Quantity w1= new Quantity(1, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(1000, WeightUnit.GRAM);
    	
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(w1, w2));
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(w2, w1));
    }
    
    @Test
    public void testEquality_ZeroValue() {
    	Quantity w1 = new Quantity(0, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(0, WeightUnit.GRAM);
    	
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(w1, w2));
    }
    
    @Test
    public void testEquality_NegativeWeight() {
    	Quantity w1 = new Quantity(-1, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(-1000, WeightUnit.GRAM);
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(w1, w2));
    }
    @Test
    public void testEquality_LargeWeightValue() {
    	Quantity w1 = new Quantity(1000000, WeightUnit.GRAM);
    	Quantity w2 = new Quantity(1000, WeightUnit.KILOGRAM);
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(w1, w2));
    }
    
    @Test
    public void testEquality_SmallWeightValue() {
    	Quantity w1 = new Quantity(0.001, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(1, WeightUnit.GRAM);
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(w1, w2));
    }
    
    @Test
    public void testConversion_PoundToKilogram() {
    	Quantity w1 = new Quantity(2.20462, WeightUnit.POUND);
    	Assertions.assertEquals(1.0, QuantityMeasurementApp.demonstrateConversion(w1, WeightUnit.KILOGRAM).getValue(), 0.1);
    }
    @Test 
    public void testConversion_KilogramToPound() {
    	Quantity w = new Quantity(1, WeightUnit.KILOGRAM);
    	Assertions.assertEquals(2.20462, QuantityMeasurementApp.demonstrateConversion(w, WeightUnit.POUND).getValue(), 0.0001);
    }
    
    @Test
    public void testConversion_SameUnit() {
    	Quantity w1 = new Quantity(5, WeightUnit.KILOGRAM);
    	Assertions.assertEquals(5.0, QuantityMeasurementApp.demonstrateConversion(w1, WeightUnit.KILOGRAM).getValue());
    }
    @Test
    public void testConversion_ZeroValue1() {
    	Quantity w = new Quantity(0, WeightUnit.KILOGRAM);
    	Assertions.assertEquals(0, QuantityMeasurementApp.demonstrateConversion(w, WeightUnit.GRAM).getValue());
    }
    
    @Test
    public void testConversion_NegativeValue1() {
    	Quantity w = new Quantity(-1, WeightUnit.KILOGRAM);
    	Assertions.assertEquals(-1000.0, QuantityMeasurementApp.demonstrateConversion(w, WeightUnit.GRAM).getValue());
    }
    @Test
    public void testConversion_RoundTrip() {
    	Quantity w = new Quantity(1.5, WeightUnit.KILOGRAM);
    	Assertions.assertEquals(1.5, QuantityMeasurementApp.demonstrateConversion(QuantityMeasurementApp.demonstrateConversion(w, WeightUnit.GRAM),WeightUnit.KILOGRAM).getValue());
    }
    @Test
    public void testAddition_SameUnit_KilogramPlusKilogram() {
    	Quantity w1 = new Quantity(1, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(2, WeightUnit.KILOGRAM);
    	Assertions.assertEquals(3.0, QuantityMeasurementApp.demonstrateAddition(w1, w2).getValue());
    }
    @Test
    public void testAddition_CrossUnit_KilogramPlusGram() {
    	Quantity w1 = new Quantity(1, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(1000, WeightUnit.GRAM);
    	Assertions.assertEquals(2.0, QuantityMeasurementApp.demonstrateAddition(w1, w2).getValue());
    }
    @Test
    public void testAddition_CrossUnit_PoundPlusKilogram() {
    	Quantity w1 = new Quantity(2.20462, WeightUnit.POUND);
    	Quantity w2 = new Quantity(1, WeightUnit.KILOGRAM);
    	Assertions.assertEquals(4.40924, QuantityMeasurementApp.demonstrateAddition(w1, w2).getValue(), 0.00001);
    }
    @Test
    public void testAddition_ExplicitTargetUnit_Kilogram() {
    	Quantity w1 = new Quantity(1, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(1000, WeightUnit.GRAM);
    	Assertions.assertEquals(2000.0, QuantityMeasurementApp.demonstrateAddition(w1, w2, WeightUnit.GRAM).getValue());
    }
    @Test
    public void testAddition_Commutativity1() {
    	Quantity w1 = new Quantity(1, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(1000, WeightUnit.GRAM);
    	Assertions.assertTrue(QuantityMeasurementApp.demonstrateAddition(w1, w2).equals(QuantityMeasurementApp.demonstrateAddition(w2, w1)));
    }
    
    @Test
    public void testAddition_WithZero1() {
    	Quantity w1 = new Quantity(5, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(0, WeightUnit.KILOGRAM);
    	Assertions.assertEquals(5.0, QuantityMeasurementApp.demonstrateAddition(w1, w2).getValue());
    }	
    @Test
    public void testAddition_NegativeValues1() {
    	Quantity w1 = new Quantity(5, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(-2000, WeightUnit.GRAM);
    	Assertions.assertEquals(3.0, QuantityMeasurementApp.demonstrateAddition(w1, w2).getValue());
    }
    @Test
    public void testAddition_LargeValue1s() {
    	Quantity w1 = new Quantity(1e6, WeightUnit.KILOGRAM);
    	Quantity w2 = new Quantity(1e6, WeightUnit.KILOGRAM);
    	Assertions.assertEquals(2e6, QuantityMeasurementApp.demonstrateAddition(w1, w2).getValue());
    }
    @Test
	public void testIMeasurableInterface_LengthUnitImplementation() {
		Quantity<LengthUnit> len1 = new Quantity(12.0, LengthUnit.INCHES);
		Quantity<LengthUnit> len2 = new Quantity(1.0, LengthUnit.FEET);
		
		Assertions.assertTrue(len1.equals(len2));
		Assertions.assertEquals(1.0, len2.convertToBaseUnit());
		Assertions.assertEquals(1.0, len1.convertToBaseUnit());
		Assertions.assertEquals(0.084,len1.getUnit().getConversionFactor(),0.001);
		Assertions.assertEquals(12.0,len1.getUnit().convertFromBaseUnit(1.0));
		Assertions.assertEquals(1.0,len1.getUnit().convertToBaseUnit(len1.getValue()));
	}
	
	@Test
	public void testIMeasurableInterface_WeightUnitImplementation() {
		Quantity<WeightUnit> weight1 = new Quantity(1,WeightUnit.KILOGRAM);
		Quantity<WeightUnit> weight2 = new Quantity(1000, WeightUnit.GRAM);
		
		Assertions.assertTrue(weight1.equals(weight2));
		Assertions.assertEquals(1, weight2.convertToBaseUnit());
		Assertions.assertEquals(1000, weight2.getUnit().convertFromBaseUnit(1));
		Assertions.assertEquals(1, weight1.getUnit().convertToBaseUnit(1));
		Assertions.assertEquals(1, weight1.getUnit().getConversionFactor());
	}
	@Test
	public void testEquality_LitreToLitre_SameValue() {
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity(1, VolumeUnit.LITRE), new Quantity(1, VolumeUnit.LITRE)));
	}
	@Test
	public void testEquality_LitreToLitre_DifferentValue() {
		Assertions.assertFalse(QuantityMeasurementApp.demonstrateEquality(new Quantity(1, VolumeUnit.LITRE), new Quantity(2, VolumeUnit.LITRE)));
	}
	@Test
	public void testEquality_LitreToMillilitre_EquivalentValue() {
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity(1,VolumeUnit.LITRE), new Quantity(1000, VolumeUnit.MILLILETRE)));
	}
	@Test
	public void testEquality_MillilitreToLitre_EquivalentValue() {
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity(1000, VolumeUnit.MILLILETRE), new Quantity(1, VolumeUnit.LITRE)));
	}
	@Test
	public void testEquality_LitreToGallon_EquivalentValue() {
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity(1, VolumeUnit.LITRE), new Quantity(0.264172, VolumeUnit.GALLON)));
	}
	@Test
	public void testEquality_GallonToLitre_EquivalentValue() {
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity(1, VolumeUnit.GALLON), new Quantity(3.78541, VolumeUnit.LITRE)));
	}
	@Test
	public void testEquality_VolumeVsLength_Incompatible() {
		Assertions.assertFalse(QuantityMeasurementApp.demonstrateEquality(new Quantity(1,VolumeUnit.LITRE), new Quantity(1, LengthUnit.FEET)));
	}
	@Test
	public void testEquality_VolumeVsWeight_Incompatible() {
		Assertions.assertFalse(QuantityMeasurementApp.demonstrateEquality(new Quantity(1,VolumeUnit.LITRE), new Quantity(1,WeightUnit.KILOGRAM)));
	}
	@Test 
	public void testVolumeEquality_NullComparison() {
		Assertions.assertFalse(QuantityMeasurementApp.demonstrateEquality(new Quantity(1,VolumeUnit.LITRE), null));
	}
	@Test
	public void testVolumeEquality_SameReference() {
		Quantity vol = new Quantity(1,VolumeUnit.LITRE);
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(vol, vol));
	}
	@Test
	public void testVolumeEquality_NullUnit() {
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			Quantity vol = new Quantity(1, null);
		});
	}
	@Test
	public void testVolumeEquality_TransitiveProperty() {
		Quantity litre = new Quantity(1, VolumeUnit.LITRE);
		Quantity millilitre = new Quantity(1000, VolumeUnit.MILLILETRE);
		Quantity litre1 = new Quantity(1, VolumeUnit.LITRE);
		
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(litre, millilitre));
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(millilitre, litre1));
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(litre, litre1));
	}
	@Test
	public void testVolumeEquality_ZeroValue() {
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity(0, VolumeUnit.LITRE), new Quantity(0, VolumeUnit.MILLILETRE) ));
	}
	@Test
	public void testEquality_NegativeVolume() {
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity(-1, VolumeUnit.LITRE), new Quantity(-1000, VolumeUnit.MILLILETRE)));
	}
	@Test
	public void testEquality_LargeVolumeValue() {
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity(1000000, VolumeUnit.MILLILETRE), new Quantity(1000, VolumeUnit.LITRE)));
	}
	@Test
	public void testEquality_SmallVolumeValue() {
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity(0.001, VolumeUnit.LITRE), new Quantity(1, VolumeUnit.MILLILETRE)));
	}
	@Test
	public void testConversion_LitreToMillilitre() {
		Assertions.assertEquals(1000, QuantityMeasurementApp.demonstrateConversion(new Quantity(1, VolumeUnit.LITRE), VolumeUnit.MILLILETRE).getValue());
	}
	@Test
	public void testConversion_MillilitreToLitre() {
		Assertions.assertEquals(1.0, QuantityMeasurementApp.demonstrateConversion(new Quantity(1000, VolumeUnit.MILLILETRE), VolumeUnit.LITRE).getValue(), 0.1);
	}
	@Test
	public void testConversion_GallonToLitre() {
		Assertions.assertEquals(3.78541, QuantityMeasurementApp.demonstrateConversion(new Quantity(1,VolumeUnit.GALLON), VolumeUnit.LITRE).getValue(), 0.00001);
	}
	@Test
	public void testConversion_LitreToGallon() {
		Assertions.assertEquals(1.0, QuantityMeasurementApp.demonstrateConversion(new Quantity(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON).getValue(),0.1);
	}
	@Test
	public void testConversion_MillilitreToGallon() {
		Assertions.assertEquals(0.264172, QuantityMeasurementApp.demonstrateConversion(new Quantity(1000.0, VolumeUnit.MILLILETRE), VolumeUnit.GALLON).getValue(),0.000001);
	}
	@Test
	public void testVolumeConversion_SameUnit() {
		Assertions.assertEquals(5, QuantityMeasurementApp.demonstrateConversion(new Quantity(5, VolumeUnit.LITRE), VolumeUnit.LITRE).getValue());
	}
	@Test
	public void testVolumeConversion_ZeroValue() {
		Assertions.assertEquals(0, QuantityMeasurementApp.demonstrateConversion(new Quantity(0, VolumeUnit.LITRE), VolumeUnit.MILLILETRE).getValue());
	}
	@Test
	public void testVolumeConversion_NegativeValue() {
		Assertions.assertEquals(-1000, QuantityMeasurementApp.demonstrateConversion(new Quantity(-1, VolumeUnit.LITRE), VolumeUnit.MILLILETRE).getValue());
	}
	@Test
	public void testVolumeConversion_RoundTrip() {
		Assertions.assertEquals(1.5, QuantityMeasurementApp.demonstrateConversion(new Quantity(1.5, VolumeUnit.LITRE), VolumeUnit.LITRE).getValue());
	}
	@Test
	public void testAddition_SameUnit_LitrePlusLitre() {
		Assertions.assertEquals(3, QuantityMeasurementApp.demonstrateAddition(new Quantity(1, VolumeUnit.LITRE), new Quantity(2, VolumeUnit.LITRE)).getValue());
	}
	@Test
	public void testAddition_SameUnit_MillilitrePlusMillilitre() {
		Assertions.assertEquals(1000,QuantityMeasurementApp.demonstrateAddition(new Quantity(500, VolumeUnit.MILLILETRE), new Quantity(500, VolumeUnit.MILLILETRE)).getValue());
	}
	@Test
	public void testAddition_CrossUnit_LitrePlusMillilitre() {
		Assertions.assertEquals(2, QuantityMeasurementApp.demonstrateAddition(new Quantity(1, VolumeUnit.LITRE), new Quantity(1000, VolumeUnit.MILLILETRE)).getValue());
	}
	@Test
	public void testAddition_CrossUnit_MillilitrePlusLitre() {
		Assertions.assertEquals(2000.0, QuantityMeasurementApp.demonstrateAddition(new Quantity(1000, VolumeUnit.MILLILETRE), new Quantity(1, VolumeUnit.LITRE)).getValue());
	}
	@Test
	public void testAddition_CrossUnit_GallonPlusLitre() {
		
		Assertions.assertEquals(2.0,QuantityMeasurementApp.demonstrateAddition(new Quantity(1, VolumeUnit.GALLON),new Quantity(3.78541, VolumeUnit.LITRE)).getValue());
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Litre() {
		Assertions.assertEquals(2, QuantityMeasurementApp.demonstrateAddition(new Quantity(1, VolumeUnit.LITRE), new Quantity(1000, VolumeUnit.MILLILETRE), VolumeUnit.LITRE).getValue());
	}
	@Test
	public void testAddition_ExplicitTargetUnit_Millilitre() {
		Assertions.assertEquals(2000, QuantityMeasurementApp.demonstrateAddition(new Quantity(1, VolumeUnit.LITRE), new Quantity(1000, VolumeUnit.MILLILETRE), VolumeUnit.MILLILETRE).getValue());
	}
	@Test
	public void testAddition_ExplicitTargetUnit_Gallon() {
		Assertions.assertEquals(2.0, QuantityMeasurementApp.demonstrateAddition(new Quantity(3.78541, VolumeUnit.LITRE), new Quantity(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON).getValue(), 0.1);
	}
	@Test
	public void testVolumeAddition_Commutativity() {
		Quantity quan1 = new Quantity(1, VolumeUnit.LITRE);
		Quantity quan2 = new Quantity(1000, VolumeUnit.MILLILETRE);
		
		Assertions.assertTrue(QuantityMeasurementApp.demonstrateEquality(QuantityMeasurementApp.demonstrateAddition(quan1, quan2), QuantityMeasurementApp.demonstrateAddition(quan2, quan1)));
		
	}
	@Test
	public void testVolumeUnitEnum_LitreConstant() {
		Assertions.assertEquals(1, VolumeUnit.LITRE.getConversionFactor());
	}
	@Test
	public void testVolumeUnitEnum_MillilitreConstant() {
		Assertions.assertEquals(0.001, VolumeUnit.MILLILETRE.getConversionFactor());
	}
	@Test
	public void testVolumeUnitEnum_GallonConstant() {
		Assertions.assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor());
	}
	@Test
	public void testConvertToBaseUnit_LitreToLitre() {
		Assertions.assertEquals(5.0, VolumeUnit.LITRE.convertToBaseUnit(5));
	}
	
	@Test
	public void testConvertToBaseUnit_MillilitreToLitre() {
		Assertions.assertEquals(1, VolumeUnit.MILLILETRE.convertToBaseUnit(1000));
	}
	@Test
	public void testConvertToBaseUnit_GallonToLitre() {
		Assertions.assertEquals(3.78541, VolumeUnit.GALLON.convertToBaseUnit(1), 0.00001);
	}
	@Test
	public void testConvertFromBaseUnit_LitreToLitre() {
		Assertions.assertEquals(2.0, VolumeUnit.LITRE.convertFromBaseUnit(2));
	}
	@Test
	public void testConvertFromBaseUnit_LitreToMillilitre() {
		Assertions.assertEquals(1000, VolumeUnit.MILLILETRE.convertFromBaseUnit(1));
	}
	@Test
	public void testConvertFromBaseUnit_LitreToGallon() {
		Assertions.assertEquals(1, VolumeUnit.GALLON.convertFromBaseUnit(3.78541), 0.1);
	}
	@Test
	public void testSubtraction_SameUnit_FeetMinusFeet() {
		Assertions.assertEquals(5.0 ,QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10, LengthUnit.FEET), new Quantity(5, LengthUnit.FEET)).getValue());
	}
	@Test
	public void testSubtraction_SameUnit_LitreMinusLitre() {
		Assertions.assertEquals(7.0, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10, VolumeUnit.LITRE), new Quantity(3, VolumeUnit.LITRE)).getValue());
	}
	@Test
	public void testSubtraction_CrossUnit_FeetMinusInches() {
		Assertions.assertEquals(9.5, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10, LengthUnit.FEET), new Quantity(6, LengthUnit.INCHES)).getValue());
	}
	@Test
	public void testSubtraction_CrossUnit_InchesMinusFeet() {
		Assertions.assertEquals(60.0, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(120, LengthUnit.INCHES), new Quantity(5, LengthUnit.FEET)).getValue());
	}
	@Test
	public void testSubtraction_ExplicitTargetUnit_Feet() {
		Assertions.assertEquals(9.5, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10, LengthUnit.FEET), new Quantity(6, LengthUnit.INCHES), LengthUnit.FEET).getValue());
	}
	@Test
	public void testSubtraction_ExplicitTargetUnit_Inches() {
		Assertions.assertEquals(114.0, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10, LengthUnit.FEET), new Quantity(6, LengthUnit.INCHES), LengthUnit.INCHES).getValue());
	}
	@Test
	public void testSubtraction_ExplicitTargetUnit_Millilitre() {
		Assertions.assertEquals(3000, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(5, VolumeUnit.LITRE), new Quantity(2, VolumeUnit.LITRE),VolumeUnit.MILLILETRE).getValue());
	}
	@Test
	public void testSubtraction_ResultingInNegative() {
		Assertions.assertEquals(-5.0, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(5, LengthUnit.FEET), new Quantity(10, LengthUnit.FEET)).getValue());
	}
	@Test
	public void testSubtraction_ResultingInZero() {
		Assertions.assertEquals(0.0, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10, LengthUnit.FEET), new Quantity(120, LengthUnit.INCHES)).getValue());
	}
	@Test
	public void testSubtraction_WithZeroOperand() {
		Assertions.assertEquals(5.0, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(5, LengthUnit.FEET), new Quantity(0, LengthUnit.FEET)).getValue());
	}
	@Test
	public void testSubtraction_WithNegativeValues() {
		Assertions.assertEquals(7.0, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(5, LengthUnit.FEET), new Quantity(-2, LengthUnit.FEET)).getValue());
	}
	@Test
	public void testSubtraction_NonCommutative() {
		Assertions.assertFalse(QuantityMeasurementApp.demonstrateEquality(QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10,LengthUnit.FEET), new Quantity(5, LengthUnit.FEET)),QuantityMeasurementApp.demonstrateSubtraction(new Quantity(5, LengthUnit.FEET), new Quantity(10, LengthUnit.FEET))));
	}
	@Test
	public void testSubtraction_WithLargeValues() {
		Assertions.assertEquals(5e5, QuantityMeasurementApp.demonstrateSubtraction(new Quantity(1e6, WeightUnit.KILOGRAM), new Quantity(5e5, WeightUnit.KILOGRAM)).getValue());
	}
	@Test
	public void testSubtraction_NullOperand() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> {
			QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10, LengthUnit.FEET), null);
		});
	}
	@Test
	public void testSubtraction_NullTargetUnit() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> {
			QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10, LengthUnit.FEET), new Quantity(5, LengthUnit.FEET), null);
		});
	}
	@Test
	public void testSubtraction_CrossCategory() {
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10, LengthUnit.FEET), new Quantity(5, WeightUnit.KILOGRAM));
		});
	}
	@Test
	public void testSubtraction_ChainedOperations() {
		Assertions.assertEquals(7.0, QuantityMeasurementApp.demonstrateSubtraction(QuantityMeasurementApp.demonstrateSubtraction(new Quantity(10, LengthUnit.FEET), new Quantity(1, LengthUnit.FEET)), new Quantity(2, LengthUnit.FEET)).getValue());
	}
	@Test
	public void testDivision_SameUnit_FeetDividedByFeet() {
		Assertions.assertEquals(5.0, QuantityMeasurementApp.demonstrateDivision(new Quantity(10, LengthUnit.FEET), new Quantity(2, LengthUnit.FEET)));
	}
	@Test
	public void testDivision_SameUnit_LitreDividedByLitre() {
		Assertions.assertEquals(2.0, QuantityMeasurementApp.demonstrateDivision(new Quantity(10, VolumeUnit.LITRE), new Quantity(5, VolumeUnit.LITRE)));
	}
	@Test
	public void testDivision_CrossUnit_FeetDividedByInches() {
		Assertions.assertEquals(1.0, QuantityMeasurementApp.demonstrateDivision(new Quantity(24, LengthUnit.INCHES), new Quantity(2, LengthUnit.FEET)));
	}
	@Test
	public void testDivision_CrossUnit_KilogramDividedByGram() {
		Assertions.assertEquals(1, QuantityMeasurementApp.demonstrateDivision(new Quantity(2, WeightUnit.KILOGRAM), new Quantity(2000, WeightUnit.GRAM)));
	}
	@Test
	public void testDivision_RatioGreaterThanOne() {
		Assertions.assertEquals(5.0, QuantityMeasurementApp.demonstrateDivision(new Quantity(10, LengthUnit.FEET), new Quantity(2, LengthUnit.FEET)));
	}
	@Test
	public void testDivision_RatioLessThanOne() {
		Assertions.assertEquals(0.5, QuantityMeasurementApp.demonstrateDivision(new Quantity(5, LengthUnit.FEET), new Quantity(10, LengthUnit.FEET)));
	}
	@Test
	public void testDivision_RatioEqualToOne() {
		Assertions.assertEquals(1.0, QuantityMeasurementApp.demonstrateDivision(new Quantity(10, LengthUnit.FEET), new Quantity(10, LengthUnit.FEET)));
	}
	@Test
	public void testDivision_NonCommutative() {
		Assertions.assertNotEquals(QuantityMeasurementApp.demonstrateDivision(new Quantity(10, LengthUnit.FEET), new Quantity(5, LengthUnit.FEET)), QuantityMeasurementApp.demonstrateDivision(new Quantity(5, LengthUnit.FEET), new Quantity(10, LengthUnit.FEET)));
	}
	@Test
	public void testDivision_ByZero() {
		Assertions.assertThrows(ArithmeticException.class, ()->{
			QuantityMeasurementApp.demonstrateDivision(new Quantity(10, LengthUnit.FEET), new Quantity(0, LengthUnit.FEET));
		});
	}
	@Test
	public void testDivision_WithLargeRatio() {
		Assertions.assertEquals(1e6, QuantityMeasurementApp.demonstrateDivision(new Quantity(1e6, WeightUnit.KILOGRAM), new Quantity(1, WeightUnit.KILOGRAM)));
	}
	@Test
	public void testDivision_WithSmallRatio() {
		Assertions.assertEquals(1e-6, QuantityMeasurementApp.demonstrateDivision(new Quantity(1, WeightUnit.KILOGRAM), new Quantity(1e6, WeightUnit.KILOGRAM)));
	}
	@Test
	public void testDivision_NullOperand() {
		Assertions.assertThrows(IllegalArgumentException.class,()-> {
			QuantityMeasurementApp.demonstrateDivision(new Quantity(10, LengthUnit.FEET), null);
		});
	}
	@Test 
	public void testDivision_CrossCategory() {
		Assertions.assertThrows(IllegalArgumentException.class,()->{ QuantityMeasurementApp.demonstrateDivision(new Quantity(10, LengthUnit.FEET), new Quantity(5, WeightUnit.KILOGRAM));});
	}
	
	@Test
    public void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        Quantity<TemperatureUnit> c1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> c2 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Assertions.assertTrue(c1.equals(c2));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_0C_32F() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        Assertions.assertTrue(celsius.equals(fahrenheit));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_100C_212F() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        Assertions.assertTrue(celsius.equals(fahrenheit));
    }

    @Test
    public void testTemperatureEquality_Negative40_Intersection() {
     
        Quantity<TemperatureUnit> c = new Quantity<>(-40.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> f = new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT);
        Assertions.assertTrue(c.equals(f));
    }

    @Test
    public void testTemperatureConversion_CelsiusToFahrenheit() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        double result = celsius.convertTo(TemperatureUnit.FAHRENHEIT).getValue();
        Assertions.assertEquals(122.0, result, 0.001);
    }

    @Test
    public void testTemperatureConversion_FahrenheitToCelsius() {
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        double result = fahrenheit.convertTo(TemperatureUnit.CELSIUS).getValue();
        Assertions.assertEquals(100.0, result, 0.001);
    }

    @Test
    public void testTemperatureAddition_ShouldThrowException() {
        Quantity<TemperatureUnit> c1 = new Quantity<>(10.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> c2 = new Quantity<>(20.0, TemperatureUnit.CELSIUS);
        
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            c1.add(c2);
        });
    }

    @Test
    public void testTemperatureSubtraction_ShouldThrowException() {
        Quantity<TemperatureUnit> c1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> c2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            c1.subtract(c2);
        });
    }

    @Test
    public void testTemperatureDivision_ShouldThrowException() {
        Quantity<TemperatureUnit> c1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> c2 = new Quantity<>(2.0, TemperatureUnit.CELSIUS);
        
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            c1.divide(c2);
        });
    }

    @Test
    public void testLegacyArithmetic_LengthAddition_StillWorks() {
        Quantity<LengthUnit> l1 = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(1, LengthUnit.FEET);
        
        Assertions.assertEquals(2.0, l1.add(l2).getValue());
    }

    @Test
    public void testCrossCategoryEquality_TemperatureVsLength() {
        Quantity<TemperatureUnit> temp = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<LengthUnit> length = new Quantity<>(100.0, LengthUnit.FEET);
     
        Assertions.assertFalse(temp.equals(length));
    }
}
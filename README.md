#  Quantity Measurement Application

A scalable, object-oriented Java application designed to perform 
**measurement comparison, conversion, and arithmetic operations** 
across multiple unit categories such as Length and Weight.

---

##  Project Objective

This project demonstrates how to build a clean, extensible, and type-safe 
measurement system using strong OOP principles and architectural best practices.

The system supports:

-  Equality comparison across compatible units  
-  Unit-to-unit conversion with base unit normalization  
-  Arithmetic operations (addition) across mixed units  
-  Multi-category support (Length, Weight)  
-  Generic architecture using Java Generics and Interfaces  

---

##  Architectural Evolution

The project evolves incrementally across multiple use cases (UC1–UC10), 
starting from basic equality checks and progressing toward a fully 
generic, reusable `Quantity<U>` design.

Key architectural milestones include:

- Encapsulation & Immutability
- DRY (Don't Repeat Yourself) Refactoring
- Enum-based Unit Abstraction
- Separation of Concerns
- Single Responsibility Principle
- Open-Closed Principle
- Interface-driven Generic Design
- Multi-Category Type Safety

---

##  Core Technical Concepts Demonstrated

- Value Objects in Java  
- Overriding `equals()` 
- Floating-point precision handling (`Double.compare`, epsilon tolerance)  
- Enum as behavior carrier  
- Generic programming with bounded type parameters  
- Interface-based abstraction (`IMeasurable`)  
- Compile-time and runtime type safety  
- Immutable domain modeling  

---

## Supported Measurement Categories

### Length Units
- Feet  
- Inches  
- Yards  
- Centimeters  

###  Weight Units
- Kilogram  
- Gram  
- Pound  

---

## Testing Strategy

The application includes comprehensive test using JUnit. 


## Branch-Based Learning Structure

Each branch in this repository represents a focused learning milestone 
progressively improving design, architecture, and scalability.


# UC1 – Feet Measurement Equality

**Date:** 2026-02-20 

## Overview
Implemented value-based equality comparison for `Feet` measurements.

## What Was Added
- `Feet` inner class with immutable `double value`
- Proper `equals()` override using `Double.compare()`
- Null and type safety checks
- Basic test coverage for equality contract

## Principles Followed
- Object-Oriented Design
- Encapsulation
- Immutability
- Unit Testing Best Practices


# UC2 – Feet and Inches Measurement Equality

**Date:** 2026-02-20 

## Overview
Extended equality logic to support `Inches` alongside `Feet`.

## What Was Added
- Separate `Inches` class
- Equality logic for both units
- Static helper methods to reduce `main()` dependency
- Test cases for both classes

## Principles Followed
- Equality Contract
- Type Safety
- DRY awareness (identified violation)
- Clean Test Design


# UC3 – Generic Quantity Class (DRY Refactor)

**Date:** 2026-02-22 

## Overview
Refactored duplicate classes into a single `QuantityLength` class.

## What Was Added
- `LengthUnit` enum
- Base-unit normalization (feet)
- Cross-unit equality and conversion
- Removal of duplicated logic

## Principles Followed
- DRY (Don't Repeat Yourself)
- Single Responsibility Principle
- Encapsulation
- Backward Compatibility


# UC4 – Extended Unit Support

**Date:** 2026-02-22

## Overview
Extended system to support Yards and Centimeters.

## What Was Added
- `YARDS` and `CENTIMETERS` in `LengthUnit`
- New conversion factors
- Multi-unit equality validation
- Additional cross-unit test cases

## Principles Followed
- Open-Closed Principle
- DRY Validation
- Enum Extensibility
- Backward Compatibility


# UC5 – Explicit Unit-to-Unit Conversion

**Date:** 2026-02-23  

## Overview
Introduced explicit conversion API for length units.

## What Was Added
- `convert(value, sourceUnit, targetUnit)` method
- Validation for NaN, Infinity, null units
- Precision handling with epsilon
- Overloaded demonstration methods

## Principles Followed
- API Design Best Practices
- Immutability
- Encapsulation
- Defensive Programming

# UC6 – Addition of Two Length Units

**Date:** 2026-02-23  

## Overview
Implemented arithmetic addition between length measurements.

## What Was Added
- `add()` method for same and cross-unit addition
- Base-unit normalization before addition
- Result returned in first operand’s unit
- Edge-case validations (zero, negative, large values)

## Principles Followed
- Immutability
- Reusability of conversion logic
- Mathematical correctness
- DRY compliance

# UC7 – Addition with Explicit Target Unit

**Date:** 2026-02-23  

## Overview
Enhanced addition logic to support explicit target unit specification.

## What Was Added
- Overloaded `add()` method with target unit
- Private utility method for base-unit arithmetic
- Consistent rounding logic
- Comprehensive unit combination tests

## Principles Followed
- DRY Principle
- API Consistency
- Immutability


# UC8 – Standalone LengthUnit Refactor

**Date:** 2026-02-23  

## Overview
Extracted `LengthUnit` into standalone enum with conversion responsibility.

## What Was Added
- Top-level `LengthUnit` enum
- `convertToBaseUnit()` method
- `convertFromBaseUnit()` method
- Delegation of conversion logic

## What I Learned
- Single Responsibility Principle
- Delegation pattern
- Cohesion vs coupling
- Architectural scalability preparation

## Principles Followed
- SRP
- Separation of Concerns
- Encapsulation
- Backward Compatibility


# UC9 – Weight Category Integration

**Date:** 2026-02-23  

## Overview
Introduced new measurement category: Weight.

## What Was Added
- `WeightUnit` enum (KG, GRAM, POUND)
- `QuantityWeight` class
- Equality, conversion, and addition for weight
- Category type-safety enforcement

## Principles Followed
- Scalability Pattern
- SRP across categories
- Immutability
- Type Safety Enforcement


# UC10 – Generic Quantity<U> with IMeasurable

**Date:** 2026-02-23  

## Overview
Refactored entire system into a single generic `Quantity<U>` class.

## What Was Added
- `IMeasurable` interface
- Generic `Quantity<U extends IMeasurable>` class
- Refactored LengthUnit and WeightUnit to implement interface
- Simplified QuantityMeasurementApp

## What I Learned
- Generic Programming in Java
- Interface-driven architecture
- Type erasure considerations
- Open-Closed Principle in action

## Principles Followed
- DRY (Logic implemented once)
- SRP
- OCP (Open for extension, closed for modification)
- Polymorphism and Delegation


# UC11 – Volume Category Integration

**Date:** 2026-02-24  

## Overview  
Introduced a third measurement category — **Volume** — to validate the scalability of the generic `Quantity<U>` architecture implemented in UC10.

Only a new `VolumeUnit` enum was required. No changes were made to `Quantity<U>`, proving the architecture is truly extensible.

## What Was Added  
- `VolumeUnit` enum (LITRE, MILLILITRE, GALLON)  
- Base unit: LITRE  
- Equality, conversion, and addition support via generic logic  
- Comprehensive unit tests for volume operations 

## Principles Followed  
- OCP (Open for extension, closed for modification)  
- DRY (Logic reused from generic implementation)  
- Type-Safe Generics  
- Separation of Concerns  

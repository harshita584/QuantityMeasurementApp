# 📏 Quantity Measurement Application

A scalable, object-oriented Java application designed to perform 
**measurement comparison, conversion, and arithmetic operations** 
across multiple unit categories such as Length and Weight.

---

## 🎯 Project Objective

This project demonstrates how to build a clean, extensible, and type-safe 
measurement system using strong OOP principles and architectural best practices.

The system supports:

- ✅ Equality comparison across compatible units  
- 🔄 Unit-to-unit conversion with base unit normalization  
- ➕ Arithmetic operations (addition) across mixed units  
- 🧱 Multi-category support (Length, Weight)  
- 🧩 Generic architecture using Java Generics and Interfaces  

---

## 🏗️ Architectural Evolution

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

## 🛠️ Core Technical Concepts Demonstrated

- Value Objects in Java  
- Overriding `equals()` 
- Floating-point precision handling (`Double.compare`, epsilon tolerance)  
- Enum as behavior carrier  
- Generic programming with bounded type parameters  
- Interface-based abstraction (`IMeasurable`)  
- Compile-time and runtime type safety  
- Immutable domain modeling  

---

## 📦 Supported Measurement Categories

### 📐 Length Units
- Feet  
- Inches  
- Yards  
- Centimeters  

### ⚖️ Weight Units
- Kilogram  
- Gram  
- Pound  

---

## 🧪 Testing Strategy

The application includes comprehensive test using JUnit. 


## 📚 Branch-Based Learning Structure

Each branch in this repository represents a focused learning milestone 
progressively improving design, architecture, and scalability.


# UC1 – Feet Measurement Equality

**Date:** 2026-02-20 

## 🚀 Overview
Implemented value-based equality comparison for `Feet` measurements.

## ✅ What Was Added
- `Feet` inner class with immutable `double value`
- Proper `equals()` override using `Double.compare()`
- Null and type safety checks
- Basic test coverage for equality contract

## 🧠 Principles Followed
- Object-Oriented Design
- Encapsulation
- Immutability
- Unit Testing Best Practices


# UC2 – Feet and Inches Measurement Equality

**Date:** 2026-02-20 

## 🚀 Overview
Extended equality logic to support `Inches` alongside `Feet`.

## ✅ What Was Added
- Separate `Inches` class
- Equality logic for both units
- Static helper methods to reduce `main()` dependency
- Test cases for both classes

## 🧠 Principles Followed
- Equality Contract
- Type Safety
- DRY awareness (identified violation)
- Clean Test Design

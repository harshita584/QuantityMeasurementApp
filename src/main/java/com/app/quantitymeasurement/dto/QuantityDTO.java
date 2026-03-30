package com.app.quantitymeasurement.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Logger;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "A quantity with a value and unit")
public class QuantityDTO {
    private static final Logger logger = Logger.getLogger(QuantityDTO.class.getName());

//    Interface representing measurable unit.
    interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

//    Length Units
    public enum LengthUnit implements IMeasurableUnit {
        FEET("FEET"),
        INCHES("INCHES"),
        YARDS("YARDS"),
        CENTIMETERS("CENTIMETERS");

        private final String unit;

        LengthUnit(String unit) {
            this.unit = unit;
        }

        @Override
        public String getUnitName() {
            return unit;
        }

        @Override
        public String getMeasurementType() {
            return "LengthUnit";
        }
    }

//    Volume Units
    public enum VolumeUnit implements IMeasurableUnit {
        LITRE("LITRE"),
        MILLILITER("MILLILITER"),
        GALLON("GALLON");

        private final String unit;

        VolumeUnit(String unit) {
            this.unit = unit;
        }

        @Override
        public String getUnitName() {
            return unit;
        }

        @Override
        public String getMeasurementType() {
            return "VolumeUnit";
        }
    }

//    Weight Units
    public enum WeightUnit implements IMeasurableUnit {
        MILLIGRAM("MILLIGRAM"),
        GRAM("GRAM"),
        KILOGRAM("KILOGRAM"),
        POUND("POUND"),
        TONNE("TONNE");

        private final String unit;

        WeightUnit(String unit) {
            this.unit = unit;
        }

        @Override
        public String getUnitName() {
            return unit;
        }

        @Override
        public String getMeasurementType() {
            return "WeightUnit";
        }
    }

//    Temperature Units
    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS("CELSIUS"),
        FAHRENHEIT("FAHRENHEIT");

        private final String unit;

        TemperatureUnit(String unit) {
            this.unit = unit;
        }

        @Override
        public String getUnitName() {
            return unit;
        }

        @Override
        public String getMeasurementType() {
            return "TemperatureUnit";
        }
    }

//    Value of the quantity
    @NotNull(message = "Value cannot be empty")
    @Schema(example = "1.0")
    public double value;

//    Unit of the quantity
    @NotNull(message = "Unit cannot be null")
    @Schema(example = "FEET", allowableValues = {
                    "FEET", "INCHES", "YARDS", "CENTIMETERS",
                    "LITRE", "MILLILITER", "GALLON",
                    "MILLIGRAM", "GRAM", "KILOGRAM", "POUND", "TONNE",
                    "CELSIUS", "FAHRENHEIT"
            })
    public String unit;

//    Measurement type
    @NotNull(message = "Measurement type cannot be null")
    @Pattern(regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit", message = "Measurement type must be one of: LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit")
    @Schema(example = "LengthUnit",  allowableValues = {
                    "LengthUnit", "VolumeUnit", "WeightUnit", "TemperatureUnit"
            })
    public String measurementType;

    // Constructor using enum
    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value = value;
        this.unit = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }


//    Custom validation to check if unit matches measurement type
    @AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isValidUnit() {
        logger.info("Validating unit: " + unit + " for measurement type: " + measurementType);
        try {
            switch (measurementType) {
                case "LengthUnit":
                    LengthUnit.valueOf(unit);
                    break;

                case "VolumeUnit":
                    VolumeUnit.valueOf(unit);
                    break;

                case "WeightUnit":
                    WeightUnit.valueOf(unit);
                    break;

                case "TemperatureUnit":
                    TemperatureUnit.valueOf(unit);
                    break;

                default: return false;
            }

        } 
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
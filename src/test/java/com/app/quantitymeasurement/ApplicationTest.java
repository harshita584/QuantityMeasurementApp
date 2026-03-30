package com.app.quantitymeasurement;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    
    public ApplicationTest() {
    	this.objectMapper = new ObjectMapper(); 
    }

    private String baseUrl = "/api/v1/quantities";

    private QuantityInputDTO input(
            double thisValue, String thisUnit, String thisMeasurementType,
            double thatValue, String thatUnit, String thatMeasurementType
    ) {
        QuantityInputDTO inputDTO = new QuantityInputDTO();
        inputDTO.setThisQuantityDTO(
                new QuantityDTO(thisValue, thisUnit, thisMeasurementType)
        );
        inputDTO.setThatQuantityDTO(
                new QuantityDTO(thatValue, thatUnit, thatMeasurementType)
        );
        return inputDTO;
    }

    private QuantityInputDTO inputWithTarget(
            double thisValue, String thisUnit, String thisMeasurementType,
            double thatValue, String thatUnit, String thatMeasurementType,
            double targetValue, String targetUnit, String targetMeasurementType
    ) {
        QuantityInputDTO dto = input(thisValue, thisUnit, thisMeasurementType,
                thatValue, thatUnit, thatMeasurementType);
        dto.setTargetQuantityDTO(
                new QuantityDTO(targetValue, targetUnit, targetMeasurementType)
        );
        return dto;
    }

    @Test
    @Order(1)
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @Order(2)
    void testCompare_FootEqualsInches() throws Exception {
        mockMvc.perform(post(baseUrl + "/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                input(1.0,"FEET","LengthUnit",12.0,"INCHES","LengthUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("true"));
    }

    @Test
    @Order(3)
    void testCompare_FootNotEqualInch() throws Exception {
        mockMvc.perform(post(baseUrl + "/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                input(1.0,"FEET","LengthUnit",1.0,"INCHES","LengthUnit"))))
        		.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("false"));
    }

    @Test
    @Order(4)
    void testCompare_GallonEqualsLitres() throws Exception {
        mockMvc.perform(post(baseUrl + "/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                input(1.0,"GALLON","VolumeUnit",3.785,"LITRE","VolumeUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("true"));
    }

    @Test
    @Order(5)
    void testCompare_FahrenheitEqualsCelsius() throws Exception {
        mockMvc.perform(post(baseUrl + "/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                input(212.0,"FAHRENHEIT","TemperatureUnit",100.0,"CELSIUS","TemperatureUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("true"));
    }

    @Test
    @Order(6)
    void testConvert_CelsiusToFahrenheit() throws Exception {
        mockMvc.perform(post(baseUrl + "/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                input(100.0,"CELSIUS","TemperatureUnit",0.0,"FAHRENHEIT","TemperatureUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(212.0));
    }

    @Test
    @Order(7)
    void testAdd_GallonAndLitres() throws Exception {
        mockMvc.perform(post(baseUrl + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                input(1.0,"GALLON","VolumeUnit",3.785,"LITRE","VolumeUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(2.0));
    }

    @Test
    @Order(8)
    void testAddWithTargetUnit() throws Exception {
        mockMvc.perform(post(baseUrl + "/add-with-target-unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                inputWithTarget(1.0,"FEET","LengthUnit",12.0,"INCHES","LengthUnit",
                                        0.0,"INCHES","LengthUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(24.0));
    }

    @Test
    @Order(9)
    void testSubtract_FeetMinusInches() throws Exception {
        mockMvc.perform(post(baseUrl + "/subtract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                input(2.0,"FEET","LengthUnit",12.0,"INCHES","LengthUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(1.0));
    }

    @Test
    @Order(10)
    void testSubtractWithTargetUnit() throws Exception {
        mockMvc.perform(post(baseUrl + "/subtract-with-target-unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                inputWithTarget(2.0,"FEET","LengthUnit",12.0,"INCHES","LengthUnit",
                                        0.0,"INCHES","LengthUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(12.0));
    }

    @Test
    @Order(11)
    void testDivide_YardByFoot() throws Exception {
        mockMvc.perform(post(baseUrl + "/divide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                input(1.0,"YARDS","LengthUnit",1.0,"FEET","LengthUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(3.0));
    }

    @Test
    @Order(12)
    void testGetHistoryByOperation_Convert() throws Exception {
        mockMvc.perform(get(baseUrl + "/history/operation/CONVERT"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(13)
    void testGetHistoryByType_Temperature() throws Exception {
        mockMvc.perform(get(baseUrl + "/history/type/TemperatureUnit"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(14)
    void testGetOperationCount_Divide() throws Exception {
        mockMvc.perform(get(baseUrl + "/count/DIVIDE"))
                .andExpect(status().isOk());
    }


    @Test
    @Order(15)
    void testCompare_UnitValidationFails() throws Exception {
        mockMvc.perform(post(baseUrl + "/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                input(1.0,"FOOT","LengthUnit",12.0,"INCHES","LengthUnit"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Unit must be valid")));
    }

    @Test
    @Order(16)
    void testCompare_TypeValidationFails() throws Exception {
        mockMvc.perform(post(baseUrl + "/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                input(1.0,"FEET","InvalidType",12.0,"INCHES","LengthUnit"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Measurement type must be one of")));
    }
}
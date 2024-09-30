package org.mtech.csa.parking.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mtech.csa.parking.entity.VehicleType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.repository.VehicleRepository;
import org.mtech.csa.parking.repository.VehicleTypeRepository;
import org.mtech.csa.parking.service.VehicleService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class VehicleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleTypeRepository vehicleTypeRepository;

    @InjectMocks
    private VehicleController vehicleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
    }

    // Test case for creating a new vehicle entry
    @Test
    void shouldCreateNewVehicleEntry() throws Exception {
        // Arrange
        String vehicleNumber = "ABC123";
        String vehicleType = "Car";
        Vehicle expectedVehicle = new Vehicle();
        expectedVehicle.setVehicleNumber(vehicleNumber);
        expectedVehicle.setVehicleType(new VehicleType()); // Assuming VehicleType has default constructor
        expectedVehicle.setEntryTime(LocalDateTime.now());

        when(vehicleService.createVehicleEntry(eq(vehicleNumber), eq(vehicleType))).thenReturn(expectedVehicle);

        // Act & Assert
        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("vehicleNumber", vehicleNumber)
                        .param("vehicleType", vehicleType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleNumber").value(equalTo(vehicleNumber)))
                .andExpect(jsonPath("$.entryTime").exists());

        verify(vehicleService).createVehicleEntry(eq(vehicleNumber), eq(vehicleType));
    }

    // Test case for processing vehicle exit
    @Test
    void shouldProcessVehicleExit() throws Exception {
        // Arrange
        Long vehicleId = 1L;
        Vehicle expectedVehicle = new Vehicle();
        expectedVehicle.setVehicleId(vehicleId);
        expectedVehicle.setExitTime(LocalDateTime.now());

        when(vehicleService.processVehicleExit(eq(vehicleId))).thenReturn(expectedVehicle);

        // Act & Assert
        mockMvc.perform(post("/vehicles/v1/{vehicleId}/exit", vehicleId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(equalTo(vehicleId.intValue())))
                .andExpect(jsonPath("$.exitTime").exists());

        verify(vehicleService).processVehicleExit(eq(vehicleId));
    }

    // Test case for finding a vehicle entry by vehicle number
    @Test
    void shouldFindVehicleEntryByVehicleNumber() throws Exception {
        // Arrange
        String vehicleNumber = "XYZ789";
        Vehicle expectedVehicle = new Vehicle();
        expectedVehicle.setVehicleNumber(vehicleNumber);

        when(vehicleService.findVehicleByVehicleNumber(eq(vehicleNumber))).thenReturn(Optional.of(expectedVehicle));

        // Act & Assert
        mockMvc.perform(get("/vehicles/" + vehicleNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleNumber").value(equalTo(vehicleNumber)));

        verify(vehicleService).findVehicleByVehicleNumber(eq(vehicleNumber));
    }

    // Additional test cases for edge scenarios...
}

// Note: You would typically add more detailed tests for each scenario including handling exceptions, etc., but this gives you a starting point.
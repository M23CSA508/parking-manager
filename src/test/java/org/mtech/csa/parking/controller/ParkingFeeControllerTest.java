package org.mtech.csa.parking.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mtech.csa.parking.entity.ParkingFee;
import org.mtech.csa.parking.entity.ParkingSpot;
import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.service.ParkingFeeService;
import org.mtech.csa.parking.service.VehicleService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ParkingFeeControllerTest {

    @Mock
    private VehicleService vehicleService;

    @Mock
    private ParkingFeeService parkingFeeService;

    @InjectMocks
    private ParkingFeeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOkWhenValidVehicleIsProvided() {
        // Arrange
        String vehicleNumber = "ABC123";
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setEntryTime(LocalDateTime.of(2023, 1, 1, 8, 0));
        vehicle.setExitTime(LocalDateTime.of(2023, 1, 1, 9, 0));
        vehicle.setAssignedSpot(new ParkingSpot());
        vehicle.getAssignedSpot().setFee(new BigDecimal("10"));

        when(vehicleService.findVehicleByVehicleNumber(vehicleNumber)).thenReturn(Optional.of(vehicle));
        when(parkingFeeService.calculateFee(vehicle)).thenReturn(new ParkingFee());

        // Act
        ResponseEntity<ParkingFee> response = controller.parkingFee(vehicleNumber);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(vehicleService, times(1)).findVehicleByVehicleNumber(vehicleNumber);
        verify(parkingFeeService, times(1)).calculateFee(vehicle);
    }

    @Test
    void shouldReturnNotFoundWhenInvalidVehicleIsProvided() {
        // Arrange
        String vehicleNumber = "XYZ789";

        when(vehicleService.findVehicleByVehicleNumber(vehicleNumber)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ParkingFee> response = controller.parkingFee(vehicleNumber);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(vehicleService, times(1)).findVehicleByVehicleNumber(vehicleNumber);
        verifyNoInteractions(parkingFeeService);
    }
}
package org.mtech.csa.parking.controller;

import lombok.AllArgsConstructor;
import org.mtech.csa.parking.entity.ParkingSpot;
import org.mtech.csa.parking.service.ParkingSpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The ParkingSpotController class handles HTTP requests related to parking spots management.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/v1/spots")
public class ParkingSpotController {

    private ParkingSpotService parkingSpotService;

    /**
     * Retrieves all available parking spots from the database.
     *
     * @return A ResponseEntity containing a list of available {@link ParkingSpot} objects.
     */
    @GetMapping("/available")
    public ResponseEntity<List<ParkingSpot>> getAvailableSpots() {
        return ResponseEntity.ok().body(parkingSpotService.findAvailableSpots());
    }

    /**
     * Creates a new parking spot based on the provided details.
     *
     * @param parkingSpot An instance of the {@link ParkingSpot} entity representing the new spot to be created.
     * @return A ResponseEntity containing the newly created {@link ParkingSpot} object and HTTP status code CREATED.
     */
    @PostMapping
    public ResponseEntity<ParkingSpot> createParkingSpot(@RequestBody ParkingSpot parkingSpot) {
        parkingSpot.setIsOccupied(Boolean.FALSE);
        ParkingSpot createdParkingSpot = parkingSpotService.createParkingSpot(parkingSpot);
        return new ResponseEntity<>(createdParkingSpot, HttpStatus.CREATED);
    }
}
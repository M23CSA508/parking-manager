package org.mtech.csa.parking.controller;

import lombok.AllArgsConstructor;
import org.mtech.csa.parking.entity.ParkingSpot;
import org.mtech.csa.parking.service.ParkingSpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/spots")
public class ParkingSpotController {

    private ParkingSpotService parkingSpotService;

    @GetMapping("/available")
    public ResponseEntity<List<ParkingSpot>> getAvailableSpots() {
        return ResponseEntity.ok().body(parkingSpotService.findAvailableSpots());
    }

    @PostMapping
    public ResponseEntity<ParkingSpot> createParkingSpot(@RequestBody ParkingSpot parkingSpot) {
        ParkingSpot createdParkingSpot = parkingSpotService.createParkingSpot(parkingSpot);
        return new ResponseEntity<>(createdParkingSpot, HttpStatus.CREATED);
    }
}

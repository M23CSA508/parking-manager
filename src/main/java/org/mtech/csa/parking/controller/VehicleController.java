package org.mtech.csa.parking.controller;

import lombok.AllArgsConstructor;
import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Vehicle> createVehicleEntry(@RequestParam String vehicleNumber, @RequestParam String vehicleType) {
        Vehicle vehicle = vehicleService.createVehicleEntry(vehicleNumber, vehicleType);
        if (vehicle == Vehicle.INVALID_VEHICLE_TYPE) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping("/v1/{vehicleId}/exit")
    public ResponseEntity<Vehicle> processVehicleExit(@PathVariable Long vehicleId) {
        Vehicle vehicle;
        try {
            vehicle = vehicleService.processVehicleExit(vehicleId);
        }catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/{vehicleNumber}")
    public ResponseEntity<Vehicle> findVehicleEntry(@PathVariable String vehicleNumber) {
        Optional<Vehicle> vehicleOptional = vehicleService.findVehicleByVehicleNumber(vehicleNumber);
        return vehicleOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}


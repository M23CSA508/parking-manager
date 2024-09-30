package org.mtech.csa.parking.controller;

import lombok.AllArgsConstructor;
import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The VehicleController class handles HTTP requests related to vehicles and delegates them to the VehicleService.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/v1/vehicles")
public class VehicleController {

    private VehicleService vehicleService;

    /**
     * Creates a new vehicle entry by processing the POST request sent to /vehicles endpoint.
     *
     * @param vehicleNumber the vehicle number of the vehicle being created
     * @param vehicleType   the type of the vehicle being created
     * @param parkingSpotId the parking spot identifier
     * @return a ResponseEntity containing either the newly created Vehicle object or an error response if invalid input was provided
     */
    @PostMapping(value = "/entry", params = {"vehicleNumber", "vehicleType", "parkingSpotId"})
    public ResponseEntity<Vehicle> createVehicleEntry(@RequestParam(value = "vehicleNumber") String vehicleNumber,
                                                      @RequestParam(value = "vehicleType") String vehicleType,
                                                      @RequestParam(value = "parkingSpotId") String parkingSpotId) {
        Vehicle vehicle = vehicleService.createVehicleEntry(vehicleNumber, vehicleType, parkingSpotId);
        if (vehicle == Vehicle.INVALID_VEHICLE) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(vehicle);
    }

    /**
     * Processes the vehicle exit event by sending a POST request to /vehicles/v1/{vehicleId}/exit endpoint.
     *
     * @param vehicleId the ID of the vehicle whose exit time needs to be processed
     * @return a ResponseEntity containing either the updated Vehicle object or an error response if no such vehicle exists
     */
    @PostMapping("/{vehicleId}/exit")
    public ResponseEntity<Vehicle> processVehicleExit(@PathVariable Long vehicleId) {
        Vehicle vehicle;
        try {
            vehicle = vehicleService.processVehicleExit(vehicleId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(vehicle);
    }

    /**
     * Retrieves a specific vehicle entry based on its vehicle number from the database.
     *
     * @param vehicleNumber the vehicle number used as a key to retrieve the corresponding Vehicle object
     * @return a ResponseEntity containing either the found Vehicle object wrapped inside an Optional container or a 'Not Found' response if no matching vehicle was found
     */
    @GetMapping("/{vehicleNumber}")
    public ResponseEntity<Vehicle> findVehicleEntry(@PathVariable String vehicleNumber) {
        Optional<Vehicle> vehicleOptional = vehicleService.findVehicleByVehicleNumber(vehicleNumber);
        return vehicleOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
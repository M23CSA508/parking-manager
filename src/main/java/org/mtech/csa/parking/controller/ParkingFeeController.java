package org.mtech.csa.parking.controller;

import lombok.AllArgsConstructor;
import org.mtech.csa.parking.entity.ParkingFee;
import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.repository.ParkingFeeRepository;
import org.mtech.csa.parking.service.ParkingFeeService;
import org.mtech.csa.parking.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/fee")
public class ParkingFeeController {

    private ParkingFeeService parkingFeeService;
    private VehicleService vehicleService;

    @GetMapping("/{vehicleNumber}")
    public ResponseEntity<ParkingFee> parkingFee(@PathVariable String vehicleNumber) {
        Optional<Vehicle> vehicleOptional = vehicleService.findVehicleByVehicleNumber(vehicleNumber);
        return vehicleOptional.map(vehicle -> ResponseEntity.ok().body(parkingFeeService.calculateFee(vehicle))).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

package org.mtech.csa.parking.controller;

import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public Vehicle createVehicleEntry(@RequestParam String vehicleNumber, @RequestParam String vehicleType) {
        return vehicleService.createVehicleEntry(vehicleNumber, vehicleType);
    }

    @PostMapping("/{vehicleId}/exit")
    public Vehicle processVehicleExit(@PathVariable Long vehicleId) {
        return vehicleService.processVehicleExit(vehicleId);
    }
}


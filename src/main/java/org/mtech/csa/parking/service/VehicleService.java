package org.mtech.csa.parking.service;

import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle createVehicleEntry(String vehicleNumber, String vehicleType) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setVehicleType(vehicleType);
        vehicle.setEntryTime(LocalDateTime.now());

        return vehicleRepository.save(vehicle);
    }

    public Vehicle processVehicleExit(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        vehicle.setExitTime(LocalDateTime.now());
        return vehicleRepository.save(vehicle);
    }
}

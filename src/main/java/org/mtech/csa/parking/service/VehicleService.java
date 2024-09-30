package org.mtech.csa.parking.service;

import lombok.AllArgsConstructor;
import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.entity.VehicleType;
import org.mtech.csa.parking.repository.VehicleRepository;
import org.mtech.csa.parking.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private VehicleTypeRepository vehicleTypeRepository;

    public Vehicle createVehicleEntry(String vehicleNumber, String type) {
        Vehicle vehicle = new Vehicle();

        Optional<VehicleType> vehicleTypeOptional = vehicleTypeRepository.findByTypeName(type);
        if (vehicleTypeOptional.isPresent()) {
            vehicle.setVehicleNumber(vehicleNumber);
            vehicle.setVehicleType(vehicleTypeOptional.get());
            vehicle.setEntryTime(LocalDateTime.now());
            return vehicleRepository.save(vehicle);
        }

        return Vehicle.INVALID_VEHICLE_TYPE;
    }

    public Vehicle processVehicleExit(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        vehicle.setExitTime(LocalDateTime.now());
        return vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> findVehicleByVehicleNumber(String vehicleNumber) {
        return vehicleRepository.findByVehicleNumber(vehicleNumber);
    }
}

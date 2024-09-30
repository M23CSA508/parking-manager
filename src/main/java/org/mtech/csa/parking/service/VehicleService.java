package org.mtech.csa.parking.service;

import lombok.AllArgsConstructor;
import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.entity.VehicleType;
import org.mtech.csa.parking.repository.VehicleRepository;
import org.mtech.csa.parking.repository.VehicleTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private VehicleTypeRepository vehicleTypeRepository;

    /**
     * Creates a new vehicle entry record by setting the entry time to the current date-time.
     *
     * @param vehicleNumber unique identifier for the vehicle
     * @param type          the type of the vehicle
     * @return the created Vehicle object or INVALID_VEHICLE_TYPE if the vehicle type does not exist
     */
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

    /**
     * Processes an existing vehicle's exit by updating its exit time to the current date-time.
     *
     * @param vehicleId ID of the vehicle whose exit needs to be processed
     * @return the updated Vehicle object
     */
    public Vehicle processVehicleExit(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        vehicle.setExitTime(LocalDateTime.now());
        return vehicleRepository.save(vehicle);
    }

    /**
     * Finds a vehicle by its number.
     *
     * @param vehicleNumber the number of the vehicle to search for
     * @return an Optional containing the found Vehicle object, or empty if no matching vehicle exists
     */
    public Optional<Vehicle> findVehicleByVehicleNumber(String vehicleNumber) {
        return vehicleRepository.findByVehicleNumber(vehicleNumber);
    }
}

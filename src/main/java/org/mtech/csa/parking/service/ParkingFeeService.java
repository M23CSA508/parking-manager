package org.mtech.csa.parking.service;

import lombok.AllArgsConstructor;
import org.mtech.csa.parking.entity.ParkingFee;
import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.repository.ParkingFeeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * Service layer implementation for calculating and saving parking fees based on vehicle entry and exit times.
 */
@Service
@AllArgsConstructor
public class ParkingFeeService {

    /**
     * Repository used to persist calculated parking fees into the database.
     */
    private ParkingFeeRepository parkingFeeRepository;

    /**
     * Calculates the total parking fee for a given vehicle considering its entry time, exit time, and spot fee.
     *
     * @param vehicle The vehicle whose parking fee needs to be calculated.
     * @return A newly created {@link ParkingFee} object representing the calculated fee.
     */
    public ParkingFee calculateFee(Vehicle vehicle) {
        ParkingFee fee = new ParkingFee();
        fee.setVehicle(vehicle);
        fee.setParkingSpot(vehicle.getAssignedSpot());

        // Example Fee Calculation (Hourly Rate)
        Duration duration = Duration.between(vehicle.getEntryTime(), vehicle.getExitTime());
        long hoursParked = Math.max(1, duration.toHours()); // Minimum 1 hour
        BigDecimal totalFee = vehicle.getAssignedSpot().getFee().multiply(BigDecimal.valueOf(hoursParked));

        fee.setTotalFee(totalFee);
        return parkingFeeRepository.save(fee);
    }
}
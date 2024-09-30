package org.mtech.csa.parking.service;

import lombok.AllArgsConstructor;
import org.mtech.csa.parking.entity.ParkingFee;
import org.mtech.csa.parking.entity.Vehicle;
import org.mtech.csa.parking.repository.ParkingFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@AllArgsConstructor
public class ParkingFeeService {

    private ParkingFeeRepository parkingFeeRepository;

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

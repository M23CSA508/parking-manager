package org.mtech.csa.parking.service;

import org.mtech.csa.parking.entity.ParkingSpot;
import org.mtech.csa.parking.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public List<ParkingSpot> findAvailableSpots() {
        return parkingSpotRepository.findByIsOccupiedFalse();
    }

    public ParkingSpot assignSpot(ParkingSpot spot) {
        spot.setIsOccupied(true);
        return parkingSpotRepository.save(spot);
    }

    public void freeSpot(ParkingSpot spot) {
        spot.setIsOccupied(false);
        parkingSpotRepository.save(spot);
    }
}

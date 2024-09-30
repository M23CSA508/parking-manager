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

    public ParkingSpot createParkingSpot(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    public ParkingSpot assignSpot(Long spotId) {
        return parkingSpotAction(spotId,true);
    }

    public void freeSpot(Long spotId) {
        parkingSpotAction(spotId,false);
    }

    private  ParkingSpot parkingSpotAction(Long spotId, boolean occupied) {
        ParkingSpot parkingSpot = parkingSpotRepository.findById(spotId).orElse(null);
        assert parkingSpot != null;
        parkingSpot.setIsOccupied(occupied);
        return parkingSpotRepository.save(parkingSpot);
    }
}

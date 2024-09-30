package org.mtech.csa.parking.service;

import lombok.AllArgsConstructor;
import org.mtech.csa.parking.entity.ParkingSpot;
import org.mtech.csa.parking.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer implementation for managing parking spots.
 */
@AllArgsConstructor
@Service
public class ParkingSpotService {

    private ParkingSpotRepository parkingSpotRepository;

    /**
     * Finds all available parking spots by checking the 'isOccupied' field.
     *
     * @return A list containing all unoccupied parking spots.
     */
    public List<ParkingSpot> findAvailableSpots() {
        return parkingSpotRepository.findByIsOccupiedFalse();
    }

    /**
     * Creates a new parking spot record.
     *
     * @param parkingSpot An instance of the ParkingSpot entity to be saved.
     * @return The newly created or updated ParkingSpot object.
     */
    public ParkingSpot createParkingSpot(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    /**
     * Assigns a parking spot to a vehicle.
     *
     * @param spotId The ID of the parking spot to be assigned.
     * @return The updated ParkingSpot object after assignment.
     */
    public ParkingSpot assignSpot(Long spotId) {
        return parkingSpotAction(spotId,true);
    }

    /**
     * Frees up a previously assigned parking spot.
     *
     * @param spotId The ID of the parking spot to be freed.
     */
    public void freeSpot(Long spotId) {
        parkingSpotAction(spotId,false);
    }

    /**
     * Helper method to set the 'isOccupied' status of a parking spot.
     *
     * @param spotId   The ID of the parking spot whose status needs to be changed.
     * @param occupied Whether the spot should be marked as occupied or not.
     * @return The updated ParkingSpot object after changing its occupancy status.
     */
    private  ParkingSpot parkingSpotAction(Long spotId, boolean occupied) {
        ParkingSpot parkingSpot = parkingSpotRepository.findById(spotId).orElse(null);
        assert parkingSpot != null;
        parkingSpot.setIsOccupied(occupied);
        return parkingSpotRepository.save(parkingSpot);
    }
}
package org.mtech.csa.parking.controller;

import org.mtech.csa.parking.entity.ParkingSpot;
import org.mtech.csa.parking.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spots")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @GetMapping("/available")
    public List<ParkingSpot> getAvailableSpots() {
        return parkingSpotService.findAvailableSpots();
    }
}

package org.mtech.csa.parking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spotId;

    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String level;
    @Column(name = "spot_number", nullable = false)
    private String spotNumber;
    @Column(nullable = false)
    private BigDecimal fee;
    @Column(name = "fee_unit", nullable = false)
    private String feeUnit;
    @Column(name = "is_occupied", nullable = false)
    private Boolean isOccupied = false;
}

package org.mtech.csa.parking.repository;

import org.mtech.csa.parking.entity.ParkingFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingFeeRepository extends JpaRepository<ParkingFee, Long> {
}


package org.mtech.csa.parking.repository;

import org.mtech.csa.parking.entity.ParkingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingFeeRepository extends JpaRepository<ParkingFee, Long> {
}


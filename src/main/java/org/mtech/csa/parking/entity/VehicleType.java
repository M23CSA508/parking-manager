package org.mtech.csa.parking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "VEHICLE_TYPE")
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "type_name", nullable = false)
    private String typeName;

    @Column(name = "description")
    private String description;
}


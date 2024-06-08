package com.example.VoyageTravel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busId;

    private String busName;
    private String driverName;
    private String busType;
    private String routeFrom;
    private String routeTo;
    private String departureDate;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private Integer seats;
    private Integer availableSeats;
    private Double fare;
}

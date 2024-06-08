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
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pId;

    private Long busId;
    private String busName;
    private String name;
    private String email;
    private String gender;
    private Integer age;
    private String mobile;
    private String selectedSeat;
    private String journeyDate;
    private String startLocation;;
    private String endLocation;;
    private Double totalFare;

}

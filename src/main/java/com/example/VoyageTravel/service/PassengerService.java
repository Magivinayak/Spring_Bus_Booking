package com.example.VoyageTravel.service;

import com.example.VoyageTravel.entity.PassengerEntity;
import com.example.VoyageTravel.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public PassengerEntity createPassenger(PassengerEntity passenger) {
        return passengerRepository.save(passenger);
    }

    public List<PassengerEntity> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public List<Integer> getSelectedSeatsByBusId(Long busId) {
        try {
            List<PassengerEntity> passengers = passengerRepository.findByBusId(busId);
            List<Integer> selectedSeats = new ArrayList<>();
            for (PassengerEntity passenger : passengers) {
                String passengerSeats = passenger.getSelectedSeat();
                if (passengerSeats != null && !passengerSeats.isEmpty()) {
                    // Remove non-integer characters
                    passengerSeats = passengerSeats.replaceAll("[^0-9]+", " ");
                    // Extract integers
                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(passengerSeats);
                    while (matcher.find()) {
                        selectedSeats.add(Integer.parseInt(matcher.group()));
                    }
                }
            }
            return selectedSeats;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch selected seats for bus with ID: " + busId, e);
        }
    }



}

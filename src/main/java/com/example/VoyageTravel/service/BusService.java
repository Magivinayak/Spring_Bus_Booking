package com.example.VoyageTravel.service;

import com.example.VoyageTravel.entity.BusEntity;
import com.example.VoyageTravel.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public BusEntity addBus(BusEntity busEntity) {
        return busRepository.save(busEntity);
    }

    public List<BusEntity> searchBuses(String from, String to ,String date) {
        return busRepository.searchBuses(from, to ,date);
    }

    public List<BusEntity> getAllBuses() {
        return busRepository.findAll();
    }

    public BusEntity getBusById(Long id) {
        Optional<BusEntity> optionalBus = busRepository.findById(id);
        if (optionalBus.isPresent()) {
            return optionalBus.get();
        } else {
            throw new RuntimeException("Bus not found with id: " + id);
        }
    }

    public void deleteBusById(Long id) {
        try {
            busRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete bus with id: " + id, e);
        }
    }

    public void updateBus(BusEntity busEntity) {
        Optional<BusEntity> optionalBus = busRepository.findById(busEntity.getBusId());
        if (optionalBus.isPresent()) {
            BusEntity existingBus = optionalBus.get();
            // Update properties
            existingBus.setBusName(busEntity.getBusName());
            existingBus.setDriverName(busEntity.getDriverName());
            existingBus.setBusType(busEntity.getBusType());
            existingBus.setRouteFrom(busEntity.getRouteFrom());
            existingBus.setRouteTo(busEntity.getRouteTo());
            existingBus.setDepartureTime(busEntity.getDepartureTime());
            existingBus.setArrivalTime(busEntity.getArrivalTime());
            existingBus.setDuration(busEntity.getDuration());
            existingBus.setSeats(busEntity.getSeats());
            existingBus.setAvailableSeats(busEntity.getAvailableSeats());
            existingBus.setFare(busEntity.getFare());
            // Save the updated entity
            try {
                busRepository.save(existingBus);
            } catch (Exception e) {
                throw new RuntimeException("Failed to update bus with id: " + busEntity.getBusId(), e);
            }
        } else {
            throw new RuntimeException("Bus not found with id: " + busEntity.getBusId());
        }
    }

    public void updateAvailableSeats(Long busId, int seatsBooked) {
        Optional<BusEntity> optionalBus = busRepository.findById(busId);
        if (optionalBus.isPresent()) {
            BusEntity bus = optionalBus.get();
            int availableSeats = bus.getAvailableSeats() - seatsBooked;
            if (availableSeats >= 0) {
                bus.setAvailableSeats(availableSeats);
                busRepository.save(bus);
            } else {
                throw new RuntimeException("Not enough available seats on the bus");
            }
        } else {
            throw new RuntimeException("Bus not found with id: " + busId);
        }
    }
}

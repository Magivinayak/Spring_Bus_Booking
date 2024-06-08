package com.example.VoyageTravel.repository;

import com.example.VoyageTravel.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity,Long> {

    @Query("SELECT p FROM PassengerEntity p WHERE p.busId = ?1")
    List<PassengerEntity> findByBusId(Long busId);
}

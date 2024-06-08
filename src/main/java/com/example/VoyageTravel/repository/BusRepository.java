package com.example.VoyageTravel.repository;

import com.example.VoyageTravel.entity.BusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<BusEntity,Long> {

    @Query("SELECT b FROM BusEntity b WHERE b.routeFrom = :from AND b.routeTo = :to AND b.departureDate = :date")
    List<BusEntity> searchBuses(String from, String to ,String date);

}

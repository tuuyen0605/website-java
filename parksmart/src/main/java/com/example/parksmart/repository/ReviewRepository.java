package com.example.parksmart.repository;

import com.example.parksmart.model.ParkingLot;
import com.example.parksmart.model.Review;
import com.example.parksmart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByParkingLotOrderByNgayTaoDesc(ParkingLot parkingLot);
    Optional<Review> findByUserAndParkingLot(User user, ParkingLot parkingLot);

    @Query("SELECT AVG(r.soSao) FROM Review r WHERE r.parkingLot = :parkingLot")
    Double avgSoSao(ParkingLot parkingLot);
}

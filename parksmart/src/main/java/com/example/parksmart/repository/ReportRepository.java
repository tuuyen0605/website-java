package com.example.parksmart.repository;

import com.example.parksmart.model.Report;
import com.example.parksmart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUserOrderByNgayTaoDesc(User user);
    List<Report> findAllByOrderByNgayTaoDesc();
}

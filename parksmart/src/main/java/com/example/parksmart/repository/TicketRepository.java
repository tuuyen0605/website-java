package com.example.parksmart.repository;

import com.example.parksmart.model.Ticket;
import com.example.parksmart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Lấy tất cả vé của một user
    List<Ticket> findByUser(User user);

    // Lấy vé đang hoạt động của user
    List<Ticket> findByUserAndTrangThai(User user, String trangThai);
}

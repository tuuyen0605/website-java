package com.example.parksmart.repository;

import com.example.parksmart.model.Notification;
import com.example.parksmart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Lấy thông báo chưa đọc của user, ngày nhắc <= hôm nay
    List<Notification> findByUserAndDaDocFalseAndNgayNhacLessThanEqual(User user, LocalDate today);

    // Lấy tất cả thông báo của user
    List<Notification> findByUser(User user);
}

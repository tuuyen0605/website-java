package com.example.parksmart.service;

import com.example.parksmart.model.Ticket;
import com.example.parksmart.model.User;
import com.example.parksmart.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Tạo và lưu vé mới vào database.
     */
    public Ticket dangKyVe(User user, String loaiXe, String loaiVe, String bienSo, Long gia) {
        LocalDate ngayDangKy = LocalDate.now();
        LocalDate ngayHetHan = switch (loaiVe) {
            case "Vé quý" -> ngayDangKy.plusDays(90);
            case "Vé năm" -> ngayDangKy.plusDays(365);
            default        -> ngayDangKy.plusDays(30); // Vé tháng
        };

        Ticket ticket = new Ticket(user, loaiXe, loaiVe, bienSo, gia, ngayDangKy, ngayHetHan);
        return ticketRepository.save(ticket);
    }

    /**
     * Lấy tất cả vé của user.
     */
    public List<Ticket> getVeCuaUser(User user) {
        return ticketRepository.findByUser(user);
    }

    /**
     * Lấy các vé đang hoạt động của user.
     */
    public List<Ticket> getVeHoatDong(User user) {
        return ticketRepository.findByUserAndTrangThai(user, "Hoạt động");
    }
}

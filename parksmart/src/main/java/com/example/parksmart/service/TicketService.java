package com.example.parksmart.service;

import com.example.parksmart.model.Ticket;
import com.example.parksmart.model.User;
import com.example.parksmart.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private NotificationService notificationService;

    /**
     * Tạo và lưu vé mới, đồng thời tạo 2 thông báo nhắc nhở.
     */
    public Ticket dangKyVe(User user, String loaiXe, String loaiVe,
                            String bienSo, String khuVuc, Long gia) {
        LocalDate ngayDangKy = LocalDate.now();
        LocalDate ngayHetHan = switch (loaiVe) {
            case "Vé quý" -> ngayDangKy.plusDays(90);
            case "Vé năm" -> ngayDangKy.plusDays(365);
            default        -> ngayDangKy.plusDays(30);
        };

        Ticket ticket = new Ticket(user, loaiXe, loaiVe, bienSo, khuVuc, gia, ngayDangKy, ngayHetHan);
        Ticket saved = ticketRepository.save(ticket);

        // Tạo 2 thông báo nhắc hạn
        notificationService.taoThongBaoNhacHan(user, saved);
        notificationService.taoThongBaoDangKyThanhCong(user, saved);
        return saved;
    }

    /**
     * Lấy tất cả vé của user dưới dạng ArrayList.
     */
    public ArrayList<Ticket> getVeCuaUser(User user) {
        return new ArrayList<>(ticketRepository.findByUser(user));
    }

    /**
     * Lấy vé đang hoạt động của user.
     */
    public ArrayList<Ticket> getVeHoatDong(User user) {
        return new ArrayList<>(ticketRepository.findByUserAndTrangThai(user, "Hoạt động"));
    }

    /**
     * Lấy tất cả vé (dành cho admin).
     */
    public ArrayList<Ticket> getTatCaVe() {
        return new ArrayList<>(ticketRepository.findAllByOrderByNgayDangKyDesc());
    }

    /**
     * Admin đổi trạng thái thanh toán của vé.
     * Xử lý ngoại lệ nếu vé không tồn tại.
     */
    public Ticket doiTrangThaiThanhToan(Long ticketId) throws Exception {
        Optional<Ticket> opt = ticketRepository.findById(ticketId);
        if (opt.isEmpty()) {
            throw new Exception("Không tìm thấy vé với ID: " + ticketId);
        }
        Ticket ticket = opt.get();
        ticket.setThanhToan(!ticket.isThanhToan()); // toggle
        return ticketRepository.save(ticket);
    }

    /**
     * Tự động cập nhật trạng thái vé hết hạn.
     */
    public void capNhatTrangThaiHetHan() {
        List<Ticket> tatCa = ticketRepository.findAll();
        LocalDate today = LocalDate.now();
        tatCa.forEach(ticket -> {
            if (ticket.getNgayHetHan().isBefore(today)
                    && ticket.getTrangThai().equals("Hoạt động")) {
                ticket.setTrangThai("Hết hạn");
                ticketRepository.save(ticket);
            }
        });
    }
}

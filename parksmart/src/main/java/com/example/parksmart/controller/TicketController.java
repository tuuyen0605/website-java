package com.example.parksmart.controller;

import com.example.parksmart.model.Notification;
import com.example.parksmart.model.Ticket;
import com.example.parksmart.model.User;
import com.example.parksmart.service.NotificationService;
import com.example.parksmart.service.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private NotificationService notificationService;

    // ===================== ĐĂNG KÝ VÉ =====================
    @PostMapping("/dang-ky-ve")
    public Map<String, Object> dangKyVe(@RequestBody Map<String, Object> request,
                                         HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "error");
            response.put("message", "Bạn chưa đăng nhập. Vui lòng đăng nhập lại.");
            return response;
        }

        String loaiXe = (String) request.get("loaiXe");
        String loaiVe = (String) request.get("loaiVe");
        String bienSo = (String) request.get("bienSo");
        String khuVuc = (String) request.get("khuVuc");
        Long gia = ((Number) request.get("gia")).longValue();

        if (loaiXe == null || loaiXe.isBlank()) {
            response.put("status", "error");
            response.put("message", "Vui lòng chọn loại xe!");
            return response;
        }
        if (loaiVe == null || loaiVe.isBlank()) {
            response.put("status", "error");
            response.put("message", "Vui lòng chọn gói vé!");
            return response;
        }
        if (khuVuc == null || khuVuc.isBlank()) {
            response.put("status", "error");
            response.put("message", "Vui lòng chọn khu vực bãi đỗ!");
            return response;
        }

        Ticket ticket = ticketService.dangKyVe(user, loaiXe, loaiVe, bienSo, khuVuc, gia);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        response.put("status", "success");
        response.put("message", "Đăng ký vé thành công!");
        response.put("ngayDangKy", ticket.getNgayDangKy().format(fmt));
        response.put("ngayHetHan", ticket.getNgayHetHan().format(fmt));
        return response;
    }

    // ===================== ADMIN: ĐỔI TRẠNG THÁI THANH TOÁN =====================
    @PostMapping("/admin/doi-thanh-toan")
    public Map<String, Object> doiThanhToan(@RequestBody Map<String, Object> request,
                                              HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("Admin")) {
            response.put("status", "error");
            response.put("message", "Bạn không có quyền thực hiện thao tác này!");
            return response;
        }

        try {
            Long ticketId = ((Number) request.get("ticketId")).longValue();
            Ticket ticket = ticketService.doiTrangThaiThanhToan(ticketId);
            response.put("status", "success");
            response.put("thanhToan", ticket.isThanhToan());
            response.put("message", ticket.isThanhToan() ? "Đã thanh toán" : "Chưa thanh toán");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    // ===================== ĐỌC TẤT CẢ THÔNG BÁO =====================
    @PostMapping("/doc-thong-bao")
    public Map<String, Object> docThongBao(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "error");
            return response;
        }
        notificationService.docTatCa(user);
        response.put("status", "success");
        return response;
    }

    // ===================== LẤY SỐ THÔNG BÁO CHƯA ĐỌC =====================
    @GetMapping("/so-thong-bao")
    public Map<String, Object> soThongBao(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("so", 0);
            return response;
        }
        response.put("so", notificationService.demChuaDoc(user));
        return response;
    }
}
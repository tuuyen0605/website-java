package com.example.parksmart.controller;

import com.example.parksmart.model.Ticket;
import com.example.parksmart.model.User;
import com.example.parksmart.service.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService ticketService;

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
        Long gia = ((Number) request.get("gia")).longValue();

        if (loaiXe == null || loaiXe.isBlank()) {
            response.put("status", "error"); response.put("message", "Vui lòng chọn loại xe!"); return response;
        }
        if (loaiVe == null || loaiVe.isBlank()) {
            response.put("status", "error"); response.put("message", "Vui lòng chọn gói vé!"); return response;
        }
        if (bienSo == null || bienSo.isBlank()) {
            response.put("status", "error"); response.put("message", "Vui lòng nhập biển số xe!"); return response;
        }

        Ticket ticket = ticketService.dangKyVe(user, loaiXe, loaiVe, bienSo, gia);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        response.put("status", "success");
        response.put("message", "Đăng ký vé thành công!");
        response.put("ngayDangKy", ticket.getNgayDangKy().format(fmt));
        response.put("ngayHetHan", ticket.getNgayHetHan().format(fmt));
        return response;
    }
}

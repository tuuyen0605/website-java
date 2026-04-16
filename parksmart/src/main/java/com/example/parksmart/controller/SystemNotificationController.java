package com.example.parksmart.controller;

import com.example.parksmart.model.User;
import com.example.parksmart.service.SystemNotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class SystemNotificationController {

    @Autowired
    private SystemNotificationService systemNotificationService;

    @PostMapping("/tao-thong-bao")
    public Map<String, Object> taoThongBao(@RequestBody Map<String, Object> request,
                                             HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("Admin")) {
            response.put("status", "error");
            response.put("message", "Không có quyền!");
            return response;
        }
        String tieuDe = (String) request.get("tieuDe");
        String noiDung = (String) request.get("noiDung");
        if (tieuDe == null || tieuDe.isBlank() || noiDung == null || noiDung.isBlank()) {
            response.put("status", "error");
            response.put("message", "Vui lòng nhập đầy đủ thông tin!");
            return response;
        }
        systemNotificationService.taoThongBao(tieuDe, noiDung, user);
        response.put("status", "success");
        response.put("message", "Đã gửi thông báo!");
        return response;
    }

    @PostMapping("/xoa-thong-bao")
    public Map<String, Object> xoaThongBao(@RequestBody Map<String, Object> request,
                                             HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("Admin")) {
            response.put("status", "error");
            response.put("message", "Không có quyền!");
            return response;
        }
        try {
            Long id = ((Number) request.get("id")).longValue();
            systemNotificationService.xoa(id);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }
}

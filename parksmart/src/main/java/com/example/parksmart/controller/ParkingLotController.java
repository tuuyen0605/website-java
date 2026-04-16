package com.example.parksmart.controller;

import com.example.parksmart.model.ParkingLot;
import com.example.parksmart.model.Review;
import com.example.parksmart.model.User;
import com.example.parksmart.service.ParkingLotService;
import com.example.parksmart.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private ReviewService reviewService;

    // Admin: cập nhật trạng thái
    @PostMapping("/admin/cap-nhat-bai-do")
    public Map<String, Object> capNhatBaiDo(@RequestBody Map<String, Object> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("Admin")) {
            response.put("status", "error"); response.put("message", "Không có quyền!"); return response;
        }
        try {
            Long id = ((Number) request.get("id")).longValue();
            String trangThai = (String) request.get("trangThai");
            ParkingLot lot = parkingLotService.capNhatTrangThai(id, trangThai);
            response.put("status", "success");
            response.put("trangThai", lot.getTrangThai());
        } catch (Exception e) {
            response.put("status", "error"); response.put("message", e.getMessage());
        }
        return response;
    }

    // Admin: cập nhật giá
    @PostMapping("/admin/cap-nhat-gia")
    public Map<String, Object> capNhatGia(@RequestBody Map<String, Object> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("Admin")) {
            response.put("status", "error"); response.put("message", "Không có quyền!"); return response;
        }
        try {
            Long id = ((Number) request.get("id")).longValue();
            Long giaXeMayTruoc = request.get("giaXeMayTruoc") != null ? ((Number) request.get("giaXeMayTruoc")).longValue() : null;
            Long giaXeMaySau = request.get("giaXeMaySau") != null ? ((Number) request.get("giaXeMaySau")).longValue() : null;
            Long giaOToGio = request.get("giaOToGio") != null ? ((Number) request.get("giaOToGio")).longValue() : null;
            Long giaXeDap = request.get("giaXeDap") != null ? ((Number) request.get("giaXeDap")).longValue() : null;
            parkingLotService.capNhatGia(id, giaXeMayTruoc, giaXeMaySau, giaOToGio, giaXeDap);
            response.put("status", "success"); response.put("message", "Cập nhật giá thành công!");
        } catch (Exception e) {
            response.put("status", "error"); response.put("message", e.getMessage());
        }
        return response;
    }

    // Admin: cập nhật tiện ích
    @PostMapping("/admin/cap-nhat-tien-ich")
    public Map<String, Object> capNhatTienIch(@RequestBody Map<String, Object> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("Admin")) {
            response.put("status", "error"); response.put("message", "Không có quyền!"); return response;
        }
        try {
            Long id = ((Number) request.get("id")).longValue();
            String tienIch = (String) request.get("tienIch");
            parkingLotService.capNhatTienIch(id, tienIch);
            response.put("status", "success"); response.put("message", "Cập nhật tiện ích thành công!");
        } catch (Exception e) {
            response.put("status", "error"); response.put("message", e.getMessage());
        }
        return response;
    }

    // Sinh viên: gửi/sửa đánh giá
    @PostMapping("/danh-gia")
    public Map<String, Object> guiDanhGia(@RequestBody Map<String, Object> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "error"); response.put("message", "Bạn chưa đăng nhập!"); return response;
        }
        try {
            Long baiDoId = ((Number) request.get("baiDoId")).longValue();
            int soSao = ((Number) request.get("soSao")).intValue();
            String binhLuan = (String) request.get("binhLuan");
            ParkingLot lot = parkingLotService.getById(baiDoId)
                .orElseThrow(() -> new Exception("Không tìm thấy bãi đỗ!"));
            reviewService.guiDanhGia(user, lot, soSao, binhLuan);
            response.put("status", "success"); response.put("message", "Đánh giá thành công!");
            response.put("diemTrungBinh", reviewService.getDiemTrungBinh(lot));
        } catch (Exception e) {
            response.put("status", "error"); response.put("message", e.getMessage());
        }
        return response;
    }

    // GET reviews cho admin
    @GetMapping("/reviews/{baiDoId}")
    public java.util.List<Map<String, Object>> getReviews(@PathVariable Long baiDoId) {
        return parkingLotService.getById(baiDoId).map(lot -> {
            return reviewService.getDanhGia(lot).stream().map(r -> {
                Map<String, Object> m = new HashMap<>();
                m.put("id", r.getId());
                m.put("userName", r.getUser().getFullName());
                m.put("soSao", r.getSoSao());
                m.put("binhLuan", r.getBinhLuan());
                m.put("ngayTao", r.getNgayTao().toString());
                return m;
            }).collect(java.util.stream.Collectors.toList());
        }).orElse(new java.util.ArrayList<>());
    }

    // Admin: xóa bình luận
    @PostMapping("/admin/xoa-binh-luan")
    public Map<String, Object> xoaBinhLuan(@RequestBody Map<String, Object> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("Admin")) {
            response.put("status", "error"); response.put("message", "Không có quyền!"); return response;
        }
        try {
            Long reviewId = ((Number) request.get("reviewId")).longValue();
            reviewService.xoaBinhLuan(reviewId);
            response.put("status", "success"); response.put("message", "Đã xóa bình luận!");
        } catch (Exception e) {
            response.put("status", "error"); response.put("message", e.getMessage());
        }
        return response;
    }
}
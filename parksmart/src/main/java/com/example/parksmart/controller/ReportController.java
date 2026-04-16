package com.example.parksmart.controller;

import com.example.parksmart.model.Report;
import com.example.parksmart.model.User;
import com.example.parksmart.service.ReportService;
import com.example.parksmart.repository.TicketRepository;
import com.example.parksmart.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketRepository ticketRepository;

    private static final String UPLOAD_DIR = "uploads/reports/";

    /**
     * Sinh viên gửi báo cáo sự cố (multipart form với ảnh optional)
     */
    @PostMapping("/bao-cao-su-co")
    public Map<String, Object> guiBaoCao(
            @RequestParam("moTa") String moTa,
            @RequestParam(value = "bienSoNghi", required = false) String bienSoNghi,
            @RequestParam(value = "khuVuc", required = false) String khuVuc,
            @RequestParam(value = "anh", required = false) MultipartFile anh,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "error");
            response.put("message", "Bạn chưa đăng nhập!");
            return response;
        }

        if (moTa == null || moTa.isBlank()) {
            response.put("status", "error");
            response.put("message", "Vui lòng mô tả sự cố!");
            return response;
        }

        // Xử lý upload ảnh nếu có
        String anhUrl = null;
        if (anh != null && !anh.isEmpty()) {
            try {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                String fileName = UUID.randomUUID() + "_" + anh.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.write(filePath, anh.getBytes());
                anhUrl = "/" + UPLOAD_DIR + fileName;
            } catch (IOException e) {
                // Không upload được ảnh thì vẫn gửi báo cáo
                anhUrl = null;
            }
        }

        Report report = reportService.guiBaoCao(user, moTa, bienSoNghi, khuVuc, anhUrl);
        response.put("status", "success");
        response.put("message", "Gửi báo cáo thành công!");
        response.put("id", report.getId());
        return response;
    }

    /**
     * Admin tra cứu biển số → tìm chủ xe
     */
    @GetMapping("/admin/tra-cuu-bien-so")
    public Map<String, Object> traCuuBienSo(@RequestParam("bienSo") String bienSo,
                                              HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User admin = (User) session.getAttribute("loggedInUser");
        if (admin == null || !admin.getRole().equals("Admin")) {
            response.put("status", "error");
            response.put("message", "Không có quyền!");
            return response;
        }

        // Tìm user qua biển số trong bảng tickets
        User chuXe = ticketRepository.findAll().stream()
            .filter(t -> bienSo.equalsIgnoreCase(t.getBienSo()))
            .map(t -> t.getUser())
            .findFirst().orElse(null);
        if (chuXe != null) {
            response.put("status", "success");
            response.put("hoTen", chuXe.getFullName());
            response.put("email", chuXe.getEmail());
        } else {
            response.put("status", "notfound");
            response.put("message", "Không tìm thấy chủ xe với biển số này!");
        }
        return response;
    }

    /**
     * Admin cập nhật trạng thái báo cáo
     */
    @PostMapping("/admin/cap-nhat-bao-cao")
    public Map<String, Object> capNhatBaoCao(@RequestBody Map<String, Object> request,
                                               HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User admin = (User) session.getAttribute("loggedInUser");
        if (admin == null || !admin.getRole().equals("Admin")) {
            response.put("status", "error");
            response.put("message", "Không có quyền!");
            return response;
        }

        try {
            Long id = ((Number) request.get("id")).longValue();
            String trangThai = (String) request.get("trangThai");
            String ghiChu = (String) request.get("ghiChu");
            reportService.capNhatTrangThai(id, trangThai, ghiChu);
            response.put("status", "success");
            response.put("message", "Cập nhật thành công!");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }
}

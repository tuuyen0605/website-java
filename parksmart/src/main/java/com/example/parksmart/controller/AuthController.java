package com.example.parksmart.controller;

import com.example.parksmart.model.User;
import com.example.parksmart.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    // Bước 1: Kiểm tra email có trong DB không
    @PostMapping("/check-email")
    public Map<String, Object> checkEmail(@RequestBody Map<String, String> request,
                                           HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String email = request.get("email");

        if (userService.emailExists(email)) {
            session.setAttribute("pendingEmail", email); // Lưu email tạm
            response.put("status", "success");
            response.put("message", "Email hợp lệ");
        } else {
            response.put("status", "error");
            response.put("message", "Chúng tôi không tìm thấy tài khoản có tên người dùng đó.");
        }
        return response;
    }

    // Bước 2: Kiểm tra mật khẩu
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request,
                                      HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String email = request.get("email");
        String password = request.get("password");

        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user); // Lưu session
            response.put("status", "success");
            response.put("role", user.getRole());
            response.put("fullName", user.getFullName());
            response.put("redirect", "/homepage");
        } else {
            response.put("status", "error");
            response.put("message", "Mật khẩu không chính xác. Vui lòng kiểm tra lại.");
        }
        return response;
    }

    // Đăng ký tài khoản mới
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String fullName = request.get("fullName");
        String email = request.get("email");
        String password = request.get("password");

        boolean success = userService.register(fullName, email, password);
        if (success) {
            response.put("status", "success");
            response.put("message", "Đăng ký thành công!");
        } else {
            response.put("status", "error");
            response.put("message", "Email này đã được đăng ký!");
        }
        return response;
    }

    // Đổi mật khẩu
    @PostMapping("/change-password")
    public Map<String, Object> changePassword(@RequestBody Map<String, String> request,
                                               HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            response.put("status", "error");
            response.put("message", "Bạn chưa đăng nhập!");
            return response;
        }

        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");

        boolean success = userService.changePassword(loggedInUser.getEmail(), oldPassword, newPassword);
        if (success) {
            response.put("status", "success");
            response.put("message", "Đổi mật khẩu thành công!");
        } else {
            response.put("status", "error");
            response.put("message", "Mật khẩu cũ không đúng!");
        }
        return response;
    }

    // Logout
    @GetMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("redirect", "/");
        return response;
    }
}

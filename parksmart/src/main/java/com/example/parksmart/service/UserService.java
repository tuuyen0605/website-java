package com.example.parksmart.service;

import com.example.parksmart.model.User;
import com.example.parksmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Kiểm tra email có tồn tại trong DB không
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // Kiểm tra login
    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user; // Đúng cả email lẫn mật khẩu
            }
        }
        return null;
    }

    // Đăng ký tài khoản mới
    public boolean register(String fullName, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            return false; // Email đã tồn tại
        }
        User newUser = new User(fullName, email, password, "Sinh viên");
        userRepository.save(newUser);
        return true;
    }

    // Đổi mật khẩu
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    // Lấy thông tin user theo email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}

package com.example.parksmart.service;

import com.example.parksmart.model.SystemNotification;
import com.example.parksmart.model.User;
import com.example.parksmart.repository.SystemNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemNotificationService {

    @Autowired
    private SystemNotificationRepository repository;

    public SystemNotification taoThongBao(String tieuDe, String noiDung, User admin) {
        return repository.save(new SystemNotification(tieuDe, noiDung, admin));
    }

    public List<SystemNotification> getAll() {
        return repository.findAllByOrderByNgayTaoDesc();
    }

    public void xoa(Long id) throws Exception {
        if (!repository.existsById(id)) throw new Exception("Không tìm thấy thông báo!");
        repository.deleteById(id);
    }
}

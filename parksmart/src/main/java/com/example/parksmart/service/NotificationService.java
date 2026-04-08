package com.example.parksmart.service;

import com.example.parksmart.model.Notification;
import com.example.parksmart.model.Ticket;
import com.example.parksmart.model.User;
import com.example.parksmart.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Tạo 2 thông báo nhắc nhở khi đăng ký vé mới:
     * - 10 ngày trước khi hết hạn
     * - 5 ngày trước khi hết hạn
     */
    public void taoThongBaoNhacHan(User user, Ticket ticket) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String ngayHetHan = ticket.getNgayHetHan().format(fmt);

        // Thông báo 10 ngày trước
        LocalDate ngayNhac10 = ticket.getNgayHetHan().minusDays(10);
        String noiDung10 = "Vé " + ticket.getLoaiXe() + " của bạn sẽ hết hạn vào ngày "
                + ngayHetHan + ". Còn 10 ngày, hãy gia hạn sớm!";
        Notification tb10 = new Notification(user, ticket, noiDung10, ngayNhac10);
        notificationRepository.save(tb10);

        // Thông báo 5 ngày trước
        LocalDate ngayNhac5 = ticket.getNgayHetHan().minusDays(5);
        String noiDung5 = "Vé " + ticket.getLoaiXe() + " của bạn sẽ hết hạn vào ngày "
                + ngayHetHan + ". Chỉ còn 5 ngày!";
        Notification tb5 = new Notification(user, ticket, noiDung5, ngayNhac5);
        notificationRepository.save(tb5);
    }

    /**
     * Lấy danh sách thông báo chưa đọc cần hiển thị hôm nay.
     */
    public List<Notification> getThongBaoChuaDoc(User user) {
        return notificationRepository
                .findByUserAndDaDocFalseAndNgayNhacLessThanEqual(user, LocalDate.now());
    }

    /**
     * Đánh dấu tất cả thông báo là đã đọc.
     */
    public void docTatCa(User user) {
        List<Notification> list = notificationRepository.findByUser(user);
        list.forEach(n -> n.setDaDoc(true));
        notificationRepository.saveAll(list);
    }

    /**
     * Đếm số thông báo chưa đọc — dùng cho badge chuông.
     */
    public int demChuaDoc(User user) {
        return getThongBaoChuaDoc(user).size();
    }
    /**
     * Tạo thông báo đăng ký vé thành công.
     */
    public void taoThongBaoDangKyThanhCong(User user, Ticket ticket) {
        String noiDung = "Bạn đã đăng ký vé " + ticket.getLoaiXe()
            + " - " + ticket.getLoaiVe()
            + " tại " + ticket.getKhuVuc() + " thành công!";
        Notification tb = new Notification(user, ticket, noiDung, LocalDate.now());
        notificationRepository.save(tb);
    }
}

package com.example.parksmart.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết với user đăng ký vé
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "loai_xe", nullable = false)
    private String loaiXe; // "Xe máy", "Xe ô tô", "Xe đạp"

    @Column(name = "loai_ve", nullable = false)
    private String loaiVe; // "Vé tháng", "Vé quý", "Vé năm"

    @Column(name = "bien_so", nullable = false)
    private String bienSo;

    @Column(name = "gia", nullable = false)
    private Long gia;

    @Column(name = "ngay_dang_ky", nullable = false)
    private LocalDate ngayDangKy;

    @Column(name = "ngay_het_han", nullable = false)
    private LocalDate ngayHetHan;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai; // "Hoạt động", "Hết hạn", "Đã hủy"

    public Ticket() {}

    public Ticket(User user, String loaiXe, String loaiVe, String bienSo,
                  Long gia, LocalDate ngayDangKy, LocalDate ngayHetHan) {
        this.user = user;
        this.loaiXe = loaiXe;
        this.loaiVe = loaiVe;
        this.bienSo = bienSo;
        this.gia = gia;
        this.ngayDangKy = ngayDangKy;
        this.ngayHetHan = ngayHetHan;
        this.trangThai = "Hoạt động";
    }

    // Getters & Setters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getLoaiXe() { return loaiXe; }
    public void setLoaiXe(String loaiXe) { this.loaiXe = loaiXe; }
    public String getLoaiVe() { return loaiVe; }
    public void setLoaiVe(String loaiVe) { this.loaiVe = loaiVe; }
    public String getBienSo() { return bienSo; }
    public void setBienSo(String bienSo) { this.bienSo = bienSo; }
    public Long getGia() { return gia; }
    public void setGia(Long gia) { this.gia = gia; }
    public LocalDate getNgayDangKy() { return ngayDangKy; }
    public void setNgayDangKy(LocalDate ngayDangKy) { this.ngayDangKy = ngayDangKy; }
    public LocalDate getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(LocalDate ngayHetHan) { this.ngayHetHan = ngayHetHan; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}

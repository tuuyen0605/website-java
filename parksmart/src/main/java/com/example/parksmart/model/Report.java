package com.example.parksmart.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "mo_ta", nullable = false, length = 1000)
    private String moTa;

    @Column(name = "bien_so_nghi")
    private String bienSoNghi;

    @Column(name = "khu_vuc")
    private String khuVuc;

    @Column(name = "anh_url")
    private String anhUrl;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai; // "Chờ xử lý", "Đang xử lý", "Đã giải quyết"

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    @Column(name = "ghi_chu_admin", length = 500)
    private String ghiChuAdmin;

    public Report() {}

    public Report(User user, String moTa, String bienSoNghi, String khuVuc, String anhUrl) {
        this.user = user;
        this.moTa = moTa;
        this.bienSoNghi = bienSoNghi;
        this.khuVuc = khuVuc;
        this.anhUrl = anhUrl;
        this.trangThai = "Chờ xử lý";
        this.ngayTao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public String getBienSoNghi() { return bienSoNghi; }
    public void setBienSoNghi(String bienSoNghi) { this.bienSoNghi = bienSoNghi; }
    public String getKhuVuc() { return khuVuc; }
    public void setKhuVuc(String khuVuc) { this.khuVuc = khuVuc; }
    public String getAnhUrl() { return anhUrl; }
    public void setAnhUrl(String anhUrl) { this.anhUrl = anhUrl; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public LocalDateTime getNgayTao() { return ngayTao; }
    public String getGhiChuAdmin() { return ghiChuAdmin; }
    public void setGhiChuAdmin(String ghiChuAdmin) { this.ghiChuAdmin = ghiChuAdmin; }
}

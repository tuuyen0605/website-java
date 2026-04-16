package com.example.parksmart.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_notifications")
public class SystemNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tieu_de", nullable = false)
    private String tieuDe;

    @Column(name = "noi_dung", nullable = false, length = 1000)
    private String noiDung;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    public SystemNotification() {}

    public SystemNotification(String tieuDe, String noiDung, User admin) {
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.admin = admin;
        this.ngayTao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTieuDe() { return tieuDe; }
    public void setTieuDe(String tieuDe) { this.tieuDe = tieuDe; }
    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }
    public LocalDateTime getNgayTao() { return ngayTao; }
    public User getAdmin() { return admin; }
}

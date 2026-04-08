package com.example.parksmart.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "noi_dung", nullable = false)
    private String noiDung;

    @Column(name = "ngay_nhac", nullable = false)
    private LocalDate ngayNhac; // Ngày sẽ hiện thông báo

    @Column(name = "da_doc", nullable = false)
    private boolean daDoc = false;

    public Notification() {}

    public Notification(User user, Ticket ticket, String noiDung, LocalDate ngayNhac) {
        this.user = user;
        this.ticket = ticket;
        this.noiDung = noiDung;
        this.ngayNhac = ngayNhac;
        this.daDoc = false;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }
    public LocalDate getNgayNhac() { return ngayNhac; }
    public void setNgayNhac(LocalDate ngayNhac) { this.ngayNhac = ngayNhac; }
    public boolean isDaDoc() { return daDoc; }
    public void setDaDoc(boolean daDoc) { this.daDoc = daDoc; }
}

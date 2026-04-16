package com.example.parksmart.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @Column(name = "so_sao", nullable = false)
    private int soSao; // 1-5

    @Column(name = "binh_luan", length = 500)
    private String binhLuan;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    public Review() {}

    public Review(User user, ParkingLot parkingLot, int soSao, String binhLuan) {
        this.user = user;
        this.parkingLot = parkingLot;
        this.soSao = soSao;
        this.binhLuan = binhLuan;
        this.ngayTao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public ParkingLot getParkingLot() { return parkingLot; }
    public int getSoSao() { return soSao; }
    public void setSoSao(int soSao) { this.soSao = soSao; }
    public String getBinhLuan() { return binhLuan; }
    public void setBinhLuan(String binhLuan) { this.binhLuan = binhLuan; }
    public LocalDateTime getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDateTime ngayTao) { this.ngayTao = ngayTao; }
}

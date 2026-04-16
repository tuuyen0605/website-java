package com.example.parksmart.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_lots")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @Column(name = "gio_mo")
    private String gioMo;

    @Column(name = "gio_dong")
    private String gioDong;

    @Column(name = "gia_xe_may_truoc")
    private Long giaXeMayTruoc;

    @Column(name = "gia_xe_may_sau")
    private Long giaXeMaySau;

    @Column(name = "gia_o_to_gio")
    private Long giaOToGio;

    @Column(name = "gia_xe_dap")
    private Long giaXeDap;

    @Column(name = "tien_ich", length = 500)
    private String tienIch;

    public ParkingLot() {}

    public ParkingLot(String ten, String diaChi, String trangThai, String gioMo, String gioDong) {
        this.ten = ten;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.gioMo = gioMo;
        this.gioDong = gioDong;
        this.giaXeMayTruoc = 3000L;
        this.giaXeMaySau = 7000L;
        this.giaOToGio = 5000L;
        this.giaXeDap = 0L;
        this.tienIch = "";
    }

    public Long getId() { return id; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getGioMo() { return gioMo; }
    public void setGioMo(String gioMo) { this.gioMo = gioMo; }
    public String getGioDong() { return gioDong; }
    public void setGioDong(String gioDong) { this.gioDong = gioDong; }
    public Long getGiaXeMayTruoc() { return giaXeMayTruoc; }
    public void setGiaXeMayTruoc(Long v) { this.giaXeMayTruoc = v; }
    public Long getGiaXeMaySau() { return giaXeMaySau; }
    public void setGiaXeMaySau(Long v) { this.giaXeMaySau = v; }
    public Long getGiaOToGio() { return giaOToGio; }
    public void setGiaOToGio(Long v) { this.giaOToGio = v; }
    public Long getGiaXeDap() { return giaXeDap; }
    public void setGiaXeDap(Long v) { this.giaXeDap = v; }
    public String getTienIch() { return tienIch; }
    public void setTienIch(String tienIch) { this.tienIch = tienIch; }

    public boolean hasTienIch(String key) {
        if (tienIch == null || tienIch.isBlank()) return false;
        return java.util.Arrays.asList(tienIch.split(",")).contains(key);
    }
}
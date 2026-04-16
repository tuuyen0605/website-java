package com.example.parksmart.service;

import com.example.parksmart.model.Report;
import com.example.parksmart.model.User;
import com.example.parksmart.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report guiBaoCao(User user, String moTa, String bienSoNghi, String khuVuc, String anhUrl) {
        Report report = new Report(user, moTa, bienSoNghi, khuVuc, anhUrl);
        return reportRepository.save(report);
    }

    public List<Report> getBaoCaoCuaUser(User user) {
        return reportRepository.findByUserOrderByNgayTaoDesc(user);
    }

    public List<Report> getTatCaBaoCao() {
        return reportRepository.findAllByOrderByNgayTaoDesc();
    }

    public Report capNhatTrangThai(Long id, String trangThai, String ghiChu) throws Exception {
        Optional<Report> opt = reportRepository.findById(id);
        if (opt.isEmpty()) throw new Exception("Không tìm thấy báo cáo!");
        Report report = opt.get();
        report.setTrangThai(trangThai);
        if (ghiChu != null && !ghiChu.isBlank()) report.setGhiChuAdmin(ghiChu);
        return reportRepository.save(report);
    }
}

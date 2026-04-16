package com.example.parksmart.service;

import com.example.parksmart.model.ParkingLot;
import com.example.parksmart.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public List<ParkingLot> getAll() {
        List<ParkingLot> list = parkingLotRepository.findAll();
        if (list.isEmpty()) list = taoMacDinh();
        return new ArrayList<>(list);
    }

    public Optional<ParkingLot> getById(Long id) {
        return parkingLotRepository.findById(id);
    }

    public ParkingLot capNhatTrangThai(Long id, String trangThai) throws Exception {
        ParkingLot lot = parkingLotRepository.findById(id)
            .orElseThrow(() -> new Exception("Không tìm thấy bãi đỗ với ID: " + id));
        lot.setTrangThai(trangThai);
        return parkingLotRepository.save(lot);
    }

    public ParkingLot capNhatGia(Long id, Long giaXeMayTruoc, Long giaXeMaySau,
                                  Long giaOToGio, Long giaXeDap) throws Exception {
        ParkingLot lot = parkingLotRepository.findById(id)
            .orElseThrow(() -> new Exception("Không tìm thấy bãi đỗ!"));
        if (giaXeMayTruoc != null) lot.setGiaXeMayTruoc(giaXeMayTruoc);
        if (giaXeMaySau != null) lot.setGiaXeMaySau(giaXeMaySau);
        if (giaOToGio != null) lot.setGiaOToGio(giaOToGio);
        if (giaXeDap != null) lot.setGiaXeDap(giaXeDap);
        return parkingLotRepository.save(lot);
    }

    public ParkingLot capNhatTienIch(Long id, String tienIch) throws Exception {
        ParkingLot lot = parkingLotRepository.findById(id)
            .orElseThrow(() -> new Exception("Không tìm thấy bãi đỗ!"));
        lot.setTienIch(tienIch);
        return parkingLotRepository.save(lot);
    }

    private List<ParkingLot> taoMacDinh() {
        List<ParkingLot> defaults = Arrays.asList(
            new ParkingLot("Nhà để xe số 1", "Cổng 207 Giải Phóng, cạnh tòa A2 - Đại học Kinh tế Quốc dân", "Mở cửa", "5:00", "22:00"),
            new ParkingLot("Nhà để xe số 2", "Cổng Trần Đại Nghĩa, cạnh giảng đường C - Đại học Kinh tế Quốc dân", "Mở cửa", "5:00", "22:00"),
            new ParkingLot("Nhà để xe số 4", "Cổng Trần Đại Nghĩa, cạnh giảng đường C - Đại học Kinh tế Quốc dân", "Mở cửa", "5:00", "22:00"),
            new ParkingLot("Nhà để xe KTX", "Tòa KTX Đại học Kinh tế Quốc dân", "Mở cửa", "5:00", "22:00"),
            new ParkingLot("Hầm để xe B2", "Tầng hầm B2 nhà Trung tâm đào tạo, trường Đại học Kinh tế Quốc dân", "Mở cửa", "5:00", "22:00")
        );
        return parkingLotRepository.saveAll(defaults);
    }
}
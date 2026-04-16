package com.example.parksmart.service;

import com.example.parksmart.model.ParkingLot;
import com.example.parksmart.model.Review;
import com.example.parksmart.model.User;
import com.example.parksmart.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review guiDanhGia(User user, ParkingLot parkingLot, int soSao, String binhLuan) {
        Optional<Review> existing = reviewRepository.findByUserAndParkingLot(user, parkingLot);
        if (existing.isPresent()) {
            // Cập nhật đánh giá cũ
            Review review = existing.get();
            review.setSoSao(soSao);
            review.setBinhLuan(binhLuan);
            review.setNgayTao(java.time.LocalDateTime.now());
            return reviewRepository.save(review);
        }
        return reviewRepository.save(new Review(user, parkingLot, soSao, binhLuan));
    }

    public List<Review> getDanhGia(ParkingLot parkingLot) {
        return reviewRepository.findByParkingLotOrderByNgayTaoDesc(parkingLot);
    }

    public Double getDiemTrungBinh(ParkingLot parkingLot) {
        Double avg = reviewRepository.avgSoSao(parkingLot);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }

    public Optional<Review> getDanhGiaCuaUser(User user, ParkingLot parkingLot) {
        return reviewRepository.findByUserAndParkingLot(user, parkingLot);
    }

    public void xoaBinhLuan(Long reviewId) throws Exception {
        if (!reviewRepository.existsById(reviewId))
            throw new Exception("Không tìm thấy bình luận!");
        reviewRepository.deleteById(reviewId);
    }
}

package com.example.parksmart.controller;

import com.example.parksmart.model.Notification;
import com.example.parksmart.model.ParkingLot;
import com.example.parksmart.model.Review;
import com.example.parksmart.model.Ticket;
import com.example.parksmart.model.User;
import com.example.parksmart.service.NotificationService;
import com.example.parksmart.service.ParkingLotService;
import com.example.parksmart.service.ReportService;
import com.example.parksmart.service.ReviewService;
import com.example.parksmart.service.SystemNotificationService;
import com.example.parksmart.service.TicketService;
import com.example.parksmart.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PageController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SystemNotificationService systemNotificationService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/homepage")
    public String homepage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        List<Notification> thongBao = notificationService.getThongBaoChuaDoc(user);
        model.addAttribute("user", user);
        model.addAttribute("thongBao", thongBao);
        model.addAttribute("soBadge", thongBao.size());
        model.addAttribute("thongBaoHeThong", systemNotificationService.getAll());
        return "homepage";
    }

    @GetMapping("/dang-ky-ve")
    public String dangKyVe(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        List<Notification> thongBao = notificationService.getThongBaoChuaDoc(user);
        model.addAttribute("user", user);
        model.addAttribute("thongBao", thongBao);
        model.addAttribute("soBadge", thongBao.size());
        return "ticket";
    }

    @GetMapping("/vi-tri-bai-do")
    public String viTriBaiDo(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        List<Notification> thongBao = notificationService.getThongBaoChuaDoc(user);
        model.addAttribute("user", user);
        model.addAttribute("thongBao", thongBao);
        model.addAttribute("soBadge", thongBao.size());
        model.addAttribute("baiDoList", parkingLotService.getAll());
        return "parking-map";
    }

    @GetMapping("/bai-do/{id}")
    public String baiDoChiTiet(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        ParkingLot baiDo = parkingLotService.getById(id).orElse(null);
        if (baiDo == null) return "redirect:/vi-tri-bai-do";

        List<Notification> thongBao = notificationService.getThongBaoChuaDoc(user);
        Optional<Review> myReview = reviewService.getDanhGiaCuaUser(user, baiDo);

        model.addAttribute("user", user);
        model.addAttribute("thongBao", thongBao);
        model.addAttribute("soBadge", thongBao.size());
        model.addAttribute("baiDo", baiDo);
        model.addAttribute("danhGiaList", reviewService.getDanhGia(baiDo));
        model.addAttribute("diemTrungBinh", reviewService.getDiemTrungBinh(baiDo));
        model.addAttribute("myReview", myReview.orElse(null));
        return "parking-detail";
    }

    @GetMapping("/ve-cua-toi")
    public String veCuaToi(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        ticketService.capNhatTrangThaiHetHan();
        ArrayList<Ticket> danhSachVe = ticketService.getVeCuaUser(user);
        List<Notification> thongBao = notificationService.getThongBaoChuaDoc(user);

        model.addAttribute("user", user);
        model.addAttribute("danhSachVe", danhSachVe);
        model.addAttribute("thongBao", thongBao);
        model.addAttribute("soBadge", thongBao.size());
        return "my-tickets";
    }

    @GetMapping("/bao-cao-su-co")
    public String baoCaoSuCo(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        List<Notification> thongBao = notificationService.getThongBaoChuaDoc(user);
        model.addAttribute("user", user);
        model.addAttribute("thongBao", thongBao);
        model.addAttribute("soBadge", thongBao.size());
        model.addAttribute("danhSachBaoCao", reportService.getBaoCaoCuaUser(user));
        return "report";
    }

    @GetMapping("/chi-phi")
    public String chiPhi(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        return "redirect:/homepage";
    }

    @GetMapping("/admin/ve")
    public String adminVe(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        if (!user.getRole().equals("Admin")) return "redirect:/homepage";
        ArrayList<Ticket> tatCaVe = ticketService.getTatCaVe();
        model.addAttribute("user", user);
        model.addAttribute("tatCaVe", tatCaVe);
        return "admin-tickets";
    }

    @GetMapping("/admin/bai-do")
    public String adminBaiDo(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        if (!user.getRole().equals("Admin")) return "redirect:/homepage";
        model.addAttribute("user", user);
        model.addAttribute("baiDoList", parkingLotService.getAll());
        return "admin-parking";
    }

    @GetMapping("/admin/users")
    public String adminUsers(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        if (!user.getRole().equals("Admin")) return "redirect:/homepage";

        List<User> userList = userService.getAllUsers();
        ArrayList<Ticket> tatCaVe = ticketService.getTatCaVe();
        Map<Long, String> bienSoMap = new HashMap<>();
        for (User u : userList) {
            String bienSo = tatCaVe.stream()
                .filter(v -> v.getUser().getId().equals(u.getId()) && v.getBienSo() != null && !v.getBienSo().isBlank())
                .map(Ticket::getBienSo)
                .distinct()
                .collect(Collectors.joining(", "));
            bienSoMap.put(u.getId(), bienSo.isBlank() ? "-" : bienSo);
        }

        model.addAttribute("user", user);
        model.addAttribute("userList", userList);
        model.addAttribute("bienSoMap", bienSoMap);
        return "admin-users";
    }

    @GetMapping("/admin/thong-bao")
    public String adminThongBao(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        if (!user.getRole().equals("Admin")) return "redirect:/homepage";
        model.addAttribute("user", user);
        model.addAttribute("danhSachThongBao", systemNotificationService.getAll());
        return "admin-notifications";
    }

    @GetMapping("/admin/bao-cao")
    public String adminBaoCao(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        if (!user.getRole().equals("Admin")) return "redirect:/homepage";
        model.addAttribute("user", user);
        model.addAttribute("danhSachBaoCao", reportService.getTatCaBaoCao());
        return "admin-reports";
    }

    @GetMapping("/admin/chat")
    public String adminChat(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        if (!user.getRole().equals("Admin")) return "redirect:/homepage";
        model.addAttribute("user", user);
        return "chat-admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
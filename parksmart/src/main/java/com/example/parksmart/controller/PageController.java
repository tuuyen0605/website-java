package com.example.parksmart.controller;

import com.example.parksmart.model.Notification;
import com.example.parksmart.model.Ticket;
import com.example.parksmart.model.User;
import com.example.parksmart.service.NotificationService;
import com.example.parksmart.service.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private NotificationService notificationService;

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
        model.addAttribute("user", user);
        List<Notification> thongBao = notificationService.getThongBaoChuaDoc(user);
        model.addAttribute("thongBao", thongBao);
        model.addAttribute("soBadge", thongBao.size());
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
        return "parking-map";
    }

    @GetMapping("/ve-cua-toi")
    public String veCuaToi(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        // Cập nhật vé hết hạn
        ticketService.capNhatTrangThaiHetHan();

        ArrayList<Ticket> danhSachVe = ticketService.getVeCuaUser(user);
        List<Notification> thongBao = notificationService.getThongBaoChuaDoc(user);

        model.addAttribute("user", user);
        model.addAttribute("danhSachVe", danhSachVe);
        model.addAttribute("thongBao", thongBao);
        model.addAttribute("soBadge", thongBao.size());
        return "my-tickets";
    }

    @GetMapping("/admin/ve")
    public String adminVe(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        if (!user.getRole().equals("Admin")) return "redirect:/homepage";

        ArrayList<Ticket> tatCaVe = ticketService.getTatCaVe();
        model.addAttribute("user", user);
        model.addAttribute("tatCaVe", tatCaVe);
        model.addAttribute("soBadge", 0);
        return "admin-tickets";
    }

    @GetMapping("/chi-phi")
    public String chiPhi(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        model.addAttribute("soBadge", notificationService.demChuaDoc(user));
        return "redirect:/homepage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
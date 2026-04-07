package com.example.parksmart.controller;

import com.example.parksmart.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

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
        return "homepage";
    }

    @GetMapping("/dang-ky-ve")
    public String dangKyVe(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "ticket";
    }

    @GetMapping("/vi-tri-bai-do")
    public String viTriBaiDo(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "parking-map";
    }

    @GetMapping("/ve-cua-toi")
    public String veCuaToi(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        // TODO: return "my-tickets"; khi tạo template tương ứng
        return "redirect:/homepage";
    }

    @GetMapping("/chi-phi")
    public String chiPhi(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        // TODO: return "history"; khi tạo template tương ứng
        return "redirect:/homepage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
package com.nouman.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard");
        return "admin/dashboard"; // ✅ renders templates/admin/dashboard.html
    }
}
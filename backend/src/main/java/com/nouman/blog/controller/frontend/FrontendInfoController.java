package com.nouman.blog.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FrontendInfoController {

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About Us");
        return "front/about"; // loads templates/front/about.html
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("pageTitle", "Contact");
        return "front/contact"; // loads templates/front/contact.html
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("pageTitle", "Our Services");
        return "front/services"; // loads templates/front/services.html
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        model.addAttribute("pageTitle", "Search");
        model.addAttribute("keyword", keyword);
        return "front/search"; // loads templates/front/search.html
    }
}
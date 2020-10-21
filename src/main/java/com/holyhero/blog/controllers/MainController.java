package com.holyhero.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("title", "Main Page");
        return "HomePage";
    }
}

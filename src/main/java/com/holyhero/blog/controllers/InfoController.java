package com.holyhero.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {

    @GetMapping("/about")
    public String getInfo(Model model) {
        model.addAttribute("title", "About us");
        return "about";
    }
}

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/index")
    public void index(Model model) {
       model.addAttribute("message", "Hello World 반가워요!!");
        System.out.println("index 요청됨...");
    }

}

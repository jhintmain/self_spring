package com.comoozi.reptile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 메인 페이지
    @GetMapping
    public String index() {
        return "index";
    }

    // 메인 페이지
    @GetMapping("/main")
    public String main() {
        return "main";
    }
}

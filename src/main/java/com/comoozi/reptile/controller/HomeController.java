package com.comoozi.reptile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 메인 페이지
//    @GetMapping
//    public String index() {
//        return "main";
//    }

    // 메인 페이지
    @GetMapping(value = {"/", "/index", "/main"})
    public String main() {
        return "main";
    }

    // 메인 페이지
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}

package com.comoozi.reptile.controller;

import com.comoozi.reptile.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/goods")
public class GoodsController {

    private final MemberService memberService;
}

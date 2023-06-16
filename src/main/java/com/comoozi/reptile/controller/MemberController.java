package com.comoozi.reptile.controller;

import com.comoozi.reptile.dto.MemberDTO;
import com.comoozi.reptile.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm() {
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute MemberDTO memberDTO) {
        boolean joinResult = memberService.join(memberDTO);
        if (joinResult) {
            System.out.println("join-1");
            return "main";
        } else {
            System.out.println("join-2");
            return "redirect:member/join";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO mDTO = memberService.login(memberDTO);
        if (mDTO == null) {
            return "redirect:/member/login";
        } else {
            session.setAttribute("memberDTO", mDTO);
            session.setMaxInactiveInterval(60 * 30);

            // 세션에서 조회
//            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
//            MemberDTO member = (MemberDTO) session.getAttribute("memberDTO");
            return "main";
        }
    }

    @GetMapping(value = "logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/main";
    }
}

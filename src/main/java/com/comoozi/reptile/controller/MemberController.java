package com.comoozi.reptile.controller;

import ch.qos.logback.classic.Logger;
import com.comoozi.reptile.dto.MemberDTO;
import com.comoozi.reptile.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
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
            return "main";
        } else {
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

            return "main";
        }
    }

    @GetMapping(value = "logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/main";
    }

    @GetMapping("/update")
    public String updateInfo(Model model, HttpSession session) {
        MemberDTO memberDTOSession = (MemberDTO) session.getAttribute("memberDTO");

        if(memberDTOSession == null){
            log.info("로그인후 이용 가능합니다");
            return "redirect:/main";
        }
        MemberDTO memberDTO = memberService.findById(memberDTOSession.getId());
        model.addAttribute("memberDTO", memberDTO);
        System.out.println(memberDTO);
        return "member/update";
    }

    // 회원 정보 갱신
    @PostMapping("/update")
    public String updateInfo(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        memberService.updateInfo(memberDTO);
        // 갱신된 정보 세션에 다시 넣어준다
        session.setAttribute("memberDTO", memberDTO);
        session.setMaxInactiveInterval(60 * 30);
        return "redirect:/main";
    }
}

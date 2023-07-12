package com.comoozi.reptile.controller;

import com.comoozi.reptile.ScriptUtils;
import com.comoozi.reptile.dto.MemberDTO;
import com.comoozi.reptile.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final HttpServletResponse httpServletResponse;

    @GetMapping("/join")
    public String joinForm() {
        return "member/join";
    }

    @PostMapping("/join")
    public void join(@ModelAttribute MemberDTO memberDTO) throws IOException {
        boolean joinResult = memberService.join(memberDTO);
        if (joinResult) {
            ScriptUtils.alertAndMovePage(httpServletResponse, "회원가입 성공!", "/member/join");
        } else {
            ScriptUtils.alertAndBackPage(httpServletResponse, "회원가입 실패");
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO mDTO = memberService.login(memberDTO);
        if (mDTO == null) {
//            ScriptUtils.alertAndMovePage(httpServletResponse, "로그인 실패!", "/member/login");
            return "/qa/list";
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

        if (memberDTOSession == null) {
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

    @PostMapping("/email-check")
    public @ResponseBody String emailCheck(@RequestParam("email") String email){
        return memberService.emailCheck(email);
    }
}

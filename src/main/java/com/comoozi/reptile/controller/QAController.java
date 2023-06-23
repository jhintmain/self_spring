package com.comoozi.reptile.controller;

import com.comoozi.reptile.dto.MemberDTO;
import com.comoozi.reptile.dto.PagingDTO;
import com.comoozi.reptile.dto.QADTO;
import com.comoozi.reptile.dto.SearchDTO;
import com.comoozi.reptile.enums.QACategory;
import com.comoozi.reptile.service.QAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/qa")
public class QAController {

    private final QAService qaService;

    @GetMapping(value = {"/", "/list"})
    public String list(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                       @ModelAttribute SearchDTO searchDTO,
                       Model model) {

        List<QADTO> qaList = qaService.pagingList(searchDTO, page);
        PagingDTO pagingDTO = qaService.pagingParam(searchDTO, page);
        QACategory[] qaCategory = QACategory.values();
        Map<String, String> map = Arrays.stream(qaCategory)
                .collect(Collectors.toMap(Enum::name, QACategory::getName));

        log.info(pagingDTO.toString());
        model.addAttribute("qaList", qaList);
        model.addAttribute("qaCategoryMap", map);
        model.addAttribute("search", searchDTO);
        model.addAttribute("paging", pagingDTO);


        return "qa/list";
    }

    @GetMapping("/detail")
    public String detail(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam("id") Long id,
            Model model) {
        QADTO qadto = qaService.detail(id);
        QACategory[] qaCategory = QACategory.values();
        Map<String, String> map = Arrays.stream(qaCategory)
                .collect(Collectors.toMap(Enum::name, QACategory::getName));

        model.addAttribute("qaDTO", qadto);
        model.addAttribute("qaCategoryMap", map);

        return "qa/detail";
    }

    @GetMapping("/write")
    public String writeForm(Model model, HttpSession session) {
        if (session.getAttribute("memberDTO") == null) {
            return "redirect:/";
        }
        model.addAttribute("qaCategory", QACategory.values());
        return "qa/form";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute QADTO qadto, HttpSession session) {
        MemberDTO mDTO = (MemberDTO) session.getAttribute("memberDTO");
        qadto.setWriter_id(mDTO.getId());
        qadto.setReg_date(LocalDateTime.now());
        qadto.setSecret_flag(qadto.getSecret_flag() == null ? 'N' : qadto.getSecret_flag());

        qaService.write(qadto);
        return "redirect:/qa/write";
    }

    @GetMapping("/modify")
    public String modifyForm(@RequestParam("id") Long id, Model model, HttpSession session) {

        // 1. 게시판 유효성 체크
        QADTO qadto = qaService.detail(id);
        if (qadto == null) {
            log.error("없는 게시판 번호");
            return "redirect:/qa/modify";
        }
        log.info(Objects.requireNonNull(qadto).toString());

        // 2. 회원 유효성 체크
        /*
            1. 로그인체크
            2. 작성자와 로그인유저 일지 여부 확인
         */
        MemberDTO mDTO = (MemberDTO) session.getAttribute("memberDTO");
        mDTO.setId(2222L);
        if (mDTO == null || !Objects.requireNonNull(qadto).getWriter_id().equals(mDTO.getId())) {
            log.error("작성자가 아닙니다");
            return "redirect:/qa/modify";
        }

        log.info(Objects.requireNonNull(mDTO).toString());
        if (session.getAttribute("memberDTO") == null) {
            return "redirect:back";
        }

        QACategory[] qaCategory = QACategory.values();
        Map<String, String> map = Arrays.stream(qaCategory)
                .collect(Collectors.toMap(Enum::name, QACategory::getName));

        model.addAttribute("qaDTO", qadto);
        model.addAttribute("qaCategoryMap", map);
        return "qa/form";
    }

}

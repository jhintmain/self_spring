package com.comoozi.reptile.controller;

import com.comoozi.reptile.dto.MemberDTO;
import com.comoozi.reptile.dto.PagingDTO;
import com.comoozi.reptile.dto.QADTO;
import com.comoozi.reptile.dto.SearchDTO;
import com.comoozi.reptile.enums.QACategory;
import com.comoozi.reptile.service.QAService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

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


        log.info(searchDTO.toString());
        List<QADTO> qaList = qaService.pagingList(searchDTO, page);
        PagingDTO pagingDTO = qaService.pagingParam(searchDTO, page);

        model.addAttribute("qaList", qaList);
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
        model.addAttribute("qaDTO", qadto);
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
}

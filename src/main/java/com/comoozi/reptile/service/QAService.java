package com.comoozi.reptile.service;

import com.comoozi.reptile.dto.PagingDTO;
import com.comoozi.reptile.dto.QADTO;
import com.comoozi.reptile.dto.SearchDTO;
import com.comoozi.reptile.repository.QARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class QAService {

    int pageLimit = 3;
    int blockLimit = 3;

    private final QARepository qaRepository;

    public List<QADTO> pagingList(SearchDTO searchDTO, int page) {
        int pagingStart = (page - 1) * pageLimit;
        Map<String, Object> pagingParams = new HashMap<>();
        pagingParams.put("start", pagingStart);
        pagingParams.put("limit", pageLimit);

        pagingParams.put("SearchDTO", searchDTO);
        List<QADTO> pagingList = qaRepository.pagingList(pagingParams);
        return pagingList;
//        return qaRepository.pagingList();
    }

    public PagingDTO pagingParam(SearchDTO searchDTO, int page) {
        Map<String, Object> pagingParams = new HashMap<>();
        pagingParams.put("SearchDTO", searchDTO);

        // 전체 게시글 조회
        int boardCount = qaRepository.qaCount(pagingParams);

        // 전체 페이지 갯수 계산
        int maxPage = (int) Math.ceil((double) boardCount / pageLimit);

        // 시작 페이지 계산
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;

        // 끝페이지 계산
        int endPage = startPage + blockLimit - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }
        if(endPage == 0){
            endPage = 1;
            maxPage = 1;
        }
        PagingDTO pagingDTO = new PagingDTO();
        pagingDTO.setPage(page);
        pagingDTO.setMaxPage(maxPage);
        pagingDTO.setEndPage(endPage);
        pagingDTO.setStartPage(startPage);
        return pagingDTO;
    }

    @Transactional
    public int write(QADTO qadto) {
        return qaRepository.write(qadto);
    }

    @Transactional
    public QADTO detail(Long id) {
        int rs = qaRepository.updateHit(id);
        return qaRepository.findById(id);
    }
}

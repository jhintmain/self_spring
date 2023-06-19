package com.comoozi.reptile.service;

import com.comoozi.reptile.dto.MemberDTO;
import com.comoozi.reptile.enums.MemberState;
import com.comoozi.reptile.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class MemberService {

    private final MemberRepository memberRepository;
    private String message;

    // 회원가입
    public boolean join(MemberDTO memberDTO) {
        memberDTO.setState(MemberState.ACTIVE);
        memberDTO.setJoin_date(LocalDateTime.now());
        int rs = memberRepository.join(memberDTO);
        System.out.println("회원가입 결과 :" + rs);
        return rs == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public MemberDTO login(MemberDTO memberDTO) {

        MemberDTO mDTO = null;
        MemberDTO loginMemberDTO = memberRepository.findByEmail(memberDTO.getEmail());

        // 이메일없음
        if (loginMemberDTO == null) {
            message = "없는 회원입니다";
        } else {
            // 비밀번호 불일치
            if (!loginMemberDTO.getUser_password().equals(memberDTO.getUser_password())) {
                message = "비밀번호를 다시 확인해 주세요";
                // 회원상태 비정상
            } else if (!loginMemberDTO.getState().equals(MemberState.ACTIVE)) {
                message = "회원상태 비정상:" + loginMemberDTO.getState()
            } else {
                mDTO = loginMemberDTO;
                mDTO.setUser_password(null);
                mDTO.setAddress(null);
//                memberRepository.updateLoginDate(mDTO);
//                mDTO.setState(MemberState.STOP);
//                memberRepository.updateState(mDTO);
            }
        }

        return mDTO;
    }

    public void updateInfo(MemberDTO memberDTO) {
        int rs = memberRepository.updateInfo(memberDTO);
        if(rs == 1){
            System.out.println("update success");
        }else{
            System.out.println("update fail");
        }
    }

    public MemberDTO findById(Long id) {
        return memberRepository.findById(id);
    }
}

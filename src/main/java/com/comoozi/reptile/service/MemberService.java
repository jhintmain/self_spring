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
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    public boolean join(MemberDTO memberDTO) {
        memberDTO.setState(MemberState.ACTIVE);
        memberDTO.setJoin_date(LocalDateTime.now());
        int rs = memberRepository.join(memberDTO);
        System.out.println("회원가입 결과 :" + rs);
        return rs == 1;
    }

    public MemberDTO login(MemberDTO memberDTO) {

        MemberDTO mDTO = new MemberDTO();
        MemberDTO loginMemberDTO = memberRepository.findByEmail(memberDTO.getEmail());

        // 이메일없음
        if (loginMemberDTO == null) {
            System.out.println("없는 회원입니다");
        } else {
            // 비밀번호 불일치
            if (!loginMemberDTO.getUser_password().equals(memberDTO.getUser_password())) {
                System.out.println("비밀번호를 다시 확인해 주세요");
                // 회원상태 비정상
            } else if (!loginMemberDTO.getState().equals(MemberState.ACTIVE)) {
                System.out.println("회원상태 비정상:"+loginMemberDTO.getState());
            } else {
                mDTO = loginMemberDTO;
                mDTO.setUser_password(null);
            }
        }

        return mDTO;
    }
}

package com.comoozi.reptile.repository;

import com.comoozi.reptile.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final SqlSessionTemplate sql;

    public int join(MemberDTO memberDTO) {
        return sql.insert("Member.join",memberDTO);
    }

    public MemberDTO findByEmail(String email) {
        return sql.selectOne("Member.findByEmail",email);
    }

    public int updateLoginDate(MemberDTO memberDTO) {
        memberDTO.setLast_login_date(LocalDateTime.now());
        return sql.update("Member.updateLoinDate",memberDTO);
    }
    public int updateState(MemberDTO memberDTO) {
        return sql.update("Member.updateState",memberDTO);
    }

    public int updateInfo(MemberDTO memberDTO) {
        return sql.update("Member.updateInfo",memberDTO);
    }

    public MemberDTO findById(Long id) {
        return sql.selectOne("Member.findById",id);
    }
}

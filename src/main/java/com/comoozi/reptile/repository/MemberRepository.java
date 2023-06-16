package com.comoozi.reptile.repository;

import com.comoozi.reptile.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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
}

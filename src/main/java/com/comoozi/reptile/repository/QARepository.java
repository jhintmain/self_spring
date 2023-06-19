package com.comoozi.reptile.repository;

import com.comoozi.reptile.dto.QADTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class QARepository {

    private final SqlSessionTemplate sql;


    public List<QADTO> pagingList(Map<String, Object> pagingParams) {
        return sql.selectList("QA.pagingList", pagingParams);
    }

    public int write(QADTO qadto) {
        return sql.insert("QA.write", qadto);
    }

    public int qaCount(Map<String, Object> pagingParams) {
        return sql.selectOne("QA.pagingCount", pagingParams);
    }

    public QADTO findById(Long id) {
        return sql.selectOne("QA.findById", id);
    }

    public int updateHit(Long id) {
        return sql.update("QA.updateHit", id);
    }
}

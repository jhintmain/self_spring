package com.comoozi.reptile.dto;

import com.comoozi.reptile.enums.MemberState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class GoodsDTO {
    private Long id;
    private String category;
    private String type;
    private int price;
    private String name;
    private String content;
    private String state;   // 상태 ['ON','OFF']
    private int quantity;   // 수량
    private String image;   // 이미지 정보 json
    private String thumbnail;   // 썸네일
    private int event_id;   // event 테이블 id
    private LocalDateTime reg_date;
}

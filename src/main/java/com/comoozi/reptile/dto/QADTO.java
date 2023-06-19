package com.comoozi.reptile.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class QADTO {
    private Long id;
    private String category;
    private String title;
    private String content;
    private Long writer_id;
    private String writer_name;
    private int hit;
    private Character secret_flag;
    private LocalDateTime update_date;
    private LocalDateTime reg_date;
}

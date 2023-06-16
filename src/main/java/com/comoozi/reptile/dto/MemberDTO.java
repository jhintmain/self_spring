package com.comoozi.reptile.dto;

import com.comoozi.reptile.enums.MemberState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDTO {
    private Long id;
    private String user_password;
    private String user_name;
    private String email;
    private String address;
    private String mobile;
    private int age;
    private MemberState state;   // 회원 상태 ['normal','stop','leave']
    private LocalDateTime join_date;
    private LocalDateTime last_login_date;
}

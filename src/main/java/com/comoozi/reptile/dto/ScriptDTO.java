package com.comoozi.reptile.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScriptDTO {
    private String message;
    private String href;

    public ScriptDTO(String message, String href) {
        this.message = message;
        this.href = href;
    }
}

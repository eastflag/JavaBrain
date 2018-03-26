package com.eastflag.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ChatVO {
    private String session_id;
    private Integer member_id;
    private String nickname;
    private String command;
    private String message;
}

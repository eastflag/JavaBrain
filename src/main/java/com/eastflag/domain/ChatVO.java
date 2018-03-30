package com.eastflag.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ChatVO {
    private Integer member_id;
    private String from;
    private String command;
    private String message;
    private Long date; // timestamp
}

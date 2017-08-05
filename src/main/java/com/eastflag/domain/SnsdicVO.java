package com.eastflag.domain;

import lombok.Data;

@Data
public class SnsdicVO {
    private String clientId; //클라이언트 아이디
    private String userName;
    private String type;  //C: client=> server, S: server => client
    private String command;
    private String message;
}

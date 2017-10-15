package com.eastflag.domain;

import lombok.Data;

@Data
public class MemberVO {
    private Integer member_id;
    private String email;
    private String name;
    private String phone;
    private String photo_url;
    private String join_path;

    private String token;
}

package com.eastflag.domain;

import lombok.Data;

@Data
public class MemberVO {
    private Integer member_id;
    private String email;
    private String nickname;
    private String pw;
    private String photo_url;
    private String join_path;
    private Boolean is_used;
    private String created;
    private String updated;
    //private ArrayList<String> role;
    private String role;

    private String token;
}

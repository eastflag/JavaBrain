package com.eastflag.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HeroVO {
    private Integer hero_id;
    private String name;
    private String email;
    private String sex;
    private String country;
    private String address;
    private String photo;
    private String power;
    private String created;
}

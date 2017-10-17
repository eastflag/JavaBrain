package com.eastflag.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class NewsVO extends SearchVO {
    private Integer news_id;
    private String title;
    private String content;
    private String created;
    private String updated;

    private Integer comment_count;
}

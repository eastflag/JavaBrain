package com.eastflag.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by eastflag on 2016-09-20.
 */
@Data
public class AnswerVO {
    private Integer answer_id;
    private Integer category_id;
    private Integer seq;
    private String question;
    private String view;
    private String answer;
    private String created;
    private String updated;
}

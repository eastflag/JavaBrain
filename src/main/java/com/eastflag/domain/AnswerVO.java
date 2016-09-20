package com.eastflag.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by eastflag on 2016-09-20.
 */
@Data
public class AnswerVO {
    private int answer_id;
    private int boards_id;
    private int member_id;
    private int total;
    private int sum = -1;
    private int checked = -1;
    private String created;
    private String updated;

    List<FeedbackVO> feedback;
}

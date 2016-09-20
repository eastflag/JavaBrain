package com.eastflag.domain;

import lombok.Data;

/**
 * Created by eastflag on 2016-09-20.
 */
@Data
public class FeedbackVO {
    private int feedback_id;
    private int answer_id;
    private int num;
    private int point;
    private int score = -1;
    private String content;
}

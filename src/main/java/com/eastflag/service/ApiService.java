package com.eastflag.service;

import com.eastflag.domain.AnswerVO;
import com.eastflag.domain.FeedbackVO;
import com.eastflag.persistence.ApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by eastflag on 2016-09-20.
 */
@Service
public class ApiService {
    @Autowired
    private ApiMapper apiMapper;

    public void addAnswer(AnswerVO answer) {
/*        //answer_id 최대값 구하기
        int answer_id = apiMapper.selectAnswerMaxId() + 1;
        answer.setAnswer_id(answer_id);
        //전체 점수 구하기
        if(answer.getFeedback() != null) {
            int total = 0;
            for(FeedbackVO feedback: answer.getFeedback()) {
                total += feedback.getPoint();
            }
            answer.setTotal(total);
        }
        //answer 생성하기
        apiMapper.insertAnswer(answer);
        //feedback 생성하기
        for(FeedbackVO feedback : answer.getFeedback()) {
            feedback.setAnswer_id(answer_id);
            apiMapper.insertFeedback(feedback);
        }*/
    }
}

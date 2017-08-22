package com.eastflag.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionManager questionManager;

    @Override
    public void joinQuestionRoom(Session userSession) {
        //
        questionManager.joinQuestionRoom(userSession);
    }
}

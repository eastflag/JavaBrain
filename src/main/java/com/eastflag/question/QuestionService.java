package com.eastflag.question;

import javax.websocket.Session;

public interface QuestionService {
    public void joinQuestionRoom(Session userSession);
}

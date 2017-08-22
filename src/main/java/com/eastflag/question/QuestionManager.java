package com.eastflag.question;

import javax.websocket.Session;

public interface QuestionManager {
    public void joinQuestionRoom(Session userSession);
}

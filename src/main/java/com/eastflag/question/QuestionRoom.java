package com.eastflag.question;

import com.eastflag.persistence.CardsRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

public interface QuestionRoom {
    //
    public final int ROOM_STATE_QUESTIONING = 0; // 문제푸는 시간
    public final int ROOM_STATE_WATING = 1; // 대기사간

    public final String QUESTION_TYPE_ENGLISH = "ENGLISH";

    public final int QUESTION_LEVEL_HIGH = 0;
    public final int QUESTION_LEVEL_MID = 1;
    public final int QUESTION_LEVEL_LOW = 2;

    public void startQuestion();
    public void stopQuestion();
    public void closeRoom();

    public void setRepository(CardsRepository repository);
    public void setMongoTemplate(MongoTemplate mongoTemplate);

    public QuestionRoomConfig getRoomConfig();
    public void setRoomConfig(QuestionRoomConfig roomConfig);

    public void joinInRoom(Participant participant);
    public void outInRoom(Participant participant);
}

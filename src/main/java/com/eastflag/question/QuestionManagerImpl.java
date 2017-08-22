package com.eastflag.question;

import com.eastflag.persistence.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@Component
public class QuestionManagerImpl implements QuestionManager {

    @Autowired
    CardsRepository repository;

    @Autowired
    MongoTemplate mongoTemplate;

    private Map<String, QuestionRoom> questionRoomMap = new HashMap<String, QuestionRoom>();

    private void makeQuestionRoom() {
        // QuestionRoom 을 만든다.
        QuestionRoom questionRoom = new QuestionRoomImpl();

        // Room Config 설정
        QuestionRoomConfig roomConfig = new QuestionRoomConfig();
        roomConfig.setQuestionType(QuestionRoom.QUESTION_TYPE_ENGLISH);
        roomConfig.setQuestionLevel(QuestionRoom.QUESTION_LEVEL_LOW);
        roomConfig.setQuestionSolveSecond(10);
        roomConfig.setWatingSecond(5);
        roomConfig.setMaxParticipant(20);
        questionRoom.setRoomConfig(roomConfig);

        // DB 관련 설정
        questionRoom.setRepository(repository);
        questionRoom.setMongoTemplate(mongoTemplate);

        // Map 에 등록
        questionRoomMap.put(QuestionRoom.QUESTION_TYPE_ENGLISH, questionRoom);
    }

    @Override
    public void joinQuestionRoom(Session userSession) {
        //
        QuestionRoom questionRoom = questionRoomMap.get(QuestionRoom.QUESTION_TYPE_ENGLISH);

        // TODO Session 으로 할 것인지, 별도의 VO 로 할 것인지
        //questionRoom.joinInRoom();
    }
}

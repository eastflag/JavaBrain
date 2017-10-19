package com.eastflag;

import com.eastflag.persistence.CardsRepository;
import com.eastflag.question.QuestionRoom;
import com.eastflag.question.QuestionRoomConfig;
import com.eastflag.question.QuestionRoomImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuesttionRoomTest {

    @Autowired
    CardsRepository repository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Ignore
    @Test
    public void startQuestion() {
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

        questionRoom.setRepository(repository);
        questionRoom.setMongoTemplate(mongoTemplate);

        // Question Room 시작
        questionRoom.startQuestion();
        try {
            Thread.sleep(60*1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

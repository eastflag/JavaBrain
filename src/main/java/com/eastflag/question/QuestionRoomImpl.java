package com.eastflag.question;

import com.eastflag.domain.CardsVO;
import com.eastflag.persistence.CardsRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionRoomImpl implements QuestionRoom {
    //
    private QuestionRoomConfig roomConfig;
    private int roomState;

    CardsRepository repository;
    MongoTemplate mongoTemplate;

    //List<Participant> participants = new ArrayList<Participant>();
    Map<String, Participant> participants = new ConcurrentHashMap<String, Participant>();

    @Override
    public QuestionRoomConfig getRoomConfig() {
        return roomConfig;
    }

    @Override
    public void setRoomConfig(QuestionRoomConfig roomConfig) {
        this.roomConfig = roomConfig;
    }

    @Override
    public void setRepository(CardsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void startQuestion() {
        // Mongo DB 에서 문제를 가져온다.
        getQuestion();

        // 문제를 참여자에게 발송한다.
        broadcastQuestion();

        // 문제풀이 시간 timer 를 실행한다.
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                stopQuestion();
            }
        };
        timer.schedule(timerTask, roomConfig.getQuestionSolveSecond()*1000);
    }

    @Override
    public void stopQuestion() {
        // 쓸로없는 리소스를 소거한다.


        // 결과를 참여자에게 전송한다.
        broadcastReport();


        // 대기 시간 timer 를 실행한다.
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startQuestion();
            }
        };
        timer.schedule(timerTask, roomConfig.getWatingSecond()*1000);
    }

    @Override
    public void closeRoom() {
        // TODO
        // 모든 작동을 멈춘다.
        // 리소스를 소거한다.
    }

    @Override
    public void joinInRoom(Participant participant) {
        //participants.add(participant);
        participants.put(participant.getId(), participant);

        //
    }

    @Override
    public void outInRoom(Participant participant) {
        participants.remove(participant.getId());
    }

    private void getQuestion() {
        //CardsVO cards = mongoTemplate.executeCommand("db.cards.find().limit(1).skip(_rand()*db.cards.count())", CardsVO.class);
        long count = repository.count();
        Query query = new Query();

        for(int i=0; i<10; ++i) {
            int random = (int) (Math.random() * count) + 1;
            query.skip(random).limit(1);
            System.out.println("count:" + count + " random: " + random);

            List<CardsVO> cards = mongoTemplate.find(query, CardsVO.class);
            System.out.println(cards);
        }
    }

    private void broadcastQuestion() {
    }

    private void broadcastReport() {
    }
}

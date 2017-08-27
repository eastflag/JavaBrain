package com.eastflag;

import com.eastflag.domain.CardsVO;
import com.eastflag.persistence.CardsRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {
    @Autowired
    CardsRepository repository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Ignore
    @Test
    public void findsByWords() {
        // 전체 숫자 카운트: repository에 정의되지 않은 함수
        long count = repository.count();
        System.out.println(count);

        //특정 단어 찾기: repository에 정의된 함수
        CardsVO result = repository.findFirstByWord("couple");
        System.out.println(result);

        //랜던 컬렉션 가져오기: 커스텀 쿼리
        //https://www.mkyong.com/spring-boot/spring-boot-spring-data-mongodb-example/
        //List<CardsVO> cards = repository.findRandom(3);

        System.out.println(repository.findOne(174l));
    }

    @Test
    public void templateTest() {
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
}

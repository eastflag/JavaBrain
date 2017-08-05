package com.eastflag;

import com.eastflag.domain.CardsVO;
import com.eastflag.persistence.CardsRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {
    @Autowired
    CardsRepository repository;

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
}

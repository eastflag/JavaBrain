package com.eastflag.persistence;

import com.eastflag.domain.CardsVO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by eastflag on 2017-08-05.
 */
public interface CardsRepository extends MongoRepository<CardsVO, Long> {
    public CardsVO findFirstByWord(String word);

    public long countAllById();

    @Query("{$sample: {size: ?0} }")
    List<CardsVO> findRandom(int quantity);

    //Supports native JSON query string
    //https://www.mkyong.com/spring-boot/spring-boot-spring-data-mongodb-example/
/*    @Query("{domain:'?0'}")
    CardsVO findCustomById();*/
}

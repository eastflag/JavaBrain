package com.eastflag;

import com.eastflag.config.DatasourceProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTest {
    @Autowired
    private DatasourceProfile profile;

    @Test
    public void contextLoads() {
        System.out.println(profile.getUserName());
    }

}
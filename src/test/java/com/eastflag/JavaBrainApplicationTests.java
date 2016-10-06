package com.eastflag;

import com.eastflag.service.ApiService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class JavaBrainApplicationTests {

	@Autowired
	private ApiService apiService;

	@Ignore
	@Test
	public void contextLoads() {
		apiService.addAnswer(null);
	}

}

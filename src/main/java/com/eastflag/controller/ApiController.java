package com.eastflag.controller;

import com.eastflag.domain.AnswerVO;
import com.eastflag.domain.BoardVO;
import com.eastflag.domain.SocialVO;
import com.eastflag.persistence.ApiMapper;
import com.eastflag.result.Result;
import com.eastflag.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by eastflag on 2016-09-20.
 */
@RestController
@RequestMapping(value="/api", method={RequestMethod.GET, RequestMethod.POST})
public class ApiController {
    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private ApiService apiService;

    @RequestMapping(value="/addAnswer")
    public Result addAnswer(@RequestBody AnswerVO answer) {
        apiService.addAnswer(answer);
        return new Result(0, "success");
    }

    @RequestMapping("/getSocialLogin")
    public SocialVO getSocialLogin() {
        SocialVO social = new SocialVO();
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);
        String naver = "https://nid.naver.com/oauth2.0/authorize?client_id=7aWy98Ywds8IV1NEXUAL" +
                "&response_type=code&redirect_uri=http://127.0.0.1:8080/naver_callback&state=" + state;
        social.setNaver(naver);
        return social;
    }
}

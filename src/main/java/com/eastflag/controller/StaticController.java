package com.eastflag.controller;

import com.eastflag.ConfigConstant;
import com.eastflag.Constant;
import com.eastflag.utils.CommonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by eastflag on 2016-11-23.
 */
@Controller
public class StaticController {
    @Autowired
    private ConfigConstant config;

    @RequestMapping("/naver_callback")
    public String naverLogin(@RequestParam String code, @RequestParam String state, HttpSession session) {
        //access token 조회--------------------------------------------------------------
        RestTemplate restTemplate = new RestTemplate();
        //String authUri = "https://nid.naver.com/oauth2.0/authorize";
        String tokenUri = "https://nid.naver.com/oauth2.0/token";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", "7aWy98Ywds8IV1NEXUAL");
        parameters.add("client_secret", "fa1vHElxst");
        parameters.add("grant_type", "authorization_code");
        parameters.add("state", state);
        parameters.add("code", code);

        String token =  restTemplate.postForObject(tokenUri, parameters, String.class);
        System.out.println("token:" + token);

        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(token).getAsJsonObject();

        String access_token = json.get("access_token").getAsString();
        String refresh_token = json.get("refresh_token").getAsString();

        System.out.println("access token:" + access_token);
        System.out.println("refresh_token:" + refresh_token);

        //프로파일 정보 조회--------------------------------------------------------------
        String profileUri = "https://openapi.naver.com/v1/nid/me";
        parameters.clear();
        parameters.add("", "");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + access_token);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(profileUri, HttpMethod.GET, entity, String.class);
        System.out.println("response:" + response.getBody());
        //{"resultcode":"00","message":"success","response":{"email":"leesott@naver.com","nickname":"lee****",
        // "enc_id":"06ced2e089c040b245f0944c7e50cfba2243c6688cf26553d6822c6744f1cafd","profile_image":"https:\/\/ssl.pstatic.net\/static\/pwe\/address\/nodata_33x33.gif",
        // "age":"40-49","gender":"M","id":"36112080","name":"\uc774\ub3d9\uae30","birthday":"08-26"}}

        //resultcode가 00이면
        //member table에서 이메일로 검색해서 없으면 회원가입을 시키고, 있으면 쿼리한후, 자체 토큰을 생성하여 리턴한다.

        //jwt를 생성하여 client에게 넘겨준다.
        JsonObject body = parser.parse(response.getBody()).getAsJsonObject();
        JsonObject responseJson = body.getAsJsonObject("response");
        String email = responseJson.get("email").getAsString();
        String name = responseJson.get("name").getAsString();

        String jwt = CommonUtil.createJWT(email, name, "naver", Constant.SESSION_TIMEOUT);

        return config.naverSuccessUrl + "?token=" + jwt;
    }
}

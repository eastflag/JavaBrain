package com.eastflag.controller;

import com.eastflag.ConfigConstant;
import com.eastflag.Constant;
import com.eastflag.domain.AuthVO;
import com.eastflag.domain.MemberVO;
import com.eastflag.persistence.LoginMapper;
import com.eastflag.utils.CommonUtil;
import com.google.gson.JsonElement;
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
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by eastflag on 2016-11-23.
 */
@Controller
public class StaticController {
    @Autowired
    private ConfigConstant configConstant;

    @Autowired
    private LoginMapper loginMapper;

    // naver javabrain 사이트 로그인 ----------------------------------------------------------------------------
    @RequestMapping("/naver_callback")
    public String naverLogin1(@RequestParam String code, @RequestParam String state, HttpSession session) {
        return naverLogin(code, state, session, configConstant.frontHost);
    }

    // naver 교육용 localhost
    @RequestMapping("/naver_callback2")
    public String naverLogin2(@RequestParam String code, @RequestParam String state, HttpSession session) {
        return naverLogin(code, state, session, "localhost:4200");
    }

    // naver 교육용 상용 사이트
    @RequestMapping("/naver_callback3")
    public String naverLogin3(@RequestParam String code, @RequestParam String state, HttpSession session) {
        return naverLogin(code, state, session, "www.eastflag.co.kr:3000");
    }

    // kakao javabrain 사이트 로그인---------------------------------------------------------------------------
    @RequestMapping("/kakao_callback")
    public String kakaoLogin1(@RequestParam String code, @RequestParam String state, HttpSession session) {
        String redirectUrl = null;
        if ("kakao2".equals(state)) {
            redirectUrl = "localhost:4200";
        } else if ("kakao3".equals(state)) {
            redirectUrl = "www.eastflag.co.kr:3000";
        } else {
            redirectUrl = configConstant.frontHost;
        }
        return kakaoLogin(code, redirectUrl);
    }

    // facebook javabrain 사이트 로그인---------------------------------------------------------------------------
    @RequestMapping("/facebook_callback")
    public String facebookLogin1(@RequestParam String code, @RequestParam String state, HttpSession session) {
        return facebookLogin(code, state, "/facebook_callback", configConstant.frontHost);
    }

    // facebook 교육용 localhost
    @RequestMapping("/facebook_callback2")
    public String facebookLogin2(@RequestParam String code, @RequestParam String state, HttpSession session) {
        return facebookLogin(code, state, "/facebook_callback2", "localhost:4200");
    }

    // facebook 교육용 상용 사이트
    @RequestMapping("/facebook_callback3")
    public String facebookLogin3(@RequestParam String code, @RequestParam String state, HttpSession session) {
        return facebookLogin(code, state, "/facebook_callback3", "www.eastflag.co.kr:3000");
    }

    private String naverLogin(String code, String state, HttpSession session, String redirectUrl) {
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
        String photo_url = responseJson.get("profile_image").getAsString();
        //String name = responseJson.get("name").getAsString();

        MemberVO inMember = new MemberVO();
        inMember.setEmail(email);
        inMember.setJoin_path("naver");
        inMember.setPhoto_url(photo_url);

        MemberVO member = loginMapper.selectMember(inMember);
        // 테이블에 없을시 회원가입 페이지로 리다이렉트, 있을경우 토큰 발행
        if (member == null) {
            return String.format("redirect:http://%s/register?result=100&email=%s&join_path=%s&photo_url=%s",
                    redirectUrl, email, "naver", photo_url);
        } else {
            getToken(member);
            return String.format("redirect:http://%s/register?result=0&token=%s", redirectUrl, member.getToken());
        }
    }

    private String kakaoLogin(String code, String redirectUrl) {
        //access token 조회--------------------------------------------------------------
        RestTemplate restTemplate = new RestTemplate();

        String tokenUri = "https://kauth.kakao.com/oauth/token";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", "65adac6a230c028f0488c1557a25e1b4");
        //parameters.add("client_secret", "g145ci996K");
        parameters.add("grant_type", "authorization_code");
        parameters.add("code", code);
        parameters.add("redirect_uri", "http://"+ configConstant.backendHost + "/kakao_callback");

        String token =  restTemplate.postForObject(tokenUri, parameters, String.class);
        System.out.println("token:" + token);

        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(token).getAsJsonObject();

        String access_token = json.get("access_token").getAsString();
//        String refresh_token = json.get("refresh_token").getAsString();
//
//        System.out.println("access token:" + access_token);
//        System.out.println("refresh_token:" + refresh_token);

        //프로파일 정보 조회--------------------------------------------------------------
        String profileUri = "https://kapi.kakao.com/v1/user/me";
        parameters.clear();
        parameters.add("", "");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + access_token);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(profileUri, HttpMethod.GET, entity, String.class);
        System.out.println("response:" + response.getBody());
        //{"kaccount_email":"minotech1@hanmail.net","kaccount_email_verified":true,"id":545546886,"properties":
        // {"profile_image":"http://k.kakaocdn.net/dn/bbP1JA/btqhAzeM8r6/kXrvkLaJtKIh4Qso7jRbr0/img_640x640.jpg",
        // "nickname":"김민호","thumbnail_image":"http://k.kakaocdn.net/dn/bbP1JA/btqhAzeM8r6/kXrvkLaJtKIh4Qso7jRbr0/img_110x110.jpg"}}

        //jwt를 생성하여 client에게 넘겨준다.
        JsonObject body = parser.parse(response.getBody()).getAsJsonObject();
        String email = null;
        if (body.has("kaccount_email")) {
            email = body.get("kaccount_email").getAsString();
        } else {
            email = body.get("id").getAsString();
        }
        //JsonObject properties = body.getAsJsonObject("properties");
        //String photo_url = properties.get("profile_image").getAsString();

        MemberVO inMember = new MemberVO();
        inMember.setEmail(email);
        inMember.setJoin_path("kakao");
        //inMember.setPhoto_url(photo_url);

        MemberVO member = loginMapper.selectMember(inMember);
        if (member == null) {
            return String.format("redirect:http://%s/register?result=100&email=%s&join_path=%s&photo_url=%s",
                    redirectUrl, email, "kakao", "");
        } else {
            getToken(member);
            return String.format("redirect:http://%s/register?result=0&token=%s", redirectUrl, member.getToken());
        }
    }

    private String facebookLogin(String code, String state, String facebookUrl, String redirectUrl) {
        //access token 조회--------------------------------------------------------------
        RestTemplate restTemplate = new RestTemplate();
        //String authUri = "https://nid.naver.com/oauth2.0/authorize";
        String tokenUri = "https://graph.facebook.com/v2.8/oauth/access_token";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", "735425506598629");
        parameters.add("redirect_uri", "http://" + configConstant.backendHost + facebookUrl);
        parameters.add("client_secret", "5250f8718e9004bff75fb536999d2cbe");
        // parameters.add("grant_type", "client_credentials");
        parameters.add("state", state);
        parameters.add("code", code);

        String token =  restTemplate.postForObject(tokenUri, parameters, String.class);
        System.out.println("token:" + token);

        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(token).getAsJsonObject();

        String access_token = json.get("access_token").getAsString();
//        String refresh_token = json.get("refresh_token").getAsString();
//
//        System.out.println("access token:" + access_token);
//        System.out.println("refresh_token:" + refresh_token);

        //프로파일 정보 조회--------------------------------------------------------------
        String profileUri = "https://graph.facebook.com/v2.8/me";
        parameters.clear();
        parameters.add("fields", "email");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + access_token);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(profileUri, HttpMethod.GET, entity, String.class);
        System.out.println("response:" + response.getBody());
        // response:{"name":"DongKee Lee","id":"1129276577139566"}

        //resultcode가 00이면
        //member table에서 이메일로 검색해서 없으면 회원가입을 시키고, 있으면 쿼리한후, 자체 토큰을 생성하여 리턴한다.

        //jwt를 생성하여 client에게 넘겨준다.
        JsonObject responseJson = parser.parse(response.getBody()).getAsJsonObject();
        String email = null;
        if (responseJson.has("email")) {
            email = responseJson.get("email").getAsString();
        } else {
            email = responseJson.get("id").getAsString();
        }
        //String photo_url = responseJson.get("profile_image").getAsString();
        //String name = responseJson.get("name").getAsString();

        MemberVO inMember = new MemberVO();
        inMember.setEmail(email);
        inMember.setJoin_path("facebook");
        //inMember.setPhoto_url(photo_url);

        MemberVO member = loginMapper.selectMember(inMember);
        // 테이블에 없을시 회원가입 페이지로 리다이렉트, 있을경우 토큰 발행
        if (member == null) {
            return String.format("redirect:http://%s/register?result=100&email=%s&join_path=%s&photo_url=%s",
                    redirectUrl, email, "facebook", "");
        } else {
            getToken(member);
            return String.format("redirect:http://%s/register?result=0&token=%s", redirectUrl, member.getToken());
        }
    }

    /**
     * 토큰 발행: id:  member_id, issuer: email, subject: role
     * @param member
     */
    private void getToken(MemberVO member) {
        // 토큰
        String token;
        try {
            List<AuthVO> authList = loginMapper.selectAuth(member);
            StringBuffer role = new StringBuffer();
            if (authList != null && authList.size() > 0) {
                for(AuthVO auth : authList) {
                    role.append(",");
                    role.append(auth.getRole());
                }
                role.deleteCharAt(0);
            }

            token = CommonUtil.createJWT(String.valueOf(member.getMember_id()), member.getEmail(),
                    role.toString(), Constant.SESSION_TIMEOUT);
            member.setToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

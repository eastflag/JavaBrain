package com.eastflag.controller;

import com.eastflag.ConfigConstant;
import com.eastflag.Constant;
import com.eastflag.domain.*;
import com.eastflag.persistence.LoginMapper;
import com.eastflag.result.Result;
import com.eastflag.result.ResultData;
import com.eastflag.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private ConfigConstant configConstant;

    @Autowired
    private LoginMapper loginMapper;

    @PostMapping(value="/api/signUp")
    public Result signup(@RequestBody MemberVO inMember) {
        MemberVO member = loginMapper.selectMemberByNickname(inMember);
        if (member == null) {
            // 탈퇴 여부 확인
            int result = loginMapper.checkMemberLog(inMember.getEmail());
            if (result == 0) {
                loginMapper.insertMember(inMember);
            } else {
                // 탈퇴 로그에 join 추가
                MemberLogVO memberLog = new MemberLogVO();
                memberLog.setEmail(inMember.getEmail());
                memberLog.setType("join");
                loginMapper.insertMemberLog(memberLog);
                // 멤버 is_used 변경
                inMember = loginMapper.selectMember(inMember);
                inMember.setIs_used(true);
                loginMapper.updateMember(inMember);
            }
            getToken(inMember);
            return new ResultData(0, "success", inMember);
        } else {
            return new Result(100, "닉네임 중복");
        }
    }

    @PostMapping(value="/api/login")
    public Result login(@RequestBody MemberVO inMember) {
        inMember.setIs_used(true);
        MemberVO member = loginMapper.selectMember(inMember);
        if (member == null) {
            return new Result(100, "do not exist");
        } else {
            getToken(member);
            return new ResultData<>(0, "success", member);
        }
    }

    @PutMapping(value="/api/nickname")
    public Result modifyNickname(@RequestBody MemberVO inMember) {
        int result = loginMapper.checkNickname(inMember);
        if (result > 0) {
            return new Result(100, "닉네임 중복. 다른 닉네임을 사용하세요");
        } else {
            loginMapper.updateNickname(inMember);
            return new Result(0, "success");
        }

    }

    @GetMapping(value="/api/nickname")
    public MemberVO getNickname(@RequestParam int member_id) {
        return loginMapper.selectNickname(member_id);
    }

    // 참고 자료: https://github.com/Blackseed/NaverLoginTutorial/wiki/Spring-MVC-%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-%EB%84%A4%EC%9D%B4%EB%B2%84%EC%95%84%EC%9D%B4%EB%94%94%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0#step-15-access-token%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-%EB%84%A4%EC%9D%B4%EB%B2%84-%EC%82%AC%EC%9A%A9%EC%9E%90-%ED%94%84%EB%A1%9C%ED%95%84-%EC%A1%B0%ED%9A%8C%ED%95%98%EA%B8%B0
    @RequestMapping("/api/social")
    public SocialVO getSocialLogin(@RequestParam String site) {
        SocialVO social = new SocialVO();

        if ("naver".equals(site)) {
            SecureRandom random = new SecureRandom();
            String state = new BigInteger(130, random).toString(32);
            String url = "https://nid.naver.com/oauth2.0/authorize?client_id=7aWy98Ywds8IV1NEXUAL" +
                    "&response_type=code&redirect_uri=http://" + configConstant.backendHost + "/naver_callback&state=" + state;
            social.setUrl(url);
        } else if ("naver2".equals(site)) { // localhost:4200 접근용
            SecureRandom random = new SecureRandom();
            String state = new BigInteger(130, random).toString(32);
            String url = "https://nid.naver.com/oauth2.0/authorize?client_id=7aWy98Ywds8IV1NEXUAL" +
                    "&response_type=code&redirect_uri=http://" + configConstant.backendHost + "/naver_callback2&state=" + state;
            social.setUrl(url);  // www.javabrain
        } else if ("naver3".equals(site)) { // www.javabrain.kr:3000 접근용
            SecureRandom random = new SecureRandom();
            String state = new BigInteger(130, random).toString(32);
            String url = "https://nid.naver.com/oauth2.0/authorize?client_id=7aWy98Ywds8IV1NEXUAL" +
                    "&response_type=code&redirect_uri=http://" + configConstant.backendHost + "/naver_callback3&state=" + state;
            social.setUrl(url);
        } else if ("kakao".equals(site)) {
            String url = "https://kauth.kakao.com/oauth/authorize?client_id=7cbf743459adef91cc9af0231f094ed6" +
                    "&redirect_uri=http://" + configConstant.backendHost + "/kakao_callback&response_type=code";
            social.setUrl(url);
        }

        return social;
    }

    @PostMapping(value="/api/leave")
    public Result leave(@RequestBody MemberVO member) {
        MemberLogVO memberLog = new MemberLogVO();
        memberLog.setEmail(member.getEmail());
        memberLog.setType("leave");
        loginMapper.insertMemberLog(memberLog);

        member = loginMapper.selectMember(member);
        member.setIs_used(false);
        loginMapper.updateMember(member);
        return new Result(0, "success");
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

    /*@PostMapping(value="/signUp")
    public MemberVO signup(@RequestBody MemberVO member) {
        loginMapper.insertMember(member);
        getToken(member);
        return member;
    }

    @PostMapping(value="/login")
    public MemberVO login(@RequestBody MemberVO inMember) {
        MemberVO member = loginMapper.selectMember(inMember);
        if (member == null) {
            loginMapper.insertMember(inMember);
            getToken(inMember);
            return inMember;
        } else {
            getToken(member);
            return member;
        }
    }

    *//**
     * 토큰 발행: id:  member_id, issuer: email, subject: role
     * @param member
     *//*
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
    }*/
}

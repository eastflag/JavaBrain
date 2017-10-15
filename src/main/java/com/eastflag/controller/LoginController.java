package com.eastflag.controller;

import com.eastflag.Constant;
import com.eastflag.domain.AuthVO;
import com.eastflag.domain.MemberVO;
import com.eastflag.domain.TodoVO;
import com.eastflag.persistence.LoginMapper;
import com.eastflag.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private LoginMapper loginMapper;

    @PostMapping(value="/signUp")
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

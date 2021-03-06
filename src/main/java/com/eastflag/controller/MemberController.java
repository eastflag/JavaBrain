package com.eastflag.controller;

import com.eastflag.ConfigConstant;
import com.eastflag.domain.CommentVO;
import com.eastflag.domain.MemberVO;
import com.eastflag.persistence.LoginMapper;
import com.eastflag.persistence.TodoMapper;
import com.eastflag.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private ConfigConstant configConstant;

    @Autowired
    private TodoMapper todoMapper;

    @Autowired
    private LoginMapper loginMapper;

    // 댓글 관리--------------------------------------------------------------------------------------------------------
    @GetMapping(value="/api/comment")
    public List<CommentVO> findComment(@RequestParam int news_id) {
        return todoMapper.selectComment(news_id);
    }

    @PostMapping(value="/api/comment")
    public Result addComment(@RequestBody CommentVO comment) {
        todoMapper.insertComment(comment);
        return new Result(0, "success");
    }

    @PutMapping(value="/api/comment")
    public Result modifyComment(@RequestBody CommentVO comment) {
        todoMapper.updateComment(comment);
        return new Result(0, "success");
    }

    @DeleteMapping(value="/api/comment")
    public Result removeComment(@RequestParam int comment_id) {
        int result = todoMapper.deleteComment(comment_id);
        if (result > 0) {
            return new Result(0, "success");
        } else {
            return new Result(100, "nothing is deleted");
        }
    }

    // 회원정보
    @GetMapping("/api/member")
    public MemberVO findMember(@RequestParam int member_id) {
        return loginMapper.selectMemberById(member_id);
    }

    @PutMapping("/api/member")
    public Result findMember(@RequestBody MemberVO member) {
        loginMapper.updateNickname(member);
        return new Result(0, "success");
    }
}

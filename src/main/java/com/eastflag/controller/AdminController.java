package com.eastflag.controller;

import com.eastflag.domain.AnswerVO;
import com.eastflag.persistence.AdminMapper;
import com.eastflag.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;

    @RequestMapping(value="/answer", method={RequestMethod.GET})
    public List<AnswerVO> getAnswerList(@RequestParam int category_id) {
        return adminMapper.selectAnswerList(category_id);
    }

    @RequestMapping(value="/answer", method={RequestMethod.POST})
    public Result addAnswer(@RequestBody AnswerVO answer) {
        adminMapper.insertAnswer(answer);
        return new Result(0, "success");
    }

    @RequestMapping(value="/answer", method={RequestMethod.PUT})
    public Result updateAnswer(@RequestBody AnswerVO answer) {
        adminMapper.updateAnswer(answer);
        return new Result(0, "success");
    }

    @RequestMapping(value="/answer", method={RequestMethod.DELETE})
    public Result deleteAnswer(@RequestParam int answer_id) {
        int result = adminMapper.deleteAnswer(answer_id);
        if (result > 0) {
            return new Result(0, "success");
        } else {
            return new Result(100, "nothing is deleted");
        }
    }
}

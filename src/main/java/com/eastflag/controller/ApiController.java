package com.eastflag.controller;

import com.eastflag.domain.AnswerVO;
import com.eastflag.domain.BoardVO;
import com.eastflag.persistence.ApiMapper;
import com.eastflag.result.Result;
import com.eastflag.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}

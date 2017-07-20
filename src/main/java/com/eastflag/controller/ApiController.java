package com.eastflag.controller;

import com.eastflag.domain.*;
import com.eastflag.persistence.AdminMapper;
import com.eastflag.persistence.ApiMapper;
import com.eastflag.result.Result;
import com.eastflag.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by eastflag on 2016-09-20.
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ApiService apiService;

    @RequestMapping(value="/addAnswer")
    public Result addAnswer(@RequestBody AnswerVO answer) {
        //apiService.addAnswer(answer);
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

    @RequestMapping(value="/categoryTree", method={RequestMethod.GET})
    public List<CategoryVO> getCategoryList(@RequestParam Integer parent_id) {
        List<CategoryVO> categoryList = apiMapper.selectCategoryTree(parent_id);
        return getChildren(categoryList);
    }

    private List<CategoryVO> getChildren(List<CategoryVO> categoryList) {
        for(CategoryVO category: categoryList) {
            if(category.getCount() > 0) {
                category.setChildren(getChildren(apiMapper.selectCategoryTree(category.getCategory_id())));
            }
        }
        return categoryList;
    }

    @RequestMapping(value="/answer", method={RequestMethod.GET})
    public List<AnswerVO> getAnswerList(@RequestParam int category_id) {
        return adminMapper.selectAnswerList(category_id);
    }
}

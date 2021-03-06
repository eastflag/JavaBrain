package com.eastflag.controller;

import com.eastflag.ConfigConstant;
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

    @Autowired
    private ConfigConstant config;

    @RequestMapping(value="/addAnswer")
    public Result addAnswer(@RequestBody AnswerVO answer) {
        //apiService.addAnswer(answer);
        return new Result(0, "success");
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

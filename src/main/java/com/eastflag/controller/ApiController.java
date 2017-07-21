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

   // 참고 자료: https://github.com/Blackseed/NaverLoginTutorial/wiki/Spring-MVC-%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-%EB%84%A4%EC%9D%B4%EB%B2%84%EC%95%84%EC%9D%B4%EB%94%94%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0#step-15-access-token%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-%EB%84%A4%EC%9D%B4%EB%B2%84-%EC%82%AC%EC%9A%A9%EC%9E%90-%ED%94%84%EB%A1%9C%ED%95%84-%EC%A1%B0%ED%9A%8C%ED%95%98%EA%B8%B0
    @RequestMapping("/social")
    public SocialVO getSocialLogin(@RequestParam String site) {
        SocialVO social = new SocialVO();
        social.setSite(site);

        if("naver".equals(site)) {
            SecureRandom random = new SecureRandom();
            String state = new BigInteger(130, random).toString(32);
            String url = "https://nid.naver.com/oauth2.0/authorize?client_id=7aWy98Ywds8IV1NEXUAL" +
                    "&response_type=code&redirect_uri=http://127.0.0.1:8080/naver_callback&state=" + state;
            social.setUrl(url);
        }

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

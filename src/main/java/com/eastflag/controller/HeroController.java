package com.eastflag.controller;

import com.eastflag.domain.HeroVO;
import com.eastflag.persistence.HeroMapper;
import com.eastflag.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HeroController {
    @Autowired
    private HeroMapper heroMapper;

    @PostMapping("/hero")
    public Result addHero(@RequestBody HeroVO hero) {
        int result = heroMapper.insertHero(hero);
        if ( result > 0) {
            return new Result(0, "success");
        } else {
            return new Result(100, "fail");
        }
    }

    @GetMapping("/hero/{hero_id}")
    public HeroVO findOneHero(@PathVariable int hero_id) {
        return heroMapper.findOneHero(hero_id);
    }

    @GetMapping("/heroes")
    public List<HeroVO> findOneHero() {
        return heroMapper.findHero();
    }

    @PutMapping("/hero")
    public Result modifyHero(@RequestBody HeroVO hero) {
        int result = heroMapper.updateHero(hero);
        if ( result > 0) {
            return new Result(0, "success");
        } else {
            return new Result(100, "fail");
        }
    }

    @DeleteMapping("hero")
    public Result removeHero(@RequestParam int hero_id) {
        int result = heroMapper.deleteHero(hero_id);
        if ( result > 0) {
            return new Result(0, "success");
        } else {
            return new Result(100, "fail");
        }
    }
}

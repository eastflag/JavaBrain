package com.eastflag.controller;

import com.eastflag.ConfigConstant;
import com.eastflag.domain.HeroVO;
import com.eastflag.domain.SearchVO;
import com.eastflag.persistence.HeroMapper;
import com.eastflag.result.Result;
import com.eastflag.result.ResultDataTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HeroController {
    @Autowired
    private ConfigConstant configConstant;

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
    public List<HeroVO> findHero() {
        return heroMapper.findHero();
    }

    @GetMapping(value="/paged_heroes")
    public Result findPagedHero(@RequestParam(required = false) Integer start_index, @RequestParam(required = false) Integer page_size) {
        SearchVO search = new SearchVO();
        search.setStart_index(start_index);
        search.setPage_size(page_size);
        return new ResultDataTotal<>(0, "success", heroMapper.selectHeroList(search), heroMapper.countHero());
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

    // 파일 업로드
    @PostMapping("/file")
    public Result fileUpload(@RequestPart(value="file") MultipartFile file) {
        try {
            // 이미지가 있는지 체크
            if (file != null) {
                //업로드할 디렉토리가 있는지 체크
                String path = configConstant.uploadRootFolder + configConstant.news_image_folder;
                File dir = new File(path);
                if (!dir.isDirectory()) {
                    dir.mkdirs();
                }
                // 파일 저장: 파일명은 중복을 피하기 위해서 파일명 _타임스템프
                String filename = file.getOriginalFilename();
                String savedFilename = filename.substring(0, filename.lastIndexOf(".")) + "_" +
                        System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
                File saveFile = new File(path, savedFilename);
                file.transferTo(saveFile);

                return new Result(0, configConstant.news_image_folder + "/" + savedFilename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(500, "internal server error");
    }
}

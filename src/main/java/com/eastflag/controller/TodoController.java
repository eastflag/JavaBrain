package com.eastflag.controller;

import com.eastflag.ConfigConstant;
import com.eastflag.domain.CommentVO;
import com.eastflag.domain.NewsVO;
import com.eastflag.domain.SearchVO;
import com.eastflag.domain.TodoVO;
import com.eastflag.persistence.TodoMapper;
import com.eastflag.result.Result;
import com.eastflag.result.ResultDataTotal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private ConfigConstant configConstant;

    @Autowired
    private TodoMapper todoMapper;


    @PostMapping(value="/api/todo")
    public TodoVO addTodo(@RequestBody TodoVO todo) {
        todoMapper.insertTodo(todo);
        return todoMapper.selectTodo(todo);
    }

    @PutMapping(value="/api/todo")
    public TodoVO modifyTodo(@RequestBody TodoVO todo) {
        todoMapper.updateTodo(todo);
        return todoMapper.selectTodo(todo);
    }

    @DeleteMapping(value="/api/todo")
    public Result removeTodo(@RequestParam int todo_id) {
        int result = todoMapper.deleteTodo(todo_id);
        if (result > 0) {
            return new Result(0, "success");
        } else {
            return new Result(100, "nothing is deleted");
        }
    }

    @GetMapping(value="/api/todo")
    public List<TodoVO> findTodo(@RequestParam(required = false) Integer start_index, @RequestParam(required = false) Integer page_size) {
        SearchVO search = new SearchVO();
        search.setStart_index(start_index);
        search.setPage_size(page_size);
        return todoMapper.selectTodoList(search);
    }

    //news -----------------------------------------------------------------------
    @PostMapping(value="/api/newsList")
    public Result findNews(@RequestBody NewsVO news) {
        return new ResultDataTotal<>(0, "success", todoMapper.selectNews(news), todoMapper.countNews());
    }

    @GetMapping(value="/api/news")
    public NewsVO findOneNews(@RequestParam int news_id) {
        return todoMapper.selectOneNews(news_id);
    }

    @PostMapping(value="/api/news")
    public Result addNews(@RequestBody NewsVO news) {
        todoMapper.insertNews(news);
        return new Result(0, "success");
    }

    @PutMapping(value="/api/news")
    public Result modifyNews(@RequestBody NewsVO news) {
        todoMapper.updateNews(news);
        return new Result(0, "success");
    }

    @DeleteMapping(value="/api/news")
    public Result removeNews(@RequestParam int news_id) {
        int result = todoMapper.deleteNews(news_id);
        if (result > 0) {
            return new Result(0, "success");
        } else {
            return new Result(100, "nothing is deleted");
        }
    }

    // 댓글 관리--------------------------------------------------------------------------------------------------------
    @GetMapping(value="/api/comment")
    public List<CommentVO> findComment() {
        return todoMapper.selectComment();
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

    // 파일 업로드
    @RequestMapping("/api/imageUpload")
    public Result imageUpload(@RequestPart(value="file") MultipartFile file) {
        try {
            //상품 이미지가 있는지 체크
            if (file != null) {
                //업로드할 디렉토리가 있는지 체크
                String path = configConstant.uploadRootFolder + configConstant.news_image_folder;
                File dir = new File(path);
                if (!dir.isDirectory()) {
                    dir.mkdirs();
                }
                //파일 저장: 파일명은 타임스템프, 확장자는 화일명에서 추출출
                String filename = file.getOriginalFilename();
                String savedFilename = System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
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

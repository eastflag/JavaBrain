package com.eastflag.controller;

import com.eastflag.domain.NewsVO;
import com.eastflag.domain.SearchVO;
import com.eastflag.domain.TodoVO;
import com.eastflag.persistence.TodoMapper;
import com.eastflag.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private TodoMapper todoMapper;

    @PostMapping(value="/api/todo")
    public TodoVO addTodo(@RequestBody TodoVO todo) {
        todoMapper.insertTodo(todo);
        return todoMapper.selectTodo(todo);
    }

    @PutMapping(value="/api/todo")
    public Result modifyTodo(@RequestBody TodoVO todo) {
        todoMapper.updateTodo(todo);
        return new Result(0, "success");
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
    @PostMapping(value="/api/news")
    public NewsVO addNews(@RequestBody NewsVO news) {
        todoMapper.insertNews(news);
        return todoMapper.selectNews(news.getNews_id());
    }

    @GetMapping(value="/api/news")
    public List<NewsVO> findNews(@RequestParam(required = false) Integer start_index, @RequestParam(required = false) Integer page_size) {
        SearchVO search = new SearchVO();
        search.setStart_index(start_index);
        search.setPage_size(page_size);
        return todoMapper.selectNewsList(search);
    }

    @GetMapping(value="/api/news/{news_id}")
    public NewsVO findOneNews(@PathVariable("news_id") int news_id) {
        return todoMapper.selectNews(news_id);
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
}

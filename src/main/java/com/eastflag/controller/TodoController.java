package com.eastflag.controller;

import com.eastflag.domain.TodoVO;
import com.eastflag.persistence.TodoMapper;
import com.eastflag.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private TodoMapper todoMapper;

    @RequestMapping(value="/api/todo", method={RequestMethod.POST})
    public Result addTodo(@RequestBody TodoVO todo) {
        todoMapper.insertTodo(todo);
        return new Result(0, "success");
    }

    @RequestMapping(value="/api/todo", method={RequestMethod.PUT})
    public Result updateTodo(@RequestBody TodoVO todo) {
        todoMapper.updateTodo(todo);
        return new Result(0, "success");
    }

    @RequestMapping(value="/api/todo", method={RequestMethod.DELETE})
    public Result deleteTodo(@RequestBody TodoVO todo) {
        todoMapper.deleteTodo(todo);
        return new Result(0, "success");
    }

    @RequestMapping(value="/api/todo", method={RequestMethod.GET})
    public List<TodoVO> getTodoList() {

        return todoMapper.selectTodoList();
    }
}

package com.eastflag.controller;

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
    public Result deleteTodo(@RequestParam int todo_id) {
        int result = todoMapper.deleteTodo(todo_id);
        if (result > 0) {
            return new Result(0, "success");
        } else {
            return new Result(100, "nothing is deleted");
        }
    }

    @RequestMapping(value="/api/todo", method={RequestMethod.GET})
    public List<TodoVO> getTodoList() {

        return todoMapper.selectTodoList();
    }
}

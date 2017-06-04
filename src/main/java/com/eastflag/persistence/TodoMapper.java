package com.eastflag.persistence;

import com.eastflag.domain.AnswerVO;
import com.eastflag.domain.TodoVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TodoMapper {
    @Insert({"<script>",
            "INSERT INTO todo(todo, created, updated) ",
            "VALUES(#{todo}, now(), now())",
            "</script>"})
    int insertTodo(TodoVO todo);

    @Select({"<script>",
            "SELECT * FROM todo",
            "</script>"})
    List<TodoVO> selectTodoList();

    @Update({"<script>",
            "UPDATE  todo",
            "set todo = #{todo}, updated = now()",
            "where todo_id = #{todo_id}",
            "</script>"})
    int updateTodo(TodoVO todo);

    @Delete({"<script>",
            "DELETE from todo",
            "where todo_id = #{todo_id}",
            "</script>"})
    int deleteTodo(TodoVO todo);
}

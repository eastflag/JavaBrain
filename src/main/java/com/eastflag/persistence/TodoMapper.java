package com.eastflag.persistence;

import com.eastflag.domain.NewsVO;
import com.eastflag.domain.SearchVO;
import com.eastflag.domain.TodoVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TodoMapper {
    @Options(useGeneratedKeys = true, keyProperty = "todo_id")
    @Insert({"<script>",
            "INSERT INTO todo(todo, created, updated) ",
            "VALUES(#{todo}, now(), now())",
            "</script>"})
    int insertTodo(TodoVO todo);

    @Select({"<script>",
            "SELECT * FROM todo",
            "order by todo_id desc",
            "<if test='start_index!=null'>LIMIT #{start_index}, #{page_size}</if>",
            "</script>"})
    List<TodoVO> selectTodoList(SearchVO search);

    @Select({"<script>",
            "SELECT * FROM todo",
            "where todo_id = #{todo_id}",
            "</script>"})
    TodoVO selectTodo(TodoVO todo);

    @Update({"<script>",
            "UPDATE  todo",
            "<trim prefix='set' suffixOverrides=','>",
            "<if test='isFinished!=null'>isFinished = #{isFinished}, </if>",
            "<if test='todo!=null'>todo = #{todo}, </if>",
            "</trim>",
            "where todo_id = #{todo_id}",
            "</script>"})
    int updateTodo(TodoVO todo);

    @Delete({"<script>",
            "DELETE from todo",
            "where todo_id = #{todo_id}",
            "</script>"})
    int deleteTodo(int todo_id);

    //News -------------------------------------
    @Options(useGeneratedKeys = true, keyProperty = "news_id")
    @Insert({"<script>",
            "INSERT INTO news(title, content, created) ",
            "VALUES(#{title}, #{content}, now())",
            "</script>"})
    int insertNews(NewsVO news);

    @Select({"<script>",
            "SELECT * FROM news",
            "order by news_id desc",
            "<if test='start_index!=null'>LIMIT #{start_index}, #{page_size}</if>",
            "</script>"})
    List<NewsVO> selectNewsList(SearchVO search);

    @Select({"<script>",
            "SELECT * FROM news",
            "where news_id = #{news_id}",
            "</script>"})
    NewsVO selectNews(int news_id);

    @Update({"<script>",
            "UPDATE news",
            "<trim prefix='set' suffixOverrides=','>",
            "<if test='title!=null'>title = #{title}, </if>",
            "<if test='content!=null'>content = #{content}, </if>",
            "</trim>",
            "where news_id = #{news_id}",
            "</script>"})
    int updateNews(NewsVO news);

    @Delete({"<script>",
            "DELETE from news",
            "where news_id = #{news_id}",
            "</script>"})
    int deleteNews(int news_id);
}

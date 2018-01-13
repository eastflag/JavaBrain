package com.eastflag.persistence;

import com.eastflag.domain.CommentVO;
import com.eastflag.domain.NewsVO;
import com.eastflag.domain.SearchVO;
import com.eastflag.domain.TodoVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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

    //News -------------------------------------------------------------------------------------------------------------
    @Insert({"<script>",
            "INSERT INTO news(title, content, created) ",
            "VALUES(#{title}, #{content}, now())",
            "</script>"})
    int insertNews(NewsVO news);

    @Select({"<script>",
            "SELECT N.*, (select count(*) from comment where news_id = N.news_id) as comment_count",
            "FROM news N",
            "order by news_id desc",
            "<if test='start_index!=null'>LIMIT #{start_index}, #{page_size}</if>",
            "</script>"})
    List<NewsVO> selectNews(NewsVO news);

    @Select({"<script>",
            "SELECT count(*) FROM news",
            "</script>"})
    int countNews();

    @Select({"<script>",
            "SELECT * FROM news",
            "where news_id = #{news_id}",
            "</script>"})
    NewsVO selectOneNews(int news_id);

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

    //comment  ---------------------------------------------------------------------------------------------------------
    @Select({"<script>",
            "SELECT comment.*, member.nickname FROM comment inner join member on comment.member_id = member.member_id",
            "WHERE news_id = #{news_id}",
            "order by comment_id desc",
            "</script>"})
    List<CommentVO> selectComment(int news_id);

    @Insert({"<script>",
            "INSERT INTO comment(member_id, news_id, content, created) ",
            "VALUES(#{member_id}, #{news_id}, #{content}, now())",
            "</script>"})
    int insertComment(CommentVO comment);

    @Update({"<script>",
            "UPDATE comment set content = #{content}",
            "where comment_id = #{comment_id}",
            "</script>"})
    int updateComment(CommentVO comment);

    @Delete({"<script>",
            "DELETE from comment",
            "where comment_id = #{comment_id}",
            "</script>"})
    int deleteComment(int comment_id);
}

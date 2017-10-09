package com.eastflag.persistence;

import com.eastflag.domain.CommentVO;
import com.eastflag.domain.NewsVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {

    //News -------------------------------------
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
    List<NewsVO> selectNews(NewsVO news);

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
    @Insert({"<script>",
            "INSERT INTO news(title, content, created) ",
            "VALUES(#{title}, #{content}, now())",
            "</script>"})
    int insertComment(CommentVO comment);
}

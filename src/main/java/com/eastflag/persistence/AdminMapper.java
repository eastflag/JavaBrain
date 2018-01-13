package com.eastflag.persistence;

import com.eastflag.domain.AnswerVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminMapper {
    @Select({"<script>",
            "SELECT * FROM answer",
            "WHERE category_id = #{category_id}",
            "ORDER BY seq asc",
            "</script>"})
    List<AnswerVO> selectAnswerList(int category_id);

    @Insert({"<script>",
            "INSERT INTO answer(category_id, seq, question, view, answer, created) ",
            "VALUES(#{category_id}, #{seq}, #{question}, #{view}, #{answer}, now())",
            "</script>"})
    int insertAnswer(AnswerVO answer);

    @Update({"<script>",
            "UPDATE answer",
            "set",
            "category_id=#{category_id}, seq=#{seq}, question=#{question}, view=#{view}, answer=#{answer}",
            "where answer_id = #{answer_id}",
            "</script>"})
    int updateAnswer(AnswerVO answer);

    @Delete({"<script>",
            "DELETE from answer",
            "where answer_id = #{answer_id}",
            "</script>"})
    int deleteAnswer(int answer_id);


}

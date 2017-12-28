package com.eastflag.persistence;

import com.eastflag.domain.AnswerVO;
import com.eastflag.domain.CategoryVO;
import com.eastflag.domain.FeedbackVO;
import com.eastflag.domain.TreeVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by eastflag on 2016-09-20.
 */
@Repository
@Mapper
public interface ApiMapper {

    @Select("SELECT IFNULL(max(answer_id), 0) as max FROM answer")
    int selectAnswerMaxId();

    @Insert({"<script>" +
            "INSERT INTO answer(answer_id, boards_id, member_id, total, created) " +
            "VALUES(#{answer_id}, #{boards_id}, #{member_id}, #{total}, now())" +
            "</script>"})
    int insertAnswer(AnswerVO answer);

    @Update("{<script>" +
            "UPDATE answer " +
            "<trim prefix='set' suffixOverrides=','" +
            "<if sum != -1>sum = #{sum},</if>" +
            "<if checked != -1>checked = #{checked},</if>" +
            "</script>}")
    int updateAnswer(AnswerVO answer);

    @Insert("INSERT INTO feedback(answer_id, num, point, content) VALUES(#{answer_id}, #{num}, #{point}, #{content})")
    int insertFeedback(FeedbackVO feedback);

    @Update("{<script>" +
            "UPDATE feedback " +
            "<trim prefix='set' suffixOverrides=','" +
            "<if score != -1>score = #{score},</if>" +
            "</script>}")
    int updateFeedback(FeedbackVO feedback);

    @Select({"<script>",
            "SELECT p.*, (select count(*) from category c where c.parent_id = p.category_id) as count FROM category p",
            "where parent_id = #{parent_id}",
            "</script>"})
    List<CategoryVO> selectCategoryTree(int category_id);
}

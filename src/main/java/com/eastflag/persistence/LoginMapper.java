package com.eastflag.persistence;

import com.eastflag.domain.AuthVO;
import com.eastflag.domain.MemberLogVO;
import com.eastflag.domain.MemberVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LoginMapper {

/*    @Select({"<script>",
            "SELECT * FROM member WHERE email= #{email}",
            "</script>"})
    MemberVO selectMember(MemberVO member);

    @Select({"<script>",
            "SELECT * FROM auth WHERE member_id= #{member_id}",
            "</script>"})
    List<AuthVO> selectAuth(MemberVO member);

    @Options(useGeneratedKeys = true, keyProperty = "member_id")
    @Insert({"<script>",
            "INSERT INTO member(email, name, phone, photo_url, join_path, created)",
            "VALUES(#{email}, #{name}, #{phone}, #{photo_url}, #{join_path}, now())",
            "</script>"})
    int insertMember(MemberVO member);*/

    @Select({"<script>",
            "SELECT * FROM member",
            "WHERE email= #{email}",
            "<if test='is_used!=null'>and is_used = #{is_used}</if>",
            "</script>"})
    MemberVO selectMember(MemberVO member);

    @Select({"<script>",
            "SELECT * FROM member",
            "WHERE nickname= #{nickname}",
            "and email != #{email}",
            "</script>"})
    MemberVO selectMemberByNickname(MemberVO member);

    @Select({"<script>",
            "SELECT * FROM member",
            "WHERE member_id= #{member_id}",
            "</script>"})
    MemberVO selectMemberById(int member_id);

    @Update({"<script>",
            "UPDATE member",
            "set is_used = #{is_used}",
            "WHERE member_id= #{member_id}",
            "</script>"})
    int updateMember(MemberVO member);

    @Select({"<script>",
            "SELECT * FROM auth WHERE member_id= #{member_id}",
            "</script>"})
    List<AuthVO> selectAuth(MemberVO member);

    @Options(useGeneratedKeys = true, keyProperty = "member_id")
    @Insert({"<script>",
            "INSERT INTO member(email, nickname, photo_url, join_path, created)",
            "VALUES(#{email}, #{nickname}, #{photo_url}, #{join_path}, now())",
            "</script>"})
    int insertMember(MemberVO member);

    @Update({"<script>",
            "UPDATE member",
            "set nickname = #{nickname}",
            "WHERE member_id= #{member_id}",
            "</script>"})
    int updateNickname(MemberVO member);

    @Select({"<script>",
            "Select member_id, nickname FROM member",
            "WHERE member_id= #{member_id}",
            "</script>"})
    MemberVO selectNickname(int member_id);

    @Select({"<script>",
            "Select count(*) FROM member",
            "WHERE nickname = #{nickname} and member_id != #{member_id}",
            "</script>"})
    int checkNickname(MemberVO member);

    @Select({"<script>",
            "Select count(*) FROM member_log",
            "WHERE email = #{email} and type = 'leave'",
            "ORDER BY created desc",
            "LIMIT 1",
            "</script>"})
    int checkMemberLog(String email);

    @Insert({"<script>",
            "INSERT INTO member_log(email, type)",
            "VALUES(#{email}, #{type})",
            "</script>"})
    int insertMemberLog(MemberLogVO memberLog);
}

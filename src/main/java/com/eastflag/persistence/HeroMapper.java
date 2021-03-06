package com.eastflag.persistence;

import com.eastflag.domain.HeroVO;
import com.eastflag.domain.SearchVO;
import com.eastflag.domain.TodoVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HeroMapper {
    @Insert({"<script>",
            "INSERT INTO hero(name, email, sex, country, address, power, photo, created)",
            "VALUES(#{name}, #{email}, #{sex}, #{country}, #{address}, #{power}, #{photo}, now())",
            "</script>"})
    int insertHero(HeroVO hero);

    @Select({"<script>",
            "SELECT * from hero",
            "order by hero_id desc",
            "</script>"})
    List<HeroVO> findHero();

    @Select({"<script>",
            "SELECT * FROM hero",
            "order by hero_id desc",
            "<if test='start_index!=null'>LIMIT #{start_index}, #{page_size}</if>",
            "</script>"})
    List<HeroVO> selectHeroList(SearchVO search);

    @Select({"<script>",
            "SELECT count(*) FROM hero",
            "</script>"})
    int countHero();

    @Select({"<script>",
            "SELECT * from hero",
            "where hero_id = #{hero_id}",
            "</script>"})
    HeroVO findOneHero(int hero_id);

    @Update({"<script>",
            "UPDATE hero",
            "<trim prefix='set' suffixOverrides=','>",
            "<if test='name!=null'>name=#{name},</if>",
            "<if test='email!=null'>email=#{email},</if>",
            "<if test='sex!=null'>sex=#{sex},</if>",
            "<if test='country!=null'>country=#{country},</if>",
            "<if test='address!=null'>address=#{address},</if>",
            "<if test='power!=null'>power=#{power},</if>",
            "<if test='photo!=null'>photo=#{photo},</if>",
            "</trim>",
            "WHERE hero_id = #{hero_id}",
            "</script>"})
    int updateHero(HeroVO hero);

    @Delete({"<script>",
            "DELETE FROM hero",
            "WHERE hero_id = #{hero_id}",
            "</script>"})
    int deleteHero(int hero_id);
}

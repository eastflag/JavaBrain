package com.eastflag.persistence;

import com.eastflag.domain.HeroVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HeroMapper {
    @Insert({"<script>",
            "INSERT INTO hero(name, email, sex, country, address, power, created)",
            "VALUES(#{name}, #{email}, #{sex}, #{country}, #{address}, #{power}, now())",
            "</script>"})
    int insertHero(HeroVO hero);

    @Select({"<script>",
            "SELECT * from hero",
            "order by hero_id desc",
            "</script>"})
    List<HeroVO> findHero();

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
            "<if test='power!=null'>data_value3=#{power},</if>",
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

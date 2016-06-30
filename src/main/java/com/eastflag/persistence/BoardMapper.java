package com.eastflag.persistence;

import com.eastflag.domain.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by eastflag on 2016-06-30.
 */
@Mapper
public interface BoardMapper {
    BoardVO selectBoard(BoardVO board);

    List<BoardVO> selectBoardList(int pageNo);

    int insertBoard(BoardVO board);

    int updateBoard(BoardVO board);

    int deleteBoard(BoardVO board);

    int selectBoardTotal();
}

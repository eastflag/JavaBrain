package com.eastflag.controller;

import java.util.List;

import com.eastflag.domain.BoardVO;
import com.eastflag.persistence.BoardMapper;
import com.eastflag.result.BoardListResult;
import com.eastflag.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//나는 의사다 서비스를 위한 api
@RestController
@RequestMapping(value="/api", method={RequestMethod.GET, RequestMethod.POST})
public class BoardController {
	
	@Autowired
	private BoardMapper mapper;
	
	@RequestMapping(value="/getBoard")
	public Result<BoardVO> getBoard(@RequestBody BoardVO boardVO) {
		Result<BoardVO> result = new Result<>(mapper.selectBoard(boardVO));
		return result;
	}
	
	@RequestMapping(value="/getBoardList")
	public BoardListResult<List<BoardVO>> getBoardList(int pageNo) {
		int seq = pageNo == 1 ? 0 : (pageNo-1) * 20;
		//System.out.println("pageNo:" + boardVO.getPageNo());
		//int seq = boardVO.getPageNo() == 1 ? 0 : (boardVO.getPageNo()-1) * 20;
		//System.out.println("seq:" + seq);
		//boardVO.setPageNo(seq);
		List<BoardVO> boardList = mapper.selectBoardList(seq);
		
		BoardListResult<List<BoardVO>> result = new BoardListResult<List<BoardVO>>(boardList);
		result.setResult(0);
		
		int total = mapper.selectBoardTotal(); //전체 게시판 수
		result.setTotal(total/20 +1); //전체 페이지 갯수
		
		return result;
	}
	
	@RequestMapping(value="/addBoard")
	@ResponseBody 
	public Result<Integer> addBoard(@RequestBody BoardVO boardVO) {
		
		mapper.insertBoard(boardVO);
		System.out.println("title:" + boardVO.getTitle());
		Result<Integer> result = new Result<Integer>(0, 0);
		
		return result;
	}
	
	@RequestMapping(value="/modifyBoard")
	@ResponseBody 
	public Result<Integer> modifyBoard(@RequestBody BoardVO boardVO) {

		Result<Integer> result = new Result<Integer>(mapper.updateBoard(boardVO));
		result.setResult(0);
		return result;
	}
	
	@RequestMapping(value="/removeBoard")
	@ResponseBody 
	public Result<Integer> removeBoard(@RequestBody BoardVO boardVO) {
		
		Result<Integer> result = new Result<Integer>(mapper.deleteBoard(boardVO));
		result.setResult(0);
		return result;
	}
}

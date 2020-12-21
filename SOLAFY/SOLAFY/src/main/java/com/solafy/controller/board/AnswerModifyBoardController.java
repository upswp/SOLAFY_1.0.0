package com.solafy.controller.board;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solafy.model.AnswerModifyBoardDto;
import com.solafy.service.board.AnswerModifyBoardService;

import io.swagger.annotations.ApiOperation;



@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/answermodify") //주소는 알아서 넣기

public class AnswerModifyBoardController {
	private static final Logger logger = LoggerFactory.getLogger(AnswerModifyBoardController.class);
	//아래 string 2개는 사용하지 않는다면 지우기
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	//@Autowired
	//private ___Service ___Service;
	
//	@ApiOperation(value = "~~~~를 반환한다", response = List.class)
//	@GetMapping(value = "/판단해서 필요하면 value 넣기")
//	public ResponseEntity<List<DTO 넣어주기>> retrieveBoard() throws Exception {
//		logger.debug("retrieveBoard - 호출");
//		return new ResponseEntity<List<DTO 넣어주기>>>(boardService.retrieveBoard(), HttpStatus.OK);
//	}
	@Autowired
	private AnswerModifyBoardService answerModifyBoardService;	
	
	@ApiOperation(value = "답안수정 게시판에 게시글을 등록한다.", response = List.class)
	@PostMapping(value = "/createArticle")
	public ResponseEntity<String> createArticle(@RequestBody AnswerModifyBoardDto answerModifyBoardDto){
		logger.info("createArticle - 호출" + new Date());
		if(answerModifyBoardService.createArticle(answerModifyBoardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {			
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	

	@ApiOperation(value = "답안수정 게시판의 모든 게시글을 반환한다.", response = List.class)
	@GetMapping(value="/selectArticles")
	public List<AnswerModifyBoardDto> selectArticles(){
		logger.info("selectArtilces - 호출" + new Date());
		List<AnswerModifyBoardDto> list = answerModifyBoardService.selectArticles();
		return list;
	}
	

	@ApiOperation(value = "답안수정 게시판의 게시글을 수정한다", response = List.class)
	@PostMapping(value="/updateArticle")
	public ResponseEntity<String> updateArticle(@RequestBody AnswerModifyBoardDto answerModifyBoardDto){
		logger.info("updateArticle - 호출" + new Date());
		if(answerModifyBoardService.updateArticle(answerModifyBoardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			// TODO: responseEntity의 의미
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_MODIFIED);
		}
		
	}
	

	@ApiOperation(value = "답안수정 게시판의 게시글을 삭제한다", response = List.class)
	@PostMapping(value = "/deleteArticle/{articleNo}")
	public ResponseEntity<String> deleteArticle(@PathVariable int articleNo){
		logger.info("deleteArticle - 호출" + new Date());
		if(answerModifyBoardService.deleteArticle(articleNo)){
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_MODIFIED);
		}
	}
	
}

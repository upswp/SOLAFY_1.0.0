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

/**
* @FileName : AnswerModifyBoardController.java
* @Project : SOLAFY
* @Date : 2020. 12. 22
* @작성자 : BUMSEOK SEO

* @변경이력 :
* @프로그램 설명 : 답안수정 게시판 컨트롤러
*/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/answermodify")

public class AnswerModifyBoardController {
	private static final Logger logger = LoggerFactory.getLogger(AnswerModifyBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private AnswerModifyBoardService answerModifyBoardService;	
	
	/**
	* @param answerModifyBoardDto
	* @return 성공여부에 따른 반환값(SUCCESS, FAIL)
	* @Method 설명 : 게시글 등록
	* @변경이력 :
	*/
	@ApiOperation(value = "답안수정 게시판에 게시글을 등록한다.", response = String.class)
	@PostMapping(value = "/createArticle")
	public ResponseEntity<String> createArticle(@RequestBody AnswerModifyBoardDto answerModifyBoardDto){
		logger.info("createArticle - 호출" + new Date());
		if(answerModifyBoardService.createArticle(answerModifyBoardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {			
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	* @return List<AnswerModifyBoardDto> 답안수정 게시판의 모든 게시글 반환
	* 	AnswerModifyBoardDto엔
	* 	articleNo, uid, problemNo, title, regiTime의 정보를 가지고 있다.
	* @Method 설명 : 모든 게시글을 반환
	* @변경이력 :
	*/
	@ApiOperation(value = "답안수정 게시판의 모든 게시글을 반환한다.", response = List.class)
	@GetMapping(value="/selectArticles")
	public ResponseEntity<List<AnswerModifyBoardDto>> selectArticles(){
		logger.info("selectArtilces - 호출" + new Date());
		return new ResponseEntity<List<AnswerModifyBoardDto>>(answerModifyBoardService.selectArticles(),HttpStatus.OK);
	}
	
	/**
	* @param answerModifyBoardDto 
	* 	articleNo, title, contents의 정보를 가지고 있다.
	* @return 성공여부에 따른 반환값(SUCCESS, FAIL)
	* @Method 설명 : 게시글 수정
	* @변경이력 :
	*/
	@ApiOperation(value = "답안수정 게시판의 게시글을 수정한다", response = String.class)
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
	
	/**
	* @param articleNo 삭제할 게시글의 게시글번호
	* @return 성공여부에 따른 반환값(SUCCESS, FAIL)
	* @Method 설명 : 게시글 삭제
	* @변경이력 :
	*/
	@ApiOperation(value = "답안수정 게시판의 게시글을 삭제한다", response = String.class)
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

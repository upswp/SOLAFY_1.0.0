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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solafy.model.AnswerModifyReplyDto;
import com.solafy.service.board.AnswerModifyReplyService;

import io.swagger.annotations.ApiOperation;

/**
* @FileName : AnswerModifyReplyController.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : BUMSEOK SEO

* @변경이력 :
* @프로그램 설명 : 답안 수정게시판 댓글을 위한 Controller
*/
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/template") //주소는 알아서 넣기

public class AnswerModifyReplyController {
	private static final Logger logger = LoggerFactory.getLogger(AnswerModifyReplyController.class);
	// TODO: 아래 string 2개는 사용하지 않는다면 지우기
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private AnswerModifyReplyService answerModifyReplyService;
	
	/**
	* @param answerModifyReplyDto
	* @return ResponseEntity<String>
	* @Method 설명 : 댓글을 등록한다.
	* @변경이력 : 
	*/
	@ApiOperation(value = "댓글을 등록한다", response = List.class)
	@PostMapping(value = "/createReply")
	public ResponseEntity<String> createReply(@RequestBody AnswerModifyReplyDto answerModifyReplyDto){
		logger.info("createReply - 호출" + new Date());
		if(answerModifyReplyService.createReply(answerModifyReplyDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	* @param articleNo
	* @return List<AnswerModifyReplyDto>
	* @Method 설명 : 해당 게시글의 모든 댓글을 반환한다.
	* @변경이력 :
	*/
	@ApiOperation(value = "모든 댓글을 반환한다", response = List.class)
	@GetMapping(value = "/selectReplies/{articleNo}")
	public List<AnswerModifyReplyDto> selectReplies(int articleNo){
		logger.info("selectReplies - 호출" + new Date());
		List<AnswerModifyReplyDto> list = answerModifyReplyService.selectReplies(articleNo);
		return list;
	}
	
	/**
	* @param answerModifyReplyDto (수정한 내용이 들어있는 Dto)
	* @return 성공여부에 따른 ResponseEntity
	* @Method 설명 : 해당 댓글을 수정한다
	* @변경이력 :
	*/
	@ApiOperation(value = "댓글을 수정한다", response = List.class)
	@PostMapping(value = "/updateReply")
	public ResponseEntity<String> updateReply(@RequestBody AnswerModifyReplyDto answerModifyReplyDto){
		logger.info("updateReply - 호출" + new Date());
		if(answerModifyReplyService.updateReply(answerModifyReplyDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	* @param answerModifyReplyDto (삭제할 게시글번호와 댓글번호가 들어간 Dto)
	* @return 성공여부에 따른 ResponseEntity
	* @Method 설명 : 댓글을 삭제한다
	* @변경이력 :
	*/
	@ApiOperation(value = "댓글을 삭제한다", response = List.class)
	@PostMapping(value = "/delteReply")
	public ResponseEntity<String> deleteReply(@RequestBody AnswerModifyReplyDto answerModifyReplyDto){
		logger.info("deleteReply - 호출" + new Date());
		if(answerModifyReplyService.deleteReply(answerModifyReplyDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
}

package com.solafy.controller.board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solafy.model.AnswerByQuestionDto;
import com.solafy.service.board.QuestionReplyService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @FileName QuestionBoardController.java
 * @Project SOLAFY
 * @Date 2021. 01. 02.
 * @Author 이주희
 * 
 * @변경이력 :
 * 21-01-02 이주희 board와 reply의 controller 분리 + mapping value와 method 이름 수정
 */

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/questionreply")

public class QuestionReplyController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionReplyController.class);
	//아래 string 2개는 사용하지 않는다면 지우기
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private QuestionReplyService answerService;
	
	/**
	 * 
	 * @param aDto - AnswerByQuestionDto
	 * @return String
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "질문 글에 새로운 답변 글을 등록한다.", response = String.class)
	@PostMapping(value = "/createReply")
	public ResponseEntity<String> createReply(@RequestBody AnswerByQuestionDto aDto) throws Exception {
		logger.debug("[questionboard] createReply 호출");
		if (answerService.createAnswer(aDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * 
	 * @param articleNo - int, 질문글번호글 번호
	 * @return AnswerByQuestionDto
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "질문 글 번호로 검색하여 해당 질문의 모든 답변을 반환한다.", response = List.class)
	@GetMapping(value = "/selectReplies/{articleNo}")
	public ResponseEntity<List<AnswerByQuestionDto>> selectReplies(@PathVariable int articleNo) throws Exception {
		logger.debug("selectReplies");
		return new ResponseEntity<List<AnswerByQuestionDto>>(answerService.selectAnswersByQuestionNo(articleNo), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param articleNo - int, 답변글 번호
	 * @return AnswerByQuestionDto
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "답변 글 번호로 검색하여 답변 글 상세 정보를 반환한다.", response = AnswerByQuestionDto.class)
	@GetMapping(value = "/selectReplyByArticleNo/{articleNo}")
	public ResponseEntity<AnswerByQuestionDto> selectAnswerByArticleNo(@PathVariable int articleNo) throws Exception {
		logger.debug("selectAnswerByArticleNo");
		return new ResponseEntity<AnswerByQuestionDto>(answerService.selectAnswerByArticleNo(articleNo), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param aDto - AnswerByQuestionDto - 내용, 등록시간만 수정
	 * @return String
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "답변 글을 수정하고 결과를 반환한다.", response = String.class)
	@PutMapping(value = "/updateReply")
	public ResponseEntity<String> updateReply(@RequestBody AnswerByQuestionDto aDto) throws Exception {
		logger.debug("updateReply");
		if (answerService.updateAnswer(aDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * 
	 * @param articleNo - int, 답변 글 번호
	 * @return String
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "답변 글을 삭제하고 결과를 반환한다.", response = String.class)
	@DeleteMapping(value = "/deleteReply/{articleNo}")
	public ResponseEntity<String> deleteReply(@PathVariable int articleNo) throws Exception {
		logger.debug("delteReply");
		if (answerService.deleteAnswer(articleNo))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}

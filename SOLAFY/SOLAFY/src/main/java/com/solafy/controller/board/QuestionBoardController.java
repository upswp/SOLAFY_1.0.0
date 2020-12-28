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
import com.solafy.model.QuestionBoardDto;
import com.solafy.service.board.QuestionBoardService;
import com.solafy.service.board.QuestionReplyService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @FileName QuestionBoardController.java
 * @Project SOLAFY
 * @Date 2020. 12. 18.
 * @Author 이주희
 * 
 * @변경이력 :
 */

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/question") // 주소는 알아서 넣기

public class QuestionBoardController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private QuestionBoardService questionService;

	@Autowired
	private QuestionReplyService answerService;

	/**
	 * 
	 * @param qDto - QuestionBoardDto
	 * @return String
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "새로운 질문 글을 등록한다.", response = String.class)
	@PostMapping(value = "/create")
	public ResponseEntity<String> createQuestion(@RequestBody QuestionBoardDto qDto) throws Exception {
		logger.debug("createQuestion");
		if (questionService.createQuestion(qDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	/**
	 * 
	 * @param aDto - AnswerByQuestionDto
	 * @return String
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "질문 글에 새로운 답변 글을 등록한다.", response = String.class)
	@PostMapping(value = "/createanswer")
	public ResponseEntity<String> createAnswer(@RequestBody AnswerByQuestionDto aDto) throws Exception {
		logger.debug("createAnswer");
		if (answerService.createAnswer(aDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	/**
	 * 
	 * @return List<QuestionBoardDto>
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "모든 질문 글 목록을 반환한다.", response = List.class)
	@GetMapping(value = "/selectall")
	public ResponseEntity<List<QuestionBoardDto>> selectAllQuestions() throws Exception {
		logger.debug("selectAllQuestions");
		return new ResponseEntity<List<QuestionBoardDto>>(questionService.selectAllQuestions(), HttpStatus.OK);
	}

	/**
	 * 
	 * @param articleNo - int, 질문 글의 번호
	 * @return QuestionBoardDto
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "글 번호에 해당하는 질문 글의 상세 정보를 반환한다.", response = QuestionBoardDto.class)
	@GetMapping(value = "/selectbyno/{articleNo}")
	public ResponseEntity<QuestionBoardDto> selectQuestionsByArticleNo(@PathVariable int articleNo) throws Exception {
		logger.debug("selectQuestionsByArticleNo");
		return new ResponseEntity<QuestionBoardDto>(questionService.selectQuestionsByArticleNo(articleNo),
				HttpStatus.OK);
	}

	/**
	 * 
	 * @param title - String, 검색 키워드
	 * @return List<QuestionBoardDto>
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "질문 글의 제목에 검색어가 포함된 질문 글 목록을 반환한다.", response = List.class)
	@GetMapping(value = "/selectbytitle/{title}")
	public ResponseEntity<List<QuestionBoardDto>> selectQuestionsByTitle(@PathVariable String title) throws Exception {
		logger.debug("selectQuestionsByTitle");
		return new ResponseEntity<List<QuestionBoardDto>>(questionService.selectQuestionsByTitle(title), HttpStatus.OK);
	}

	/**
	 * 
	 * @param uid - String, nickname
	 * @return List<QuestionBoardDto>
	 * @throws Exception
	 *
	 * @변경이력 
	 * 20-12-28 uid 검색에서 별명 검색으로 변경
	 */
	@ApiOperation(value = "작성자의 별명으로 검색한 결과를 반환한다.", response = List.class)
	@GetMapping(value = "/selectbywriter/{nickname}")
	public ResponseEntity<List<QuestionBoardDto>> selectQuestionsByWriter(@PathVariable String nickname)
			throws Exception {
		logger.debug("selectQuestionsByWriter");
		return new ResponseEntity<List<QuestionBoardDto>>(questionService.selectQuestionsByWriter(nickname),
				HttpStatus.OK);
	}

	/**
	 * 
	 * @param problemNo - int, 문제 번호
	 * @return List<QuestionBoardDto>
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "문제 번호로 검색한 결과를 반환한다.", response = List.class)
	@GetMapping(value = "/selectbypno/{problemNo}")
	public ResponseEntity<List<QuestionBoardDto>> selectQuestionsByProblemNo(@PathVariable int problemNo)
			throws Exception {
		logger.debug("selectQuestionsByProblemNo");
		return new ResponseEntity<List<QuestionBoardDto>>(questionService.selectQuestionsByProblemNo(problemNo),
				HttpStatus.OK);
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
	@GetMapping(value = "/selectanswer/{articleNo}")
	public ResponseEntity<AnswerByQuestionDto> selectAnswerByArticleNo(@PathVariable int articleNo) throws Exception {
		logger.debug("selectAnswerByArticleNo");
		return new ResponseEntity<AnswerByQuestionDto>(answerService.selectAnswerByArticleNo(articleNo), HttpStatus.OK);
	}

	/**
	 * 
	 * @param qDto - QuestionBoardDto, 수정할 내용 포함
	 * @return String
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "질문 글을 수정하고 결과를 반환한다.", response = String.class)
	@PutMapping(value = "/update")
	public ResponseEntity<String> updateQuestion(@RequestBody QuestionBoardDto qDto) throws Exception {
		logger.debug("updateQuestion");
		if (questionService.updateQuestion(qDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
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
	@PutMapping(value = "/updateanswer")
	public ResponseEntity<String> updateAnswer(@RequestBody AnswerByQuestionDto aDto) throws Exception {
		logger.debug("updateAnswer");
		if (answerService.updateAnswer(aDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	/**
	 * 
	 * @param articleNo - int, 질문 글 번호
	 * @return String
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "질문 글을 삭제하고 결과를 반환한다.", response = String.class)
	@DeleteMapping(value = "/delete/{articleNo}")
	public ResponseEntity<String> deleteQuestion(@PathVariable int articleNo) throws Exception {
		logger.debug("deleteQuestion");
		if (questionService.deleteQuestion(articleNo))
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
	@DeleteMapping(value = "/deleteanswer/{articleNo}")
	public ResponseEntity<String> deleteAnswer(@PathVariable int articleNo) throws Exception {
		logger.debug("deleteAnswer");
		if (answerService.deleteAnswer(articleNo))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}

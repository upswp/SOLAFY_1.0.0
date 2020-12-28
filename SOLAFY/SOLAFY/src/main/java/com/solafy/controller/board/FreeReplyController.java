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

import com.solafy.model.FreeReplyDto;
import com.solafy.service.board.FreeReplyService;

import io.swagger.annotations.ApiOperation;

/**
* @FileName : FreeReplyController.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : BUMSEOK SEO

* @변경이력 :
* @프로그램 설명 : 자유게시판 댓글을 위한 Controller
*/
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/freereply") //주소는 알아서 넣기
public class FreeReplyController {
	private static final Logger logger = LoggerFactory.getLogger(FreeReplyController.class);
	//아래 string 2개는 사용하지 않는다면 지우기
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private FreeReplyService freeReplyService;
	
	//@Autowired
	//private ___Service ___Service;
	
//	@ApiOperation(value = "~~~~를 반환한다", response = List.class)
//	@GetMapping(value = "/판단해서 필요하면 value 넣기")
//	public ResponseEntity<List<DTO 넣어주기>> retrieveBoard() throws Exception {
//		logger.debug("retrieveBoard - 호출");
//		return new ResponseEntity<List<DTO 넣어주기>>>(boardService.retrieveBoard(), HttpStatus.OK);
//	}
	
	/**
	* @param freeReplyDto
	* @return ResponseEntity<String>
	* @Method 설명 : 댓글을 등록한다.
	* @변경이력 : 
	*/
	@ApiOperation(value = "댓글을 등록한다", response = List.class)
	@PostMapping(value = "/createReply")
	public ResponseEntity<String> createReply(@RequestBody FreeReplyDto freeReplyDto){
		logger.info("createReply - 호출" + new Date());
		if(freeReplyService.createReply(freeReplyDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	* @param articleNo
	* @return List<FreeReplyDto>
	* @Method 설명 : 모든 댓글을 반환한다.
	* @변경이력 :
	*/
	@ApiOperation(value = "모든 댓글을 반환한다", response = List.class)
	@GetMapping(value = "/selectReplies/{articleNo}")
	public List<FreeReplyDto> selectReplies(@PathVariable int articleNo){
		logger.info("selectReplies - 호출" + new Date());
		List<FreeReplyDto> list = freeReplyService.selectReplies(articleNo);
		return list;
	}
	
	/**
	* @param freeReplyDto (수정한 내용이 들어있는 Dto)
	* @return 성공여부에 따른 ResponseEntity
	* @Method 설명 : 댓글을 수정한다
	* @변경이력 :
	*/
	@ApiOperation(value = "댓글을 수정한다", response = List.class)
	@PostMapping(value = "/updateReply")
	public ResponseEntity<String> updateReply(@RequestBody FreeReplyDto freeReplyDto){
		logger.info("updateReply - 호출" + new Date());
		if(freeReplyService.updateReply(freeReplyDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	* @param freeReplyDto (삭제할 게시글번호와 댓글번호가 들어간 Dto)
	* @return 성공여부에 따른 ResponseEntity
	* @Method 설명 : 댓글을 삭제한다
	* @변경이력 :
	*/
	@ApiOperation(value = "댓글을 삭제한다", response = List.class)
	@PostMapping(value = "/deleteReply")
	public ResponseEntity<String> deleteReply(@RequestBody FreeReplyDto freeReplyDto){
		logger.info("deleteReply - 호출" + new Date());
		if(freeReplyService.deleteReply(freeReplyDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
}

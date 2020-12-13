package com.solafy.controller.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/template") //주소는 알아서 넣기

public class QuestionReplyController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionReplyController.class);
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
}

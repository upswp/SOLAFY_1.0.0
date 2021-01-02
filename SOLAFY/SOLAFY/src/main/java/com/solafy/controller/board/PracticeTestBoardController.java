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

import com.solafy.model.PracticeTestBoardDto;
import com.solafy.service.board.PracticeTestBoardService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @FileName PracticeTestBoardController.java
 * @Project SOLAFY
 * @Date 2020. 12. 18.
 * @Author 이주희
 * 
 * @변경이력
 * 20-01-02 articleNo에서 practiceNo로 수정
 */

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/practicetest")

public class PracticeTestBoardController {
	private static final Logger logger = LoggerFactory.getLogger(PracticeTestBoardController.class);

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private PracticeTestBoardService practiceService;

	/**
	 * 
	 * @param pDto - PracticeTestBoardDto
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "새로운 모의고사를 생성하고 생성의 결과를 반환한다.", response = String.class)
	@PostMapping(value = "/create")
	public ResponseEntity<String> createPracticeTest(@RequestBody PracticeTestBoardDto pDto) throws Exception {
		logger.debug("createPracticeTest");
		if (practiceService.createPracticeTest(pDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "모든 모의고사 목록을 반환한다.", response = List.class)
	@GetMapping(value = "/selectall")
	public ResponseEntity<List<PracticeTestBoardDto>> selectAllPracticeTest() throws Exception {
		logger.debug("selectAllPracticeTest");
		return new ResponseEntity<List<PracticeTestBoardDto>>(practiceService.selectAllPracticeTest(), HttpStatus.OK);
	}

	/**
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 * 20-01-02 반환 값을 PracticeTestBoardDto에서 List<PracticeTestBoardDto>로 수정
	 */
	@ApiOperation(value = "모의고사 번호로 검색한 결과를 목록으로 반환한다.", response = List.class)
	@GetMapping(value = "/selectbyno/{practiceNo}")
	public ResponseEntity<List<PracticeTestBoardDto>> selectPracticeTestByPracticeNo(@PathVariable int practiceNo)
			throws Exception {
		logger.debug("selectPracticeTestByPracticeNo");
		return new ResponseEntity<List<PracticeTestBoardDto>>(practiceService.selectPracticeTestByPracticeNo(practiceNo),
				HttpStatus.OK);
	}

	/**
	 * 
	 * @param nickname - String, 검색된 nickname
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 * 20-12-28 uid 검색에서 별명 검색으로 변경
	 */
	@ApiOperation(value = "모의고사 작성자의 별명으로 검색한 결과를 반환한다.", response = List.class)
	@GetMapping(value = "/selectbywriter/{nickname}")
	public ResponseEntity<List<PracticeTestBoardDto>> selectPracticeTestByWriter(@PathVariable String nickname)
			throws Exception {
		logger.debug("selectPracticeTestByWriter");
		return new ResponseEntity<List<PracticeTestBoardDto>>(practiceService.selectPracticeTestByWriter(nickname),
				HttpStatus.OK);
	}

	/**
	 * 
	 * @param title - String, 검색된 제목 키워드
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "검색된 키워드가 모의고사 제목에 포함된 결과를 반환한다.", response = List.class)
	@GetMapping(value = "/selectbytitle/{title}")
	public ResponseEntity<List<PracticeTestBoardDto>> selectPracticeTestByTitle(@PathVariable String title)
			throws Exception {
		logger.debug("selectPracticeTestByTitle");
		return new ResponseEntity<List<PracticeTestBoardDto>>(practiceService.selectPracticeTestByTitle(title),
				HttpStatus.OK);
	}

	/**
	 * 
	 * @param pDto - PracticeTestBoardDto - 제목, 등록,시작,종료,제한시간, 문제목록 변경
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "모의고사 정보를 수정한다.", response = String.class)
	@PutMapping(value = "/update")
	public ResponseEntity<String> updatePracticeTest(@RequestBody PracticeTestBoardDto pDto) throws Exception {
		logger.debug("updatePracticeTest");
		if (practiceService.updatePracticeTest(pDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	/**
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "모의고사를 삭제한다.", response = String.class)
	@DeleteMapping(value = "/delete/{practiceNo}")
	public ResponseEntity<String> deleltePracticeTest(@PathVariable int practiceNo) throws Exception {
		logger.debug("updatePracticeTest");
		if (practiceService.deleltePracticeTest(practiceNo))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}

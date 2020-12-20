 package com.solafy.controller.problem;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.solafy.model.ProblemSetDto;
import com.solafy.service.problem.ProblemSetService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/problem/problemset") 
/**
 * 문제집 관련 컨트롤러
 * problemSet Controller
 * @author Park Sangwoo
 * @since 2020-12-14
 */
public class ProblemSetController {
	private static final Logger logger = LoggerFactory.getLogger(ProblemSetController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ProblemSetService problemsetService;

	/**
	 * 모든 문제집 정보 반환
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "모든 문제집의 정보를 반환한다.", response = List.class)
	@GetMapping("/problemSetSelect")
	public ResponseEntity<List<ProblemSetDto>> selectAllProblemSet() throws Exception{
		logger.debug("selectAllProblemSet -- 호출");
		return new ResponseEntity<List<ProblemSetDto>>(problemsetService.selectAllProblemSet(), HttpStatus.OK);
	}
	
	/**
	 * 지정한 문제집 No에 따른 문제집 정보 반환
	 * @param problemSetNo 지정한 문제집 번호
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "문제집번호에 해당하는 문제집 정보를 반환한다.", response = ProblemSetDto.class)
	@GetMapping("/problemSetSelect/{problemSetNo}")
	public ResponseEntity<Map<String,Object>> selectProblemByNo(@PathVariable int problemSetNo) throws Exception{
		logger.debug("selectProblemByNo -- 호출");
		return new ResponseEntity<Map<String,Object>> (problemsetService.selectProblemByNo(problemSetNo), HttpStatus.OK);
	}
	
	/**
	 * 새롭게 생성하고자 하는 문제집 등록
	 * @param problemSet 새롭게 만들고자 하는 문제집 번호
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "새로운 문제집 정보를 입력한다. 그리고 DB 입력 성공 여부에 따라 'success' 또는 'fail' 반환한다.")
	@PostMapping("/createProblemSet")
	public ResponseEntity<String> createProblemSet(@RequestBody ProblemSetDto problemSet) throws Exception{
		logger.debug("createProblemSet -- 호출");
		if (problemsetService.createProblemSet(problemSet)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * 수정하고자 하는 문제집 수정
	 * @param problemSet 수정하고자 하는 문제집 번호
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "문제집 번호에 해당하는 문제집 정보를 수정한다. 그리고 DB 수정 성공 여부에 따라 'success' 또는 'fail'을 반환한다.")
	@PutMapping("/updateProblemSet/{problemSetNo}")
	public ResponseEntity<String> updateProblemSet(@RequestBody ProblemSetDto problemSet) throws Exception{
		logger.debug("updateProblemSet -- 호출");
		if (problemsetService.updateProblemSet(problemSet)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * 삭제하고자 하는 문제집 삭제
	 * @param problemSetNo 삭제하고자 하는 문제집 번호
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "문제집 번호에 해당하는 문제집 정보를 삭제한다. 그리고 DB 삭제 성공 여부에 따라 'success' 또는 'fail'을 반환한다.")
	@DeleteMapping("/deleteProblemSet/{problemSetNo}")
	public ResponseEntity<String> deleteProblemSet(@PathVariable int problemSetNo) throws Exception{
		logger.debug("deleteProblemSet -- 호출");
		if(problemsetService.deleteProblemSet(problemSetNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
}

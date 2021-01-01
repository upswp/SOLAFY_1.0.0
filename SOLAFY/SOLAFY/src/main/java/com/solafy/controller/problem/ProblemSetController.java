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
 * 
* @FileName : ProblemSetController.java
* @Project : SOLAFY
* @Date : 2020. 12. 22
* @작성자 : Park Sangwoo

* @변경이력 :
* @프로그램 설명 : 문제집 관련 컨트롤러
 */
public class ProblemSetController {
	private static final Logger logger = LoggerFactory.getLogger(ProblemSetController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ProblemSetService problemsetService;

	/**
	 * 
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @throws Exception
	* @Method 설명 : 모든 문제집 정보 반환
	* @변경이력 :
	 */
	@ApiOperation(value = "모든 문제집의 정보를 반환한다.", response = List.class)
	@GetMapping(value = "/problemSetSelect")
	public ResponseEntity<List<ProblemSetDto>> selectAllProblemSet() throws Exception{
		logger.debug("selectAllProblemSet -- 호출");
		return new ResponseEntity<List<ProblemSetDto>>(problemsetService.selectAllProblemSet(), HttpStatus.OK);
	}
	
	/**
	 * 
	* @param problemSetNo 지정한 문제집 번호
	* @return ProblemSetDto의 - 번호, 작성자, 제목, 등록시간, 문제 리스트 포함
	* @throws Exception
	* @Method 설명 : 지정한 문제집 No에 따른 문제집 정보 반환
	* @변경이력 :
	 */
	@ApiOperation(value = "문제집 번호에 해당하는 문제집 정보를 반환한다.")
	@GetMapping(value = "/problemSetSelectByNo/{problemSetNo}")
	public ResponseEntity<Map<String,Object>> selectProblemByNo(@PathVariable int problemSetNo) throws Exception{
		logger.debug("selectProblemByNo -- 호출");
		return new ResponseEntity<Map<String,Object>> (problemsetService.selectProblemByNo(problemSetNo), HttpStatus.OK);
	}
	
	/**
	 * 
	* @param uid - String, 검색된 uid
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @throws Exception
	* @Method 설명 : 문제집 작성자에 해당하는 문제집을 가져온다.
	* @변경이력 :
	 */
	@ApiOperation(value = "문제집 작성자에 해당하는 문제집 정보를 반환한다.",response = List.class)
	@GetMapping(value = "/problemSetSelectByWriter/{nickname}")
	public ResponseEntity<List<ProblemSetDto>> selectProblemByWriter(@PathVariable String nickname) throws Exception{
		logger.debug("selectProblemByWriter -- 호출");
		return new ResponseEntity<List<ProblemSetDto>>(problemsetService.selectProblemByWriter(nickname), HttpStatus.OK);
	}
	
	/**
	 * 
	* @param title - String, 검색된 제목 키워드
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @throws Exception
	* @Method 설명 : 문제집 키워드가 포함이된 문제집을 가져온다.
	* @변경이력 :
	 */
	@ApiOperation(value = "문제집 제목 키워드에 해당하는 문제집 정보를 반환한다.",response = List.class)
	@GetMapping(value = "/problemSetSelectByTitle/{title}")
	public ResponseEntity<List<ProblemSetDto>> selectProblemByTitle(@PathVariable String title) throws Exception{
		logger.debug("selectProblemByTitle -- 호출");
		return new ResponseEntity<List<ProblemSetDto>>(problemsetService.selectProblemByTitle(title), HttpStatus.OK);
	}
	
	/**
	 * 
	* @param problemSet 새롭게 만들고자 하는 문제집 번호
	* @return int, problemSetNo
	* @throws Exception
	* @Method 설명 : 새롭게 생성하고자 하는 문제집 등록
	* @변경이력 : 2021.01.02 - createProblemSet의 반환형을 String(성공여부)에서 int(problemSetNo)로 변경
	 */
	@ApiOperation(value = "새로운 문제집 정보를 입력한다. 그리고 DB 입력 성공 여부에 따라 'success' 또는 'fail' 반환한다." , response = String.class)
	@PostMapping(value = "/createProblemSet")
	public ResponseEntity<Integer> createProblemSet(@RequestBody ProblemSetDto problemSet) throws Exception{
		logger.debug("createProblemSet -- 호출");
		return new ResponseEntity<Integer>(problemsetService.createProblemSet(problemSet), HttpStatus.OK);
	}
	
	/**
	 * 
	* @param problemSet 수정하고자 하는 문제집 번호
	* @return boolean, 정상적으로 수정 시 SUCCESS 반환
	* @throws Exception
	* @Method 설명 : 수정하고자 하는 문제집 수정
	* @변경이력 :
	 */
	@ApiOperation(value = "문제집 번호에 해당하는 문제집 정보를 수정한다. 그리고 DB 수정 성공 여부에 따라 'success' 또는 'fail'을 반환한다." , response = String.class)
	@PutMapping(value = "/updateProblemSet/{problemSetNo}")
	public ResponseEntity<String> updateProblemSet(@RequestBody ProblemSetDto problemSet) throws Exception{
		logger.debug("updateProblemSet -- 호출");
		if (problemsetService.updateProblemSet(problemSet)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * 
	* @param problemSetNo 삭제하고자 하는 문제집 번호
	* @return boolean, 정상적으로 삭제 시 SUCCESS 반환
	* @throws Exception
	* @Method 설명 : 삭제하고자 하는 문제집 삭제
	* @변경이력 :
	 */
	@ApiOperation(value = "문제집 번호에 해당하는 문제집 정보를 삭제한다. 그리고 DB 삭제 성공 여부에 따라 'success' 또는 'fail'을 반환한다." , response = String.class)
	@DeleteMapping(value = "/deleteProblemSet/{problemSetNo}")
	public ResponseEntity<String> deleteProblemSet(@PathVariable int problemSetNo) throws Exception{
		logger.debug("deleteProblemSet -- 호출");
		if(problemsetService.deleteProblemSet(problemSetNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	//TODO : <@PathVariable> 상태이면 주소창에 uid 노출이 되는지 확인하기.
	
	@ApiOperation(value = "문제집 작성시 임시로 저장된 문제집과 문제의 flag값을 수정한다. 그리고 DB 수정 성공 여부에 따라 'success' 또는 'fail'을 반환한다." , response = String.class)
	@PutMapping(value = "/updatePrblemSetFlag/{uid}")
	public ResponseEntity<String> updatePrblemSetFlag(@PathVariable String uid) throws Exception{
		logger.debug("updatePrblemSetFlag -- 호출");
		if (problemsetService.updatePrblemSetFlag(uid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "문제집 작성시 임시로 저장된 문제집과 문제의 flag값이 false(0)인 상태인 문제집과 문제를 삭제한다. 그리고 DB 삭제 성공 여부에 따라 'success' 또는 'fail'을 반환한다." , response = String.class)
	@DeleteMapping(value = "/deleteProblemSetFlag/{uid}")
	public ResponseEntity<String> deleteProblemSetFlag(@PathVariable String uid) throws Exception{
		logger.debug("deleteProblemSetFlag -- 호출");
		if(problemsetService.deleteProblemSetFlag(uid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
}

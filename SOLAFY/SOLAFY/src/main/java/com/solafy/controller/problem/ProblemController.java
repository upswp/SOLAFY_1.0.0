package com.solafy.controller.problem;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RestController;

import com.solafy.model.HashTagDto;
import com.solafy.model.ProblemAnswerDto;
import com.solafy.model.ProblemDto;
import com.solafy.service.problem.ProblemService;

import io.swagger.annotations.ApiOperation;

/**
* @FileName : ProblemController.java
* @Project : SOLAFY
* @Date : 2020. 12. 18.
* @작성자 : Lee Ayoung

* @변경이력 :
* @프로그램 설명 : Problem Controller
*/
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/problem")
public class ProblemController {
	private static final Logger logger = LoggerFactory.getLogger(ProblemController.class);

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ProblemService problemService;
	
	/**
	* @return ResponseEntity<List<ProblemDto>> - 문제리스트, 응답형태
	* @throws Exception
	* @Method 설명 : 모든 문제 리스트를 반환
	* @변경이력 :
	*/
	@ApiOperation(value="모든 문제 리스트를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<ProblemDto>> selectProblemList() throws Exception{
		logger.debug("selectProblemList - 호출");
		return new ResponseEntity<List<ProblemDto>>(problemService.selectProblemList(), HttpStatus.OK);
	}
	
	/**
	* @param problemNo - 문제 번호
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title
	* @throws Exception
	* @Method 설명 : 문제번호를 받아와서 카테고리번호가 같은(소분류가 같은)문제들 중 랜덤하게 10개를 뽑은 리스트 반환 (문제 추천용)
	* @변경이력 :
	*/
	@ApiOperation(value=" 문제번호를 받아와서 카테고리번호가 같은(소분류가 같은)문제들 중 랜덤하게 10개를 뽑은 리스트를 반환한다.",response = List.class)
	@GetMapping(value="/recommend/{problemNo}")
	public ResponseEntity<List<ProblemDto>> selectRecommendProblemList(@PathVariable int problemNo) throws Exception{
		logger.debug("selectRecommendProblemList - 호출");
		return new ResponseEntity<List<ProblemDto>>(problemService.selectRecommendProblemList(problemNo), HttpStatus.OK);
	}
	
	/**
	* @param problemNo - 문제번호
	* @return ResponseEntity<Map<String,Object>> - 문제의 정보들, 응답형태
	 * @throws Exception 
	* @Method 설명 : 문제 번호에 해당하는 문제 정보를 반환(문제+해쉬태그+카테고리)(상세보기 용)
	* @변경이력 : 
	*/
	@ApiOperation(value = "문제 번호에 해당하는 문제 정보를 반환한다(문제+해쉬태그+카테고리)", response = HashMap.class)
	@GetMapping(value = "/{problemNo}")
	public ResponseEntity<HashMap<String, Object>> selectProblem(@PathVariable int problemNo) throws Exception{
		logger.debug("selectProblem - 호출");
		return new ResponseEntity<HashMap<String, Object>>(problemService.selectProblem(problemNo), HttpStatus.OK);
	}
	
	@ApiOperation(value = "문제번호에 해당하는 문제정답을 반환한다.", response = ProblemAnswerDto.class)
	@GetMapping(value = "/answer/{problemNo}")
	public ResponseEntity<ProblemAnswerDto> selectProblemAnswer(@PathVariable int problemNo) throws Exception {
		logger.debug("selectProblemAnswer - 호출");
		return new ResponseEntity<ProblemAnswerDto>(problemService.selectProblemAnswer(problemNo), HttpStatus.OK);
	}
	
	// 지금 방식 
	// 1. 문제를 클릭 -> selectProblem(problemNo)호출 -> ProblemDto를 반환 
	// -> (vue)ProblemDto에서 categoryNo를  뽑음 -> select대,중,소(categoryNo)호출 -> 각 카테고리Dto반환
	// 2. selectHashTag(problemNo)호출 -> HashTagDto List반환
	
	// 다른 방식
	// 1. 문제를 클릭 -> selectProblem(problemNo)호출 -> ProblemDto를 반환
	// 2. select대,중,소(problemNo)호출 -> 각 카테고리Dto반환  *query문의 join을 이용해서 problemNo로 카테고리 dto를 뽑아옴
	// 3. selectHashTag(problemNo)호출 -> HashTagDto List반환
	
	/**
	* @param hashTagNo - 해시태그 번호
	* @return ResponseEntity<List<ProblemDto>> - 문제리스트, 응답형태
	 * @throws Exception 
	* @Method 설명 : 해시태그번호에 해당하는 문제들을 반환(해시태그를 클릭했을 때)
	* @변경이력 :
	*/
	@ApiOperation(value = "해시태그번호에 해당하는 문제들을 반환한다.", response = List.class)
	@GetMapping(value = "/hashtag/{hashTagNo}")
	public ResponseEntity<List<ProblemDto>> selectProblemByHashTag(@PathVariable int hashTagNo) throws Exception {
		logger.debug("selectProblemByHashTag - 호출");
		return new ResponseEntity<List<ProblemDto>>(problemService.selectProblemByHashTag(hashTagNo), HttpStatus.OK);
	}
	
	/**
	* @param categoryNo - 카테고리 번호
	* @return ResponseEntity<List<ProblemDto>> - 문제리스트, 응답형태
	 * @throws Exception 
	* @Method 설명 : 카테고리 번호(소+중+대/중+대/대)에 해당하는 문제들을 반환
	* @변경이력 :
	*/
	@ApiOperation(value = "카테고리 번호(소+중+대/중+대/대)에 해당하는 문제들을 반환한다.", response = List.class)
	@GetMapping(value = "/category/{categoryNo}")
	public ResponseEntity<List<ProblemDto>> selectProblemByCategory(@PathVariable String categoryNo) throws Exception {
			logger.debug("selectProblemByCategory - 호출");
			return new ResponseEntity<List<ProblemDto>>(problemService.selectProblemByCategory(categoryNo), HttpStatus.OK);
	}
	
	
	/**
	* @param type - 검색조건
	* @param keyword - 검색키워드
	* @return ResponseEntity<List<ProblemDto>> - 문제리스트, 응답형태
	 * @throws Exception 
	* @Method 설명 : 검색조건과 검색키워드에 해당하는 문제를 반환
	* @변경이력 :
	*/
	@ApiOperation(value = "검색조건과 검색키워드에 해당하는 문제를 반환한다.", response = List.class)
	@GetMapping(value = "/search/{type}/{keyword}")
	public ResponseEntity<List<ProblemDto>> selectProblemByKeyword(@PathVariable String type, @PathVariable String keyword) throws Exception {
		logger.debug("selectProblemByKeyword - 호출");
		return new ResponseEntity<List<ProblemDto>>(problemService.selectProblemByKeyword(type,keyword), HttpStatus.OK);
	}
	
	/**
	* @param map : problemDto - 문제정보, problemAnswerDto - 문제 정답 정보, hashTagList(List<String>) - 해시태그명 리스트, problemSetNo - 문제집 번호
	* @return ResponseEntity<String> - 응답형태
	 * @throws Exception 
	* @Method 설명 : 문제 등록 (문제+문제정답+문제와 해시태그 매핑+문제와 문제집 매핑)
	* @변경이력 :
	*/
	@ApiOperation(value = "문제를 등록한다. 그리고 DB등록 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping(value = "/create")
	public ResponseEntity<String> createProblem(@RequestBody HashMap<String, Object> map) throws Exception{
		logger.debug("createProblem - 호출");
		if(problemService.createProblem(map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	/**
	* @param uid - 유저토큰값
	* @return boolean - 성공, 실패 여부
	* @throws Exception
	* @Method 설명 : 임시 저장된 문제들의 flag를 0에서 1로 변환
	* @변경이력 :
	*/
	@ApiOperation(value = "임시 저장된 문제들의 flag를 0에서 1로 변환. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping(value = "/updateflag/{uid}")
	public ResponseEntity<String> updateProblemFlag(@PathVariable String uid) throws Exception{
		logger.debug("updateProblemFlag - 호출");
		if(problemService.updateProblemFlag(uid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	/**
	* @param map : problemDto - 문제정보, problemAnswerDto - 문제 정답 정보, hashTagList(List<String>) - 해시태그명 리스트
	* @return ResponseEntity<String> - 응답형태
	* @throws Exception
	* @Method 설명 : 문제 수정 (문제+문제정답+문제와 해시태그 매핑)
	* @변경이력 :
	*/
	@ApiOperation(value = "문제를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping(value = "/update")
	public ResponseEntity<String> updateProblem(@RequestBody HashMap<String, Object> map) throws Exception{
		logger.debug("updateProblem - 호출");
		if(problemService.updateProblem(map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// cascade되어있어서 삭제되면 자동으로 다른 것도 삭제됨
	// TODO : delete확인
	/**
	* @param problemNo - 문제 번호
	* @return ResponseEntity<String> - 응답형태
	* @throws Exception
	* @Method 설명 : 문제 삭제(연관된 나머지는 cascade로 DB에서 자동 삭제)
	* @변경이력 :
	*/
	@ApiOperation(value = "문제를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping(value = "/delete/{problemNo}")
	public ResponseEntity<String> deleteProblem(@PathVariable int problemNo) throws Exception{
		logger.debug("updateProblem - 호출");
		if(problemService.deleteProblem(problemNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}

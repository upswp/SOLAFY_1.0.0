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
	* @param problemNo - 문제번호
	* @return ResponseEntity<Map<String,Object>> - 문제의 정보들, 응답형태
	* @Method 설명 : 문제 번호에 해당하는 문제 정보를 반환(문제+해쉬태그+카테고리)(상세보기 용)
	* @변경이력 : 
	*/
	@ApiOperation(value = "문제 번호에 해당하는 문제 정보를 반환한다(문제+해쉬태그+카테고리)", response = ProblemDto.class)
	@GetMapping(value = "/{problemNo}")
	public ResponseEntity<Map<String, Object>> selectProblem(@PathVariable int problemNo){
		logger.debug("selectProblem - 호출");
		return new ResponseEntity<Map<String, Object>>(problemService.selectProblem(problemNo), HttpStatus.OK);
	}
	
	// 지금 방식 
	// 1. 문제를 클릭 -> selectProblem(problemNo)호출 -> ProblemDto를 반환 
	// -> (vue)ProblemDto에서 categoryNo를  뽑음 -> select대,중,소(categoryNo)호출 -> 각 카테고리Dto반환
	// 2. selectHashTag(problemNo)호출 -> HashTagDto List반환
	
	// 다른 방식
	// 1. 문제를 클릭 -> selectProblem(problemNo)호출 -> ProblemDto를 반환
	// 2. select대,중,소(problemNo)호출 -> 각 카테고리Dto반환  *query문의 join을 이용해서 problemNo로 카테고리 dto를 뽑아옴
	// 3. selectHashTag(problemNo)호출 -> HashTagDto List반환
	
//	@ApiOperation(value = "문제의 카테고리번호에 해당하는  소분류 카테고리 반환한다.", response = CategorySmallDto.class)
//	@GetMapping(value = "/category/small/{categoryNo}")
//	public ResponseEntity<CategorySmallDto> selectCategorySmall(@PathVariable String categoryNo){
//		logger.debug("selectCategorySmall - 호출");
//		return new ResponseEntity<CategorySmallDto>(problemService.selectCategorySmall(categoryNo), HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "문제의 카테고리번호에 해당하는 중분류 카테고리 반환한다.", response = CategoryMediumDto.class)
//	@GetMapping(value = "/category/medium/{categoryNo}")
//	public ResponseEntity<CategoryMediumDto> selectCategoryMedium(@PathVariable String categoryNo){
//		logger.debug("selectCategoryMedium - 호출");
//		return new ResponseEntity<CategoryMediumDto>(problemService.selectCategoryMedium(categoryNo), HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "문제의 카테고리번호에 해당하는 대분류 카테고리 반환한다.", response = CategoryLargeDto.class)
//	@GetMapping(value = "/category/Large/{categoryNo}")
//	public ResponseEntity<CategoryLargeDto> selectCategoryLarge(@PathVariable String categoryNo) {
//		logger.debug("selectCategoryLarge - 호출");
//		return new ResponseEntity<CategoryLargeDto>(problemService.selectCategoryLarge(categoryNo), HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "문제번호에 해당 하는 해시태그를 반환한다.", response = List.class)
//	@GetMapping(value = "/{problemNo}/hashtag")
//	public ResponseEntity<List<HashTagDto>> selectHashTagList(@PathVariable int problemNo){
//		logger.debug("selectHashtagList - 호출");
//		return new ResponseEntity<List<HashTagDto>>(problemService.selectHashTagList(problemNo), HttpStatus.OK);
//	}
	
	/**
	* @param hashTagNo - 해시태그 번호
	* @return ResponseEntity<List<ProblemDto>> - 문제리스트, 응답형태
	* @Method 설명 : 해시태그번호에 해당하는 문제들을 반환(해시태그를 클릭했을 때)
	* @변경이력 :
	*/
	@ApiOperation(value = "해시태그번호에 해당하는 문제들을 반환한다.", response = List.class)
	@GetMapping(value = "/hashtag/{hashTagNo}")
	public ResponseEntity<List<ProblemDto>> selectProblemByHashTag(@PathVariable int hashTagNo) {
		logger.debug("selectProblemByHashTag - 호출");
		return new ResponseEntity<List<ProblemDto>>(problemService.selectProblemByHashTag(hashTagNo), HttpStatus.OK);
	}
	
	/**
	* @param categoryNo - 카테고리 번호
	* @return ResponseEntity<List<ProblemDto>> - 문제리스트, 응답형태
	* @Method 설명 : 카테고리 번호(소+중+대/중+대/대)에 해당하는 문제들을 반환
	* @변경이력 :
	*/
	@ApiOperation(value = "카테고리 번호(소+중+대/중+대/대)에 해당하는 문제들을 반환한다.", response = List.class)
	@GetMapping(value = "/category/{categoryNo}")
	public ResponseEntity<List<ProblemDto>> selectProblemByCategory(String categoryNo) {
			logger.debug("selectProblemByCategory - 호출");
			return new ResponseEntity<List<ProblemDto>>(problemService.selectProblemByCategory(categoryNo), HttpStatus.OK);
	}
	
	
	/**
	* @param type - 검색조건
	* @param keyword - 검색키워드
	* @return ResponseEntity<List<ProblemDto>> - 문제리스트, 응답형태
	* @Method 설명 : 검색조건과 검색키워드에 해당하는 문제를 반환
	* @변경이력 :
	*/
	@ApiOperation(value = "검색조건과 검색키워드에 해당하는 문제를 반환한다.", response = List.class)
	@GetMapping(value = "/search/{type}/{keyword}")
	public ResponseEntity<List<ProblemDto>> selectProblemByKeyword(@PathVariable String type, @PathVariable String keyword) {
		logger.debug("selectProblemByKeyword - 호출");
		return new ResponseEntity<List<ProblemDto>>(problemService.selectProblemByKeyword(type,keyword), HttpStatus.OK);
	}
	
	/**
	* @param problemDto - 문제정보
	* @param problemAnswerDto - 문제 정답 정보
	* @param hashTagList(List<String>) - 해시태그명 리스트
	* @return ResponseEntity<String> - 응답형태
	* @Method 설명 : 문제 등록
	* @변경이력 :
	*/
	@ApiOperation(value = "문제를 등록한다. 그리고 DB등록 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = List.class)
	@PostMapping(value = "/create")
	public ResponseEntity<String> createProblem(@RequestBody ProblemDto problemDto, @RequestBody ProblemAnswerDto problemAnswerDto, @RequestBody List<String> hashTagList){
		logger.debug("createProblem - 호출");
		if(problemService.createProblem(problemDto,problemAnswerDto,hashTagList)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// TODO : 2020.12.20 여기부터
	// TODO : 카테고리 대 중 소 리스트 볼 수 있게 Mapper추가
	@ApiOperation(value = "문제를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = List.class)
	@PutMapping(value = "/update")
	public ResponseEntity<String> updateProblem(@RequestBody ProblemDto problemDto){
		logger.debug("updateProblem - 호출");
		if(problemService.updateProblem(problemDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// cascade되어있어서 삭제되면 자동으로 다른 것도 삭제됨
	@ApiOperation(value = "문제를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = List.class)
	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deleteProblem(@RequestBody int problemNo){
		logger.debug("updateProblem - 호출");
		if(problemService.deleteProblem(problemNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}

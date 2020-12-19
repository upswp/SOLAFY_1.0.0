package com.solafy.service.problem;

import java.util.List;
import java.util.Map;

import com.solafy.model.ProblemAnswerDto;
import com.solafy.model.ProblemDto;

/**
* @FileName : ProblemService.java
* @Project : SOLAFY
* @Date : 2020. 12. 16.
* @작성자 : Lee Ayoung

* @변경이력 :
* @프로그램 설명 : Problem Service Interface
*/

public interface ProblemService {
	
	/**
	* @param problemNo - 문제 번호
	* @return Map<String,Object> - DTO이름과 DTO : ProblemDto, CategorySmallDto, CategoryMediumDto, CategoryLargeDto, List<HashTagDto>
	* @Method 설명 : 문제 번호에 해당하는 문제 정보를 반환(문제+해쉬태그+카테고리)(상세보기 용)
	* @변경이력 :
	*/
	public Map<String, Object> selectProblem(int problemNo);
	
//	/**
//	* @Method Name : selectCategorySmall
//	* @작성일 : 2020. 12. 16.
//	* @작성자 : Lee Ayoung
//	* @param categoryNo
//	* @return
//	* @Method 설명 : 문제의 소분류 카테고리 반환(상세보기 용)
//	* @변경이력 :
//	*/
//	public CategorySmallDto selectCategorySmall(String categoryNo);
//	
//	/**
//	* @Method Name : selectCategoryMedium
//	* @작성일 : 2020. 12. 16.
//	* @작성자 : Lee Ayoung
//	* @param categoryNo
//	* @return
//	* @Method 설명 : 문제의 중분류 카테고리 반환(상세보기 용)
//	* @변경이력 :
//	*/
//	public CategoryMediumDto selectCategoryMedium(String categoryNo);
//	
//	/**
//	* @Method Name : selectCategoryLarge
//	* @작성일 : 2020. 12. 16.
//	* @작성자 : Lee Ayoung
//	* @param categoryNo
//	* @return
//	* @Method 설명 : 문제의 대분류 카테고리 반환(상세보기 용)
//	* @변경이력 :
//	*/
//	public CategoryLargeDto selectCategoryLarge(String categoryNo);
//	
//	/**
//	* @Method Name : selectHashTagList
//	* @작성일 : 2020. 12. 16.
//	* @작성자 : Lee Ayoung
//	* @param problemNo
//	* @return
//	* @Method 설명 : 문제의 해시태그 반환
//	* @변경이력 :
//	*/
//	public List<HashTagDto> selectHashTagList(int problemNo);
	 
	/**
	* @param hashTagNo - 해시태그 번호
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @Method 설명 : 해시태그번호로 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByHashTag(int hashTagNo);
	
	/**
	* @param categoryNo - 문제의 대,중,소분류 카테고리 숫자
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @Method 설명 : 카테고리 번호(소+중+대/중+대/대)에 해당하는 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByCategory(String categoryNo);
	
	/**
	* @param type - 검색조건
	* @param keyword - 검색키워드
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @Method 설명 : 검색조건과 검색키워드에 해당하는 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByKeyword(String type, String keyword) ;
	
	/**
	* @param problemDto - 문제정보
	* @param problemAnswerDto - 문제 정답 정보
	* @param hashTagList(List<String>) - 해시태그명 리스트
	* @return boolean - 성공,실패 여부
	* @Method 설명 : 문제 등록
	* @변경이력 :
	*/
	public boolean createProblem(ProblemDto problemDto, ProblemAnswerDto problemAnswerDto,List<String> hashTagList);
	
	/**
	* @Method Name : updateProblem
	* @작성일 : 2020. 12. 16.
	* @작성자 : Lee Ayoung
	* @param problemDto
	* @return
	* @Method 설명 : 문제 수정
	* @변경이력 :
	*/
	public boolean updateProblem(ProblemDto problemDto);
	
	/**
	* @Method Name : deleteProblem
	* @작성일 : 2020. 12. 19.
	* @작성자 : Lee Ayoung
	* @param problemNo
	* @return
	* @Method 설명 : 문제 삭제
	* @변경이력 :
	*/
	public boolean deleteProblem(int problemNo);
}

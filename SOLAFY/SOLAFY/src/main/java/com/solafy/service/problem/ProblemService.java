package com.solafy.service.problem;

import java.sql.SQLException;
import java.util.HashMap;
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
	* @return List<ProblemDto> - 문제 리스트
	* @throws Exception
	* @Method 설명 : 전체 문제 리스트를 반환
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemList() throws Exception;

	/**
	* @param problemNo - 문제 번호
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title
	* @throws Exception
	* @Method 설명 : 문제번호를 받아와서 카테고리번호가 같은(소분류가 같은)문제들 중 랜덤하게 10개를 뽑은 리스트 반환 (문제 추천용)
	* @변경이력 :
	*/
	public List<ProblemDto> selectRecommendProblemList(int problemNo) throws Exception;
	
	/**
	* @param problemNo - 문제 번호
	* @return Map<String,Object> - DTO이름과 DTO : ProblemDto, CategorySmallDto, CategoryMediumDto, CategoryLargeDto, List<HashTagDto>
	* @throws Exception
	* @Method 설명 : 문제 번호에 해당하는 문제 정보를 반환(문제+해쉬태그+카테고리)(상세보기 용)
	* @변경이력 :
	*/
	public HashMap<String, Object> selectProblem(int problemNo) throws Exception;
	

	/**
	* @param problemNo - 문제번호
	* @return ProblemAnswerDto - 문제 정답 : problemNo, answer, solution, keyword
	* @throws Exception
	* @Method 설명 : 문제 정답 반환
	* @변경이력 :
	*/
	public ProblemAnswerDto selectProblemAnswer(int problemNo) throws Exception;
	 
	/**
	* @param hashTagNo - 해시태그 번호
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @throws Exception
	* @Method 설명 : 해시태그번호로 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByHashTag(int hashTagNo) throws Exception;
	
	/**
	* @param categoryNo - 문제의 대,중,소분류 카테고리 숫자
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @throws Exception
	* @Method 설명 : 카테고리 번호(소+중+대/중+대/대)에 해당하는 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByCategory(String categoryNo) throws Exception;
	
	/**
	* @param type - 검색조건
	* @param keyword - 검색키워드
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @throws Exception
	* @Method 설명 : 검색조건과 검색키워드에 해당하는 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByKeyword(String type, String keyword) throws Exception;
	
	/**
	* @param map : problemDto - 문제정보, problemAnswerDto - 문제 정답 정보, hashTagList(List<String>) - 해시태그명 리스트, problemSetNo - 문제집 번호
	* @return boolean - 성공, 실패 여부
	* @throws Exception
	* @Method 설명 : 문제 등록
	* @변경이력 :
	*/
	public boolean createProblem(HashMap<String, Object> map) throws Exception;
	
	/**
	* @param map : problemDto - 문제정보, problemAnswerDto - 문제 정답 정보, hashTagList(List<String>) - 해시태그명 리스트
	* @return boolean - 성공, 실패 여부
	* @throws Exception
	* @Method 설명 : 문제 수정
	* @변경이력 :
	*/
	public boolean updateProblem(HashMap<String, Object> map) throws Exception;
	
	/**
	* @param problemNo - 문제 번호
	* @return boolean - 성공, 실패 여부
	* @throws Exception
	* @Method 설명 : 문제 삭제
	* @변경이력 :
	*/
	public boolean deleteProblem(int problemNo) throws Exception;
}

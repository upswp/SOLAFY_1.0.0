package com.solafy.service.problem;

import java.util.List;

import com.solafy.model.CategoryLargeDto;
import com.solafy.model.CategoryMediumDto;
import com.solafy.model.CategorySmallDto;
import com.solafy.model.HashTagDto;
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
	* @Method Name : selectProblem
	* @작성일 : 2020. 12. 16.
	* @작성자 : Lee Ayoung
	* @param problemNo
	* @return
	* @Method 설명 : 문제 정보 반환(상세보기 용)
	* @변경이력 :
	*/
	public ProblemDto selectProblem(int problemNo);
	
	/**
	* @Method Name : selectCategorySmall
	* @작성일 : 2020. 12. 16.
	* @작성자 : Lee Ayoung
	* @param categoryNo
	* @return
	* @Method 설명 : 문제의 소분류 카테고리 반환(상세보기 용)
	* @변경이력 :
	*/
	public CategorySmallDto selectCategorySmall(String categoryNo);
	
	/**
	* @Method Name : selectCategoryMedium
	* @작성일 : 2020. 12. 16.
	* @작성자 : Lee Ayoung
	* @param categoryNo
	* @return
	* @Method 설명 : 문제의 중분류 카테고리 반환(상세보기 용)
	* @변경이력 :
	*/
	public CategoryMediumDto selectCategoryMedium(String categoryNo);
	
	/**
	* @Method Name : selectCategoryLarge
	* @작성일 : 2020. 12. 16.
	* @작성자 : Lee Ayoung
	* @param categoryNo
	* @return
	* @Method 설명 : 문제의 대분류 카테고리 반환(상세보기 용)
	* @변경이력 :
	*/
	public CategoryLargeDto selectCategoryLarge(String categoryNo);
	
	/**
	* @Method Name : selectHashTagList
	* @작성일 : 2020. 12. 16.
	* @작성자 : Lee Ayoung
	* @param problemNo
	* @return
	* @Method 설명 : 문제의 해시태그 반환
	* @변경이력 :
	*/
	public List<HashTagDto> selectHashTagList(int problemNo);
	 
	/**
	* @Method Name : selectProblemByHashTag
	* @작성일 : 2020. 12. 16.
	* @작성자 : Lee Ayoung
	* @param hashTagNo
	* @return
	* @Method 설명 : 해시태그번호로 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByHashTag(int hashTagNo);
	
	/**
	* @Method Name : selectProblemByCategorySmall
	* @작성일 : 2020. 12. 17.
	* @작성자 : Lee Ayoung
	* @param categoryNo
	* @return
	* @Method 설명 : 소분류 카테고리를 이용한 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByCategorySmall(String categoryNo);

	/**
	* @Method Name : selectProblemByCategoryMedium
	* @작성일 : 2020. 12. 17.
	* @작성자 : Lee Ayoung
	* @param categoryLargeMediumNo
	* @return
	* @Method 설명 : 중분류 카테고리를 이용한 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByCategoryMedium(String categoryLargeMediumNo);
	 
	/**
	* @Method Name : selectProblemByCategoryLarge
	* @작성일 : 2020. 12. 17.
	* @작성자 : Lee Ayoung
	* @param categoryLargeNo
	* @return
	* @Method 설명 :대분류 카테고리를 이용한 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByCategoryLarge(String categoryLargeNo);
	
	// type, keyword로 문제 검색
	/**
	* @Method Name : selectProblemByKeyword
	* @작성일 : 2020. 12. 18.
	* @작성자 : Lee Ayoung
	* @param type
	* @param keyword
	* @return
	* @Method 설명 :
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByKeyword(String type, String keyword) ;
	
	/**
	* @Method Name : createProblem
	* @작성일 : 2020. 12. 16.
	* @작성자 : Lee Ayoung
	* @param problemDto
	* @return
	* @Method 설명 : 문제 등록
	* @변경이력 :
	*/
	public boolean createProblem(ProblemDto problemDto);
	
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

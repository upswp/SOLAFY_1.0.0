package com.solafy.mapper.problem;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.CategoryLargeDto;
import com.solafy.model.CategoryMediumDto;
import com.solafy.model.CategorySmallDto;
import com.solafy.model.HashTagDto;
import com.solafy.model.ProblemDto;

/**
* @FileName : ProblemMapper.java
* @Project : SOLAFY
* @Date : 2020. 12. 13.
* @작성자 : Lee Ayoung

* @변경이력 :
* @프로그램 설명 : Problem Mapper
*/
@Mapper
public interface ProblemMapper {
	/**
	* @param problemNo - 문제 번호
	* @return ProblemDto - 문제 : problemNo, multipleChoice, title, uid, contents, categorySmallNo, type 
	* @Method 설명 : 문제의 정보 반환(상세보기 용)
	* @변경이력 :
	*/
	public ProblemDto selectProblem(int problemNo);
	
	/**
	* @param categorySmallNo - 문제의 소분류 카테고리 숫자
	* @return CategorySmallDto - 소분류 카테고리 : categoryNo, categoryName
	* @Method 설명 : 문제의 소분류 카테고리 반환(상세보기 용)
	* @변경이력 :
	*/
	public CategorySmallDto selectCategorySmall(int categorySmallNo);
	
	/**
	* @param categoryMediumNo - 문제의 중분류 카테고리 숫자
	* @return CategoryMediumDto - 중분류 카테고리 : categoryNo, categoryName
	* @Method 설명 : 문제의 중분류 카테고리 반환(상세보기 용)
	* @변경이력 :
	*/
	public CategoryMediumDto selectCategoryMedium(int categoryMediumNo);
	
	/**
	* @param categoryLargeNo - 문제의 대분류 카테고리 숫자
	* @return CategoryLargeDto - 대분류 카테고리 : categoryNo, categoryName
	* @Method 설명 : 문제의 대분류 카테고리 반환(상세보기 용)
	* @변경이력 :
	*/
	public CategoryLargeDto selectCategoryLarge(int categoryLargeNo);
	
	/**
	* @param problemNo
	* @return List<HashTagDto> - 해시태그 리스트 : hashTagNo, hashTag
	* @Method 설명 : 문제의 해시태그리스트를 반환
	* @변경이력 :
	*/
	public List<HashTagDto> selectHashTagList(int problemNo);
	
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
	* @Method 설명 : 소분류 카테고리를 이용한 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByCategorySmall(String categoryNo);
	
	/**
	* @param categoryLargeMediumNo - 문제의 대,중분류 카테고리 숫자
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @Method 설명 : 중분류 카테고리를 이용한 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByCategoryMedium(String categoryLargeMediumNo);
	
	/**
	* @param categoryLargeNo - 문제의 대분류 카테고리 숫자
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @Method 설명 : 대분류 카테고리를 이용한 문제 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByCategoryLarge(String categoryLargeNo);
	
	/**
	* @param title - 문제이름(일부)
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @Method 설명 : 문제이름(일부)으로 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByName(String title);
	
	/**
	* @param problemNo - 문제번호
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @Method 설명 : 문제번호로 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByProblemNo(int problemNo);
	
	/**
	* @param userNickname - 유저 닉네임
	* @return List<ProblemDto> - 문제 리스트 : problemNo, title, uid
	* @Method 설명 : 유저닉네임으로 검색
	* @변경이력 :
	*/
	public List<ProblemDto> selectProblemByUserNickname(String nickname);
	
	/**
	* @param problemDto - 문제
	* @return int - 등록된 행의 개수 반환
	* @Method 설명 : 문제 등록
	* @변경이력 :
	*/
	public int createProblem(ProblemDto problemDto);
	
	/**
	* @param hashTagDto - 해시태그
	* @return int - 등록된 행의 개수 반환
	* @Method 설명 : 문제와 해시태그를 연결
	* @변경이력 :
	*/
	public int createHashTagMapping(int problemNo, int hashTagNo);
	
	/**
	 * 
	* @param map - Map<String, Integer> - problemSetNo, problemNo 포함
	* @return int 등록된 행의 갯수 반환
	* @throws SQLException
	* @Method 설명 : 문제집에서 문제들을 등록한다.
	* @변경이력 :
	 */
	public int createProblemSetMapping(Map<String, Integer> map) throws SQLException;
	
	public int updateProblemFlag(String uid) throws SQLException;
	
	public int deleteProblemFlag(String uid) throws SQLException;
	
	/**
	* @param problemDto - 문제
	* @return int - 수정된 행의 개수 반환 
	* @Method 설명 : 문제 수정
	* @변경이력 :
	*/
	public int updateProblem(ProblemDto problemDto);
	
	/**
	* @param ProblemNo - 문제번호
	* @return int - 삭제된 행의 개수 반환
	* @Method 설명 : 문제번호를 이용한 해시태그와 문제의 연결 해제
	* @변경이력 :
	*/
	public int deleteHashTagMappingByProblemNo(int ProblemNo);
	
	/**
	* @param problemNo - 문제
	* @return int - 삭제된 행의 개수 반환
	* @Method 설명 : 문제 삭제
	* @변경이력 :
	*/
	public int deleteProblem(int problemNo);
}

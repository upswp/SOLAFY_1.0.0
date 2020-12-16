package com.solafy.mapper.problem;

import java.util.List;

import com.solafy.model.CategoryLargeDto;
import com.solafy.model.CategoryMediumDto;
import com.solafy.model.CategorySmallDto;
import com.solafy.model.HashTagDto;
import com.solafy.model.ProblemDto;

/**
 * ProblemMapper
 * @author Lee AYoung
 * @since 2020-12-13
 */

public interface ProblemMapper {
	/**
	 * 문제의 정보 반환(상세보기 용)
	 * @param problemNo - 한 문제 번호
	 * @return problemNo에 해당하는 ProblemDto 반환 - 전체 column
	 */
	ProblemDto selectProblem(int problemNo);
	
	/**
	 * 문제의 소분류 카테고리 반환(상세보기 용)
	 * @param categorySmallNo - 문제의 소분류 카테고리 숫자
	 * @return categorySmallNo에 해당하는 CategorySmallDto반환 - 전체 column
	 */
	CategorySmallDto selectCategorySmall(int categorySmallNo);
	
	/**
	 * 문제의 중분류 카테고리 반환(상세보기 용)
	 * @param categoryMediumNo - 문제의 중분류 카테고리 숫자
	 * @return categoryMediumNo에 해당하는 CategoryMediumDto반환 - 전체 column
	 */
	CategoryMediumDto selectCategoryMedium(int categoryMediumNo);
	
	/**
	 * 문제의 대분류 카테고리 반환(상세보기 용)
	 * @param categoryLargeNo - 문제의 대분류 카테고리 숫자
	 * @return categoryLargeNo에 해당하는 CategoryLargeDto반환 - 전체 column
	 */
	CategoryLargeDto selectCategoryLarge(int categoryLargeNo);
	
	/**
	 * 문제의 해시태그 반환 : hashtagmapping테이블에서 문제 번호를 받아 반환
	 * @param problemNo - 한 문제 번호
	 * @return problemNo에 해당하는 HashTagDto반환 - 전체 column
	 */
	List<HashTagDto> selectHashtagList(int problemNo);
	
	/**
	 * 해시태그번호로 문제 검색
	 * @param hashTagNo - 한 해시태그 번호
	 * @return hashTag에 해당하는 ProblemDto의 List 반환 - problemNo, title, uid, starScore
	 */ 
	List<ProblemDto> selectProblemByHashTag(int hashTagNo);
	 
	/**
	 * 문제이름(일부)으로 검색
	 * @param title - 문제이름의 일부
	 * @return title에 해당하는 ProblemDto의 List 반환 - problemNo, title, uid, starScore
	 */ 
	List<ProblemDto> selectProblemByName(String title);
	 
	/**
	 * 소분류 카테고리를 이용한 문제 검색
	 * @param categorySmallNo - 문제의 소분류 카테고리 숫자
	 * @return categorySmallNo에 해당하는 ProblemDto의 List 반환 - problemNo, title, uid, starScore
	 */ 
	List<ProblemDto> selectProblemByCategoryScmall(int categorySmallNo);
	
	/**
	 * 문제 등록
	 * @param problemDto - 문제정보
	 * @return int : 등록된 행의 개수 반환 
	 */ 
	int insertProblem(ProblemDto problemDto);
	
	/**
	 * 문제 수정
	 * @param problemDto - 문제정보
	 * @return int : 수정된 행의 개수 반환 
	 */ 
	int updateProblem(ProblemDto problemDto);
	
	/**
	 * 문제 삭제
	 * @param problemNo - 문제번호
	 * @return int : 삭제된 행의 개수 반환 
	 */ 
	int deleteProblem(int problemNo);
}

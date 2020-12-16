package com.solafy.service.problem;

import java.util.List;

import com.solafy.model.CategoryLargeDto;
import com.solafy.model.CategoryMediumDto;
import com.solafy.model.CategorySmallDto;
import com.solafy.model.HashTagDto;
import com.solafy.model.ProblemDto;

/**
 * ProblemService
 * @author Lee AYoung
 * @since 2020-12-16
 */
public interface ProblemService {
	// 문제의 정보 반환(상세보기 용)
	public ProblemDto selectProblem(int problemNo);
	
	// 문제의 소분류 카테고리 반환(상세보기 용)
	public CategorySmallDto selectCategorySmall(int categorySmallNo);
	
	// 문제의 중분류 카테고리 반환(상세보기 용)
	public CategoryMediumDto selectCategoryMedium(int categoryMediumNo);
	
	// 문제의 대분류 카테고리 반환(상세보기 용)
	public CategoryLargeDto selectCategoryLarge(int categoryLargeNo);
	
	// 문제의 해시태그 반환
	public List<HashTagDto> selectHashtagList(int problemNo);
	
	// 해시태그번호로 문제 검색
	public List<ProblemDto> selectProblemByHashTag(int hashTagNo);
	
	// 문제이름(일부)으로 검색
	public List<ProblemDto> selectProblemByName(String title);
	
	// 소분류 카테고리를 이용한 문제 검색
	public List<ProblemDto> selectProblemByCategorySmall(int categoryNo);
	
	// 중분류 카테고리를 이용한 문제 검색
	public List<ProblemDto> selectProblemByCategoryMedium(int categoryLargeMediumNo);
	
	// 대분류 카테고리를 이용한 문제 검색
	public List<ProblemDto> selectProblemByCategoryLarge(int categoryLargeNo);
	
	// 문제 등록
	public int insertProblem(ProblemDto problemDto);
	
	// 문제 수정
	public int updateProblem(ProblemDto problemDto);
	
	// 문제 삭제
	public int deleteProblem(int problemNo);
}

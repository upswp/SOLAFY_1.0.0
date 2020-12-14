package com.solafy.mapper;
import java.sql.SQLException;
/**
 * ProblemMapper
 * @author Lee AYoung
 * @since 2020-12-13
 */
import java.util.List;

import com.solafy.model.HashTagDto;
import com.solafy.model.ProblemDto;

public interface ProblemMapper {
	/**
	 * 문제집에 포함되는 문제리스트 반환
	 * @param problemSetNo - 한 문제집 번호
	 * @return 문제집에 해당하는 ProblemDto의 List 반환 - problemNo, title, uid, starScore
	 */
	List<ProblemDto> selectProblemList(int problemSetNo);
	
	/**
	 * 문제하나 정보 반환(상세보기 용)
	 * @param problemNo - 한 문제 번호
	 * @return problemNo에 해당하는 ProblemDto 반환 - 전체 column
	 */
	ProblemDto selectProblem(int problemNo);
	
	/**
	 * 문제의 카테고리 반환
	 * @param 
	 * @return 
	 */
	
	/**
	 * 문제의 해시태그 반환 : hashtagmapping테이블에서 문제 번호를 받아 반환
	 * @param 
	 * @return 
	 */
	List<HashTagDto> selectHashtagList(int problemNo);
	
	/**
	 * 해시태그로 문제 검색
	 * @param 
	 * @return 
	 */ 
	List<ProblemDto> selectProblemByHashTag(String hashTag);
	 
	/**
	 * 문제이름(일부)으로 검색
	 * @param 
	 * @return 
	 */ 
	List<ProblemDto> selectProblemByName(String title);
	 
	/**
	 * 카테고리를 이용한 문제 검색
	 * @param 
	 * @return 
	 */ 
	

	/**
	 * 문제 평가하기(서비스단에서 계산해서 넣어줘야할 거 같은데 평균을 어찌줄까?)
	 * @param 
	 * @return 
	 */ 
	int updateStarScore(double starScore);
	
	/**
	 * 문제 등록
	 * @param 
	 * @return 
	 */ 
	int insertProblem(ProblemDto problemDto);
	
	/**
	 * 문제 수정
	 * @param 
	 * @return 
	 */ 
	int updateProblem(ProblemDto problemDto);
	
	/**
	 * 문제 삭제
	 * @param 
	 * @return 
	 */ 
	int deleteProblem(int problemNo);
}

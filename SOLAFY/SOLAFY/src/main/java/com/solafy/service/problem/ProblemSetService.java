package com.solafy.service.problem;

import java.util.List;

import com.solafy.model.ProblemSetDto;
/**
 * ProblemService
 * 문제집 interface
 * @author Park Sangwoo
 * @since 2020-12-13 최초 개발, 문제집 CRUD 생성.
 * @since 2020-12-19 문제집의 문제에 따른 CRUD 생성.
 */
public interface ProblemSetService {

	/**
	 * 모든 문제집 반환
	 * @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	 */
	public List<ProblemSetDto> selectAllProblemSet() ;
	
	/**
	 * 문제집 번호에 해당하는 문제집을 가져온다.
	 * @param problemSetNo - int, 문제집 번호
	 * @return ProblemSetDto의 - 번호, 작성자, 제목, 등록시간 포함
	 */
	public ProblemSetDto selectProblemByNo(int problemSetNo);
	
	/**
	 * 문제집 작성자에 해당하는 문제집을 가져온다.
	 * @param uid - String, 검색된 uid
	 * @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	 */
	public List<ProblemSetDto> selectProblemByWriter(String uid);
	
	/**
	 * 문제집 작성자에 해당하는 문제집을 가져온다.
	 * @param title - String, 검색된 제목 키워드
	 * @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	 */
	public List<ProblemSetDto> selectProblemByTitle(String title);
	
	/**
	 * 문제집 등록 및 문제 mapping
	 * @param problemSet
	 * @return boolean, 정상적으로 삭제 시 true 반환
	 */
	public boolean createProblemSet(ProblemSetDto problemSet);

	/**
	 * 문제집 수정 및 문제 mapping 갱신
	 * @param problemSet 
	 * @return boolean, 정상적으로 삭제 시 true 반환
	 */
	public boolean updateProblemSet(ProblemSetDto problemSet);

	/**
	 * 문제집 삭제 및 문제 mapping 삭제
	 * @param problemSetNo - int, 문제집 번호
	 * @return boolean, 정상적으로 삭제 시 true 반환
	 */
	public boolean deleteProblemSet(int problemSetNo);
	
}

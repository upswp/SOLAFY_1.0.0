package com.solafy.service.problem;

import java.util.List;
import java.util.Map;

import com.solafy.model.ProblemDto;
import com.solafy.model.ProblemSetDto;
/**
 * 
* @FileName : ProblemSetService.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : Park Sangwoo

* @변경이력 :
* @프로그램 설명 : 문제집 interface
 */
public interface ProblemSetService {

	/**
	 * 
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @Method 설명 : 모든 문제집 반환
	* @변경이력 :
	 */
	public List<ProblemSetDto> selectAllProblemSet() throws Exception;
	
	/**
	 * 
	* @param problemSetNo - int, 문제집 번호
	* @return ProblemSetDto의 - 번호, 작성자, 제목, 등록시간, 문제 리스트 포함
	* @Method 설명 : 문제집 번호에 해당하는 문제집을 가져온다.
	* @변경이력 :
	 */
	public Map<String,Object> selectProblemByNo(int problemSetNo) throws Exception;
	
	/**
	 * 
	* @param uid - String, 검색된 uid
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @Method 설명 : 문제집 작성자에 해당하는 문제집을 가져온다.
	* @변경이력 :
	 */
	public List<ProblemSetDto> selectProblemByWriter(String uid) throws Exception;
	
	/**
	 * 
	* @param title - String, 검색된 제목 키워드
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @Method 설명 : 문제집 키워드가 포함이된 문제집을 가져온다.
	* @변경이력 :
	 */
	public List<ProblemSetDto> selectProblemByTitle(String title) throws Exception;
	
	/**
	 * 
	* @param problemSet
	* @return boolean, 정상적으로 등록 시 true 반환
	* @Method 설명 : 문제집 등록 및 문제 mapping
	* @변경이력 : 
	 */
	public boolean createProblemSet(ProblemSetDto problemSet) throws Exception;

	/**
	 * 
	* @param problemSet
	* @return boolean, 정상적으로 수정 시 true 반환
	* @Method 설명 : 문제집 수정 및 문제 mapping 갱신
	* @변경이력 :
	 */
	public boolean updateProblemSet(ProblemSetDto problemSet) throws Exception;

	/**
	 *  
	* @param problemSetNo - int, 문제집 번호
	* @return boolean, 정상적으로 삭제 시 true 반환
	* @Method 설명 : 문제집 삭제 및 문제 mapping 삭제
	* @변경이력 :
	 */
	public boolean deleteProblemSet(int problemSetNo) throws Exception;
	
}

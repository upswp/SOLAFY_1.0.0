package com.solafy.service.problem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.solafy.model.ProblemDto;
import com.solafy.model.ProblemSetDto;
/**
 * 
* @FileName : ProblemSetService.java
* @Project : SOLAFY
* @Date : 2020. 12.22
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
	public Map<String,Object> selectProblemSetByNo(int problemSetNo) throws Exception;
	
	/**
	 * 
	* @param uid - String, 검색된 uid
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @Method 설명 : 문제집 작성자에 해당하는 문제집을 가져온다.
	* @변경이력 :
	 */
	public List<ProblemSetDto> selectProblemSetByWriter(String uid) throws Exception;
	
	/**
	 * 
	* @param title - String, 검색된 제목 키워드
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @Method 설명 : 문제집 키워드가 포함이된 문제집을 가져온다.
	* @변경이력 :
	 */
	public List<ProblemSetDto> selectProblemSetByTitle(String title) throws Exception;
	
	/**
	 * 
	* @param problemSet
	* @return int, 등록한 problemSet의 No반환
	* @Method 설명 : 문제집 등록 및 문제 mapping
	* @변경이력 : 2021.01.02 - createProblemSet의 반환형을 boolean(성공여부)에서 int(problemSetNo)로 변경
	 */
	public int createProblemSet(ProblemSetDto problemSet) throws Exception;

	/**
	* @param map - ProblemSetDto:problemSetNo,nickname,title,regitime / List<ProblemDto>:problemNo,title
	* @return boolean, 정상적으로 수정 시 true 반환
	* @throws Exception
	* @Method 설명 : 문제집 수정 및 문제 mapping 갱신
	* @변경이력 :
	*/
	public boolean updateProblemSet(HashMap<String, Object> map) throws Exception;

	/**
	 *  
	* @param problemSetNo - int, 문제집 번호
	* @return boolean, 정상적으로 삭제 시 true 반환
	* @Method 설명 : 문제집 삭제 및 문제 mapping 삭제
	* @변경이력 :
	 */
	public boolean deleteProblemSet(int problemSetNo) throws Exception;
	
	/**
	 *  
	* @param uid 작성중인 사용자의 uid
	* @return int 수정된 행의 갯수 반환
	* @throws Exception
	* @Method 설명 :  문제집의 flag를 임시저장 상태에서 저장으로 변경
	* @변경이력 :
	 */
	public boolean updateProblemSetFlag (String uid) throws Exception;
	
	/**
	 * 
	* @param uid 작성중인 사용자의 uid
	* @return int 삭제된 행의 갯수 반환
	* @throws Exception
	* @Method 설명 : 문제집의 flag가 임시저장상태인 문제집을 삭제
	* @변경이력 : 
	 */
	public boolean deleteProblemSetFlag (String uid) throws Exception;
	
}

package com.solafy.mapper.problem;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.ProblemDto;
import com.solafy.model.ProblemSetDto;

/**
 * 
* @FileName : ProblemSetMapper.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : Park Sangwoo

* @변경이력 :
* @프로그램 설명 :
 */
@Mapper
public interface ProblemSetMapper {

	/**
	 * 
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @throws SQLException
	* @Method 설명 : 문제집 리스트를 전부 가져온다.
	* @변경이력 :
	 */
	public List<ProblemSetDto> selectAllProblemSet() throws SQLException;
	
	/**
	 * 문제집 번호에 해당하는 문제집을 가져온다.
	 * 
	 * @param problemSetNo - int, 문제집 번호
	 * @return ProblemSetDto의 - 번호, 작성자, 제목, 등록시간 포함
	 */
	public ProblemSetDto selectProblemByNo(int problemSetNo) throws SQLException;
	
	/**
	 * 
	* @param uid - String, 검색된 uid
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @throws SQLException
	* @Method 설명 : 문제집 작성자에 해당하는 문제집을 가져온다.
	* @변경이력 :
	 */
	public List<ProblemSetDto> selectProblemByWriter(String uid) throws SQLException;

	/**
	 * 
	* @param title - String, 검색된 제목 키워드
	* @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	* @throws SQLException
	* @Method 설명 : 검색된 키워드가 제목에 포함된 문제집을 가져온다.
	* @변경이력 :
	 */
	public List<ProblemSetDto> selectProblemByTitle(String title) throws SQLException;

	/**
	 * 
	* @param problemSet
	* @return int , 등록된 문제집의 갯수 반환
	* @throws SQLException
	* @Method 설명 : 문제집을 생성한다.
	* @변경이력 :
	 */
	public int createProblemSet(ProblemSetDto problemSet) throws SQLException;

	/**
	 * 
	* @param problemSet
	* @return int , 수정한 문제집의 갯수 반환
	* @throws SQLException
	* @Method 설명 : 문제집의 정보를 수정한다.
	* @변경이력 :
	 */
	public int updateProblemSet(ProblemSetDto problemSet) throws SQLException;

	/**
	 * 
	* @param problemSetNo
	* @return int , 삭제한 문제집의 갯수 반환
	* @throws SQLException
	* @Method 설명 : 문제집을 삭제한다.
	* @변경이력 :
	 */
	public int deleteProblemSet(int problemSetNo) throws SQLException;

	/**
	 * 
	* @param map - Map<String, Integer> - problemSetNo, problemNo 포함
	* @return List<ProblemDto> problemSetNo, problemNo이 서로 맞는 List 반환
	* @throws SQLException
	* @Method 설명 :
	* @변경이력 :
	 */
	public List<ProblemDto> selectProblemSetMapping(int problemSetNo) throws SQLException;

	/**
	 * 
	* @param map - Map<String, Integer> - problemSetNo, problemNo 포함
	* @return int 등록된 행의 갯수 반환
	* @throws SQLException
	* @Method 설명 : 문제집에서 문제들을 등록한다.
	* @변경이력 :
	 */
	public int createProblemSetMapping(Map<String, Integer> map) throws SQLException;

	//TODO : 문제집에서 문제에 대한 수정은 등록/삭제 만 이뤄지므로 따로 update 부분은 불필요.
	/**
	 * 
	* @param map - Map<String, Integer> - problemSetNo, problemNo 포함
	* @return int 수정된 행의 갯수 반환
	* @throws SQLException
	* @Method 설명 : 문제집의 문제들의 문제 리스트를 수정한다.
	* @변경이력 :
	 */
	//public int updateProblemSetMapping(Map<String, Integer> map) throws SQLException;

	/**
	 * 
	* @param map - Map<String, Integer> - problemSetNo, problemNo 포함
	* @return int 삭제된 행의 갯수 반환
	* @throws SQLException
	* @Method 설명 : 문제집의 문제들을 삭제한다.
	* @변경이력 :
	 */
	public int deleteProblemSetMapping(Map<String, Integer> map) throws SQLException;
}

package com.solafy.mapper.problem;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.ProblemSetDto;

/**
 * ProblemSet Mapper
 * @author Park Sangwoo
 * @since 2020-12-13 최초생성 / 문제집에 대한 CRUD 생성 
 * @sicne 2020-12-19 문제집의 문제들에 대한 CRUD 생성
 */

@Mapper
public interface ProblemSetMapper {

	/**
	 * 문제집 리스트를 전부 가져온다.
	 * @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	 * @throws SQLException
	 */
	public List<ProblemSetDto> selectAllProblemSet()throws SQLException;

	/**
	 * 문제집 번호에 해당하는 문제집을 가져온다.
	 * @param problemSetNo - int, 문제집 번호
	 * @return ProblemSetDto의 - 번호, 작성자, 제목, 등록시간 포함
	 * @throws SQLException 
	 */
	public ProblemSetDto selectProblemByNo(int problemSetNo)throws SQLException;

	/**
	 * 문제집 작성자에 해당하는 문제집을 가져온다.
	 * @param uid - String, 검색된 uid
	 * @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	 * @throws SQLException
	 */
	public List<ProblemSetDto> selectProblemByWriter(String uid)throws SQLException;
	
	/**
	 * 검색된 키워드가 제목에 포함된 문제집을 가져온다.
	 * @param title - String, 검색된 제목 키워드
	 * @return ProblemSetDto 의 List - 번호, 작성자, 제목, 등록시간 포함
	 * @throws SQLException
	 */
	public List<ProblemSetDto> selectProblemByTitle(String title)throws SQLException;
	
	/**
	 * 문제집을 생성한다.
	 * @param problemSet
	 * @return int , 등록된 문제집의 갯수 반환
	 * @throws SQLException
	 */
	public int createProblemSet(ProblemSetDto problemSet)throws SQLException;

	/**
	 * 문제집의 정보를 수정한다.
	 * @param problemSet
	 * @return int , 수정한 문제집의 갯수 반환
	 * @throws SQLException
	 */
	public int updateProblemSet(ProblemSetDto problemSet)throws SQLException;

	/**
	 * 문제집을 삭제한다.
	 * @param problemSetNo
	 * @return int , 삭제한 문제집의 갯수 반환
	 * @throws SQLException
	 */
	public int deleteProblemSet(int problemSetNo)throws SQLException;
	
	/**
	 * 문제집에서 문제들의 리스트를 등록한다.
	 * @param map - Map<String, Integer> - problemSetNo, problemNo 포함
	 * @return 문제집의 리스트에 해당하는 ProblemSetDto를 가져온다. 
	 * @throws SQLException
	 */
	public ProblemSetDto selectProblemSetMapping(Map<String, Integer> map)throws SQLException;
	
	/**
	 * 문제집에서 문제들을 등록한다.
	 * @param map - Map<String, Integer> - problemSetNo, problemNo 포함
	 * @return int 수정된 행의 갯수 반환
	 * @throws SQLException
	 */
	public int createProblemSetMapping(Map<String, Integer> map)throws SQLException;
	
	/**
	 * 문제집의 문제들의 문제 리스트를 수정한다. 
	 * @param map - Map<String, Integer> - problemSetNo, problemNo 포함
	 * @return int 수정된 행의 갯수 반환
	 * @throws SQLException
	 */
	public int updateProblemSetMapping(Map<String, Integer> map)throws SQLException;
	
	/**
	 * 문제집의 문제들을 삭제한다.
	 * @param map - Map<String, Integer> - problemSetNo, problemNo 포함
	 * @return int 삭제된 행의 갯수 반환
	 * @throws SQLException
	 */
	public int deleteProblemSetMapping(Map<String, Integer> map)throws SQLException;
}

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
 * @sicne 문제집의 문제들에 대한 CRUD 생성
 */

@Mapper
public interface ProblemSetMapper {

	/**
	 * 문제집 리스트를 전부 가져온다.
	 * @return
	 * @throws SQLException
	 */
	public List<ProblemSetDto> selectAllProblemSet()throws SQLException;

	/**
	 * 문제집 번호에 해당하는 문제집을 가져온다.
	 * @param problemSetNo
	 * @return
	 * @throws SQLException
	 */
	public ProblemSetDto selectProblemByNo(int problemSetNo)throws SQLException;

	/**
	 * 문제집을 생성한다.
	 * @param problemSet
	 * @return
	 * @throws SQLException
	 */
	public int createProblemSet(ProblemSetDto problemSet)throws SQLException;

	/**
	 * 문제집의 정보를 수정한다.
	 * @param problemSet
	 * @return
	 * @throws SQLException
	 */
	public int updateProblemSet(ProblemSetDto problemSet)throws SQLException;

	/**
	 * 문제집을 삭제한다.
	 * @param problemSetNo
	 * @return
	 * @throws SQLException
	 */
	public int deleteProblemSet(int problemSetNo)throws SQLException;
	
	/**
	 * 문제집에서 문제들의 리스트를 등록한다.
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int selectProblemSetMapping(Map<String, Integer> map)throws SQLException;
	
	/**
	 * 문제집에서 문제들을 등록한다.
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int createProblemSetMapping(Map<String, Integer> map)throws SQLException;
	
	/**
	 * 문제집의 문제들의 문제 리스트를 수정한다. 
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int updateProblemSetMapping(Map<String, Integer> map)throws SQLException;
	
	/**
	 * 문제집의 문제들을 삭제한다.
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int deleteProblemSetMapping(Map<String, Integer> map)throws SQLException;
}

package com.solafy.mapper.problem;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.ProblemAnswerDto;

/**
* @FileName : ProblemAnswerMapper.java
* @Project : SOLAFY
* @Date : 2020. 12. 13.
* @작성자 : Lee Ayoung

* @변경이력 :
* @프로그램 설명 : Problem Answer Mapper
*/
@Mapper
public interface ProblemAnswerMapper {
	/**
	* @param problemNo - 한 문제 번호
	* @return ProblemAnswerDto - 한 문제의 정답 : problemNo, answer, solution, keyword
	* @throws SQLException
	* @Method 설명 : 문제 정답 반환
	* @변경이력 :
	*/
	public ProblemAnswerDto selectProblemAnswer(int problemNo) throws SQLException;

	/**
	* @param problemAnswerDto - 한 문제의 정답 : problemNo, answer, solution, keyword
	* @return int - 등록된 행의 개수 반환
	* @throws SQLException
	* @Method 설명 : 문제 정답 등록
	* @변경이력 :
	*/
	public int createProblemAnswer(ProblemAnswerDto problemAnswerDto) throws SQLException;
	
	/**
	* @param problemAnswerDto - 한 문제의 정답 : problemNo, answer, solution, keyword
	* @return int - 수정된 행의 개수 반환
	* @throws SQLException
	* @Method 설명 : 문제 정답 수정
	* @변경이력 :
	*/
	public int updateProblemAnswer(ProblemAnswerDto problemAnswerDto) throws SQLException;

	/**
	* @param problemNo - 한 문제 번호
	* @return int - 삭제된 행의 개수 반환
	* @throws SQLException
	* @Method 설명 : 문제 정답 삭제
	* @변경이력 :
	*/
	public int deleteProblemAnswer(int problemNo) throws SQLException;
}

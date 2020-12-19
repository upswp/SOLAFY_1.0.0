package com.solafy.mapper.problem;

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
	 * 문제 정답 반환
	 * @param problemNo - 한 문제 번호
	 * @return problemNo에 해당하는 ProblemAnswerDto 반환 - 전체 column
	 */
	public ProblemAnswerDto selectProblemAnswer(int problemNo);

	/**
	 * 문제 정답 등록
	 * @param problemAnswerDto - 한 문제의 정답
	 * @return int : 등록된 행의 개수 반환
	 */
	public int createProblemAnswer(ProblemAnswerDto problemAnswerDto);
	
	/**
	 * 문제 정답 수정
	 * @param problemAnswerDto - 한 문제의 정답
	 * @return int : 수정된 행의 개수 반환
	 */
	public int updateProblemAnswer(ProblemAnswerDto problemAnswerDto);

	/**
	 * 문제 정답 삭제
	 * @param problemNo - 한 문제 번호
	 * @return int : 삭제된 행의 개수 반환
	 */
	public int deleteProblemAnswer(int problemNo);
}

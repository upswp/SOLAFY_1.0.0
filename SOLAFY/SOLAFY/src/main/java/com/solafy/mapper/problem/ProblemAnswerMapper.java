package com.solafy.mapper;

/**
 * ProblemAnswerMapper
 * @author Lee AYoung
 * @since 2020-12-13
 */
import com.solafy.model.ProblemAnswerDto;

public interface ProblemAnswerMapper {
	/**
	 * 문제 정답 반환
	 * @param problemNo - 한 문제 번호
	 * @return problemNo에 해당하는 ProblemAnswerDto 반환 - 전체 column
	 */
	ProblemAnswerDto selectProblemAnswer(int problemNo);

	/**
	 * 문제 정답 등록
	 * @param problemAnswerDto - 한 문제의 정답
	 * @return int : 등록된 행의 개수 반환
	 */
	int insertProblemAnswer(ProblemAnswerDto problemAnswerDto);

	/**
	 * 문제 정답 수정
	 * @param problemAnswerDto - 한 문제의 정답
	 * @return int : 수정된 행의 개수 반환
	 */
	int updateProblemAnswer(ProblemAnswerDto problemAnswerDto);

	/**
	 * 문제 정답 삭제
	 * @param problemNo - 한 문제 번호
	 * @return int : 삭제된 행의 개수 반환
	 */
	int deleteProblemAnswer(int problemNo);
}

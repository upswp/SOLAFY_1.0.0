package com.solafy.mapper;
/**
 * ProblemAnswerMapper
 * @author Lee AYoung
 * @since 2020-12-13
 */
import com.solafy.model.ProblemAnswerDto;

public interface ProblemAnswerMapper {
	// 문제 정답 반환
	ProblemAnswerDto selectProblemAnswer(int problemNo);
	// 문제 정답 등록
	int insertProblemAnswer(ProblemAnswerDto problemAnswerDto);
	// 문제 정답 수정
	int updateProblemAnswer(ProblemAnswerDto problemAnswerDto);
	// 문제 정답 삭제
	int deleteProblemAnswer(int problemNo);
}

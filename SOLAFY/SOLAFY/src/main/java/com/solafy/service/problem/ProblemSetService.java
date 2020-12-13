package com.solafy.service.problem;

import java.util.List;

import com.solafy.model.ProblemSetDto;
/**
 * ProblemService
 * 문제집 interface
 * @author Park Sangwoo
 * @since 2020-12-13
 */
public interface ProblemSetService {

	public List<ProblemSetDto> selectAllProblemSet();
	
	public ProblemSetDto selectProblemByNo(int problemSetNo);
	
	public boolean createProblemSet(ProblemSetDto problemSet);

	public boolean updateProblemSet(ProblemSetDto problemSet);

	public boolean deleteProblemSet(int problemSetNo);
	
}

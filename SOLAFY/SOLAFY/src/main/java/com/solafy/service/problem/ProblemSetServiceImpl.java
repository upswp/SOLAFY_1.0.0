package com.solafy.service.problem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.problem.ProblemSetMapper;
import com.solafy.model.ProblemSetDto;

/**
 * ProblemSetServiceImpl 문제에 관한 문제집 기능 구현
 * 
 * @author Park Sangwoo
 * @since 2020-12-13
 */
@Service
public class ProblemSetServiceImpl implements ProblemSetService {

	@Autowired
	private ProblemSetMapper problemSetMapper;

	@Override
	public List<ProblemSetDto> selectAllProblemSet() {
		return problemSetMapper.selectAllProblemSet();
	}

	@Override
	public ProblemSetDto selectProblemByNo(int problemSetNo) {
		return problemSetMapper.selectProblemByNo(problemSetNo);
	}

	@Override
	public boolean createProblemSet(ProblemSetDto problemSet) {
		return problemSetMapper.createProblemSet(problemSet) == 1;
	}

	@Override
	public boolean updateProblemSet(ProblemSetDto problemSet) {
		return problemSetMapper.updateProblemSet(problemSet) == 1;
	}

	@Override
	public boolean deleteProblemSet(int problemSetNo) {
		return problemSetMapper.deleteProblemSet(problemSetNo) == 1;
	}

}

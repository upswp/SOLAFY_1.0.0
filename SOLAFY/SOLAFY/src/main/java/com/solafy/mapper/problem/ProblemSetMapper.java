package com.solafy.mapper.problem;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.ProblemSetDto;

/**
 * ProblemSet Mapper
 * @author Park Sangwoo
 * @since 2020-12-13
 */

@Mapper
public interface ProblemSetMapper {

	public List<ProblemSetDto> selectAllProblemSet();

	public ProblemSetDto selectProblemByNo(int problemSetNo);

	public int createProblemSet(ProblemSetDto problemSet);

	public int updateProblemSet(ProblemSetDto problemSet);

	public int deleteProblemSet(int problemSetNo);

}

package com.solafy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.ProblemSetDto;

@Mapper
public interface ProblemSetMapper {

	public List<ProblemSetDto> selectAllProblemSet();

	public ProblemSetDto selectProblemByNo(int problemSetNo);

	public int createProblemSet(ProblemSetDto problemSet);

	public int updateProblemSet(ProblemSetDto problemSet);

	public int deleteProblemSet(int problemSetNo);

}

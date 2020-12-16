package com.solafy.service.problem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.solafy.mapper.problem.ProblemMapper;
import com.solafy.model.CategoryLargeDto;
import com.solafy.model.CategoryMediumDto;
import com.solafy.model.CategorySmallDto;
import com.solafy.model.HashTagDto;
import com.solafy.model.ProblemDto;

/**
 * ProblemService
 * @author Lee AYoung
 * @since 2020-12-16
 */
public class ProblemServiceImpl implements ProblemService {

	@Autowired
	private ProblemMapper problemMapper;
	
	@Override
	public ProblemDto selectProblem(int problemNo) {
		return problemMapper.selectProblem(problemNo);
	}

	@Override
	public CategorySmallDto selectCategorySmall(int categorySmallNo) {
		return problemMapper.selectCategorySmall(categorySmallNo);
	}

	@Override
	public CategoryMediumDto selectCategoryMedium(int categoryMediumNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryLargeDto selectCategoryLarge(int categoryLargeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HashTagDto> selectHashtagList(int problemNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProblemDto> selectProblemByHashTag(int hashTagNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProblemDto> selectProblemByName(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProblemDto> selectProblemByCategorySmall(int categoryNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProblemDto> selectProblemByCategoryMedium(int categoryLargeMediumNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProblemDto> selectProblemByCategoryLarge(int categoryLargeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertProblem(ProblemDto problemDto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateProblem(ProblemDto problemDto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteProblem(int problemNo) {
		// TODO Auto-generated method stub
		return 0;
	}

}

package com.solafy.service.problem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.problem.ProblemMapper;
import com.solafy.model.CategoryLargeDto;
import com.solafy.model.CategoryMediumDto;
import com.solafy.model.CategorySmallDto;
import com.solafy.model.HashTagDto;
import com.solafy.model.ProblemDto;

/**
* @FileName : ProblemServiceImpl.java
* @Project : SOLAFY
* @Date : 2020. 12. 16.
* @작성자 : Lee Ayoung

* @변경이력 :
* @프로그램 설명 : Problem Service Implement Class
*/
@Service
public class ProblemServiceImpl implements ProblemService {

	@Autowired
	private ProblemMapper problemMapper;
	
	@Override
	public ProblemDto selectProblem(int problemNo) {
		return problemMapper.selectProblem(problemNo);
	}

	@Override
	public CategorySmallDto selectCategorySmall(String categoryNo) {
		int categorySmallNo = Integer.parseInt(categoryNo.substring(5, 10));
		return problemMapper.selectCategorySmall(categorySmallNo);
	}

	@Override
	public CategoryMediumDto selectCategoryMedium(String categoryNo) {
		int categoryMediumNo = Integer.parseInt(categoryNo.substring(2, 5));
		return problemMapper.selectCategoryMedium(categoryMediumNo);
	}

	@Override
	public CategoryLargeDto selectCategoryLarge(String categoryNo) {
		int categoryLargeNo = Integer.parseInt(categoryNo.substring(0, 2));
		return problemMapper.selectCategoryLarge(categoryLargeNo);
	}

	@Override
	public List<HashTagDto> selectHashTagList(int problemNo) {
		return problemMapper.selectHashTagList(problemNo);
	}
	
	@Override
	public List<ProblemDto> selectProblemByHashTag(int hashTagNo) {
		return problemMapper.selectProblemByHashTag(hashTagNo);
	}

	@Override
	public List<ProblemDto> selectProblemByCategorySmall(String categoryNo) {
		return problemMapper.selectProblemByCategorySmall(categoryNo);
	}

	@Override
	public List<ProblemDto> selectProblemByCategoryMedium(String categoryLargeMediumNo) {
		return problemMapper.selectProblemByCategoryMedium(categoryLargeMediumNo);
	}

	@Override
	public List<ProblemDto> selectProblemByCategoryLarge(String categoryLargeNo) {
		return problemMapper.selectProblemByCategoryLarge(categoryLargeNo);
	}
	
	@Override
	public List<ProblemDto> selectProblemByKeyword(String type, String keyword) {
		List<ProblemDto> list;
		if("제목".contains(type)) {
			list = problemMapper.selectProblemByName(keyword);
		}
		else if("문제번호".contains(type)) {
			list = problemMapper.selectProblemByProblemNo(Integer.parseInt(keyword));
		}
		else {
			list = null;
		}
		return list;
	}

	@Override
	public boolean createProblem(ProblemDto problemDto) {
		return problemMapper.createProblem(problemDto) > 0;
	}

	@Override
	public boolean updateProblem(ProblemDto problemDto) {
		return problemMapper.updateProblem(problemDto) > 0;
	}

	// TODO : 문제 삭제했을 때 다른것도 삭제되도록 불러와야할거 같음
	// ex)모의고사, 대회, 문제집 ...

	@Override
	public boolean deleteProblem(int problemNo) {
		return problemMapper.deleteProblem(problemNo) > 0;
	}
}

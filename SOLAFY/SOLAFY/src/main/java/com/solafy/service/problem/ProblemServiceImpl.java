package com.solafy.service.problem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.problem.ProblemAnswerMapper;
import com.solafy.mapper.problem.ProblemMapper;
import com.solafy.model.HashTagDto;
import com.solafy.model.ProblemAnswerDto;
import com.solafy.model.ProblemDto;

/**
 * @FileName : ProblemServiceImpl.java
 * @Project : SOLAFY
 * @Date : 2020. 12. 16.
 * @작성자 : Lee Ayoung
 * 
 * @변경이력 :
 * @프로그램 설명 : Problem Service Implement Class
 */
@Service
public class ProblemServiceImpl implements ProblemService {

	@Autowired
	private ProblemMapper problemMapper;
	
	@Autowired
	private ProblemAnswerMapper problemAnswerMapper;

	@Override
	public Map<String, Object> selectProblem(int problemNo) {
		Map<String, Object> map = new HashMap<String, Object>();

		// ProbleDto
		ProblemDto problem = problemMapper.selectProblem(problemNo);
		map.put("problem", problem);

		// categorySmallNo
		String categoryNo = problem.getCategoryNo();
		int categorySmallNo = Integer.parseInt(categoryNo.substring(5, 10));
		map.put("categorySmall", problemMapper.selectCategorySmall(categorySmallNo));

		// CategoryMediumDto
		int categoryMediumNo = Integer.parseInt(categoryNo.substring(2, 5));
		map.put("categoryMedium", problemMapper.selectCategoryMedium(categoryMediumNo));

		// CategoryLargeDto
		int categoryLargeNo = Integer.parseInt(categoryNo.substring(0, 2));
		map.put("categoryLarge", problemMapper.selectCategoryLarge(categoryLargeNo));

		// List<HashTagDto>
		map.put("hashTag", problemMapper.selectHashTagList(problemNo));

		return map;
	}

//	@Override
//	public CategorySmallDto selectCategorySmall(String categoryNo) {
//		int categorySmallNo = Integer.parseInt(categoryNo.substring(5, 10));
//		return problemMapper.selectCategorySmall(categorySmallNo);
//	}
//
//	@Override
//	public CategoryMediumDto selectCategoryMedium(String categoryNo) {
//		int categoryMediumNo = Integer.parseInt(categoryNo.substring(2, 5));
//		return problemMapper.selectCategoryMedium(categoryMediumNo);
//	}
//
//	@Override
//	public CategoryLargeDto selectCategoryLarge(String categoryNo) {
//		int categoryLargeNo = Integer.parseInt(categoryNo.substring(0, 2));
//		return problemMapper.selectCategoryLarge(categoryLargeNo);
//	}
//
//	@Override
//	public List<HashTagDto> selectHashTagList(int problemNo) {
//		return problemMapper.selectHashTagList(problemNo);
//	}

	@Override
	public List<ProblemDto> selectProblemByHashTag(int hashTagNo) {
		return problemMapper.selectProblemByHashTag(hashTagNo);
	}

	@Override
	public List<ProblemDto> selectProblemByCategory(String categoryNo) {
		// 소분류 카테고리를 이용한 문제 검색 (소+중+대 번호 전달)
		if (categoryNo.length() == 10) {
			return problemMapper.selectProblemByCategorySmall(categoryNo);
		}
		// 중분류 카테고리를 이용한 문제 검색 (중+대 번호 전달)
		else if (categoryNo.length() == 5) {
			return problemMapper.selectProblemByCategoryMedium(categoryNo);
		}
		// 대분류 카테고리를 이용한 문제 검색 (대 번호 전달)
		else if (categoryNo.length() == 2){
			return problemMapper.selectProblemByCategoryLarge(categoryNo);
		}
		// 다른 길이
		else {
			return null;
		}
	}

//	@Override
//	public List<ProblemDto> selectProblemByCategorySmall(String categoryNo) {
//		return problemMapper.selectProblemByCategorySmall(categoryNo);
//	}
//
//	@Override
//	public List<ProblemDto> selectProblemByCategoryMedium(String categoryLargeMediumNo) {
//		return problemMapper.selectProblemByCategoryMedium(categoryLargeMediumNo);
//	}
//
//	@Override
//	public List<ProblemDto> selectProblemByCategoryLarge(String categoryLargeNo) {
//		return problemMapper.selectProblemByCategoryLarge(categoryLargeNo);
//	}

	@Override
	public List<ProblemDto> selectProblemByKeyword(String type, String keyword) {
		if ("제목".contains(type)) {
			return problemMapper.selectProblemByName(keyword);
		} else if ("문제번호".contains(type)) {
			return problemMapper.selectProblemByProblemNo(Integer.parseInt(keyword));
		} else if ("작성자".contains(type)) {
			return problemMapper.selectProblemByUserNickname(keyword);
		} else {
			return null;
		}
	}

	// TODO : 해쉬태그관련
	// 없는 걸 등록했다 -> 해쉬태그 생성
	// 있는 걸 등록했다 -> mappingtable연결
	// 이미 등록 되어있는 해쉬태그를 없앤다 -> mappingtable삭제
	
	@Override
	public boolean createProblem(ProblemDto problemDto, List<HashTagDto> hashTagList, ProblemAnswerDto problemAnswerDto) {
		boolean result = (problemMapper.createProblem(problemDto) > 0);
		for (HashTagDto hashTagDto : hashTagList) {
			result &= (problemMapper.createHashTagMapping(hashTagDto) > 0);
		}
		result &= (problemAnswerMapper.createProblemAnswer(problemAnswerDto)>0);
		
		return result;
	}

	@Override
	public boolean updateProblem(ProblemDto problemDto) {
		return problemMapper.updateProblem(problemDto) > 0;
	}

	@Override
	public boolean deleteProblem(int problemNo) {
		return problemMapper.deleteProblem(problemNo) > 0;
	}
}

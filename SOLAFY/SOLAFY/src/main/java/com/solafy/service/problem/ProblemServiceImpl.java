package com.solafy.service.problem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solafy.mapper.problem.HashTagMapper;
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

	@Autowired
	private HashTagMapper hashTagMapper;

	@Override
	public HashMap<String, Object> selectProblem(int problemNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();

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
		else if (categoryNo.length() == 2) {
			return problemMapper.selectProblemByCategoryLarge(categoryNo);
		}
		// 다른 길이
		else {
			return null;
		}
	}

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

	// 해쉬태그관련
	// 없는 걸 등록한다 -> 해쉬태그 생성
	// 있는 걸 등록한다 -> mappingtable연결
	//@RequestBody ProblemDto problemDto, @RequestBody ProblemAnswerDto problemAnswerDto, @RequestBody List<String> hashTagList
	@Override
	public boolean createProblem(HashMap<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		ProblemDto problemDto = mapper.convertValue(map.get("problem"),new TypeReference<ProblemDto>() {});
		ProblemAnswerDto problemAnswerDto = mapper.convertValue(map.get("problemAnswer"),new TypeReference<ProblemAnswerDto>() {});
		List<String> hashTagList = mapper.convertValue(map.get("hashTag"),new TypeReference<List<String>>() {});
		
		// 문제 등록
		boolean result = (problemMapper.createProblem(problemDto) > 0);
		//System.out.println("problemNo : "+problemDto.getProblemNo());
		
		// 문제 번호를 받아서 정답에 넣어줘야하는데 어떻게 받아올 것인가? --> answer : problem.xml참고
		problemDto.setProblemNo(problemDto.getProblemNo());
		problemAnswerDto.setProblemNo(problemDto.getProblemNo());
		// 문제 답 등록
		result &= (problemAnswerMapper.createProblemAnswer(problemAnswerDto) > 0);
		
		// 해시태그 매핑
		for (String hashTag : hashTagList) {
			HashTagDto hashTagDto;
			// hashTag 테이블에 존재하지 않으면 hashTag 테이블에 등록
			if ((hashTagDto = hashTagMapper.selectHashTagNoByHashTag(hashTag)) == null) {
				result &= (hashTagMapper.createHashTag(hashTag) > 0);
				hashTagDto = hashTagMapper.selectHashTagNoByHashTag(hashTag);
			}
			// 문제와 해시태그를 mapping 테이블을 통해 연결
			result &= (problemMapper.createHashTagMapping(problemDto.getProblemNo(), hashTagDto.getHashTagNo()) > 0);
		}

		return result;
	}

	// TODO : 새로운 DB 넣고, update구성하고, create부분하기
	@Override
	public boolean updateProblem(ProblemDto problemDto, ProblemAnswerDto problemAnswerDto, List<String> hashTagList) {
		
		return problemMapper.updateProblem(problemDto) > 0;
	}

	@Override
	public boolean deleteProblem(int problemNo) {
		return problemMapper.deleteProblem(problemNo) > 0;
	}
}

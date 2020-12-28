package com.solafy.service.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solafy.mapper.board.PracticeTestBoardMapper;
import com.solafy.model.PracticeTestBoardDto;
import com.solafy.model.ProblemDto;

/**
 * 
 * @FileName PracticeTestBoardServiceImpl.java
 * @Project SOLAFY
 * @Date 2020. 12. 14.
 * @author 이주희
 *
 * @변경이력
 */
@Service
public class PracticeTestBoardServiceImpl implements PracticeTestBoardService {

	@Autowired
	private PracticeTestBoardMapper practiceTestMapper;

	@Override
	public boolean createPracticeTest(PracticeTestBoardDto pDto) throws Exception {
		if (pDto.getProblems().size() <= 0)
			return false;
		List<ProblemDto> problems = pDto.getProblems();
		// 모의고사 정보 등록
		practiceTestMapper.createPracticeTest(pDto);
		System.out.println("articleNo  " + pDto.getArticleNo());

		// 모의고사와 문제들 매핑
		Map<String, Integer> map = new HashMap<>();
		map.put("articleNo", pDto.getArticleNo());
		for (ProblemDto problem : problems) {
			System.out.println(problem.getProblemNo());
			map.put("problemNo", problem.getProblemNo());
			practiceTestMapper.createPracticeTestMapping(map);
		}

		return true;
	}

	@Transactional
	@Override
	public List<PracticeTestBoardDto> selectAllPracticeTest() throws Exception {
		return practiceTestMapper.selectAllPracticeTest();
	}

	@Transactional
	@Override
	public PracticeTestBoardDto selectPracticeTestByArticleNo(int articleNo) throws Exception {
		return practiceTestMapper.selectPracticeTestByArticleNo(articleNo);
	}

	@Transactional
	@Override
	public List<PracticeTestBoardDto> selectPracticeTestByWriter(String uid) throws Exception {
		return practiceTestMapper.selectPracticeTestByWriter(uid);
	}

	@Transactional
	@Override
	public List<PracticeTestBoardDto> selectPracticeTestByTitle(String title) throws Exception {
		return practiceTestMapper.selectPracticeTestByTitle("%" + title + "%");
	}

	@Transactional
	@Override
	public boolean updatePracticeTest(PracticeTestBoardDto pDto) throws Exception {
		if (pDto.getProblems().size() <= 0)
			return false;

		// 모의고사 정보 갱신
		practiceTestMapper.updatePracticeTest(pDto);

		// 모의고사와 문제들 매핑 삭제
		practiceTestMapper.deletePracticeTestMapping(pDto.getArticleNo());

		// 모의고사와 문제들 매핑 등록
		Map<String, Integer> map = new HashMap<>();
		map.put("articleNo", pDto.getArticleNo());
		map.put("problemNo", pDto.getProblems().get(0).getProblemNo());
		for (int i = 0; i < pDto.getProblems().size(); i++) {
			map.replace("problemNo", pDto.getProblems().get(i).getProblemNo());
			practiceTestMapper.createPracticeTestMapping(map);
		}

		return true;
	}

	@Transactional
	@Override
	public boolean deleltePracticeTest(int articleNo) throws Exception {
		// 모의고사와 문제들 매핑 삭제
		practiceTestMapper.deletePracticeTestMapping(articleNo);

		// 모의고사 삭제
		practiceTestMapper.deleltePracticeTest(articleNo);

		return true;
	}

}

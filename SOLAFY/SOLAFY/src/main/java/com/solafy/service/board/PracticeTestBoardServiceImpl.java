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

	@Transactional
	@Override
	public boolean createPracticeTest(PracticeTestBoardDto pDto) throws Exception {
		if (pDto.getProblems().size() <= 0)
			return false;

		// 모의고사 정보 등록
		practiceTestMapper.createPracticeTest(pDto);

		// 모의고사와 문제들 매핑
		Map<String, Integer> map = new HashMap<>();
		map.put("articleNo", pDto.getPracticeNo());
		for (ProblemDto problem : pDto.getProblems()) {
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
	public List<PracticeTestBoardDto> selectPracticeTestByPracticeNo(int practiceNo) throws Exception {
		return practiceTestMapper.selectPracticeTestByPracticeNo(practiceNo);
	}

	@Transactional
	@Override
	public List<PracticeTestBoardDto> selectPracticeTestByWriter(String nickname) throws Exception {
		return practiceTestMapper.selectPracticeTestByWriter("%" + nickname + "%");
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
		practiceTestMapper.deletePracticeTestMapping(pDto.getPracticeNo());

		// 모의고사와 문제들 매핑 등록
		Map<String, Integer> map = new HashMap<>();
		map.put("practiceNo", pDto.getPracticeNo());
		map.put("problemNo", pDto.getProblems().get(0).getProblemNo());
		for (int i = 0; i < pDto.getProblems().size(); i++) {
			map.replace("problemNo", pDto.getProblems().get(i).getProblemNo());
			practiceTestMapper.createPracticeTestMapping(map);
		}

		return true;
	}

	@Transactional
	@Override
	public boolean deleltePracticeTest(int practiceNo) throws Exception {
		// 모의고사와 문제들 매핑 삭제
		practiceTestMapper.deletePracticeTestMapping(practiceNo);

		// 모의고사 삭제
		practiceTestMapper.deleltePracticeTest(practiceNo);

		return true;
	}

}

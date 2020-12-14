package com.solafy.service.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solafy.mapper.board.PracticeTestBoardMapper;
import com.solafy.model.PracticeTestBoardDto;

@Service
public class PracticeTestBoardServiceImpl implements PracticeTestBoardService {

	@Autowired
	private SqlSession sqlSession;

	@Transactional
	@Override
	public boolean createPracticeTest(PracticeTestBoardDto pDto) throws Exception {
		if (pDto.getProblems().size() <= 0)
			return false;

		// 모의고사 정보 등록
		sqlSession.getMapper(PracticeTestBoardMapper.class).createPracticeTest(pDto);

		// 모의고사와 문제들 매핑
		Map<String, Integer> map = new HashMap<>();
		map.put("articleNo", pDto.getArticleNo());
		map.put("problemNo", pDto.getProblems().get(0).getProblemNo());
		for (int i = 0; i < pDto.getProblems().size(); i++) {
			map.replace("problemNo", pDto.getProblems().get(i).getProblemNo());
			sqlSession.getMapper(PracticeTestBoardMapper.class).createPracticeTestMapping(map);
		}

		return true;
	}

	@Transactional
	@Override
	public List<PracticeTestBoardDto> selectAllPracticeTest() throws Exception {
		return sqlSession.getMapper(PracticeTestBoardMapper.class).selectAllPracticeTest();
	}

	@Transactional
	@Override
	public PracticeTestBoardDto selectPracticeTestByArticleNo(int articleNo) throws Exception {
		return sqlSession.getMapper(PracticeTestBoardMapper.class).selectPracticeTestByArticleNo(articleNo);
	}

	@Transactional
	@Override
	public List<PracticeTestBoardDto> selectPracticeTestByWriter(String uid) throws Exception {
		return sqlSession.getMapper(PracticeTestBoardMapper.class).selectPracticeTestByWriter(uid);
	}

	@Transactional
	@Override
	public List<PracticeTestBoardDto> selectPracticeTestByTitle(String title) throws Exception {
		title = "%" + title + "%";
		return sqlSession.getMapper(PracticeTestBoardMapper.class).selectPracticeTestByTitle(title);
	}

	@Transactional
	@Override
	public boolean updatePracticeTest(PracticeTestBoardDto pDto) throws Exception {
		if (pDto.getProblems().size() <= 0)
			return false;

		// 모의고사 정보 갱신
		sqlSession.getMapper(PracticeTestBoardMapper.class).updatePracticeTest(pDto);

		// 모의고사와 문제들 매핑 삭제
		sqlSession.getMapper(PracticeTestBoardMapper.class).deletePracticeTestMapping(pDto.getArticleNo());

		// 모의고사와 문제들 매핑 등록
		Map<String, Integer> map = new HashMap<>();
		map.put("articleNo", pDto.getArticleNo());
		map.put("problemNo", pDto.getProblems().get(0).getProblemNo());
		for (int i = 0; i < pDto.getProblems().size(); i++) {
			map.replace("problemNo", pDto.getProblems().get(i).getProblemNo());
			sqlSession.getMapper(PracticeTestBoardMapper.class).createPracticeTestMapping(map);
		}

		return true;
	}

	@Transactional
	@Override
	public boolean deleltePracticeTest(int articleNo) throws Exception {
		// 모의고사와 문제들 매핑 삭제
		sqlSession.getMapper(PracticeTestBoardMapper.class).deletePracticeTestMapping(articleNo);

		// 모의고사 삭제
		sqlSession.getMapper(PracticeTestBoardMapper.class).deleltePracticeTest(articleNo);

		return true;
	}

}

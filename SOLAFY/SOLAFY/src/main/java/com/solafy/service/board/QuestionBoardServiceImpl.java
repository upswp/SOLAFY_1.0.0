package com.solafy.service.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.board.QuestionBoardMapper;
import com.solafy.model.QuestionBoardDto;

@Service
public class QuestionBoardServiceImpl implements QuestionBoardService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean createQuestion(QuestionBoardDto qDto) throws Exception {
		int result = sqlSession.getMapper(QuestionBoardMapper.class).createQuestion(qDto);
		if (result != 1)
			return false;
		return true;
	}

	@Override
	public List<QuestionBoardDto> selectAllQuestions() throws Exception {
		return sqlSession.getMapper(QuestionBoardMapper.class).selectAllQuestions();
	}

	@Override
	public QuestionBoardDto selectQuestionsByArticleNo(int articleNo) throws Exception {
		return sqlSession.getMapper(QuestionBoardMapper.class).selectQuestionsByArticleNo(articleNo);
	}

	@Override
	public List<QuestionBoardDto> selectQuestionsByTitle(String keyword) throws Exception {
		return sqlSession.getMapper(QuestionBoardMapper.class).selectQuestionsByTitle(keyword);
	}

	@Override
	public List<QuestionBoardDto> selectQuestionsByWriter(String uid) throws Exception {
		return sqlSession.getMapper(QuestionBoardMapper.class).selectQuestionsByWriter(uid);
	}

	@Override
	public List<QuestionBoardDto> selectQuestionsByProblemNo(int problemNo) throws Exception {
		return sqlSession.getMapper(QuestionBoardMapper.class).selectQuestionsByProblemNo(problemNo);
	}

	@Override
	public boolean updateQuestion(QuestionBoardDto qDto) throws Exception {
		int result = sqlSession.getMapper(QuestionBoardMapper.class).updateQuestion(qDto);
		if(result != 1)
			return false;
		return true;
	}

	@Override
	public boolean deleteQuestion(int articleNo) throws Exception {
		int result = sqlSession.getMapper(QuestionBoardMapper.class).deleteQuestion(articleNo);
		if(result != 1)
			return false;
		return true;
	}

}

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
	private QuestionBoardMapper questionMapper;

	@Override
	public boolean createQuestion(QuestionBoardDto qDto) throws Exception {
		int result = questionMapper.createQuestion(qDto);
		if (result != 1)
			return false;
		return true;
	}

	@Override
	public List<QuestionBoardDto> selectAllQuestions() throws Exception {
		return questionMapper.selectAllQuestions();
	}

	@Override
	public QuestionBoardDto selectQuestionsByArticleNo(int articleNo) throws Exception {
		return questionMapper.selectQuestionsByArticleNo(articleNo);
	}

	@Override
	public List<QuestionBoardDto> selectQuestionsByTitle(String keyword) throws Exception {
		return questionMapper.selectQuestionsByTitle(keyword);
	}

	@Override
	public List<QuestionBoardDto> selectQuestionsByWriter(String uid) throws Exception {
		return questionMapper.selectQuestionsByWriter(uid);
	}

	@Override
	public List<QuestionBoardDto> selectQuestionsByProblemNo(int problemNo) throws Exception {
		return questionMapper.selectQuestionsByProblemNo(problemNo);
	}

	@Override
	public boolean updateQuestion(QuestionBoardDto qDto) throws Exception {
		int result = questionMapper.updateQuestion(qDto);
		if(result != 1)
			return false;
		return true;
	}

	@Override
	public boolean deleteQuestion(int articleNo) throws Exception {
		int result = questionMapper.deleteQuestion(articleNo);
		if(result != 1)
			return false;
		return true;
	}

}

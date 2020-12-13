package com.solafy.service.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.board.QuestionReplyMapper;
import com.solafy.model.AnswerByQuestionDto;

@Service
public class QuestionReplyServiceImpl implements QuestionReplyService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean createAnswer(AnswerByQuestionDto aDto) throws Exception {
		int result = sqlSession.getMapper(QuestionReplyMapper.class).createAnswer(aDto);
		if(result != 1)
			return false;
		return true;
	}

	@Override
	public List<AnswerByQuestionDto> selectAnswersByQuestionNo(int qestionNo) throws Exception {
		return sqlSession.getMapper(QuestionReplyMapper.class).selectAnswersByQuestionNo(qestionNo);
	}

	@Override
	public AnswerByQuestionDto selectAnswerByArticleNo(int articleNo) throws Exception {
		return sqlSession.getMapper(QuestionReplyMapper.class).selectAnswerByArticleNo(articleNo);
	}

	@Override
	public boolean updateAnswer(AnswerByQuestionDto aDto) throws Exception {
		int result = sqlSession.getMapper(QuestionReplyMapper.class).updateAnswer(aDto);
		if(result != 1)
			return false;
		return true;
	}

	@Override
	public boolean deleteAnswer(int articleNo) throws Exception {
		int result = sqlSession.getMapper(QuestionReplyMapper.class).deleteAnswer(articleNo);
		if(result != 1)
			return false;
		return true;
	}

}

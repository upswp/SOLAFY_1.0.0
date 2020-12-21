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
	private QuestionReplyMapper answerMapper;

	@Override
	public boolean createAnswer(AnswerByQuestionDto aDto) throws Exception {
		int result = answerMapper.createAnswer(aDto);
		if (result != 1)
			return false;
		return true;
	}

	@Override
	public List<AnswerByQuestionDto> selectAnswersByQuestionNo(int qestionNo) throws Exception {
		return answerMapper.selectAnswersByQuestionNo(qestionNo);
	}

	@Override
	public AnswerByQuestionDto selectAnswerByArticleNo(int articleNo) throws Exception {
		return answerMapper.selectAnswerByArticleNo(articleNo);
	}

	@Override
	public boolean updateAnswer(AnswerByQuestionDto aDto) throws Exception {
		int result = answerMapper.updateAnswer(aDto);
		if (result != 1)
			return false;
		return true;
	}

	@Override
	public boolean deleteAnswer(int articleNo) throws Exception {
		int result = answerMapper.deleteAnswer(articleNo);
		if (result != 1)
			return false;
		return true;
	}

}

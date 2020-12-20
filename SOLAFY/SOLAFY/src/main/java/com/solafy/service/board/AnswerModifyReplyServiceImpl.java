package com.solafy.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.board.AnswerModifyReplyMapper;
import com.solafy.model.AnswerModifyReplyDto;

/**
 * @FileName : AnswerModifyReplyServiceImpl.java
 * @Project : SOLAFY
 * @Date : 2020. 12. 20
 * @작성자 : BUMSEOK SEO
 * 
 * @변경이력 :
 * @프로그램 설명 : 답안수정게시판 답글 ServiceImpl
 */
@Service
public class AnswerModifyReplyServiceImpl implements AnswerModifyReplyService {

	@Autowired
	private AnswerModifyReplyMapper answerModifyReplyMapper;

	@Override
	public boolean createReply(AnswerModifyReplyDto answerModifyReplyDto) {
		int result = answerModifyReplyMapper.createReply(answerModifyReplyDto);
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<AnswerModifyReplyDto> selectReplies(int articleNo) {
		List<AnswerModifyReplyDto> list = answerModifyReplyMapper.selectReplies(articleNo);
		return list;
	}

	@Override
	public boolean updateReply(AnswerModifyReplyDto answerModifyReplyDto) {
		int result = answerModifyReplyMapper.updateReply(answerModifyReplyDto);
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteReply(AnswerModifyReplyDto AnswerModifyReplyDto) {
		int result = answerModifyReplyMapper.deleteReply(AnswerModifyReplyDto);
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

}

package com.solafy.service.board;

import java.util.List;

import com.solafy.model.AnswerModifyBoardDto;

/**
 * AnswerModifyBoardService
 * 답안 수정 게시판 Interface
 * @author BUMSEOK SEO
 * @since 2020-12-15
 */

public interface AnswerModifyBoardService {
	public int createAnswerModify(AnswerModifyBoardDto answerModifyDto);

	public int updateAnswerModify(AnswerModifyBoardDto answerModifyDto);

	public AnswerModifyBoardDto selectAnswerModifyByArticleNo();

	public List<AnswerModifyBoardDto> selectAnswerModifyByUid();

	public List<AnswerModifyBoardDto> selectAnswerModifyByUidSubMitter();

	public List<AnswerModifyBoardDto> selectAnswerModifyByProblemNo();
}

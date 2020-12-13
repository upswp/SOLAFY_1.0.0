package com.solafy.service.board;

import java.sql.SQLException;
import java.util.List;

import com.solafy.model.AnswerByQuestionDto;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 */

public interface QuestionReplyService {

	/**
	 * 질문에 대한 답변 등록
	 * @param aDto - AnswerByQuestionDto
	 * @return boolean, 정상적으로 등록 시 true 반환
	 * @throws Exception
	 */
	public boolean createAnswer(AnswerByQuestionDto aDto) throws Exception;

	/**
	 * 질문 글 번호에 해당하는 답변들 반환 
	 * @param qestionNo
	 * @return AnswerByQuestionDto의 List 반환 - 답변글번호, 질문글번호, 내용, 작성자, 등록시간 포함
	 * @throws Exception
	 */
	public List<AnswerByQuestionDto> selectAnswersByQuestionNo(int qestionNo) throws Exception;

	/**
	 * 답변 글 번호에 해당하는 답변 반환
	 * @param articleNo - 답변글 번호
	 * @return AnswerByQuestionDto 객체 하나 반환 - 답변글번호, 질문글번호, 내용, 작성자, 등록시간 포함
	 * @throws Exception
	 */
	public AnswerByQuestionDto selectAnswerByArticleNo(int articleNo) throws Exception;

	/**
	 * 답변 글의 내용을 수정
	 * @param aDto - AnswerByQuestionDto - 내용, 등록시간만 수정
	 * @return boolean, 정상적으로 수정 시 true 반환
	 * @throws Exception
	 */
	public boolean updateAnswer(AnswerByQuestionDto aDto) throws Exception;

	/**
	 * 글 번호에 해당하는 답변 글 삭제
	 * @param articleNo, 답변 글 번호
	 * @return boolean, 정상적으로 삭제 시 true 반환
	 * @throws Exception
	 */
	public boolean deleteAnswer(int articleNo) throws Exception;

}

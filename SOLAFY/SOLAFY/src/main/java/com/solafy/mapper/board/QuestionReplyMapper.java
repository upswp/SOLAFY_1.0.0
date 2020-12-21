package com.solafy.mapper.board;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.AnswerByQuestionDto;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 */

@Mapper
public interface QuestionReplyMapper {

	/**
	 * 질문에 대한 답변 등록
	 * @param aDto - AnswerByQuestionDto
	 * @return int, 등록된 행의 개수 반환
	 * @throws SQLException
	 */
	public int createAnswer(AnswerByQuestionDto aDto) throws SQLException;

	/**
	 * 질문 글 번호에 해당하는 답변들 반환 
	 * @param qestionNo
	 * @return AnswerByQuestionDto의 List 반환 - 답변글번호, 질문글번호, 내용, 작성자, 등록시간 포함
	 * @throws SQLException
	 */
	public List<AnswerByQuestionDto> selectAnswersByQuestionNo(int qestionNo) throws SQLException;

	/**
	 * 답변 글 번호에 해당하는 답변 반환
	 * @param articleNo - 답변글 번호
	 * @return AnswerByQuestionDto 객체 하나 반환 - 답변글번호, 질문글번호, 내용, 작성자, 등록시간 포함
	 * @throws SQLException
	 */
	public AnswerByQuestionDto selectAnswerByArticleNo(int articleNo) throws SQLException;

	/**
	 * 답변 글의 내용을 수정
	 * @param aDto - AnswerByQuestionDto - 내용, 등록시간만 수정
	 * @return int, 수정된 행의 개수 반환
	 * @throws SQLException
	 */
	public int updateAnswer(AnswerByQuestionDto aDto) throws SQLException;

	/**
	 * 글 번호에 해당하는 답변 글 삭제
	 * @param articleNo, 답변 글 번호
	 * @return int, 삭제된 행의 개수
	 * @throws SQLException
	 */
	public int deleteAnswer(int articleNo) throws SQLException;

}

package com.solafy.mapper.board;

import java.sql.SQLException;
import java.util.List;

import com.solafy.model.QuestionBoardDto;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 */

public interface QuestionBoardMapper {

	/**
	 * 질문 게시판에 질문을 등록
	 * @param qDto - QuestionBoardDto 타입
	 * @return int로 등록된 행의 개수 반환
	 * @throws SQLException
	 */
	public int createQuestion(QuestionBoardDto qDto) throws SQLException;
	
	/**
	 * 질문 게시판의 모든 질문 글을 반환
	 * @return QuestionBoardDto의 List 반환 - 질문글번호, 제목, 작성자, 등록시간 포함
	 * @throws SQLException
	 */
	public List<QuestionBoardDto> selectAllQuestions() throws SQLException;

	/**
	 * 질문 글의 번호로 검색하여 dto 하나 반환
	 * @param articleNo - int, 질문 글의 번호
	 * @return QuestionBoardDto 객체 하나 반환 - 질문글번호, 제목, 내용, 작성자, 포인트, 등록시간, 문제번호 포함
	 * @throws SQLException
	 */
	public QuestionBoardDto selectQuestionsByArticleNo(int articleNo) throws SQLException;

	/**
	 * keyword가 제목에 포함되어 있는 질문 글들을 반환
	 * @param keyword - String, 검색 키워드 
	 * @return QuestionBoardDto의 List 반환 - 질문글번호, 제목, 작성자, 등록시간, 문제번호 포함
	 * @throws SQLException
	 */
	public List<QuestionBoardDto> selectQuestionsByTitle(String keyword) throws SQLException;

	/**
	 * 동일한 uid가 작성한 질문 글만 반환
	 * @param uid - String, uid 
	 * @return QuestionBoardDto의 List 반환 - 질문글번호, 제목, 작성자, 등록시간, 문제번호 포함
	 * @throws SQLException
	 */
	public List<QuestionBoardDto> selectQuestionsByWriter(String uid) throws SQLException;
	
	/**
	 * 문제 번호로 검색, 한 문제에 대한 질문글들을 반환
	 * @param problemNo - int, 문제 번호 
	 * @return QuestionBoardDto의 List 반환- 질문글번호, 제목, 작성자, 등록시간, 문제번호 포함
	 * @throws SQLException
	 */
	public List<QuestionBoardDto> selectQuestionsByProblemNo(int problemNo) throws SQLException;
	
	/**
	 * 질문 글 수정
	 * @param qDto - QuestionBoardDto, 수정할 내용 포함  - 제목, 내용, 등록시간만 수정
	 * @return int, 수정된 행의 개수 반환
	 * @throws SQLException
	 */
	public int updateQuestion(QuestionBoardDto qDto) throws SQLException;
	
	/**
	 * 질문 글 삭제
	 * @param articleNo - int, 질문 글 번호
	 * @return int, 삭제된 행의 개수 반환
	 * @throws SQLException
	 */
	public int deleteQuestion(int articleNo) throws SQLException;

}

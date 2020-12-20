package com.solafy.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.AnswerModifyBoardDto;

/**
* @FileName : AnswerModifyMapper.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : BUMSEOK SEO

* @변경이력 :
* @프로그램 설명 : 답안수정게시판 Mapper
*/
@Mapper
public interface AnswerModifyBoardMapper {
	/**
	 * 답안수정게시판에 게시글 등록
	 * @param answerModifyBoardDto
	 * @return boolean, 성공적으로 등록 되었을 경우 true 반환
	 */
	public int createArticle(AnswerModifyBoardDto answerModifyBoardDto);
	
	/**
	 * 답안수정게시판 모든 게시글들을 반환
	 * @return List<AnswerModifyBoardDto>
	 */
	public List<AnswerModifyBoardDto> selectArticles();

	/**
	 * 입력한 게시를 번호에 해당하는 게시글 반환
	 * @param ArticleNo
	 * @return AnswerModifyBoardDto(해당 번호에 해당하는 게시글)
	 */
	public AnswerModifyBoardDto selectArticleByNo(int ArticleNo);

	/**
	 * 입력한 게시글 제목에 해당하는 게시글 반환
	 * @param title
	 * @return 
	 */
	public List<AnswerModifyBoardDto> selectArticleByTitle(String title);

	/**
	 * 입력한 uid와 일치하는 작성자의 게시글 반환
	 * @param uid
	 * @return AnswerModifyBoardDto
	 */
	public List<AnswerModifyBoardDto> selectArticleByUid(String uid);

	/**
	 * 입력한 문제번호와 일치하는 작성자의 게시글 반환
	 * @param ProblemNo
	 * @return AnswerModifyBoardDto
	 */
	public List<AnswerModifyBoardDto> selectArticleByProblemNo(int problemNo);

	// TODO: 공지사항 검색이 필요할지? : 이후에 추가하도록 하겠습니다.
	// public AnswerModifyBoardDto selectNotice(ArticleNo);

	/**
	 * 답안수정게시판 게시글을 수정
	 * @param answerModifyBoardDto
	 * @return boolean, 정상적으로 수정 되었을 경우 true 반환
	 */
	public int updateArticle(AnswerModifyBoardDto answerModifyBoardDto);

	/**
	 * 답안수정게시판 게시글 삭제
	 * @param ArticleNo
	 * @return boolean, 정상적으로 삭제 되었을 경우 true 반환
	 */
	public int deleteArticle(int ArticleNo);
}

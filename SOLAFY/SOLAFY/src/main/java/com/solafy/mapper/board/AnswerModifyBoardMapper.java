package com.solafy.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.AnswerModifyBoardDto;

/**
 * @FileName : AnswerModifyMapper.java
 * @Project : SOLAFY
 * @Date : 2020. 12. 20
 * @작성자 : BUMSEOK SEO
 * 
 * @변경이력 :
 * @프로그램 설명 : 답안수정게시판 Mapper
 */
@Mapper
public interface AnswerModifyBoardMapper {

	/**
	 * @param answerModifyBoardDto
	 * @return boolean, 성공적으로 등록 되었을 경우 true 반환
	 * @Method 설명 : 답안수정게시판에 게시글 등록
	 * @변경이력 :
	 */
	public int createArticle(AnswerModifyBoardDto answerModifyBoardDto);

	/**
	 * @return List<AnswerModifyBoardDto>
	 * @Method 설명 : 답안수정게시판 모든 게시글들을 반환
	 * @변경이력 :
	 */
	public List<AnswerModifyBoardDto> selectAllArticles();

	/**
	 * @param ArticleNo
	 * @return AnswerModifyBoardDto(해당 번호에 해당하는 게시글)
	 * @Method 설명 : 입력한 게시를 번호에 해당하는 게시글 반환
	 * @변경이력 :
	 */
	public AnswerModifyBoardDto selectArticleByArticleNo(int ArticleNo);

	/**
	 * @param title
	 * @return List<AnswerModifyBoardDto>
	 * @Method 설명 : 입력한 게시글 제목에 해당하는 게시글 반환
	 * @변경이력 :
	 */
	public List<AnswerModifyBoardDto> selectArticleByTitle(String title);

	/**
	 * @param nickname
	 * @return List<AnswerModifyBoardDto>
	 * @Method 설명 : 입력한 닉네임와 일치하는 작성자의 게시글 반환
	 * @변경이력 :
	 */
	public List<AnswerModifyBoardDto> selectArticleByNickname(String nickname);

	/**
	 * @param problemNo
	 * @return List<AnswerModifyBoardDto>
	 * @Method 설명 : 입력한 문제번호와 일치하는 작성자의 게시글 반환
	 * @변경이력 :
	 */
	public List<AnswerModifyBoardDto> selectArticleByProblemNo(int problemNo);

	/**
	 * @param answerModifyBoardDto
	 * @return boolean, 정상적으로 수정 되었을 경우 true 반환
	 * @Method 설명 : 답안수정게시판 게시글을 수정
	 * @변경이력 :
	 */
	public int updateArticle(AnswerModifyBoardDto answerModifyBoardDto);

	/**
	 * @param ArticleNo
	 * @return boolean, 정상적으로 삭제 되었을 경우 true 반환
	 * @Method 설명 : 답안수정게시판 게시글 삭제
	 * @변경이력 :
	 */
	public int deleteArticle(int ArticleNo);
}

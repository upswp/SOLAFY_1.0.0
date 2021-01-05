package com.solafy.service.board;

import java.util.List;

import com.solafy.model.AnswerModifyBoardDto;
import com.solafy.model.FreeBoardDto;

/**
 * AnswerModifyBoardService 답안 수정 게시판 Interface
 * 
 * @author BUMSEOK SEO
 * @since 2020-12-15
 */

public interface AnswerModifyBoardService {

	/**
	 * @param answerModifyDto
	 * @return
	 * @Method 설명 : 답안수정 게시글 등록
	 * @변경이력 :
	 */
	public boolean createArticle(AnswerModifyBoardDto answerModifyDto);

	/**
	 * @return List<FreeBoardDto>
	 * @Method 설명 : 자유게시판의 모든 게시글들을 반환
	 * @변경이력 :
	 */
	public List<AnswerModifyBoardDto> selectAllArticles();

	/**
	 * @param int articleNo
	 * @return
	 * @Method 설명 : 게시글 번호로 답안수정 게시글 검색
	 * @변경이력 :
	 */
	public AnswerModifyBoardDto selectArticleByArticleNo(int articleNo);

	/**
	 * @param String nickname
	 * @return
	 * @Method 설명 : 닉네임으로 답안수정 게시글 검색
	 * @변경이력 : 2020-01-01(BEOMSEOK SEO) - uid 검색에서 닉네임 검색으로 변경
	 */
	public List<AnswerModifyBoardDto> selectArticleByNickname(String nickname);

	/**
	 * @param String title
	 * @return
	 * @Method 설명 : 입력한 검색어를 포함하는 제목의 답안수정 게시글 검색
	 * @변경이력 :
	 */
	public List<AnswerModifyBoardDto> selectArticleByTitle(String title);

	/**
	 * @param String uid_submitter
	 * @return
	 * @Method 설명 : 문제 제출자의 Uid로 답안수정 게시글 검색
	 * @변경이력 :
	 */
	public List<AnswerModifyBoardDto> selectArticleByUidSubmitter(String uid_submitter);

	/**
	 * @param problemNo
	 * @return
	 * @Method 설명 : 문제 번호로 답안수정 게시글 검색
	 * @변경이력 :
	 */
	public List<AnswerModifyBoardDto> selectArticleByProblemNo(int problemNo);

	/**
	 * @param answerModifyDto
	 * @return answerModifyDto
	 * @Method 설명 : 입력받은 게시글 정보로 답안수정 게시판의 게시글 수정
	 * @변경이력 :
	 */
	public boolean updateArticle(AnswerModifyBoardDto answerModifyDto);

	/**
	 * @param ArticleNo
	 * @return boolean, 성공적으로 삭제 되었을 경우 true 반환
	 * @Method 설명 : 답안수정게시판 게시글 삭제
	 * @변경이력 :
	 */
	public boolean deleteArticle(int ArticleNo);

}

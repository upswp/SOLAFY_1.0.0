package com.solafy.service.board;

import java.util.List;

import com.solafy.model.AnswerModifyBoardDto;
import com.solafy.model.FreeBoardDto;

/**
 * AnswerModifyBoardService
 * 답안 수정 게시판 Interface
 * @author BUMSEOK SEO
 * @since 2020-12-15
 */

public interface AnswerModifyBoardService {
	/**
	* @Method Name : createAnswerModify
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @param answerModifyDto
	* @return
	* @Method 설명 : 답안수정 게시글 등록
	* @변경이력 :
	*/
	public boolean createArticle(AnswerModifyBoardDto answerModifyDto);

	/**
	 * 자유게시판의 모든 게시글들을 반환
	 * @return List<FreeBoardDto>
	 */
	public List<AnswerModifyBoardDto> selectAllArticles();
	

	/**
	* @Method Name : selectAnswerModifyByArticleNo
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @return
	* @Method 설명 : 게시글 번호로 답안수정 게시글 검색
	* @변경이력 :
	*/
	public AnswerModifyBoardDto selectArticleByArticleNo(int articleNo);

	/**
	* @Method Name : selectAnswerModifyByNickName
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @return
	* @Method 설명 : 닉네임으로 답안수정 게시글 검색
	* @변경이력 :
	* 2020-01-01(BEOMSEOK SEO) - uid검색에서 닉네임 검색으로 변경
	*/
	public List<AnswerModifyBoardDto> selectArticleByNickname(String nickname);
	
	/**
	 * @Method Name : selectAnswerModifyByTitle
	 * @작성일 : 2020. 12. 19
	 * @작성자 : BUMSEOK SEO
	 * @return
	 * @Method 설명 : 입력한 검색어를 포함하는 제목의 답안수정 게시글 검색
	 * @변경이력 :
	 */
	public List<AnswerModifyBoardDto> selectArticleByTitle(String title);

	/**
	* @Method Name : selectAnswerModifyByUidSubMitter
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @return
	* @Method 설명 : 문제 제출자의 Uid로 답안수정 게시글 검색
	* @변경이력 :
	*/
	public List<AnswerModifyBoardDto> selectArticleByUidSubmitter(String uid_submitter);

	/**
	* @Method Name : selectAnswerModifyByProblemNo
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @return
	* @Method 설명 : 문제 번호로 답안수정 게시글 검색
	* @변경이력 :
	*/
	public List<AnswerModifyBoardDto> selectArticleByProblemNo(int problemNo);
	
	/**
	 * @Method Name : updateAnswerModify
	 * @작성일 : 2020. 12. 19
	 * @작성자 : BUMSEOK SEO
	 * @param answerModifyDto
	 * @return
	 * @Method 설명 : 답안수정 게시글 수정
	 * @변경이력 :
	 */
	public boolean updateArticle(AnswerModifyBoardDto answerModifyDto);

	/**
	 * 답안수정게시판 게시글 삭제
	 * @param ArticleNo
	 * @return boolean, 성공적으로 삭제 되었을 경우 true 반환
	 */
	public boolean deleteArticle(int ArticleNo);
	
	
}

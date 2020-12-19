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
	/**
	* @Method Name : createAnswerModify
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @param answerModifyDto
	* @return
	* @Method 설명 : 답안수정 게시글 등록
	* @변경이력 :
	*/
	public int createAnswerModify(AnswerModifyBoardDto answerModifyDto);

	/**
	* @Method Name : updateAnswerModify
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @param answerModifyDto
	* @return
	* @Method 설명 : 답안수정 게시글 수정
	* @변경이력 :
	*/
	public int updateAnswerModify(AnswerModifyBoardDto answerModifyDto);

	/**
	* @Method Name : selectAnswerModifyByArticleNo
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @return
	* @Method 설명 : 게시글 번호로 답안수정 게시글 검색
	* @변경이력 :
	*/
	public AnswerModifyBoardDto selectAnswerModifyByArticleNo();

	/**
	* @Method Name : selectAnswerModifyByUid
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @return
	* @Method 설명 : Uid로 답안수정 게시글 검색
	* @변경이력 :
	*/
	public List<AnswerModifyBoardDto> selectAnswerModifyByUid();

	/**
	* @Method Name : selectAnswerModifyByUidSubMitter
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @return
	* @Method 설명 : 문제 제출자의 Uid로 답안수정 게시글 검색
	* @변경이력 :
	*/
	public List<AnswerModifyBoardDto> selectAnswerModifyByUidSubMitter();

	/**
	* @Method Name : selectAnswerModifyByProblemNo
	* @작성일 : 2020. 12. 19
	* @작성자 : BUMSEOK SEO
	* @return
	* @Method 설명 : 문제 번호로 답안수정 게시글 검색
	* @변경이력 :
	*/
	public List<AnswerModifyBoardDto> selectAnswerModifyByProblemNo();
}

package com.solafy.service.board;

import java.util.List;

import com.solafy.model.FreeReplyDto;

/**
* @FileName : FreeReplyService.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : BUMSEOK SEO

* @변경이력 :
* @프로그램 설명 : 자유게시판 댓글용 Service
*/
public interface FreeReplyService {
	/**
	* @param freeReplyDto
	* @return 등록 성공여부를 boolean 형태로 반환
	* @Method 설명 : 자유게시판 댓글을 등록한다
	* @변경이력 :
	*/
	boolean createReply(FreeReplyDto freeReplyDto);
	
	/**
	* @param articleNo
	* @return List<FreeReplyDto>
	* @Method 설명 : 해당 게시글의 모든 댓글을 반환한다
	* @변경이력 :
	*/
	List<FreeReplyDto> selectReplies(int articleNo);
	
	/**
	* @param freeReplyDto
	* @return 수정 성공여부를 boolean 형태로 반환
	* @Method 설명 : 해당 댓글을 수정
	* @변경이력 :
	*/
	boolean updateReply(FreeReplyDto freeReplyDto);
	
	/**
	* @param freeReplyDto
	* @return 삭제 성공여부를 boolean 형태로 반환
	* @Method 설명 : 해당 댓글을 삭제
	* @변경이력 :
	*/
	boolean deleteReplty(FreeReplyDto freeReplyDto);
}

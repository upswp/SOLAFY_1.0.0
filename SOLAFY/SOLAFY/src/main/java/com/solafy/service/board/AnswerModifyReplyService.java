package com.solafy.service.board;

import java.util.List;

import com.solafy.model.AnswerModifyReplyDto;

public interface AnswerModifyReplyService {
	/**
	* @param answerModifytReplyDto
	* @return 등록 성공여부를 boolean 형태로 반환
	* @Method 설명 : 자유게시판 댓글을 등록한다
	* @변경이력 :
	*/
	boolean createReply(AnswerModifyReplyDto answerModifytReplyDto);
	
	/**
	* @param articleNo
	* @return List<answerModifytReplyDto>
	* @Method 설명 : 해당 게시글의 모든 댓글을 반환한다
	* @변경이력 :
	*/
	List<AnswerModifyReplyDto> selectReplies(int articleNo);
	
	/**
	* @param answerModifytReplyDto
	* @return 수정 성공여부를 boolean 형태로 반환
	* @Method 설명 : 해당 댓글을 수정
	* @변경이력 :
	*/
	boolean updateReply(AnswerModifyReplyDto answerModifyReplyDto);
	
	/**
	* @param answerModifytReplyDto
	* @return 삭제 성공여부를 boolean 형태로 반환
	* @Method 설명 : 해당 댓글을 삭제
	* @변경이력 :
	*/
	boolean deleteReply(AnswerModifyReplyDto answerModifyReplyDto);
}

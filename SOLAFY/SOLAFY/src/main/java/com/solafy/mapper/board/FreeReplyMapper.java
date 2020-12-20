package com.solafy.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.FreeReplyDto;

/**
* @FileName : FreeReplyMapper.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : BUMSEOK SEO

* @변경이력 :
* @프로그램 설명 : Free.xml 파일에 있는 SQL호출하기 위한 인터페이스
*/
@Mapper
public interface FreeReplyMapper {

	/**
	* @param freeReplyDto
	* @Method 설명 : 자유게시판 댓글을 등록한다
	* @변경이력 : 
	*/
	int createReply(FreeReplyDto freeReplyDto);
	
	/**
	* @param articleNo (댓글을 조회할 게시글 번호)
	* @return List<FreeReplyDto> (해당하는 댓글 List)
	* @Method 설명 : 해당 게시글의 모든 댓글을 반환
	* @변경이력 :
	*/
	List<FreeReplyDto> selectReplies(int articleNo);
	
	/**
	* @param freeReplyDto (수정할 댓글의 정보)
	* @return 업데이트된 댓글의 갯수 반환
	* @Method 설명 : 해당 댓글을 수정
	* @변경이력 :
	*/
	int updateReply(FreeReplyDto freeReplyDto);
	
	/** TODO: int[]를 사용할지, 그냥 DTO를 사용할지
	* @param freeReplyDto (삭제할 댓글의 게시글번호와 댓글 번호를 인자값으로 넘김)
	* @return
	* @Method 설명 : 해당 댓글을 삭제
	* @변경이력 :
	*/
	int deleteReply(FreeReplyDto freeReplyDto);
}

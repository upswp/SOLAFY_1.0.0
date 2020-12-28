package com.solafy.service.board;

import java.util.List;

import com.solafy.model.FreeBoardDto;

/**
 * @FileName : FreeBoardService.java
 * @Project : SOLAFY
 * @Date : 2020. 12. 14
 * @작성자 : BUMSEOK SEO
 * 
 * @변경이력 :
 * @프로그램 설명 : 자유게시판 Interface
 */
public interface FreeBoardService {

	/**
	 * @param freeBoardDto
	 * @return boolean, 성공적으로 등록 되었을 경우 true 반환
	 * @Method 설명 : 자유게시판에 게시글 등록
	 * @변경이력 :
	 */
	public boolean createArticle(FreeBoardDto freeBoardDto);

	/**
	 * @return List<FreeBoardDto>
	 * @Method 설명 : 자유게시판의 모든 게시글들을 반환
	 * @변경이력 :
	 */
	public List<FreeBoardDto> selectAllArticles();

	/**
	 * @param ArticleNo
	 * @return FreeBoardDto(해당 번호에 해당하는 게시글)
	 * @Method 설명 : 게시를 번호에 해당하는 게시글 반환
	 * @변경이력 :
	 */
	public FreeBoardDto selectArticleByArticleNo(int ArticleNo);

	/**
	 * @param title
	 * @return
	 * @Method 설명 : 입력한 키워드를 포함하는 제목의 게시글 반환
	 * @변경이력 :
	 */
	public List<FreeBoardDto> selectArticleByTitle(String title);

	/**
	 * @param nickname
	 * @return FreeBoardDto
	 * @Method 설명 : 입력한 닉네임과 일치하는 작성자의 게시글 반환
	 * @변경이력 :
	 */
	public List<FreeBoardDto> selectArticleByNickname(String nickname);

	// TODO: 공지사항 검색이 필요할지?
	// public FreeBoardDto selectNotice( ArticleNo);

	/**
	 * @param freeBoardDto
	 * @return boolean, 성공적으로 수정 되었을 경우 true 반환
	 * @Method 설명 : 자유게시판 게시글을 수정
	 * @변경이력 :
	 */
	public boolean updateArticle(FreeBoardDto freeBoardDto);

	/**
	 * @param ArticleNo
	 * @return boolean, 성공적으로 삭제 되었을 경우 true 반환
	 * @Method 설명 : 자유게시판 게시글 삭제
	 * @변경이력 :
	 */
	public boolean deleteArticle(int ArticleNo);

}

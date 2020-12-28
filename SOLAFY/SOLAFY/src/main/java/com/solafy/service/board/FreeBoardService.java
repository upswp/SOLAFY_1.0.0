package com.solafy.service.board;

import java.util.List;

import com.solafy.model.FreeBoardDto;

/**
 * FreeboardService
 * 자유게시판 Interface
 * @author BUMSEOK SEO
 * @since 2020-12-14
 */
public interface FreeBoardService {
	/**
	 * 자유게시판에 게시글 등록
	 * @param freeBoardDto
	 * @return boolean, 성공적으로 등록 되었을 경우 true 반환
	 */
	public boolean createArticle(FreeBoardDto freeBoardDto);
	
	/**
	 * 자유게시판의 모든 게시글들을 반환
	 * @return List<FreeBoardDto>
	 */
	public List<FreeBoardDto> selectAllArticles();

	/**
	 * 입력한 게시를 번호에 해당하는 게시글 반환
	 * @param ArticleNo
	 * @return FreeBoardDto(해당 번호에 해당하는 게시글)
	 */
	public FreeBoardDto selectArticleByArticleNo(int ArticleNo);

	/**
	 * 입력한 게시글 제목에 해당하는 게시글 반환
	 * @param title
	 * @return 
	 */
	public List<FreeBoardDto> selectArticleByTitle(String title);

	/**
	 * 입력한 닉네임과 일치하는 작성자의 게시글 반환
	 * @param nickname
	 * @return FreeBoardDto
	 */
	public List<FreeBoardDto> selectArticleByNickname(String nickname);

	// TODO: 공지사항 검색이 필요할지?
	// public FreeBoardDto selectNotice( ArticleNo);

	/**
	 * 자유게시판 게시글을 수정
	 * @param freeBoardDto
	 * @return boolean, 성공적으로 수정 되었을 경우 true 반환
	 */
	public boolean updateArticle(FreeBoardDto freeBoardDto);

	/**
	 * 자유게시판 게시글 삭제
	 * @param ArticleNo
	 * @return boolean, 성공적으로 삭제 되었을 경우 true 반환
	 */
	public boolean deleteArticle(int ArticleNo);


}

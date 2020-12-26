package com.solafy.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.FreeBoardDto;

/**
* @FileName : FreeBoardMapper.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : BUMSEOK SEO

* @변경이력 :
* @프로그램 설명 : 자유게시판 Mapper
*/
@Mapper
public interface FreeBoardMapper {
	/**
	 * 자유게시판에 게시글 등록
	 * @param freeBoardDto
	 * @return boolean, 성공적으로 등록 되었을 경우 true 반환
	 */
	public int createArticle(FreeBoardDto freeBoardDto);
	
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
	public FreeBoardDto selectArticleByNo(int ArticleNo);

	/**
	 * 입력한 게시글 제목에 해당하는 게시글 반환
	 * @param title
	 * @return 
	 */
	public FreeBoardDto selectArticleByTitle(String title);

	/**
	 * 입력한 닉네임와 일치하는 작성자의 게시글 반환
	 * @param nickname
	 * @return FreeBoardDto
	 */
	public FreeBoardDto selectArticleByNickname(String nickname);

	// TODO: 공지사항 검색이 필요할지?
	// public FreeBoardDto selectNotice( ArticleNo);

	/**
	 * 자유게시판 게시글을 수정
	 * @param freeBoardDto
	 * @return boolean, 정상적으로 수정 되었을 경우 true 반환
	 */
	public int updateArticle(FreeBoardDto freeBoardDto);

	/**
	 * 자유게시판 게시글 삭제
	 * @param ArticleNo
	 * @return boolean, 정상적으로 삭제 되었을 경우 true 반환
	 */
	public int deleteArticle(int ArticleNo);
}

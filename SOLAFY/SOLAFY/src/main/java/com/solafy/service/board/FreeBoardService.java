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

	public int createArticle(FreeBoardDto freeBoardDto);

	public List<FreeBoardDto> selectArticles();

	public FreeBoardDto selectArticleByNo(int ArticleNo);

	public FreeBoardDto selectArticleByTitle(String title);

	public FreeBoardDto selectArticleByUid(String uid);

	// TODO: 공지사항 검색이 필요할지?
	// public FreeBoardDto selectNotice( ArticleNo);

	public int updateArticle(FreeBoardDto freeBoardDto);

	public int deleteArticle(int ArticleNo);
}

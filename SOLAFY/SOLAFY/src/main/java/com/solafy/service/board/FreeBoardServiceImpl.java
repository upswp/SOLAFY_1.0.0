package com.solafy.service.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.board.FreeBoardMapper;
import com.solafy.model.FreeBoardDto;

/**
 * FreeboardServiceImpl
 * 자유게시판 ServiceImpl
 * @author BUMSEOK SEO
 * @since 2020-12-15
 */

@Service
public class FreeBoardServiceImpl implements FreeBoardService {

	@Autowired
	private FreeBoardMapper freeBoardMapper;
	
	@Override
	public boolean createArticle(FreeBoardDto freeBoardDto) {
		int result = freeBoardMapper.createArticle(freeBoardDto);
		if(result == 1) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<FreeBoardDto> selectArticles() {
		List<FreeBoardDto> list = freeBoardMapper.selectArticles();
		return list;
	}

	@Override
	public FreeBoardDto selectArticleByNo(int ArticleNo) {
		FreeBoardDto freeBoardDto = freeBoardMapper.selectArticleByNo(ArticleNo);
		return freeBoardDto;
	}

	@Override
	public FreeBoardDto selectArticleByTitle(String title) {
		FreeBoardDto freeBoardDto = freeBoardMapper.selectArticleByTitle(title);
		return freeBoardDto;
	}

	@Override
	public FreeBoardDto selectArticleByUid(String uid) {
		FreeBoardDto freeBoardDto = freeBoardMapper.selectArticleByUid(uid);
		return freeBoardDto;
	}

	@Override
	public boolean updateArticle(FreeBoardDto freeBoardDto) {
		int result = freeBoardMapper.updateArticle(freeBoardDto);
		if(result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteArticle(int ArticleNo) {
		int result = freeBoardMapper.deleteArticle(ArticleNo);
		if(result == 1) {
			return true;
		}else{
			return false;
		}
	}
}

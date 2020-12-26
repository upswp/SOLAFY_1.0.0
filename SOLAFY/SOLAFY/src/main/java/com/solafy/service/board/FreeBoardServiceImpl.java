package com.solafy.service.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.board.FreeBoardMapper;
import com.solafy.model.FreeBoardDto;

/**
* @FileName : FreeBoardServiceImpl.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : BUMSEOK SEO

* @변경이력 :
* @프로그램 설명 : 자유게시판 ServiceImpl
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
	public List<FreeBoardDto> selectAllArticles() {
		List<FreeBoardDto> list = freeBoardMapper.selectAllArticles();
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
	public FreeBoardDto selectArticleByNickname(String nickname) {
		FreeBoardDto freeBoardDto = freeBoardMapper.selectArticleByNickname(nickname);
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

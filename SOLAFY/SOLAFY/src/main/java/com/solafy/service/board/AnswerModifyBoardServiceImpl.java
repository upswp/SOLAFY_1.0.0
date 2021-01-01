package com.solafy.service.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.board.AnswerModifyBoardMapper;
import com.solafy.model.AnswerModifyBoardDto;

/**
* @FileName : FreeBoardServiceImpl.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : BUMSEOK SEO

* @변경이력 :
* @프로그램 설명 : 자유게시판 ServiceImpl
*/
@Service
public class AnswerModifyBoardServiceImpl implements AnswerModifyBoardService {

	@Autowired
	private AnswerModifyBoardMapper answerModifyBoardMapper;
	
	@Override
	public boolean createArticle(AnswerModifyBoardDto answerModifyBoardDto) {
		int result = answerModifyBoardMapper.createArticle(answerModifyBoardDto);
		if(result == 1) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<AnswerModifyBoardDto> selectAllArticles() {
		List<AnswerModifyBoardDto> list = answerModifyBoardMapper.selectAllArticles();
		return list;
	}

	@Override
	public AnswerModifyBoardDto selectArticleByArticleNo(int articleNo) {
		AnswerModifyBoardDto answerModifyBoardDto = answerModifyBoardMapper.selectArticleByArticleNo(articleNo);
		return answerModifyBoardDto;
	}

	@Override
	public List<AnswerModifyBoardDto> selectArticleByTitle(String title) {
		List<AnswerModifyBoardDto> list = answerModifyBoardMapper.selectArticleByTitle(title);
		return list;
	}

	@Override
	public List<AnswerModifyBoardDto> selectArticleByNickname(String nickname) {
		List<AnswerModifyBoardDto> list = answerModifyBoardMapper.selectArticleByNickname(nickname);
		return list;
	}
	
	@Override
	public List<AnswerModifyBoardDto> selectArticleByUidSubmitter(String nickname_submitter) {
		List<AnswerModifyBoardDto> list = answerModifyBoardMapper.selectArticleByNickname(nickname_submitter);
		return list;
	}
	
	@Override
	public List<AnswerModifyBoardDto> selectArticleByProblemNo(int problemNo) {
		List<AnswerModifyBoardDto> list = answerModifyBoardMapper.selectArticleByProblemNo(problemNo);
		return list;
	}

	@Override
	public boolean updateArticle(AnswerModifyBoardDto answerModifyBoardDto) {
		int result = answerModifyBoardMapper.updateArticle(answerModifyBoardDto);
		if(result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteArticle(int ArticleNo) {
		int result = answerModifyBoardMapper.deleteArticle(ArticleNo);
		if(result == 1) {
			return true;
		}else{
			return false;
		}
	}
}

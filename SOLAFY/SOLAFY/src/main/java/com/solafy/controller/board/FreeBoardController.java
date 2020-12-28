package com.solafy.controller.board;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solafy.model.FreeBoardDto;
import com.solafy.service.board.FreeBoardService;

import io.swagger.annotations.ApiOperation;

/**
* @FileName : FreeBoardController.java
* @Project : SOLAFY
* @Date : 2020. 12. 18
* @작성자 : BUMSEOK SEO

* @변경이력 :
* 2020-12-28(BUMSEOK SEO) : 게시글 번호에 따른 게시글 반환 메서드 추가, 게시물 삭제 메서드 어노테이션 @DeleteMapping으로 변경
* @프로그램 설명 : 자유게시판 관리를 위한 Controller
*/
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/free") //주소는 알아서 넣기

public class FreeBoardController {
	private static final Logger logger = LoggerFactory.getLogger(FreeBoardController.class);
	//아래 string 2개는 사용하지 않는다면 지우기
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private FreeBoardService freeBoardService;
	
	/**
	* @param freeBoardDto
	* @return ResponseEntity<String> SUCCESS / FAIL
	* @Method 설명 : 게시글을 등록하고 성공하면 SUCCECSS, 실패하면 FAIL을 반환
	* @변경이력 :
	*/
	@ApiOperation(value = "자유게시판에 게시글을 등록한다.", response = String.class)
	@PostMapping(value = "/createArticle")
	public ResponseEntity<String> createArticle(@RequestBody FreeBoardDto freeBoardDto){
		logger.info("createArticle - 호출" + new Date());
		if(freeBoardService.createArticle(freeBoardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {			
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * @return List<FreeBoardDto> 게시글 list
	 * @Method 설명 : 인자값은 없으며, 자유게시판의 모든 공지글을 반환한다.
	 * @변경이력 :
	 * 2020-12-28(BEOMSEOK SEO) : 공지사항 우선 순위 표시 필요성에 따른 해당 메서드 추가
	 */
	@ApiOperation(value = "자유게시판의 모든 공지글을 반환한다.", response = List.class)
	@GetMapping(value="/selectAllNotices")
	public List<FreeBoardDto> selectAllNotices(){
		logger.info("selectNotices - 호출" + new Date());
		List<FreeBoardDto> list = freeBoardService.selectAllNotices();
		return list;
	}
	
	/**
	* @return List<FreeBoardDto> 게시글 list
	* @Method 설명 : 인자값은 없으며, 자유게시판의 모든 게시글을 반환한다.
	* @변경이력 :
	* 2020-12-27(BUMSEOK SEO) : selectArticles에서 selectAllArticles로 메서드명 변경
	*/
	@ApiOperation(value = "자유게시판의 모든 게시글을 반환한다.", response = List.class)
	@GetMapping(value="/selectAllArticles")
	public List<FreeBoardDto> selectAllArticles(){
		logger.info("selectArtilces - 호출" + new Date());
		List<FreeBoardDto> list = freeBoardService.selectAllArticles();
		return list;
	}
	
	/**
	* @param articleNo
	* @return freeBoardDto
	* @Method 설명 : 게시글 번호에 해당하는 게시글을 반환한다.
	* @변경이력 :
	* 2020-12-27(BUMSEOK SEO) : 해당 메서드 추가
	*/
	@ApiOperation(value = "게시글 번호에 해당하는 게시글을 반환한다", response = FreeBoardDto.class)
	@GetMapping(value = "/selectArticleByArticleNo/{articleNo}")
	public FreeBoardDto selectArticleByArticleNo(@PathVariable int articleNo){
		logger.info("selectAtricleByArticleNo - 호출" + new Date());
		FreeBoardDto freeBoardDto = freeBoardService.selectArticleByArticleNo(articleNo);
		return freeBoardDto;
	}

	/**
	* @param title
	* @return List<FreeBoardDto> 게시글 list
	* @Method 설명 : 입력한 키워드를 포함하는 제목의 게시글들을 반환한다.
	* @변경이력 :
	*/
	@ApiOperation(value = "입력한 제목에 해당하는 게시글을 반환한다", response = List.class)
	@GetMapping(value = "/selectArticleByTitle/{title}")
	public List<FreeBoardDto> selectArticleByTitle(@PathVariable String title){
		logger.info("selectAtricleByTitle - 호출" + new Date());
		List<FreeBoardDto> list = freeBoardService.selectArticleByTitle(title);
		return list;
	}
	
	/**
	* @param nickname
	* @return List<FreeBoardDto> 게시글 list
	* @Method 설명 : 입력한 닉네임에 해당하는 작성자의 게시글들을 반환한다.
	* @변경이력 :
	*/
	@ApiOperation(value = "입력한 닉네임에 해당하는 게시글을 반환한다", response = List.class)
	@GetMapping(value = "/selectArticleByNickname/{nickname}")
	public List<FreeBoardDto> selectArticleByNickname(@PathVariable String nickname){
		logger.info("selectAtricleByTitle - 호출" + new Date());
		List<FreeBoardDto> list = freeBoardService.selectArticleByNickname(nickname);
		return list;
	}
	
	/**
	* @param freeBoardDto
	* @return ResponseEntity<String> SUCCESS / FAIL
	* @Method 설명 : 게시물 수정을 한다. 성공하면 SUCCECSS, 실패하면 FAIL을 반환
	* @변경이력 :
	*/
	@ApiOperation(value = "자유 게시판의 게시글을 수정한다", response = String.class)
	@PostMapping(value="/updateArticle")
	public ResponseEntity<String> updateArticle(@RequestBody FreeBoardDto freeBoardDto){
		logger.info("updateArticle - 호출" + new Date());
		if(freeBoardService.updateArticle(freeBoardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			// TODO: responseEntity의 의미
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_MODIFIED);
		}
		
	}
	
	/**
	* @param articleNo
	* @return ResponseEntity<String> SUCCESS / FAIL
	* @Method 설명 : 게시글번호에 해당하는 게시글을 삭제한다.  성공하면 SUCCECSS, 실패하면 FAIL을 반환
	* @변경이력 :
	*/
	@ApiOperation(value = "자유 게시판의 게시글을 삭제한다", response = String.class)
	@DeleteMapping(value = "/deleteArticle/{articleNo}")
	public ResponseEntity<String> deleteArticle(@PathVariable int articleNo){
		logger.info("deleteArticle - 호출" + new Date());
		if(freeBoardService.deleteArticle(articleNo)){
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_MODIFIED);
		}
	}
	
}

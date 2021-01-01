package com.solafy.model;

/**
 * AnswerModifyBoardDto
 * 답안 수정 게시판 DTO
 * @author BUMSEOK SEO
 * @since 2020-12-14
 *
 */

/**
* @FileName : AnswerModifyBoardDto.java
* @Project : SOLAFY
* @Date : 2020. 12. 14
* @작성자 : BUMSEOK SEO

* @변경이력 :
* 2021-01-01(BEOMSEOK SEO) : [이름변경] problem -> problemNo [추가] nickname, nickname_submitter 컬럼추가
* 2021-01-01(BEOMSEOK SEO) : 컬럼 데이터 활용을 위한 재배치
* @프로그램 설명 : 답안 수정 게시판 DTO
*/
public class AnswerModifyBoardDto {

	private int articleNo;
	private String uid;
	private String nickname;
	private int problemNo;
	private String title;
	private String uid_submitter;
	private String nickname_submitter;
	private String contents;
	private String regiTime;
	
	
	public AnswerModifyBoardDto() {}


	public AnswerModifyBoardDto(int articleNo, String uid, String nickname, int problemNo, String title,
			String uid_submitter, String nickname_submitter, String contents, String regiTime) {
		super();
		this.articleNo = articleNo;
		this.uid = uid;
		this.nickname = nickname;
		this.problemNo = problemNo;
		this.title = title;
		this.uid_submitter = uid_submitter;
		this.nickname_submitter = nickname_submitter;
		this.contents = contents;
		this.regiTime = regiTime;
	}


	public int getArticleNo() {
		return articleNo;
	}


	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public int getProblemNo() {
		return problemNo;
	}


	public void setProblemNo(int problemNo) {
		this.problemNo = problemNo;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getUid_submitter() {
		return uid_submitter;
	}


	public void setUid_submitter(String uid_submitter) {
		this.uid_submitter = uid_submitter;
	}


	public String getNickname_submitter() {
		return nickname_submitter;
	}


	public void setNickname_submitter(String nickname_submitter) {
		this.nickname_submitter = nickname_submitter;
	}


	public String getContents() {
		return contents;
	}


	public void setContents(String contents) {
		this.contents = contents;
	}


	public String getRegiTime() {
		return regiTime;
	}


	public void setRegiTime(String regiTime) {
		this.regiTime = regiTime;
	}


	@Override
	public String toString() {
		return "AnswerModifyBoardDto [articleNo=" + articleNo + ", uid=" + uid + ", nickname=" + nickname
				+ ", problemNo=" + problemNo + ", title=" + title + ", uid_submitter=" + uid_submitter
				+ ", nickname_submitter=" + nickname_submitter + ", contents=" + contents + ", regiTime=" + regiTime
				+ "]";
	}



	
}

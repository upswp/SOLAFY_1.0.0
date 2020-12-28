package com.solafy.model;

/**
* @FileName : FreeReplyDto.java
* @Project : SOLAFY
* @Date : 2020. 12. 14
* @작성자 : BUMSEOK SEO
* @변경이력 :
* 2020-12-29(BEOMSEOK SEO) : nickname 저장을 위한 필드 추가
* @프로그램 설명 : FreeReplyDto 자유게시판 댓글 DTO
*/
public class FreeReplyDto {

	private int replyNo;
	private int articleNo;
	private String contents;
	private String uid;
	private String nickname;
	private String regiTime;

	public FreeReplyDto() {
	}

	public FreeReplyDto(int replyNo, int articleNo, String contents, String uid, String nickname, String regiTime) {
		super();
		this.replyNo = replyNo;
		this.articleNo = articleNo;
		this.contents = contents;
		this.uid = uid;
		this.nickname = nickname;
		this.regiTime = regiTime;
	}

	public int getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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

	public String getRegiTime() {
		return regiTime;
	}

	public void setRegiTime(String regiTime) {
		this.regiTime = regiTime;
	}

	@Override
	public String toString() {
		return "FreeReplyDto [replyNo=" + replyNo + ", articleNo=" + articleNo + ", contents=" + contents + ", uid="
				+ uid + ", nickname=" + nickname + ", regiTime=" + regiTime + "]";
	}

}

package com.solafy.model;

/**
 * ReplyByAnswerModifyDto
 * 답안 수정 게시판 댓글 DTO
 * @author BUMSEOK SEO
 * @since 2020-12-14
 *
 * @변경이력
 * 2021-01-02 [이주희] nickname 추가
 */

public class AnswerModifyReplyDto {
	private int replyNo;
	private int articleNo;
	private String contents;
	private String uid;
	private String nickname;
	private String regiTime;

	public AnswerModifyReplyDto() {
	}

	public AnswerModifyReplyDto(int replyNo, int articleNo, String contents, String uid, String nickname,
			String regiTime) {
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

	public String getRegiTime() {
		return regiTime;
	}

	public void setRegiTime(String regiTime) {
		this.regiTime = regiTime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "AnswerModifyReplyDto [replyNo=" + replyNo + ", articleNo=" + articleNo + ", contents=" + contents
				+ ", uid=" + uid + ", nickname=" + nickname + ", regiTime=" + regiTime + "]";
	}

}

package com.solafy.model;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 * @변경이력 
 * 20-12-28 [이주희] 닉네임 추가 
 * 21-01-02 [이주희] 컴포넌트 재사용을 용이하게 하기 위해 field 명 수정
 */

public class AnswerByQuestionDto {

	private int replyNo;
	private int articleNo;
	private String contents;
	private String uid;
	private String regiTime;
	private String nickname;

	public AnswerByQuestionDto() {
		super();
	}

	public AnswerByQuestionDto(int replyNo, int articleNo, String contents, String uid, String regiTime,
			String nickname) {
		super();
		this.replyNo = replyNo;
		this.articleNo = articleNo;
		this.contents = contents;
		this.uid = uid;
		this.regiTime = regiTime;
		this.nickname = nickname;
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
		return "AnswerByQuestionDto [replyNo=" + replyNo + ", articleNo=" + articleNo + ", contents=" + contents
				+ ", uid=" + uid + ", regiTime=" + regiTime + ", nickname=" + nickname + "]";
	}

}

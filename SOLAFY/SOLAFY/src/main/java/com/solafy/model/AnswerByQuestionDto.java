package com.solafy.model;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 * @변경이력
 * 20-12-28 닉네임 추가
 */

public class AnswerByQuestionDto {

	private int articleNo;
	private int questionNo;
	private String contents;
	private String uid;
	private String regiTime;
	private String nickname;

	public AnswerByQuestionDto() {
		super();
	}

	public AnswerByQuestionDto(int articleNo, int questionNo, String contents, String uid, String regiTime,
			String nickname) {
		super();
		this.articleNo = articleNo;
		this.questionNo = questionNo;
		this.contents = contents;
		this.uid = uid;
		this.regiTime = regiTime;
		this.nickname = nickname;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public int getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
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
		return "AnswerByQuestionDto [articleNo=" + articleNo + ", questionNo=" + questionNo + ", contents=" + contents
				+ ", uid=" + uid + ", regiTime=" + regiTime + ", nickname=" + nickname + "]";
	}

}

package com.solafy.model;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 */

public class AnswerByQuestionDto {

	private int articleNo;
	private int questionNo;
	private String contents;
	private String uid;
	private String regiTime;

	public AnswerByQuestionDto() {
		super();
	}

	public AnswerByQuestionDto(int articleNo, int questionNo, String contents, String uid, String regiTime) {
		super();
		this.articleNo = articleNo;
		this.questionNo = questionNo;
		this.contents = contents;
		this.uid = uid;
		this.regiTime = regiTime;
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

	@Override
	public String toString() {
		return "AnswerByQuestionDto [articleNo=" + articleNo + ", questionNo=" + questionNo + ", contents=" + contents
				+ ", uid=" + uid + ", regiTime=" + regiTime + "]";
	}

}

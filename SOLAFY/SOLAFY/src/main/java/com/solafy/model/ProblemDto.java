package com.solafy.model;

/**
 * ProblemDto
 * @author Lee AYoung
 * @since 2020-12-13
 */
public class ProblemDto {
	private int problemNo;
	private String multipleChoice;
	private String title;
	private String uid;
	private String contents;
	private String categoryNo;
	private int type;
	private String regiTime;
	private String nickname;
	
	public ProblemDto() {
		super();
	}

	public ProblemDto(int problemNo, String multipleChoice, String title, String uid, String contents,
			String categoryNo, int type, String regiTime, String nickname) {
		super();
		this.problemNo = problemNo;
		this.multipleChoice = multipleChoice;
		this.title = title;
		this.uid = uid;
		this.contents = contents;
		this.categoryNo = categoryNo;
		this.type = type;
		this.regiTime = regiTime;
		this.nickname = nickname;
	}

	public int getProblemNo() {
		return problemNo;
	}

	public void setProblemNo(int problemNo) {
		this.problemNo = problemNo;
	}

	public String getMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(String multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
		return "ProblemDto [problemNo=" + problemNo + ", multipleChoice=" + multipleChoice + ", title=" + title
				+ ", uid=" + uid + ", contents=" + contents + ", categoryNo=" + categoryNo + ", type=" + type
				+ ", regiTime=" + regiTime + ", nickname=" + nickname + "]";
	}
}

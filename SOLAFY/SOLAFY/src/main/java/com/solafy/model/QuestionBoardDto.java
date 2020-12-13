package com.solafy.model;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 */

public class QuestionBoardDto {

	private int articleNo;
	private String title;
	private String contents;
	private String uid;
	private int problemNo;
	private int point;
	private String regiTime;
	private boolean isGroup;
	private int groupNo;

	public QuestionBoardDto() {
		super();
	}

	public QuestionBoardDto(int articleNo, String title, String contents, String uid, int problemNo, int point,
			String regiTime, boolean isGroup, int groupNo) {
		super();
		this.articleNo = articleNo;
		this.title = title;
		this.contents = contents;
		this.uid = uid;
		this.problemNo = problemNo;
		this.point = point;
		this.regiTime = regiTime;
		this.isGroup = isGroup;
		this.groupNo = groupNo;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getProblemNo() {
		return problemNo;
	}

	public void setProblemNo(int problemNo) {
		this.problemNo = problemNo;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getRegiTime() {
		return regiTime;
	}

	public void setRegiTime(String regiTime) {
		this.regiTime = regiTime;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	@Override
	public String toString() {
		return "QuestionBoardDto [articleNo=" + articleNo + ", title=" + title + ", contents=" + contents + ", uid="
				+ uid + ", problemNo=" + problemNo + ", point=" + point + ", regiTime=" + regiTime + ", isGroup="
				+ isGroup + ", groupNo=" + groupNo + "]";
	}

}

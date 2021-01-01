package com.solafy.model;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 * @변경이력 20-12-28 nickname 추가
 */

public class QuestionBoardDto {

	private int articleNo;
	private String nickname;
	private int problemNo;
	private String title;
	private String regiTime;
	private String contents;
	private String uid;
	private int point;
	private boolean isGroup;
	private int groupNo;
	
	public QuestionBoardDto() {}

	public QuestionBoardDto(int articleNo, String nickname, int problemNo, String title, String regiTime,
			String contents, String uid, int point, boolean isGroup, int groupNo) {
		super();
		this.articleNo = articleNo;
		this.nickname = nickname;
		this.problemNo = problemNo;
		this.title = title;
		this.regiTime = regiTime;
		this.contents = contents;
		this.uid = uid;
		this.point = point;
		this.isGroup = isGroup;
		this.groupNo = groupNo;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
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

	public String getRegiTime() {
		return regiTime;
	}

	public void setRegiTime(String regiTime) {
		this.regiTime = regiTime;
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

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
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
		return "QuestionBoardDto [articleNo=" + articleNo + ", nickname=" + nickname + ", problemNo=" + problemNo
				+ ", title=" + title + ", regiTime=" + regiTime + ", contents=" + contents + ", uid=" + uid + ", point="
				+ point + ", isGroup=" + isGroup + ", groupNo=" + groupNo + "]";
	}


}

package com.solafy.model;

import java.util.List;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 * @변경이력 20-12-28 nickname 추가
 */

public class PracticeTestBoardDto {

	private int articleNo;
	private String uid;
	private String title;
	private String regiTime;
	private String startTime;
	private String endTime;
	private String limitTime;
	private List<ProblemDto> problems;
	private boolean isGroup;
	private int groupNo;
	private String nickname;

	public PracticeTestBoardDto() {
		super();
	}

	public PracticeTestBoardDto(int articleNo, String uid, String title, String regiTime, String startTime,
			String endTime, String limitTime, List<ProblemDto> problems, boolean isGroup, int groupNo,
			String nickname) {
		super();
		this.articleNo = articleNo;
		this.uid = uid;
		this.title = title;
		this.regiTime = regiTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.limitTime = limitTime;
		this.problems = problems;
		this.isGroup = isGroup;
		this.groupNo = groupNo;
		this.nickname = nickname;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}

	public List<ProblemDto> getProblems() {
		return problems;
	}

	public void setProblems(List<ProblemDto> problems) {
		this.problems = problems;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "PracticeTestBoardDto [articleNo=" + articleNo + ", uid=" + uid + ", title=" + title + ", regiTime="
				+ regiTime + ", startTime=" + startTime + ", endTime=" + endTime + ", limitTime=" + limitTime
				+ ", problems=" + problems + ", isGroup=" + isGroup + ", groupNo=" + groupNo + ", nickname=" + nickname
				+ "]";
	}

}

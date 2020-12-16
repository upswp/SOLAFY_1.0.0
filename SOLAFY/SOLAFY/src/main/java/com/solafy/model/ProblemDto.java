package com.solafy.model;

import java.util.List;

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
	private double starScore;
	private int categoryNo;
	private int type;
	private List<HashTagDto> hashtags;
	private ProblemAnswerDto answer;
	
	public ProblemDto() {
		super();
	}

	public ProblemDto(int problemNo, String multipleChoice, String title, String uid, String contents, double starScore,
			int categoryNo, int type, List<HashTagDto> hashtags, ProblemAnswerDto answer) {
		super();
		this.problemNo = problemNo;
		this.multipleChoice = multipleChoice;
		this.title = title;
		this.uid = uid;
		this.contents = contents;
		this.starScore = starScore;
		this.categoryNo = categoryNo;
		this.type = type;
		this.hashtags = hashtags;
		this.answer = answer;
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

	public double getStarScore() {
		return starScore;
	}

	public void setStarScore(double starScore) {
		this.starScore = starScore;
	}

	public int getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<HashTagDto> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<HashTagDto> hashtags) {
		this.hashtags = hashtags;
	}

	public ProblemAnswerDto getAnswer() {
		return answer;
	}

	public void setAnswer(ProblemAnswerDto answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "ProblemDto [problemNo=" + problemNo + ", multipleChoice=" + multipleChoice + ", title=" + title
				+ ", uid=" + uid + ", contents=" + contents + ", starScore=" + starScore + ", categoryNo=" + categoryNo
				+ ", type=" + type + ", hashtags=" + hashtags + ", answer=" + answer + "]";
	}
}

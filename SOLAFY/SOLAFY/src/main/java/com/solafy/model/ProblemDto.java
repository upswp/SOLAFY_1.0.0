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
	private double starScore;
	private int categorySmallNo;
	private int type;
	
	public ProblemDto() {
		super();
	}

	public ProblemDto(int problemNo, String multipleChoice, String title, String uid, String contents, double starScore,
			int categorySmallNo, int type) {
		super();
		this.problemNo = problemNo;
		this.multipleChoice = multipleChoice;
		this.title = title;
		this.uid = uid;
		this.contents = contents;
		this.starScore = starScore;
		this.categorySmallNo = categorySmallNo;
		this.type = type;
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

	public int getCategorySmallNo() {
		return categorySmallNo;
	}

	public void setCategorySmallNo(int categorySmallNo) {
		this.categorySmallNo = categorySmallNo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ProblemDto [problemNo=" + problemNo + ", multipleChoice=" + multipleChoice + ", title=" + title
				+ ", uid=" + uid + ", contents=" + contents + ", starScore=" + starScore + ", categorySmallNo="
				+ categorySmallNo + ", type=" + type + "]";
	}
}

package com.solafy.model;
/**
 * ProblemAnswerDto
 * @author Lee AYoung
 * @since 2020-12-13
 */
public class ProblemAnswerDto {
	private int problemNo;
	private String answer;
	private String solution;
	private String keyword;
	
	public ProblemAnswerDto() {
		super();
	}

	public ProblemAnswerDto(int problemNo, String answer, String solution, String keyword) {
		super();
		this.problemNo = problemNo;
		this.answer = answer;
		this.solution = solution;
		this.keyword = keyword;
	}

	public int getProblemNo() {
		return problemNo;
	}

	public void setProblemNo(int problemNo) {
		this.problemNo = problemNo;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "ProblemAnswerDto [problemNo=" + problemNo + ", answer=" + answer + ", solution=" + solution
				+ ", keyword=" + keyword + "]";
	}
}

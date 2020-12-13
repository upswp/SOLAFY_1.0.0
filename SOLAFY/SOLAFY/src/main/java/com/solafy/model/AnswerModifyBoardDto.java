package com.solafy.model;

/**
 * AnswerModifyBoardDto
 * 답안 수정 게시판 DTO
 * @author BUMSEOK SEO
 * @since 2020-12-14
 *
 */

public class AnswerModifyBoardDto {

	private int articleNo;
	private String uid;
	private int problem;
	private String uid_submitter;
	private String contents;
	private String regiTime;

	public AnswerModifyBoardDto() {
	}

	public AnswerModifyBoardDto(int articleNo, String uid, int problem, String uid_submitter, String contents,
			String regiTime) {
		super();
		this.articleNo = articleNo;
		this.uid = uid;
		this.problem = problem;
		this.uid_submitter = uid_submitter;
		this.contents = contents;
		this.regiTime = regiTime;
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

	public int getProblem() {
		return problem;
	}

	public void setProblem(int problem) {
		this.problem = problem;
	}

	public String getUid_submitter() {
		return uid_submitter;
	}

	public void setUid_submitter(String uid_submitter) {
		this.uid_submitter = uid_submitter;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getRegiTime() {
		return regiTime;
	}

	public void setRegiTime(String regiTime) {
		this.regiTime = regiTime;
	}

	@Override
	public String toString() {
		return "AnswerModifyBoardDto [articleNo=" + articleNo + ", uid=" + uid + ", problem=" + problem
				+ ", uid_submitter=" + uid_submitter + ", contents=" + contents + ", regiTime=" + regiTime + "]";
	}

}

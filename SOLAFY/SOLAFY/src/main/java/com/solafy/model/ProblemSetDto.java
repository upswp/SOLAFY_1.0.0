package com.solafy.model;

import java.util.List;
/**
 * 
* @FileName : ProblemSetDto.java
* @Project : SOLAFY
* @Date : 2020. 12. 20
* @작성자 : Park Sangwoo

* @변경이력 :
* @프로그램 설명 : 문제집 관련 Dto
 */
public class ProblemSetDto {
	/* private 설정 
	 * 타입 잘 보고 설정
	 * toString 만들어주기
	 * 생성자는 기본 + 모든 맴버변수 용 만들기(총 2개)
	 * getter, setter만들어주기
	 */
	
	private int problemSetNo;
	private String uid;
	private String title;
	private String regiTime;
	
	public ProblemSetDto() {
		super();
	}
	public int getProblemSetNo() {
		return problemSetNo;
	}
	public void setProblemSetNo(int problemSetNo) {
		this.problemSetNo = problemSetNo;
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
	@Override
	public String toString() {
		return "ProblemSetDto [problemSetNo=" + problemSetNo + ", uid=" + uid + ", title=" + title + ", regiTime="
				+ regiTime + "]";
	}
}

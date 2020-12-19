package com.solafy.model;


/**
* @FileName : GroupBoardDto.java
* @Project : SOLAFY
* @Date : 2020. 12. 19
* @작성자 : Yun Kihyeon

* @변경이력 :
* @프로그램 설명 : GroupBoardDto
*/
public class GroupBoardDto {
	private int boardNo;
	private String title;
	private int groupNo;
	public GroupBoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GroupBoardDto(int boardNo, String title, int groupNo) {
		super();
		this.boardNo = boardNo;
		this.title = title;
		this.groupNo = groupNo;
	}
	@Override
	public String toString() {
		return "GroupBoardDto [boardNo=" + boardNo + ", title=" + title + ", groupNo=" + groupNo + "]";
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
}

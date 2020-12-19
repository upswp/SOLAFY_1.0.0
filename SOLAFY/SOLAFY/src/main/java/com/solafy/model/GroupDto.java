package com.solafy.model;


/**
* @FileName : GroupDto.java
* @Project : SOLAFY
* @Date : 2020. 12. 18
* @작성자 : Yun Kihyeon

* @변경이력 :
* @프로그램 설명 : GroupDto
*/
public class GroupDto {

	private int groupNo;
	private String title;
	private byte type;
	private String description;
	
	public GroupDto() {}
	public GroupDto(int groupNo, String title, byte type, String description) {
		super();
		this.groupNo = groupNo;
		this.title = title;
		this.type = type;
		this.description = description;
	}
	@Override
	public String toString() {
		return "GroupDto [groupNO=" + groupNo + ", title=" + title + ", type=" + type + ", description=" + description
				+ "]";
	}
	public int getGroupNO() {
		return groupNo;
	}
	public void setGroupNO(int groupNo) {
		this.groupNo = groupNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

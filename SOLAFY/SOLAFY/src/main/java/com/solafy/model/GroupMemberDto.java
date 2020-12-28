package com.solafy.model;


/**
* @FileName : GroupMemberDto.java
* @Project : SOLAFY
* @Date : 2020. 12. 19
* @작성자 : Yun Kihyeon

* @변경이력 :
* @프로그램 설명 : GroupMemberDto
*/
public class GroupMemberDto {
	private String uid;
	private int groupNo;
	private byte grade;
	private String statusMessage;
	private String regiMessage;
	private String nickName;
	public GroupMemberDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GroupMemberDto(String uid, int groupNo, byte grade, String statusMessage, String regiMessage,
			String nickName) {
		super();
		this.uid = uid;
		this.groupNo = groupNo;
		this.grade = grade;
		this.statusMessage = statusMessage;
		this.regiMessage = regiMessage;
		this.nickName = nickName;
	}
	@Override
	public String toString() {
		return "GroupMemberDto [uid=" + uid + ", groupNo=" + groupNo + ", grade=" + grade + ", statusMessage="
				+ statusMessage + ", regiMessage=" + regiMessage + ", nickName=" + nickName + "]";
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
	public byte getGrade() {
		return grade;
	}
	public void setGrade(byte grade) {
		this.grade = grade;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getRegiMessage() {
		return regiMessage;
	}
	public void setRegiMessage(String regiMessage) {
		this.regiMessage = regiMessage;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}

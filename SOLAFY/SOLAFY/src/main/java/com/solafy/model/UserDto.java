package com.solafy.model;

/**
 * 
 * @FileName UserDto.java
 * @Project SOLAFY
 * @Date 2020. 12. 27.
 * @Author 이주희
 * 
 * @변경이력
 */

public class UserDto {
	private String uid;
	private String nickname;
	private String statusMessage;

	public UserDto() {
		super();
	}

	public UserDto(String uid, String nickname, String statusMessage) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.statusMessage = statusMessage;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		return "UserDto [uid=" + uid + ", nickname=" + nickname + ", statusMessage=" + statusMessage + "]";
	}

}

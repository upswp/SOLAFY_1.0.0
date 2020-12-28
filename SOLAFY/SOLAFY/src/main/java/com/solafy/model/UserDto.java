package com.solafy.model;

/**
 * 
 * @FileName UserDto.java
 * @Project SOLAFY
 * @Date 2020. 12. 27.
 * @Author 이주희
 * 
 * @변경이력 20-12-28 admin 추가
 */

public class UserDto {
	private String uid;
	private String nickname;
	private String statusMessage;
	private boolean admin;

	public UserDto() {
		super();
	}

	public UserDto(String uid, String nickname, String statusMessage, boolean admin) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.statusMessage = statusMessage;
		this.admin = admin;
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "UserDto [uid=" + uid + ", nickname=" + nickname + ", statusMessage=" + statusMessage + ", admin="
				+ admin + "]";
	}

}

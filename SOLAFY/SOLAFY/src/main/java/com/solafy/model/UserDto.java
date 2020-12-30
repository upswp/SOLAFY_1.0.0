package com.solafy.model;

/**
 * 
 * @FileName UserDto.java
 * @Project SOLAFY
 * @Date 2020. 12. 27.
 * @Author 이주희
 * 
 * @변경이력 
 * 20-12-28 admin 추가
 * 20-12-30 admin type 변경 (boolean -> byte)
 */

public class UserDto {
	private String uid;
	private String nickname;
	private String statusMessage;
	private byte admin;

	public UserDto() {
		super();
	}

	public UserDto(String uid, String nickname, String statusMessage, byte admin) {
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

	public byte getAdmin() {
		return admin;
	}

	public void setAdmin(byte admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "UserDto [uid=" + uid + ", nickname=" + nickname + ", statusMessage=" + statusMessage + ", admin="
				+ admin + "]";
	}

}

package com.solafy.model;

/**
 * HashTagDto
 * @author Lee AYoung
 * @since 2020-12-14
 */

public class HashTagDto {
	private int hashTagNo;
	private String hashTag;
	public HashTagDto() {
		super();
	}
	public HashTagDto(int hashTagNo, String hashTag) {
		super();
		this.hashTagNo = hashTagNo;
		this.hashTag = hashTag;
	}
	public int getHashTagNo() {
		return hashTagNo;
	}
	public void setHashTagNo(int hashTagNo) {
		this.hashTagNo = hashTagNo;
	}
	public String getHashTag() {
		return hashTag;
	}
	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}
	@Override
	public String toString() {
		return "HashTagDto [hashTagNo=" + hashTagNo + ", hashTag=" + hashTag + "]";
	}
}

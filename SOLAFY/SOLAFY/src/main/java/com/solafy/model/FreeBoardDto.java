package com.solafy.model;

/**
 * FreeBoardDto
 * 자유게시판 DTO
 * @author BUMSEOK SEO
 * @since 2020-12-14
 */

public class FreeBoardDto {

	private int articleNo;
	private String title;
	private String contents;
	private String uid;
	private int likeCount;
	private boolean isNotice;
	private String regiTime;
	private boolean isGroup;
	private int groupNo;
	private int boardNo;

	public FreeBoardDto() {
	}

	public FreeBoardDto(int articleNo, String title, String contents, String uid, int likeCount, boolean isNotice,
			String regiTime, boolean isGroup, int groupNo, int boardNo) {
		super();
		this.articleNo = articleNo;
		this.title = title;
		this.contents = contents;
		this.uid = uid;
		this.likeCount = likeCount;
		this.isNotice = isNotice;
		this.regiTime = regiTime;
		this.isGroup = isGroup;
		this.groupNo = groupNo;
		this.boardNo = boardNo;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public boolean isNotice() {
		return isNotice;
	}

	public void setNotice(boolean isNotice) {
		this.isNotice = isNotice;
	}

	public String getRegiTime() {
		return regiTime;
	}

	public void setRegiTime(String regiTime) {
		this.regiTime = regiTime;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	@Override
	public String toString() {
		return "FreeBoardDto [articleNo=" + articleNo + ", title=" + title + ", contents=" + contents + ", uid=" + uid
				+ ", likeCount=" + likeCount + ", isNotice=" + isNotice + ", regiTime=" + regiTime + ", isGroup="
				+ isGroup + ", groupNo=" + groupNo + ", boardNo=" + boardNo + "]";
	}

}

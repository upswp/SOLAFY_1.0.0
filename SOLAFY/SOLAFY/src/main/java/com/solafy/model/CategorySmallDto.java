package com.solafy.model;
/**
 * CategorySmallDto
 * @author Lee AYoung
 * @since 2020-12-14
 */
public class CategorySmallDto {
	private int categoryNo;
	private String categoryName;
	public CategorySmallDto() {
		super();
	}
	public CategorySmallDto(int categoryNo, String categoryName) {
		super();
		this.categoryNo = categoryNo;
		this.categoryName = categoryName;
	}
	public int getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "CategorySmallDto [categoryNo=" + categoryNo + ", categoryName=" + categoryName + "]";
	}
}

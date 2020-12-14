package com.solafy.model;
/**
 * CategorySmallDto
 * @author Lee AYoung
 * @since 2020-12-14
 */
public class CategorySmallDto {
	private int categoryNo;
	private String categoryName;
	private int categoryMediumNo;
	public CategorySmallDto() {
		super();
	}
	public CategorySmallDto(int categoryNo, String categoryName, int categoryMediumNo) {
		super();
		this.categoryNo = categoryNo;
		this.categoryName = categoryName;
		this.categoryMediumNo = categoryMediumNo;
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
	public int getCategoryMediumNo() {
		return categoryMediumNo;
	}
	public void setCategoryMediumNo(int categoryMediumNo) {
		this.categoryMediumNo = categoryMediumNo;
	}
	@Override
	public String toString() {
		return "CategorySmallDto [categoryNo=" + categoryNo + ", categoryName=" + categoryName + ", categoryMediumNo="
				+ categoryMediumNo + "]";
	}
}

package com.solafy.mapper.problem;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.CategoryLargeDto;
import com.solafy.model.CategoryMediumDto;
import com.solafy.model.CategorySmallDto;

/**
* @FileName : CategoryMapper.java
* @Project : SOLAFY
* @Date : 2020. 12. 25.
* @작성자 : Lee Ayoung

* @변경이력 :
* @프로그램 설명 : Category Mapper
*/
@Mapper
public interface CategoryMapper {

	/**
	* @return List<CategoryLargeDto> - 대분류 카테고리 리스트 : categoryNo, categoryName
	* @throws SQLException
	* @Method 설명 : 대분류 카테고리 리스트 반환
	* @변경이력 :
	*/
	public List<CategoryLargeDto> selectCategoryLargeList() throws SQLException;
	
	/**
	* @param largeCategoryNo - 대분류 카테고리 번호
	* @return List<CategoryMediumDto> - 중분류 카테고리 리스트 : categoryNo, categoryName
	* @throws SQLException
	* @Method 설명 : 대분류에 해당하는 중분류 카테고리 리스트 반환
	* @변경이력 :
	*/
	public List<CategoryMediumDto> selectCategoryMediumList(int largeCategoryNo) throws SQLException;
	
	/**
	* @param mediumCategoryNo - 중분류 카테고리 번호
	* @return List<CategorySmallDto> - 소분류 카테고리 리스트 : categoryNo, categoryName
	* @throws SQLException
	* @Method 설명 : 중분류에 해당하는 소분류 카테고리 리스트 반환
	* @변경이력 :
	*/
	public List<CategorySmallDto> selectCategorySmallList(int mediumCategoryNo) throws SQLException;
}

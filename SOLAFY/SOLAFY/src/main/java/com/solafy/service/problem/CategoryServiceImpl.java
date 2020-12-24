package com.solafy.service.problem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.problem.CategoryMapper;
import com.solafy.model.CategoryLargeDto;
import com.solafy.model.CategoryMediumDto;
import com.solafy.model.CategorySmallDto;

/**
 * @FileName : ProblemServiceImpl.java
 * @Project : SOLAFY
 * @Date : 2020. 12. 16.
 * @작성자 : Lee Ayoung
 * 
 * @변경이력 :
 * @프로그램 설명 : Problem Service Implement Class
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public List<CategoryLargeDto> selectCategoryLargeList() throws Exception {
		return categoryMapper.selectCategoryLargeList();
	}

	@Override
	public List<CategoryMediumDto> selectCategoryMediumList(int largeCategoryNo) throws Exception {
		return categoryMapper.selectCategoryMediumList(largeCategoryNo);
	}

	@Override
	public List<CategorySmallDto> selectCategorySmallList(int mediumCategoryNo) throws Exception {
		return categoryMapper.selectCategorySmallList(mediumCategoryNo);
	}
}

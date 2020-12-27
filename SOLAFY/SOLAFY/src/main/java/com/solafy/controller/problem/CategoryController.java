package com.solafy.controller.problem;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solafy.model.CategoryLargeDto;
import com.solafy.model.CategoryMediumDto;
import com.solafy.model.CategorySmallDto;
import com.solafy.service.problem.CategoryService;

import io.swagger.annotations.ApiOperation;

/**
* @FileName : CategoryController.java
* @Project : SOLAFY
* @Date : 2020. 12. 25.
* @작성자 : Lee Ayoung

* @변경이력 :
* @프로그램 설명 : Cateogory Controller
*/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/category")
public class CategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private CategoryService categoryService;
	
	
	/**
	* @return ResponseEntity<List<CategoryLargeDto>> - 대분류 카테고리 리스트, 응답형태
	* @throws Exception
	* @Method 설명 : 대분류 카테고리 리스트 반환
	* @변경이력 :
	*/
	@ApiOperation(value="대분류 카테고리 리스트를 반환한다.", response = List.class)
	@GetMapping(value = "/large")
	public ResponseEntity<List<CategoryLargeDto>> selectCategoryLargeList() throws Exception{
		logger.debug("selectCategoryLargeList - 호출");
		return new ResponseEntity<List<CategoryLargeDto>>(categoryService.selectCategoryLargeList(),HttpStatus.OK);
	}
	
	/**
	* @param largeCategoryNo - 대분류 카테고리 번호
	* @return ResponseEntity<List<CategoryMediumDto>> - 중분류 카테고리 리스트, 응답형태
	* @throws Exception
	* @Method 설명 : 대분류에 해당하는 중분류 카테고리 리스트 반환
	* @변경이력 :
	*/
	@ApiOperation(value="대분류에 해당하는 중분류 카테고리 리스트를 반환한다.", response = List.class)
	@GetMapping(value = "/medium/{largeCategoryNo}")
	public ResponseEntity<List<CategoryMediumDto>> selectCategoryMediumList(@PathVariable int largeCategoryNo) throws Exception{
		logger.debug("selectCategoryMediumList - 호출");
		return new ResponseEntity<List<CategoryMediumDto>>(categoryService.selectCategoryMediumList(largeCategoryNo),HttpStatus.OK);
	}
	
	/**
	* @param mediumCategoryNo - 중분류 카테고리 번호
	* @return ResponseEntity<List<CategorySmallDto>> - 소분류 카테고리 리스트, 응답형태
	* @throws Exception
	* @Method 설명 : 중분류에 해당하는 소분류 카테고리 리스트 반환
	* @변경이력 :
	*/
	@ApiOperation(value="중분류에 해당하는 소분류 카테고리 리스트를 반환한다.", response = List.class)
	@GetMapping(value = "/small/{mediumCategoryNo}")
	public ResponseEntity<List<CategorySmallDto>> selectCategorySmallList(@PathVariable int mediumCategoryNo) throws Exception{
		logger.debug("selectCategorySmallList - 호출");
		return new ResponseEntity<List<CategorySmallDto>>(categoryService.selectCategorySmallList(mediumCategoryNo),HttpStatus.OK);
	}
}

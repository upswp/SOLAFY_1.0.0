package com.solafy.service.problem;

import java.util.List;

import com.solafy.model.CategoryLargeDto;
import com.solafy.model.CategoryMediumDto;
import com.solafy.model.CategorySmallDto;
import com.solafy.model.HashTagDto;
import com.solafy.model.ProblemDto;

/**
* @FileName : ProblemAnswerService.java
* @Project : SOLAFY
* @Date : 2020. 12. 19.
* @작성자 : Lee Ayoung

* @변경이력 :
* @프로그램 설명 : Problem Service Answer Interface
*/
public interface ProblemAnswerService {
	// 문제의 정보 반환(상세보기 용)
	public ProblemDto selectProblem(int problemNo);
}

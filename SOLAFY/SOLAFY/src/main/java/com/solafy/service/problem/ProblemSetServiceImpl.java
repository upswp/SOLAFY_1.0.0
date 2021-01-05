package com.solafy.service.problem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solafy.mapper.problem.ProblemMapper;
import com.solafy.mapper.problem.ProblemSetMapper;
import com.solafy.model.ProblemAnswerDto;
import com.solafy.model.ProblemDto;
import com.solafy.model.ProblemSetDto;

/**
 * 
* @FileName : ProblemSetServiceImpl.java
* @Project : SOLAFY
* @Date : 2020. 12. 22
* @작성자 : Park Sangwoo

* @변경이력 :
* @프로그램 설명 : ProblemSetServiceImpl 문제에 관한 문제집 기능 구현
 */
@Service
public class ProblemSetServiceImpl implements ProblemSetService {

	@Autowired
	private ProblemSetMapper problemSetMapper;
	
	@Autowired
	private ProblemMapper problemMapper;

	@Override
	public List<ProblemSetDto> selectAllProblemSet() throws Exception{
		return problemSetMapper.selectAllProblemSet();
	}
	
	@Override
	@Transactional
	public Map<String,Object> selectProblemSetByNo(int problemSetNo) throws Exception{
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		//List<ProblemDto>
		map.put("problemList",problemSetMapper.selectProblemSetMapping(problemSetNo));
		
		//ProblemSetDto
		map.put("problemSet", problemSetMapper.selectProblemSetByNo(problemSetNo));
		
		return map;
	}

	@Override
	public List<ProblemSetDto> selectProblemSetByWriter(String uid) throws Exception{
		return problemSetMapper.selectProblemSetByWriter(uid);
	}

	@Override
	public List<ProblemSetDto> selectProblemSetByTitle(String title) throws Exception {
		return problemSetMapper.selectProblemSetByTitle(title);
	}
	
	//TODO : <문제집 등록 프로세스>
	/*1.[문제집 만들기] 클릭 (유저) 
	 *2.(유저가 문제집 정보 입력 , title 작성) [문제 만들기] 버튼 클릭 (유저) createProblemSet 구동 (시스템)
	 *2-1.TODO : 문제집 생성 테이블과, 문제 생성 테이블 만들기
	 *			-- 문제집과 문제를 등록할때 중간에 취소 하는 상황을 고려해서 
	 *			-- 만든 정보를 완료할때 flag를 이용하여 true/false 조정, 문제도 이하 동일
	 *			-- 중간 [취소] 버튼 클릭시 uid를 이용하여 해당 사용자가 작성한 문제, 문제집 정보를 모두 삭제.
	 *			-- TODO : flag 컬럼을 만들어서 uid, flag부분을 확인해서 삭제하도록 변경! 임시데이터일때 false, 완료되면 ture.
	 *			-- TODO : 기존의 createProblem, createProblemSet 쿼리문 수정. updateFlag 메서드 만들기.
	 *			-- TODO : 특수상황이 발생할때 삭제가 이뤄지도록 쿼리문 추가.
	 *3. 문제를 계속 만든다. (시스템)_createProblem
	 *4. 문제만들기를 완료하면 [문제집 생성하기]버튼 클릭 (유저) updateFlag실행 (시스템)
	 * */
	@Override
	public int createProblemSet(ProblemSetDto problemSet) throws Exception {
		problemSetMapper.createProblemSet(problemSet);
		return  problemSet.getProblemSetNo();
	}

	@Override
	@Transactional
	public boolean updateProblemSet(HashMap<String, Object> map) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		ProblemSetDto problemSet = mapper.convertValue(map.get("problemSet"), new TypeReference<ProblemSetDto>() {
		});
		List<ProblemDto> problemList = mapper.convertValue(map.get("problemList"),new TypeReference<List<ProblemDto>>() {});
		
		// 문제집 내용 수정
		boolean result = (problemSetMapper.updateProblemSet(problemSet)>0);
		
		// 문제와 문제집 매핑 해제
		result &= (problemSetMapper.deleteProblemSetMapping(problemSet.getProblemSetNo())>0);
		
		// 문제와 문제집 매핑
		for(ProblemDto problem:problemList) {
			result &= (problemMapper.createProblemSetMapping(problemSet.getProblemSetNo(), problem.getProblemNo())>0);
		}
		return result;
	}

	//TODO : 삭제시 problemsetmapping 에서도 같이 삭제가 되는지 확인.
	@Override
	public boolean deleteProblemSet(int problemSetNo) throws Exception {
		return problemSetMapper.deleteProblemSet(problemSetNo) == 1;
	}

	@Override
	public boolean updateProblemSetFlag(String uid) throws Exception {
		return (problemSetMapper.updatePrblemSetFlag(uid)>0);
	}

	@Override
	@Transactional
	public boolean deleteProblemSetFlag(String uid) throws Exception {
		boolean result = (problemSetMapper.deleteProblemSetFlag(uid)>0);
		result &= (problemMapper.deleteProblemFlag(uid)>0);
		
		return result;
	}

}

package com.solafy.service.board;

import java.util.List;

import com.solafy.model.PracticeTestBoardDto;

/**
 * 
 * @FileName PracticeTestBoardService.java
 * @Project SOLAFY
 * @Date 2020. 12. 14.
 * @author 이주희
 *
 * @변경이력
 * 20-01-02 articleNo에서 practiceNo로 수정
 */

public interface PracticeTestBoardService {
	/**
	 * 모의고사 등록 및 문제 mapping
	 * 
	 * @param pDto - PracticeTestBoardDto
	 * @return boolean, 등록 성공 시 true 반환
	 * @throws Exception
	 * 
	 * @변경이력
	 */
	public boolean createPracticeTest(PracticeTestBoardDto pDto) throws Exception;

	/**
	 * 모든 모의고사 반환
	 * 
	 * @return PracticeTestBoardDto의 List - 번호, uid, 제목, 등록시간, 시작시간, 종료시간, 별명 포함
	 * @throws Exception
	 * 
	 * @변경이력
	 */
	public List<PracticeTestBoardDto> selectAllPracticeTest() throws Exception;

	/**
	 * 모의고사 번호로 모의고사 상세 내용 반환
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return PracticeTestBoardDto - 번호, uid, 제목, 등록시간, 시작시간, 종료시간, 제한시간, 문제목록, 별명 포함
	 * @throws Exception
	 * 
	 * @변경이력
	 * 20-01-02 반환 값을 PracticeTestBoardDto에서 List<PracticeTestBoardDto>로 수정
	 */
	public List<PracticeTestBoardDto> selectPracticeTestByPracticeNo(int practiceNo) throws Exception;

	/**
	 * 검색된 별명과 작성자 별명이 동일한 모의고사들 반환
	 * 
	 * @param nickname - String, 검색된 nickname
	 * @return PracticeTestBoardDto의 List - 번호, uid, 제목, 등록시간, 시작시간, 종료시간, 별명 포함
	 * @throws Exception
	 * 
	 * @변경이력
	 * 20-12-28 uid 검색에서 별명 검색으로 수정
	 */
	public List<PracticeTestBoardDto> selectPracticeTestByWriter(String nickname) throws Exception;

	/**
	 * 검색된 키워드가 제목에 포함된 모의고사들 반환
	 * 
	 * @param title - String, 검색된 제목 키워드
	 * @return PracticeTestBoardDto의 List - 번호, uid, 제목, 등록시간, 시작시간, 종료시간, 별명 포함
	 * @throws Exception
	 * 
	 * @변경이력
	 */
	public List<PracticeTestBoardDto> selectPracticeTestByTitle(String title) throws Exception;

	/**
	 * 모의고사 정보 수정 및 문제 mapping 갱신
	 * 
	 * @param pDto - PracticeTestBoardDto - 제목, 등록,시작,종료,제한시간, 문제목록 변경
	 * @return boolean, 정상적으로 수정 시 true 반환
	 * @throws Exception
	 * 
	 * @변경이력
	 */
	public boolean updatePracticeTest(PracticeTestBoardDto pDto) throws Exception;

	/**
	 * 모의고사 삭제 및 문제 mapping 삭제
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return boolean, 정상적으로 삭제 시 true 반환
	 * @throws Exception
	 * 
	 * @변경이력
	 */
	public boolean deleltePracticeTest(int practiceNo) throws Exception;

}

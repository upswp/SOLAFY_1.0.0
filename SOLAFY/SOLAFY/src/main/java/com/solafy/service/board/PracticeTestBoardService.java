package com.solafy.service.board;

import java.util.List;

import com.solafy.model.PracticeTestBoardDto;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 */

public interface PracticeTestBoardService {
	/**
	 * 모의고사 등록 및 문제 mapping
	 * 
	 * @param pDto - PracticeTestBoardDto
	 * @return boolean, 등록 성공 시 true 반환
	 * @throws Exception
	 */
	public boolean createPracticeTest(PracticeTestBoardDto pDto) throws Exception;

	/**
	 * 모든 모의고사 반환
	 * 
	 * @return PracticeTestBoardDto의 List - 번호, 작성자, 제목, 등록시간, 시작시간, 종료시간 포함
	 * @throws Exception
	 */
	public List<PracticeTestBoardDto> selectAllPracticeTest() throws Exception;

	/**
	 * 모의고사 번호로 모의고사 상세 내용 반환
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return PracticeTestBoardDto - 번호, 작성자, 제목, 등록시간, 시작시간, 종료시간, 제한시간, 문제목록 포함
	 * @throws Exception
	 */
	public PracticeTestBoardDto selectPracticeTestByArticleNo(int articleNo) throws Exception;

	/**
	 * 검색된 uid와 작성자 uid가 동일한 모의고사들 반환
	 * 
	 * @param uid - String, 검색된 uid
	 * @return PracticeTestBoardDto의 List - 번호, 작성자, 제목, 등록시간, 시작시간, 종료시간 포함
	 * @throws Exception
	 */
	public List<PracticeTestBoardDto> selectPracticeTestByWriter(String uid) throws Exception;

	/**
	 * 모의고사 정보 수정 및 문제 mapping 갱신
	 * 
	 * @param pDto - PracticeTestBoardDto - 제목, 등록,시작,종료,제한시간, 문제목록 변경
	 * @return boolean, 정상적으로 수정 시 true 반환
	 * @throws Exception
	 */
	public boolean updatePracticeTest(PracticeTestBoardDto pDto) throws Exception;

	/**
	 * 모의고사 삭제 및 문제 mapping 삭제
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return boolean, 정상적으로 삭제 시 true 반환
	 * @throws Exception
	 */
	public boolean deleltePracticeTest(int articleNo) throws Exception;

}

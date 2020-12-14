package com.solafy.mapper.board;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.solafy.model.PracticeTestBoardDto;

/**
 * 
 * @author Juhee Lee
 * @since 2020-12-14
 *
 */

public interface PracticeTestBoardMapper {

	/**
	 * 모의고사 등록
	 * 
	 * @param pDto - PracticeTestBoardDto
	 * @return int, 등록된 행의 개수 반환
	 * @throws SQLException
	 */
	public int createPracticeTest(PracticeTestBoardDto pDto) throws SQLException;

	/**
	 * 모든 모의고사 반환
	 * 
	 * @return PracticeTestBoardDto의 List - 번호, 작성자, 제목, 등록시간, 시작시간, 종료시간 포함
	 * @throws SQLException
	 */
	public List<PracticeTestBoardDto> selectAllPracticeTest() throws SQLException;

	/**
	 * 모의고사 번호로 모의고사 상세 내용 반환
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return PracticeTestBoardDto - 번호, 작성자, 제목, 등록시간, 시작시간, 종료시간, 제한시간
	 * @throws SQLException
	 */
	public PracticeTestBoardDto selectPracticeTestByArticleNo(int articleNo) throws SQLException;

	/**
	 * 검색된 uid와 작성자 uid가 동일한 모의고사들 반환
	 * 
	 * @param uid - String, 검색된 uid
	 * @return PracticeTestBoardDto의 List - 번호, 작성자, 제목, 등록시간, 시작시간, 종료시간 포함
	 * @throws SQLException
	 */
	public List<PracticeTestBoardDto> selectPracticeTestByWriter(String uid) throws SQLException;

	/**
	 * 모의고사 정보 업데이트
	 * 
	 * @param pDto - PracticeTestBoardDto - 제목, 등록,시작,종료,제한시간, 변경
	 * @return int, 업데이트된 행의 개수 반환
	 * @throws SQLException
	 */
	public int updatePracticeTest(PracticeTestBoardDto pDto) throws SQLException;

	/**
	 * 모의고사 삭제
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return int, 삭제된 행의 개수 반환
	 * @throws SQLException
	 */
	public int deleltePracticeTest(int articleNo) throws SQLException;

	/**
	 * 모의고사와 해당 모의고사에 포함되는 문제 리스트를 mapping
	 * 
	 * @param map - Map<String, Integer> - articleNo, problemNo 포함
	 * @return int, 등록된 행의 개수 반환
	 * @throws SQLException
	 */
	public int createPracticeTestMapping(Map<String, Integer> map) throws SQLException;

	/**
	 * 모의고사 번호로 해당 모의고사와 문제들의 mapping을 모두 삭제
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return int, 삭제된 행의 개수 반환
	 * @throws SQLException
	 */
	public int deletePracticeTestMapping(int articleNo) throws SQLException;

}

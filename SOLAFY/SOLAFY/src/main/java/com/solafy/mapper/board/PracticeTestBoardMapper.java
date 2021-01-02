package com.solafy.mapper.board;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.PracticeTestBoardDto;

/**
 * 
 * @FileName PracticeTestBoardMapper.java
 * @Project SOLAFY
 * @Date 2020. 12. 14.
 * @author 이주희
 *
 * @변경이력
 * 20-01-02 articleNo에서 practiceNo로 수정
 */

@Mapper
public interface PracticeTestBoardMapper {

	/**
	 * 모의고사 등록
	 * 
	 * @param pDto - PracticeTestBoardDto
	 * @return int, 등록된 행의 개수 반환
	 * @throws SQLException
	 * 
	 * @변경이력
	 */
	public int createPracticeTest(PracticeTestBoardDto pDto) throws SQLException;

	/**
	 * 모든 모의고사 반환
	 * 
	 * @return PracticeTestBoardDto의 List - 번호, uid, 제목, 등록시간, 시작시간, 종료시간, 별명 포함
	 * @throws SQLException
	 * 
	 * @변경이력
	 */
	public List<PracticeTestBoardDto> selectAllPracticeTest() throws SQLException;

	/**
	 * 모의고사 번호로 모의고사 상세 내용 반환
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return PracticeTestBoardDto - 번호, uid, 제목, 등록시간, 시작시간, 종료시간, 제한시간, 별명 포함
	 * @throws SQLException
	 * 
	 * @변경이력
	 * 20-01-02 반환 값을 PracticeTestBoardDto에서 List<PracticeTestBoardDto>로 수정
	 */
	public List<PracticeTestBoardDto> selectPracticeTestByPracticeNo(int practiceNo) throws SQLException;

	/**
	 * 검색된 별명과 작성자 별명이 동일한 모의고사들 반환
	 * 
	 * @param nickname - String, 검색된 nickname
	 * @return PracticeTestBoardDto의 List - 번호, uid, 제목, 등록시간, 시작시간, 종료시간, 별명 포함
	 * @throws SQLException
	 * 
	 * @변경이력 
	 * 20-12-28 uid 검색에서 별명 검색으로 변경
	 */
	public List<PracticeTestBoardDto> selectPracticeTestByWriter(String nickname) throws SQLException;

	/**
	 * 검색된 키워드가 제목에 포함된 모의고사들 반환
	 * 
	 * @param title - String, 검색된 제목 키워드
	 * @return PracticeTestBoardDto의 List - 번호, uid, 제목, 등록시간, 시작시간, 종료시간, 별명 포함
	 * @throws SQLException
	 * 
	 * @변경이력
	 */
	public List<PracticeTestBoardDto> selectPracticeTestByTitle(String title) throws SQLException;

	/**
	 * 모의고사 정보 업데이트
	 * 
	 * @param pDto - PracticeTestBoardDto - 제목, 등록,시작,종료,제한시간, 변경
	 * @return int, 업데이트된 행의 개수 반환
	 * @throws SQLException
	 * 
	 * @변경이력
	 */
	public int updatePracticeTest(PracticeTestBoardDto pDto) throws SQLException;

	/**
	 * 모의고사 삭제
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return int, 삭제된 행의 개수 반환
	 * @throws SQLException
	 * 
	 * @변경이력
	 */
	public int deleltePracticeTest(int practiceNo) throws SQLException;

	/**
	 * 모의고사와 해당 모의고사에 포함되는 문제의 매핑 추가
	 * 
	 * @param map - Map<String, Integer> - articleNo, problemNo 포함
	 * @return int, 등록된 행의 개수 반환
	 * @throws SQLException
	 * 
	 * @변경이력
	 */
	public int createPracticeTestMapping(Map<String, Integer> map) throws SQLException;

	/**
	 * 모의고사 번호로 해당 모의고사와 문제들의 매핑을 모두 삭제
	 * 
	 * @param articleNo - int, 모의고사 번호
	 * @return int, 삭제된 행의 개수 반환
	 * @throws SQLException
	 * 
	 * @변경이력
	 */
	public int deletePracticeTestMapping(int practiceNo) throws SQLException;

}

package com.solafy.mapper.user;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.UserDto;

/**
 * 
 * @FileName UserMapper.java
 * @Project SOLAFY
 * @Date 2020. 12. 27.
 * @Author 이주희
 * 
 * @변경이력
 */

@Mapper
public interface UserMapper {

	/**
	 * 사용자 등록
	 * 
	 * @param uDto - UserDto
	 * @return int, 등록된 행의 개수 반환
	 * @throws SQLException
	 *
	 * @변경이력
	 */
	public int createUser(UserDto uDto) throws SQLException;

	/**
	 * 모든 사용자 반환
	 * 
	 * @return UserDto의 List - uid, 별명, 상태메시지 포함
	 * @throws SQLException
	 *
	 * @변경이력
	 */
	public List<UserDto> selectAllUsers() throws SQLException;

	/**
	 * uid로 검색한 결과 반환
	 * 
	 * @param uid - String
	 * @return UserDto - uid, 별명, 상태메시지 포함
	 * @throws SQLException
	 *
	 * @변경이력
	 */
	public UserDto selectUserByUid(String uid) throws SQLException;

	/**
	 * 별명으로 검색한 결과 반환
	 * 
	 * @param nickname - String
	 * @return UserDto - uid, 별명, 상태메시지 포함
	 * @throws SQLException
	 *
	 * @변경이력
	 */
	public UserDto selectUserByNickname(String nickname) throws SQLException;

	/**
	 * 사용자 정보 수정 - uid를 기준으로 별명과 상태메시지를 수정
	 * 
	 * @param uDto - UserDto
	 * @return int - 수정된 행의 개수 반환
	 * @throws SQLException
	 *
	 * @변경이력
	 */
	public int updateUser(UserDto uDto) throws SQLException;

	/**
	 * 사용자 삭제
	 * 
	 * @param uid - String
	 * @return int - 삭제된 행의 개수 반환
	 * @throws SQLException
	 *
	 * @변경이력
	 */
	public int deleteUser(String uid) throws SQLException;
}

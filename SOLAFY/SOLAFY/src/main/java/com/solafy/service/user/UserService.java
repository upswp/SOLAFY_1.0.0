package com.solafy.service.user;

import java.util.List;

import com.solafy.model.UserDto;

/**
 * 
 * @FileName UserService.java
 * @Project SOLAFY
 * @Date 2020. 12. 27.
 * @Author 이주희
 * 
 * @변경이력
 */

public interface UserService {

	/**
	 * 사용자 등록
	 * 
	 * @param uDto - UserDto
	 * @return boolean, 등록 성공 시 true 반환
	 * @throws Exception
	 *
	 * @변경이력
	 */
	public boolean createUser(UserDto uDto) throws Exception;

	/**
	 * 모든 사용자 반환
	 * 
	 * @return UserDto의 List - uid, 별명, 상태메시지 포함
	 * @throws Exception
	 *
	 * @변경이력
	 */
	public List<UserDto> selectAllUsers() throws Exception;

	/**
	 * uid로 검색한 결과 반환
	 * 
	 * @param uid - String
	 * @return UserDto - uid, 별명, 상태메시지 포함
	 * @throws Exception
	 *
	 * @변경이력
	 */
	public UserDto selectUserByUid(String uid) throws Exception;

	/**
	 * 별명으로 검색한 결과 반환
	 * 
	 * @param nickname - String
	 * @return UserDto - uid, 별명, 상태메시지 포함
	 * @throws Exception
	 *
	 * @변경이력
	 */
	public UserDto selectUserByNickname(String nickname) throws Exception;
	
	/**
	 * 회원가입 신청한 사용자들 목록 반환 (admin column의 값이 2)
	 * 
	 * @return List<UserDto>
	 * @throws Exception
	 * 
	 * @변경이력 
	 * 21-01-01 추가
	 */
	public List<UserDto> selectRegiRequest() throws Exception;

	/**
	 * 사용자 정보 수정 - uid를 기준으로 별명과 상태메시지를 수정
	 * 
	 * @param uDto - UserDto
	 * @return boolean - 수정 성공 시 true 반환
	 * @throws Exception
	 *
	 * @변경이력
	 */
	public boolean updateUser(UserDto uDto) throws Exception;

	/**
	 * 사용자 삭제
	 * 
	 * @param uid - String
	 * @return boolean - 삭제 성공 시 true 반환
	 * @throws Exception
	 *
	 * @변경이력
	 */
	public boolean deleteUser(String uid) throws Exception;
}

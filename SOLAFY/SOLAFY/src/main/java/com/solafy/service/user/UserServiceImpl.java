package com.solafy.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.user.UserMapper;
import com.solafy.model.UserDto;

/**
 * 
 * @FileName UserServiceImpl.java
 * @Project SOLAFY
 * @Date 2020. 12. 27.
 * @Author 이주희
 * 
 * @변경이력
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean createUser(UserDto uDto) throws Exception {
		userMapper.createUser(uDto);
		return true;
	}

	@Override
	public List<UserDto> selectAllUsers() throws Exception {
		return userMapper.selectAllUsers();
	}

	@Override
	public UserDto selectUserByUid(String uid) throws Exception {
		return userMapper.selectUserByUid(uid);
	}

	@Override
	public UserDto selectUserByNickname(String nickname) throws Exception {
		return userMapper.selectUserByNickname(nickname);
	}

	@Override
	public boolean updateUser(UserDto uDto) throws Exception {
		userMapper.updateUser(uDto);
		return true;
	}

	@Override
	public boolean deleteUser(String uid) throws Exception {
		userMapper.deleteUser(uid);
		return true;
	}

}

package com.solafy.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solafy.model.UserDto;
import com.solafy.service.user.UserService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @FileName UserController.java
 * @Project SOLAFY
 * @Date 2020. 12. 27.
 * @Author 이주희
 * 
 * @변경이력
 */

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")

public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserService userService;

	/**
	 * 
	 * @param uDto
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "새로운 사용자를 등록하고 등록 결과를 반환한다.", response = String.class)
	@PostMapping(value = "/create")
	public ResponseEntity<String> createUser(@RequestBody UserDto uDto) throws Exception {
		logger.debug("createUser");
		if (userService.createUser(uDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "모든 사용자 목록을 반환한다.", response = List.class)
	@GetMapping(value = "/selectall")
	public ResponseEntity<List<UserDto>> selectAllUsers() throws Exception {
		logger.debug("selectAllUsers");
		return new ResponseEntity<List<UserDto>>(userService.selectAllUsers(), HttpStatus.OK);
	}

	/**
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "사용자 uid로  검색하여 사용자의 상세 정보를 반환한다.", response = UserDto.class)
	@GetMapping(value = "/selectbyuid/{uid}")
	public ResponseEntity<UserDto> selectUserByUid(@PathVariable String uid) throws Exception {
		logger.debug("selectUserByUid");
		return new ResponseEntity<UserDto>(userService.selectUserByUid(uid), HttpStatus.OK);
	}

	/**
	 * 
	 * @param nickname
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "사용자 별명으로  검색하여 사용자의 상세 정보를 반환한다.", response = UserDto.class)
	@GetMapping(value = "/selectbynickname/{nickname}")
	public ResponseEntity<UserDto> selectUserByNickname(@PathVariable String nickname) throws Exception {
		logger.debug("selectUserByNickname");
		return new ResponseEntity<UserDto>(userService.selectUserByNickname(nickname), HttpStatus.OK);
	}

	/**
	 * 
	 * @param nickname
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "사용자 별명으로  검색하여 별명의 존재 여부를 반환한다. OK-이미존재함, NO_CONTENT-없음(사용가능)", response = String.class)
	@GetMapping(value = "/searchnickname/{nickname}")
	public ResponseEntity<String> searchNickname(@PathVariable String nickname) throws Exception {
		logger.debug("searchNickname");
		if (userService.selectUserByNickname(nickname) != null)
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 * 21-01-01 추가
	 */
	@ApiOperation(value = "회원가입 신청한  회원들의 목록을 반환.", response = List.class)
	@GetMapping(value = "/selectregirequest")
	public ResponseEntity<List<UserDto>> selectRegiRequest() throws Exception {
		logger.debug("selectRegiRequest");
		return new ResponseEntity<List<UserDto>>(userService.selectRegiRequest(), HttpStatus.OK);
	}

	/**
	 * 
	 * @param uDto
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "사용자의 정보를 수정하고 결과를 반환한다.", response = String.class)
	@PutMapping(value = "/update")
	public ResponseEntity<String> updateUser(@RequestBody UserDto uDto) throws Exception {
		logger.debug("updateUser");
		if (userService.updateUser(uDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	/**
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 *
	 * @변경이력
	 */
	@ApiOperation(value = "사용자를 삭제하고 결과를 반환한다.", response = String.class)
	@DeleteMapping(value = "/delete/{uid}")
	public ResponseEntity<String> deleteUser(@PathVariable String uid) throws Exception {
		logger.debug("deleteUser");
		if (userService.deleteUser(uid))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}

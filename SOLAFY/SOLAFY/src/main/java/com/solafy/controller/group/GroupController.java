package com.solafy.controller.group;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solafy.model.GroupDto;
import com.solafy.service.group.GroupService;

import io.swagger.annotations.ApiOperation;

/**
 * group CRUD controller
 * @author Yun Kihyeon
 * @since 2020-12-16
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/group")

public class GroupController {
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private GroupService groupService;
	
//	===============================CREATE=============================== //
	@ApiOperation(value = "그룹을 생성하고 결과를 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> createGroup(@RequestBody GroupDto group)throws Exception{
		logger.debug("createGroup 호출");
		if(groupService.createGroup(group))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
//	===================================================================== //

	
	
	
//	===============================SELECT=============================== //	
	@ApiOperation(value = "전체 그룹 리스트 정보를 받아온다.", response = List.class)
	@GetMapping(value = "/selectAllGroup")
	public ResponseEntity<List<GroupDto>> selectAllGroup() throws Exception{
		logger.debug("selectAllGroup 호출");
		return new ResponseEntity<List<GroupDto>>(groupService.selectAllGroup(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택한 그룹의 정보를 받아온다.", response = GroupDto.class)
	@GetMapping(value = "/selectGroupByNo/{groupNo}")
	public ResponseEntity<GroupDto> selectGroupByNo(@PathVariable int groupNo) throws Exception{
		logger.debug("selectGroupByNo 호출");
		return new ResponseEntity<GroupDto>(groupService.selectGroupByNo(groupNo),HttpStatus.OK);
	}
	
	@ApiOperation(value = "그룹 이름 중복을 확인한다.", response = String.class)
	@GetMapping(value = "/CheckDuplicateName/{title}")
	public ResponseEntity<String> CheckDuplicateName(@PathVariable String title)throws Exception{
		return new ResponseEntity<String>(groupService.CheckDuplicateName(title), HttpStatus.OK);
	}
	
//	===================================================================== //
	
	
//	===============================UPDATE=============================== //	
	@ApiOperation(value = "그룹 정보를 수정한다.", response = String.class)
	@PutMapping(value="/updateGroup")
	public ResponseEntity<String> updateGroup(@RequestBody GroupDto group)throws Exception{
		if(groupService.updateGroup(group))
			return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "그룹원 권한을 수정한다.", response = String.class)
	@PutMapping(value = "/updatePermissionOfMember/{}")
	public ResponseEntity<String> updatePermissionOfMember(@PathVariable String uid)throws Exception{
		if(groupService.updatePermissionOfMember(uid))
			return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
	}
//	===================================================================== //

	
	
//	===============================DELETE=============================== //
//	@ApiOperation(value = "그룹을 삭제한다.", response = String.class)
//	@ApiOperation(value = "그룹원을 삭제한다.", response = String.class)
//	@ApiOperation(value = "그룹을 탈퇴한다.", response = String.class)
//	===================================================================== //	

	
/*	=========================== Firebase로 넘길까? =========================
 *	1. 회원이 가입한 그룹
 *	2. 가입 신청
 *  3. 가입 수락:"
 *	======================================================================
 */
	

}

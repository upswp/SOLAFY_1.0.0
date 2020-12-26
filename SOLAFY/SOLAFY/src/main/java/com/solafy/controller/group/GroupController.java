
package com.solafy.controller.group;

import java.util.List;
import java.util.Map;

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

import com.solafy.model.GroupDto;
import com.solafy.model.GroupMemberDto;
import com.solafy.service.group.GroupService;

import io.swagger.annotations.ApiOperation;


/**
* @FileName : GroupController.java
* @Project : SOLAFY
* @Date : 2020. 12. 16
* @작성자 : Yun Kihyeon

* @변경이력 :
* 2020. 12. 18. : 초대, 초대수락, 신청, 신청수락, 탈퇴 methods 작성 
* @프로그램 설명 :Group backEnd controller
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
	/**
	* @param group
	* @return
	* @throws Exception
	* @Method 설명 :그룹을 생성하고 success or fail 결과를 반환
	* @변경이력 :
	*/
	@ApiOperation(value = "그룹을 생성하고 결과를 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> createGroup(@RequestBody GroupDto group)throws Exception{
		logger.debug("createGroup 호출");
		if(groupService.createGroup(group))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	//TODO : Message를 통해 그룹 관리자에게 메세지 전송
	/**
	* @param groupMember
	* @return
	* @throws Exception
	* @Method 설명 : 일반회원이 그룹에 가입을 신청한다(신청한 그룹 관리자에게 메시지를 보낸다. 서비스 방향 결정x)
	* @변경이력 :
	*/
	@ApiOperation(value = "그룹 가입을 신청한다.", response = String.class)
	@PostMapping(value="/createApplyGroupSignUp")
	public ResponseEntity<String> createApplyGroupSignUp(@RequestBody GroupMemberDto groupMember)throws Exception{
		logger.debug("createApplyGroupSignUp 호출");
		System.out.println(groupMember);
		if(groupService.createApplyGroupSignUp(groupMember))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	

	//TODO : Message를 통해 초대 회원에게 메세지 전송
	
	/**
	* @param groupMember
	* @return
	* @throws Exception
	* @Method 설명 : 그룹에 회원을 초대하고 초대 메세지를 전송한다.(메세지 전송은 아직 방향을 결정하지 않았음)
	* @변경이력 :
	*/
	@ApiOperation(value = "그룹에 회원을 초대한다.", response = String.class)
	@PutMapping(value="/createGroupInvitation")
	public ResponseEntity<String> createGroupInvitation(@RequestBody GroupMemberDto groupMember)throws Exception{
		logger.debug("createGroupInvitation 호출");
		if(groupService.createGroupInvitation(groupMember))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
//	===================================================================== //

	
	
	
//	===============================SELECT=============================== //	


	/**
	* @return
	* @throws Exception
	* @Method 설명 : 전체 그룹 리스트의 정보를 받아온다.
	* @변경이력 :
	*/
	@ApiOperation(value = "전체 그룹 리스트 정보를 받아온다.", response = List.class)
	@GetMapping(value = "/selectAllGroup/{uid}")
	public ResponseEntity<List<GroupDto>> selectAllGroup(@PathVariable String uid) throws Exception{
		logger.debug("selectAllGroup 호출   "+uid);
		return new ResponseEntity<List<GroupDto>>(groupService.selectAllGroup(uid), HttpStatus.OK);
	}
	
	/**
	* @param groupNo
	* @return
	* @throws Exception
	* @Method 설명 : 그룹 리스트에서 선택된 그룹의 정보를 받아온다.
	* @변경이력 :
	*/
	@ApiOperation(value = "선택한 그룹의 정보를 받아온다.", response = GroupDto.class)
	@GetMapping(value = "/selectGroupByNo/{groupNo}")
	public ResponseEntity<GroupDto> selectGroupByNo(@PathVariable int groupNo) throws Exception{
		logger.debug("selectGroupByNo 호출");
		return new ResponseEntity<GroupDto>(groupService.selectGroupByNo(groupNo),HttpStatus.OK);
	}
	
	/**
	* @param title
	* @return
	* @throws Exception
	* @Method 설명 : 그룹 이름의 중복을 확인한다.
	* @변경이력 :
	*/
	@ApiOperation(value = "그룹 이름 중복을 확인한다.", response = String.class)
	@GetMapping(value = "/checkDuplicateName/{title}")
	public ResponseEntity<String> checkDuplicateName(@PathVariable String title)throws Exception{
		logger.debug("checkDuplicateName 호출");
		if(groupService.checkDuplicateName(title))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
//	===================================================================== //
	
	
//	===============================UPDATE=============================== //	

	/**
	* @param group
	* @return
	* @throws Exception
	* @Method 설명 : 관리자가 그룹의 정보를 수정한다.
	* @변경이력 :
	*/
	@ApiOperation(value = "그룹 정보를 수정한다.", response = String.class)
	@PutMapping(value="/updateGroup")
	public ResponseEntity<String> updateGroup(@RequestBody GroupDto group)throws Exception{
		logger.debug("updateGroup 호출");
		if(groupService.updateGroup(group))
			return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
	}
	

	/**
	* @param groupMember
	* @return
	* @throws Exception
	* @Method 설명 : 관리자가 그룹원 권한을 수정한다.
	* @변경이력 :
	*/
	@ApiOperation(value = "그룹원 권한을 수정한다.", response = String.class)
	@PutMapping(value = "/updatePermissionOfMember")
	public ResponseEntity<String> updatePermissionOfMember(@RequestBody GroupMemberDto groupMember)throws Exception{
		logger.debug("updatePermissionOfMember 호출");
		if(groupService.updatePermissionOfMember(groupMember))
			return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
	}
	


	/**
	* @param groupMember
	* @return
	* @throws Exception
	* @Method 설명 : 관리자가 가입 신청을 수락한다.
	* @변경이력 :
	*/
	@ApiOperation(value = "그룹 가입을 수락한다.", response = String.class)
	@PutMapping(value="/updateGroupApplyConfirm")
	public ResponseEntity<String> updateGroupApplyConfirm(@RequestBody GroupMemberDto groupMember)throws Exception{
		logger.debug("updateGroupApplyConfirm 호출");
		if(groupService.updateGroupApplyConfirm(groupMember))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	


	/**
	* @param groupMember
	* @return
	* @throws Exception
	* @Method 설명 : 일반회원이 초대된 그룹의 초대를 수락한다.
	* @변경이력 :
	*/
	@ApiOperation(value = "회원이 그룹 초대를 수락한다.", response = String.class)
	@PutMapping(value="/updateGroupInvitationConfirm")
	public ResponseEntity<String> updateGroupInvitationConfirm(@RequestBody GroupMemberDto groupMember)throws Exception{
		logger.debug("updateGroupInvitationConfirm 호출");
		if(groupService.updateGroupInvitationConfirm(groupMember))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	} 
	
//	===================================================================== //

	
	
//	===============================DELETE=============================== //

	/**
	* @param groupNo
	* @return
	* @throws Exception
	* @Method 설명 : 관리자가 그룹을 삭제한다.
	* @변경이력 :
	*/
	@ApiOperation(value = "그룹을 삭제한다.", response = String.class)
	@DeleteMapping(value = "/deleteGroup/{groupNo}")
	public ResponseEntity<String> deleteGroup(@PathVariable int groupNo)throws Exception{
		if(groupService.deleteGroup(groupNo))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
				
	}


	/**
	* @param uid
	* @return
	* @throws Exception
	* @Method 설명 : 그룹 관리자가 그룹원을 탈퇴시킨다. uid와 groupNo가 필요하다.
	* @변경이력 :
	*/
	@ApiOperation(value = "그룹원을 삭제한다.", response = String.class)
	@DeleteMapping(value="/deleteGroupMember")
	public ResponseEntity<String> deleteGroupMember(@RequestBody Map<String, Object> deleteMember)throws Exception{
		if(groupService.deleteGroupMember(deleteMember))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}


	/**
	* @param uid
	* @return
	* @throws Exception
	* @Method 설명 : 맴버가 스스로 그룹을 탈퇴한다. uid와 groupNo가 필요하다.
	* @변경이력 :
	*/
	@ApiOperation(value = "그룹을 탈퇴한다.", response = String.class)
	@DeleteMapping(value="/deleteGroupMemberself")
	public ResponseEntity<String> deleteGroupMemberself(@RequestBody Map<String, Object> deleteMemberSelf)throws Exception{
		System.out.println(deleteMemberSelf);
		if(groupService.deleteGroupMemberself(deleteMemberSelf))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
//	===================================================================== //	
	//TODO : 회원이 가입한 그룹 -> firebase에 그룹 번호 저장	
}

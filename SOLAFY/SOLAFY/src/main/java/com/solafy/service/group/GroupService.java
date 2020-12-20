package com.solafy.service.group;

import java.util.List;
import java.util.Map;

import com.solafy.model.GroupDto;
import com.solafy.model.GroupMemberDto;

/**
* @FileName : GroupService.java
* @Project : SOLAFY
* @Date : 2020. 12. 19
* @작성자 : Yun Kihyeon

* @변경이력 :
* @프로그램 설명 : 그룹CRUD 및 가입관련 서비스
*/
public interface GroupService {
	
	/**
	* @Method Name : createGroup
	* @작성일 : 2020. 12. 19
	* @작성자 : Yun Kihyeon
	* @Method 설명 : 그룹을 생성한다.
	* @변경이력 :
	*/
	boolean createGroup(GroupDto group)throws Exception;

	/**
	* @param groupMember
	* @return
	* @throws Exception
	* @Method 설명 : 그룹에 가입신청을 한다.
	* @변경이력 :
	*/
	boolean createApplyGroupSignUp(GroupMemberDto groupMember)throws Exception;
	
	/**
	* @param groupMember
	* @return
	* @throws Exception
	* @Method 설명 : 그룹에 일반 회원을 초대한다.
	* @변경이력 :
	*/
	boolean createGroupInvitation(GroupMemberDto groupMember)throws Exception;
	
	/**
	* @return
	* @throws Exception
	* @Method 설명 : 모든 그룹을 list로 반환한다.
	* @변경이력 :
	*/
	List<GroupDto> selectAllGroup()throws Exception;
	
	/**
	* @param groupNo
	* @return
	* @throws Exception
	* @Method 설명 : 1개의 그룹의 정보를 받아온다.
	* @변경이력 :
	*/
	GroupDto selectGroupByNo(int groupNo)throws Exception;
	
	/**
	* @param title
	* @return
	* @throws Exception
	* @Method 설명 : 그룹명이 중복인지 확인한다.
	* @변경이력 :
	*/
	boolean checkDuplicateName(String title)throws Exception;
	
	/**
	* @param group
	* @return
	* @throws Exception
	* @Method 설명 : 그룹의 정보를 수정한다.
	* @변경이력 :
	*/
	boolean updateGroup(GroupDto group)throws Exception;
	
	/**
	* @param GroupMember
	* @return
	* @throws Exception
	* @Method 설명 : 그룹 맴버의 권한을 업데이트 한다.
	* @변경이력 :
	*/
	boolean updatePermissionOfMember(GroupMemberDto GroupMember)throws Exception;
	
	/**
	* @param groupMember
	* @return
	* @throws Exception
	* @Method 설명 : 일반회원의 가입신청을 수락한다.
	* @변경이력 :
	*/
	boolean updateGroupApplyConfirm(GroupMemberDto groupMember)throws Exception;
	
	/**
	* @param groupMember
	* @return
	* @throws Exception
	* @Method 설명 : 일반회원이 초대된 그룹의 가입을 수락한다. 
	* @변경이력 :
	*/
	boolean updateGroupInvitationConfirm(GroupMemberDto groupMember)throws Exception;
	
	/**
	* @param groupNo
	* @return
	* @throws Exception
	* @Method 설명 : 그룹을 삭제한다.
	* @변경이력 :
	*/
	boolean deleteGroup(int groupNo)throws Exception;
	
	/**
	* @param uid
	* @return
	* @throws Exception
	* @Method 설명 : 그룹의 맴버를 탈퇴시킨다(관리자 권한 있어야 함)
	* @변경이력 :
	*/
	boolean deleteGroupMember(String uid)throws Exception;
	
	/**
	* @param uid
	* @return
	* @throws Exception
	* @Method 설명 : 그룹 맴버가 스스로 탈퇴한다.
	* @변경이력 :
	*/
	boolean deleteGroupMemberself(String uid)throws Exception;
}

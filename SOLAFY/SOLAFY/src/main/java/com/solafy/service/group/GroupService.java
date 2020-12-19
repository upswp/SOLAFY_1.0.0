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
* @프로그램 설명 :
*/
public interface GroupService {
	
	boolean createGroup(GroupDto group)throws Exception;
	boolean createApplyGroupSignUp(GroupMemberDto groupMember)throws Exception;
	boolean createGroupInvitation(GroupMemberDto groupMember)throws Exception;
	
	List<GroupDto> selectAllGroup()throws Exception;
	GroupDto selectGroupByNo(int groupNo)throws Exception;
	boolean checkDuplicateName(String title)throws Exception;
	
	boolean updateGroup(GroupDto group)throws Exception;
	boolean updatePermissionOfMember(GroupMemberDto GroupMember)throws Exception;
	boolean updateGroupApplyConfirm(GroupMemberDto groupMember)throws Exception;
	boolean updateGroupInvitationConfirm(GroupMemberDto groupMember)throws Exception;
	
	boolean deleteGroup(int groupNo)throws Exception;
	boolean deleteGroupMember(String uid)throws Exception;
	boolean deleteGroupMemberself(String uid)throws Exception;
}

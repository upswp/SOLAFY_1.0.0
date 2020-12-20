package com.solafy.mapper.group;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.GroupDto;
import com.solafy.model.GroupMemberDto;

/**
* @FileName : GroupMapper.java
* @Project : SOLAFY
* @Date : 2020. 12. 18
* @작성자 : Yun Kihyeon

* @변경이력 :
* @프로그램 설명 :
*/
@Mapper
public interface GroupMapper {
	int createGroup(GroupDto group)throws Exception;
	
	//TODO : 아래 2개를 합치는 것에 대한 이슈
	int createApplyGroupSignUp(GroupMemberDto groupMember)throws Exception;
	int createGroupInvitation(GroupMemberDto groupMember)throws Exception;
	
	//TODO : private 그룹도 보여줄 것인가?
	List<GroupDto> selectAllGroup()throws Exception;
	GroupDto selectGroupByNo(int groupNo)throws Exception;
	int checkDuplicateName(String title)throws Exception;
	
	int updateGroup(GroupDto group)throws Exception;
	int updatePermissionOfMember(GroupMemberDto groupMember)throws Exception;
	int updateGroupApplyConfirm(GroupMemberDto groupMember)throws Exception;
	int updateGroupInvitationConfirm(GroupMemberDto groupMember)throws Exception;
	
	int deleteGroup(int groupNo)throws Exception;
	int deleteGroupMember(String uid)throws Exception;
	int deleteGroupMemberself(String uid)throws Exception;
}

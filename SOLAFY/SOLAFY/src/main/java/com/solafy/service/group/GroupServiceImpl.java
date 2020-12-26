package com.solafy.service.group;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solafy.mapper.group.GroupMapper;
import com.solafy.model.GroupDto;
import com.solafy.model.GroupMemberDto;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupMapper groupMapper;
	
	@Override
	public boolean createGroup(GroupDto group) throws Exception {
		return groupMapper.createGroup(group) > 0 ? true : false;
	}
	@Override
	public boolean createApplyGroupSignUp(GroupMemberDto groupMember) throws Exception {
		return groupMapper.createApplyGroupSignUp(groupMember) > 0 ? true : false;
	}
	@Override
	public boolean createGroupInvitation(GroupMemberDto groupMember) throws Exception {
		return groupMapper.createGroupInvitation(groupMember) > 0 ? true : false;
	}
	@Override
	public List<GroupDto> selectAllGroup(String uid) throws Exception {
		return groupMapper.selectAllGroup(uid);
	}

	@Override
	public GroupDto selectGroupByNo(int groupNo) throws Exception {
		return groupMapper.selectGroupByNo(groupNo);
	}

	@Override
	public boolean checkDuplicateName(String title) throws Exception {
		return groupMapper.checkDuplicateName(title) == 0 ? true : false;
	}

	@Override
	public boolean updateGroup(GroupDto group) throws Exception {
		return groupMapper.updateGroup(group) > 0 ? true : false;
	}

	@Override
	public boolean updatePermissionOfMember(GroupMemberDto GroupMember) throws Exception {
		return groupMapper.updatePermissionOfMember(GroupMember) > 0 ? true : false;
	}
	@Override
	public boolean updateGroupApplyConfirm(GroupMemberDto groupMember) throws Exception {
		return groupMapper.updateGroupApplyConfirm(groupMember)> 0 ? true : false;
	}
	@Override
	public boolean updateGroupInvitationConfirm(GroupMemberDto groupMember) throws Exception {
		return groupMapper.updateGroupInvitationConfirm(groupMember) > 0 ? true : false;
	}
	@Override
	public boolean deleteGroup(int groupNo) throws Exception {
		return groupMapper.deleteGroup(groupNo) > 0 ? true : false;
	}

	@Override
	public boolean deleteGroupMember(Map<String, Object> deleteMember) throws Exception {
		return groupMapper.deleteGroupMember(deleteMember) > 0 ? true : false;
	}
	@Override
	public boolean deleteGroupMemberself(Map<String, Object> deleteMemberSelf) throws Exception {
		return groupMapper.deleteGroupMemberself(deleteMemberSelf) > 0 ? true : false;
	}

}

package com.solafy.service.group;

import java.util.List;

import com.solafy.model.GroupDto;

public interface GroupService {
	boolean createGroup(GroupDto group)throws Exception;
	List<GroupDto> selectAllGroup()throws Exception;
	GroupDto selectGroupByNo(int groupNo)throws Exception;
	String CheckDuplicateName(String title)throws Exception;
	boolean updateGroup(GroupDto group)throws Exception;
	boolean updatePermissionOfMember(String uid);
	
}
